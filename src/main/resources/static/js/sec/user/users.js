$(function () {
    $.fn.dataTable.ext.buttons.create = {
        className: 'buttons-create',

        action: function (e, dt, node, config) {
            // alert( this.text() );
            location.href = "/sec/user/create";
        }
    };

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },
        columns: [
            {"data": "no"},
            {"data": "id"},
            {"data": "username"},
            {"data": "displayname"},
            {"data": "created"},
            {"data": "total_score"},
            {"data": "real_name"},
            {"data": "gender"},
            {"data": "id_card"},
            {"data": "is_real"},
            {"data": "real_time"},
            {"data": "email"},
            {"data": "work_company"},
            {"data": "is_party"},
            {"data": "address"},
            {"data": "blood_type"},
            {"data": "clothing_size"},
            {"data": "occupation"},
            {"data": "emergency_contact_name"},
            {"data": "emergency_contact_mobile"},
            {"data": "wechat_id"},
            {"data": "action"},
        ],
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
                title: '用户',
                exportOptions: {
                    // columns: ':visible'
                    // columns: [
                    //     0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20
                    // ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '用户',
            }
        ],

        //栏定义
        columnDefs: [
            {
                "searchable": false,
                "orderable": false,
                "targets": 0,
            },
            {
                targets:[8],//身份证号的导出处理
                render: function(data){
                    return "\u200C" + data ;
                }
            },
            {
                "targets": [6,7,8,9,10,11,12,13,14,15,16,17,18,19,20],
                "visible": false
            },
        ],
        //默认排序
        "order": [[4, 'desc']],
    });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

    //删除
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row($(this).parents('tr')).data();
        if (window.confirm("请确认删除？")) {
            location.href = "/sec/user/delete/" + data.id;
        }
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
