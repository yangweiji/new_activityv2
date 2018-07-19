<template>
  <div class="page">
    <div class="page__hd">
      <div class="community-avatar">
        <kyimage :src="user.avatar" type="avatar" />
        <span class="userinfo-name">{{user.displayname}}</span>
      </div>
    </div>
    <div class="page__bd">
      <div class="weui-media-box weui-media-box_appmsg" v-for="item in grids" :key="item.id">
        <div class="weui-media-box__title">{{item.title}}</div>
        <div class="weui-media-box-text" ><span v-if='item.score>0'> +</span>{{item.score}}</div>
      </div>
    </div>

  </div>

</template>

<script>
import kyimage from '@/components/kyimage.vue'
import base64 from "../../../static/images/base64";
export default {
  data() {
    return {
      //活动相册
      grids: [],
      user: null,
    };
  },
  computed: {},
  components: {
    kyimage
  },
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
        communityId: that.community.id,
        userId: wx.getStorageSync("user").id
      };
      this.$kyutil.get("/pub/wx/profile/scores", param).then(res => {
        that.grids = res;
      });
    }
  },
  created() {
  },
  onShow() {
    var that = this;
    this.community = this.$store.state.community;
    that.user = this.$kyutil.GetUser();
    if (that.user) {
      this.getData();
    }
    wx.setNavigationBarTitle({
      title: "积分"
    })
  }
};
</script>

<style scoped>
.page__hd {
  padding: 0 0;
}
.weui-media-box__title {
  float: left;
  width: 80%;
}
.weui-media-box-text {
  float: right;
  width: 20%;
  text-align: right;
  font-size: 18px;
  color: #f37b1d;
}
.community-avatar {
    padding-top: 5px;
    padding-bottom: 5px;
    background-color:#1E364E;
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
</style>
