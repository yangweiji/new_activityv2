<template>
       <div class="page">
         <div class="page__hd">
      <!-- <div class="page__desc">表单输入</div> -->
      <!-- 如果只是展示用户头像昵称，可以使用 <open-data /> 组件 -->
      <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
      <open-data class="userinfo-name" type="userNickName"></open-data>
         </div>
          <div class="weui-media-box weui-media-box_appmsg" v-for="item in grids" :key="item.id">
             <div class="weui-media-box__title" style="float:left;width:50%">{{item.title}}</div>
              <div class="weui-media-box-text" > +{{item.score}}</div>
          </div>
      </div>
      

</template>

<script>
import base64 from "../../../static/images/base64";
import global from '../../global/index';
import wxParse from 'mpvue-wxparse'
export default {
  data() {
    return {
      //活动相册
      grids: [
      ],
  
    };
  },
  computed: {
  },
  components: {
  },
  methods: {
    //取得文章信息
    getData()  {
      var that = this;
      var param = {
        communityId: that.community.id,
        userId: wx.getStorageSync("user").id
      };
      this.$kyutil.HttpRequest(
        true,
        "/pub/wx/profile/scores",
        false,
        "",
        param,
        "GET",
        false,
        function(res) {
         console.log(res);
         that.grids=res;
        }
      );
    },
  },
  created() {
    // console.log("photos created");
  },
   onShow () {
  //   console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    var that = this;
   this.community = this.$store.state.community;
   this.getData();
   }
};
</script>

<style scoped>
@import url("~mpvue-wxparse/src/wxParse.css");
.userinfo-avatar {
  margin: 0 auto;  
  margin-top: 50rpx;
  display: flex;
  justify-content: center;
  overflow: hidden;
  width: 161rpx;
  height: 161rpx;
  border-radius: 50%;
}
.userinfo-name {
  margin: 0 auto;  
  margin-top: 20rpx;
  display: flex;
  justify-content: center;
  overflow: hidden;
}
.weui-media-box-text{
  float: right;
  width: 50%;
  text-align: right;
  font-size: 18px;
  color: #F37B1D;
}

</style>
