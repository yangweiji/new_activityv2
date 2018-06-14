<template>

  <div class="page">
    <div class="page__bd">
      <!-- banner -->
      <div>
        <navigator url="../../pages/community/community" hover-class="navigator-hover">
          <span class="community-select">
            切换团体
          </span>
        </navigator>  
        <image src="../../static/images/banner_bg.png" class="banner" model="aspectFit" />
        <div class="c-bg"></div>
      </div>

      <!-- 通知公告、赛事新闻、运动指南、活动相册 -->
      <div class="weui-grids" style=" border-top:0px; border-left:0px; background-color:#ffffff;">
        <block v-for="(item,index) in grids" :key='index'>
          <navigator :url="item.url" class="weui-grid" hover-class="weui-grid_active" 
            style="width:25%;border-right:0px;border-bottom:0px">
            <image class="weui-grid__icon" :src='icon60'/>
            <div class="weui-grid_label">{{item.name}}</div>
          </navigator>
        </block>
      </div>

      <!-- navbar -->
      <div class="weui-tab">
        <div class="weui-navbar" style="top:auto;">
            <block v-for="(item,index) in choice" :key="index">
              <div :id="index" :class="{'weui-bar__item_on': activeIndex==item.id}" class="weui-navbar__item"  
                @click="tabClick(item.id)">
                
                <div class="weui-navbar__title" v-if="item.id!='0'">{{item.matter}}</div>
                <div class="weui-navbar__title" v-if="item.id=='0'">
                  <picker @change="bindPickerChange" :value="index" :range="ranges">
                    <span class="wx-btn" type="default">
                      {{ces}}
                    </span>
                  </picker>
                </div>
              </div>
            </block>
          <div class="weui-navbar__slider" :class="navbarSliderClass" style="width:64px;"></div>
        </div>

        <div class="weui-tab__panel">
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 'b5'">
              <div class="weui-panel__bd">
                <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
                    </div>
                </div>
              </div>
            </div>
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 'b13'">
              <div class="weui-panel__bd">
                <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;height">
                      <image class="weui-media-box__thumb" :src="item.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
                    </div>
                </div>
              </div>
            </div>
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 'b12'">
              <div class="weui-panel__bd">
                <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
                    </div>
                </div>
              </div>
            </div>
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != 'b10,b11'">
              <div class="weui-panel__bd">
                <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
                    </div>
                </div>
              </div>
            </div>
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != '0'">
              <div class="weui-panel__bd">
                <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
                    </div>
                </div>
              </div>
          </div>

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
      //Banner
      url: "../../../static/images/banner_bg.png",
      //ICON
      icon60: base64.icon60,
      //活动信息
      items: [],
      //活动ID
      activityId: "",
      //其他活动标签
      ces: "",
      //文章集合
      grids: [
        { src: "", name: "通知公告", url: "/pages/articlelist/articlelist?articleCategory=1" },
        { src: "", name: "赛事新闻", url: "/pages/articlelist/articlelist?articleCategory=2" },
        { src: "", name: "运动指南", url: "/pages/articlelist/articlelist?articleCategory=3" },
        { src: "", name: "活动相册", url: "/pages/articlelist/articlelist?articleCategory=4" }
      ],
      //文章分类
      articleCategory: "",
      //活动标签分类
      choice: [
        { id: "b5", matter: "训练" },
        { id: "b13", matter: "装备" },
        { id: "b12", matter: "福利" },
        { id: "b10,b11", matter: "赛事" },
        { id: "0", matter: "其他" }
      ],
      //默认的选项卡片索引值
      activeIndex: "b5",
      //其余活动标签分类
      ranges: [
        "全部",
        "徒步",
        "越野",
        "聚餐",
        "骑行",
        "会议",
        "招募",
        "讲座",
        "国内赛事",
        "国际赛事"
      ],
      //索引
      index: 0
    };
  },
  computed: {
    navbarSliderClass() {
      if (this.activeIndex == "b5") {
        return "weui-navbar__slider_0";
      }
      if (this.activeIndex == "b13") {
        return "weui-navbar__slider_1";
      }
      if (this.activeIndex == "b12") {
        return "weui-navbar__slider_2";
      }
      if (this.activeIndex == "b10,b11") {
        return "weui-navbar__slider_3";
      }
      if (this.activeIndex == "0") {
        return "weui-navbar__slider_4";
      }
    }
  },
  components: {
  },
  methods: {
    //获取活动信息
    getData() {
      var that = this;
      var param = {
        s: 1, //默认community_id=1
        t: that.activeIndex
      }
      this.$kyutil.HttpRequest(true, "/pub/wx/activity/search", false, "", param, "GET", false, function (res) {
        that.items = res;
      });
    },
    //主要活动标签分类触发事件，重新获取相应的数据
    tabClick(e) {
      this.activeIndex = e;
      this.getData();
    },
    //其他活动标签分类触发事件，重新获取相应的数据
    bindPickerChange(e) {
      this.index = e.mp.detail.value;
      this.ces = this.ranges[e.mp.detail.value];
      if (this.ces == "全部") {
        this.activeIndex = "0";
      } else if (this.ces == "徒步") {
        this.activeIndex = "b1";
      } else if (this.ces == "越野") {
        this.activeIndex = "b2";
      } else if (this.ces == "聚餐") {
        this.activeIndex = "b3";
      } else if (this.ces == "骑行") {
        this.activeIndex = "b4";
      } else if (this.ces == "会议") {
        this.activeIndex = "b6";
      } else if (this.ces == "招募") {
        this.activeIndex = "b7";
      } else if (this.ces == "讲座") {
        this.activeIndex = "b8";
      } else if (this.ces == "国内赛事") {
        this.activeIndex = "b10";
      } else if (this.ces == "国际赛事") {
        this.activeIndex = "b11";
      }
      this.getData();
    },
    //查看活动详情
    checkdetails(activityId) {
      var that = this;
      that.activityId = activityId;
      wx.navigateTo({
        url: "../../pages/details/details?activityId=" + that.activityId
      });
      success: {
        console.log("-> details: activityId=" + activityId);
      }
    },
    //查看文章分类列表
    articlelist(articleCategory) {
      var that = this;
      that.articleCategory = articleCategory;
      wx.navigateTo({
        url: "../../pages/articlelist/articlelist?articleCategory=" + that.articleCategory
      });
      success: {
        console.log("-> articlelist: articleCategory=" + articleCategory);
      }
    }
  },
  //页面创建完成，获取活动信息
  created() {
    console.log("global:", global);
    console.log("index created");
    this.getData();
    //设置默认的其他活动标签分类值
    this.ces = this.ranges[this.index];
  }
};
</script>

<style scoped>
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */

.weui-flex {
  -webkit-box-align: center;
  -webkit-align-items: center;
  align-items: center;
}
.class-a {
  background-color: #ff7f00;
}

.placeholder {
  margin: 0.7em;
  padding: 0 10px;
  text-align: center;
  height: 2.3em;
  line-height: 2.3em;
  color: #000000;
}

.weui-cells {
  margin-top: 0;
  opacity: 0;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  -webkit-transition: 0.3s;
  transition: 0.3s;
}

.weui-cells:after,
.weui-cells:before {
  display: none;
}

.weui-cells_show {
  opacity: 1;
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.weui-cell:before {
  right: 15px;
}

.kind-list__item {
  margin: 10px 0;
  background-color: #fff;
  border-radius: 2px;
  overflow: hidden;
}

.kind-list__item:first-child {
  margin-top: 0;
}

.kind-list__img {
  width: 30px;
  height: 30px;
}

.kind-list__item-hd {
  padding: 20px;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
}

.kind-list__item-hd_show {
  opacity: 0.4;
}

.kind-list__item-bd {
  height: 0;
  overflow: hidden;
}

.kind-list__item-bd_show {
  height: auto;
}
.weui-tab__content {
  padding-top: 0px;
  text-align: center;
}

.weui-navbar__slider_0 {
  left: 5rpx;
  transform: translateX(0);
}
.weui-navbar__slider_1 {
  left: 5rpx;
  transform: translateX(150rpx);
}
.weui-navbar__slider_2 {
  left: 10rpx;
  transform: translateX(300rpx);
}
.weui-navbar__slider_3 {
  left: 15rpx;
  transform: translateX(450rpx);
}
.weui-navbar__slider_4 {
  left: 20rpx;
  transform: translateX(600rpx);
}
.wx-btn {
  font-size: 15px;
  line-height: normal;
  border: none;
}
.weui-panel {
  background-color: #fff;
  margin-top: -2px;
  position: relative;
  overflow: hidden;
}

</style>
