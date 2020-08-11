package com.lghcode.briefbook.util;

import com.lghcode.briefbook.config.TencentSmsProperties;
import com.lghcode.briefbook.constant.TencentSmsConstant;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import com.tencentcloudapi.sms.v20190711.models.SendStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lgh
 * @Date 2020/8/10 22:59
 */
@Service
@Slf4j
public class TencentSmsUtil {

    @Autowired
    private TencentSmsProperties tencentSmsProperties;

    /**
     * 发送短信验证码
     *
     * @Author lghcode
     * @param  code 随机6位字符串
     * @param  mobile 手机号
     * @param  tempId 短信模板id
     * @return boolean
     * @Date 2020/8/10 23:16
     */
    private boolean sendSms(String code, String mobile, String tempId) {
        try {
            Credential cred = new Credential(tencentSmsProperties.getSecretId(), tencentSmsProperties.getSecretKey());

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setReqMethod("POST");
            httpProfile.setConnTimeout(60);
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setSignMethod("HmacSHA256");
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            SendSmsRequest req = new SendSmsRequest();

            /* 短信应用 ID: 在 [短信控制台] 添加应用后生成的实际 SDKAppID，例如1400006666 */
            String appid = tencentSmsProperties.getSdkAppId();
            req.setSmsSdkAppid(appid);

            /* 短信签名内容: 使用 UTF-8 编码，必须填写已审核通过的签名，可登录 [短信控制台] 查看签名信息 */
            String sign = tencentSmsProperties.getSign();
            req.setSign(sign);

            /* 模板 ID: 必须填写已审核通过的模板 ID，可登录 [短信控制台] 查看模板 ID */
            req.setTemplateID(tempId);

            /* 下发手机号码，采用 e.164 标准，+[国家或地区码][手机号]
             * 例如+8613711112222， 其中前面有一个+号 ，86为国家码，13711112222为手机号，最多不要超过200个手机号*/
            String[] phoneNumbers = {"+86" + mobile};
            req.setPhoneNumberSet(phoneNumbers);

            /* 模板参数: 若无模板参数，则设置为空*/
            String[] templateParams = {code, TencentSmsConstant.VALID_PERIOD};
            req.setTemplateParamSet(templateParams);

            /* 通过 client 对象调用 SendSms 方法发起请求。注意请求方法名与请求对象是对应的
             * 返回的 res 是一个 SendSmsResponse 类的实例，与请求对象对应 */
            log.info("==========>开始给{}发送短信验证码：{}", mobile, code);
            SendSmsResponse res = client.SendSms(req);
            log.info("==========>短信发送完毕,{}", SendSmsResponse.toJsonString(res));
            // 可以取出单个值，您可以通过官网接口文档或跳转到 response 对象的定义处查看返回字段的定义
            SendStatus sendStatus = res.getSendStatusSet()[0];
            String c = sendStatus.getCode();
            if (!"Ok".equals(c)) {
                log.info("=======>给{}发送短信验证码{}失败！", mobile, code);
                return false;
            }
            log.info("=======>给{}发送短信验证码{}成功！", mobile, code);
            return true;
        } catch (TencentCloudSDKException e) {
            e.printStackTrace();
            log.info("=======>给{}发送短信验证码{}失败！", mobile, code);
            return false;
        }
    }

    /**
     * 发送登录短信验证码
     *
     * @Author lghcode
     * @param  code 随机6位字符串
     * @param  mobile 手机号
     * @return boolean
     * @Date 2020/8/11 11:42
     */
    public boolean sendLoginSms(String code,String mobile) {
        return sendSms(code,mobile,tencentSmsProperties.getLoginTemplateId());
    }

    /**
     * 发送重置密码短信验证码
     *
     * @Author lghcode
     * @param  code 随机6位字符串
     * @param  mobile 手机号
     * @return boolean
     * @Date 2020/8/11 11:42
     */
    public boolean sendUpPwdSms(String code,String mobile) {
        return sendSms(code,mobile,tencentSmsProperties.getUpPwdTemplateId());
    }
}
