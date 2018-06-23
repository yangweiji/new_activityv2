
new Vue({
    el: '#app',
    data: function() {
        return {
            user: user,
            message: message,
            memberFee: 100,
        };
    },
    mounted: function(){
        var that = this;
        $('#c-member-reg-form').validator({
        }).submit(function() {
            return true; // return false to cancel form action
        });
    },
    methods: {
    }
})