$(function () {

})

/***
 * 删除相册图片
 * @param id
 */
function deletePicture(id) {
    var del = window.confirm("确定删除吗？");
    if (del) {
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: '/sec/community/thirdactivity/deletePicture',
            data: {
                pictureId: id,
            },
            success: function (data) {
                if (data) {
                    alert("操作成功！");
                    location.reload()
                }
            },
        })
    }
};

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
