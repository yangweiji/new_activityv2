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
                    <div class="weui-article__h3 c-display-text">{{item.displayname}} {{item.created}}</div>
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
    <div class="c-footer-btns weui-flex c-border-top">
      <div @click="addFavorite()" class="c-default-btn c-border-right">
        喜欢<span class="weui-badge" >{{item.favorite_count}}</span>
      </div>
      <div @click="gotoAttendUsers()" class="c-default-btn">
        参加<span class="weui-badge" >{{item.attend_count}}</span>
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
      activityId: 0,
      ilike:false,
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
    gotoAttend() {
      wx.navigateTo({
        url: "../../pages/attend/attend?activityId=" + this.activityId
      });
    },
    gotoAttendUsers(){

    },
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
          res => that.item.favorite_count = res
        );
      }
    }
  },
  created() {
    console.log("details created");
  },
  onShow() {
    console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
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
.c-display-text {
  line-height: 40px;
  color: grey;
  margin-left: 50px;
}

.c-footer-btns {
  position: fixed;
  bottom: 0;
  width: 100%;
  border-top: 1px lightgray solid;
  line-height: 48px;
  text-align: center;
  background-color: #f8f8f8;
}
.c-footer-btns .weui-badge {
  margin-bottom: 3px;
}
.c-default-btn {
  width: 25%;
}
.c-primary-btn {
}
</style>
