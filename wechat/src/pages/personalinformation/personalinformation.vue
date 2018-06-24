<template>
       <div class="page">
         <div class="page__hd" style="text-align:center">
           完善个人信息    
         </div>
       <div>
       </div>
         <div>
           <div class="weui-cell__hd weui-check__hd_in-checkbox">
          <icon class="weui-icon-checkbox_circle" type="circle" size="23" v-if="!checked"></icon>
          <icon class="weui-icon-checkbox_success" type="success" size="23" v-if="checked"></icon>
          </div>
          <textarea>
          </textarea>
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
        {id:"1",title: "冬季香山20公里长跑", count:5}, 
        {id:"2", title: "奥园接力跑", count:6}, 
        {id:"3", title: "北京马拉松比赛", count: 7}, 
        {id:"4", title: "长跑运动会", count:8}
      ],
      checked:true
  
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
    
        
     });
    },
  },
  created() {
    // console.log("photos created");
  },
   onShow () {
  //   console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
  //   var that = this;
  //   that.articleId = this.$root.$mp.query.articleId;
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
