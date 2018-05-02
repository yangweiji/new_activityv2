new Vue({
    el: '#app',
    data: function () {
        return {
            uploader: null,
            communities: _global_data
        }
    },
    mounted: function () {
        var that = this
        this.uploader=Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-background',
            success: function (file) {
                that.update('background', file.randomName)
            }
        })
    },
    methods: {
        getBackGround: function () {
            if (this.communities.background)
                return Util.file.downloadUrl(this.communities.background)
        },
        update: function (property, value, success) {
            var that = this
            Util.ajax({
                type: 'post',
                dataType: 'text',
                url: '/indexes/communityproperties',
                data: JSON.stringify({property: property, value: value}),
                success: function () {
                    if (success) {
                        success(value);
                    } else {
                        that.communities[property] = value
                    }
                }
            })
        }
    },
})
