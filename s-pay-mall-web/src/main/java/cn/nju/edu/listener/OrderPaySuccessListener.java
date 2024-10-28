package cn.nju.edu.listener;

import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：OrderPaySuccessListener
 * 作者：tkj
 * 日期：2024/10/28
 */
@Slf4j
@Component
public class OrderPaySuccessListener {
    @Subscribe
    public void handleEvent(String paySuccessMessage){
        log.info("支付成功，可以接下来做下面的事情，如：发货、充值、开会员、返利{}",paySuccessMessage);
    }

}
