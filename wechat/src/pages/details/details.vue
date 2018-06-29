<template>
  <!-- <div class="page">
    <div class="page__bd">
        <div class="weui-article">
            <div class="weui-article__h1">{{item.title}}</div>
            <div class="weui-article__section">
                <div class="weui-article__title">
                  <image :src="item.avatar" style="width: 40px; height: 40px; overflow: hidden; border-radius: 50%; float: left; position: relative;" />
                </div>
                <div class="weui-article__section">
                    <div class="weui-article__h3 c-display-text">{{item.displayname}} {{item.created}}</div>
                    <div class="weui-article__p">
                        <wxParse :content="item.body" />
                    </div>                 
                </div>               
            </div>
        </div>
    </div>
    <div class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div @click="addFavorite()" class="c-default-btn">
        喜欢<span class="weui-badge" >{{item.favorite_count}}</span>
      </div>
      <div @click="gotoAttendUsers()" class="c-default-btn">
        参加<span class="weui-badge" >{{item.attend_count}}</span>
      </div>
      <div @click="gotoAttend()" class="weui-flex__item c-bg-primary">
        立即报名
      </div>
    </div>
  </div> -->
  <div class="page">
    <div class="page__bd">
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
          <div class="weui-tab__content" :hidden="activeIndex != 0">活动详情</div>
          <div class="weui-tab__content" :hidden="activeIndex != 1">活动相册</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
import wxParse from "mpvue-wxparse";
export default {
  data() {
    return {
      isIpx: false,
      activityId: 0,
      ilike: false,
      item: {},
      tabs: ["活动详情", "活动相册"],
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
  components: {
    wxParse
  },
  methods: {
    getData() {
      var that = this;
      var param = {
        activityId: that.activityId
      };
      that.$kyutil.HttpRequest(
        true,
        "/pub/wx/activity/details",
        false,
        "",
        param,
        "GET",
        false,
        function(res) {
          that.item = res;
        }
      );
    },
    tabClick(e) {
      console.log(e);
      this.activeIndex = e.currentTarget.id;
    },
    gotoAttend() {
      wx.navigateTo({
        url: "../../pages/attend/attend?activityId=" + this.activityId
      });
    },
    gotoAttendUsers() {},
    addFavorite() {
      var that = this;
      this.$kyutil.CheckUserValidation();
      var user = this.$kyutil.GetUser();
      if (user) {
        this.$kyutil.HttpRequest(
          true,
          "/pub/wx/activity/favorite",
          false,
          "",
          { activityId: that.activityId, userId: user.id },
          "GET",
          false,
          res => (that.item.favorite_count = res)
        );
      }
    }
  },
  created() {
    this.isIpx = this.$kyutil.data.isIpx;
  },
  onShow() {
    console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
    var that = this;
    that.activityId = this.$root.$mp.query.activityId;
    this.getData();
  },
  onShareAppMessage(res) {
    return {
      title: this.item.activity.title,
      path: "/page/details/details?activityId=" + this.activityId
    };
  }
};
</script>

<style scoped>
@import url("~mpvue-wxparse/src/wxParse.css");
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */
.c-display-text {
  line-height: 40px;
  color: grey;
  margin-left: 50px;
}
page,
.page,
.page__bd {
  height: 100%;
}
.page__bd {
  padding-bottom: 0;
}
.weui-tab__content {
  padding-top: 60px;
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
</style>
