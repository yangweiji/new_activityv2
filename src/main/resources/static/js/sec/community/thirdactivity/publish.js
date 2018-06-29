var type = Util.getQueryStrings("type")
var __c_activity_publish_cookie_key_activity = '__c2_activity_publish_cookie_key_activity_' + type
var __c_activity_publish_cookie_key_editor = '__c2_activity_publish_cookie_key_editor_' + type
new Vue({
    el: '#c_sec_thirdactivity_publish_app',
    data: function() {
        var storeCachedData = _global_data
        if( !_global_data.activity.id) {
            var storeCachedDataActivity = Util.storageGet(__c_activity_publish_cookie_key_activity)
            var storeCachedDataEditor = Util.storageGet(__c_activity_publish_cookie_key_editor)
            if (storeCachedDataActivity && storeCachedDataActivity != 'undefined' && storeCachedDataActivity != 'null') {
                var storeCachedData = JSON.parse(storeCachedDataActivity)
                if (storeCachedDataEditor && storeCachedDataEditor != 'undefined' && storeCachedDataEditor != 'null') {
                    storeCachedData.activity.body = storeCachedDataEditor
                }

            }
        }
        if(!storeCachedData.scoreInfos){
            storeCachedData.scoreInfos = {
                generalUserScore: 0,
                vipUserScore: 0
            }
        }
        return {
            cacheData: storeCachedData ,
            attendInfo:{
                commons: [
                    { title: '昵称', type: 'text', required: false },
                    { title: '邮件', type: 'text', required: false },
                    { title: '性别', multiple: false, type: 'select', required: false, options: [{ title:'男' }, {title: '女' }] },
                    { title: '血型', multiple: false, type: 'select', required: false, options: [{ title:'A' }, {title: 'B' }, {title: 'AB' }, {title: 'O' }] },
                    { title: 'T恤尺寸', multiple: false, type: 'select', required: false, options: [{ title:'S' }, {title: 'M' }, {title: 'L' }, {title: 'XL' }, {title: 'XXL' }, {title: 'XXXL' }] },
                    { title: '工作单位', type: 'text', required: false },
                    { title: '职业', type: 'text', required: false },
                    { title: '紧急联系人姓名', type: 'text', required: false },
                    { title: '紧急联系人电话', type: 'text', required: false },
                    { title: '是否党员', multiple: false, type: 'select', required: false, options: [{ title:'群众' }, {title: '党员' }] },
                    { title: '家庭地址', type: 'text', required: false },
                    { title: '微信号', type: 'text', required: false },
                    { title: '身份证号', type: 'text', required: false },
                    { title: '出生日期', type: 'text', required: false },
                    { title: '备注', type: 'textarea', required: false },
                ],
                customs: [
                    { title: '单行文本', type: 'text', required: false },
                    { title: '多行文本', type: 'textarea', required: false },
                    { title: '单选', multiple: false, type: 'select', required: false, options: [{ title:'' }, {title: '' }] },
                    { title: '多选', multiple: true, type: 'select', required: false , options: [{ title:'' }, {title: '' }]}
                ]
            },
            scoreInfo:{

            },
            showAddressMap:false
        }
    },
    mounted: function(){
        var that = this
        ///时间控件
        $('.c-datetimepicker.start-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN'
        }).on('changeDate', function(ev){
            if (ev.date.valueOf()){
                that.cacheData.activity.startTime = ev.date
            }
        })
        if(that.cacheData.activity.startTime) {
            $('.c-datetimepicker.start-time').datetimepicker('update', new Date(that.cacheData.activity.startTime))
        }

        $('.c-datetimepicker.end-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN'
        }).on('changeDate', function(ev){
            if (ev.date.valueOf()){
                that.cacheData.activity.endTime = ev.date
            }
        });
        if(that.cacheData.activity.endTime) {
            $('.c-datetimepicker.end-time').datetimepicker('update', new Date(that.cacheData.activity.endTime))
        }

        $('.c-datetimepicker.attend-due-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN'
        }).on('changeDate', function(ev){
            if (ev.date.valueOf()){
                that.cacheData.activity.attendDueTime = ev.date
            }
        });
        if(that.cacheData.activity.attendDueTime) {
            $('.c-datetimepicker.attend-due-time').datetimepicker('update', new Date(that.cacheData.activity.attendDueTime))
        }
        //类别
        $('#activity_type').on('change', function(ev) {
            that.cacheData.activity.activityType = ev.currentTarget.value
        }).val(that.cacheData.activity.activityType);

        $('#activity_tags').on('change', function(ev) {
            that.cacheData.activity.tags = ev.currentTarget.value
        }).val(that.cacheData.activity.tags);

        ///富文本控件
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
                'image': function(callback){
                    imageHandleCallback = callback
                    document.getElementById('c-upload-activity-body-editor').click()
                }
            }
        }

        Quill.prototype.getHtml = function() {
            return this.container.querySelector('.ql-editor').innerHTML;
        };

        var quill = new Quill('#c-activity-body-editor', {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入活动详情',
            theme: 'snow'  // or 'bubble'
        });

        this.editorUploader = Util.file.uploader({
            randomName: true,
            selectId:'c-upload-activity-body-editor',
            success:function (file) {
                if(imageHandleCallback) {
                    var url = Util.file.downloadUrl(file.randomName)
                    var range = quill.getSelection()
                    if(!range){
                        range= { index : 0}
                    }
                    quill.clipboard.dangerouslyPasteHTML(range.index, "<img src='" + url + "' />")
                }
            }
        })


        if(that.cacheData.activity.body){
            quill.clipboard.dangerouslyPasteHTML(that.cacheData.activity.body)
        }

        quill.on('editor-change', function(eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }


            var bodyInput = $('#c-activity-body-text')
            bodyInput.val(quill.getText())

            that.cacheData.activity.body = quill.getHtml()

            bodyInput.trigger('change')
        })

        $('#c-thirdactivity-create-form').validator({

        }).submit(function() {
            $('input[name=json_data]').val(JSON.stringify(that.cacheData))
            return true; // return false to cancel form action
        });


        $(window).on("unload", function(){
            if( !_global_data.activity.id) {
                var body = that.cacheData.activity.body
                that.cacheData.activity.body = null
                Util.storageSet(__c_activity_publish_cookie_key_activity, JSON.stringify(that.cacheData))
                Util.storageSet(__c_activity_publish_cookie_key_editor, body)
            }
        });

        window.addEventListener('message', function(event) {
            // 接收位置信息，用户选择确认位置点后选点组件会触发该事件，回传用户的位置信息
            var loc = event.data;
            if (loc && loc.module == 'locationPicker') {//防止其他应用也会向该页面post信息，需判断module是否为'locationPicker'
                console.log('location', loc);
                $('#activity_address').trigger('change')
                that.cacheData.activity.address = loc.poiaddress
            }
        }, false);
    },
    methods: {
        objectId: function (obj) {
            return Util.objectId(obj)
        },
        addTicket: function (index) {
            this.cacheData.tickets.splice(index + 1, 0, {
                title:null,
                price:null,
                count:null,
                userLevel:0
            });
        },
        removeTicket:function (index) {
            this.cacheData.tickets.splice(index, 1);
        },
        addAttendInfo: function (info) {
            this.cacheData.attendInfos.push($.extend(true, {}, info))
        },
        removeAttendInfo: function (info) {
            this.cacheData.attendInfos.splice(this.cacheData.attendInfos.indexOf(info), 1)
        },
        removeAttendInfoOpt: function (info, opt) {
            info.options.splice(info.options.indexOf(opt), 1)
        },
        addAttendInfoOpt:function (info) {
            info.options.push({title: ''});
        }


    }

})