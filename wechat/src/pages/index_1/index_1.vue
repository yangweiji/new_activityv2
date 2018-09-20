<template>
  <div class="page">
    <div v-if="posters" class="page__bd">
      <!-- banner -->
      <div>
        <navigator url="../../pages/community/community" hover-class="navigator-hover" class="community-select">
          <span class="">
            <i class="fa fa-exchange" aria-hidden="true"></i>
            切换团体
          </span>
        </navigator>
        <div>
          <swiper :indicator-dots="indicatorDots" :autoplay="autoplay" :interval="interval" :duration="duration" :circular="circular">
            <div v-for="item in posters" :key="item.id">
              <swiper-item>
                <image mode="aspectFill" :src="item.mobile_avatar" @click="checkdetails(item.activity_id)" class="slide-image" />
                <span class="poster_title">
                  {{item.title}}
                </span>
              </swiper-item>
            </div>
          </swiper>
        </div>
      </div>
      <!-- 通知公告、赛事新闻、运动指南、活动相册 -->
      <div class="weui-grids c-blocks">
        <div class="c-kynav" @touchmove="touchmove">
          <!-- <block v-for="(item,index) in grids" :key='index'>
            <navigator :url="item.url" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image :src='item.src' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">{{item.name}}</div>
            </navigator>
          </block> -->

          <block>
            <navigator url="/pages/posterlist/posterlist" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/images_news.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">赛事</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/articlelist/articlelist?articleCategory=1" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/activity_notices.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">公告</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/articlelist/articlelist?articleCategory=3" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/images_sport.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">指南</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/photos/photos" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/pictures.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">相册</div>
            </navigator>
          </block>

          <!-- 测试跳转到另外一个小程序中 -->
          <block>
            <navigator target="miniProgram" app-id="wxfe00747cfa9a07f9" path="/pages/index/index" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/store.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">燃宁</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/posterlist/posterlist" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/images_news.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">赛事</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/articlelist/articlelist?articleCategory=1" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/activity_notices.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">公告</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/articlelist/articlelist?articleCategory=3" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/images_sport.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">指南</div>
            </navigator>
          </block>

          <block>
            <navigator url="/pages/photos/photos" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/pictures.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">相册</div>
            </navigator>
          </block>

          <!-- 测试跳转到另外一个小程序中 -->
          <block>
            <navigator target="miniProgram" app-id="wxfe00747cfa9a07f9" path="/pages/index/index" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
              <image src='/static/images/store.png' style="width:50px;height:50px" />
              <div class="weui-grid_label" style="text-align:center">燃宁</div>
            </navigator>
          </block>
        </div>
      </div>

      <!-- tab导航栏 -->
      <!-- scroll-left属性可以控制滚动条位置 -->
      <!-- scroll-with-animation滚动添加动画过渡 -->
      <!-- <scroll-view scroll-x="true" class="c-nav" :scroll-left="navScrollLeft" scroll-with-animation="true">
        <block v-for="(navItem, index) in categories" :key="index">
          <view class="c-nav-item" :class="currentTab == index ?'active':''" :data-current="index" @click="switchNav">{{navItem.name}}</view>
        </block>
      </scroll-view> -->

      <!-- 页面内容 -->
      <!-- <swiper class="c-tab-box" :current="currentTab" duration="300" @change="switchTab">
        <block v-for="(tabItem, index) in swipers" :key="index">
          <swiper-item class="c-tab-content">
            <view class="">AAAAAAAAAAAAAAAAAAAAAAAAAAA{{tabItem}}</view>
          </swiper-item>
        </block>
      </swiper> -->

      <!-- navbar -->
      <div class="weui-tab">
        <kytabs_1 :tabs="categories" v-model="activeTab" @input="tabClick" :tab-count="5" />
        <activity :item="item" v-for="item in items" :key="item.id"></activity>
      </div>

    </div>
  </div>
</template>

<script>
import activity from "@/components/activity.vue";
import kytabs from "@/components/kytabs.vue";
import kytabs_1 from "@/components/kytabs_1.vue";
export default {
  data() {
    return {
      //ICON
      //活动信息
      items: [],
      //其他活动标签
      ces: "",
      // //文章集合
      // grids: [
      //   {
      //     src: "/static/images/images_news.png",
      //     name: "赛事",
      //     url: "/pages/posterlist/posterlist"
      //   },
      //   {
      //     src: "/static/images/activity_notices.png",
      //     name: "公告",
      //     url: "/pages/articlelist/articlelist?articleCategory=1"
      //   },
      //   {
      //     src: "/static/images/images_sport.png",
      //     name: "指南",
      //     url: "/pages/articlelist/articlelist?articleCategory=3"
      //   },
      //   {
      //     src: "/static/images/pictures.png",
      //     name: "相册",
      //     url: "/pages/photos/photos"
      //   }
      // ],
      categories: [
        {
          id: "b5",
          name: "训练"
        },
        {
          id: "b13",
          name: "装备"
        },
        {
          id: "b12",
          name: "福利"
        },
        {
          id: "b10,b11",
          name: "赛事"
        },
        {
          id: "b1",
          name: "徒步"
        },
        {
          id: "b2",
          name: "越野"
        },
        {
          id: "b3",
          name: "聚餐"
        },
        {
          id: "b4",
          name: "骑行"
        },
        {
          id: "b6",
          name: "会议"
        },
        {
          id: "b7",
          name: "招募"
        },
        {
          id: "b8",
          name: "讲座"
        },
        {
          id: "b10",
          name: "国内赛事"
        },
        {
          id: "b11",
          name: "国际赛事"
        }
      ],
      activeTab: null,
      //活动标签分类
      //默认的团体组织
      community: null,
      //是否显示面板指示点
      indicatorDots: true,
      //是否自动切换
      autoplay: true,
      //自动切换时间间隔
      interval: 5000,
      //滑动动画时长
      duration: 900,
      //是否采用衔接滑动
      circular: true,
      //图片的url地址
      imgUrls: [],
      posters: null,

      // currentTab: 0,
      // navScrollLeft: 0,
      // pixelRatio: null,
      // windowHeight: null,
      // windowWidth: null
    };
  },
  computed: {},
  components: {
    activity,
    kytabs,
    kytabs_1
  },
  methods: {
    //获取活动信息
    getData() {
      var that = this;
      that.items = null;
      var param = {
        communityId: that.community.id,
        t: that.activeTab
      };
      this.$kyutil.get("/pub/wx/home/data", param).then(res => {
        that.items = res.activities;
        that.posters = res.posters;
      });
    },
    search() {
      var that = this;
      that.items = null;
      var param = {
        communityId: that.community.id,
        t: that.activeTab
      };
      this.$kyutil.get("/pub/wx/home/search", param).then(res => {
        that.items = res;
      });
    },
    //主要活动标签分类触发事件，重新获取相应的数据
    tabClick(e) {
      this.search();
    },
    //查看活动详情
    checkdetails(activityId) {
      if (activityId)
        wx.navigateTo({
          url: "../../pages/details/details?activityId=" + activityId
        });
    },
    //查看文章分类列表
    articlelist(articleCategory) {
      wx.navigateTo({
        url:
          "../../pages/articlelist/articlelist?articleCategory=" +
          articleCategory
      });
    },

    // switchNav(event, item) {
    //   var cur = event.mp.currentTarget.dataset.current;
    //   //每个tab选项宽度占1/5
    //   var singleNavWidth = this.windowWidth / 5;
    //   //tab选项居中
    //   this.navScrollLeft = (cur - 2) * singleNavWidth;
    //   if (this.currentTab == cur) {
    //     return false;
    //   } else {
    //     this.currentTab = cur;
    //   }

    //   this.activeTab = item.id;
    //   this.tabClick(event);
    // },
    // switchTab(event) {
    //   var cur = event.mp.detail.current;
    //   var singleNavWidth = this.windowWidth / 5;
    //   this.currentTab = cur;
    //   this.navScrollLeft = (cur - 2) * singleNavWidth;
    // }
  },
  //页面创建完成，获取活动信息
  created() {},
  onLoad() {
    // var that = this;
    // wx.getSystemInfo({
    //   success: res => {
    //     (that.pixelRatio = res.pixelRatio),
    //       (that.windowHeight = res.windowHeight),
    //       (that.windowWidth = res.windowWidth);
    //   }
    // });
  },
  onShow() {
    console.log("首页显示...");
    var that = this;
    if (!that.activeTab) {
      that.activeTab = this.$root.$mp.query.activeTab || "b5"; //训练
      //如果有参数值，获取北京市马拉松协会的活动内容
      if (this.$root.$mp.query.activeTab) {
        that.community = {
          id: 1, //默认的组织团体ID
          name: "北京市马拉松协会",
          background: "NzrSDNSBEP.png"
        };
        //设置标题
        wx.setNavigationBarTitle({
          title: that.community.name
        });
        that.getData();
        return;
      }
    }

    //切换团体组织后的处理
    if (!that.$store.state.community) {
      //根据用户登录后获取的团体组织信息加载活动内容
      this.$kyutil.Login().then(function(res) {
        that.community = that.$store.state.community;
        //设置标题
        wx.setNavigationBarTitle({
          title: that.community.name
        });

        that.getData();
      });
    } else {
      that.community = that.$store.state.community;
      //设置标题
      wx.setNavigationBarTitle({
        title: that.community.name
      });
      that.getData();
    }
  }
};
</script>

<style scoped>
.slide-image {
  width: 100%;
  height: 100%;
}
.poster_title {
  font-size: 12px;
  color: #fff;
  background-color: #fff;
}
.c-blocks {
  border-top: 0px;
  border-left: 0px;
  background-color: #ffffff;
  text-align: center;
}
</style>
