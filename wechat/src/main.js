import Vue from 'vue'
import App from './App'
import './css/app.css'
import '../static/weui/weui.css'

import store from './store';
import kyutil from './global';
import kyimage from '@/components/kyimage'

Vue.component('kyimage', kyimage)
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
            'pages/attend/attend'
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
                    iconPath: 'static/images/icon_nav_toast.png',
                    selectedIconPath: 'static/images/icon_nav_toast.png',
                    text: '首页'
                },
                {
                    pagePath: 'pages/tool/tool',
                    iconPath: 'static/images/icon_nav_toast.png',
                    selectedIconPath: 'static/images/icon_nav_toast.png',
                    text: '小工具'
                },
                {
                    pagePath: 'pages/oneself/oneself',
                    iconPath: 'static/images/icon_nav_toast.png',
                    selectedIconPath: 'static/images/icon_nav_toast.png',
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