//引入filter
import Vue2Filters from 'vue2-filters'
import Dateutil from './date'
import store from '../store'
var kyFilters = {}
kyFilters.filter = (key, value) => {
    kyFilters[key] = value
}
kyFilters.mixin = opt => {
    for (var key in opt.methods) {
        kyFilters[key] = opt.methods[key]
    }
}
Vue2Filters.install(kyFilters)

//全局变量
const data = {
    // // 正式
    // fsurl: "https://fs.81dong.com",
    // serverUrl: "https://hdb.81dong.com",
    // imageServer: "https://hxzt2018.oss-cn-beijing.aliyuncs.com/",

    // 测试
    fsurl: "https://fs.9kylin.cn",
    serverUrl: "https://a.9kylin.cn",
    imageServer: "https://bjmlsxh.oss-cn-beijing.aliyuncs.com/",

    isIpx: false
}

//sessionChoose 1是带sessionID的GET方法  2是不带sessionID的GET方法, 3是带sessionID的Post方法, 4是不带sessionID的Post方法
//ask是是否要进行询问授权，true为要，false为不要
//sessionChoose为1,2,3,4,所以paramSession下标为0的则为空
function HttpRequest(loading, url, sessionChoose, sessionId, params, method, ask, callBack, error) {
    if (loading == true) {
        wx.showToast({
            title: '数据加载中',
            icon: 'loading'
        })
    }
    var paramSession = [{},
        { 'content-type': 'application/json', 'Cookie': 'JSESSIONID=' + sessionId },
        { 'content-type': 'application/json' },
        { 'content-type': 'application/x-www-form-urlencoded', 'Cookie': 'JSESSIONID=' + sessionId },
        { 'content-type': 'application/x-www-form-urlencoded' }
    ]

    //处理日期格式属性， 后台只识别yyyy-MM-dd HH:mm:ss格式日期
    var castDate = function(obj) {
        if (obj != null && obj instanceof Object) {
            var copy = {}
            for (var k in obj) {
                var v = obj[k]
                if (v instanceof Date) {
                    copy[k] = Dateutil.format(v, 'yyyy-MM-dd HH:mm:ss')
                } else if (v instanceof Object || v instanceof Array) {
                    copy[k] = castDate(v)
                } else {
                    copy[k] = v
                }
            }
            return copy
        } else if (obj != null && obj instanceof Array) {
            var copy = []
            for (var i = 0; i < obj.length; i++) {
                copy.push(castDate(obj[i]))
            }
            return copy
        }
        return obj
    }

    wx.request({
        url: data.serverUrl + url,
        data: castDate(params),
        dataType: "json",
        header: paramSession[sessionChoose],
        method: method,
        success: function(res) {
            console.log("HttpRequest 结果: ", res)

            if (loading == true) {
                wx.hideToast(); //隐藏提示框
            }
            if (res.data.code == 5000) {
                console.log("需要登录")
                    // wxLogin(loading, url, sessionChoose, sessionId, params, method,ask, callBack);
            }
            callBack(res.data);
        },
        complete: function() {
            if (loading == true) {
                wx.hideToast(); //隐藏提示框
            }
        },
        fail: function(res) {
            if (error) error(res)
        }
    })
}

function wxLogin(loading, url, sessionChoose, sessionId, params, method, ask, callBack) {
    wx.login({
        success: function(res) {
            var code = res.code; //得到code
            HttpRequest(true, "/pub/wx/auth/login", false, "", { "code": code }, "GET", false, function(res) {
                if (res.code == 200) {
                    wx.setStorageSync('sessionId', res.sessionId);
                    if (res.isNeedUserInfo == true) {
                        wx.getUserInfo({
                            success: function(res) {},
                            fail: function(res) {
                                console.log("我还没有授权");
                                if (ask == true) {
                                    wx.showModal({
                                        title: '提示',
                                        confirmText: "授权",
                                        content: '若不授权微信登陆，则无法正常使用xxx小程序的功能；点击重新授权，则重新使用；若点击不授权，后期还使用小程序，需在微信【发现】--【小程序】--删除【xxxx】，重新搜索授权登陆，方可使用',
                                        success: function(res) {
                                            if (res.confirm) {
                                                console.log('用户点击确定');
                                                wx.openSetting({
                                                    success: function(res) {
                                                        console.log(res)
                                                        if (!res.authSetting["scope.userInfo"] || !res.authSetting["scope.userLocation"]) {
                                                            //这里是授权成功之后 填写你重新获取数据的js
                                                            wx.getUserInfo({
                                                                withCredentials: false,
                                                                success: function(data) {},
                                                                fail: function() {
                                                                    console.info("3授权失败返回数据");
                                                                }
                                                            });
                                                        }
                                                    }
                                                })
                                            }
                                        }
                                    })
                                }
                            }
                        })
                    } else {
                        HttpRequest(loading, url, sessionChoose, wx.getStorageSync("sessionId"), params, method, ask, callBack);
                    }
                }
            })
        }
    })
}

//小程序登录
function Login() {
    wx.clearStorageSync()

    let promisevariable = new Promise(function(resolve, reject) {
        wx.login({
            success: res => {
                // 发送 res.code 到后台换取 openId, sessionKey, unionId
                var errMsg = res.errMsg;
                if (errMsg != "login:ok") {
                    console.log("错误提示", "出错了，请稍后再试试...")
                } else {
                    var code = res.code;
                    HttpRequest(true, "/pub/wx/auth/login", false, "", { "code": code }, "GET", false, function(res) {
                        console.log("sessionInfo: ", res)
                        if (res.code == 200) {
                            wx.setStorageSync("sessionInfo", res)
                            if (!res.unionId) {
                                //如果unionId没有获取到,设置unionId=''空字符串,通过openId去获取用户信息
                                res.unionId = ''
                            }

                            HttpRequest(true, "/pub/wx/auth/getUserInfo", false, "", { "openid": res.openid, "unionId": res.unionId }, "GET", false, function(res) {
                                // console.log("user: ", res)
                                if (res.user) {
                                    wx.setStorageSync("user", res.user)
                                    console.log("storage user->", res)
                                }

                                //取得用户的默认团体组织，如果没有则默认为【北京市马拉松协会】
                                if (res.community) {
                                    store.state.community = res.community
                                } else {
                                    store.state.community = {
                                        id: 1, //默认的组织团体ID
                                        name: "北京市马拉松协会",
                                        background: "NzrSDNSBEP.png",
                                    };
                                }

                                if (res) {
                                    resolve(res);
                                }
                            });
                        }
                    });
                }
            }
        });
    });

    return promisevariable;
}

//验证用户身份，小程序页面创建时调用此方法
//检查用户手机号是否填写，没有绑定手机号强制绑定手机号登录
function CheckUserValidation() {

    return new Promise((resolve, reject) => {
        console.log("验证用户身份...")
        var user = wx.getStorageSync("user")
        if (!user || !user.mobile) {
            //重新进行登录
            Login().then(function(res) {
                if (res.user) {
                    console.log("验证用户身份OK");
                    resolve(res.user);
                } else {
                    console.log("跳转至登录注册页面");
                    wx.redirectTo({
                        url: "/pages/login/login"
                    });
                }
            });
        } else {
            console.log("验证用户身份OK");
            resolve(user);
        }
    });

}

/*
根据〖中华人民共和国国家标准 GB 11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
    地址码表示编码对象常住户口所在县(市、旗、区)的行政区划代码。
    出生日期码表示编码对象出生的年、月、日，其中年份用四位数字表示，年、月、日之间不用分隔符。
    顺序码表示同一地址码所标识的区域范围内，对同年、月、日出生的人员编定的顺序号。顺序码的奇数分给男性，偶数分给女性。
    校验码是根据前面十七位数字码，按照ISO 7064:1983.MOD 11-2校验码计算出来的检验码。

出生日期计算方法。
    15位的身份证编码首先把出生年扩展为4位，简单的就是增加一个19或18,这样就包含了所有1800-1999年出生的人;
    2000年后出生的肯定都是18位的了没有这个烦恼，至于1800年前出生的,那啥那时应该还没身份证号这个东东，⊙﹏⊙b汗...
下面是正则表达式:
 出生日期1800-2099  (18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])
 身份证正则表达式 /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[12])(0[1-9]|[12]\d|3[01])\d{3}(\d|X)$/i
 15位校验规则 6位地址编码+6位出生日期+3位顺序号
 18位校验规则 6位地址编码+8位出生日期+3位顺序号+1位校验位

 校验位规则     公式:∑(ai×Wi)(mod 11)……………………………………(1)
                公式(1)中：
                i----表示号码字符从由至左包括校验码在内的位置序号；
                ai----表示第i位置上的号码字符值；
                Wi----示第i位置上的加权因子，其数值依据公式Wi=2^(n-1）(mod 11)计算得出。
                i 18 17 16 15 14 13 12 11 10 9 8 7 6 5 4 3 2 1
                Wi 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2 1

*/
//身份证号合法性验证
//支持15位和18位身份证号
//支持地址编码、出生日期、校验位验证
function IdentityCodeValid(code) {
    var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
    var tip = "";
    var pass = true;

    if (!code || !/^[1-9]\d{5}(18|19|20)\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9xX]$/i.test(code)) {
        tip = "身份证号格式错误";
        pass = false;
    } else if (!city[code.substr(0, 2)]) {
        tip = "地址编码错误";
        pass = false;
    } else {
        //18位身份证需要验证最后一位校验位
        if (code.length == 18) {
            code = code.split('');
            //∑(ai×Wi)(mod 11)
            //加权因子
            var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
            //校验位
            var parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2];
            var sum = 0;
            var ai = 0;
            var wi = 0;
            for (var i = 0; i < 17; i++) {
                ai = code[i];
                wi = factor[i];
                sum += ai * wi;
            }
            var last = parity[sum % 11];
            if (parity[sum % 11] != code[17]) {
                tip = "校验位错误";
                pass = false;
            }
        }
    }
    return pass;
}

function emailValid(email) {
    var reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/;
    if (email) {
        return reg.test(email)
    }
    return false
}

//出生日期验证，格式YYYY-MM-DD
function birthdayValid(val) {
    var pattern = /^((19[2-9]\d{1})|(20((0[0-9])|(1[0-8]))))\-((0?[1-9])|(1[0-2]))\-((0?[1-9])|([1-2][0-9])|30|31)$/;
    if (pattern.test(val)) {
        var date = new Date(val);
        var month = val.substring(val.indexOf("-") + 1, val.lastIndexOf("-"));
        return date && (date.getMonth() + 1 == parseInt(month));
    }
    return false;
}

function wxAlert(msg) {
    return new Promise((resolve, reject) => {
        wx.showModal({
            title: '提示',
            content: msg,
            showCancel: false,
            success: function(res) {
                if (res.confirm) {
                    resolve()
                } else if (res.cancel) {
                    reject()
                }
            }
        })
    })

}

function getUser() {
    return wx.getStorageSync("user")
}

function getCommunityId() {
    return wx.getStorageSync("community_id") || 1
}

function downloadUrl(name, style, folder) {
    if (name && (name.toLowerCase().indexOf('http://') == 0 || name.toLowerCase().indexOf('https://') == 0 || name.indexOf('/') == 0)) {
        return name
    }
    var url = data.imageServer;
    if (folder) {
        url += folder + "/"
    } else {
        url += "activity/"
    }
    url += name
    if (style) {
        url += '?x-oss-process=style/' + style
    }
    return url
}


function httpGet(url, param) {
    return new Promise((resolve, reject) => {
        HttpRequest(false, url, false, "", param, "GET", false, resolve, reject)
    })
}

function httpPost(url, param) {
    return new Promise((resolve, reject) => {
        HttpRequest(false, url, false, "", param, "POST", false, resolve, reject)
    })
}

export default {
    data: data,
    HttpRequest: HttpRequest,
    Login: Login,
    CheckUserValidation: CheckUserValidation,
    GetUser: getUser,
    GetCommunityId: getCommunityId,
    filters: kyFilters,
    date: Dateutil,
    downloadUrl: downloadUrl,
    get: httpGet,
    post: httpPost,
    idcardValid: IdentityCodeValid,
    emailValid: emailValid,
    birthdayValid: birthdayValid,
    alert: wxAlert
}