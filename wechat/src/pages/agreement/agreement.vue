<template>
  <div class="page">
    <!-- <div class="page__hd">
          <div class="page__title">Article</div>
          <div class="page__desc">文章</div>
      </div> -->
    <div class="page__bd">
      <div class="weui-article">
        <div class="weui-article__h1">{{item.name}}</div>
        <div class="weui-article__section">
          <div class="weui-article__h3 c-display-text">{{item.displayname}} {{item.created}}</div>
          <div class="weui-article__p">
            <wxParse :content="item.vipAgreement" />
          </div>
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
          communityId: that.communityId
        };
        this.$kyutil.get("/pub/wx/profile/agreement", param).then(res => {
          this.item = res
          that.processing = false
        })
        
      }
      
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      var that = this;
      that.communityId = this.$root.$mp.query.communityId;
      wx.setNavigationBarTitle({
          title: "相关条款"
        })
      this.getData();
    },
    onShareAppMessage(res) {
      return {
        title: this.item.activity.title,
        path: 'pages/agreement/agreement?communityId=' + this.communityId
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
</style>
