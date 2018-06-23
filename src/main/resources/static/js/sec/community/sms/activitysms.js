$(function () {
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
                /* {
                     extend: 'create',
                     text: '添加'
                 },*/
                {
                    extend: 'excel',
                    text: '导出Excel',
                    title: '短信消息',
                    exportOptions: {
                        // columns: ':visible'
                        columns: [
                            1, 2, 3, 4, 5, 6, 7, 8, 9
                        ],
                        modifier: {
                            search: 'none'
                        }
                    },
                    filename: '短信消息',
                },
                {
                    extend: 'colvis',
                    // postfixButtons: [ 'colvisRestore' ],
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed three-column'
                }
            ],
            ajax: {
                "url": "/sec/community/sms/getActivitySms",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                    //查询条件参数
                    var param = {
                        templateName: $("#templateName").val().trim()
                    };
                    return JSON.stringify(param);
                },
                "dataSrc": ""
            },
            columns: [
                {"data": "id", "width": "30px"},
                {"data": "id", "width": "50px"},
                {"data": "template_name"},
                {"data": "message_content"},
                {"data": "displayname"},
                {"data": "title"},
                {"data": "send_time"},
                {"data": "template_code"},
                {"data": "send_result_code"},
                {"data": "send_result_desc"}
            ],

            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0,
                },
                {targets: [0, 1, 2, 3, 4, 5, 6, 7], visible: true},
                {targets: '_all', visible: false}
                // {
                //     targets:[8],//身份证号的导出处理
                //     render: function(data){
                //         return "\u200C" + data ;
                //     }
                // },
                /* {
                     targets: [7,8,9,10,11,12,13,14,15,16,17,18,19,20], visible: false
                 },*/
            ],
            //默认排序
            "order": [[6, 'desc']],
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
});

new Vue({
    el: '#app',
    data: function () {
        return {
            tab: 'users'
        };
    },
    mounted: function () {
        var that = this;
    },
    methods: {
        go: function (url, event) {
            location.href = url;
        },
    }
})
