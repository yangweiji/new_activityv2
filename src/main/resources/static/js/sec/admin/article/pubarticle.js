new Vue({
    el: "#app",
    data: function () {
        return {
            article: _global_data
        }
    },
    mounted: function () {
        var that = this
        ///时间控件
        $('.c-datetimepicker.publish-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.article.publishTime = ev.date
            }
        })
        if (that.article.publishTime) {
            $('.c-datetimepicker.publish-time').datetimepicker('update', new Date(that.article.publishTime))
        }
    },
    methods: {
    }
});
