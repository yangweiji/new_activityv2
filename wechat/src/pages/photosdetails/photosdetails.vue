<template>
  <div class="page">
    <div class="page__hd">
      {{name}}
    </div>
    <div class="page__bd">
    <div class="weui-photosdetails__files weui-photosdetails__img" >
      <block v-for="item in items" :key="item.id">

        <kyimage :preview="true" :src="item.picture" />

      </block>
    </div>
    </div>
  </div>

</template>

<script>
import kyimage from '@/components/kyimage.vue'
export default {
  data() {
    return {
      activityId: 0,
      items:null,
      name: ''
    };
  },
  components: {kyimage},
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
        activityId: that.activityId
      };
      this.$kyutil.get("/pub/wx/photo/getPicturesByActivityId",param).then( res => {
          that.items = res.pictures
          that.name = res.description
        }
      );
    }
   
  },
  created() {
   
  },
  onShow() {
    var that = this;
    that.activityId = this.$root.$mp.query.activityId;
    this.getData();
  }
};
</script>

<style>
.weui-photosdetails__img image{
  height: 100px;
  width: 48%;
  padding-right: 3.5px;
  padding-left: 3.5px;
}
</style>
