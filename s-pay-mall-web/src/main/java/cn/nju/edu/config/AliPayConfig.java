package cn.nju.edu.config;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：AliPayConfig
 * 作者：tkj
 * 日期：2024/10/26
 */
@Configuration
@EnableConfigurationProperties(AliPayConfigProperties.class)
public class AliPayConfig {

    @Bean("alipayClient")
    public AlipayClient alipayClient(AliPayConfigProperties properties){
        return new DefaultAlipayClient(properties.getGatewayUrl(),
                properties.getApp_id(),
                properties.getMerchant_private_key(),
                properties.getFormat(),
                properties.getCharset(),
                properties.getAlipay_public_key(),
                properties.getSign_type());
    }
}
