package cn.nju.edu.domain.res;

import lombok.Data;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：WeixinQrCodeRes
 * 作者：tkj
 * 日期：2024/10/25
 */
@Data
public class WeixinQrCodeRes {
    private String ticket;
    private int expire_seconds;
    private String url;
}
