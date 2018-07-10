package com.kylin.activity.util

import com.xiaoleilu.hutool.date.BetweenFormater
import com.xiaoleilu.hutool.date.DateTime
import com.xiaoleilu.hutool.date.DateUtil
import org.springframework.security.web.savedrequest.HttpSessionRequestCache
import org.springframework.stereotype.Service
import java.lang.Double
import java.math.BigDecimal
import java.sql.Timestamp
import java.util.*
import java.util.regex.Pattern
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Service
class KylinUtil {

    fun fromNow(date: Any): String? {
        var d: DateTime = if (date is Date) {
            DateUtil.date(date)
        } else if (date is Long) {
            DateUtil.date(date)
        } else if (date is Calendar) {
            DateUtil.date(date)
        } else if (date is Timestamp) {
            DateUtil.date(date.time)
        } else {
            DateUtil.parseDate(date.toString())
        }
        var result = DateUtil.formatBetween(
                d,
                DateUtil.date(), BetweenFormater.Level.DAY)
        if(result.isNullOrBlank()){
            result = DateUtil.formatBetween(
                    d,
                    DateUtil.date(), BetweenFormater.Level.HOUR)

            if(result.isNullOrBlank()){
                result = DateUtil.formatBetween(
                        d,
                        DateUtil.date(), BetweenFormater.Level.MINUTE)

                if(result.isNullOrBlank()){
                    result = "刚刚"
                }
            }

        }
        if(result != "刚刚") {
            result += if (d.time < DateUtil.date().time) {
                "前"
            } else {
                "后"
            }
        }
        return result
    }

    fun redirectAfterLogin(request: HttpServletRequest, response: HttpServletResponse):String{
        val savedRequest = HttpSessionRequestCache().getRequest(request, response)
        return if (savedRequest != null) {
            savedRequest.redirectUrl
        } else {
            "/"
        }
    }

    fun getClientIpAddress(request: HttpServletRequest): String {
        val ipHeaderCandidates = arrayOf("X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP", "HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR")
        ipHeaderCandidates
                .map { request.getHeader(it) }
                .filter { it != null && it.isNotEmpty() && !"unknown".equals(it, ignoreCase = true) }
                .forEach { return it }
        return request.remoteAddr
    }

    fun getBaseUrl(request: HttpServletRequest): String {
        return if (request.serverPort == 80 || request.serverPort == 443)
            request.scheme + "://" +
                    request.serverName
        else
            request.scheme + "://" +
                    request.serverName + ":" + request.serverPort
    }

    fun feeToYuan(fee: Int?): String {
        return BigDecimal(Double.valueOf(fee!!.toDouble()) / 100).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
    }

    /**
     * 验证手机号码有效性
     *  中国电信号段 133、149、153、173、177、180、181、189、199
    中国联通号段 130、131、132、145、155、156、166、175、176、185、186
    中国移动号段 134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
    其他号段
    14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。
    虚拟运营商
    电信：1700、1701、1702
    移动：1703、1705、1706
    联通：1704、1707、1708、1709、171
    卫星通信：1349
     */
    fun isPhone(phone: String): Boolean {
        val regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$"
        return if (phone.length != 11) {
            false
        } else {
            val p = Pattern.compile(regex)
            val m = p.matcher(phone)
            val isMatch = m.matches()
            isMatch
        }
    }
}
