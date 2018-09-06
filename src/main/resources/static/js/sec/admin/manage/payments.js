$(function () {
    // 动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
            // $('#bmBody').show();
        })
        .on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        })
        .on('xhr.dt', function (e, settings, json, xhr) {
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
                        columns: ':not(:eq(0))',//jquery to exclude column 0
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
                "url": "/sec/admin/manage/getPayments",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        start: $("#start").val().trim(),
                        end: $("#end").val().trim(),
                        activityId: $("#activityId").val().trim(),
                        title: $("#title").val().trim(),
                        username: $("#username").val().trim(),
                        real_name: $("#real_name").val().trim(),
                        mobile: $("#mobile").val().trim(),
                        ticket_title: $("#ticket_title").val().trim(),
                        extenal_id: $('#extenal_id').val().trim(),
                        status: $('#status').val().trim(),
                        refund_trade_no: $('#refund_trade_no').val().trim(),
                        refund_status: $('#refund_status').val().trim(),
                        community_user: $('#community_user').val().trim(),
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "30px"},
                {"data": "name"},   //团体名称
                {"data": "username"},
                {"data": "real_name"},
                {"data": "mobile"},
                {"data": "price"},
                {"data": "pay_time"},
                {"data": "body"},
                {"data": "activity_title", width: "160px"},
                {"data": "ticket_title"},
                {"data": "status", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 2) {
                            return "完成";
                        }
                        else if (data == 1) {
                            return "未付";
                        }
                        else {
                            return "";
                        }
                    }},
                {"data": "created"},
                {"data": "extenal_id"},
                {"data": "refund_trade_no"},
                {"data": "refund_time"},
                {"data": "refund_status", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 1)
                        {
                            return "申请退款";
                        }
                        else if (data == 2)
                        {
                            return "完成退款";
                        }
                        else {
                            return "";
                        }
                    }},
            ],
            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0,
                },
                {
                    targets: [], visible: false
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
    data: {},
    mounted: function () {
    },
    methods: {}
});
