$(function () {
    //动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

    //dataTables部分
    var t = $("#bmTable")
        .on('init.dt', function () {

        }).on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        }).on('xhr.dt', function (e, settings, json, xhr) {
            $("#communityCount").text(json.length);
            Util.loading(false)
        }).DataTable({
            language: {
                url: "/json/chinese.json",
                buttons: {
                    colvis: '显示栏位'
                }
            },
            dom: '<"top">Bfrt<"bottom">lip<"clear">',
            buttons: [
                {
                    extend: 'create',
                    text: '添加'
                },
                {
                    extend: 'excel',
                    text: '导出Excel',
                    title: '团体记录',
                    exportOptions: {
                        columns: [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
                    },
                    modifier: {
                        search: 'none'
                    },
                    format: {},
                    filename: '团体记录'
                },
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }
            ],
            ajax: {
                "url": "/sec/admin/community/getCommunities",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        name: $("#name").val().trim()
                    };
                    return JSON.stringify(param)
                },
                "dataSrc": ""
            },
            columns: [
                {
                    "data": "action", "width": "120px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑团体"><i class="am-icon-edit"></i></button>' +
                            '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除团体"><i class="am-icon-trash-o"></i></button>'

                    }
                },
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "name", "width": "120px"},
                {"data": "created"},
                {"data": "contact"},
                {"data": "company", "width": "120px"},
                {"data": "control_name"},
                {"data": "business_license"},
                {"data": "manager_phone_number"},
                {"data": "count_people"},
                {
                    "data": "status", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 0) {
                            return "草稿"
                        } else if (data == 1) {
                            return "待审核"
                        } else if (data == 2) {
                            return "拒绝"
                        } else if (data == 9) {
                            return "审核通过"
                        }
                    }
                },
                {"data": "description"},
                {"data": "background"},
                {
                    "data": "using_score", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == true) {
                            return "开启"
                        } else if (data == false) {
                            return "关闭"
                        }
                    }
                },
                {"data": "avatar"},
                {
                    "data": "vip_amount", defaultContent: "",
                    render: function (data, type, row) {
                        if (data > 0) {
                            return "开启"
                        } else if (data == 0) {
                            return "关闭"
                        }
                    }
                },
                {"data": "created_by"},
                {"data": "address"},
                {"data": "about"}
            ],
            //定义指定的栏
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                {targets: [0, 1, 2, 3, 4, 5, 6, 7], visible: true},
                {targets: "_all", visible: false}
            ],
            //默认排序
            order: [[4, "desc"]],
            autoWidth: false,
            scrollX: true,
            //推迟渲染
            deferRender: true
        });
    //添加索引号
    t.on('order.dt search.dt', function () {
        t.column(1, {order: 'applied', search: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        })
    }).draw();

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        t.ajax.reload();
    })

    /**
     * 添加团体
     */
    $.fn.dataTable.ext.buttons.create = {
        className: "",
        action: function (e, dt, node, config) {
            location.href = "/sec/admin/community/community";
        }
    }

    /**
     * 编辑团体
     */
    $("#bmTable tbody").on('click', 'button#editrow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/admin/community/community?id=" + data.id;
    });

    /**
     * 删除团体
     */
    $("#bmTable tbody").on('click', 'button#delrow', function () {
        var data = t.row($(this).parents('tr')).data();
        if (window.confirm('确定删除吗？')) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: '/sec/admin/community/deleteCommunity',
                data: {
                    communityId: data.id
                },
                success: function (data) {
                    if (data == 0) {
                        alert("操作成功！")
                        location.reload()
                    }
                    else if (data == -1) {
                        alert("当前团体有关联活动，不能删除！")
                    }
                }
            })

        }
    })
})
new Vue({
    el: '#app',
    data: {},
    mounted: function () {
        var that = this;
    },
    methods: {},
});