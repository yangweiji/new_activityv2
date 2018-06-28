<template>
  <div class="page">
    <div v-if="item" class="page__bd">
    </div>
  </div>

</template>

<script>
export default {
  data() {
    return {
      xs: 1,
      userId:null,
      communityId: null,
      item:null
    };
  },
  components: {},
  methods: {
    getData() {
      var that = this;
      var param = {
        communityId: that.communityId,
        userId: this.userId
      };
      this.$kyutil.get("/pub/wx/profile/vip",param).then(res => this.item = res)
    },
  },
  created() {
  },
  onLoad() {
    this.$kyutil.CheckUserValidation();
  },
  onShow() {
    //接受参数
    if (this.$store.state.community) {
      var community = this.$store.state.community;
      this.communityId = community.id
      
      this.$kyutil.CheckUserValidation();
      var user = this.$kyutil.GetUser();
      if (user) {
        this.userId = user.id;
        this.getData();
      }
      wx.setNavigationBarTitle({
        title: community.name
      })
    }
  }
};
</script>

<style scoped>

</style>
