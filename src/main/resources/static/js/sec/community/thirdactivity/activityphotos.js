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
        //验证
        $('#c-activity-picture-create-form').validator({
            // ignore: ':hidden:not(.am-validate)',
            onValid: function(validity) {
                $(validity.field).closest('.am-u-sm-10').find('.am-alert').hide();
            },

            onInValid: function(validity) {
                var $field = $(validity.field);
                var $group = $field.closest('.am-u-sm-10');
                var $alert = $group.find('.am-alert');
                // 使用自定义的提示信息 或 插件内置的提示信息
                var msg = $field.data('validationMessage') || this.getValidationMessage(validity);

                if (!$alert.length) {
                    $alert = $('<div class="am-alert am-alert-danger"></div>').hide().
                    appendTo($group);
                }

                $alert.html(msg).show();
            },
            // validate: function(validity) {
            //     var v = $('#picture').val()
            //     if (!v) {
            //         var $field = $(validity.field);
            //         var $group = $field.closest('.am-u-sm-10');
            //         var $alert = $group.find('.am-alert');
            //         // 使用自定义的提示信息 或 插件内置的提示信息
            //         // var msg = $field.data('validationMessage') || this.getValidationMessage(validity);
            //         var msg = "请上传图片";
            //         if (!$alert.length) {
            //             $alert = $('<div class="am-alert am-alert-danger"></div>').hide().
            //             appendTo($group);
            //         }
            //
            //         $alert.html(msg).show();
            //         validity.valid = false;
            //     }
            // },

        }).submit(function() {
            return true; // return false to cancel form action
        });

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
