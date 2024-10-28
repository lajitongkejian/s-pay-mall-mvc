package cn.nju.edu.job;

import cn.nju.edu.service.IOrderService;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeQueryResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：NoPayNotifyOrderJob
 * 作者：tkj
 * 日期：2024/10/28
 */
@Slf4j
@Component
public class NoPayNotifyOrderJob {

    @Resource
    private IOrderService orderService;

    @Resource
    private AlipayClient alipayClient;

    @Scheduled(cron = "0/3 * * * * ?")
    public void exec(){

        try{
            log.info("任务：检测未正确处理的支付订单");
            List<String> orderIds = orderService.queryNoPayNotifyOrderList();
            if(null == orderIds || orderIds.isEmpty()) return;
            for(String orderId : orderIds){
                AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
                AlipayTradeQueryModel bizModel = new AlipayTradeQueryModel();
                bizModel.setOutTradeNo(orderId);
                request.setBizModel(bizModel);

                AlipayTradeQueryResponse alipayTradeQueryResponse = alipayClient.execute(request);
                String code = alipayTradeQueryResponse.getCode();
                // 判断状态码
                if ("10000".equals(code)) {
                    orderService.changeOrderPaySuccess(orderId);
                }
            }
        }catch (Exception e){
            log.error("检测未接受到或未正确处理的支付回调通知失败",e);
        }


    }
}
