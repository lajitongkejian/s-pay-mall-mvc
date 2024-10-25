package cn.nju.edu.config;

import cn.nju.edu.service.weixin.IWeixinApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：Retrofit2Config
 * 作者：tkj
 * 日期：2024/10/25
 */
@Slf4j
@Configuration
public class Retrofit2Config {

    private static final String BASE_URL = "https://api.weixin.qq.com/";

    /**
     * 创建Retrofit实例，装载Json转换器，这里选用的Jackson
     * @return
     */
    @Bean
    public Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    /**
     * 向Retrofit注册你放有请求方法列表的接口，这样Retrofit可以为你创建一个实现接口的实例对象，
     * 然后就可以通过这个对象去调用http方法
     * @return
     */

    @Bean
    public IWeixinApiService iWeixinApiService(){
        return retrofit().create(IWeixinApiService.class);
    }
}
