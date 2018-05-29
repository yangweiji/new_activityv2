new Vue({
    el: "#c_sec_community_add_app",
    data: {
        communities:{
            created:_global_data.created
        }
    },
    mounted: function () {
        var that=this
        //社团创建时间控件

        $('.c-datetimepicker.created').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.communities.created = ev.date
            }
        });
        if (that.communities.created) {
            $('.c-datetimepicker.created').datetimepicker('update', new Date(that.communities.created))
        }

    }
});