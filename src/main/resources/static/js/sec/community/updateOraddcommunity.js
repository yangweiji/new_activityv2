new Vue({
    el: "#c_sec_community_add_app",
    data: function () {
        return {
            cacheData:_global_data
        }
    },
    mounted: function () {
        var that = this
        //社团背景图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-community-background',
            success: function (file) {
                that.cacheData.community.background = file.randomName
            }
        })
        //社团小图标上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-community-avatar',
            success: function (file) {
                that.cacheData.community.avatar = file.randomName
            }
        })

        //社团创建时间控件

        $('.c-datetimepicker.created').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.cacheData.community.created = ev.date
            }
        });
        if (that.cacheData.community.created) {
            $('.c-datetimepicker.created').datetimepicker('update', new Date(that.cacheData.community.created))
        }

        //富文本控件
        var imageHandleCallback;
        var toolbarOptions = {
            container: [
                ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
                ['blockquote', 'code-block'],

                [{'header': 1}, {'header': 2}],               // custom button values
                [{'list': 'ordered'}, {'list': 'bullet'}],
                [{'script': 'sub'}, {'script': 'super'}],      // superscript/subscript
                [{'indent': '-1'}, {'indent': '+1'}],          // outdent/indent
                [{'direction': 'rtl'}],                         // text direction

                [{'size': ['small', false, 'large', 'huge']}],  // custom dropdown

                [{'color': []}, {'background': []}],          // dropdown with defaults from theme
                [{'font': []}],
                [{'align': []}],
                ['link', 'image'],
                ['clean']                                         // remove formatting button
            ],
            handlers: {
                'image': function (callback) {
                    imageHandleCallback = callback
                    document.getElementById('c-upload-community-about-editor').click()
                }
            }
        }

        Quill.prototype.getHtml = function () {
            return this.container.querySelector('.ql-editor').innerHTML;
        };

        var quill = new Quill('#c-community-about-editor', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入社团详情',
            theme: 'snow'
        });

        this.editorUploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-community-about-editor',
            success: function (file) {
                if (imageHandleCallback) {
                    var url = Util.file.downloadUrl(file.randomName)
                    var range = quill.getSelection()
                    if (!range) {
                        range = {index: 0}
                    }
                    quill.clipboard.dangerouslyPasteHTML(range.index, "<img src='" + url + "' />")
                }
            }
        })


        if (that.cacheData.community.about) {
            quill.clipboard.dangerouslyPasteHTML(that.cacheData.community.about)
        }

        quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-community-about-text')
            bodyInput.val(quill.getText())
            that.cacheData.community.about = quill.getHtml()
            bodyInput.trigger('change')
        })


        $('#c-community-add-form').validator({}).submit(function () {
            $('input[name=json_data]').val(JSON.stringify(that.cacheData.community.about))
            return true;
        });

        $(window).on("upload", function () {
            var about = that.cacheData.community.about
            that.cacheData.community.about = null
            Util.storageGet(JSON.stringify(that.cacheData.community.about))
        })
    },
    methods: {
        getCommunityBackground: function () {
            if (this.cacheData.community.background)
                return Util.file.downloadUrl(this.cacheData.community.background)
            else
                return "/img/community/activity-avatar.png"
        },
        getCommunityAvatar: function () {
            if (this.cacheData.community.avatar)
                return Util.file.downloadUrl(this.cacheData.community.avatar)
            else
                return "/img/community/activity-avatar.png"
        }
    }
})