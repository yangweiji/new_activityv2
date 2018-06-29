new Vue({
    el: "#app",
    data: function () {
        return {
            cacheData: _global_data
        }
    },
    mounted: function () {
        var that = this
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


        if (that.cacheData.article.body) {
            quill.clipboard.dangerouslyPasteHTML(that.cacheData.article.body)
        }

        quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-article-body-text')
            bodyInput.val(quill.getText())

            that.cacheData.article.body = quill.getHtml()

            bodyInput.trigger('change')
        })

        $('#c-article-create-form').validator({}).submit(function () {
            $('input[name=json_data]').val(JSON.stringify(that.cacheData.article.body))
            return true;
        });
        $(window).on("upload", function () {
            var body = that.cacheData.article.body
            that.article.body = null
            Util.storageGet(JSON.stringify(that.cacheData.article.body))
        })
    },
    methods: {
    }
});
