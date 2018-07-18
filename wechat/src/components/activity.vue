<template>
  <div @click="checkdetails(item.id)" class="c-activity-com weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
      <kyimage type="thumb" :src="item.avatar" />
    </div>
    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
      <div class="weui-media-box__title">{{item.title}}</div>
      <div class="weui-media-box__desc" style="float:left">{{item.start_time}}
        <!-- <span class="weui-badge" v-if="item.activity_type==1">活动</span>
        <span class="weui-badge" v-if="item.activity_type==2">赛事</span>
        <span class="weui-badge" v-if="item.activity_type==3">抽签</span> -->
        <span class="weui-badge" v-if="item.activity_type==4">打卡</span>
      </div>
      <div class="weui-media-box__desc" style="float:right">
        <label class="c-activity-label">
          <image src="/static/images/favorite.png" style="width: 18px;height:18px;" /> {{item.favorite_count}}</label>
        <label class="c-activity-label">
          <image src="/static/images/enter.png" style="width: 15px;height:15px;padding-right:5px" />{{item.attend_count}}</label>
      </div>
    </div>
  </div>
</template>

<script>
import kyimage from "@/components/kyimage.vue";
export default {
  name: "",
  props: ["item"],
  components: {
    kyimage
  },
  methods: {
    checkdetails(activityId) {
      if (activityId)
        wx.navigateTo({
          url: "../../pages/details/details?activityId=" + activityId
        });
      success: {
      }
    }
  }
};
</script>

<style scoped>
</style>
