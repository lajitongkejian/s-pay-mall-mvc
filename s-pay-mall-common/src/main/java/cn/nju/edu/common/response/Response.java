package cn.nju.edu.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：Response
 * 作者：tkj
 * 日期：2024/10/25
 * 用于Controller返回值，所以一般使用泛型，为了序列化所以要实现Serializable
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> implements Serializable {
    private static final long serialVersionUID = -2944994270195124973L;
    private T data;
    private String code;
    private String info;
}
