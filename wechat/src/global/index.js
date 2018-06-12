//全局变量
const data = {
    sessionInfo: null,
    userInfo: null,
    serverUrl: "https://a.9kylin.cn/",
    imageServer: "http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/"
}
  
//sessionChoose 1是带sessionID的GET方法  2是不带sessionID的GET方法, 3是带sessionID的Post方法, 4是不带sessionID的Post方法  
//ask是是否要进行询问授权，true为要，false为不要  
//sessionChoose为1,2,3,4,所以paramSession下标为0的则为空  
function HttpRequest(loading, url, sessionChoose, sessionId, params, method, ask, callBack) {  
    if (loading == true) {  
        wx.showToast({  
            title: '数据加载中',  
            icon: 'loading'  
        })  
    }  
    var paramSession = [{},  
     { 'content-type': 'application/json', 'Cookie': 'JSESSIONID=' + sessionId },   
     { 'content-type': 'application/json' },  
     { 'content-type': 'application/x-www-form-urlencoded', 'Cookie': 'JSESSIONID=' + sessionId},  
     { 'content-type': 'application/x-www-form-urlencoded'}]  
    wx.request({  
        url: data.serverUrl + url,  
        data: params,  
        dataType: "json",  
        header: paramSession[sessionChoose],  
        method: method,  
        success: function (res) {  
            console.log("HttpRequest 结果: ", res)

            if (loading == true) {  
                wx.hideToast();//隐藏提示框  
            }  
            if (res.data.code == 5000) {  
                console.log("需要登录")
                // wxLogin(loading, url, sessionChoose, sessionId, params, method,ask, callBack);  
            }  
            callBack(res.data);  
        },  
        complete: function () {  
            if (loading == true) {  
                wx.hideToast();//隐藏提示框  
            }  
        }  
    })  
}  
  
function wxLogin(loading, url, sessionChoose, sessionId, params, method,ask, callBack) {  
    wx.login({  
        success: function (res) {  
            var code = res.code;//得到code  
            HttpRequest(true, "/pub/wx/auth/login", false, "", { "code": code }, "GET", false, function (res) {  
                if (res.code == 200) {  
                    wx.setStorageSync('sessionId', res.sessionId);  
                    if (res.isNeedUserInfo == true) {  
                        wx.getUserInfo({  
                            success: function (res) {  
                                // HttpRequst(true, "ztc/product/saveUser", 3, wx.getStorageSync("sessionId"), { "encryptedData": res.encryptedData, "iv": res.iv }, "POST", false, function (res) {  
                                //     HttpRequst(loading, url, sessionChoose, wx.getStorageSync("sessionId"), params, method, ask, callBack);  
                                // })  
                            },  
                            fail: function (res) {  
                                console.log("我还没有授权");  
                                if (ask == true) {  
                                    wx.showModal({  
                                        title: '提示',  
                                        confirmText: "授权",  
                                        content: '若不授权微信登陆，则无法正常使用xxx小程序的功能；点击重新授权，则重新使用；若点击不授权，后期还使用小程序，需在微信【发现】--【小程序】--删除【xxxx】，重新搜索授权登陆，方可使用',  
                                        success: function (res) {  
                                            if (res.confirm) {  
                                                console.log('用户点击确定');  
                                                wx.openSetting({  
                                                    success: function (res) {  
                                                        console.log(res)  
                                                        if (!res.authSetting["scope.userInfo"] || !res.authSetting["scope.userLocation"]) {  
                                                            //这里是授权成功之后 填写你重新获取数据的js  
                                                            wx.getUserInfo({  
                                                                withCredentials: false,  
                                                                success: function (data) {  
                                                                    // HttpRequest(true, "ztc/product/saveUser", 3, wx.getStorageSync("sessionId"), { "encryptedData": res.encryptedData, "iv": res.iv }, "POST", false, function (res) {  
                                                                    //     HttpRequest(loading, url, sessionChoose, wx.getStorageSync("sessionId"), params, method, ask, callBack);  
                                                                    // })  
                                                                },  
                                                                fail: function () {  
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
    if (wx.getStorageSync("sessionInfo")) return;
    
    wx.login({
        success: res => {
            // 发送 res.code 到后台换取 openId, sessionKey, unionId  
            var errMsg = res.errMsg;  
            if (errMsg != "login:ok") {  
                console.log("错误提示","出错了，请稍后再试试...")  
            } else {  
                var code = res.code;
                HttpRequest(true, "/pub/wx/auth/login", false, "", { "code": code }, "GET", false, function (res) {
                    console.log("global data: ", res)
                    if (res.code == 200) {
                        wx.setStorageSync("sessionInfo", res)
                        HttpRequest(true, "/pub/wx/auth/getUserInfo", false, "", { "openid": res.openid }, "GET", false, function (res) {
                            console.log("user: ", res)
                            if (res) {
                                wx.setStorageSync("user", res)
                            }
                        });
                    }
                });
            }
        }
    });

    //检查session_key
    // wx.checkSession({
    //     success: function(){
    //       //session_key 未过期，并且在本生命周期一直有效
    //       console.log("session_key 未过期，并且在本生命周期一直有效");
    //       //if (wx.getStorageSync("sessionInfo")) return;
    //     },
    //     fail: function() {
    //         // session_key 已经失效，需要重新执行登录流程
    //         wx.login({
    //             success: res => {
    //                 // 发送 res.code 到后台换取 openId, sessionKey, unionId  
    //                 var errMsg = res.errMsg;  
    //                 if (errMsg != "login:ok") {  
    //                     console.log("错误提示","出错了，请稍后再试试...")  
    //                 } else {  
    //                     var code = res.code;
    //                     HttpRequest(true, "/pub/wx/auth/login", false, "", { "code": code }, "GET", false, function (res) {
    //                         console.log("global data: ", res)
    //                         wx.setStorageSync("sessionInfo", res)
    //                     });
    //                 }
    //             }
    //         });
    //     }
    // })
    
}

//验证用户身份，小程序页面创建时调用此方法
function CheckUserValidation() {
    // console.log("openid: ", wx.getStorageSync("sessionInfo").openid)

    // var param = {
    //     openId: wx.getStorageSync("sessionInfo").openid
    // }
    // //验证用户身份
    // HttpRequest(true, "/pub/wx/auth/validate", 4, "", param, "POST", false, function (res) {
    //     if (!res) {
    //         //跳转至登录界面验证身份
    //         // wx.navigateTo({
    //         //     url: "/pages/login/login"
    //         // });
    //         wx.redirectTo({
    //             url: "/pages/login/login"
    //         });
    //     }        
    // });

    console.log("user: ", wx.getStorageSync("user"));
    if (!wx.getStorageSync("user")) {
        // 跳转至登录界面验证身份
        wx.redirectTo({
            url: "/pages/login/login"
        });
    }
}

module.exports = {  
    data: data,
    HttpRequest: HttpRequest,  
    Login: Login,
    CheckUserValidation: CheckUserValidation
} 