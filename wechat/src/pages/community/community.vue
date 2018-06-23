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
                <div @click="bindSwitchCommunity(item)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <!-- <image class="weui-media-box__thumb" :src="'http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/'+item.avatar"  /> -->
                    <kyimage :src="item.avatar" type="thumb"/>
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">{{item.name}}</div>
                    <div class="weui-media-box__title weui-media-box__title-r">{{item.countPeople}}人</div>
                    <div class="weui-media-box__desc">{{item.description}}</div>
                    <!-- <div class="weui-media-box__desc">
                      <div class="weui-badge-label">跑步</div>
                    </div> -->
                  </div>
                </div>
               
              </div>
            </div>

          <!-- </div> -->

          <!-- <div class="weui-tab__content" :hidden="activeIndex != 1"> -->
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 1">
              <div @click="bindSwitchCommunity(item)" open-type="switchTab" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <!-- <image class="weui-media-box__thumb" :src="'http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/'+item.avatar"  /> -->
                    <kyimage :src="item.avatar" type="thumb"/>
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">{{item.name}}</div>
                    <div class="weui-media-box__title weui-media-box__title-r">{{item.countPeople}}人</div>
                    <div class="weui-media-box__desc">{{item.description}}</div>
                    <!-- <div class="weui-media-box__desc">
                      <div class="weui-badge-label">跑步</div>
                    </div> -->
                  </div>
              </div>
            </div>

          <!-- </div> -->

          <!-- <div class="weui-tab__content" :hidden="activeIndex != 2"> -->
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 2">
              <div @click="bindSwitchCommunity(item)" open-type="switchTab" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">
                  <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
                    <!-- <image class="weui-media-box__thumb" :src="'http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/'+item.avatar"  /> -->
                    <kyimage :src="item.avatar" type="thumb"/>
                  </div>
                  <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                    <div class="weui-media-box__title">{{item.name}}</div>
                    <div class="weui-media-box__title weui-media-box__title-r">{{item.countPeople}}人</div>
                    <div class="weui-media-box__desc">{{item.description}}</div>
                    <!-- <div class="weui-media-box__desc">
                      <div class="weui-badge-label">跑步</div>
                    </div> -->
                  </div>
              </div>
            </div>

          <!-- </div> -->

        </div>

      </div>


    </div>
  </div>
</template>

<script>
import base64 from "../../../static/images/base64";

export default {
  data() {
    return {
      imageUrl: this.$kyutil.imageServer,
      icon20: base64.icon20,
      icon60: base64.icon60,
      //团体组织
      items: [],

      tabs: ["最新热门", "我加入的", "排行榜"],
      activeIndex: 0,
      fontSize: 30
    };
  },
  computed: {
    navbarSliderClass() {
      if (this.activeIndex == 0) {
        return "weui-navbar__slider_0";
      }
      if (this.activeIndex == 1) {
        return "weui-navbar__slider_1";
      }
      if (this.activeIndex == 2) {
        return "weui-navbar__slider_2";
      }
    }
  },
  methods: {
    //获取活动信息
    getData() {
      var that = this;
      var param = {
        userId: wx.getStorageSync("user").id,
        status: that.activeIndex
      };
      this.$kyutil.HttpRequest(
        true,
        "/pub/wx/community/search",
        false,
        "",
        param,
        "GET",
        false,
        function(res) {
          that.items = res;
        }
      );
    },
    tabClick(e) {
      console.log(e);
      this.activeIndex = e.currentTarget.id;
      this.getData();
    },
    bindSwitchCommunity(community) {
      //设置团体组织
      // this.$store.state.communityId = communityId;
      this.$store.state.community = community
      wx.switchTab({
        url: "../../pages/index/index",
        success: function(e) {
          console.log("-> index");
        }
      });
    }
  },
  created() {
    console.log("community created");
  },
  onLoad() {
    this.$kyutil.CheckUserValidation();
    if (wx.getStorageSync("user")) {
      this.getData();
    }
  },
  onShow() {
    wx.setNavigationBarTitle({
      title: "团体组织"
    });
  }
};
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
.weui-media-box__desc {
  max-width: 230px;
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  display: inline-block;
}
.weui-media-box__hd_in-appmsg {
  width: 80px;
  height: 80px;
}
.weui-media-box__title-l {
  float: left;
}
.weui-media-box__title-r {
  float: right;
  color: #999;
  font-size: 14px;
  top: -22px;
  position: relative;
}
.weui-badge-label {
  display: inline-block;
  padding: 0.4em 0.4em;
  min-width: 40px;
  border-radius: 18px;
  border: 1px solid #00c000;
  color: #00c000;
  line-height: 1.2;
  text-align: center;
  font-size: 12px;
  vertical-align: middle;
}
</style>
