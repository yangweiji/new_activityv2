<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div class="weui-article">
        <div class="weui-article__h1">{{item.title}}
          <!-- <span class="weui-badge" v-if="item.activity_type==1">活动</span>
          <span class="weui-badge" v-if="item.activity_type==2">赛事</span>
          <span class="weui-badge" v-if="item.activity_type==3">抽签</span> -->
          <span class="weui-badge" v-if="item.activity_type==4">打卡</span>
        </div>
        <div class="weui-article__section">
          <div class="weui-article__title">
            <image :src="item.avatar" style="width: 40px; height: 40px; overflow: hidden; border-radius: 50%; float: left; position: relative;" />
          </div>
          <div class="weui-article__section">
            <div class="weui-article__h3 c-display-text">
              {{item.displayname}}   {{item.created}}
              <navigator v-if="item.picture_count > 0" :url="'/pages/photosdetails/photosdetails?activityId='+item.id" class="c-photo">
                <i class="fa fa-picture-o"></i> {{item.picture_count}} 张
              </navigator>
            </div>
            <div v-if="item.body" class="weui-article__p">
              <p v-if="item.start_time">时间: {{item.start_time}} - {{item.end_time}}</p>
              <p v-if="item.address">地点: {{item.address}}</p>
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
          //设置活动详情页面的标题
          wx.setNavigationBarTitle({
            title: that.item.community_name
          });
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
        this.$kyutil.CheckUserValidation().then(function(res) {
          var user = that.$kyutil.GetUser();
          that.$kyutil.get("/pub/wx/activity/favorite",{
            activityId: that.activityId,
            userId: user.id
          }).then(res => that.item.favorite_count = res)
        });
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
      var that = this;
      that.activityId = this.$root.$mp.query.activityId;
      this.getData();
    },
    onShareAppMessage(res) {
      return {
        title: this.item.title,
        path: 'pages/details/details?activityId=' + this.activityId
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
    color: #aaa;
    margin-left: 50px;
  }
  .c-photo{
    float: right;
    color:#aaa;
  }
  .c-photo .weui-badge{
    background-color: #008cff;
    margin-bottom: 10px;  }
</style>
