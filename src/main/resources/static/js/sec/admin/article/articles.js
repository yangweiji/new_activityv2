$(function () {
    //动态计算主要区域宽度
    /* $("div.manage-r").width(window.screen.width - 100);*/

    //dataTables部分
    var t = $('#bmTable')
        .on('init.dt', function () {

        }).on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        }).on('xhr.dt', function (e, settings, json, xhr) {
            $('#articleCount').text(json.length);
            Util.loading(false);
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
                    title: '内容记录',
                    exportOptions: {
                        columns: [2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15]
                    },
                    modifier: {
                        search: 'none'
                    },
                    format: {},
                    filename: '内容记录'
                },
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }

            ],
            ajax: {
                "url": "/sec/admin/article/getArticles",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        category: $("#category").val().trim(),
                        title: $("#title").val().trim()
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {
                    "data": "action", "width": "120px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑内容"><i class="am-icon-edit"></i></button>'
                            + '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除内容"><i class="am-icon-trash-o"></i></button>'
                    }
                },
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "title", "width": "200px"},
                {"data": "publish_time"},
                {
                    "data": "status", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == -1) {
                            return "禁用"
                        } else if (data == 0) {
                            return "草稿"
                        } else if (data == 1) {
                            return "发布"
                        }
                    }
                },
                {"data": "unit"},
                {
                    "data": "category", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 1) {
                            return "公告通知"
                        }
                        else if (data == 2) {
                            return "赛事新闻"
                        }
                        else if (data == 3) {
                            return "运动指南"
                        }
                    }
                },
                {
                    "data": "community_id", "width": "50px", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 0) {
                            return "平台发布"
                        } else {
                            return "团体发布"
                        }
                    }
                },
                {"data": "summary"},

                {"data": "body"},
                {"data": "avatar"},
                {"data": "created"},
                {"data": "created_by"},
                {"data": "modified"},
                {"data": "modified_by"}
            ],
            //定义指定的栏
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                {targets: [0, 1, 2, 3, 4, 5, 6, -1], visible: true},
                {targets: "_all", visible: false}
            ],
            //默认排序
            order: [[4, 'desc']],
            autoWidth: false,
            scrollX: true,
            //推迟渲染
            deferRender: true
        });
    //添加索引号
    t.on('order.dt search.dt', function () {
        t.column(1, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        })
    })

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        t.ajax.reload();
    })

    /**
     * 添加内容
     * @type {{className: string, action: $.fn.dataTable.ext.buttons.create.action}}
     */
    $.fn.dataTable.ext.buttons.create = {
        className: '',
        action: function (e, dt, node, config) {
            location.href = "/sec/admin/article/article";
        }
    }

    /**
     * 编辑内容
     */
    $("#bmTable tbody").on('click', 'button#editrow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/admin/article/article?id=" + data.id;
    })

    /**
     * 删除内容
     */
    $("#bmTable tbody").on('click', 'button#delrow', function () {
        var data = t.row($(this).parents('tr')).data()
        if (window.confirm("确定删除吗？")) {
            $.ajax({
                type: 'post',
                dataType: 'json',
                url: '/sec/admin/article/deleteArticle',
                data: {
                    articleId: data.id
                },
                success: function (data) {
                    if (data) {
                        alert("操作成功！")
                        location.reload()
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