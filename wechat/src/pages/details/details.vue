<template>
  <div class="page">

    <!-- <div class="page__hd">
        <div class="page__title">Article</div>
        <div class="page__desc">文章</div>
    </div> -->
    <div class="page__bd">
        <div class="weui-article">
            <div class="weui-article__h1">{{item.title}}</div>
            <div class="weui-article__section">
                <div class="weui-article__title">
                  <image :src="item.avatar" style="width: 40px; height: 40px; overflow: hidden; border-radius: 50%; float: left; position: relative;" />
                </div>
                <div class="weui-article__section">
                    <div class="weui-article__h3">{{item.displayname}} {{item.created}}</div>
                    <div class="weui-article__p">
                        <wxParse :content="item.body" />
                    </div>
                    <!-- <div class="weui-article__p">
                        <image class="weui-article__img" src="/static/images/pic_article.png" mode="aspectFit" style="height: 180px" />
                        <image class="weui-article__img" src="/static/images/pic_article.png" mode="aspectFit" style="height: 180px" />
                    </div> -->
                </div>
                <!-- <div class="weui-article__section">
                    <div class="weui-article__h3">1.2 节标题</div>
                    <div class="weui-article__p">
                        Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod
                        tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,
                        cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non
                        proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </div>
                </div> -->
            </div>
        </div>
    </div>

  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
import wxParse from 'mpvue-wxparse';
export default {
  data() {
    return {
      activityId: 0,
      item:{},
    };
  },
  computed: {
  },
  components: {
    wxParse
  },
  methods: {
    getData() {
      var that = this;
      wx.request({
        url: "https://a.9kylin.cn/pub/wx/activity/details", //仅为示例，并非真实的接口地址
        data: {
          activityId: that.activityId
        },
        header: {
          "content-type": "application/json" // 默认值
        },
        success: function(res) {
          console.log(res.data);
          that.item = res.data;
        },
        fail: function(error) {
          console.log(error);
        }
      });
    },
  },
  created() {
    console.log("details created");
  },
  onShow () {
    console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    var that = this;
    that.activityId = this.$root.$mp.query.activityId;
    this.getData();
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
 .pet_zlnr_user_l { width: 40px; height: 40px; overflow: hidden; border-radius: 50%; float: left; position: relative;}
</style>
