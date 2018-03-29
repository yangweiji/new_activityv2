$(function() {
    //手工选择日期后的时间
    $('#start_date').datepicker().
    on('changeDate.datepicker.amui', function(event) {
        location.href = "/pub/search/"+s+";tag="+tag+";time="+moment(event.date).format("YYYYMMDD")+";pay="+pay;
        $(this).datepicker('close');
    });

});

new Vue({
    el: '#app',
    data: function() {
        return {
        }
    },
    methods: {
    },
    // directives: {
    //     translator: {
    //         // 指令的定义
    //         inserted: function (el) {
    //             // console.log(el);
    //             $.ajax({
    //                 url: "/pub/images",
    //                 type: "get",
    //                 data: { fileId: el.attributes["data-url"].value },
    //                 dataType: "text",
    //                 success: function(data) {
    //                     el.src = data;
    //                 },
    //             });
    //         }
    //     }
    // }
})