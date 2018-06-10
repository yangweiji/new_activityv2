new Vue({
    el: '#c_pub_register_app',
    data: function() {
        return {
            username: $('#username').attr('data-session'),
            disabled: false,
            seconds: 60,
            error: null
        }
    },
    methods: {
        sendVercode:function () {
            var that = this
            this.disabled = true
            if(!this.username){
                this.username = $('#username').val()
            }
            if(!this.username || this.username.length < 11){
                alert("请先输入合法的手机号")
                return
            }
            Util.ajax({
                type: 'get',
                url: '/pub/vercode/getVerCode/' + this.username,
                dataType: "json",
                success: function (data) {
                    var fun = function () {
                        that.seconds--;
                        if(that.seconds > 0){
                            setTimeout(fun,1000)
                        } else {
                            that.seconds = 60
                            that.disabled = false
                        }
                    }
                    setTimeout(fun,1000)
                },
                error:function () {
                    that.disabled = false
                    that.error = "发送验证码出现错误"
                }
            })

        },
        vercodeBtnText: function () {
            if(this.seconds < 60){
                return '还有（{seconds}s）'.format(this)
            }
            return '获取验证码'
        }
    }

})