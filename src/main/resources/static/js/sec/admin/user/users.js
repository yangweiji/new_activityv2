$(function () {
});

new Vue({
    el: '#app',
    data: function () {
        return {
            tab: 'users',
            userInfo: {},
        };
    },
    mounted: function () {
        var that = this;
        var $modal = $('#userinfo-modal');

        //自定义添加按钮事件
        $.fn.dataTable.ext.buttons.create = {
            className: '',
            action: function (e, dt, node, config) {
                // alert( this.text() );
                location.href = "/sec/admin/user/create";
            }
        };
        //This event is emitted by DataTables when a table is being initialised and is about to request data.
        // At the point of being fired the table will have its columns and features initialised,
        // but no data will have been loaded (either by Ajax, or reading from the DOM).
        $(document).on('preInit.dt', function (e, settings) {
            // var api = new $.fn.dataTable.Api( settings );
            // api.page.len( 100 );
        });

        //JQuery DataTables HTML (DOM) sourced data
        var t = $('#bmTable')
            .on('init.dt', function () {
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
                    //显示添加按钮
                    {
                        extend: 'create',
                        text: '添加'
                    },
                    {
                        extend: 'excel',
                        text: '导出Excel',
                        title: '用户',
                        exportOptions: {
                            // columns: ':visible'
                            columns: [
                                1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
                            ],
                            modifier: {
                                search: 'none'
                            },
                            format: {
                                body: function (data, row, column, node) {
                                    if (data && data.length > 15) {
                                        //身份证号格式化
                                        return ("\u200C" + data)
                                    }
                                }
                            }
                        },
                        filename: '用户',
                    },
                    {
                        extend: 'colvis',
                        // postfixButtons: [ 'colvisRestore' ],
                        columns: ':not(.noVis)',
                        collectionLayout: 'fixed three-column'
                    }
                ],
                ajax: {
                    "url": "/sec/admin/user/searchUsers",
                    "contentType": "application/json;charset=utf-8",
                    "type": "POST",
                    "data": function () {
                        //查询条件参数
                        var param = {
                            start: $("#start").val().trim(),
                            end: $("#end").val().trim(),
                            username: $("#username").val().trim(),
                            displayname: $("#displayname").val().trim(),
                            real_name: $("#real_name").val().trim(),
                            id_card: $("#id_card").val().trim(),
                            role: $("#role").val().trim()
                        };
                        return JSON.stringify(param);
                    },
                    "dataSrc": ""
                },
                columns: [
                    {"data": "id", "width": "30px"},
                    {"data": "id", "width": "50px"},
                    {"data": "username", "default": '',
                        render: function (data, type, row) {
                            return '<a id="view" href="javascript:void(0);">'+data+'</a>';
                        }
                    },
                    {"data": "displayname", "width:": "80px"},
                    {"data": "real_name", "width": "80px"},
                    {
                        "data": "gender", "defaultContent": "",
                        render: function (data, type, row) {
                            if (data == 1) {
                                return "男";
                            }
                            else if (data == 2) {
                                return "女";
                            }
                            else {
                                return "";
                            }
                        }
                    },
                    {"data": "id_card"},
                    {"data": "created"},
                    {
                        "data": "is_real", "defaultContent": "",
                        render: function (data, type, row) {
                            if (data == true) {
                                return "是";
                            }
                            else {
                                return "否";
                            }
                        }
                    },
                    {"data": "real_time"},
                    {"data": "email"},
                    {"data": "work_company"},
                    {
                        "data": "is_party", "defaultContent": "",
                        render: function (data, type, row) {
                            if (data == true) {
                                return "是";
                            }
                            else {
                                return "否";
                            }
                        }
                    },
                    {"data": "address"},
                    {
                        "data": "blood_type", "defaultContent": "",
                        render: function (data, type, row) {
                            return data;
                        }
                    },
                    {
                        "data": "clothing_size", "defaultContent": "",
                        render: function (data, type, row) {
                            return data;
                        }
                    },
                    {"data": "occupation"},
                    {"data": "emergency_contact_name"},
                    {"data": "emergency_contact_mobile"},
                    {"data": "wechat_id"},
                    {"data": "role"},  //平台角色
                    {"data": "total_score", "width": "30px"}, //积分
                    {
                        "data": "action", "width": "100px", "defaultContent": "",
                        render: function (data, type, row) {
                            return '<button id="btnEdit" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑用户"><i class="am-icon-edit"></i></button>'
                                + '<button id="btnDelete" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除用户"><i class="am-icon-trash-o"></i></button>';
                        }
                    },
                ],
                //栏定义
                columnDefs: [
                    {
                        searchable: false,
                        orderable: false,
                        targets: 0,
                    },
                    {targets: [0, 1, 2, 3, 4, 5, 6, 7, 9, -3, -2, -1], visible: true},
                    {targets: '_all', visible: false}
                ],
                //默认排序
                "order": [[1, 'desc']],
                "autoWidth": false,
                "scrollX": true,
                // "scrollY": '50vh',
                // "scrollCollapse": true,
                "deferRender": true,
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
         * 编辑
         */
        $('#bmTable tbody').on('click', 'button#btnEdit', function () {
            var data = t.row($(this).parents('tr')).data();
            location.href = "/sec/admin/user/update/" + data.id;
        });

        /**
         * 删除
         */
        $('#bmTable tbody').on('click', 'button#btnDelete', function () {
            var data = t.row($(this).parents('tr')).data();
            if (window.confirm("请确认删除？")) {
                location.href = "/sec/admin/user/delete/" + data.id;
            }
        });

        /**
         * 查看用户详情
         */
        $('#bmTable tbody').on('click', 'a#view', function () {
            var data = t.row($(this).parents('tr')).data();
            var d = { username: data.username };
            $.ajax({
                cache: true,
                type: "POST",
                url: '/sec/admin/user/getUserInfo',
                data: JSON.stringify(d),// 指定请求的数据格式为json，实际上传的是json字符串
                contentType: 'application/json;charset=utf-8',//指定请求的数据格式为json,这样后台才能用@RequestBody 接受java bean
                dataType: "json",
                async: false,
                beforeSend: function () {
                    Util.loading(true);
                },
                success: function (data) {
                    if (data) {
                        that.userInfo = data;
                        $modal.modal({width: 800, height: 600});
                    }
                },
                complete: function () {
                    Util.loading(false);
                },
                error: function (data) {
                    console.info("error: " + data.responseText);
                }
            });
        });
    },
    methods: {
        go: function (e) {
            console.log(e);
        }
    }
})
