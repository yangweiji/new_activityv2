<template>
  <div class="page">

    <div class="page__bd">
      <div class="weui-tab">
        <kytabs_1 :tabs="categories" v-model="activeTab" @input="tabClick" :tab-count="4" :tab-width="180" />

        <div @click="gotoDetails(item)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" v-for="item in items" :key="item.id">
          <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;height">
            <image class="weui-media-box__thumb" :src="item.mobile_avatar" />
          </div>
          <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
            <div class="weui-media-box__title">{{item.title}}</div>
            <div class="weui-media-box__desc" style="float:left"></div>
            <div class="weui-media-box__desc" style="float:right">{{item.created}}</div>
          </div>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import kytabs_1 from "@/components/kytabs_1.vue";
export default {
  data() {
    return {
      items: [],
      //文章分类数组
      categories: [
        { id: "n1", name: "赛事" },
        { id: "b5", name: "训练" },
        { id: "b12", name: "福利" },
        { id: "b7", name: "招募" },
      ],
      //默认的选项卡片索引值
      activeTab: "n1"
    };
  },
  computed: {},
  components: {
    kytabs_1
  },
  methods: {
    touchmove(e) {},
    getData() {
      var that = this;
      this.$kyutil
        .get("/pub/wx/poster/getPosters", { posterType: this.activeTab })
        .then(res => {
          that.items = res;
        });
    },
    tabClick() {
      this.getData();
    },
    gotoDetails(item) {
      if (item.activity_id) {
        wx.navigateTo({
          url: "../../pages/details/details?activityId=" + item.activity_id
        });
      }
    }
  },
  created() {},
  //显示页面时，获取传递参数，加载获取数据
  onShow() {
    var that = this;
    that.activeIndex = this.$root.$mp.query.category;
    if (!that.activeIndex) {
      that.activeIndex = 0;
    }
    this.getData();
  }
};
</script>

<style scoped>
</style>
