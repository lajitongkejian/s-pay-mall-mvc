package cn.nju.edu.domain.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：ShopCartReq
 * 作者：tkj
 * 日期：2024/10/26
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopCartReq {
    private String userId;
    private String productId;

}
