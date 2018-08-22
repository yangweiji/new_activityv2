//图片URL
Vue.directive('translator', {

    bind:function (el, binding, vnode) { //1-被绑定
        console.log("1-bind 被绑定");
        // console.log("el:",el);
        // console.log("binding:",binding);
        // console.log("vnode:",vnode);
        // el.style.color=binding.value;
    },
    inserted:function (el, binding, vnode) { //2-被插入
        console.log("2-inserted 被插入");
        $.ajax({
            url: "/pub/images",
            type: "get",
            data: { fileId: el.attributes["data-url"].value },
            dataType: "text",
            success: function(data) {
                el.src = data;
            },
        });
    },
    update:function (el, binding, vnode) { //3-更新
        console.log("3-update 更新");
        $.ajax({
            url: "/pub/images",
            type: "get",
            data: { fileId: el.attributes["data-url"].value },
            dataType: "text",
            success: function(data) {
                el.src = data;
            },
        });
    },
    componentUpdated:function (el, binding, vnode) { //4-更新完成
        console.log("4-componentUpdated 更新完成");
    },
    unbind:function (el, binding, vnode) { //5-解绑
        console.log("5-unbind 解绑");
    }

});

//全局组件
//图片上传组件
Vue.component('input-image-uploader', {
    template: '\
        <div>\
            <input type="hidden" :value="value" :id="nameText" :name="nameText" />\
            <div class="c-image">\
                <img :src="getImageUrl">\
                <span>\
                    <button :id="getImageId" class="am-btn am-btn-primary am-btn-xs">本地浏览</button>\
                    <a href="javascript:void(0)" id="from-library-btn" class="am-btn am-btn-primary am-btn-xs" v-on:click="selectImage()">从素材库中选择</a>\
                    <br/>\
                    <span class="c-image-tip">{{descText}}</span>\
                </span>\
            </div>\
        </div>\
        ',
    props: {
        nameText: {
            type: String,
            default: ''
        },
        value: {
            type: String,
            default: '/img/community/activity-avatar.png'
        },
        descText: {
            type: String,
            default: '温馨提示：背景图片尺寸建议为：900*525，图片小于4M'
        },
        saveToLibrary: {
            type: Boolean,
            default: true
        }
    },
    data: function () {
        return {}
    },
    methods: {
        selectImage: function () {
            var $modal = $('#material-library-modal');
            $modal.modal({
                width: 800,
                // height: 400
            });
        }
    },
    computed: {
        getImageUrl: function () {
            if (this.value) {
                return Util.file.downloadUrl(this.value)
            }
            else {
                return "/img/community/activity-avatar.png"
            }
        },
        getImageId: function () {
            return "c-upload-image-" + this.nameText
        }
    },
    mounted: function () {
        var that = this;
        //图片上传
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId: that.getImageId,
            success: function (file) {
                var fileName = file.randomName;
                that.$emit('input', file.randomName);
                //文件保存至素材库
                var b = that.saveToLibrary === "false" ? false: true;
                if (b) {
                    $.ajax({
                        type: "POST",
                        url: '/sec/admin/material/saveToLibrary',
                        data: {
                            name: fileName
                        },
                        dataType: "json",
                        async: false,
                        beforeSend: function () {
                            Util.loading(true);
                        },
                        success: function (data) {
                            if (data) {
                                nativeToast({
                                    message: '图片已保存至素材库！',
                                    position: 'center',
                                    timeout: 3000,
                                    square: true,
                                    type: 'success'
                                });
                            }
                        },
                        complete: function () {
                            Util.loading(false);
                        },
                        error: function (data) {
                            console.info("error: " + data.responseText);
                        }
                    });
                }
            }
        });
    }

});
