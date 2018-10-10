$(function () {
    //动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

    //dataTables部分
    var t = $("#bmTable")
        .on('init.dt', function () {

        }).on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        }).on('xhr.dt', function (e, settings, json, xhr) {
            $("#trainingCampCount").text(json.length);
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
                    title: '训练营记录',
                    exportOptions: {
                        columns: [2, 3, 4, 5, 6]
                    },
                    modifier: {
                        search: 'none'
                    },
                    format: {},
                    filename: '训练营记录'
                },
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }
            ],
            ajax: {
                "url": "/sec/community/thirdactivity/searchTrainingCamp",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        campName: $("#campName").val().trim()
                    };
                    return JSON.stringify(param)
                },
                "dataSrc": ""
            },
            columns: [
                {
                    "data": "action", "width": "100px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑训练营"><i class="am-icon-edit"></i></button>' +
                            '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除训练营"><i class="am-icon-trash-o"></i></button>'
                    }
                },
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "camp_name", "width": "50px"},
                {"data": "camp_note","width": "100px"},
                {"data": "modified","width":"100px"},
                {"data": "realName","width":"100px"}
            ],
            //定义指定的栏
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                {targets: [0, 1, 2, 3, 4], visible: true},
                {targets: "_all", visible: false}
            ],
            //默认排序
            order: [[2, "asc"]],
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
    });

    /**
     * 添加训练营
     */
    $.fn.dataTable.ext.buttons.create = {
        className: "",
        action: function (e, dt, node, config) {
            location.href = "/sec/community/thirdactivity/trainingcamps";
        }
    };

    /**
     * 编辑训练营
     */
    $("#bmTable tbody").on('click', 'button#editrow', function () {
        var data = t.row($(this).parents('tr')).data();
        location.href = "/sec/community/thirdactivity/trainingcamps?id=" + data.id;
    });

    /**
     * 删除训练营
     */
    $("#bmTable tbody").on('click', 'button#delrow', function () {
        var data = t.row($(this).parents('tr')).data();
        if(window.confirm("请确认删除？")){
            location.href="/sec/community/thirdactivity/deleteTrainingCamp/"+data.id;
        }
    })
});

new Vue({
    el: '#app',
    data: {},
    mounted: function () {
        var that = this;
    },
    methods: {}
});