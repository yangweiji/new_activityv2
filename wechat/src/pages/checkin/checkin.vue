<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div class="weui-cells__title" v-if="!item.checkInTime">
          <h1 class="c-title-text">
            报名截止时间已过
          </h1>
        </div>

        <div class="weui-cells__title">
            <h1 class="am-article-title">
              当前签到人数
              <span style="color:#F37B1D;font-size:25px;font-weight: bold;">
                <span style="color:#F37B1D;font-size:25px;font-weight: bold;" >{{item.checkInCount}}</span>
              </span>
            </h1>
          </div>
          <div class="weui-cells__title">
            <h1 class="am-article-title">
              您已签到
            </h1>
          </div>
          <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
              <div v-if="item.checkInScore > 0" class="weui-form-preview__item">
                <div class="weui-form-preview__label">签到获得积分:</div>
                <div class="weui-form-preview__value_in-hd">{{item.checkInScore}}</div>
              </div>
               <div v-if="item.checkInTime" class="weui-form-preview__item">
                <div class="weui-form-preview__label">签到时间:</div>
                <div class="weui-form-preview__value_in-hd">{{item.checkInTime}}</div>
              </div>
            </div>
        </div>

        <div @click="checkdetails(item.activity.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" >
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.activity.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.activity.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.activity.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.activity.favorite_count}} 报名：{{item.activity.attend_count}}</div>
                    </div>
                </div>
          
    </div>
  </div>
</template>

<script>
export default {
  data() {
   return {
      loaded:false,
      item:null,
      activityId:null,
      userId: 2128,
   }
  },
  methods: {
    getData(){
      var that = this
      var param = {
        activityId: that.activityId,
        userId: that.userId
      }
      this.$kyutil.HttpRequest(
        true,
        "/pub/wx/activity/checkin",
        false,
        "",
        param,
        "GET",
        false,
        function(res) {
          that.item = res;
          that.loaded = true;
        }
      )
    },
    checkdetails(activityId) {
      var that = this;
      that.activityId = activityId;
      wx.navigateTo({
        url: "../../pages/details/details?activityId=" + that.activityId
      })
    }
   
  },
  onShow() {
    var that = this;
    that.loaded = false
    that.activityId = this.$root.$mp.query.activityId || this.$root.$mp.query.scene;
    this.$kyutil.CheckUserValidation();
    var user = this.$kyutil.GetUser()
    if(user){
      this.userId = user.id
      this.getData();
    }
  },
}
</script>

<style>

</style>
