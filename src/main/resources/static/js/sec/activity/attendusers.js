$(function () {

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },

        // 定位
        // l - Length changing 每页显示多少条数据选项
        // f - Filtering input 搜索框
        // t - The Table 表格
        // i - Information 表格信息
        // p - Pagination 分页按钮
        // r - pRocessing 加载等待显示信息
        // < and > - div elements 一个div元素
        // <"#id" and > - div with an id 指定id的div元素
        // <"class" and > - div with a class 指定样式名的div元素
        // <"#id.class" and > - div with an id and class 指定id和样式的div元素

        // dom: 'Blfrtip',
        dom: '<"top">Bfrt<"bottom">lip<"clear">',
        buttons: [
            // 'csv',
            {
                extend: 'excel',
                text: '导出Excel',
                // charset: 'utf8',
                title: '报名签到记录',
                exportOptions: {
                    // columns: ':visible'
                    // columns: [
                    //     1,2,3,4,5,6,7,8
                    // ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '报名签到记录',
            }
        ],

        //栏定义
        columnDefs: [
            {
                "searchable": false,
                "orderable": false,
                "targets": 0
            },
            {
                targets:'_all',//身份证号的导出处理
                render: function(data){
                    return "\u200C" + data ;
                }
            },
            { targets: [0, 1,2,3,4,5,6,7], visible: true},
            { targets: '_all', visible: false }
        ],

        //默认排序
        "order": [[6, 'desc']],
    });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

    //JQuery DataTables HTML (DOM) sourced data
    var t1 = $('#statTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },
        dom: 'f',
        //栏定义
        "columnDefs": [{
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
    });

    //添加索引序号
    t1.on('order.dt search.dt', function () {
        t1.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

    //定义模态窗口
    var $modal = $('#stat-modal');

    $modal.siblings('.am-btn').on('click', function(e) {
        var $target = $(e.target);
        if (($target).hasClass('js-modal-open')) {
            $modal.modal();
        } else if (($target).hasClass('js-modal-close')) {
            $modal.modal('close');
        } else {
            $modal.modal('toggle');
        }
    });
});


new Vue({
    el: '#app',
    data: {
    },
    mounted: function () {
    },
    methods: {

    }
});