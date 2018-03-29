

$(function () {

    //JQuery DataTables HTML (DOM) sourced data
    var t = $('#bmTable').DataTable({
        language: {
            url: "/json/chinese.json",
        },
        dom: 'Bfrtip',
        buttons: [
            // 'csv'
        ],
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
        "order": [[4, 'desc']],
    });

    //添加索引序号
    t.on('order.dt search.dt', function () {
        t.column(0, {search: 'applied', order: 'applied'}).nodes().each(function (cell, i) {
            cell.innerHTML = i + 1;
        });
    }).draw();
});

new Vue({
    el: '#app',
    data: function() {
        return {
            uploader:null,
            user: _global_data,
            isUpdateDisplayname:false,
            displayname: ''
        }
    },
    mounted: function(){
        var that = this
        this.uploader = Util.file.uploader({
            randomName: true,
            selectId:'c-upload-avatar',
            success:function (file) {
                that.update('avatar', file.randomName)
            }
        })
    },
    methods: {
        getAvatar: function () {
            if (this.user.avatar)
                return Util.file.downloadUrl(this.user.avatar, 'small')
            else
                return '/img/activity/man.png'
        },
        startUpdateDisplayname:function () {
            this.isUpdateDisplayname = true
            this.displayname = this.user.displayname
        },
        cancelUpdateDisplayname:function () {
            this.isUpdateDisplayname = false
        },
        updateDisplayname:function () {
            var that = this
            if(this.displayname) {
                this.update('displayname', this.displayname, function () {
                    that.user['displayname'] = that.displayname
                    that.cancelUpdateDisplayname()
                })
            }
        },
        update:function (property, value, success) {
            var that = this
            Util.ajax({
                type: 'post',
                dataType:'text',
                url: '/sec/user/update',
                data: JSON.stringify({ property: property, value: value}),
                success: function () {
                    if(success){
                        success(value);
                    } else {
                        that.user[property] = value
                    }
                }
            })

        }
    }

})