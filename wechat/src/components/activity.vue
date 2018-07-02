<template>
  <div @click="checkdetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active">
    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
      <kyimage type="thumb" :src="item.avatar" />
    </div>
    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
      <div class="weui-media-box__title">{{item.title}}</div>
      <div class="weui-media-box__desc" style="float:left">{{item.start_time}}</div>
      <div class="weui-media-box__desc" style="float:right">喜欢：{{item.favorite_count}} 报名：{{item.attend_count}}</div>
    </div>
  </div>
</template>

<script>
  import kyimage from '@/components/kyimage.vue'
  export default {
    name: '',
    props: ['item'],
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
  }
</script>

<style>

</style>
