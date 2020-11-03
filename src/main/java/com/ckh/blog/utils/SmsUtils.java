package com.ckh.blog.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class SmsUtils {

    private static final Logger log = LoggerFactory.getLogger(SmsUtils.class);

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    // TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
    static final String accessKeyId = "LTAI4GCNj4dwo1AFXsUBZGiF";
    static final String accessKeySecret = "KRCVAcLZQWllOlZofzuL5HtASNk2kL";

    // 发送短信验证码
    public static boolean sendSms(String phoneNumber, String code) throws ClientException {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phoneNumber);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("ckhBlog");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_204455967");
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":" + code + "}");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
        System.out.println("短信接口返回的数据----------------");
        System.out.println("Code=" + sendSmsResponse.getCode());
        System.out.println("Message=" + sendSmsResponse.getMessage());
        System.out.println("RequestId=" + sendSmsResponse.getRequestId());
        System.out.println("BizId=" + sendSmsResponse.getBizId());
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
           log.info("短信发送成功");
           return true;
        } else {
            log.error("短信发送失败");
            return false;
        }
    }
}
