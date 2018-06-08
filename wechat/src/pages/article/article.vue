<template>
  <div class="page">

    <div class="page__bd">
        <div class="weui-article">
            <div class="weui-article__h1">{{item.title}}</div>
            <div class="weui-article__section">
                <!-- <div class="weui-article__title">
                  <image :src="item.avatar" style="height: 180px" />
                </div> -->
                <div class="weui-article__section">
                    <div class="weui-article__h3">{{item.displayname}} {{item.publish_time}}</div>

                    <div class="weui-article__p">
                        <image class="weui-article__img" :src="item.avatar" style="height: 180px" />
                    </div>

                    <div class="weui-article__p">
                        <wxParse :content="item.body" />
                    </div>
                    
                </div>
            </div>
        </div>
    </div>

  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
import global from '../../global/index'
import wxParse from 'mpvue-wxparse';
export default {
  data() {
    return {
      //文章ID
      articleId: "",
      //文章数据
      item:{},
    };
  },
  computed: {
  },
  components: {
    wxParse
  },
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
        articleId: that.articleId
      };
      global.HttpRequest(true, "/pub/wx/article/getArticleExt", false, "", param, "GET", false, function (res) {
        that.item = res;
      });
      
      // wx.request({
      //   url: "https://a.9kylin.cn/pub/wx/article/getArticleExt", //仅为示例，并非真实的接口地址
      //   data: {
      //     articleId: that.articleId
      //   },
      //   header: {
      //     "content-type": "application/json" // 默认值
      //   },
      //   success: function(res) {
      //     console.log(res.data);
      //     that.item = res.data;
      //   },
      //   fail: function(error) {
      //     console.log(error);
      //   }
      // });
    },
  },
  created() {
    console.log("article created");
  },
  onShow () {
    console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    var that = this;
    that.articleId = this.$root.$mp.query.articleId;
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
