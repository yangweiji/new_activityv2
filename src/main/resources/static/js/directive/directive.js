//图片URL
Vue.directive('translator', {

    bind: function (el, binding, vnode) { //1-被绑定
        // console.log("1-bind 被绑定");
        // console.log("el:",el);
        // console.log("binding:",binding);
        // console.log("vnode:",vnode);
        // el.style.color=binding.value;
    },
    inserted: function (el, binding, vnode) { //2-被插入
        // console.log("2-inserted 被插入");
        var attr = el.attributes["data-url"];
        if (attr) {
            $.ajax({
                url: "/pub/images",
                type: "get",
                data: {fileId: el.attributes["data-url"].value},
                dataType: "text",
                success: function (data) {
                    el.src = data;
                },
            });
        }
    },
    update: function (el, binding, vnode) { //3-更新
        // console.log("3-update 更新");
        var attr = el.attributes["data-url"];
        if (attr) {
            $.ajax({
                url: "/pub/images",
                type: "get",
                data: {fileId: el.attributes["data-url"].value},
                dataType: "text",
                success: function (data) {
                    el.src = data;
                },
            });
        }
    },
    componentUpdated: function (el, binding, vnode) { //4-更新完成
        // console.log("4-componentUpdated 更新完成");
    },
    unbind: function (el, binding, vnode) { //5-解绑
        // console.log("5-unbind 解绑");
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
                    <button :id="getImageId" class="am-btn am-btn-primary am-btn-xs">本地上传图片</button>\
                    <a href="javascript:void(0)" id="from-library-btn" class="am-btn am-btn-primary am-btn-xs" v-on:click="material()" v-if="showLibrary()">素材库中选择</a>\
                    <br/>\
                    <span class="c-image-tip">{{descText}}</span>\
                </span>\
            </div>\
            \
            \
            <!-- 图片素材库 BEGIN -->\
            <div class="am-modal am-modal-no-btn" tabindex="-2" :id="getMaterialLibraryModel" v-if="showLibrary()">\
                <div class="am-modal-dialog">\
                    <div class="am-modal-hd">素材库图片\
                        <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>\
                    </div>\
                    <div class="am-modal-bd">\
                        <div class="am-tabs" data-am-tabs>\
                            <ul class="am-tabs-nav am-nav am-nav-tabs">\
                                <li v-for="(category, index) in categories" v-bind:class="{\'am-active\': category.category==\'未分组\'}">\
                                    <a :href="\'#tab\' + index" v-on:click="switchCategory(category)">{{category.category}}</a>\
                                </li>\
                            </ul>\
                            <div class="am-tabs-bd">\
                                <div class="am-tab-panel am-fade am-in" v-bind:class="{\'am-active\': category.category==\'未分组\'}"  :id="\'tab\' + index" v-for="(category, index) in categories">\
                                    <div class="am-g dis_line">\
                                        <div class="am-g am-imglist">\
                                            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-2 am-avg-md-3 am-avg-lg-4 am-gallery-default">\
                                                <li v-for="(item, index) in images">\
                                                <div class="am-gallery-item am_list_block">\
                                                    <a href="javascript:void(0)" v-on:click="selectImage(item)"><img v-translator class="am_img animated c-img" :data-url="item.name" alt=""/></a>\
                                                </div>\
                                                </li>\
                                            </ul>\
                                        </div>\
                                    </div>\
                                </div>\
                                <ul class="am-pagination am-pagination-centered" v-if="pageCount>1">\
                                    <li v-bind:class="{\'am-disabled\': pageIndex==0}">\
                                        <a href="#" v-on:click="getItems(category, pageIndex-1)">&laquo;</a>\
                                    </li>\
                                    \
                                    <li v-bind:class="{\'am-active\': pageIndex==0}">\
                                        <a href="#" v-on:click="getItems(category, 0)">1</a>\
                                    </li>\
                                    \
                                    <li v-if="pageIndex>=offset">\
                                        <span>...</span>\
                                    </li>\
                                    \
                                    <li v-for="(item,index) in mArr" v-bind:class="{\'am-active\': pageIndex==(item-1)}">\
                                        <a href="#" v-on:click="getItems(category, (item-1))">{{item}}</a>\
                                    </li>\
                                    \
                                    <li v-if="pageIndex<(pageCount-offset)">\
                                        <span>...</span>\
                                    </li>\
                                    \
                                    <li v-bind:class="{\'am-active\': pageIndex==(pageCount-1)}">\
                                        <a href="#" v-on:click="getItems(category, (pageCount-1))">{{pageCount}}</a>\
                                    </li>\
                                    \
                                    <li v-bind:class="{\'am-disabled\': pageIndex==(pageCount-1)}">\
                                        <a href="#" v-on:click="getItems(category, pageIndex+1)">&raquo;</a>\
                                    </li>\
                                </ul>\
                            </div>\
                        </div>\
                    </div>\
                </div>\
            </div>\
            <!-- 图片素材库 END -->\
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
        },
        selectFromLibrary: {
            type: Boolean,
            default: true
        }
    },
    data: function () {
        return {
            //素材库
            images: [],
            //素材库图片分类数组
            categories: [],
            //默认的分组
            category: '未分组',

            //当前页索引
            pageIndex: 0,
            //总记录数
            totalCount: 0,
            //总页数
            pageCount: 0,
            //每页记录数
            pageSize: 12,
            //分页首尾固定数
            offset: 4,
            //动态页码
            mArr: [],
        }
    },
    methods: {
        showLibrary: function () {
            return (this.selectFromLibrary === 'false') ? false : true;
        },
        material: function () {
            var that = this;

            //打开素材库
            var $modal = $('#' + that.getMaterialLibraryModel);
            $modal.modal({width: 800, height: 600});

            //获取素材库图片分类
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterialCatgories',
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                data: JSON.stringify({}),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.categories = data;
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });

            that.pageIndex = 0;
            //获取素材库图片: 默认未分组下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: JSON.stringify({
                    category: that.category,
                    index: that.pageIndex,
                    pageSize: that.pageSize
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.totalCount = data.totalCount;
                        that.images = data.items;
                        that.pageCount = that.getPageCount(that.totalCount);
                        that.mArr = that.getMArray();
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        },
        //从素材库中选择图片
        selectImage: function (e) {
            var that = this;
            that.$emit('input', e.name);
            //关闭素材库选择图片对话框
            var $modal = $('#' + that.getMaterialLibraryModel);
            $modal.modal('close');
        },
        //切换素材库图片的分组选项卡
        switchCategory: function (e) {
            var that = this;
            that.category = e.category;
            that.pageIndex = 0;

            //获取素材库图片:指定分类下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: JSON.stringify({
                    category: that.category,
                    index: that.pageIndex,
                    pageSize: that.pageSize
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.totalCount = data.totalCount;
                        that.images = data.items;
                        that.pageCount = that.getPageCount(that.totalCount);
                        that.mArr = that.getMArray();
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        },
        //分页获取
        getItems: function (e, index) {
            var that = this;
            that.pageIndex = index;
            //获取素材库图片:指定分类下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: JSON.stringify({
                    category: that.category,
                    index: that.pageIndex,
                    pageSize: that.pageSize
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.totalCount = data.totalCount;
                        that.images = data.items;
                        that.pageCount = that.getPageCount(that.totalCount);
                        that.mArr = that.getMArray();
                        console.log("pageIndex: ", that.pageIndex, " pageCount: ", that.pageCount, " mArr: " + that.mArr);
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        },
        getPageCount: function (totalCount) {
            var i = totalCount % this.pageSize;
            if (i == 0) {
                return totalCount / this.pageSize;
            }
            else {
                return Math.ceil(totalCount / this.pageSize);
            }
        },
        getMArray: function () {
            var that = this;
            var arr = [];
            if (that.offset < 3) {
                //最小为3
                that.offset = 3;
            }
            if (that.pageCount <= that.offset + 1) {
                for (var i = 1; i <= that.pageCount - 2; i++) {
                    arr.push(i + 1);
                }
                return arr;
            }

            if (that.pageIndex < that.offset) {
                for (var i = 1; i <= that.offset; i++) {
                    arr.push(i + 1);
                }
            }
            else if (that.pageIndex >= that.pageCount - that.offset) {
                for (var i = that.pageCount - that.offset; i <= that.pageCount - 1; i++) {
                    arr.push(i);
                }
            }
            else {
                for (var i = that.pageIndex; i < that.pageIndex + 3; i++) {
                    arr.push(i);
                }
            }
            return arr;
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
        },
        getMaterialLibraryModel: function () {
            return "material-library-modal-" + this.nameText;
        },

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
                var b = that.saveToLibrary === "false" ? false : true;
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


//富文本组件
Vue.component('quill-text', {
    template: '\
        <div>\
            <div :id="getEditorId"></div>\
            <input :value="value" :id="nameText" :name="nameText" style="display: none;" type="text"/>\
            <div :id="getQuillId">\
            </div>\
            \
            \
            <!-- Vue 图片选择来源 BEGIN -->\
            <div class="am-modal am-modal-no-btn" tabindex="-1" :id="getImageSelectModal">\
                <div class="am-modal-dialog">\
                    <div class="am-modal-hd">选择图片\
                        <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>\
                        <hr/>\
                    </div>\
                    <div class="am-modal-bd" style="margin-top: 10px; margin-bottom: 20px;">\
                        <a href="javascript:void(0);" class="am-btn am-btn-primary" v-on:click="upload()">本地上传图片</a>\
                        <a href="javascript:void(0);" class="am-btn am-btn-primary" v-on:click="material()">素材库中选择</a>\
                    </div>\
                </div>\
            </div>\
            <!-- Vue 图片选择来源 END -->\
            \
            \
            <!-- 图片素材库 BEGIN -->\
            <div class="am-modal am-modal-no-btn" tabindex="-2" :id="getMaterialLibraryModel">\
                <div class="am-modal-dialog">\
                    <div class="am-modal-hd">素材库图片\
                        <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>\
                    </div>\
                    <div class="am-modal-bd">\
                        <div class="am-tabs" data-am-tabs>\
                            <ul class="am-tabs-nav am-nav am-nav-tabs">\
                                <li v-for="(category, index) in categories" v-bind:class="{\'am-active\': category.category==\'未分组\'}">\
                                    <a :href="\'#tab\' + index" v-on:click="switchCategory(category)">{{category.category}}</a>\
                                </li>\
                            </ul>\
                            <div class="am-tabs-bd">\
                                <div class="am-tab-panel am-fade am-in" v-bind:class="{\'am-active\': category.category==\'未分组\'}" :id="\'tab\' + index" v-for="(category, index) in categories">\
                                    <div class="am-g dis_line">\
                                        <div class="am-g am-imglist">\
                                            <ul data-am-widget="gallery" class="am-gallery am-avg-sm-2 am-avg-md-3 am-avg-lg-4 am-gallery-default">\
                                                <li v-for="(item, index) in images">\
                                                <div class="am-gallery-item am_list_block">\
                                                    <a href="javascript:void(0)" v-on:click="selectImage(item)"><img v-translator class="am_img animated c-img" :data-url="item.name" alt=""/></a>\
                                                </div>\
                                                </li>\
                                            </ul>\
                                        </div>\
                                    </div>\
                                </div>\
                                <ul class="am-pagination am-pagination-centered" v-if="pageCount>1">\
                                    <li v-bind:class="{\'am-disabled\': pageIndex==0}">\
                                        <a href="#" v-on:click="getItems(category, pageIndex-1)">&laquo;</a>\
                                    </li>\
                                    \
                                    <li v-bind:class="{\'am-active\': pageIndex==0}">\
                                        <a href="#" v-on:click="getItems(category, 0)">1</a>\
                                    </li>\
                                    \
                                    <li v-if="pageIndex>=offset">\
                                        <span>...</span>\
                                    </li>\
                                    \
                                    <li v-for="(item,index) in mArr" v-bind:class="{\'am-active\': pageIndex==(item-1)}">\
                                        <a href="#" v-on:click="getItems(category, (item-1))">{{item}}</a>\
                                    </li>\
                                    \
                                    <li v-if="pageIndex<(pageCount-offset)">\
                                        <span>...</span>\
                                    </li>\
                                    \
                                    <li v-bind:class="{\'am-active\': pageIndex==(pageCount-1)}">\
                                        <a href="#" v-on:click="getItems(category, (pageCount-1))">{{pageCount}}</a>\
                                    </li>\
                                    \
                                    <li v-bind:class="{\'am-disabled\': pageIndex==(pageCount-1)}">\
                                        <a href="#" v-on:click="getItems(category, pageIndex+1)">&raquo;</a>\
                                    </li>\
                                </ul>\
                            </div>\
                        </div>\
                    </div>\
                </div>\
            </div>\
            <!-- 图片素材库 END -->\
        </div>\
        ',
    props: {
        nameText: {
            type: String,
            default: ''
        },
        value: {
            type: String,
            default: ''
        },
        descText: {
            type: String,
            default: ''
        },
        saveToLibrary: {
            type: Boolean,
            default: true
        }
    },
    data: function () {
        return {
            //素材库
            images: [],
            //素材库图片分类数组
            categories: [],
            //默认的分组
            category: '未分组',

            //当前页索引
            pageIndex: 0,
            //总记录数
            totalCount: 0,
            //总页数
            pageCount: 0,
            //每页记录数
            pageSize: 12,
            //分页首尾固定数
            offset: 4,
            //动态页码
            mArr: [],

            //图像回传
            imageHandleCallback: null,
            //文本控件
            quill: null,
        }
    },
    methods: {
        //从本地上传图片
        upload: function () {
            var that = this;
            //本地图片上传文件触发事件
            $('#' + that.getEditorId).click();

            //关闭当前图片选择对话框
            var $modal = $('#' + that.getImageSelectModal);
            $modal.modal('close'); //关闭对话框
        },
        //从素材库中选择
        material: function () {
            var that = this;

            //打开素材库
            var $modal = $('#' + that.getMaterialLibraryModel);
            $modal.modal({width: 800, height: 600});

            //关闭当前图片选择对话框
            var $modal = $('#' + that.getImageSelectModal);
            $modal.modal('close');

            //获取素材库图片分类
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterialCatgories',
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                data: JSON.stringify({}),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.categories = data;
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });

            that.pageIndex = 0;
            //获取素材库图片: 默认未分组下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: JSON.stringify({
                    category: that.category,
                    index: that.pageIndex,
                    pageSize: that.pageSize
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.totalCount = data.totalCount;
                        that.images = data.items;
                        that.pageCount = that.getPageCount(that.totalCount);
                        that.mArr = that.getMArray();
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        },
        //从素材库中选择图片
        selectImage: function (e) {
            var that = this;
            if (that.imageHandleCallback) {
                var url = OssUrl + '/activity/' + e.name;
                var range = that.quill.getSelection()
                if (!range) {
                    range = {index: 0}
                }
                that.quill.clipboard.dangerouslyPasteHTML(range.index, "<img src='" + url + "' />")
            }

            var $modal = $('#' + that.getMaterialLibraryModel);
            $modal.modal('close');
        },
        //切换素材库图片的分组选项卡
        switchCategory: function (e) {
            var that = this;
            that.category = e.category;
            that.pageIndex = 0;
            //获取素材库图片:指定分类下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: JSON.stringify({
                    category: that.category,
                    index: that.pageIndex,
                    pageSize: that.pageSize
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.totalCount = data.totalCount;
                        that.images = data.items;
                        that.pageCount = that.getPageCount(that.totalCount);
                        that.mArr = that.getMArray();
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        },
        //分页获取
        getItems: function (e, index) {
            var that = this;
            that.pageIndex = index;
            //获取素材库图片:指定分类下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',
                dataType: "json",
                data: JSON.stringify({
                    category: that.category,
                    index: that.pageIndex,
                    pageSize: that.pageSize
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.totalCount = data.totalCount;
                        that.images = data.items;
                        that.pageCount = that.getPageCount(that.totalCount);
                        that.mArr = that.getMArray();
                        console.log("pageIndex: ", that.pageIndex, " pageCount: ", that.pageCount, " mArr: " + that.mArr);
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        },
        getPageCount: function (totalCount) {
            var i = totalCount % this.pageSize;
            if (i == 0) {
                return totalCount / this.pageSize;
            }
            else {
                return Math.ceil(totalCount / this.pageSize);
            }
        },
        getMArray: function () {
            var that = this;
            var arr = [];
            if (that.offset < 3) {
                //最小为3
                that.offset = 3;
            }
            if (that.pageCount <= that.offset + 1) {
                for (var i = 1; i <= that.pageCount - 2; i++) {
                    arr.push(i + 1);
                }
                return arr;
            }

            if (that.pageIndex < that.offset) {
                for (var i = 1; i <= that.offset; i++) {
                    arr.push(i + 1);
                }
            }
            else if (that.pageIndex >= that.pageCount - that.offset) {
                for (var i = that.pageCount - that.offset; i <= that.pageCount - 1; i++) {
                    arr.push(i);
                }
            }
            else {
                for (var i = that.pageIndex; i < that.pageIndex + 3; i++) {
                    arr.push(i);
                }
            }
            return arr;
        }
    },
    computed: {
        getEditorId: function () {
            return "c-upload-" + this.nameText + "-editor";
        },
        getQuillId: function () {
            return "c-" + this.nameText + "-editor";
        },
        getImageSelectModal: function () {
            return "image-select-modal-" + this.nameText;
        },
        getMaterialLibraryModel: function () {
            return "material-library-modal-" + this.nameText;
        }
    },
    mounted: function () {
        var that = this;
        ///活动详情：富文本控件
        // var quill = null;
        // var imageHandleCallback;
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
                    that.imageHandleCallback = callback;
                    //弹出图片选择器
                    var $modal = $('#' + that.getImageSelectModal);
                    $modal.modal({width: 400,});
                }
            }
        };

        //构建文本控件
        that.quill = new Quill('#' + that.getQuillId, {
            modules: {
                toolbar: toolbarOptions
            },
            placeholder: '请在此输入活动详情',
            theme: 'snow'  // or 'bubble'
        });

        //显示活动详情
        if (that.value) {
            that.quill.clipboard.dangerouslyPasteHTML(that.value);
        }
        //活动详情内容变更
        that.quill.on('editor-change', function (eventName) {
            if (eventName === 'text-change') {
                // args[0] will be delta
            } else if (eventName === 'selection-change') {
                // args[0] will be old range
            }

            // var bodyInput = $('#'+that.nameText);
            // bodyInput.val(that.quill.getText());
            // bodyInput.trigger('change')

            //向父级赋值
            that.$emit('input', that.quill.getHtml());
        });

        Quill.prototype.getHtml = function () {
            var html = this.container.querySelector('.ql-editor').innerHTML;
            // console.log("html: ", html);
            return html;
        };

        //本地上传图片
        Util.file.uploader({
            randomName: true,
            selectId: that.getEditorId,
            success: function (file) {
                var fileName = file.randomName;
                if (that.imageHandleCallback) {
                    var url = Util.file.downloadUrl(fileName)
                    var range = that.quill.getSelection()
                    if (!range) {
                        range = {index: 0}
                    }
                    that.quill.clipboard.dangerouslyPasteHTML(range.index, "<img src='" + url + "' />")
                }

                //文件保存至素材库
                var b = that.saveToLibrary === "false" ? false : true;
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

//用户信息
Vue.component('user-info', {
    template: '<!-- 用户详细信息 begin -->\n' +
        '    <div class="am-modal am-modal-no-btn" tabindex="-1" id="userinfo-modal">\n' +
        '        <div class="am-modal-dialog">\n' +
        '            <div class="am-modal-hd">\n' +
        '                <div style="margin: 0 auto;width: 65px;height: 65px;"><img style="width: 100%;border-radius: 50%" v-translator :data-url="userInfo.avatar" /></div>\n' +
        '                {{userInfo.displayname}}\n' +
        '                <a href="javascript: void(0)" class="am-close am-close-spin" data-am-modal-close>&times;</a>\n' +
        '            </div>\n' +
        '\n' +
        '            <div class="am-modal-bd">\n' +
        '                <div class="am-g dis_line">\n' +
        '                    <table class="am-table am-table-striped am-table-compact am-table-hover" id="userintoTable" cellspacing="0"\n' +
        '                           width="100%">\n' +
        '                        <tbody>\n' +
        '                        <tr>\n' +
        '                            <td>用户账号</td>\n' +
        '                            <td><span>{{userInfo.username}}</span></td>\n' +
        '                            <td>显示名称</td>\n' +
        '                            <td><span>{{userInfo.displayname}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>手机号码</td>\n' +
        '                            <td><span>{{userInfo.mobile}}</span></td>\n' +
        '                            <td>微信昵称</td>\n' +
        '                            <td><span>{{userInfo.nickName}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>性别</td>\n' +
        '                            <td><span>{{userInfo.gender==1?"男":"女"}}</span></td>\n' +
        '                            <td>用户姓名</td>\n' +
        '                            <td><span>{{userInfo.realName}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>创建时间</td>\n' +
        '                            <td><span>{{userInfo.created}}</span></td>\n' +
        '                            <td>会员年份</td>\n' +
        '                            <td><span>{{userInfo.level}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>身份证号</td>\n' +
        '                            <td><span>{{userInfo.idCard}}</span></td>\n' +
        '                            <td>是否认证</td>\n' +
        '                            <td><span>{{userInfo.isReal?\'是\':\'否\'}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>微信账号</td>\n' +
        '                            <td><span>{{userInfo.wechatId}}</span></td>\n' +
        '                            <td>认证时间</td>\n' +
        '                            <td><span>{{userInfo.realTime}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>电子邮箱</td>\n' +
        '                            <td><span>{{userInfo.email}}</span></td>\n' +
        '                            <td>家庭地址</td>\n' +
        '                            <td><span>{{userInfo.address}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>血型</td>\n' +
        '                            <td><span>{{userInfo.bloodType}}</span></td>\n' +
        '                            <td>T恤尺寸</td>\n' +
        '                            <td><span>{{userInfo.clothingSize}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>紧急联系人</td>\n' +
        '                            <td><span>{{userInfo.emergencyContactName}}</span></td>\n' +
        '                            <td>紧急联系方式</td>\n' +
        '                            <td><span>{{userInfo.emergencyContactMobile}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>工作单位</td>\n' +
        '                            <td><span>{{userInfo.workCompany}}</span></td>\n' +
        '                            <td>是否党员</td>\n' +
        '                            <td><span>{{userInfo.isParty?\'是\':\'否\'}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>职业</td>\n' +
        '                            <td><span>{{userInfo.occupation}}</span></td>\n' +
        '                            <td></td>\n' +
        '                            <td></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>平台角色</td>\n' +
        '                            <td><span>{{userInfo.role}}</span></td>\n' +
        '                            <td>是否启用</td>\n' +
        '                            <td><span>{{userInfo.enabled?\'是\':\'否\'}}</span></td>\n' +
        '                        </tr>\n' +
        '                        <tr>\n' +
        '                            <td>unionId</td>\n' +
        '                            <td><span>{{userInfo.unionId}}</span></td>\n' +
        '                            <td>openId</td>\n' +
        '                            <td><span>{{userInfo.openId}}</span></td>\n' +
        '                        </tr>\n' +
        '                        </tbody>\n' +
        '                    </table>\n' +
        '                </div>\n' +
        '                <hr data-am-widget="divider" style="" class="am-divider am-divider-default"/>\n' +
        '                <div class="am-g">\n' +
        '                    <button type="button" class="am-btn am-btn-secondary js-modal-close" data-am-modal-close>\n' +
        '                        关闭\n' +
        '                    </button>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '    <!-- 用户详细信息 end -->',
    props: {
        value: {
            type: String,
            default: ''
        }
    },
    data: function () {
        return {
            userInfo: {}
        }
    },
    watch: {
        // 如果 `question` 发生改变，这个函数就会运行
        value: function (nv, ov) {
            let that = this;
            $.ajax({
                cache: true,
                type: "POST",
                url: '/sec/admin/user/getUserInfo',
                data: JSON.stringify({ username: nv }),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.userInfo = data;
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
    },
    mounted: function () {
    },
    computed: {
    },
    methods: {
    }
});