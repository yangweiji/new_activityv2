new Vue({
    el: "#app",
    data: function () {
        return {
            material: _global_data,
            //素材库
            images: [],
            //素材库图片分类数组
            categories: [],
            //默认的分组
            category: '未分组',
        }
    },
    mounted: function () {
        var that = this;
        //获取素材库图片分类
        $.ajax({
            type: "POST",
            url: '/sec/admin/material/getMaterialCatgories',
            contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
            dataType: "json",
            data: JSON.stringify({
            }),
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

        //获取素材库图片: 默认未分组下的图片
        $.ajax({
            type: "POST",
            url: '/sec/admin/material/getMaterials',
            contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
            dataType: "json",
            data: JSON.stringify({
                category: that.category
            }),
            beforeSend: function () {
                Util.loading(true);
            },
            success: function (data) {
                if (data) {
                    that.images = data;
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
    methods: {
        //从素材库中选择图片
        selectImage: function (e) {
            var that = this;
            that.material.name = e.name;
            var $modal = $('#material-library-modal');
            $modal.modal('close');
        },
        switchCategory: function (e) {
            var that = this;
            //获取素材库图片:指定分类下的图片
            $.ajax({
                type: "POST",
                url: '/sec/admin/material/getMaterials',
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                data: JSON.stringify({
                    category: e.category
                }),
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.images = data;
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