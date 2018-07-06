<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div class="weui-cells__title">
        打卡图片
      </div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <kyuploader @input="save()" :disabled="!editable" v-model="item.pictures"></kyuploader>
          </div>
        </div>
      </div>
      <div class="weui-cells__title">
        备注信息
      </div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell">
          <div class="weui-cell__bd">
            <textarea @change="save()" :disabled="!editable" placeholder="请输入..." v-model="item.notes" style="height: 3.3em" />
            <div class="weui-textarea-counter">0/200</div>
          </div>
        </div>
      </div>
    </div>
    <div v-if="item && editable" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="back()" class="weui-flex__item c-bg-primary">
        确定
      </div>
    </div>
  </div>
</template>

<script>
import kyuploader from '@/components/kyuploader.vue'
  export default {
    components:{kyuploader},
    data() {
      return {
        isIpx: false,
        editable:false,
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
        this.editable = !!this.activityUserId
        this.processing = true
        var param = {}
        if(this.recordId){
          param.recordId = this.recordId
        } else {
          param.activityUserId= this.activityUserId
        }
        this.$kyutil.get("/pub/wx/activityuserrecord/get", param, "GET").then(res => {
          if(res){
            this.item = res;
          } else {
            this.item = {recordTime:new Date(), activityUserId:this.activityUserId}
          }
          this.processing = false
        });
      },
      save() {
        this.processing = true
        this.$kyutil.post("/pub/wx/activityuserrecord/save", this.item).then(res => {
        })
      },
      back(){
        wx.navigateBack({
          delta: 1
        })
      }
      
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      var pages = getCurrentPages()

      //修改小程序 chooseImage 会触发页面重新 onShow 事件问题, url参数和缓存页面数据相同事， 不重新加载数据
      if(this.$root.$mp.query.uid == this.activityUserId && this.recordId == this.$root.$mp.query.id){
        return
      }
      
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
  .c-text {
    color: red;
  }
</style>
