package cn.nju.edu.job;

import cn.nju.edu.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：TimeOutCloseOrderJob
 * 作者：tkj
 * 日期：2024/10/28
 * 超时关闭订单
 */
@Slf4j
@Component
public class TimeOutCloseOrderJob {
    @Resource
    private IOrderService service;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void exec(){
        try{
            log.info("任务：超时三十分钟订单修改为关闭状态");
            List<String> orderIds = service.queryTimeOutCloseOrderList();
            if(null == orderIds || orderIds.isEmpty()) {
                log.info("暂时无超时订单");
                return;
            }
            for(String orderId:orderIds){
                boolean isSuccess = service.changeOrderClose(orderId);
                log.info("超时30min订单关闭，订单号：{}，订单是否关闭：{}",orderId,isSuccess);
            }



        }catch (Exception e){
            log.error("定时任务，超时订单关闭失败",e);
        }
    }

}
