new Vue({
    el:"#c_sec_activityphoto_publish_app",
    data:function () {
        return{
            cacheData:_global_data
        }
    },
    mounted: function () {
        var that = this
        //图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-activityphoto-picture',
            success: function (file) {
                that.cacheData.activityphoto.picture=file.randomName
            }
        });

        //创建时间
        $('.c-datetimepicker.created').datetimepicker({
            format: 'yyyy-mm-dd hh:ii:ss',
            language: 'zh-CN'
        }).on('changeDate', function (ev) {
            if (ev.date.valueOf()) {
                that.cacheData.activityphoto.created=ev.date
            }
        });
        if (that.cacheData.activityphoto.created) {
            $('.c-datetimepicker.created').datetimepicker('update', new Date(that.cacheData.activityphoto.created))
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
                    document.getElementById('c-upload-activityphoto-axtenalUrl-editor').click()
                }
            }
        }

        Quill.prototype.getHtml = function () {
            return this.container.querySelector('.ql-editor').innerHTML;
        };

        var quill = new Quill('#c-activityphoto-axtenalUrl-editor', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入内容详情',
            theme: 'snow'  // or 'bubble'
        });

        this.editorUploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-activityphoto-axtenalUrl-editor',
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


        if (that.cacheData.activityphoto.axtenalUrl) {
            quill.clipboard.dangerouslyPasteHTML(that.cacheData.activityphoto.axtenalUrl)
        }

        quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-activityphoto-axtenalUrl-text')
            bodyInput.val(quill.getText())

            that.cacheData.activityphoto.axtenalUrl = quill.getHtml()

            bodyInput.trigger('change')
        })
        $('#c-activityphoto-create-form').validator({}).submit(function () {
            $('input[name=json_data]').val(JSON.stringify(that.cacheData.activityphoto.axtenalUrl))
            return true;
        });
        $(window).on("upload", function () {
            var axtenalUrl = that.cacheData.activityphoto.axtenalUrl
            that.cacheData.activityphoto.axtenalUrl = null
            Util.storageGet(JSON.stringify(that.cacheData.activityphoto.axtenalUrl))
        })
    },
    methods: {
        getActivityPicture: function () {
            if (this.cacheData.activityphoto.picture)
                return Util.file.downloadUrl(this.cacheData.activityphoto.picture)
            else
                return "/img/article/activity-avatar.png"
        }
    }
})
