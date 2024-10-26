package cn.nju.edu.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：ProductVO
 * 作者：tkj
 * 日期：2024/10/26
 */
@Data
public class ProductVO {
    private String productId;
    private String productName;
    private String productDesc;
    private BigDecimal price;
}
