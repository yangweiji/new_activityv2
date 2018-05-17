$(function () {
    // 动态计算主要区域宽度
    $("div.manage-r").width(window.screen.width - 100);

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable')
        .on('init.dt', function () {
            // $('#bmBody').show();
        })
        .on('preXhr.dt', function ( e, settings, data ) {
            Util.loading(true);
        })
        .on('xhr.dt', function ( e, settings, json, xhr ) {
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
                    title: '相册记录',
                    exportOptions: {
                        // columns: ':visible'
                        columns: [
                            1, 2, 3, 4, 5, 6, 7
                        ],
                        // columns: ':not(:eq(-1))',//jquery to exclude column -1
                        modifier: {
                            search: 'none'
                        },
                    },
                    filename: '相册记录',
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
                "url": "/sec/thirdphotos/getActivityPhotos",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        description: $("#description").val().trim()
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "50px"},
                {"data": "id", "width": "80px"},
                {"data": "description","width": "160px"},
                {"data": "created"},
                {"data": "picture"},
                {"data": "activity_id"},
                {"data": "created_by"},
                {"data": "axtenal_url"},
                {"data": "action", "width": "160px", defaultContent: "",
                    render: function (data, type, row) {
                        return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑活动"><i class="am-icon-edit"></i></button>'
                            + '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除活动"><i class="am-icon-trash-o"></i></button>'
                    }},
            ],

            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                { targets: [0,1,2,3,-1], visible: true},
                { targets: '_all', visible: false }
            ],
            //默认排序
            order: [[2, 'desc']],
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

        action: function ( e, dt, node, config ) {
            // alert( this.text() );
            location.href = "/sec/thirdphotos/updateOraddphotos";
        }
    };

    /**
     * 编辑活动
     */
    $('#bmTable tbody').on('click', 'button#editrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        location.href = "/sec/thirdphotos/updateOraddphotos?id="+data.id;
    });

    /**
     * 删除活动
     */
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        if (window.confirm("请确认删除？"))
        {
            location.href = "/sec/thirdphotos/deleteActivityPhoto/"+data.id;
        }
    });
});

new Vue({
    el: '#app',
    data: {
    },
    mounted: function () {
        var that = this;
    },
    methods: {
    },
});