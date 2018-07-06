<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div class="weui-article">
        <div class="weui-article__h1">{{item.title}}</div>
        <div class="weui-article__section">
          <div class="weui-article__title">
            <image :src="item.avatar" style="width: 40px; height: 40px; overflow: hidden; border-radius: 50%; float: left; position: relative;" />
          </div>
          <div class="weui-article__section">
            <div class="weui-article__h3 c-display-text">
              {{item.displayname}}   {{item.created}}
              <navigator v-if="item.picture_count > 0" :url="'/pages/photosdetails/photosdetails?activityId='+item.id" class="c-photo">
                活动相册<div class="weui-badge">{{item.picture_count}}</div>
              </navigator>
            </div>
            <div v-if="item.body" class="weui-article__p">
              <wxParse :content="item.body" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <div class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div @click="addFavorite()" class="c-default-btn">
        喜欢<span class="weui-badge">{{item.favorite_count}}</span>
      </div>
      <div @click="gotoAttendUsers()" class="c-default-btn">
        参加<span class="weui-badge">{{item.attend_count}}</span>
      </div>
      <div @click="gotoAttend()" class="weui-flex__item c-bg-primary">
        立即报名
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
        item: {}
      };
    },
    computed: {},
    components: {
      wxParse
    },
    methods: {
      getData() {
        var that = this;
        var param = {
          activityId: that.activityId
        };
        that.$kyutil.get("/pub/wx/activity/details",param).then(res => {
          that.item = res;
        })
      },
      gotoAttend() {
        wx.navigateTo({
          url: "../../pages/attend/attend?activityId=" + this.activityId
        });
      },
      gotoAttendUsers() {
        wx.navigateTo({
          url: "../../pages/attendusers/attendusers?activityId=" + this.activityId
        });
      },
      addFavorite() {
        var that = this;
        this.$kyutil.CheckUserValidation();
        var user = this.$kyutil.GetUser();
        if (user) {
          this.$kyutil.get("/pub/wx/activity/favorite",{
              activityId: that.activityId,
              userId: user.id
            }).then(res => that.item.favorite_count = res)
        }
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
      var that = this;
      that.activityId = this.$root.$mp.query.activityId;
      if (this.$store.state.community) {
        wx.setNavigationBarTitle({
          title: this.$store.state.community.name
        })
      }
      this.getData();
    },
    onShareAppMessage(res) {
      return {
        title: this.item.activity.title,
        path: '/page/details/details?activityId=' + this.activityId
      }
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
  .c-photo{
    float: right;
    color:#008cff;
  }
  .c-photo .weui-badge{
    background-color: #008cff;
  }
</style>
