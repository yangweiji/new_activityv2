<template>
  <div class="page">

    <div class="page__bd">
        <div class="weui-tab">
        <kytabs :tabs="categories" v-model="activeTab" :tab-width="200" />

        <div class="weui-tab__panel">
            
            <div class="weui-panel weui-panel_access">
              <div class="weui-panel__bd">
                <div @click="gotoDetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in tabPosters" :key="item.id">    
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
        
      </div>
    </div>

  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
import kytabs from "@/components/kytabs.vue";
export default {
  data() {
    return {
      items: [],
      //文章分类数组
      categories: [
        { id: "n1", name: "赛事新闻" },
        { id: "b5", name: "跑步训练" },
        { id: "b13", name: "运动装备" },
        { id: "b12", name: "会员福利" },
        { id: "b10", name: "国内赛事" },
        { id: "b11", name: "国际赛事" },
        { id: "b1", name: "徒步" },
        { id: "b2", name: "越野" },
        { id: "b3", name: "聚餐" },
        { id: "b4", name: "骑行" },
        { id: "b6", name: "会议" },
        { id: "b7", name: "招募" },
        { id: "b8", name: "讲座" }
      ],
      //默认的选项卡片索引值
      activeTab: "n1"
    };
  },
  computed: {
    tabPosters() {
      var posters = [];
      if (this.items) {
        for (var i = 0; i < this.items.length; i++) {
          var item = this.items[i];
          if (item.poster_type == this.activeTab) {
            posters.push(item);
          }
        }
      }
      return posters;
    }
  },
  components: {
    kytabs
  },
  methods: {
    touchmove(e) {},
    getData() {
      var that = this;
      this.$kyutil.get("/pub/wx/poster/getPosters").then(res => {
        this.items = res;
      });
    },
    gotoDetails(item) {
      wx.navigateTo({
        url: "../../pages/details/details?activityId=" + item.activity_id
      });
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
