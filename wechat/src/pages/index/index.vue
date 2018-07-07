<template>
  <div class="page">
    <div v-if="posters" class="page__bd">
      <!-- banner -->
      <div>
        <navigator url="../../pages/community/community" hover-class="navigator-hover" class="community-select">
          <span class="">
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
        <block v-for="(item,index) in grids" :key='index'>
          <navigator :url="item.url" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
            <image :src='item.src' style="width:50px;height:50px" />
            <div class="weui-grid_label" style="text-align:center">{{item.name}}</div>
          </navigator>
        </block>
      </div>
      <!-- navbar -->
      <div class="weui-tab">
        <kytabs :tabs="categories" v-model="activeTab" @input="tabClick" :tab-width="150" />
        <div class="weui-tab__panel">
          <div class="weui-panel weui-panel_access">
            <div class="weui-panel__bd">
              <activity :item="item" v-for="item in items" :key="item.id"></activity>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import activity from '@/components/activity.vue'
  import kytabs from "@/components/kytabs.vue";
  export default {
    data() {
      return {
        //ICON
        //活动信息
        items: [],
        //其他活动标签
        ces: "",
        //文章集合
        grids: [{
            src: "/static/images/images_news.png",
            name: "赛事活动",
            url: "/pages/posterlist/posterlist"
          },
          {
            src: "/static/images/activity_notices.png",
            name: "通知公告",
            url: "/pages/articlelist/articlelist?articleCategory=1"
          },
          {
            src: "/static/images/images_sport.png",
            name: "运动指南",
            url: "/pages/articlelist/articlelist?articleCategory=3"
          },
          {
            src: "/static/images/pictures.png",
            name: "活动相册",
            url: "/pages/photos/photos"
          }
        ],
        categories: [{
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
        activeTab: 'b5',
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
        posters: null
      };
    },
    computed: {
    },
    components: {
      activity,
      kytabs
    },
    methods: {
      //获取活动信息
      getData() {
        var that = this;
        var param = {
          communityId: that.community.id,
          t: that.activeTab
        }
        this.$kyutil.get("/pub/wx/home/data", param).then(res => {
          that.items = res.activities
          that.posters = res.posters
        })
      },
      search() {
        var that = this;
        that.items = null
        var param = {
          communityId: that.community.id,
          t: that.activeTab
        }
        this.$kyutil.get("/pub/wx/home/search", param).then(res => {
          that.items = res
        })
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
          })
      },
      //查看文章分类列表
      articlelist(articleCategory) {
        wx.navigateTo({
          url: "../../pages/articlelist/articlelist?articleCategory=" +
            articleCategory
        })
      }
    },
    //页面创建完成，获取活动信息
    created() {
    },
    onShow() {
      //接受参数
      if (this.$store.state.community) {
        this.community = this.$store.state.community;
        //设置标题
        wx.setNavigationBarTitle({
          title: this.community.name
        });
      }
      this.getData();
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
