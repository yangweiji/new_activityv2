$(function () {
    //@首页 数字跳动
    var options = {
        useEasing: true,
        useGrouping: true,
        separator: '',
        decimal: '.',
        prefix: '',
        suffix: ''
    };
    var banner_num = new CountUp("banner_num", 0, parseInt($('#banner_num').attr('count')), 0, 5, options);
    banner_num.start();
});

//noinspection JSAnnotator
new Vue({
    el: '#app',
    data: function () {
        return {
            //登录方式，1：手机号+密码 2：手机号+短信验证码 3: 微信扫码
            loginType: 1,
            //验证码时间计数
            codeCount: 0,
            canGetVerCode: true,
            error: '',
            username: null,
            password: null,
            mobile: null,
            code: null,
        }
    },
    computed: {
        disabled1 () {
            if (!this.username || this.username.length != 11
                || !this.password
            ) {
                return true;
            }
            else {
                return false;
            }
        },
        disabled2 () {
            if (!this.mobile || this.mobile.length != 11
                || !this.code || this.code.length < 6
            ) {
                return true;
            }
            else {
                return false;
            }
        },
    },
    methods: {
        show: function (e) {
            this.loginType = e
        },
        getVerCode: function () {
            var that = this;
            that.canGetVerCode = true;
            if (!that.mobile || that.mobile.length < 11 || !that.mobile.match(/^1\d{10}$/)) {
                nativeToast({
                    message: '请输入有效的手机号码！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    // type: 'error'
                });
                return;
            }

            $.ajax({
                url: "/pub/wx/vercode/getVerCode/" + this.mobile,
                contentType: "application/json;charset=utf-8",
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        nativeToast({
                            message: '短信验证码已发送，十分钟内有效！',
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            // type: 'error'
                        });

                        // that.code = data.message;
                        that.canGetVerCode = false;
                        that.codeCount = 60;
                        var fun = function () {
                            that.codeCount--;
                            if (that.codeCount > 0) {
                                setTimeout(fun, 1000)
                            } else {
                                that.codeCount = 60
                                that.canGetVerCode = true
                            }
                        }
                        setTimeout(fun, 1000)
                    }
                },
                error: function () {
                    that.canGetVerCode = true;
                    that.error = "发送验证码出现错误"
                }
            });


        }
    },
})