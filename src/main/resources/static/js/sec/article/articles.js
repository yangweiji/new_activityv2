$(function () {

    $.fn.dataTable.ext.buttons.create = {
        className: 'buttons-create',

        action: function ( e, dt, node, config ) {
            location.href = "/sec/article/pubarticle";
        }
    };
    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },
        //当处理大数据时，延迟渲染数据，有效提高Datatables处理能力
        deferRender: true,
        //指定每页显示10行数据
        iDisplayLength:10,
        columns: [
            {"data":"no"},
            {"data": "id"},
            {"data": "title"},
            {"data": "publish_time"},
            {"data": "category"},
            {"data": "status"},
            {"data":"community_id"},
            {"data":"action"},
        ],
        // dom: 'Blfrtip',
        dom: '<"top">Bfrt<"bottom">lip<"clear">',
        buttons: [
            {
                extend: 'create',
                text: '添加'
            },
            {
                extend: 'excel',
                text: '导出Excel',
                // charset: 'utf8',
                title: '内容记录',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        1,2,3,4,5,6
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '内容记录',
            }
        ],
        autoWidth: false,
        // select: true,
        // autoFill: {
        //     columns: ':not(:first-child)'
        // },

        //栏定义
        columnDefs: [{
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
        //默认排序
        order: [[3, 'desc']]
    });

    //添加索引序号
   t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();


    //删除
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        if (window.confirm("请确认删除？"))
        {
            location.href = "/sec/article/deleteArticle/"+data.id;
        }
    });

   //编辑
    $('#bmTable tbody').on('click', 'button#editrow', function () {
            var data = t.row( $(this).parents('tr') ).data();
            location.href = "/sec/article/article_update/" + data.id;
    });
});

new Vue({
    el: '#app',
    data: {
    },
    mounted: function () {
        var that = this;
    },
    methods: {
    },
});