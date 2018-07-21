<template>
  <div class="page">
    <div class="page__hd">
      <div class="photo-title">
        {{name}} 
        <span class="photo-img-r"><i class="fa fa-eye"></i> {{browse_count}} 次浏览</span>
      </div>
    </div>

    <div class="page__bd">
      <div class="weui-photosdetails__files weui-photosdetails__img">
        <block v-for="item in items" :key="item.id">
          <kyimage :preview="true" :src="item.picture" />
        </block>
      </div>
    </div>
  </div>

</template>

<script>
import kyimage from "@/components/kyimage.vue";
export default {
  data() {
    return {
      activityId: 0,
      items: null,
      name: "",
      browse_count: 0
    };
  },
  components: { kyimage },
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
        activityId: that.activityId
      };
      this.$kyutil
        .get("/pub/wx/photo/getPicturesByActivityId", param)
        .then(res => {
          console.log(res);
          that.items = res.pictures;
          that.name = res.description;
          that.browse_count = res.browse_count;
          console.log(browse_count);
        });
    }
  },
  created() {},
  onShow() {
    var that = this;
    that.activityId = this.$root.$mp.query.activityId;
    this.getData();
  }
};
</script>

<style>
.page__hd {
  height: 60px;
  padding: 20px;
}
.weui-photosdetails__img image {
  height: 100px;
  width: 48%;
  padding-right: 3.5px;
  padding-left: 3.5px;
}
.photo-title {
  float: left;
  font-size: 18px;
}
.photo-img-l {
  color: #999;
  font-size: 12px;
  float: left;
  margin-left: 10px;
}
.photo-img-r {
  color: #999;
  font-size: 12px;
  float: right;
  margin-right: 10px;
  margin-top: 5px;
}
</style>
