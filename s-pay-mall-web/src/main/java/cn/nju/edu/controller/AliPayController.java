package cn.nju.edu.controller;

import cn.nju.edu.common.constants.Constants;
import cn.nju.edu.common.response.Response;
import cn.nju.edu.controller.dto.CreatePayRequestDTO;
import cn.nju.edu.domain.req.ShopCartReq;
import cn.nju.edu.domain.res.PayOrderRes;
import cn.nju.edu.service.IOrderService;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：AliPayController
 * 作者：tkj
 * 日期：2024/10/26
 */

@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/alipay/")
public class AliPayController {

    @Resource
    private IOrderService orderService;

    @Value("${alipay.alipay_public_key}")
    private String alipayPublicKey;

    @RequestMapping(value = "create_pay_order",method = RequestMethod.POST)
    public Response<String> createPayOrder(@RequestBody CreatePayRequestDTO createPayRequestDTO){
        try{
            log.info("商品下单，创建支付单开始 userId:{},productId:{}",createPayRequestDTO.getUserId(),createPayRequestDTO.getProductId());
            String userId = createPayRequestDTO.getUserId();
            String productId = createPayRequestDTO.getProductId();
            PayOrderRes orderRes = orderService.createOrder(ShopCartReq.builder()
                    .productId(productId)
                    .userId(userId)
                    .build());
            log.info("商品下单，创建支付单完成 userId:{},productId:{},orderId:{}",createPayRequestDTO.getUserId(),createPayRequestDTO.getProductId(),orderRes.getOrderId());

            return Response.<String>builder()
                    .data(orderRes.getPayUrl())
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .build();

        }catch (Exception e){
            log.error("商品下单，创建支付单失败 userId:{},productId:{}",createPayRequestDTO.getUserId(),createPayRequestDTO.getProductId());

            return Response.<String>builder()
                    .data(null)
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .build();

        }
    }

    @RequestMapping(value = "alipay_notify_url", method = RequestMethod.POST)
    public String payNotify(HttpServletRequest request) throws AlipayApiException {
        log.info("支付回调，消息接收 {}", request.getParameter("trade_status"));

        if (!request.getParameter("trade_status").equals("TRADE_SUCCESS")) {
            return "false";
        }

        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (String name : requestParams.keySet()) {
            params.put(name, request.getParameter(name));
        }

        String tradeNo = params.get("out_trade_no");
        String gmtPayment = params.get("gmt_payment");
        String alipayTradeNo = params.get("trade_no");

        String sign = params.get("sign");
        String content = AlipaySignature.getSignCheckContentV1(params);
        boolean checkSignature = AlipaySignature.rsa256CheckContent(content, sign, alipayPublicKey, "UTF-8"); // 验证签名
        // 支付宝验签
        if (!checkSignature) {
            return "false";
        }

        // 验签通过
        log.info("支付回调，交易名称: {}", params.get("subject"));
        log.info("支付回调，交易状态: {}", params.get("trade_status"));
        log.info("支付回调，支付宝交易凭证号: {}", params.get("trade_no"));
        log.info("支付回调，商户订单号: {}", params.get("out_trade_no"));
        log.info("支付回调，交易金额: {}", params.get("total_amount"));
        log.info("支付回调，买家在支付宝唯一id: {}", params.get("buyer_id"));
        log.info("支付回调，买家付款时间: {}", params.get("gmt_payment"));
        log.info("支付回调，买家付款金额: {}", params.get("buyer_pay_amount"));
        log.info("支付回调，支付回调，更新订单 {}", tradeNo);

        return "success";
    }



}
