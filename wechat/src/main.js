import Vue from 'vue'
import App from './App'
import './css/app.css'
Vue.config.productionTip = false
App.mpType = 'app'

import '../static/weui/weui.css'
const app = new Vue(App)
app.$mount()

export default {
  // 这个字段走 app.json
  config: {
    pages: [
      'pages/index/index',
      'pages/tool/tool',
      'pages/oneself/oneself',
      // 'pages/details/details'
      
    ], // Will be filled in webpack
    window: {
      backgroundTextStyle: 'light',
      navigationBarBackgroundColor: '#fff',
      navigationBarTitleText: '活动平台V2',
      navigationBarTextStyle: 'black'
    },
    tabBar:{
      list:[
        {
          pagePath:'pages/index/index',
          text:'首页'
        },
        {
          pagePath:'pages/tool/tool',
          text:'小工具'
        },
        {
          pagePath:'pages/oneself/oneself',
          text:'我'
        }
      ]
    }
    /* tabBar: {
      color: '#999999',
      selectedColor: '#1AAD16',
      backgroundColor: '#ffffff',
      borderStyle: 'white',
      list: [
        {
          pagePath: 'pages/tabbar/tabbar',
          text: '微信',
          iconPath: 'static/images/icon_nav_button.png',
          selectedIconPath: 'static/images/icon_nav_button.png'
        },
        {
          pagePath: 'pages/tabbar/tabbar',
          text: '通讯录',
          iconPath: 'static/images/icon_nav_cell.png',
          selectedIconPath: 'static/images/icon_nav_cell.png'
        },
        {
          pagePath: 'pages/tabbar/tabbar',
          text: '发现',
          iconPath: 'static/images/icon_nav_cell.png',
          selectedIconPath: 'static/images/icon_nav_cell.png'
        },
        {
          pagePath: 'pages/tabbar/tabbar',
          text: '我',
          iconPath: 'static/images/icon_nav_toast.png',
          selectedIconPath: 'static/images/icon_nav_toast.png'
        }
      ]
    } */
  }
}
