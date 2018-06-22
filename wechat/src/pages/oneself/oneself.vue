<template>
  <div class="page">
    <div v-if="xs==1">
      <!-- banner -->
      <div>
        <navigator url="../../pages/community/community" hover-class="navigator-hover">
          <span class="community-select" >
            切换 >>
          </span>
        </navigator>  
        <!-- <image src="../../static/images/banner_bg.png" class="banner" model="aspectFit" /> -->
        <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
      <open-data class="userinfo-name" type="userNickName"></open-data> 
     
      </div>

      <!-- <div>
          <div style="position:absolute;right:0;margin-top:10px;margin-right:20px;" @click="qh(2)">
             切换到管理者 >>
          </div>
          <div style="position:absolute;margin-top:15%;width:100%;">
           <images :src="'../../../static/images/a2.jpg'" style="width:4rem;height:4rem;border-radius:50%;margin-left:40%"></images>
            <p class="p-text__xx">用户信息【参与者】</p>
            <p class="p-text__xx">用户加入的团体信息</p>
          </div>
          <image style="height:200px; width:100%;" />
      </div> -->
       
      <div>
        <navigator url="/pages/myactivitys/myactivitys" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>我的活动</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
          </navigator> 

        <navigator url="/pages/integrals/integrals" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>积分</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
        </navigator> 

        

        <navigator url="" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>会员</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
        </navigator> 

        <!-- 如下为全局功能 -->
        <navigator url="" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>实名认证</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
        </navigator> 

        <navigator url="" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>我加入的团体</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
        </navigator> 

      </div>
  </div>

  <div v-if="xs==2" :hidden=true>
      
      <div>
          <div style="position:absolute;right:0;margin-top:10px;margin-right:20px;" @click="qh(1)">
             切换到参与者 >>
          </div>
          <div style="position:absolute;margin-top:15%;width:100%;">
           <images :src="'../../../static/images/a2.jpg'" style="width:4rem;height:4rem;border-radius:50%;margin-left:40%"></images>
            <p class="p-text__xx">用户信息【管理者】</p>
            <p class="p-text__xx">用户加入的团体信息</p>
          </div>
          <images style="height:200px; width:100%;"></images>
      </div>
      <div>
            <navigator url="" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
                <div class="weui-cell__hd">
                  <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
                </div>
                <div class="weui-cell__bd weui-cell_primary">
                  <div>我发布的活动</div>
                </div>
                <div class="weui-cell__ft weui-cell__ft_in-access"></div>
              </navigator> 

            <navigator url="" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
                <div class="weui-cell__hd">
                  <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
                </div>
                <div class="weui-cell__bd weui-cell_primary">
                  <div>加入团队人员</div>
                </div>
                <div class="weui-cell__ft weui-cell__ft_in-access"></div>
            </navigator> 

            <navigator url="" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
                <div class="weui-cell__hd">
                  <image :src="icon20" style="width: 20px;height: 20px;margin-right: 5px" />
                </div>
                <div class="weui-cell__bd weui-cell_primary">
                  <div>团队会员</div>
                </div>
                <div class="weui-cell__ft weui-cell__ft_in-access"></div>
            </navigator> 

      </div>
   </div>
  </div>
</template>

<script>
import base64 from "../../../static/images/base64";
export default {
  data() {
    return {
      icon20: base64.icon20,
      xs: 1,
      
      community: {
        id: 1, //默认的组织团体ID
        communityName: "北京市马拉松协会",
        background: "NzrSDNSBEP.png",
      }
    };
  },
  components: {},
  methods: {
    getData() {

    },
    qh(cs) {
      this.xs = cs;
    }
  },
  created() {
    console.log("oneself created");
  },
  onLoad() {
    this.$kyutil.CheckUserValidation();
    if (wx.getStorageSync("user")) {
      this.getData();
    }
  },
  onShow() {
    console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
    //接受参数
    if (this.$store.state.community) {
      this.community = this.$store.state.community;
      //设置标题
      wx.setNavigationBarTitle({
        title: this.community.name
      });
    }
    this.getData();

    // wx.setNavigationBarTitle({
    //   title: "个人中心"
    // });
  }
};
</script>

<style scoped>
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */

.weui-flex {
  -webkit-box-align: center;
  -webkit-align-items: center;
  align-items: center;
}

.weui-cells {
  margin-top: 0;
  opacity: 0;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  -webkit-transition: 0.3s;
  transition: 0.3s;
}

.weui-cells:after,
.weui-cells:before {
  display: none;
}

.weui-cells_show {
  opacity: 1;
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.weui-cell:before {
  right: 15px;
}
.community-select{
  font-family:'微软雅黑' 12px ;
  color: #000000;
}
.kind-list__item {
  margin: 10px 0;
  background-color: #fff;
  border-radius: 2px;
  overflow: hidden;
}

.kind-list__item:first-child {
  margin-top: 0;
}

.kind-list__img {
  width: 30px;
  height: 30px;
}

.kind-list__item-hd {
  padding: 20px;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
}

.kind-list__item-hd_show {
  opacity: 0.4;
}

.kind-list__item-bd {
  height: 0;
  overflow: hidden;
}

.kind-list__item-bd_show {
  height: auto;
}

.p-text__xx {
  width: 100%;
  text-align: center;
}
.userinfo-avatar {
  margin: 0 auto;  
  margin-top: 50rpx;
  display: flex;
  justify-content: center;
  overflow: hidden;
  width: 161rpx;
  height: 161rpx;
  border-radius: 50%;
}
.userinfo-name {
  margin: 0 auto;  
  margin-top: 20rpx;
  display: flex;
  justify-content: center;
  overflow: hidden;
}
</style>
