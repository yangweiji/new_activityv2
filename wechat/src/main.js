import Vue from 'vue'
import App from './App'
import '../static/weui/weui.css'
import './css/app.css'

import store from './store';
import kyutil from './global';
//导入自定义组件
import "./components"


Vue.config.productionTip = false
App.mpType = 'app'
Vue.prototype.$store = store;
Vue.prototype.$kyutil = kyutil;

const app = new Vue(App)
app.$mount()

export default {
    config: {
        pages: [
            '^pages/index/index',
            'pages/login/login',
            'pages/register/register',
            'pages/error/error',
            'pages/tool/tool',
            'pages/oneself/oneself',
            'pages/details/details',
            'pages/articlelist/articlelist',
            'pages/article/article',
            'pages/community/community',
            'pages/attend/attend',
            'pages/attendupdate/attendupdate',
            'pages/photos/photos',
            'pages/photosdetails/photosdetails',
            'pages/myactivitys/myactivitys',
            'pages/integrals/integrals',
            'pages/personalinformation/personalinformation',
            'pages/activityrecord/activityrecord'
        ],

        // Will be filled in webpack
        window: {
            backgroundTextStyle: 'light',
            navigationBarBackgroundColor: '#fff',
            navigationBarTitleText: '活动平台V2',
            navigationBarTextStyle: 'black'
        },
        tabBar: {
            list: [{
                    pagePath: 'pages/index/index',
                    iconPath: 'static/images/home_page.png',
                    selectedIconPath: 'static/images/home_pages.png',
                    text: '首页'
                },
                {
                    pagePath: 'pages/tool/tool',
                    iconPath: 'static/images/small_tool.png',
                    selectedIconPath: 'static/images/small_tools.png',
                    text: '小工具'
                },
                {
                    pagePath: 'pages/oneself/oneself',
                    iconPath: 'static/images/myself.png',
                    selectedIconPath: 'static/images/myselves.png',
                    text: '我'
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