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
                    title: '素材记录',
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5, 6]
                    },
                    modifier: {
                        search: 'none'
                    },
                    format: {},
                    filename: '素材记录'
                },
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }

            ],
            ajax: {
                "url": "/sec/admin/material/getItems",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "name", "width": "100px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<img class="img_thumb" src="'+OssUrl+'/activity/'+data+'">'
                    }
                },
                {"data": "name", "width": "100px"},
                {"data": "created"},
                {"data": "category"},
                {"data": "sequence"},
                {
                    "data": "action", "width": "120px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑内容"><i class="am-icon-edit"></i></button>'
                            + '<button id="delrow" style="width: 42px" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除内容"><i class="am-icon-trash-o"></i></button>'

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
            location.href = "/sec/admin/material/material";
        }
    }

    /**
     * 编辑内容
     */
    $("#bmTable tbody").on('click', 'button#editrow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/admin/material/material?id=" + data.id;
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
                url: '/sec/admin/material/delete',
                data: {
                    id: data.id
                },
                success: function (data) {
                    if (data) {
                        alert("操作成功！");
                        location.reload();
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