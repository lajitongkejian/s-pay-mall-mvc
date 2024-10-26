package cn.nju.edu.service.weixin;

import cn.nju.edu.domain.vo.WeixinTemplateMessageVO;
import cn.nju.edu.domain.req.WeixinQrCodeReq;
import cn.nju.edu.domain.res.WeixinQrCodeRes;
import cn.nju.edu.domain.res.WeixinTokenRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 *这个类负责提供：返回accesstoken、根据accesstoken返回QRcode、发送模板消息
 */
public interface IWeixinApiService {
    /**
     *
     * @param grantType 获取access_token填写client_credential
     * @param appid 第三方用户唯一凭证
     * @param secret 第三方用户唯一密钥
     * @return
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenRes> getToken(@Query("grant_type") String grantType,
                                  @Query("appid") String appid,
                                  @Query("secret") String secret);

    /**
     *
     * @param accessToken getToken返回的token
     * @param weixinQrCodeReq 入参对象
     * @return 拿到ticket（相当于二维码）
     */

    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeRes> getQrCode(@Query("access_token") String accessToken,
                                    @Body WeixinQrCodeReq weixinQrCodeReq);

    /**
     *
     * @param accessToken
     * @param weixinTemplateMessageVo
     * @return
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendTemplateMessage(@Query("access_token") String accessToken,
                                   @Body WeixinTemplateMessageVO weixinTemplateMessageVo);
}
