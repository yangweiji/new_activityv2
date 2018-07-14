new Vue({
    el: '#app',
    data: function () {
        return {
            uploader: null,
            user: _global_data,
            isUpdateDisplayname: false,
            displayname: '',
            tab: 1,
        }
    },
    mounted: function () {
        var that = this
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-avatar',
            success: function (file) {
                that.update('avatar', file.randomName)
            }
        })
    },
    methods: {
        getAvatar: function () {
            if (this.user.avatar)
                return Util.file.downloadUrl(this.user.avatar, 'small')
            else
                return '/img/activity/man.png'
        },
        startUpdateDisplayname: function () {
            this.isUpdateDisplayname = true
            this.displayname = this.user.displayname
        },
        cancelUpdateDisplayname: function () {
            this.isUpdateDisplayname = false
        },
        updateDisplayname: function () {
            var that = this
            if (this.displayname) {
                this.update('displayname', this.displayname, function () {
                    that.user['displayname'] = that.displayname
                    that.cancelUpdateDisplayname()
                })
            }
        },
        update: function (property, value, success) {
            var that = this
            Util.ajax({
                type: 'post',
                dataType: 'text',
                url: '/sec/user/updateproperty',
                data: JSON.stringify({property: property, value: value}),
                success: function () {
                    if (success) {
                        success(value);
                    } else {
                        that.user[property] = value
                    }
                }
            })
        },
        go: function (e) {
            if (e == 1) {
                location.href = "/sec/user/baseinfo"
            }
            if (e == 2) {
                location.href = "/sec/user/account"
            }
        }
    }

})