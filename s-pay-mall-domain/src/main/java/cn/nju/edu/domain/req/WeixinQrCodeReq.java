package cn.nju.edu.domain.req;

import lombok.*;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：WeixinQrCodeReq
 * 作者：tkj
 * 日期：2024/10/25
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeixinQrCodeReq {
    private String action_name;
    private int expire_seconds;
    private ActionInfo action_info;


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ActionInfo{

        private Scene scene;


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class Scene{
            private int scene_id;
        }
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ActionNameTypeVO{
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private String code;
        private String info;
    }
}
