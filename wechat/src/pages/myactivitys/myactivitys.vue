<template>
   <div class="page">
      <div class="page__hd">
      <!-- <div class="page__desc">表单输入</div> -->
      <!-- 如果只是展示用户头像昵称，可以使用 <open-data /> 组件 -->
      <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
      <open-data class="userinfo-name" type="userNickName"></open-data>   
    </div>
     <div class="weui-panel__bd">
       <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" v-for="item in items" :key="item.id">
          <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.avatar" />
          </div>
          <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
             <div class="weui-media-box__title">{{item.title}}</div>
             <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
             <div class="weui-media-box__desc" style="float:right"> 喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
          </div>
       </div>
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
      items: [
        { id: "1", avatar: "http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/KdeCYJRfQb.jpg", title: "夏季马拉松长跑",start_time:"2018/6/22",favorite_count:"1",attend_count:"4"}, 
         { id: "2", avatar: "http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/DykBC4CXFD.png", title: "夏季马拉松长跑",start_time:"2018/6/22",favorite_count:"2",attend_count:"3"}, 
          { id: "3", avatar: "http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/akE7DGexz8.jpg", title: "夏季马拉松长跑",start_time:"2018/6/22",favorite_count:"3",attend_count:"2"}, 
           { id: "4", avatar: "http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/HZWbHk8WCm.jpg", title: "夏季马拉松长跑",start_time:"2018/6/22",favorite_count:"4",attend_count:"1"}
      ]
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
