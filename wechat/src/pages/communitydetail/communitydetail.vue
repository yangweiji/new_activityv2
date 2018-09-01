<template>
  <div class="page">
    <div class="page__hd">
      <kyimage :src="item.avatar" folder="activity" type="logo" />
      <div class="page__title">{{item.name}}</div>
      <div class="page__desc">{{item.description}}</div>
    </div>

    <div class="page__bd">
      <!-- banner begin -->
      <!-- <div>
        <navigator hover-class="navigator-hover">
            <span class="community-search">
              {{d}}
            </span>
        </navigator>  
        <kyimage src="fl01.png" folder="wx_home" type="banner" />
        <div class="c-bg"></div>
      </div> -->
      <!-- banner end -->

      <!-- 文字组合列表  start -->
      <div class="weui-panel weui-panel_access">
        <div class="weui-panel__hd">简介</div>
        <div class="weui-panel__bd">
          <div class="weui-media-box weui-media-box_text">
            <!-- <div class="weui-media-box__title weui-media-box__title_in-text">简介</div> -->
            <div class="weui-media-box__desc">
              <wxParse :content="item.about" />
            </div>
          </div>
        </div>
        <!-- <div class="weui-panel__ft">
          <div class="weui-cell weui-cell_access weui-cell_link">
            <div class="weui-cell__bd">查看更多</div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
          </div>
        </div> -->
      </div>
      <!-- 文字组合列表  end -->

      <!-- 小图文组合列表  start -->
      <div class="weui-panel">
        <!-- <div class="weui-panel__hd">其他</div> -->
        <div class="weui-panel__bd">
          <div class="weui-media-box weui-media-box_small-appmsg">
            <!-- <div class="weui-cells__title">带图标、说明的列表项</div> -->
            <div class="weui-cells weui-cells_after-title">
              <div class="weui-cell">
                <div class="weui-cell__hd">
                  <i class="fa fa-users"></i>
                </div>
                <div class="weui-cell__bd" style="margin-left:5px;">团体成员</div>
                <div class="weui-cell__ft">{{item.countPeople}}</div>
              </div>
            </div>

          </div>
        </div>
      </div>
      <!-- 小图文组合列表 end -->


    </div>
  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
import wxParse from "mpvue-wxparse";
import kyimage from '@/components/kyimage.vue'
import dateutil from '../../global/date.js';

export default {
  data() {
    return {
      icon: base64.icon20,
      icon20: base64.icon20,
      icon60: base64.icon60,
      //团体ID
      communityId: 1,
      //团体
      item: [],
    };
  },
  computed: {
  },
  components: {
    wxParse,
    kyimage
  },
  methods: {
    getData() {
      var that = this;
      var param = {
        communityId: that.communityId,
      }
      that.$kyutil.post("/pub/wx/community/detail", param).then(res =>{
        that.item = res;
      });
    },
    getDateFormat(src) {
      return dateutil.format(new Date(src), "yyyy年MM月dd日");
    }
  },
  created() {
  },
  onLoad() {
  },
  onShow() {
    var that = this;
    wx.setNavigationBarTitle({
      title: "团体组织"
    });

    //团体参数
    if (this.$root.$mp.query.id) {
      that.communityId = this.$root.$mp.query.id;
    }
    
    this.$kyutil.CheckUserValidation().then(function(res) {
      that.getData();
    });
  }
};
</script>

<style>
page,
.page,
.page__bd {
  height: 100%;
}
.page__bd {
  padding-bottom: 0;
}
.weui-tab {
  position: relative;
}
.weui-tab__content {
  padding-top: 0px;
  text-align: center;
}
.weui-navbar__slider_0 {
  left: 29rpx;
  transform: translateX(0);
}
.weui-navbar__slider_1 {
  left: 29rpx;
  transform: translateX(250rpx);
}
.weui-navbar__slider_2 {
  left: 29rpx;
  transform: translateX(500rpx);
}
.weui-media-box__desc {
  /* max-width: 230px; */
  /* text-overflow: ellipsis; */
  /* white-space: nowrap; */
  overflow: hidden;
  display: inline-block;
}
.weui-media-box__hd_in-appmsg {
  width: 80px;
  height: 80px;
}
.weui-media-box__title-l {
  float: left;
}
.weui-media-box__title-r {
  float: right;
  color: #999;
  font-size: 14px;
  top: -22px;
  position: relative;
}
.weui-badge-label {
  display: inline-block;
  padding: 0.4em 0.4em;
  min-width: 40px;
  border-radius: 18px;
  border: 1px solid #00c000;
  color: #00c000;
  line-height: 1.2;
  text-align: center;
  font-size: 12px;
  vertical-align: middle;
}

.page__desc {
  text-align: center;
}

</style>
