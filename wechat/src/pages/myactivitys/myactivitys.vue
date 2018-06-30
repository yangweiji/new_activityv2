<template>
   <div class="page">
      <div class="page__hd">
      <div class="userinfo-name" v-if="items && items.length > 0 && type">{{types[type]}}的活动</div>
      <div class="userinfo-name" v-if="items && items.length == 0">还没有{{types[type]}}的活动</div>
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
      community:null,
      type:null,
      userId:null,
      items: null,
      types:{
        '1':'已参与',
        '2':'需签到',
        '3':'已签到',
        '4':'我喜欢'
      }
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
      var that = this
        var param = {
          communityId: this.community.id,
          type:this.type,
          userId:this.userId
        };
        this.$kyutil.get("/pub/wx/profile/getmyactivities", param).then(res => {
          that.items = res
          
        })
    },
  },
  created() {
    console.log("photos created");
  },
  onShow () {
    if (this.$store.state.community) {
        this.community = this.$store.state.community
        this.type = this.$root.$mp.query.type
        this.$kyutil.CheckUserValidation()
        var user = this.$kyutil.GetUser()
        if (user) {
          this.userId = user.id;
          this.getData();
        }
        wx.setNavigationBarTitle({
          title: this.community.name
        });
      }
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
