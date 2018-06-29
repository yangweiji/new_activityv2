new Vue({
    el: "#c_sec_community_add_app",
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
                    document.getElementById('c-upload-community-about-editor').click()
                    document.getElementById('c-upload-community-vip-editor').click()
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
            placeholder: '请在此输入团体介绍',
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

        //条款
        var quillVip = new Quill('#c-community-vip-editor', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入会员条款',
            theme: 'snow'
        });

        this.editorUploader = Util.file.uploader({
            randomName: true,
            selectId: 'c-upload-community-vip-editor',
            success: function (file) {
                if (imageHandleCallback) {
                    var url = Util.file.downloadUrl(file.randomName)
                    var range = quillVip.getSelection()
                    if (!range) {
                        range = {index: 0}
                    }
                    quillVip.clipboard.dangerouslyPasteHTML(range.index, "<img src='" + url + "' />")
                }
            }
        })


        if (that.cacheData.community.vipAgreement) {
            quillVip.clipboard.dangerouslyPasteHTML(that.cacheData.community.vipAgreement)
        }

        quillVip.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }

            var bodyInput = $('#c-community-vip-text')
            bodyInput.val(quillVip.getText())
            that.cacheData.community.vipAgreement = quillVip.getHtml()
            bodyInput.trigger('change')
        })


        $('#c-community-add-form').validator({}).submit(function () {

            $('input[name=json_data]').val(JSON.stringify(that.cacheData.community.about))
            $('input[name=json_data]').val(JSON.stringify(that.cacheData.community.vipAgreement))
            return true;
        });

        $(window).on("upload", function () {
            var about = that.cacheData.community.about
            that.cacheData.community.about = null
            Util.storageGet(JSON.stringify(that.cacheData.community.about))

            var vipAgreement = that.cacheData.community.vipAgreement
            that.cacheData.community.vipAgreement = null
            Util.storageGet(JSON.stringify(that.cacheData.community.vipAgreement))
        })
    },
    methods: {
        selectVal: function (e) {
            console.log(e.target.value, this.cacheData.community.isVip);
        }
    }
})