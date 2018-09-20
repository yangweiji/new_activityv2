<template>
  <div class="page">

    <div class="page__bd">

      <kytabs_1 :tabs="categories" v-model="activeTab" @input="tabClick" :tab-count="4" :tab-width="200" />

      <div @click="articledetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" v-for="item in items" :key="item.id">
        <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;height">
          <image class="weui-media-box__thumb" :src="item.avatar" />
        </div>
        <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
          <div class="weui-media-box__title">{{item.title}}</div>
          <div class="weui-media-box__desc" style="float:left">{{item.summary}}</div>
          <div class="weui-media-box__desc" style="float:right">{{item.publish_time}}</div>
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
      //文章ID
      articleId: "",
      //默认的文章分类ID
      articleCategory: 1,
      //文章信息数组
      items: [],
      //文章分类数组
      categories: [
        { id: "1", name: "通知公告" },
        { id: "2", name: "赛事新闻" },
        { id: "3", name: "运动指南" },
        { id: "0", name: "其他" }
      ],
      //默认的选项卡片索引值
      activeIndex: "1"
    };
  },
  computed: {
  },
  components: {
    kytabs_1
  },
  methods: {
    //获取指定类别下的文章列表，参数从OnShow事件中获取
    getData() {
      var that = this;
      var param = {
        category: that.articleCategory
      };
      this.$kyutil
        .get("/pub/wx/article/getArticleListByCategory", param)
        .then(res => {
          that.items = res;
        });
    },
    //点击文章分类后触发事件，重新获取相应的信息
    tabClick(e) {
      this.activeIndex = e;
      this.articleCategory = e;
      this.getData();
    },
    //点击文章触发事件
    articledetails(articleId) {
      var that = this;
      that.articleId = articleId;
      wx.navigateTo({
        url: "../../pages/article/article?articleId=" + that.articleId
      });
      success: {
        console.log("-> article: articleId=" + articleId);
      }
    }
  },
  //页面创建完成
  created() {
    console.log("articlelist created");
  },
  //显示页面时，获取传递参数，加载获取数据
  onShow() {
    console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
    var that = this;
    that.articleCategory = this.$root.$mp.query.articleCategory;
    that.activeIndex = that.articleCategory;
    this.getData();
  }
};
</script>

<style scoped>
</style>
