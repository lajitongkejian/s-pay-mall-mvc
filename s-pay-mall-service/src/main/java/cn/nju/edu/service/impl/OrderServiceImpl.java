package cn.nju.edu.service.impl;

import cn.nju.edu.common.constants.Constants;
import cn.nju.edu.dao.IOrderDao;
import cn.nju.edu.domain.po.PayOrder;
import cn.nju.edu.domain.req.ShopCartReq;
import cn.nju.edu.domain.res.PayOrderRes;
import cn.nju.edu.domain.vo.ProductVO;
import cn.nju.edu.service.IOrderService;
import cn.nju.edu.service.rpc.ProductRPC;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：OrderServiceImpl
 * 作者：tkj
 * 日期：2024/10/26
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {

    @Resource
    private IOrderDao orderDao;

    @Resource
    private ProductRPC productRPC;

    @Resource
    private AlipayClient alipayClient;

    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;

    @Override
    public PayOrderRes createOrder(ShopCartReq shopCartReq) throws AlipayApiException{
        //1.查询用户是否存在未支付订单或掉单
        PayOrder payOrder = PayOrder.builder()
                .userId(shopCartReq.getUserId())
                .productId(shopCartReq.getProductId())
                .build();

        PayOrder unpaidOrder = orderDao.queryUnPayOrder(payOrder);
        if(null!= unpaidOrder && Constants.OrderStatusEnum.PAY_WAIT.getCode().equals(unpaidOrder.getStatus())){
            //该用户已存在未支付订单
            log.info("创建订单-存在，已存在未支付订单。userId:{} productId:{} orderId:{}", shopCartReq.getUserId(), shopCartReq.getProductId(), unpaidOrder.getOrderId());
            return PayOrderRes.builder()
                    .orderId(unpaidOrder.getOrderId())
                    .payUrl(unpaidOrder.getPayUrl())
                    .build();
        }else if(null!= unpaidOrder && Constants.OrderStatusEnum.CREATE.getCode().equals(unpaidOrder.getStatus())){
            //undo
            //该用户已存在只是创建状态的订单
            PayOrder payOrder1 = doPrePayOrder(unpaidOrder.getProductId(),
                    unpaidOrder.getProductName(),
                    unpaidOrder.getOrderId(),
                    unpaidOrder.getTotalAmount());
            orderDao.updateOrderPayInfo(payOrder1);
            return PayOrderRes.builder()
                    .orderId(payOrder1.getOrderId())
                    .payUrl(payOrder1.getPayUrl())
                    .build();
        }
        //2.用户不存在未支付订单，正常查询商品，创建订单
        ProductVO productVO = productRPC.queryProductByProductId(shopCartReq.getProductId());
        String orderId = RandomStringUtils.randomNumeric(16);
        orderDao.insert(PayOrder.builder()
                        .userId(shopCartReq.getUserId())
                        .productId(productVO.getProductId())
                        .productName(productVO.getProductName())
                        .orderId(orderId)
                        .orderTime(new Date())
                        .totalAmount(productVO.getPrice())
                        .status(Constants.OrderStatusEnum.CREATE.getCode())
                .build());
        //3.创建支付订单，也就是搞个url出来
        PayOrder payOrder2 = doPrePayOrder(productVO.getProductId(),
                productVO.getProductName(),
                orderId,productVO.getPrice());

        return PayOrderRes.builder()
                .payUrl(payOrder2.getPayUrl())
                .orderId(orderId)
                .build();


    }

    private PayOrder doPrePayOrder(String productId, String productName,String orderId, BigDecimal totalAmount) throws AlipayApiException {
        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderId(orderId);
        payOrder.setPayUrl(form);
        payOrder.setStatus(Constants.OrderStatusEnum.PAY_WAIT.getCode());
        //向数据库写入支付url以及待支付状态
        orderDao.updateOrderPayInfo(payOrder);

        return payOrder;

    }


}
