package cn.nju.edu.domain.res;

import cn.nju.edu.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：PayOrderRes
 * 作者：tkj
 * 日期：2024/10/26
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRes {
    private String orderId;
    private String userId;
    private String payUrl;
    private Constants.OrderStatusEnum orderStatusEnum;
}
