new Vue({
    el:'#c_sec_activity_picture_app',
    data:{
        pictures:{
            picture:_global_data.picture,
            created:_global_data.created
        }
    },
    mounted:function () {
        var that=this
        //上传时间控件
        $('.c-datetimepicker.created').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss ',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.pictures.created = ev.date
            }
        });
        if (that.pictures.picture) {
            $('.c-datetimepicker.created').datetimepicker('update', new Date(that.pictures.created))
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
                    document.getElementById('c-upload-activity-picture-editor').click()
                }
            }
        }

        Quill.prototype.getHtml = function () {
            return this.container.querySelector('.ql-editor').innerHTML;
        };

        var quill = new Quill('#c-activity-picture-editor', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入内容详情',
            theme: 'snow'  // or 'bubble'
        });

        this.editorUploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-activity-picture-editor',
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


        if (that.pictures.picture) {
            quill.clipboard.dangerouslyPasteHTML(that.pictures.picture)
        }

        quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-activity-picture-text')
            bodyInput.val(quill.getText())

            that.pictures.picture = quill.getHtml()

            bodyInput.trigger('change')
        })
        $('#c-activity-picture-create-form').validator({}).submit(function () {
            $('input[name=json_data]').val(JSON.stringify(that.pictures.picture))
            return true;
        });
        $(window).on("upload", function () {
            var picture = that.pictures.picture
            that.pictures.picture = null
            Util.storageGet(JSON.stringify(that.pictures.picture))
        })
    }
})