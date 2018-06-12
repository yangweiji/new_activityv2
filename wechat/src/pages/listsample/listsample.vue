<template>
  <div class="page">
    <!-- <div class="page__hd">
      <div class="page__title">Input</div>
      <div class="page__desc">表单输入</div>
    </div> -->

    <div class="page__bd">
      <!-- banner -->
      <div>
        <navigator hover-class="navigator-hover">
            <span class="community-search">
              搜索团体
            </span>
        </navigator>  
        <image src="../../static/images/banner_road.jpg" class="banner-community" model="aspectFit" />
        <div class="c-bg"></div>
      </div>
    
      <!-- navbar -->
      <div class="weui-tab">
        
        <div class="weui-navbar">
          <block v-for="(item,index) in tabs" :key="index">
            <div :id="index" :class="{'weui-bar__item_on':activeIndex == index}" class="weui-navbar__item" @click="tabClick">
              <div class="weui-navbar__title">{{item}}</div>
            </div>
          </block>
          <div class="weui-navbar__slider" :class="navbarSliderClass"></div>
        </div>

        <div class="weui-tab__panel">
          <!-- <div class="weui-tab__content" :hidden="activeIndex != 0"> -->
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 0">
              <div class="weui-panel__bd">
                <navigator url="" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <image class="weui-media-box__thumb" :src="icon60" />
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">标题一</div>
                    <div class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</div>
                  </div>
                </navigator>
                <navigator url="" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <image class="weui-media-box__thumb" :src="icon60" />
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">标题二</div>
                    <div class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</div>
                  </div>
                </navigator>
              </div>
            </div>

          <!-- </div> -->

          <!-- <div class="weui-tab__content" :hidden="activeIndex != 1"> -->
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 1">
              <div class="weui-panel__bd">
                <navigator url="" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <image class="weui-media-box__thumb" :src="icon60" />
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">标题一</div>
                    <div class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</div>
                  </div>
                </navigator>
                <navigator url="" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <image class="weui-media-box__thumb" :src="icon60" />
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">标题二</div>
                    <div class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</div>
                  </div>
                </navigator>
              </div>
            </div>

          <!-- </div> -->

          <!-- <div class="weui-tab__content" :hidden="activeIndex != 2"> -->
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 2">
              <div class="weui-panel__bd">
                <navigator url="" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <image class="weui-media-box__thumb" :src="icon60" />
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">标题一</div>
                    <div class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</div>
                  </div>
                </navigator>
                <navigator url="" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <image class="weui-media-box__thumb" :src="icon60" />
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">标题二</div>
                    <div class="weui-media-box__desc">由各种物质组成的巨型球状天体，叫做星球。星球有一定的形状，有自己的运行轨道。</div>
                  </div>
                </navigator>
              </div>
            </div>

          <!-- </div> -->

        </div>

      </div>


    </div>
  </div>
</template>

<script>
import base64 from '../../../static/images/base64';
export default {
  data() {
    return {
      icon20: base64.icon20,
      icon60: base64.icon60,

      tabs: ["最新热门", "我关注的", "其他"],
      activeIndex: 0,
      fontSize: 30
    }
  },
  computed: {
    navbarSliderClass() {
      if (this.activeIndex == 0) {
        return 'weui-navbar__slider_0'
      }
      if (this.activeIndex == 1) {
        return 'weui-navbar__slider_1'
      }
      if (this.activeIndex == 2) {
        return 'weui-navbar__slider_2'
      }
    }
  },
  methods: {
    tabClick(e) {
      console.log(e);
      this.activeIndex = e.currentTarget.id;
    }
  },
  created() {
  },
  onShow() {
    wx.setNavigationBarTitle({
      title: '团体组织'
    });
  }
}
</script>

<style>
page,
.page,
.page__bd {
  height: 100%;
}
.page__bd {
  padding-bottom: 0;
}
.weui-tab__content {
  padding-top: 0px;
  text-align: center;
}
.weui-navbar__slider_0 {
  left: 29rpx;
  transform: translateX(0);
}
.weui-navbar__slider_1 {
  left: 29rpx;
  transform: translateX(250rpx);
}
.weui-navbar__slider_2 {
  left: 29rpx;
  transform: translateX(500rpx);
}
.weui-panel {
  background-color: #fff;
  margin-top: 0;
  position: relative;
  overflow: hidden;
}
</style>
