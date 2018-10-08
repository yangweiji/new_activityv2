new Vue({
    el: "#app",
    data: function () {
        return {
            trainingCamp: _global_data
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

                that.trainingCamp.modified=ev.date
            }
        })
        if (that.trainingCamp.modified) {
            $('.c-datetimepicker.publish-time').datetimepicker('update', new Date(that.trainingCamp.modified))
        }
    },
    methods: {
    }
});
