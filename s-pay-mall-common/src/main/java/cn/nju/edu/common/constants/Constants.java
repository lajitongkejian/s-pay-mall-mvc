package cn.nju.edu.common.constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：Constants
 * 作者：tkj
 * 日期：2024/10/25
 */


public class Constants {

    public final static String SPLIT = ",";

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum ResponseCode {
        SUCCESS("0000", "调用成功"),
        UN_ERROR("0001", "调用失败"),
        ILLEGAL_PARAMETER("0002", "非法参数"),
        NO_LOGIN("0003", "未登录"),
        ;

        private String code;
        private String info;

    }

    @Getter
    @AllArgsConstructor
    public enum OrderStatusEnum {

        CREATE("CREATE", "创建完成 - 如果调单了，也会从创建记录重新发起创建支付单"),
        PAY_WAIT("PAY_WAIT", "等待支付 - 订单创建完成后，创建支付单"),
        PAY_SUCCESS("PAY_SUCCESS", "支付成功 - 接收到支付回调消息"),
        DEAL_DONE("DEAL_DONE", "交易完成 - 商品发货完成"),
        CLOSE("CLOSE", "超时关单 - 超市未支付"),
        ;

        private final String code;
        private final String desc;

    }

}
