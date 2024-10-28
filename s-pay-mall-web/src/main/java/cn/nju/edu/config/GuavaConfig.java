package cn.nju.edu.config;

import cn.nju.edu.domain.res.WeixinTokenRes;
import cn.nju.edu.listener.OrderPaySuccessListener;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.eventbus.EventBus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：GuavaConfig
 * 作者：tkj
 * 日期：2024/10/25
 */
@Configuration
public class GuavaConfig {

    /**
     * 这是使用了Guava缓存，用于返回装有accessToken的缓存Cache，expireAfterWrite(2, TimeUnit.HOURS)表明设立规则，
     * 每个存入的键值对有效时间只有2h
     * @return
     */
    //这个cache存accesstoken
    @Bean(name = "weixinAccessTokenCache")
    public Cache<String ,String> weixinAccessTokenCache(){
        return CacheBuilder.newBuilder()
                .expireAfterWrite(2, TimeUnit.HOURS)
                .build();
    }

    @Bean(name = "openidTokenCache")
    public Cache<String ,String> openidTokenCache(){
        return CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.HOURS)
                .build();
    }

    @Bean(name = "eventBus")
    public EventBus eventBusListener(OrderPaySuccessListener listener){
        EventBus eventBus = new EventBus();
        eventBus.register(listener);
        return eventBus;
    }
}
