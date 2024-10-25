package cn.nju.edu.service;

import java.io.IOException;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：ILogInService
 * 作者：tkj
 * 日期：2024/10/25
 */
public interface ILogInService {

    String createQrCodeTicket() throws Exception;

    String checkLogIn(String ticket);

    void saveLogInState(String ticket,String openid) throws IOException;

}
