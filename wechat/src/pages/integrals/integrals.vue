<template>
       <div class="page">
         <div class="page__hd">
      <!-- <div class="page__desc">表单输入</div> -->
      <!-- 如果只是展示用户头像昵称，可以使用 <open-data /> 组件 -->
      <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
      <open-data class="userinfo-name" type="userNickName"></open-data>
         </div>
          <div class="weui-media-box weui-media-box_appmsg" v-for="item in grids" :key="item.id">
             <div class="weui-media-box__title" style="float:left">{{item.title}}</div>
              <div class="weui-media-box__desc" style="float:right;font-size:14px;"> +{{item.count}}</div>
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
        {id:1,title: "冬季香山20公里长跑", count: "5"}, 
        {id:2, title: "奥园接力跑", count: "6"}, 
        {id:3, title: "北京马拉松比赛", count: "7"}, 
        {id:4, title: "长跑运动会", count: "8"}
      ],
      counts:0
    };
  },
  computed: {
  },
  components: {
  },
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
        communityId:1
     };
      global.HttpRequest(true, "", false, "", param, "GET", false, function (res) {
         that.grids=res;
        
     });
    },
  },
  created() {
    console.log("photos created");
  },
  onShow () {
    // console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    // var that = this;
    // that.articleId = this.$root.$mp.query.articleId;
    this.getData();
  }
};
</script>

<style scoped>
@import url("~mpvue-wxparse/src/wxParse.css");
.userinfo-avatar {
  /* width: 140rpx;
  height: 140rpx;
  border-radius: 50%;
  margin-top: 40rpx;
  display: block;
  overflow: hidden; */
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

</style>
