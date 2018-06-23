new Vue({
    el: "#app",
    data: function () {
        return {
            cacheData: _global_data
        }
    },
    mounted: function () {
        var that = this
        //海报上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-poster-avatar',
            success: function (file) {
                that.cacheData.poster.avatar = file.randomName
            }
        })

        //手机海报上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-poster-mobileAvatar',
            success: function (file) {
                that.cacheData.poster.mobileAvatar = file.randomName
            }
        })

    },
    methods: {
        getPosterAvatar: function () {
            if (this.cacheData.poster.avatar)
                return Util.file.downloadUrl(this.cacheData.poster.avatar)
            else
                return "/img/article/activity-avatar.png"
        },
        getPosterMobileAvatar: function () {
            if (this.cacheData.poster.mobileAvatar)
                return Util.file.downloadUrl(this.cacheData.poster.mobileAvatar)
            else
                return "/img/article/activity-avatar.png"
        },
    }
});