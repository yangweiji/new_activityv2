$(function () {
    // 动态计算主要区域宽度
    $("div.manage-r").width(window.screen.width - 100);

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmusers')
        .on('init.dt', function () {
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
                extend: 'excel',
                text: '导出Excel',
                title: '会员',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21
                    ],
                    modifier: {
                        search: 'none'
                    },
                    format: {
                        body: function ( data, row, column, node ) {
                            //身份证号格式化
                            return column === 7 ? ("\u200C" + data) : data;
                        }
                    }
                },
                filename: '会员',
            },
            {
                extend: 'colvis',
                // postfixButtons: [ 'colvisRestore' ],
                columns: ':not(.noVis)',
                collectionLayout: 'fixed three-column'
            }
        ],
        ajax: {
            "url": "/sec/user/searchUsers",
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
                    level: $("#level").val().trim(),
                    isMember: 1
                };
                return JSON.stringify(param);
            },
            "dataSrc": ""
        },
        columns: [
            {"data": "id", "width": "30px"},
            {"data": "id", "width": "50px"},
            {"data": "username"},
            {"data": "displayname"},
            {"data": "created"},
            {"data": "real_name", "width": "80px"},
            {"data": "total_score", "width": "30px"},
            {"data": "gender", "defaultContent": "",
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
                }},
            {"data": "id_card"},
            {"data": "is_real", "defaultContent": "",
                render: function (data, type, row) {
                    if (data == true) {
                        return "是";
                    }
                    else {
                        return "否";
                    }
                }},
            {"data": "real_time"},
            {"data": "level"},
            {"data": "email"},
            {"data": "work_company"},
            {"data": "is_party", "defaultContent": "",
                render: function (data, type, row) {
                    if (data == true) {
                        return "是";
                    }
                    else {
                        return "否";
                    }
                }},
            {"data": "address"},
            {"data": "blood_type", "defaultContent": "",
                render: function (data, type, row) {
                    return data;
                }},
            {"data": "clothing_size", "defaultContent": "",
                render: function (data, type, row) {
                    return data;
                }},
            {"data": "occupation"},
            {"data": "emergency_contact_name"},
            {"data": "emergency_contact_mobile"},
            {"data": "wechat_id"},
        ],

        //栏定义
        columnDefs: [
            {
                searchable: false,
                orderable: false,
                targets: 0
            },
            // {
            //     targets:[8],//身份证号的导出处理
            //     render: function(data){
            //         return "\u200C" + data ;
            //     }
            // },
            {
                targets: [1, 9, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21], visible: false
            }
        ],
        //默认排序
        order: [[4, 'desc']],
        autoWidth: false,
        scrollX: true,
        // scrollY: '50vh',
        // scrollCollapse: true,
        deferRender: true,
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
            tab: 'members'
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