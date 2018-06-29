$(function () {

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
                {
                    extend: 'checkOrder',
                    text: '检查付款',
                    enabled: false,
                },
                {
                    extend: 'excel',
                    text: '导出Excel',
                    // charset: 'utf8',
                    title: '订单',
                    exportOptions: {
                        // columns: ':visible'
                        // columns: [
                        //     1,2,3,4,5,6,7,8,9,10,11
                        // ],
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
                "url": "/sec/community/thirdmanage/getPayments",
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
                        extenal_id: $('#extenal_id').val().trim(),
                        status: $('#status').val().trim(),
                        refund_trade_no: $('#refund_trade_no').val().trim(),
                        refund_status: $('#refund_status').val().trim(),
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                { "data": "id" },
                { "data": "id" },
                {"data": "username"},
                {"data": "real_name"},
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
                            return "取消";
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
                    targets: [0,1]
                },
                {
                    targets: 0,
                    className: 'noVis',
                    render: function (data, type, row, meta) {
                        return '<input type="checkbox" id="checkbox" class="icheckbox_minimal childcheck"  value="' + data + '" />';
                    },
                },
                {
                    targets: [-1,-2,-3,-4,-5], visible: false
                },
            ],
            //默认排序
            order: [[1, 'desc']],
            autoWidth: false,
            scrollX: true,
            deferRender: true,
        });

    //检查付款
    $.fn.dataTable.ext.buttons.checkOrder = {
        className: '',
        action: function (e, dt, node, config) {
            var d = [];
            $('.childcheck:checked').each(function(){
                d.push($(this).val());
            });

            if (d.length == 0)
            {
                alert("至少选择一项记录！");
                return;
            }

            $.ajax({
                cache: true,
                type: "POST",
                url: '/sec/community/thirdactivity/checkOrder',
                data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                beforeSend: function () {
                    // 禁用按钮防止重复提交
                    t.button(0).enable(false);
                    Util.loading(true);
                },
                success: function (data) {
                    $("#all_checked").prop("checked", false);
                    if (data) {
                        alert("检查付款成功订单 " + data + " 条记录！");
                        t.ajax.reload();
                    }
                    else {
                        alert("订单未支付！");
                    }
                    Util.loading(false);
                },
                complete: function () {
                    $("#all_checked").prop("checked", false);
                    t.button(0).enable(true);
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        }
    };

    // //添加索引序号
    // t.on('order.dt search.dt', function () {
    //     t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
    //         cell.innerHTML = i + 1;
    //     });
    // }).draw();

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        // t.ajax.reload(null, false);
        t.ajax.reload();
    });

    /**
     * 全选、全取消
     */
    $("#all_checked").click(function () {
        var check = $(this).prop("checked");
        $(".icheckbox_minimal").prop("checked", check);
        t.button(0).enable(check === true);
    });


    /**
     * 监听checkbox
     */
    $('#bmTable tbody').on('click', 'input#checkbox', function () {
        var d = [];
        $('.childcheck:checked').each(function(){
            d.push($(this).val());
        });

        t.button(0).enable(d.length > 0);
    } );
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
