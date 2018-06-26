<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div class="weui-cells__title">
        上传图片
      </div>
      <kyuploader v-model="item.pictures"></kyuploader>
      <div class="weui-cells__title">
        备注信息
      </div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <textarea class="" placeholder="请输入..." v-model="item.notes" style="height: 3.3em" />
            <div class="weui-textarea-counter">0/200</div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="item && activityUserId" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="save()" class="weui-flex__item c-bg-primary">
        提交
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        isIpx: false,
        activityUserId: null,
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
        var that = this
        this.processing = true
        var param = {}
        if(this.recordId){
          param.recordId = this.recordId
        } else {
          param.activityUserId= this.activityUserId
        }
        this.$kyutil.get("/pub/wx/activityuserrecord/get", param).then(res => {
          if(res){
            that.item = res;
          } else {
            that.item = {recordTime:new Date(), activityUserId:that.activityUserId}
          }
          that.processing = false
        });
      },
      save() {
        var that = this
        this.processing = true
        this.$kyutil.post("/pub/wx/activityuserrecord/get", this.item).then(res => {
          that.item = res;
          that.processing = false
        })
      }
      
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      this.loaded = false;
      this.recordId =
        this.$root.$mp.query.id
      this.activityUserId = this.$root.$mp.query.uid
      this.$kyutil.CheckUserValidation();
      var user = this.$kyutil.GetUser();
      if (user) {
        this.userId = user.id;
        this.getData();
      }
    }
  };
</script>

<style scoped>
</style>
