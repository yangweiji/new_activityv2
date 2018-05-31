new Vue({
    el:"#app",
    data:function () {
        return{
            activityPhoto: _global_data,
        }
    },
    mounted: function () {
        var that = this
        //图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-activityphoto-picture',
            success: function (file) {
                that.activityPhoto.picture = file.randomName
            }
        });
    },
    methods: {
        getPictureUrl: function () {
            if (this.activityPhoto.picture)
                return Util.file.downloadUrl(this.activityPhoto.picture)
            else
                return "/img/article/activity-avatar.png"
        }
    }
})
