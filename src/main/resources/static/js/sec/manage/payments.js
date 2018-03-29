$(function () {

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable').DataTable({
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
                        1,2,3,4,5,6,7
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '订单',
            }
        ],
        // select: true,
        // autoFill: {
        //     columns: ':not(:first-child)'
        // },
        //栏定义
        "columnDefs": [
            {
                "targets": 0,
                "searchable": false,
                "orderable": false,

            }
        ],
        //默认排序
        "order": [[4, 'desc']],
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
