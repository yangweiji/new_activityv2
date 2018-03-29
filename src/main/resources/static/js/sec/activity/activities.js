$(function () {

    $.fn.dataTable.ext.buttons.create = {
        className: 'buttons-create',

        action: function ( e, dt, node, config ) {
            // alert( this.text() );
            location.href = "/sec/activity/publish?type=1";
        }
    };

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },
        "columns": [
            {"data": "no"},
            {"data": "id"},
            {"data": "title"},
            {"data": "start_time"},
            {"data": "end_time"},
            // {"data": "created"},
            {"data": "attend_user_count"},
            {"data": "check_user_count"},
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
                title: '活动记录',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        1,2,3,4,5,6
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '活动记录',
            }
        ],

        autoWidth: false,
        // select: true,
        // autoFill: {
        //     columns: ':not(:first-child)'
        // },

        //栏定义
        "columnDefs": [{
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
        //默认排序
        "order": [[3, 'desc']],
    });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();

    //编辑
    $('#bmTable tbody').on('click', 'button#editrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        // var fields = $("#add-form").serializeArray();
        // jQuery.each( fields, function(i, field){
        //     //jquery根据name属性查找
        //     $(":input[name='"+field.name+"']").val(data[i]);
        // });
        // $(":input[name='mark']").val("edit");
        // $("#modal-form").modal("show");//弹出框show
        location.href = "/sec/activity/publish?id="+data.id;
    });

    //删除
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        if (window.confirm("请确认删除？"))
        {
            location.href = "/sec/activity/deleteActivity/"+data.id;
        }
    });

    //二维码
    $('#bmTable tbody').on('click', 'button#qrcoderow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        // location.href = "/sec/activity/qrcode?id="+data.id;
        window.open("/sec/activity/qrcode?id="+data.id);
    });

    //报名签到
    $('#bmTable tbody').on('click', 'button#attendrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        // location.href = "/sec/activity/attendusers?activityId="+data.id+"&title="+data.title;
        window.open("/sec/activity/attendusers?activityId="+data.id+"&title="+data.title);
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