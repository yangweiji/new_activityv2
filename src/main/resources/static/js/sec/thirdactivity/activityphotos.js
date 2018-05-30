function _deletePicture(id) {
    var del = window.confirm("确定删除吗？");
    if (del) {
        Util.ajax({
            type: 'post',
            dataType: 'json',
            url: '/sec/thirdactivity/deletePictures?pictureId=' + id,
            success: function (data) {
                if (data) {
                    location.reload()
                }
            },
        })
    }
}


new Vue({
    el: '#app',
    data: function () {
        return {
            cacheData: _global_data
        }
    },
    mounted: function () {
        var that = this
        //图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-activity-pictures',
            success: function (file) {
                that.cacheData.activityPhotoPicture.picture = file.randomName
            }
        })
    },
    methods: {
        getActivityPhotoPicture: function () {
            if (this.cacheData.activityPhotoPicture.picture)
                return Util.file.downloadUrl(this.cacheData.activityPhotoPicture.picture)
            else
                return "/img/community/activity-avatar.png"
        },
    }
})
