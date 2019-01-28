package com.yeta.pps.util;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.yeta.pps.exception.CommonException;
import com.yeta.pps.mapper.MyMarketingMapper;
import com.yeta.pps.po.SMSHistory;
import com.yeta.pps.po.SMSTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author YETA
 * @date 2019/01/28/17:34
 */
@Component
public class SMSUtil {

    @Autowired
    private MyMarketingMapper myMarketingMapper;

    /**
     * 发送短信的方法
     * @param smsHistories
     * @throws ClientException
     */
    @Transactional
    public void sendSMSMethod(List<SMSHistory> smsHistories) throws ClientException {
        //查询所有短信模版
        List<SMSTemplate> smsTemplates = myMarketingMapper.findAllSMSTemplate(new SMSTemplate());

        //设置超时时间-可自行调整
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化ascClient需要的几个参数
        final String product = "Dysmsapi";      //短信API产品名称（短信产品名固定，无需修改）
        final String domain = "dysmsapi.aliyuncs.com";      //短信API产品域名（接口地址固定，无需修改）

        //替换成你的AK
        final String accessKeyId = "LTAImTG7N8qiSS6q";      //你的accessKeyId,参考本文档步骤2
        final String accessKeySecret = "L5PixOObvJzxKPRhMcXSchfTFZ6yXA";        //你的accessKeySecret，参考本文档步骤2
        final String signName = "长沙应吾源头贸易有限公司";

        //初始化ascClient,暂时不支持多region（请勿修改）
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        SendSmsRequest request = new SendSmsRequest();

        //使用post提交
        request.setMethod(MethodType.POST);

        //发短信
        smsHistories.stream().forEach(smsHistory -> {
            //必填:待发送手机号。支持JSON格式的批量调用，批量上限为100个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
            request.setPhoneNumbers(smsHistory.getClientPhone());

            //必填:短信签名-支持不同的号码发送不同的短信签名
            request.setSignName(signName);

            //必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsHistory.getTemplateCode());

            //必填:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
            TemplateParam templateParam = new TemplateParam();
            //过滤短信内容
            Optional<SMSTemplate> smsTemplateOptional = smsTemplates.stream().filter(smsTemplate -> smsTemplate.getId().equals(smsHistory.getTemplateCode())).findFirst();
            if (!smsTemplateOptional.isPresent()) {
                throw new CommonException(CommonResponse.PARAMETER_ERROR, "短信模版编号错误");
            }
            if (smsHistory.getClientName() != null) {
                templateParam = new TemplateParam(smsHistory.getClientName());
                smsHistory.setContent(smsTemplateOptional.get().getContent().replace("${name}", smsHistory.getClientName()));
            } else if (smsHistory.getCode() != null) {
                templateParam = new TemplateParam(smsHistory.getCode());
                smsHistory.setContent(smsTemplateOptional.get().getContent().replace("${code}", smsHistory.getCode().toString()));
            }
            request.setTemplateParam(JSON.toJSON(templateParam).toString());

            //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
            //request.setSmsUpExtendCodeJson("[\"90997\",\"90998\"]");

            //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
            //request.setOutId("yourOutId");

            //请求失败这里会抛ClientException异常
            try {
                SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
                if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {       //请求成功
                    smsHistory.setStatus((byte) 1);
                    myMarketingMapper.addSMSHistory(smsHistory);
                } else {        //请求失败
                    smsHistory.setStatus((byte) 0);
                    smsHistory.setRemark(sendSmsResponse.getMessage());
                    myMarketingMapper.addSMSHistory(smsHistory);
                }
            } catch (ClientException e) {
                e.printStackTrace();
            }
        });
    }
}
