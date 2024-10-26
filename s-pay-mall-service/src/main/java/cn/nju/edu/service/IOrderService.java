package cn.nju.edu.service;

import cn.nju.edu.domain.req.ShopCartReq;
import cn.nju.edu.domain.res.PayOrderRes;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：IPayOrderService
 * 作者：tkj
 * 日期：2024/10/26
 */
public interface IOrderService {

    PayOrderRes createOrder(ShopCartReq shopCartReq);
}
