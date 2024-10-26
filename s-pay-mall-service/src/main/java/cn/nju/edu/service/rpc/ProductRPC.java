package cn.nju.edu.service.rpc;

import cn.nju.edu.domain.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：ProductRPC
 * 作者：tkj
 * 日期：2024/10/26
 */
@Service
public class ProductRPC {

//    假的，假装生成一个Product
    public ProductVO queryProductByProductId(String productId){
        ProductVO productVO = new ProductVO();
        productVO.setProductId(productId);
        productVO.setProductName("测试商品");
        productVO.setProductDesc("这是一个测试商品");
        productVO.setPrice(new BigDecimal("1.68"));
        return productVO;
    }
}
