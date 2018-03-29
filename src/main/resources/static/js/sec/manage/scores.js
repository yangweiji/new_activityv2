$(function () {

    $.fn.dataTable.ext.buttons.create = {
        className: 'buttons-create',

        action: function ( e, dt, node, config ) {
            // alert( this.text() );
            location.href = "/sec/manage/score";
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
            {"data": "username"},
            {"data": "displayname"},
            {"data": "score"},
            {"data": "created"},
            {"data": "memo"},
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
                title: '积分',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        2,3,4,5,6,7
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '积分',
            }
        ],
        // select: true,
        // autoFill: {
        //     columns: ':not(:first-child)'
        // },

        //栏定义
        "columnDefs": [
            // {
            //     "targets": -1,//编辑和删除
            //     "data": null,
            //     "render": function ( data, type, row, meta ) {
            //         return "<button id='editrow' class='am-btn am-btn-primary' type='button' v-on:click='edit("+data+")'><i class='am-icon-edit'></i></button>" +
            //             "<button id='delrow' class='am-btn am-btn-danger' type='button' v-on:click='delete("+data+")'><i class='am-icon-trash-o'></i></button>"
            //     },
            // // "defaultContent": "<button id='editrow' class='am-btn am-btn-primary' type='button'><i class='am-icon-edit'></i></button>" +
            // // "<button id='delrow' class='am-btn am-btn-danger' type='button'><i class='am-icon-trash-o'></i></button>"
            // },
            {
                "targets": 0,
                "searchable": false,
                "orderable": false,

            },
            {
                "targets": 1,
                "visible": false
            }
        ],
        //默认排序
        "order": [[6, 'desc']],
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
        location.href = "/sec/manage/score/"+data.id;
    });

    //删除
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        if (window.confirm("请确认删除？"))
        {
            location.href = "/sec/manage/deleteScore/"+data.id;
        }
    });
});

// //编辑
// function edit(id) {
//     alert(id);
// }

new Vue({
    el: '#app',
    data: {
    },
    mounted: function () {
    },
    methods: {
        create: function () {
            location.href = "/sec/manage/score";
        },
        edit: function (id) {
            alert("edit: " + id);
        },
        delete: function () {
            alert("delete");
        }
    }
});
