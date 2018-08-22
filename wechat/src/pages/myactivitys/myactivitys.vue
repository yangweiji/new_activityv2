<template>
   <div class="page">
      <div class="page__hd">
      <div class="userinfo-name" v-if="items && items.length > 0 && type">{{types[type]}}的活动</div>
      <div class="userinfo-name" v-if="items && items.length == 0">还没有{{types[type]}}的活动</div>
    </div>
     <div class="weui-panel__bd">
       <activity :item="item" v-for="item in items" :key="item.id" :link-page= "type==5?'activityrecord':''"></activity>
     </div>

   </div>



</template>

<script>
import base64 from "../../../static/images/base64";
import global from '../../global/index';
import wxParse from 'mpvue-wxparse'
import activity from '@/components/activity.vue'
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
        '4':'我喜欢',
        '5':'需打卡'
      }
    };
  },
  computed: {
  },
  components: {
    wxParse,
    activity
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
    var that = this;
    if (this.$store.state.community) {
        this.community = this.$store.state.community
        this.type = this.$root.$mp.query.type
        
        // this.$kyutil.CheckUserValidation()
        // var user = this.$kyutil.GetUser()
        // if (user) {
        //   this.userId = user.id;
        //   this.getData();
        // }
        
        this.$kyutil.CheckUserValidation().then(function(res) {
          var user = that.$kyutil.GetUser();
          that.userId = user.id;
          that.getData();
        });
        
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
