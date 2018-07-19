new Vue({
    el: "#app",
    data: function () {
        return {
            sms: _global_data,
            smsTemplateList: []
        }
    },
    computed: {

    },
    mounted: function () {
        var that = this;
        $.ajax({
            url: "/sec/community/sms/getSmsTemplateList",
            contentType: "application/json;charset=utf-8",
            type: "post",
            dataType: "json",
            success: function (data) {
                that.smsTemplateList = data;
                that.selectName('活动提醒通知');
            },
            error: function () {
                nativeToast({
                    message: '短信模板配置读取异常！',
                    position: 'center',
                    timeout: 3000,
                    square: true,
                    type: 'error'
                });
            }
        });
    },
    methods: {
        //选择短信模板
        selectVal: function (e) {
            var that = this;
            // console.log(e.target.value);
            for (var i = 0; i < this.smsTemplateList.length; i ++) {
                var item = this.smsTemplateList[i];
                if (e.target.value == item.code) {
                    that.sms.template = item.template;
                    that.sms.templateName = item.name;
                }
            }
        },
        selectName: function (value) {
            var that = this;
            for (var i = 0; i < this.smsTemplateList.length; i ++) {
                var item = this.smsTemplateList[i];
                if (value == item.name) {
                    that.sms.templateCode = item.code;
                    that.sms.template = item.template;
                    that.sms.templateName = item.name;
                }
            }
        }

    }
})
