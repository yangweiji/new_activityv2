$(function () {

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
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
                    extend: 'excel',
                    text: '导出Excel',
                    // charset: 'utf8',
                    title: '打卡统计',
                    exportOptions: {
                        // columns: ':visible'
                        columns: [
                            1, 2, 3, 4
                        ],
                        // columns: ':not(:eq(-1))',//jquery to exclude column -1
                        modifier: {
                            search: 'none'
                        },
                    },
                    filename: '打卡统计',
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
                "url": "/sec/community/thirdactivity/getCheckInData",
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
                        displayname: $("#displayname").val().trim(),
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {data: "id", width: "30px", defaultContent: ""},
                {data: "username", width: "100px"},
                {data: "displayname"},
                {data: "real_name"},
                {data: "count", width: "100px"},
                // {
                //     data: "action", width: "200px", defaultContent: "",
                //     render: function (data, type, row) {
                //         return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title=""><i class="am-icon-edit"></i></button>'
                //     }
                // },
            ],

            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                // {targets: [0, 1, 2, 3], visible: true},
                {targets: '_all', visible: true}
            ],
            //默认排序
            order: [[4, 'desc']],
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
});

new Vue({
    el: '#app',
    data: {},
    mounted: function () {
        var that = this;
    },
    methods: {},
});