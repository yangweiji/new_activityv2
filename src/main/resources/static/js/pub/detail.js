$(function() {

});

new Vue({
    el: '#app',
    data: g_data,
    mounted: function () {
        var that = this;
    },
    methods: {
        favorite: function () {
            var that = this;
            if (that.userId)
            {
                $.ajax({
                    type: "POST",
                    url: "/sec/admin/activity/favorite",
                    data: "id={0}".format(that.activityId),
                    dataType: 'text',
                    success: function(data){
                        that.favorite_count = data;
                    }
                });
            }
            else {
                if (window.confirm("请先登录活动平台!"))
                {
                    location.href = "/login";
                }
            }
        }
    }
})