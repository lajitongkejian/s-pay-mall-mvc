package cn.nju.edu.test.service;

import cn.nju.edu.domain.req.ShopCartReq;
import cn.nju.edu.domain.res.PayOrderRes;
import cn.nju.edu.service.IOrderService;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;


/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：OrderServiceTest
 * 作者：tkj
 * 日期：2024/10/26
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Resource
    private IOrderService orderService;

    @Test
    public void test_createOrder(){
        ShopCartReq shopCartReq = new ShopCartReq();
        shopCartReq.setUserId("xiaofuge");
        shopCartReq.setProductId("10001");
        PayOrderRes payOrderRes = orderService.createOrder(shopCartReq);
        log.info("请求参数:{}", JSON.toJSONString(shopCartReq));
        log.info("测试结果:{}", JSON.toJSONString(payOrderRes));
    }

}

