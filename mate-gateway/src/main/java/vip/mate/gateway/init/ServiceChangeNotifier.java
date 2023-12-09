//package vip.mate.gateway.init;
//
//import com.alibaba.nacos.api.common.Constants;
//import com.alibaba.nacos.client.naming.event.InstancesChangeEvent;
//import com.alibaba.nacos.common.notify.NotifyCenter;
//import com.alibaba.nacos.common.notify.listener.Subscriber;
//import com.alibaba.nacos.common.notify.Event;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.loadbalancer.cache.LoadBalancerCacheManager;
//import org.springframework.stereotype.Component;
//import org.springframework.cache.Cache;
//import org.springframework.cache.CacheManager;
//import org.springframework.cloud.loadbalancer.core.CachingServiceInstanceListSupplier;
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
///**
// * 服务变更监听
// *
// * @author butterfly
// * @date 2023-09-24
// */
//@Slf4j
//@Component
//public class ServiceChangeNotifier extends Subscriber<InstancesChangeEvent> {
//
//    /**
//     * 由于会有多个类型的 CacheManager bean, 这里的 defaultLoadBalancerCacheManager 名称不可修改
//     */
//    @Resource
//   // private CacheManager defaultLoadBalancerCacheManager;
//    private LoadBalancerCacheManager loadBalancerCacheManager;
//    @PostConstruct
//    public void init() {
//        // 注册当前自定义的订阅者以获取通知
//        NotifyCenter.registerSubscriber(this);
//    }
//
//    @Override
//    public void onEvent(InstancesChangeEvent event) {
//        String serviceName = event.getServiceName();
//        // 使用 dubbo 时包含 rpc 服务类会注册以 providers: 或者 consumers: 开头的服务
//        // 由于不是正式的服务, 这里需要进行排除, 如果未使用 dubbo 则不需要该处理
//        if (serviceName.contains(":")) {
//            return;
//        }
//        // serviceName 格式为 groupName@@name
//        String split = Constants.SERVICE_INFO_SPLITER;
//        if (serviceName.contains(split)) {
//            serviceName = serviceName.substring(serviceName.indexOf(split) + split.length());
//        }
//        log.info("服务上下线: {}", serviceName);
//        // 手动更新服务列表
//        Cache cache = loadBalancerCacheManager.getCache(
//                CachingServiceInstanceListSupplier.SERVICE_INSTANCE_CACHE_NAME);
//        if (cache != null) {
//            cache.evictIfPresent(serviceName);
//        }
//    }
//
//    @Override
//    public Class<? extends Event> subscribeType() {
//        return InstancesChangeEvent.class;
//    }
//
//}
