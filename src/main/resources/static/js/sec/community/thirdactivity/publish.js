$(function() {
    // $('#saveBtn').on('click', function () {
    //     console.log("save");
    //     $('#c-thirdactivity-create-form').action = "/sec/community/thirdactivity/publish"
    //     $('#c-thirdactivity-create-form').validator({
    //     }).submit(function() {
    //         alert("submit");
    //         $('input[name=json_data]').val(JSON.stringify(that.cacheData))
    //         return true; // return false to cancel form action
    //     });
    // });
});

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
            showAddressMap:false,

            //素材库
            images: [],
            //素材库图片分类数组
            categories: [],
            //默认的分组
            category: '未分组',
        }
    },
    mounted: function(){
        var that = this;
        ///时间控件:活动开始时间
        $('.c-datetimepicker.start-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN'
        }).on('changeDate', function(ev){
            if (ev.date.valueOf()){
                that.cacheData.activity.startTime = ev.date
            }
        })
        //显示活动开始时间
        if(that.cacheData.activity.startTime) {
            $('.c-datetimepicker.start-time').datetimepicker('update', new Date(that.cacheData.activity.startTime))
        }

        //活动结束时间
        $('.c-datetimepicker.end-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN'
        }).on('changeDate', function(ev){
            if (ev.date.valueOf()){
                that.cacheData.activity.endTime = ev.date
            }
        });
        //显示活动结束时间
        if(that.cacheData.activity.endTime) {
            $('.c-datetimepicker.end-time').datetimepicker('update', new Date(that.cacheData.activity.endTime))
        }

        //报名结束时间
        $('.c-datetimepicker.attend-due-time').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh-CN'
        }).on('changeDate', function(ev){
            if (ev.date.valueOf()){
                that.cacheData.activity.attendDueTime = ev.date
            }
        });
        //显示报名结束时间
        if(that.cacheData.activity.attendDueTime) {
            $('.c-datetimepicker.attend-due-time').datetimepicker('update', new Date(that.cacheData.activity.attendDueTime))
        }

        //活动类别变更
        $('#activity_type').on('change', function(ev) {
            that.cacheData.activity.activityType = ev.currentTarget.value
        }).val(that.cacheData.activity.activityType);
        //活动标签变更
        $('#activity_tags').on('change', function(ev) {
            that.cacheData.activity.tags = ev.currentTarget.value
        }).val(that.cacheData.activity.tags);

        //表单验证提交
        $('#c-thirdactivity-create-form').validator({
            validate: function (validity) {
                if ($(validity.field).is('.c-datetimepicker.start-time')) {
                    var v = $(validity.field).val();
                    var v1 = moment(v);
                    var v2 = moment(that.cacheData.activity.endTime);
                    if (v1.isAfter(v2)) {
                        $('#date-alert').show();
                        validity.valid = false;
                    } else {
                        $('#date-alert').hide();
                    }
                }

                // if ($(validity.field).is('.c-datetimepicker.end-time')) {
                //     var v = $(validity.field).val();
                //     var v1 = moment(that.cacheData.activity.startTime);
                //     var v2 = moment(v);
                //     if (v2.isBefore(v1)) {
                //         $('#date-alert').show();
                //         validity.valid = false;
                //     } else {
                //         $('#date-alert').hide();
                //     }
                // }
            }
        }).submit(function() {
            $('input[name=json_data]').val(JSON.stringify(that.cacheData))
            return true; // return false to cancel form action
        });

        //加载缓存内容
        $(window).on("unload", function(){
            if( !_global_data.activity.id) {
                var body = that.cacheData.activity.body
                that.cacheData.activity.body = null
                Util.storageSet(__c_activity_publish_cookie_key_activity, JSON.stringify(that.cacheData))
                Util.storageSet(__c_activity_publish_cookie_key_editor, body)
            }
        });

        //监听活动地点位置变更
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
        },
        //活动暂存
        save: function () {
            var that = this;
            //保存草稿
            that.cacheData.activity.status = 0;
        },
        //活动发布
        publish: function () {
            var that = this;
            //保存正式发布
            that.cacheData.activity.status = 1;
        },
    }

})