package cn.nju.edu.service.impl;

import cn.nju.edu.common.constants.Constants;
import cn.nju.edu.dao.IOrderDao;
import cn.nju.edu.domain.po.PayOrder;
import cn.nju.edu.domain.req.ShopCartReq;
import cn.nju.edu.domain.res.PayOrderRes;
import cn.nju.edu.domain.vo.ProductVO;
import cn.nju.edu.service.IOrderService;
import cn.nju.edu.service.rpc.ProductRPC;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Override
    public PayOrderRes createOrder(ShopCartReq shopCartReq) {
        //1.查询用户是否存在未支付订单或掉单
        PayOrder payOrder = PayOrder.builder()
                .userId(shopCartReq.getUserId())
                .productId(shopCartReq.getProductId())
                .build();

        PayOrder unpaidOrder = orderDao.queryUnPayOrder(payOrder);
        if(null!= unpaidOrder && Constants.OrderStatusEnum.PAY_WAIT.getCode().equals(unpaidOrder.getStatus())){
            log.info("创建订单-存在，已存在未支付订单。userId:{} productId:{} orderId:{}", shopCartReq.getUserId(), shopCartReq.getProductId(), unpaidOrder.getOrderId());
            return PayOrderRes.builder()
                    .orderId(unpaidOrder.getOrderId())
                    .payUrl(unpaidOrder.getPayUrl())
                    .build();
        }else if(null!= unpaidOrder && Constants.OrderStatusEnum.CREATE.getCode().equals(unpaidOrder.getStatus())){
            //undo
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
        return PayOrderRes.builder()
                .payUrl("暂无")
                .orderId(orderId)
                .build();


    }
}
