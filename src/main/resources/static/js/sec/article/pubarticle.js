new Vue({
    el: "#c_sec_article_publish_app",
    data: {
        article: {
            avatar: _global_data.avatar,
            body:_global_data.body,
            publishTime:_global_data.publishTime
        }
    },
    mounted: function () {
        var that = this
        //图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-article-avatar',
            success: function (file) {
                that.article.avatar = file.randomName
            }
        })

        //发布时间控件
        $('.c-datetimepicker.publish-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss ',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.article.publishTime = ev.date
            }
        });
        if (that.article.publishTime) {
            $('.c-datetimepicker.publish-time').datetimepicker('update', new Date(that.article.publishTime))
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
                    document.getElementById('c-upload-article-body-editor').click()
                }
            }
        }

        Quill.prototype.getHtml = function () {
            return this.container.querySelector('.ql-editor').innerHTML;
        };

        var quill = new Quill('#c-article-body-editor', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入内容详情',
            theme: 'snow'  // or 'bubble'
        });

        this.editorUploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-article-body-editor',
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


        if (that.article.body) {
            quill.clipboard.dangerouslyPasteHTML(that.article.body)
        }

        quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-article-body-text')
            bodyInput.val(quill.getText())

            that.article.body = quill.getHtml()

            bodyInput.trigger('change')
        })
        $('#c-article-create-form').validator({}).submit(function () {
            $('input[name=json_data]').val(JSON.stringify(that.article.body))
            return true;
        });
        $(window).on("upload", function () {
            var body = that.article.body
            that.article.body = null
            Util.storageGet(JSON.stringify(that.article.body))
        })
    },
    methods: {
        getArticleAvatar: function () {
            if (this.article.avatar)
                return Util.file.downloadUrl(this.article.avatar)
            else
                return "/img/article/activity-avatar.png"
        }
    }
})
;
