<template>
  <div class="page">

    <div class="page__bd">
        <div class="weui-tab">
        <div class="weui-navbar" style="top:auto;">
            <block v-for="(item,index) in categories" :key="index">
              <div :id="index" :class="{'weui-bar__item_on' :activeIndex==item.id}" class="weui-navbar__item"  
                @click="tabClick(item.id)">
                <div class="weui-navbar__title">{{item.title}}</div>
              </div>
            </block>
          <div class="weui-navbar__slider" :class="navbarSliderClass"></div>
        </div>

        <div class="weui-tab__panel">
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != '1'">
              <div class="weui-panel__bd">
                <div @click="articledetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
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
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != '2'">
              <div class="weui-panel__bd">
                <div @click="articledetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
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

            <div class="weui-panel weui-panel_access" :hidden="activeIndex != '3'">
              <div class="weui-panel__bd">
                <div @click="articledetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
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
            
            <div class="weui-panel weui-panel_access" :hidden="activeIndex != '0'">
              <div class="weui-panel__bd">
                <div @click="articledetails(item.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" 
                  v-for="item in items" :key="item.id">    
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
        </div>
        
      </div>
    </div>

  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
import global from '../../global/index'
export default {
  data() {
    return {
      //文章ID
      articleId: "",
      //默认的文章分类ID
      articleCategory: 1,
      //文章信息数组
      items:[],
      //文章分类数组
      categories: [
        { id: "1", title: "通知公告" },
        { id: "2", title: "赛事新闻" },
        { id: "3", title: "运动指南" },
        { id: "0", title: "其他" }
      ],
      //默认的选项卡片索引值
      activeIndex: "1",
    };
  },
  computed: {
    navbarSliderClass() {
      if (this.activeIndex == "1") {
        return "weui-navbar__slider_0";
      }
      if (this.activeIndex == "2") {
        return "weui-navbar__slider_1";
      }
      if (this.activeIndex == "3") {
        return "weui-navbar__slider_2";
      }
      if (this.activeIndex == "0") {
        return "weui-navbar__slider_3";
      }
    }
  },
  components: {
  },
  methods: {
    //获取指定类别下的文章列表，参数从OnShow事件中获取
    getData() {
      var that = this;
      var param = {
        category: that.articleCategory
      };
      global.HttpRequest(true, "/pub/wx/article/getArticleListByCategory", false, "", param, "GET", false, function (res) {
        that.items = res;
      });
      // wx.request({
      //   url: "https://a.9kylin.cn/pub/wx/article/getArticleListByCategory", //仅为示例，并非真实的接口地址
      //   data: {
      //     category: that.articleCategory
      //   },
      //   header: {
      //     "content-type": "application/json" // 默认值
      //   },
      //   success: function(res) {
      //     console.log(res.data);
      //     that.items = res.data;
      //   },
      //   fail: function(error) {
      //     console.log(error);
      //   }
      // });
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
  onShow () {
    console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    var that = this;
    that.articleCategory = this.$root.$mp.query.articleCategory;
    that.activeIndex = that.articleCategory;
    this.getData();
  }
};
</script>

<style scoped>
.weui-navbar__slider_0 {
  left: 0;
  transform: translateX(0);
}
.weui-navbar__slider_1 {
  left: 35rpx;
  transform: translateX(150rpx);
}
.weui-navbar__slider_2 {
  left: 70rpx;
  transform: translateX(300rpx);
}
.weui-navbar__slider_3 {
  left: 105rpx;
  transform: translateX(450rpx);
}
.weui-navbar__slider_4 {
  left: 35rpx;
  transform: translateX(600rpx);
}
.weui-panel {
  background-color: #fff;
  margin-top: 0;
  position: relative;
  overflow: hidden;
}
</style>
