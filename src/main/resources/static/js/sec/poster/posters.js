$(function () {
    //动态计算主要区域宽度
    /* $("div.manage-r").width(window.screen.width - 100);*/

    //dataTables部分
    var t = $('#bmTable')
        .on('init.dt', function () {

        }).on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        }).on('xhr.dt', function (e, settings, json, xhr) {
            $('#posterCount').text(json.length);
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
                    title: '海报记录',
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5, 6, 7, 8, 9,10]
                    },
                    modifier: {
                        search: 'none'
                    },
                    format: {},
                    filename: '海报记录'
                },
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }

            ],
            ajax: {
                "url": "/sec/poster/getPosters",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        posterType:$("#posterType").val().trim(),
                        title: $("#title").val().trim()
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "title", "width": "200px"},
                {"data": "created"},
                {
                    "data": "poster_type", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == 1) {
                            return "活动海报"
                        }
                    }
                },
                {
                    "data": "show", "width": "50px", defaultContent: "",
                    render: function (data, type, row) {
                        if (data == true) {
                            return "是"
                        } else if (data == false) {
                            return "否"
                        }
                    }
                },
                {"data": "sequence"},
                {"data": "activityTitle"},
                {"data": "avatar"},
                {"data": "mobile_avatar"},
                {"data": "link"},
                {
                    "data": "action", "width": "80px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑内容"><i class="am-icon-edit"></i></button>'
                            + '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除内容"><i class="am-icon-trash-o"></i></button>'
                            + '<button id="showPoster" class="am-btn am-btn-sm am-btn-danger" type="button" title="隐藏海报"><i class="am-icon-edit"></i></button>'
                        +'<button id="displayPoster" class="am-btn am-btn-sm am-btn-secondary" type="button" title="显示海报"><i class="am-icon-edit"></i></button>'
                    }
                }
            ],
            //定义指定的栏
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                {targets: [0, 1, 2, 3, 4, 5, 6,7, -1], visible: true},
                {targets: "_all", visible: false}
            ],
            //默认排序
             order: [[6,'asc']],
            autoWidth: false,
            scrollX: true,
            //推迟渲染
            deferRender: true
        });
    //添加索引号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
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
            location.href = "/sec/poster/poster";
        }
    }

    /**
     * 编辑内容
     */
    $("#bmTable tbody").on('click', 'button#editrow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/poster/poster?id=" + data.id;
    })


    /**
     * 隐藏海报
     */
    $("#bmTable tbody").on('click', 'button#showPoster', function () {
        var data = t.row($(this).parents('tr')).data();
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: '/sec/poster/showPoster',
            data: {
                posterId: data.id
            },
            success: function (data) {
                if (data) {
                    location.reload()
                }
            }
        })
    })

    /**
     * 显示海报
     */
    $("#bmTable tbody").on('click', 'button#displayPoster', function () {
        var data = t.row($(this).parents('tr')).data();
        $.ajax({
            type: 'post',
            dataType: 'json',
            url: '/sec/poster/displayPoster',
            data: {
                posterId: data.id
            },
            success: function (data) {
                if (data) {
                    location.reload()
                }
            }
        })
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
                url: '/sec/poster/deletePoster',
                data: {
                    posterId: data.id
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