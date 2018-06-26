package com.kylin.activity.util

import com.aliyun.oss.OSSClient
import com.aliyun.oss.common.utils.BinaryUtil
import com.aliyun.oss.model.MatchMode
import com.aliyun.oss.model.PolicyConditions
import com.aliyuncs.DefaultAcsClient
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse
import com.aliyuncs.exceptions.ClientException
import com.aliyuncs.profile.DefaultProfile
import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import java.util.*


@Service
class CommonService {
    //产品名称:云通信短信API产品,开发者无需替换
    val product = "Dysmsapi"
    //产品域名,开发者无需替换
    val domain = "dysmsapi.aliyuncs.com"

    @Autowired
    private val env: Environment? = null

    val accessKeyId: String get() = env!!.getProperty("aliyun.access-key-id")
    val accessKeySecret: String get() = env!!.getProperty("aliyun.access-key-secret")
    val endpoint: String get() = env!!.getProperty("aliyun.oss.endpoint")
    val bucket: String get() = env!!.getProperty("aliyun.oss.bucket")
    val dir: String get() = env!!.getProperty("aliyun.oss.dir")
    val smsTemplate: String get() = env!!.getProperty("aliyun.sms.template")
    val smsSign: String get() = env!!.getProperty("aliyun.sms.sign")

    /**
     * 活动签名
     */
    val activitySmsSign: String get() = env!!.getProperty("sms.sign")

    /**
     * 发送模板消息短息：接收短信码
     * @param mobile: 手机号
     * @param code: 短信验证
     */
    @Throws(ClientException::class)
    fun sendSms(mobile:String, code:String): SendSmsResponse {

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000")
        System.setProperty("sun.net.client.defaultReadTimeout", "10000")

        //初始化acsClient,暂不支持region化
        val profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret)
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain)
        val acsClient = DefaultAcsClient(profile)

        //组装请求对象-具体描述见控制台-文档部分内容
        val request = SendSmsRequest()
        //必填:待发送手机号
        request.setPhoneNumbers(mobile)
        //必填:短信签名-可在短信控制台中找到
        request.setSignName(smsSign)
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(smsTemplate)
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}")

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId")

        //hint 此处可能会抛出异常，注意catch

        return acsClient.getAcsResponse<SendSmsResponse>(request)
    }

    /**
     * 批量发送模板消息短信
     * @param mobiles: 接收手机号，多个号码使用,分隔
     * @param smsSign: 短信签名
     * @param templateCode: 短信模板代码
     * @param templateParam: 模板变量替换Json参数,友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
     */
    @Throws(ClientException::class)
    fun sendBatchSms(mobiles: String, smsSign: String, templateCode: String, templateParam: String): SendSmsResponse {
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000")
        System.setProperty("sun.net.client.defaultReadTimeout", "10000")

        //初始化acsClient,暂不支持region化
        val profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret)
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain)
        val acsClient = DefaultAcsClient(profile)

        //组装请求对象-具体描述见控制台-文档部分内容
        val request = SendSmsRequest()
        ////必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式；发送国际/港澳台消息时，接收号码格式为00+国际区号+号码，如“0085200000000”
        request.phoneNumbers = mobiles
        //必填:短信签名-可在短信控制台中找到
        request.signName = smsSign
        //必填:短信模板-可在短信控制台中找到
        request.templateCode = templateCode
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        request.templateParam = templateParam

        return acsClient.getAcsResponse<SendSmsResponse>(request)
    }


    fun getPolicy(): LinkedHashMap<String, String> {
        val host = "http://$bucket.$endpoint"
        val client = OSSClient(endpoint, accessKeyId, accessKeySecret)
        val expireTime: Long = 30
        val expireEndTime = System.currentTimeMillis() + expireTime * 1000
        val expiration = Date(expireEndTime)
        val policyConds = PolicyConditions()
        policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000)
        policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir)

        val postPolicy = client.generatePostPolicy(expiration, policyConds)
        val binaryData = postPolicy.toByteArray(charset("utf-8"))
        val encodedPolicy = BinaryUtil.toBase64String(binaryData)
        val postSignature = client.calculatePostSignature(postPolicy)

        val respMap = LinkedHashMap<String, String>()
        respMap.put("accessid", accessKeyId)
        respMap.put("policy", encodedPolicy)
        respMap.put("signature", postSignature)
        respMap.put("dir", dir)
        respMap.put("host", host)
        respMap.put("expire", (expireEndTime / 1000).toString())
        return respMap
    }

    /**
     * 取得图片链接
     * @param fileId: 文件名
     * @param style: 样式
     * @param subDir: 子目录
     */
    fun getDownloadUrl(fileId:String, style:String? = null, subDir:String? = null):String {
        if(fileId.startsWith("http://", true) || fileId.startsWith("https://", true)){
            return fileId
        }
        var subDir = if(subDir == null) dir else subDir
        var url = "http://${bucket}.${endpoint}/${subDir}/${fileId}"
        if(style != null && !style.isNullOrBlank()){
            url += "?x-oss-process=style/" + style
        }
        return url
    }
}