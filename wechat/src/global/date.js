function anyToDate(d) {
    if (!d) {
        return null
    }
    if (d instanceof Date) {
        return d;
    }
    if (/^\d+$/.test(d)) {
        return new Date(parseInt(d));

    }
    if (d.constructor == Array && d.length == 3) {
        return new Date(d[0], d[1] - 1, d[2])
    }
    if (d.constructor == String) {
        return new Date((d || '').trim().replace(/\.\d+/, '')
            .replace(/-/, '/').replace(/-/, '/')
            .replace(/(\d)T(\d)/, '$1 $2').replace(/Z/, ' UTC')
            .replace(/([\+\-]\d\d)\:?(\d\d)/, ' $1$2'));
    }
}

function dateFormat(date, fmt) {
    date = anyToDate(date)
    var o = {
        "M+": date.getMonth() + 1,
        "d+": date.getDate(),
        "H+": date.getHours(),
        "m+": date.getMinutes(),
        "s+": date.getSeconds(),
        "q+": Math.floor((date.getMonth() + 3) / 3),
        "S": date.getMilliseconds()
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

function timeago(time) {
    var agos = [{
            maxTime: 0,
            text: '未来',
            unit: 0
        },
        {
            maxTime: 30,
            /**一分钟**/
            text: '刚刚',
            unit: 1
        },
        {
            maxTime: 60,
            /**一分钟**/
            text: 'tt 秒前',
            unit: 1
        },
        {
            maxTime: 3600,
            /**一小时**/
            text: 'tt 分钟前',
            unit: 60
        },
        {
            maxTime: 86400,
            /**一天**/
            text: 'tt 小时前',
            unit: 3600
        },
        {
            maxTime: 604800,
            /**一周**/
            text: 'tt 天前',
            unit: 86400
        },
        {
            maxTime: 2678400,
            /**一月**/
            text: 'tt 周前',
            unit: 604800
        },
        {
            maxTime: 31536000,
            /**一年**/
            text: 'tt 月前',
            unit: 2678400
        },
        {
            maxTime: 3153600000,
            /**一百年**/
            text: 'tt 年前',
            unit: 31536000
        }
    ]

    var timeNow = new Date()
    var _time = Math.round((anyToDate(timeNow) - anyToDate(time)) / 1000),
        isin;

    for (var i = 0; i < agos.length; i++) {
        if (_time < agos[i].maxTime) {
            return agos[i].text.replace(/tt/, Math.round(_time / agos[i].unit));
        }

    }
}


function addDays(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

function today() {
    var now = new Date()
    return new Date(now.getFullYear(), now.getMonth(), now.getDate())
}

function datePart(date) {
    date = anyToDate(date)
    return new Date(date.getFullYear(), date.getMonth(), date.getDate())
}

function sameDay(a, b) {
    if (a && b) {
        a = anyToDate(a)
        b = anyToDate(b)
        return a.getMonth() == b.getMonth() && a.getFullYear() == b.getFullYear() && a.getDate() == b.getDate();
    }
    return false;
}

function getSeconds(hours, minutes, seconds) {
    var seconds = 0
    if (hours) {
        seconds += hours * 3600
    }
    if (minutes) {
        seconds += minutes * 60
    }
    if (seconds) {
        seconds += seconds * 1
    }
    return seconds
}



export default {
    format: dateFormat,
    fromNow: timeago,
    anyToDate: anyToDate,
    addDays: addDays,
    today: today,
    datePart: datePart,
    sameDay: sameDay,
    getSeconds: getSeconds
}