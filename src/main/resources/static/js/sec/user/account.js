$(function () {

});

new Vue({
    el: '#app',
    data: function () {
        return {
            user: _global_data,
            uploader: null,
            isUpdateDisplayname: false,
            displayname: '',
            tab: 2,
            //验证码时间计数
            codeCount: 0,
            canGetVerCode: true,
            error: '',
            mobile: null,
            smsCode: null,
            //验证码时间计数
            codeCount2: 0,
            canGetVerCode2: true,
            error2: '',
            newMobile: null,
            smsCode2: null,
            //密码
            oldPassword: null,
            password: null,
            password2: null,
            //添加手机号
            codeCount3: 0,
            canGetVerCode3: true,
            error3: '',
            mobile3: null,
            smsCode3: null,
            password3: null,
        }
    },
    mounted: function () {
        var that = this;
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-avatar',
            success: function (file) {
                that.update('avatar', file.randomName)
            }
        });

        //当前手机号
        if (that.user.mobile) {
            that.mobile = that.user.mobile;
        }
    },
    computed: {
        //验证更换手机号
        disabled () {
            if (!this.mobile || this.mobile.length != 11
                || !this.smsCode || this.smsCode.length < 6
                || !this.newMobile || this.newMobile.length != 11
                || !this.smsCode2 || this.smsCode2.length < 6
            ) {
                return true;
            }
            else {
                return false;
            }
        },
        //验证更新登录密码
        changePasswordDisabled () {
            if (!this.oldPassword
                || !this.password || this.password.length < 6
                || !this.password2 || this.password2.length < 6
                || this.password2 != this.password) {
                return true;
            }
            else {
                return false;
            }
        },
        //验证添加手机号
        addMobileDisabled () {
            if (!this.mobile3 || this.mobile3.length != 11
                || !this.smsCode3 || this.smsCode3.length < 6
                || !this.password3 || this.password3.length < 6
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
            if (!that.mobile || that.mobile.length < 11 || !that.mobile.match(/^1\d{10}$/)) {
                nativeToast({
                    message: '请输入有效的原手机号码！',
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

                        that.smsCode = data.message;
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
        getVerCode2: function () {
            var that = this;
            that.canGetVerCode2 = true;
            if (!that.newMobile || that.newMobile.length < 11 || !that.newMobile.match(/^1\d{10}$/)) {
                nativeToast({
                    message: '请输入有效的新手机号码！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    // type: 'error'
                });
                return;
            }

            $.ajax({
                url: "/pub/wx/vercode/getVerCode/" + this.newMobile,
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

                        that.smsCode2 = data.message;
                        that.canGetVerCode2 = false;
                        that.codeCount2 = 60;
                        var fun = function () {
                            that.codeCount2--;
                            if (that.codeCount2 > 0) {
                                setTimeout(fun, 1000)
                            } else {
                                that.codeCount2 = 60
                                that.canGetVerCode2 = true
                            }
                        }
                        setTimeout(fun, 1000)
                    }
                },
                error: function () {
                    that.canGetVerCode2 = true;
                    that.error2 = "发送验证码出现错误"
                }
            });
        },
        getVerCode3: function () {
            var that = this;
            that.canGetVerCode3 = true;
            if (!that.mobile3 || that.mobile3.length < 11 || !that.mobile3.match(/^1\d{10}$/)) {
                nativeToast({
                    message: '请输入有效的登录手机号码！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    // type: 'error'
                });
                return;
            }

            $.ajax({
                url: "/pub/wx/vercode/getVerCode/" + this.mobile3,
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

                        that.smsCode3 = data.message;
                        that.canGetVerCode3 = false;
                        that.codeCount3 = 60;
                        var fun = function () {
                            that.codeCount3--;
                            if (that.codeCount3 > 0) {
                                setTimeout(fun, 1000)
                            } else {
                                that.codeCount3 = 60
                                that.canGetVerCode3 = true
                            }
                        }
                        setTimeout(fun, 1000)
                    }
                },
                error: function () {
                    that.canGetVerCode3 = true;
                    that.error3 = "发送验证码出现错误"
                }
            });
        },
        //更换手机号
        bindUpdateMobile: function () {
            var that = this;
            //绑定原手机号
            that.mobile = that.user.mobile
            that.newMobile = null;
            that.smsCode = null;
            that.smsCode2 = null;
            that.canGetVerCode = true;
            that.canGetVerCode2 = true;
            //打开modal
            var $modal = $('#update-mobile-modal');
            $modal.modal('open');
        },
        updateMobile: function () {
            var that = this;

            if (that.newMobile == that.mobile) {
                nativeToast({
                    message: '请输入不同的手机号码！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    // type: 'error'
                });
                return;
            }

            $.ajax({
                url: "/sec/user/updateMobile",
                contentType: "application/json;charset=utf-8",
                type: "post",
                data: JSON.stringify({
                    mobile: $("#mobile").val().trim(),
                    smsCode: $("#smsCode").val().trim(),
                    newMobile: $("#newMobile").val().trim(),
                    smsCode2: $("#smsCode2").val().trim(),
                }),
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        //绑定用户手机号
                        that.user.mobile = $("#newMobile").val().trim()

                        nativeToast({
                            message: '手机号已更换成功！',
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            type: 'success'
                        });
                        //关闭modal
                        var $modal = $('#update-mobile-modal');
                        $modal.modal('close');
                    }
                    else {
                        nativeToast({
                            message: data.message,
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            type: 'error'
                        });
                    }
                },
                error: function () {
                    nativeToast({
                        message: '操作失败！',
                        position: 'center',
                        timeout: 3000,
                        square: true,
                        type: 'error'
                    });
                }
            });
        },
        //修改登录密码
        bindChangePassword: function () {
            //打开modal
            var $modal = $('#change-password-modal');
            $modal.modal('open');
        },
        changePassword: function () {
            var that = this;
            if (that.password == that.oldPassword) {
                nativeToast({
                    message: '新密码不能与旧密码相同，请重新输入新密码！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    // type: 'error'
                });
                return;
            }

            $.ajax({
                url: "/sec/user/changePassword2",
                contentType: "application/json;charset=utf-8",
                type: "post",
                data: JSON.stringify({
                    oldPassword: $("#oldPassword").val().trim(),
                    password: $("#password").val().trim(),
                }),
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        nativeToast({
                            message: '修改密码成功，请牢记新密码！',
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            type: 'success'
                        });
                        //关闭modal
                        var $modal = $('#change-password-modal');
                        $modal.modal('close');
                    }
                    else {
                        nativeToast({
                            message: data.message,
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            type: 'error'
                        });
                    }
                },
                error: function () {
                    nativeToast({
                        message: '操作失败！',
                        position: 'center',
                        timeout: 3000,
                        square: true,
                        type: 'error'
                    });
                }
            });
        },
        //添加手机号
        bindAddMobile: function () {
            var that = this;
            that.mobile3 = null;
            that.smsCode3 = null;
            that.password3 = null;
            that.canGetVerCode3 = true;
            //打开modal
            var $modal = $('#add-mobile-modal');
            $modal.modal('open');
        },
        addMobile: function () {
            var that = this;
            $.ajax({
                url: "/sec/user/addMobile",
                contentType: "application/json;charset=utf-8",
                type: "post",
                data: JSON.stringify({
                    mobile: $("#mobile3").val().trim(),
                    smsCode: $("#smsCode3").val().trim(),
                    password: $("#password3").val().trim(),
                }),
                dataType: "json",
                success: function (data) {
                    if (data.code == 200) {
                        that.user.mobile = that.mobile3;

                        nativeToast({
                            message: '添加登录手机号码成功，请牢记登录密码！',
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            type: 'success'
                        });
                        //关闭modal
                        var $modal = $('#add-mobile-modal');
                        $modal.modal('close');
                    }
                    else {
                        nativeToast({
                            message: data.message,
                            position: 'center',
                            timeout: 3000,
                            square: true,
                            type: 'error'
                        });
                    }
                },
                error: function () {
                    nativeToast({
                        message: '操作失败！',
                        position: 'center',
                        timeout: 3000,
                        square: true,
                        type: 'error'
                    });
                }
            });
        },
        getAvatar: function () {
            if (this.user.avatar)
                return Util.file.downloadUrl(this.user.avatar, 'small')
            else
                return '/img/activity/man.png'
        },
        startUpdateDisplayname: function () {
            this.isUpdateDisplayname = true
            this.displayname = this.user.displayname
        },
        cancelUpdateDisplayname: function () {
            this.isUpdateDisplayname = false
        },
        updateDisplayname: function () {
            var that = this
            if (this.displayname) {
                this.update('displayname', this.displayname, function () {
                    that.user['displayname'] = that.displayname
                    that.cancelUpdateDisplayname()
                })
            }
        },
        update: function (property, value, success) {
            var that = this
            Util.ajax({
                type: 'post',
                dataType: 'text',
                url: '/sec/user/updateproperty',
                data: JSON.stringify({property: property, value: value}),
                success: function () {
                    if (success) {
                        success(value);
                    } else {
                        that.user[property] = value
                    }
                }
            })
        },
        go: function (e) {
            if (e == 1) {
                location.href = "/sec/user/baseinfo"
            }
            if (e == 2) {
                location.href = "/sec/user/account"
            }
        }
    }

})