package vip.mate.gateway.init;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
import com.alibaba.nacos.common.notify.listener.Subscriber;
import com.alibaba.nacos.common.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import com.alibaba.nacos.common.notify.NotifyCenter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.loadbalancer.cache.DefaultLoadBalancerCacheManager;
import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import reactor.core.publisher.Mono;
import vip.mate.core.common.constant.MateConstant;
import vip.mate.gateway.entity.GatewayRoute;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import static org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME;

/**
 * 动态路由初始化类
 * 请将/doc/nacos目录下的mate-dynamic-routes.yaml配置至nacos
 *
 * @author pangu
 * @since 2.3.8
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DynamicRouteInit {

	private final RouteDefinitionWriter routeDefinitionWriter;
	private final NacosConfigProperties nacosProperties;

	@PostConstruct
	public void initRoute() throws NacosException {
		nacosConfigListener();

		nacosServiceDiscoveryListener();
	}
	public void nacosConfigListener() throws NacosException {
		try {
			Properties properties = new Properties();
			properties.put(PropertyKeyConst.SERVER_ADDR, nacosProperties.getServerAddr());
			properties.put(PropertyKeyConst.USERNAME, nacosProperties.getUsername());
			properties.put(PropertyKeyConst.PASSWORD, nacosProperties.getPassword());
			properties.put(PropertyKeyConst.NAMESPACE, nacosProperties.getNamespace());
			ConfigService configService = NacosFactory.createConfigService(properties);

			String content = configService.getConfig(MateConstant.CONFIG_DATA_ID_DYNAMIC_ROUTES, nacosProperties.getGroup(), MateConstant.CONFIG_TIMEOUT_MS);
			log.info("初始化网关路由开始");
			updateRoute(content);
			log.info("初始化网关路由完成");
			//开户监听，实现动态
			configService.addListener(MateConstant.CONFIG_DATA_ID_DYNAMIC_ROUTES, nacosProperties.getGroup(), new Listener() {
				@Override
				public void receiveConfigInfo(String configInfo) {
					if(configInfo!=null) {
						log.info("更新网关路由开始");
						updateRoute(configInfo);
						log.info("更新网关路由完成");
					}
				}
				@Override
				public Executor getExecutor() {
					return null;
				}
			});
		} catch (NacosException e) {
			log.error("加载路由出错：{}", e.getErrMsg());
		}
	}

	public void updateRoute(String content) {
		Yaml yaml = new Yaml();
		GatewayRoute gatewayRoute = yaml.loadAs(content, GatewayRoute.class);
		gatewayRoute.getRoutes().forEach(route -> {
			log.info("加载路由：{},{}", route.getId(), route);
			routeDefinitionWriter.save(Mono.just(route)).subscribe();
		});
	}
	@Autowired
	private NacosInstancesChangeEventListener nacosInstancesChangeEventListener;

	public void nacosServiceDiscoveryListener() {
		// 实例刷新
		NotifyCenter.registerSubscriber(nacosInstancesChangeEventListener);
	}

	@Component
	public static class NacosInstancesChangeEventListener extends Subscriber<InstancesChangeEvent> {
		private static final Logger logger = LoggerFactory.getLogger(NacosInstancesChangeEventListener.class);

		@Resource
		private CacheManager defaultLoadBalancerCacheManager;


		@Resource
		private ApplicationContext applicationContext;
		@Override
		public void onEvent(InstancesChangeEvent event) {
			logger.info("Spring Gateway 接收实例刷新事件：{}, 开始刷新缓存", JacksonUtils.toJson(event));


			LoadBalancerCacheManager loadBalancerCacheManager = applicationContext.getBean(LoadBalancerCacheManager.class);
			log.info("bean:{}",loadBalancerCacheManager);
			/*java.util.concurrent.ConcurrentMap<String, Cache> cacheMap = (java.util.concurrent.ConcurrentMap<String, Cache>) ReflectUtil.getFieldValue(loadBalancerCacheManager, "cacheMap");
			cacheMap.forEach((key, value) -> {
				log.info("key:{}\tvalue:{}",key,value);
				value.clear();
			});*/

			Cache cache = loadBalancerCacheManager.getCache(SERVICE_INSTANCE_CACHE_NAME);
			if (cache != null) {
			Object data=	cache.get(event.getServiceName());
			if(data!=null) {
				logger.info("cache-data:"+cache.get(event.getServiceName()).toString());
			}
				cache.evict(event.getServiceName());
			}
			logger.info("Spring Gateway 实例刷新完成");

		}

		@Override
		public Class<? extends com.alibaba.nacos.common.notify.Event> subscribeType() {
			return InstancesChangeEvent.class;
		}
	}

}
