new Vue({
    el: "#c_sec_community_add_app",
    data: {
        communities: {
            avatar: _global_data.avatar,
            background: _global_data.background,
            created: _global_data.created,
            about: _global_data.about
        }
    },
    mounted: function () {
        var that = this
        //社团背景图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-community-background',
            success: function (file) {
                that.communities.background = file.randomName
            }
        })
        //社团小图标上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-community-avatar',
            success: function (file) {
                that.communities.avatar = file.randomName
            }
        })

        //社团创建时间控件

        $('.c-datetimepicker.created').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.communities.created = ev.date
            }
        });
        if (that.communities.created) {
            $('.c-datetimepicker.created').datetimepicker('update', new Date(that.communities.created))
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


        if (that.communities.about) {
            quill.clipboard.dangerouslyPasteHTML(that.communities.about)
        }

        quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-community-about-text')
            bodyInput.val(quill.getText())
            that.communities.about = quill.getHtml()
            bodyInput.trigger('change')
        })


        $('#c-community-add-form').validator({}).submit(function () {
            $('input[name=json_data]').val(JSON.stringify(that.communities.about))
            return true;
        });

        $(window).on("upload", function () {
            var about = that.communities.about
            that.communities.about = null
            Util.storageGet(JSON.stringify(that.communities.about))
        })
    },
    methods: {
        getCommunityBackground: function () {
            if (this.communities.background)
                return Util.file.downloadUrl(this.communities.background)
            else
                return "/img/community/activity-avatar.png"
        },
        getCommunityAvatar: function () {
            if (this.communities.avatar)
                return Util.file.downloadUrl(this.communities.avatar)
            else
                return "/img/community/activity-avatar.png"
        }
    }
})