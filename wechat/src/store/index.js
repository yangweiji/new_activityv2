import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const store = new Vuex.Store({
    state: {
        count: 0, //测试
        // //默认的组织团体
        // community: {
        //     id: 1, //默认的组织团体ID
        //     name: "北京市马拉松协会",
        //     background: "NzrSDNSBEP.png",
        // },
        //选择的团体组织
        community: null,
        //是否调用了图片选择器
        isUpload: false,
    },
    mutations: {
        increment(state) {
            state.count++
        }
    }
})

export default store