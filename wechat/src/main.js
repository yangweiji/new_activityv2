import Vue from 'vue'
import App from './App'
import './css/app.css'
import store from './store';
import kyutil from './global';

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
            'pages/myvip/myvip',
            'pages/agreement/agreement',
            'pages/details/details',
            'pages/preview/preview',
            'pages/articlelist/articlelist',
            'pages/posterlist/posterlist',
            'pages/article/article',
            'pages/community/community',
            'pages/mycommunity/mycommunity',
            'pages/communitydetail/communitydetail',
            'pages/attend/attend',
            'pages/attendupdate/attendupdate',
            'pages/photos/photos',
            'pages/photosdetails/photosdetails',
            'pages/myactivitys/myactivitys',
            'pages/integrals/integrals',
            'pages/userinfo/userinfo',
            'pages/realinfo/realinfo',
            'pages/activityrecord/activityrecord',
            'pages/activityrecorditem/activityrecorditem',
            'pages/toolcaculate/toolcaculate',
            'pages/toolbomamatch/toolbomamatch',
            'pages/toolmaf180/toolmaf180',
            'pages/toolsizemap/toolsizemap',
            'pages/attendusers/attendusers',
            'pages/search_activity/search_activity',
            'pages/page/page'
        ],

        // Will be filled in webpack
        window: {
            backgroundTextStyle: 'light',
            navigationBarBackgroundColor: '#fff',
            navigationBarTitleText: '燃宁running',
            navigationBarTextStyle: 'black'
        },
        tabBar: {
            selectedColor: '#f37b1d',
            color: '#7f8389',
            list: [{
                    pagePath: 'pages/index/index',
                    iconPath: 'static/images/home_page.png',
                    selectedIconPath: 'static/images/home_pages.png',
                    text: '首页'
                }, {
                    pagePath: 'pages/search_activity/search_activity',
                    iconPath: 'static/images/search_cheched.png',
                    selectedIconPath: 'static/images/search_image.png',
                    text: '发现'
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