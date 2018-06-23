new Vue({
    el: "#app",
    data: function () {
        return {
            sms: _global_data,
        }
    },
    mounted: function () {

    },
    methods: {
        selectVal: function (e) {
            console.log(e.target.value);
        }
    }
})
