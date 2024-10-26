package cn.nju.edu.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：CreatePayRequestDTO
 * 作者：tkj
 * 日期：2024/10/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePayRequestDTO {

    private String userId;
    private String productId;
}
