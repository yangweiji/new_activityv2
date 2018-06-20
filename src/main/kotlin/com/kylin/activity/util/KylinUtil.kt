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
}
