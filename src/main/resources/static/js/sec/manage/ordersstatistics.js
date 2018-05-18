Util.loading(true);
$(function () {
    // 动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
            $('#bmBody').show();
            Util.loading(false);
        })
        .DataTable({
        language: {
            url: "/json/chinese.json",
        },
        // dom: 'Blfrtip',
        dom: '<"top">Bfrt<"bottom">lip<"clear">',
        buttons: [
            // 'csv',
            {
                extend: 'excel',
                text: '导出Excel',
                // charset: 'utf8',
                title: '订单',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        1,2,3
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '订单',
            }
        ],
        columns: [
            {"data": "no", "width": "30px"},
            {"data": "activity_id", "width": "100px"},
            {"data": "title"},
            {"data": "amount",  "width": "100px"},
        ],
        //栏定义
        columnDefs: [
            {
                "targets": 0,
                "searchable": false,
                "orderable": false,

            }
        ],
        //默认排序
        order: [[3, 'desc']],
        autoWidth: false,
        scrollX: true,
        // scrollY: '50vh',
        // scrollCollapse: true,
        deferRender: true,
        // select: true,
        // autoFill: {
        //     columns: ':not(:first-child)'
        // },
    });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

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
