/*
package vip.mate.gateway.init;


import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.NacosServiceManager;
import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cloud.loadbalancer.cache.DefaultLoadBalancerCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME;

*/
/**
 * 监听 config 监听
 *
 * @author huan.fu
 * @date 2023/3/7 - 22:55
 *//*

@Component
@RequiredArgsConstructor
@Slf4j
public class ListenerConfigChange {
    @Resource
    private DefaultLoadBalancerCacheManager defaultLoadBalancerCacheManager;
    @Resource
    private NacosServiceManager nacosServiceManager;
    @Resource
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Resource
    private NacosServiceDiscovery nacosServiceDiscovery;
    @Resource
    private NacosConfigManager nacosConfigManager;

    */
/**
     * 注解生效：参考 <a href="https://github.com/nacos-group/nacos-spring-project">https://github.com/nacos-group/nacos-spring-project</a>
     * <a href="https://github.com/alibaba/spring-cloud-alibaba/issues/458">https://github.com/alibaba/spring-cloud-alibaba/issues/458</a>
     *
     * @param config 配置
     *//*

    @NacosConfigListener(groupId = "DEFAULT_GROUP", dataId = "mate.yaml")
    public void configChange(String config) {
        log.warn("==>接收到 无损服务下线 配置变更:[{}]", config);
        String serviceName = config.split("")[0];
        log.info("需要无损下线的服务名:[{}]", serviceName);

        Cache cache = defaultLoadBalancerCacheManager.getCache(SERVICE_INSTANCE_CACHE_NAME);
        if (null != cache) {
            cache.evict(serviceName);
            log.info("失效serviceName:[{}]的缓存", serviceName);
        }
    }


    private final ConfigChangeListener configChangeListener = new ConfigChangeListener();

    // @PostConstruct
    public void init() throws NacosException {
        configChangeListener.setNacosConfigManager(nacosConfigManager);
      //  nacosConfigManager.getConfigService()
       //         .addListener(DATA_ID, GROUP, configChangeListener);
    }

    // @PreDestroy
    public void destroy() {
       // nacosConfigManager.getConfigService()
        //        .removeListener(DATA_ID, GROUP, configChangeListener);
    }

    @Slf4j
    static class ConfigChangeListener extends AbstractConfigChangeListener {

        private final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        @Getter
        @Setter
        private NacosConfigManager nacosConfigManager;

        @Override
        public void receiveConfigChange(ConfigChangeEvent event) {
            try {
                log.warn("接收到 无损服务下线 配置变更:[{}]", OBJECT_MAPPER.writeValueAsString(event));
               // try {
                    // https://github.com/alibaba/nacos/issues/2060
                    // 发布配置是异步的，写入数据库就返回成功。但是不一定所有Server都更新到了最新的配置。
                  //  String config = nacosConfigManager.getConfigService()
                  //          .getConfig(DATA_ID, GROUP, 10000);
                  //  System.out.println(config);
               // } catch (NacosException e) {
               //     throw new RuntimeException(e);
               // }
            } catch (JsonProcessingException e) {
                log.error("监听 无损服务下线 配置变更失败", e);
            }
        }
    }
}*/
