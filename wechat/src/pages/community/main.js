import Vue from 'vue'
import App from './community'

// 不支持filter
// Vue.filter("dateFormat", function(src) { //全局方法 Vue.filter() 注册一个自定义过滤器,必须放在Vue实例化前面
//     console.log("f: ", dateutil.format(new Date(src), "yyyy年MM月dd日"));
//     return dateutil.format(new Date(src), "yyyy年MM月dd日");
// })

const app = new Vue(App)
app.$mount()

// console.log(app)