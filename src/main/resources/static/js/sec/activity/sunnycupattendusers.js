Util.loading(true);
$(function () {
    // 动态计算主要区域宽度
    $("div.manage-r").width(window.screen.width - 100);

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
            $('#bmBody').show();
            Util.loading(false);
        })
        .DataTable({
        language: {
            url: "/json/chinese.json",
            buttons: {
                colvis: '显示栏位',
            }
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
            {
                extend: 'approve',
                text: '中签',
                enabled: false,
            },
            {
                extend: 'cancel',
                text: '取消中签',
                enabled: false,
            },
            {
                extend: 'refund',
                text: '申请退款',
                enabled: false,
            },
            {
                extend: 'check',
                text: '检查退款是否成功',
                enabled: false,
            },
            {
                extend: 'excel',
                text: '导出Excel',
                // charset: 'utf8',
                title: '阳光杯马拉松报名签到记录',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        1,2,3,4,5,6,7,8,9,11,12,13,14,15,16,17,18,19,20,21
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '报名签到记录',
            },
            {
                extend: 'colvis',
                // postfixButtons: [ 'colvisRestore' ],
                columns: ':not(.noVis)'
            }
        ],

        //栏定义
        columnDefs: [
            {
                "searchable": false,
                "orderable": false,
                "targets": [0,1]
            },
            {
                targets: 0,
                className: 'noVis',
                render: function (data, type, row, meta) {
                    return '<input type="checkbox" id="checkbox" class="icheckbox_minimal childcheck"  value="' + data + '" />';
                },
            },
            // {
            //     targets: 10, //操作
            //     render: function(data, type, row, meta) {
            //         if (row[9] == '待审')
            //         {
            //             return '<button id="approve" type="button" class="am-btn am-btn-sm am-btn-secondary">中签</button>';
            //         }
            //         else if (row[9] == '中签') {
            //             return '<button id="cancel" type="button" class="am-btn am-btn-sm am-btn-danger">取消中签</button>';
            //         }
            //     }
            // },
            {
                targets: 17,//身份证号的导出处理
                render: function(data){
                    return "\u200C" + data ;
                }
            },
            { targets: [0,1,2,3,4,5,6,7,9], visible: true},
            { targets: '_all', visible: false }
        ],

        //默认排序
        order: [[7, 'desc']],
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

    //定义扩展按钮
    //中签处理
    $.fn.dataTable.ext.buttons.approve = {
        className: '',
        action: function (e, dt, node, config) {
            this.text( '<i class="fa fa-spinner fa-pulse"></i>' );
            this.enable(false);
            Util.loading(true);

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
                url: '/sec/activity/approve',
                data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        t.button(0).text( '中签' );
                        t.button(0).enable(true);
                        Util.loading(false);
                        alert("操作成功!");
                        location.reload();
                        // t.ajax.reload();
                    }
                }
            });
        }
    };
    //取消中签处理
    $.fn.dataTable.ext.buttons.cancel = {
        className: '',
        action: function (e, dt, node, config) {
            this.text( '<i class="fa fa-spinner fa-pulse"></i>' );
            this.enable(false);
            Util.loading(true);

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
                url: '/sec/activity/cancel',
                data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        // setTimeout(function(){
                        //     t.button(1).text( '取消中签' );
                        //     t.button(1).enable(true);
                        //     Util.loading(false);
                        // }, 3000);

                        t.button(1).text( '取消中签' );
                        t.button(1).enable(true);
                        Util.loading(false);
                        alert("操作成功!");
                        location.reload();
                        // t.ajax.reload();
                    }
                }
            });
        }
    };
    //申请退款
    $.fn.dataTable.ext.buttons.refund = {
        className: '',
        action: function (e, dt, node, config) {
            this.text( '<i class="fa fa-spinner fa-pulse"></i>' );
            this.enable(false);
            Util.loading(true);

            var d = [];
            $('.childcheck:checked').each(function(){
                d.push($(this).val());
            });

            if (d.length == 0)
            {
                alert("至少选择一项记录！");
                return;
            }
            if(confirm("请检查选择的退费人员是否正确，退费操作不可逆，请确定是否继续退费")) {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: '/sec/activity/refund',
                    data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                    contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                    dataType: "json",
                    async: false,
                    success: function (data) {
                        if (data) {
                            t.button(2).text( '申请退款' );
                            t.button(2).enable(true);
                            Util.loading(false);
                            alert("申请退款成功" + data + "人，请稍后检查退款是否成功");
                            location.reload();
                            // t.ajax.reload();
                        }
                    }
                });
            }
        }
    };
    //检查退款
    $.fn.dataTable.ext.buttons.check = {
        className: '',
        action: function (e, dt, node, config) {
            this.text( '<i class="fa fa-spinner fa-pulse"></i>' );
            this.enable(false);
            Util.loading(true);

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
                url: '/sec/activity/checkrefund',
                data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                success: function (data) {
                    if (data) {
                        t.button(3).text( '检查退款是否成功' );
                        t.button(3).enable(true);
                        Util.loading(false);
                        alert("检查退款成功" + data + "人");
                        location.reload();
                        // t.ajax.reload();
                    }
                }
            });
        }
    };

    //添加索引序号
    // t.on('order.dt search.dt', function () {
    //     t.column(1, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
    //         cell.innerHTML = i + 1;
    //     });
    // }).draw();

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

    //全选、全取消
    $("#all_checked").click(function () {
        var check = $(this).prop("checked");
        $(".icheckbox_minimal").prop("checked", check);
        t.button(0).enable(check === true);
        t.button(1).enable(check === true);
        t.button(2).enable(check === true);
        t.button(3).enable(check === true);
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
        t.button(1).enable(d.length > 0);
        t.button(2).enable(d.length > 0);
        t.button(3).enable(d.length > 0);
    } );

    /**
     * 中签操作
     */
    $('#bmTable tbody').on('click', 'button#approve', function () {
        var tr = $(this).parents('tr');
        //取得行值数组
        var d = t.row(tr).data();
        // console.log(d);

        var param = [];
        param.push(d[0]);
        $.ajax({
            cache: true,
            type: "POST",
            url: '/sec/activity/approve',
            data: JSON.stringify(param),// 指定请求的数据格式为json，实际上传的是json字符串
            contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
            dataType: "json",
            async: false,
            success: function (data) {
                console.log(data);
                if (data) {
                    var r = t.row(tr).data();

                    // t.ajax.reload(null, false);
                    //改变状态显示值
                    r[9] = '中签';
                    //行重绘
                    t.row(tr).data(r).draw();
                    alert("操作成功!");
                }
            }
        });
    } );


    /**
     * 取消中签操作
     */
    $('#bmTable tbody').on('click', 'button#cancel', function () {
        var tr = $(this).parents('tr');
        //取得行值数组
        var d = t.row(tr).data();
        // console.log(d);

        var param = [];
        param.push(d[0]);
        $.ajax({
            cache: true,
            type: "POST",
            url: '/sec/activity/cancel',
            data: JSON.stringify(param),// 指定请求的数据格式为json，实际上传的是json字符串
            contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
            dataType: "json",
            async: false,
            success: function (data) {
                console.log(data);
                if (data) {
                    var r = t.row(tr).data();

                    // t.ajax.reload(null, false);
                    //改变状态显示值
                    r[9] = '待审';
                    //行重绘
                    t.row(tr).data(r).draw();
                    alert("操作成功!");
                }
            }
        });
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