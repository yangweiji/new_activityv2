new Vue({
    el: '#app',
    data: function() {
        return {
            //验证码时间计数
            codeCount: 0,
            canGetVerCode: true,
            error: '',
            username: null,
            password: null,
            password2: null,
            code: null,
        }
    },
    computed: {
        disabled() {
            if (!this.username || this.username.length != 11
                || !this.password || !this.password2 || (this.password != this.password2)
            ) {
                return true;
            }
            else {
                return false;
            }
        },
    },
    methods: {
        getVerCode: function () {
            var that = this;
            that.canGetVerCode = true;
            if (!that.username || that.username.length < 11) {
                nativeToast({
                    message: '请输入有效的手机号码！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    // type: 'error'
                });
                return;
            }

            if (!that.username.match(/^1\d{10}$/)) {
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
                url: "/pub/wx/vercode/getVerCode/" + this.username,
                contentType: "application/json;charset=utf-8",
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        nativeToast({
                            message: '短信验证码已发送，十分钟内有效！',
                            position: 'center',
                            timeout: 5000,
                            square: true,
                            // type: 'error'
                        });

                        that.code = data.message;
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
        },
    }

})