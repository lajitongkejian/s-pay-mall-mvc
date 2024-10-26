package cn.nju.edu.service.impl;

import cn.nju.edu.domain.vo.WeixinTemplateMessageVO;
import cn.nju.edu.domain.req.WeixinQrCodeReq;
import cn.nju.edu.domain.res.WeixinQrCodeRes;
import cn.nju.edu.domain.res.WeixinTokenRes;
import cn.nju.edu.service.ILogInService;
import cn.nju.edu.service.weixin.IWeixinApiService;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：s-pay-mall-mvc
 * 类名称：WeixinLogInServiceImpl
 * 作者：tkj
 * 日期：2024/10/25
 */
@Slf4j
@Service
public class WeixinLogInServiceImpl implements ILogInService {

    @Value("${weixin.config.app-id}")
    private String appid;

    @Value("${weixin.config.app-secret}")
    private String appSecret;

    @Value("${weixin.config.template-id}")
    private String templateId;

    @Resource
    private Cache<String,String> weixinAccessTokenCache;

    @Resource
    private Cache<String,String> openidTokenCache;

    @Resource
    private IWeixinApiService iWeixinApiService;


    @Override
    public String createQrCodeTicket() throws Exception {
        String token = weixinAccessTokenCache.getIfPresent(appid);
        if(null == token){
            Call<WeixinTokenRes> tokenCall = iWeixinApiService.getToken("client_credential",appid,appSecret);
            WeixinTokenRes tokenRes = tokenCall.execute().body();
            assert tokenRes!=null;
            token = tokenRes.getAccess_token();
            weixinAccessTokenCache.put(appid,token);
        }
        WeixinQrCodeReq req = WeixinQrCodeReq.builder()
                .expire_seconds(2592000)
                .action_name(WeixinQrCodeReq.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrCodeReq.ActionInfo.builder()
                        .scene(new WeixinQrCodeReq.ActionInfo.Scene(100601)).build()
                        )
                .build();
        Call<WeixinQrCodeRes> qrCodeResCall =  iWeixinApiService.getQrCode(token,req);
        WeixinQrCodeRes qrCodeRes = qrCodeResCall.execute().body();
        assert qrCodeRes!=null;
        return qrCodeRes.getTicket();

    }

    @Override
    public String checkLogIn(String ticket) {
        return openidTokenCache.getIfPresent(ticket);
    }

    @Override
    public void saveLogInState(String ticket, String openid) throws IOException {
        openidTokenCache.put(ticket, openid);
        //获取access_token
        String token = weixinAccessTokenCache.getIfPresent(appid);
        if(null == token){
            Call<WeixinTokenRes> tokenCall = iWeixinApiService.getToken("client_credential",appid,appSecret);
            WeixinTokenRes tokenRes = tokenCall.execute().body();
            assert tokenRes!=null;
            token = tokenRes.getAccess_token();
            weixinAccessTokenCache.put(appid,token);
        }
        //发送模板消息
        WeixinTemplateMessageVO vo = new WeixinTemplateMessageVO(openid,templateId);
        Map<String, Map<String,String>> data = new HashMap<>();
        //这个方法只是把某个键值放到data中
        WeixinTemplateMessageVO.put(data,WeixinTemplateMessageVO.TemplateKey.USER,openid);
        //把data装配到vo里
        vo.setData(data);
        Call<Void> templateMessageVOCall = iWeixinApiService.sendTemplateMessage(token,vo);
        templateMessageVOCall.execute();

    }
}
