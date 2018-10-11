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
                    title: '打卡查询',
                    exportOptions: {
                        columns: ':not(:eq(7))',//jquery to exclude column 0
                        modifier: {
                            search: 'none'
                        },
                    },
                    filename: '打卡查询',
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
                "url": "/sec/community/thirdactivity/getCheckInList",
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
                {data: "title", width: "100px"},
                {data: "username", width: "100px"},
                {data: "displayname"},
                {data: "real_name"},
                {data: "notes", width: "100px"},
                // {data: "pictures"},
                {data: "record_time"},
                {
                    "className":      'details-control',
                    "orderable":      false,
                    "data":           null,
                    "defaultContent": '',
                    "title": '打卡照片'
                },
            ],

            //栏定义
            columnDefs: [
                {
                    searchable: false,
                    orderable: false,
                    targets: 0
                },
                {targets: '_all', visible: true}
            ],
            //默认排序
            order: [[6, 'desc']],
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

    // //添加索引序号
    // t.on('order.dt search.dt', function () {
    //     t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
    //         cell.innerHTML = i + 1;
    //     });
    // }).draw();

    /**
     * 查询
     */
    $("#btnSearch").click(function () {
        // t.ajax.reload(null, false);
        t.ajax.reload();
    });

    // Add event listener for opening and closing details
    $('#bmTable tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = t.row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( format(row.data()) ).show();
            tr.addClass('shown');
        }
    } );

    /* Formatting function for row details - modify as you need */
    function format ( d ) {
        // `d` is the original data object for the row

        if (!d.pictures) {
            return '<p>查询无记录！</p>';
        }

        var html = '<div class="am-g am-imglist" data-am-widget="paragraph" data-am-paragraph="{ tableScrollable: true, pureview: true }" >';
        html += '<ul data-am-widget="gallery" class="am-gallery am-avg-sm-2 am-avg-md-3 am-avg-lg-4 am-gallery-default">';
        var ps = d.pictures.split(',');
        for (var i = 0; i < ps.length; i ++) {
            var url = OssUrl+'/activity/'+ps[i];
            html += '<li>' + '<div class="am-gallery-item am_list_block">' + '<a href="'+url+'" target="_blank" class="am_img_bg" title="点击查看大图"><img class="am_img animated m-img" src="'+url+'"></a>' + '</div>' + '</li>'
        }
        html += '</ul>';
        html += '</div>';
        return html;
    }

});

new Vue({
    el: '#app',
    data: {},
    mounted: function () {
        var that = this;
    },
    methods: {},
});