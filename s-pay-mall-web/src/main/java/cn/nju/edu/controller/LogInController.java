package cn.nju.edu.controller;

import cn.nju.edu.common.constants.Constants;
import cn.nju.edu.common.response.Response;
import cn.nju.edu.service.ILogInService;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：LogInController
 * 作者：tkj
 * 日期：2024/10/25
 */

@Slf4j
@RestController()
@CrossOrigin("*")
@RequestMapping("/api/v1/login/")
public class LogInController {
    @Resource
    private ILogInService iLogInService;

    /**
     *
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "weixin_qrcode_ticket",method = RequestMethod.GET)
    public Response<String> weixinQrCodeTicket() throws Exception {

        try {
            String ticket = iLogInService.createQrCodeTicket();
            log.info("生成微信扫码登录二维码， ticket：{}",ticket);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.SUCCESS.getCode())
                    .info(Constants.ResponseCode.SUCCESS.getInfo())
                    .data(ticket)
                    .build();
        }catch (Exception e){
            log.info("生成微信扫码登录二维码失败",e);
            return Response.<String>builder()
                    .code(Constants.ResponseCode.UN_ERROR.getCode())
                    .info(Constants.ResponseCode.UN_ERROR.getInfo())
                    .data(null)
                    .build();
        }

    }

    /**
     *
     * @param ticket
     * @return
     */
    @RequestMapping(value = "check_login",method = RequestMethod.GET)
    public Response<String> checkLogIn(@RequestParam String ticket){

        try{
            String openid = iLogInService.checkLogIn(ticket);
            if(StringUtils.isNotBlank(openid)){
                log.info("扫码登录检测成功，openid：{}，ticket：{}",openid,ticket);
                return Response.<String>builder()
                        .code(Constants.ResponseCode.SUCCESS.getCode())
                        .info(Constants.ResponseCode.SUCCESS.getInfo())
                        .data(openid)
                        .build();
            }else{
                log.info("尚未进行扫码登录，openid：{}，ticket：{}",openid,ticket);
                return Response.<String>builder()
                        .code(Constants.ResponseCode.NO_LOGIN.getCode())
                        .info(Constants.ResponseCode.NO_LOGIN.getInfo())
                        .data(null)
                        .build();
            }
        }catch (Exception e){
                log.error("扫码登录失败，ticket：{}",ticket);
                return Response.<String>builder()
                        .code(Constants.ResponseCode.UN_ERROR.getCode())
                        .info(Constants.ResponseCode.UN_ERROR.getInfo())
                        .data(null)
                        .build();
        }

    }
}
