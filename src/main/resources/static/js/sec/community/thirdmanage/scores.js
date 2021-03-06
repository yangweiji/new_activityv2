$(function () {
    // 动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

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
                    extend: 'create',
                    text: '添加'
                },
                {
                    extend: 'excel',
                    text: '导出Excel',
                    title: '积分',
                    exportOptions: {
                        //columns: ':visible',
                        columns: [
                            2,3,4,5,6,7,8
                         ],
                       /* columns: ':not(:eq(0))',*///jquery to exclude column 0
                        modifier: {
                            search: 'none'
                        },
                        format: {
                            body: function ( data, row, column, node ) {
                                //身份证号的处理
                                return ("\u200C" + data);
                            }
                        }

                    },
                    filename: '积分',
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
                "url": "/sec/community/thirdmanage/getScores",
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
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "action", "width": "120px", "defaultContent": "",
                    render: function (data, type, row) {
                        return '<button id="btnEdit" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑"><i class="am-icon-edit"></i></button>'
                            +  '<button id="btnDelete" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除"><i class="am-icon-trash-o"></i></button>';
                    }},
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "title", "width": "120px"},
                {"data": "username"},
                {"data": "real_name"},
                {"data": "score", "width": "30px"},
                {"data": "created"},
                {"data": "memo"}
            ],


            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0,
                },
            ],
            //默认排序
            order: [[2, 'desc']],
            autoWidth: false,
            scrollX: true,
            // scrollY: '50vh',
            // scrollCollapse: true,
            deferRender: true,
        });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(1, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
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
     * 添加积分
     * @type {{className: string, action: $.fn.dataTable.ext.buttons.create.action}}
     */
    $.fn.dataTable.ext.buttons.create = {
        className: '',
        action: function ( e, dt, node, config ) {
            // alert( this.text() );
            location.href = "/sec/community/thirdmanage/createActivityScore";
        }
    };

    //编辑
    $('#bmTable tbody').on('click', 'button#btnEdit', function () {
        var data = t.row( $(this).parents('tr') ).data();
        location.href = "/sec/community/thirdmanage/editActivityScore/"+data.id;
    });

    //删除
    $('#bmTable tbody').on('click', 'button#btnDelete', function () {
        var data = t.row( $(this).parents('tr') ).data();
        if (window.confirm("请确认删除？"))
        {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: '/sec/community/thirdmanage/deleteActivityScore',
                data: {
                    id: data.id,
                },
                success: function (data) {
                    if (data) {
                        alert("操作成功！");
                        location.reload()
                    }
                },
            })
            // location.href = "/sec/thirdmanage/deleteActivityScore/"+data.id;
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