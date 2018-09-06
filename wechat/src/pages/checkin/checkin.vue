<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <activity :item="item.activity"></activity>
      <div class="weui-cells weui-cells_after-title">
          <div class="weui-cell weui-cell_input">
            <div class="weui-cell__hd">
              <div class="weui-label">结束时间</div>
            </div>
            <div class="weui-cell__bd" style="text-align:right;">
              <span>{{item.activity.end_time}}</span>
            </div>
          </div>
        </div>

      <div class="weui-cells__title" >
        <h1 class="c-title-text" v-if="item.isOverdue">
            已超过活动结束时间，无法签到
        </h1>
        <h1 class="c-title-text" v-else-if="!item.checkInUserId">
          您未报名
        </h1>
        <h1 class="c-title-text" v-else-if="item.activity.activity_type == 3 && item.zqStatus != 2  ">
          未中签，无法签到
        </h1>
        <h1 class="c-title-text" v-else-if="!item.is_CheckInTimeNow">
          您已签到
        </h1>
        <h1 class="c-title-text" v-else>
          签到成功
        </h1>
      </div>
      <div class="weui-cells__title c-text-center">
        <h1 style="font-size:20px;">
          已签到人数
          <div class="c-text-primary c-checkin-num">
            <span>{{item.checkInCount}}</span>
          </div>
        </h1>
      </div>
      <!--<div class="weui-cells__title">
        <h1 class="am-article-title">
          您已签到
        </h1>
      </div>-->
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

      <div class="weui-btn-area">
        <navigator url="../../pages/index/index" open-type="switchTab" hover-class="navigator-hover">
          <button class="weui-btn" type="default">返回首页</button>
        </navigator>
      </div>
    </div>
  </div>
</template>

<script>
  import activity from '@/components/activity.vue'
  import store from '../../store'

  export default {
    components: {
      activity
    },
    data() {
      return {
        loaded: false,
        item: null,
        activityId: null,
        userId: 2128,
      }
    },
    methods: {
      getData() {
        var that = this
        var param = {
          activityId: that.activityId,
          userId: that.userId
        }
        this.$kyutil.get("/pub/wx/activity/checkin",param).then(res=>{
          that.item = res;
          that.loaded = true;
        })
      }
    },
    onShow() {
      var that = this;
      that.loaded = false
      that.activityId = this.$root.$mp.query.activityId || this.$root.$mp.query.scene;
      // this.$kyutil.CheckUserValidation();
      // var user = this.$kyutil.GetUser()
      // if (user) {
      //   this.userId = user.id
      //   this.getData();
      // }

      this.$kyutil.CheckUserValidation().then(function(res) {
          var user = that.$kyutil.GetUser();
          that.userId = user.id;
          that.getData();

          // wx.showModal({
          //   title: '活动签到',
          //   content: '感谢您参与此次活动，请点击【确定】完成活动报名签到！',
          //   confirmText: "确定",
          //   cancelText: "取消",
          //   success: function (res) {
          //     // console.log(res);
          //     if (res.confirm) {
          //       that.getData();
          //     } else {
          //       wx.switchTab({
          //         url:"../../pages/index/index",
          //         success: function (e) {
          //           console.log("登录成功，转向首页")
          //         }
          //       }); 
          //     }
          //   }
          // });
      });
    },
  }
</script>

<style>
  .c-checkin-num {
    font-weight: bold;
    font-size: 50px;
    margin: 30px;
  }
</style>
