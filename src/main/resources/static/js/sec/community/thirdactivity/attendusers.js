$(function () {
    // 动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

    //定义表栏位
    var _columns = [
        {"data": "id"},
        {"data": "id"},
        {"data": "title"},
        {"data": "real_name"},
        {"data": "mobile"},
        {"data": "ticket_title"},
        {"data": "activity_price"},
        {"data": "score"},
        {"data": "attend_time"},
        {"data": "check_in_time"},
        {
            "data": "status", "defaultContent": "",
            render: function (data, type, row) {
                if (data == 0) {
                    return "不抽签"
                } else if (data == 1) {
                    return "待抽签"
                } else if (data == 2) {
                    return "中签"
                } else if (data == 3) {
                    return "已申请退费"
                } else if (data == 4) {
                    return "已完成退费"
                }
            }
        },
        {"data": "after_files", "defaultContent:": "",
            render: function(data, type, row) {
                if(!data) {
                    return '';
                }

                var html = '';
                var arr = data.split(',');
                for (var i = 0; i < arr.length; i ++) {
                    var url = OssUrl+'/activity/'+arr[i];
                    html += '<a target="_blank" href="'+url+'" title="点击查看大图"><img class="img_thumb" src="'+url+'"></a>'
                }
                return html;
            }
        },
        {"data": "after_time"}
    ];
    //添加动态栏位
    g_attendColumns.forEach(function (value) {
        //依据data值检查 {"姓名":"胡冲","手机":"13910438997","昵称":"胡冲","邮件":"chong.hu@9kylin.cn","性别":"男","血型":"O","T恤尺寸":"XL","工作单位":"北京九麒信息技术有限公司","职业":"IT经理","紧急联系人姓名":"九麒","紧急联系人电话":"18610806245","是否党员":"党员","家庭地址":"北京市朝阳区八里庄西里99","微信号":"cf_hc1980","身份证号":"421023198008080050","出生日期":null,"备注":null,"图像上传":"yGaEts4JWf.jpg,sFbXcfTSRd.jpg,FSkDxBKchS.jpg"}
        _columns.push({
            "data": value, "defaultContent": "",
            render: function (data, type, row) {
                if (!data) {
                    return '';
                }

                if (data.toLowerCase().indexOf(".jpg") >= 0 || data.toLowerCase().indexOf(".png") >= 0 || data.toLowerCase().indexOf(".gif") >= 0) {
                    //图片类型
                    var html = '';
                    var arr = data.split(',');
                    for (var i = 0; i < arr.length; i ++) {
                        var url = OssUrl+'/activity/'+arr[i];
                        html += '<a target="_blank" href="'+url+'" title="点击查看大图"><img class="img_thumb" src="'+url+'"></a>'
                    }
                    return html;
                }

                return data;
            }
        });
    });

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
            // $('#bmBody').show();
            //检查当前团体是否可以在线退款，如果有则添加操作按钮
            if (g_community_context.canRefund) {
                t.button().add(3, {
                    extend: 'refund',
                    text: '申请退款',
                    enabled: false,
                    // action: function ( e, dt, button, config ) {
                    //     dt.ajax.reload();
                    // },
                });
                t.button().add(4, {
                    extend: 'check',
                    text: '检查退款',
                    enabled: false,
                    // action: function ( e, dt, button, config ) {
                    //     dt.ajax.reload();
                    // },
                });
            }
        })
        .on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        })
        .on('xhr.dt', function (e, settings, json, xhr) {
            // for ( var i=0, ien=json.aaData.length ; i<ien ; i++ ) {
            //     json.aaData[i].sum = json.aaData[i].one + json.aaData[i].two;
            // }
            // Note no return - manipulate the data directly in the JSON object.
            $('#totalCount').text(json.length);
            Util.loading(false);
        }).DataTable({
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
                    extend: 'delete',
                    text: '删除',
                    enabled: false,
                },
                {
                    extend: 'excel',
                    text: '导出Excel',
                    title: '报名签到记录',
                    exportOptions: {
                        // columns: ':visible'
                        // columns: [
                        //     1,2,3,4,5,6,7,8
                        // ],
                        columns: ':not(:eq(0))',//jquery to exclude column 0
                        modifier: {
                            search: 'none',//ignore search
                        },
                        format: {
                            body: function (data, row, column, node) {
                                if (data && data.length >= 15) {
                                    return ("\u200C" + data);
                                }
                                else {
                                    return data;
                                }
                            }
                        }
                    },
                    filename: '报名签到记录',
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
                "url": "/sec/community/thirdactivity/getAttendUsers",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        start: $("#start").val().trim(),
                        end: $("#end").val().trim(),
                        activityId: $("#activityId").val().trim(),
                        title: $("#title").val().trim(),
                        real_name: $("#real_name").val().trim(),
                        mobile: $("#mobile").val().trim(),
                        ticket_title: $("#ticket_title").val().trim(),
                        other_info: $("#other_info").val().trim(),
                        status: $("#status").val().trim(),
                        checked:$("#checked").is(":checked")
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: _columns,

            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: [0, 1]
                },
                {
                    targets: 0,
                    className: 'noVis',
                    render: function (data, type, row, meta) {
                        return '<input type="checkbox" id="checkbox" class="icheckbox_minimal childcheck"  value="' + data + '" />';
                    },
                },
                {targets: [0, 1, 2, 3, 4, 5, 6, 7, 8, 10], visible: true},
                {targets: '_all', visible: false}
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

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        // t.ajax.reload(null, false);
        t.ajax.reload();
    });

    //定义扩展按钮
    //中签处理
    $.fn.dataTable.ext.buttons.approve = {
        className: '',
        action: function (e, dt, node, config) {
            var d = [];
            $('.childcheck:checked').each(function () {
                d.push($(this).val());
            });

            if (d.length == 0) {
                alert("至少选择一项记录！");
                return;
            }

            if (window.confirm("提示：【不抽签】、【已申请退款】、【已完成退款】状态的用户报名记录无法进行中签操作，请确认？")) {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: '/sec/community/thirdactivity/approve',
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
                        if (data) {
                            nativeToast({
                                message: '操作成功！',
                                position: 'center',
                                timeout: 3000,
                                square: true,
                                type: 'success'
                            });
                            t.ajax.reload();
                        }
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
        }
    };

    //取消中签处理
    $.fn.dataTable.ext.buttons.cancel = {
        className: '',
        action: function (e, dt, node, config) {
            var d = [];
            $('.childcheck:checked').each(function () {
                d.push($(this).val());
            });

            if (d.length == 0) {
                alert("至少选择一项记录！");
                return;
            }

            if (window.confirm("提示：【不抽签】、【已申请退款】、【已完成退款】状态的用户报名记录无法进行取消中签操作，请确认？")) {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: '/sec/community/thirdactivity/cancel',
                    data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                    contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                    dataType: "json",
                    async: false,
                    beforeSend: function () {
                        // 禁用按钮防止重复提交
                        t.button(1).enable(false);
                        Util.loading(true);
                    },
                    success: function (data) {
                        if (data) {
                            nativeToast({
                                message: '操作成功！',
                                position: 'center',
                                timeout: 3000,
                                square: true,
                                type: 'success'
                            });
                            t.ajax.reload();
                        }
                    },
                    complete: function () {
                        $("#all_checked").prop("checked", false);
                        t.button(1).enable(true);
                        Util.loading(false);
                    },
                    error: function (data) {
                        console.info("error: " + data.responseText);
                    }
                });
            }
        }
    };

    /***
     * 删除报名记录
     * @type {{className: string, action: $.fn.dataTable.ext.buttons.approve.action}}
     */
    $.fn.dataTable.ext.buttons.delete = {
        className: '',
        action: function (e, dt, node, config) {
            var d = [];
            $('.childcheck:checked').each(function () {
                d.push($(this).val());
            });

            if (d.length == 0) {
                alert("至少选择一项记录！");
                return;
            }

            if (window.confirm("请确认删除？")) {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: '/sec/community/thirdactivity/deleteAttendUsers',
                    data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                    contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                    dataType: "json",
                    async: false,
                    beforeSend: function () {
                        // 禁用按钮防止重复提交
                        t.button(2).enable(false);
                        Util.loading(true);
                    },
                    success: function (data) {
                        if (data) {
                            nativeToast({
                                message: '操作成功！',
                                position: 'center',
                                timeout: 3000,
                                square: true,
                                type: 'success'
                            });
                            t.ajax.reload();
                        }
                    },
                    complete: function () {
                        $("#all_checked").prop("checked", false);
                        t.button(2).enable(true);
                        Util.loading(false);
                    },
                    error: function (data) {
                        console.info("error: " + data.responseText);
                    }
                });
            }
        }
    };

    //申请退款
    $.fn.dataTable.ext.buttons.refund = {
        className: '',
        action: function (e, dt, node, config) {
            var d = [];
            $('.childcheck:checked').each(function () {
                d.push($(this).val());
            });

            if (d.length == 0) {
                alert("至少选择一项记录！");
                return;
            }
            if (confirm("请检查选择的退费人员是否正确，退费操作不可逆，请确定是否继续退费")) {
                $.ajax({
                    cache: true,
                    type: "POST",
                    url: '/sec/community/thirdactivity/refund',
                    data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                    contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                    dataType: "json",
                    async: false,
                    beforeSend: function () {
                        // 禁用按钮防止重复提交
                        t.button(3).enable(false);
                        Util.loading(true);
                    },
                    success: function (data) {
                        if (data) {
                            alert("申请退款成功" + data + "人，请稍后检查退款是否成功");
                            //location.reload();
                            t.ajax.reload();
                        }
                    },
                    complete: function () {
                        $("#all_checked").prop("checked", false);
                        t.button(3).enable(true);
                        Util.loading(false);
                    },
                    error: function (data) {
                        console.info("error: " + data.responseText);
                    }
                });
            }
        }
    };

    //检查退款
    $.fn.dataTable.ext.buttons.check = {
        className: '',
        action: function (e, dt, node, config) {
            var d = [];
            $('.childcheck:checked').each(function () {
                d.push($(this).val());
            });

            if (d.length == 0) {
                alert("至少选择一项记录！");
                return;
            }

            $.ajax({
                cache: true,
                type: "POST",
                url: '/sec/community/thirdactivity/checkrefund',
                data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                beforeSend: function () {
                    // 禁用按钮防止重复提交
                    t.button(4).enable(false);
                    Util.loading(true);
                },
                success: function (data) {
                    $("#all_checked").prop("checked", false);
                    if (data) {
                        alert("检查退款成功" + data + "人");
                        //location.reload();
                        t.ajax.reload();
                    }

                    Util.loading(false);
                },
                complete: function () {
                    $("#all_checked").prop("checked", false);
                    t.button(4).enable(true);
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        }
    };



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
    $modal.siblings('.am-btn').on('click', function (e) {
        var $target = $(e.target);
        if (($target).hasClass('js-modal-open')) {
            $modal.modal();
        } else if (($target).hasClass('js-modal-close')) {
            $modal.modal('close');
        } else {
            $modal.modal('toggle');
        }
    });

    /**
     * 全选、全取消
     */
    $("#all_checked").click(function () {
        var check = $(this).prop("checked");
        $(".icheckbox_minimal").prop("checked", check);
        t.button(0).enable(check === true);
        t.button(1).enable(check === true);
        t.button(2).enable(check === true);
        t.button(3).enable(check === true);
        t.button(4).enable(check === true);
    });

    /**
     * 监听checkbox
     */
    $('#bmTable tbody').on('click', 'input#checkbox', function () {
        var d = [];
        $('.childcheck:checked').each(function () {
            d.push($(this).val());
        });

        t.button(0).enable(d.length > 0);
        t.button(1).enable(d.length > 0);
        t.button(2).enable(d.length > 0);
        t.button(3).enable(d.length > 0);
        t.button(4).enable(d.length > 0);
    });
});

new Vue({
    el: '#app',
    data: function () {
        return {
            communityContext: g_community_context,
        }
    },
    mounted: function () {
    },
    methods: {}
});
