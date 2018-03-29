//图片URL
Vue.directive('translator', {

    bind:function (el, binding, vnode) { //1-被绑定
        console.log("1-bind 被绑定");
        // console.log("el:",el);
        // console.log("binding:",binding);
        // console.log("vnode:",vnode);
        // el.style.color=binding.value;
    },
    inserted:function (el, binding, vnode) { //2-被插入
        console.log("2-inserted 被插入");
        // console.log(el);
        $.ajax({
            url: "/pub/images",
            type: "get",
            data: { fileId: el.attributes["data-url"].value },
            dataType: "text",
            success: function(data) {
                el.src = data;
            },
        });
    },
    update:function (el, binding, vnode) { //3-更新
        console.log("3-update 更新");
    },
    componentUpdated:function (el, binding, vnode) { //4-更新完成
        console.log("4-componentUpdated 更新完成");
    },
    unbind:function (el, binding, vnode) { //5-解绑
        console.log("5-unbind 解绑");
    }

})
