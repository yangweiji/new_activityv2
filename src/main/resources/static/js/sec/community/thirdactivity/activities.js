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
                    title: '活动记录',
                    exportOptions: {
                        // columns: ':visible'
                        columns: [
                            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13
                        ],
                        // columns: ':not(:eq(-1))',//jquery to exclude column -1
                        modifier: {
                            search: 'none'
                        },
                        format: {
                            body: function (data, row, column, node) {
                                //身份证号的处理
                                return ("\u200C" + data);
                            }
                        }
                    },
                    filename: '活动记录',
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
                "url": "/sec/community/thirdactivity/getActivities",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        start: $("#start").val().trim(),
                        end: $("#end").val().trim(),
                        status: $("#status").val().trim(),
                        tags: $("#tags").val().trim(),
                        title: $("#title").val().trim(),
                        id: $("#id").val().trim()
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "title", "width": "150px"},
                {"data": "start_time"},
                {"data": "end_time"},
                {"data": "attend_user_count"},
                {"data": "check_user_count"},
                {
                    "data": "status", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 1) {
                            return "发布";
                        }
                        else {
                            return "草稿";
                        }
                    }
                },
                {"data": "created"},
                {"data": "displayname"},
                {"data": "modified"},
                {"data": "modifiedbyname"},
                {
                    "data": "public", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == true) {
                            return "公开";
                        }
                        else {
                            return "非公开";
                        }
                    }
                },
                {"data": "unit"},
                {
                    "data": "action", "width": "200px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑活动"><i class="am-icon-edit"></i></button>'
                            + '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除活动"><i class="am-icon-trash-o"></i></button>'
                            + '<button id="qrcoderow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="活动二维码"><i class="fa fa-qrcode"></i></button>'
                            + '<button id="attendrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="活动报名签到"><i class="fa fa-user-o"></i></button>'
                            + '<button id="picturerow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="相册管理"><i class="am-icon-shield"></i></button>'
                    }
                },
            ],

            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                {targets: [0, 1, 2, 3, 4, 5, 6, 7, -1], visible: true},
                {targets: '_all', visible: false}
            ],
            //默认排序:活动开始时间降序排列
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

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        // t.ajax.reload(null, false);
        t.ajax.reload();
    });

    /**
     * 添加活动
     * @type {{className: string, action: $.fn.dataTable.ext.buttons.create.action}}
     */
    $.fn.dataTable.ext.buttons.create = {
        className: '',

        action: function (e, dt, node, config) {
            // alert( this.text() );
            location.href = "/pub/activity/publish";
        }
    };

    /**
     * 编辑活动
     */
    $('#bmTable tbody').on('click', 'button#editrow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/community/thirdactivity/publish?id=" + data.id;
    });

    /**
     * 删除活动
     */
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row($(this).parents('tr')).data();
        if (window.confirm("请确认删除？")) {
            location.href = "/sec/community/thirdactivity/deleteActivity/" + data.id;
        }
    });

    /**
     * 查看活动二维码
     */
    $('#bmTable tbody').on('click', 'button#qrcoderow', function () {
        var data = t.row($(this).parents('tr')).data();
        window.open("/sec/community/thirdactivity/qrcode?id=" + data.id);
    });

    /**
     * 活动报名签到情况
     */
    $('#bmTable tbody').on('click', 'button#attendrow', function () {
        var data = t.row($(this).parents('tr')).data();
        window.open("/sec/community/thirdactivity/attendusers?activityId=" + data.id + "&title=" + data.title + "&start=2018-01-01&end=");
    });

    /**
     * 相册管理
     */
    $('#bmTable tbody').on('click', 'button#picturerow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/community/thirdactivity/activityphotos?activityId=" + data.id;
    })
});

new Vue({
    el: '#app',
    data: {},
    mounted: function () {
        var that = this;
    },
    methods: {},
});