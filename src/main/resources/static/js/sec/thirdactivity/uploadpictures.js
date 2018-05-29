
new Vue({
    el:'#c_sec_activity_addpicture_app',
    data:function () {
        return {
            cacheData:_global_data
        }
    },
    mounted:function () {
        var that=this
        //图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-activity-pictures',
            success: function (file) {
               /* that.cacheData.activityPhotoPicture.picture = file.randomName*/
               that.cacheData.activityPhotoPicture.picture=file.randomName
            }
        })
    },
    methods:{
        getActivityPhotoPicture: function () {
            if (this.cacheData.activityPhotoPicture.picture)
                return Util.file.downloadUrl(this.cacheData.activityPhotoPicture.picture)
            else
                return "/img/community/activity-avatar.png"
        },
    }
})