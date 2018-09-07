<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div class="weui-cells__title">
        活动结束后，请上传图片
      </div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <kyuploader @input="save()" v-model="item.afterFiles"></kyuploader>
          </div>
        </div>
      </div>
      
    </div>
    <div v-if="item" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="back()" class="weui-flex__item c-bg-primary">
        确定
      </div>
    </div>
  </div>
</template>

<script>
import kyuploader from "@/components/kyuploader.vue";
export default {
  components: { kyuploader },
  data() {
    return {
      isIpx: false,
      editable: false,
      activityId: null,
      loaded: false,
      item: null,
      recordId: null,
      userId: null,
      files: []
    };
  },
  computed: {},
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      this.processing = true;
      var param = {
        userId: that.userId,
        activityId: that.activityId    
      };
      this.$kyutil
        .get("/pub/wx/activity/getActivityUser", param, "GET")
        .then(res => {
          if (res) {
            this.item = res;
          }
          this.processing = false;
        });
    },
    save() {
      this.processing = true;
      this.$kyutil
        .post("/pub/wx/activity/afterUpload", this.item)
        .then(res => {});
    },
    back() {
      wx.navigateBack({
        delta: 1
      });
    }
  },
  created() {
    this.isIpx = this.$kyutil.data.isIpx;
  },
  onShow() {
    var that = this;
    // var pages = getCurrentPages();
    //修改小程序 chooseImage 会触发页面重新 onShow 事件问题, 使用state全局存储变量， 不重新加载数据
    if (that.$store.state.isUpload) {
      that.$store.state.isUpload = !that.$store.state.isUpload;
      return;
    }

    this.loaded = false;
    this.activityId = this.$root.$mp.query.activityId;

    this.$kyutil.CheckUserValidation().then(function(res) {
      var user = that.$kyutil.GetUser();
      that.userId = user.id;
      that.getData();
    });
  }
};
</script>

<style scoped>
.c-text {
  color: red;
}
</style>
