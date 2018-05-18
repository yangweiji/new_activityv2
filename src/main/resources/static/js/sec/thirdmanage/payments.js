$(function () {
    // 动态计算主要区域宽度
  /*  $("div.manage-r").width(window.screen.width - 100);*/

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
            // $('#bmBody').show();
        })
        .on('preXhr.dt', function ( e, settings, data ) {
            Util.loading(true);
        })
        .on('xhr.dt', function ( e, settings, json, xhr ) {
            $('#totalCount').text(json.length);
            Util.loading(false);
        }).DataTable({
            language: {
                url: "/json/chinese.json",
                buttons: {
                    colvis: '显示栏位',
                }
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
                },
                {
                    extend: 'colvis',
                    // postfixButtons: [ 'colvisRestore' ],
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }
            ],
            // processing: true,
            // serverSide: true,
            ajax: {
                "url": "/sec/thirdmanage/getPayments",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        start: $("#start").val().trim(),
                        end: $("#end").val().trim(),
                        title: $("#title").val().trim(),
                        username: $("#username").val().trim(),
                        real_name: $("#real_name").val().trim(),
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "30px"},
                {"data": "username"},
                {"data": "real_name"},
                {"data": "price"},
                {"data": "pay_time"},
                {"data": "body"},
                {"data": "activity_title", "width": "100px"},
                {"data": "ticket_title", "width": "80px"},
                {"data": "status", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 2) {
                            return "完成";
                        }
                        else if (data == 1) {
                            return "未付";
                        }
                        else {
                            return "取消";
                        }
                    }},
                {"data": "created"},
            ],
            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0,
                },
                {
                    targets: -1, visible: false
                },
            ],
            //默认排序
            order: [[1, 'desc']],
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

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        // t.ajax.reload(null, false);
        t.ajax.reload();
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
