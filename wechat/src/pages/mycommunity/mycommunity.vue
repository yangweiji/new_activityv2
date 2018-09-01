<template>
  <div class="page">
    <!-- <div class="page__hd">
      <kyimage src="fl01.png" folder="wx_home" type="banner" />
      <div class="page__title">北京市马拉松协会</div>
      <div class="page__desc">运动、健康、快乐、分享、梦想</div>
    </div> -->

    <div class="page__bd">
      <!-- banner begin -->
      <div>
        <!-- <navigator hover-class="navigator-hover">
            <span class="community-search">
            </span>
        </navigator>  
        <kyimage src="fl01.png" folder="wx_home" type="banner" />
        <div class="c-bg"></div> -->

        <!-- <navigator url="../../pages/community/community" hover-class="navigator-hover" class="community-select">
          <span style="top:5px;">
            <i class="fa fa-exchange" aria-hidden="true"></i>切换
          </span>
        </navigator> -->
        <div class="community-avatar">
          <kyimage :src="user.avatar" type="avatar" />
          <span class="userinfo-name">{{user.displayname}}</span>
        </div>
      </div>
      <!-- banner end -->

      <div class="weui-panel weui-panel_access">
        <div class="weui-panel__bd">
          <div @click="open(item)" open-type="switchTab" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" v-for="item in items" :key="item.id">
            <div class="weui-media-box__hd weui-media-box__hd_in-appmsg">
              <kyimage :src="item.avatar" type="thumb" />
            </div>
            <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
              <div class="weui-media-box__title">{{item.name}}</div>
              <div class="weui-media-box__title weui-media-box__title-r">
                {{item.count_people}}人
              </div>
              <!-- <div class="weui-media-box__desc">{{item.description}}</div> -->
              <div class="weui-media-box__desc" style="font-size:12px;">
                <span v-if="item.member_time">{{item.member_time}} 入会</span>
                <div class="weui-badge-label" v-if="item.is_default==1" style="margin-left:5px;">默认团体</div>
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
import kyimage from "@/components/kyimage.vue";
import dateutil from "../../global/date.js";

export default {
  components: { kyimage },
  data() {
    return {
      icon: base64.icon20,
      icon20: base64.icon20,
      icon60: base64.icon60,
      d: null,

      //团体组织
      items: [],
      user: null
    };
  },
  computed: {},
  methods: {
    getData() {
      var that = this;
      that.d = that.getDateFormat("2018-09-01 00:00:00");

      var param = {
        userId: wx.getStorageSync("user").id,
        status: 1
      };
      this.$kyutil.get("/pub/wx/community/search", param).then(res => {
        that.items = res;
      });
    },
    getDateFormat(src) {
      return dateutil.format(new Date(src), "yyyy年MM月dd日");
    },
    open(item) {
      var that = this;
      wx.showActionSheet({
        itemList: ["设为默认团体", "团体概要"],
        success: function(res) {
          // console.log(res.tapIndex)
          if (res.tapIndex == 0) {
            var param = {
              userId: wx.getStorageSync("user").id,
              communityId: item.id
            };

            that.$kyutil.post("/pub/wx/community/default", param).then(res => {
              if (res) {
                wx.showToast({
                  title: "已完成",
                  icon: "success",
                  duration: 2000
                });
                //切换为选中团体组织
                that.$store.state.community = item;
                that.getData();
              }
            });
          }

          if (res.tapIndex == 1) {
            wx.navigateTo({
              url: "../../pages/communitydetail/communitydetail?id=" + item.id
            });
          }
        }
      });
    }
  },
  created() {},
  onLoad() {},
  onShow() {
    var that = this;
    wx.setNavigationBarTitle({
      title: "我的团体组织"
    });

    this.$kyutil.CheckUserValidation().then(function(res) {
      var user = that.$kyutil.GetUser();
      that.user = user;
      that.getData();
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
.weui-tab {
  position: relative;
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
  /* max-width: 230px; */
  /* text-overflow: ellipsis; */
  /* white-space: nowrap; */
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

.page__desc {
  text-align: center;
}

.weui-media-box__thumb {
  /* border: 1px solid #e7e7e7;
  border-radius: 10px; */
}

.weui-media-box {
  border-bottom: 1px solid #e5e5e5;
}
.user-avatar {
  height: 65px;
  width: 65px;
}
.community-avatar {
  padding-top: 5px;
  padding-bottom: 5px;
  background-color: #1e364e;
}
.userinfo-avatar {
  margin: 0 auto;
  margin-top: 10px;
  margin-bottom: 10px;
  display: flex;
  justify-content: center;
  overflow: hidden;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  border: 2px solid #aaa;
}
.userinfo-name {
  color: #fff;
  margin: 0 auto;
  margin-top: 20rpx;
  display: flex;
  justify-content: center;
  overflow: hidden;
}
.community-select {
  padding: 0 0;
  margin-top: 5px;
  margin-right: 5px;
  background-color: rgba(0, 0, 0, 0);
}
</style>
