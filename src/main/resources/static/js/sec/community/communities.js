/*$(function () {

    $.fn.dataTable.ext.buttons.create = {
        className: 'buttons-create',

        action: function ( e, dt, node, config ) {
            location.href = "/sec/community/addcommunity";
        }
    };
    var t = $('#bmTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },
        //当处理大数据时，延迟渲染数据，有效提高Datatables处理能力
        deferRender: true,
        //指定每页显示10行数据
        iDisplayLength:10,
        columns: [
            {"data":"no"},
            {"data": "id"},
            {"data": "name"},
            {"data": "created"},
            {"data":"contact"},
            {"data": "company"},
            {"data": "status"},
            {"data":"action"}
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
                title: '内容记录',
                exportOptions: {
                    // columns: ':visible'
                    columns: [
                        1,2,3,4,5,6
                    ],
                    modifier: {
                        search: 'none'
                    }
                },
                filename: '内容记录',
            }
        ],
        autoWidth: false,
        // select: true,
        // autoFill: {
        //     columns: ':not(:first-child)'
        // },

        //栏定义
        columnDefs: [{
            "searchable": false,
            "orderable": false,
            "targets": 0
        }],
        //默认排序
        order: [[3,'desc']]
    });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();


    //删除
    $('#bmTable tbody').on('click', 'button#delrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        if (window.confirm("请确认删除？"))
        {
            location.href = "/sec/community/deleteCommunity/"+data.id;
        }
    });

    //编辑
    $('#bmTable tbody').on('click', 'button#editrow', function () {
        var data = t.row( $(this).parents('tr') ).data();
        location.href = "/sec/community/updatecommunity/" + data.id;
    });
});*/


$(function () {
    //动态计算主要区域宽度
    /*$("div.manage-r").width(window.screen.width - 100);*/

    //dataTables部分
    var t = $("#bmTable")
        .on('init.dt', function () {

        }).on('preXhr.dt', function (e, settings, data) {
            Util.loading(true);
        }).on('xhr.dt', function (e, settings, json, xhr) {
            $("#communityCount").text(json.length);
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
                    title: '内容记录',
                    exportOptions: {
                        columns: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
                    },
                    modifier: {
                        search: 'none'
                    },
                    format: {},
                    filename: '内容记录'
                },
                {
                    extend: 'colvis',
                    columns: ':not(.noVis)',
                    collectionLayout: 'fixed four-column'
                }
            ],
            ajax: {
                "url": "/sec/community/getCommunities",
                "contentType": "application/json;charset=utf-8",
                "type": "POST",
                "data": function () {
                   //查询条件参数
                    var param={
                         name:$("#name").val().trim()
                    };
                    return JSON.stringify(param)
                },
                "dataSrc": ""
            },
            columns:[
                {"data":"id","width":"30px"},
                {"data":"id","width":"50px"},
                {"data":"name","width":"200px"},
                {"data":"created"},
                {"data":"contact"},
                {"data":"company"},
                {"data":"status",defaultContent:"",
                  render:function (data,type,row) {
                      if(data==0){
                          return "草稿"
                      }else if(data==1){
                          return "待审核"
                      }else if(data==2){
                          return "拒绝"
                      }else if(data==2){
                          return "审核通过"
                      }
                  }
                },
                {"data":"description"},
                {"data":"background"},
                {"data":"using_score",defaultContent:"",
                   render:function (data,type,row) {
                       if(data==true){
                           return "开启"
                       }else if(data==false){
                           return "关闭"
                       }
                   }
                },
                {"data":"avatar"},
                {"data":"vip_amount",defaultContent:"",
                    render:function (data,type,row) {
                        if(data>0){
                            return "开启"
                        }else if(data==0){
                            return "关闭"
                        }
                    }
                },
                {"data":"created_by"},
                {"data":"address"},
                {"data":"about"},
                {"data":"action","width":"160px",defaultContent:"",
                   render:function (data,type,row) {
                       return '<button id="editrow" class="am-btn am-btn-sm am-btn-secondary" type="button" title="编辑团体"><i class="am-icon-edit"></i></button>'+
                           '<button id="delrow" class="am-btn am-btn-sm am-btn-danger" type="button" title="删除团体"><i class="am-icon-trash-o"></i></button>'

                   }
                },
            ],
            //定义指定的栏
            columnDefs:[
                {
                      searchable:false,
                      orderable:false,
                      targets:0
                },
                {targets:[0,1,2,3,4,5,6,-1],visible:true},
                {targets:"_all",visible:false}
            ],
            //默认排序
            order:[[3,"desc"]],
            autoWidth:false,
            scrollX:true,
            //推迟渲染
            deferRender:true
        });
      //添加索引号
      t.on('order.dt search.dt',function () {
         t.column(0,{order:'applied',search:'applied'}).nodes().each(function (cell,i) {
             cell.innerHTML=i+1;
         })
      }).draw();

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        t.ajax.reload();
    })

    /**
     * 添加团体
     */
    $.fn.dataTable.ext.buttons.create={
           className:"",
           action:function (e,dt,node,config) {
               location.href="/sec/community/updateOraddcommunity";
           }
    }

    /**
     * 编辑团体
     */
    $("#bmTable tbody").on('click','button#editrow',function () {
        var data=t.row($(this).parents('tr')).data();
        location.href="/sec/community/updateOraddcommunity?id="+data.id;
    });

    /**
     * 删除团体
     */
    $("#bmTable tbody").on('click','button#delrow',function () {
        var data=t.row($(this).parents('tr')).data();
        if(window.confirm('请确定删除')){
            location.href="/sec/community/deleteCommunity/"+data.id;
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