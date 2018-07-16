<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div>
        <!-- banner -->
        <div>
          <navigator url="../../pages/community/community" hover-class="navigator-hover" class="community-select">
            <span style="top:5px;">
                切换团体
              </span>
          </navigator>
          <!-- <kyimage :src="community.background" model="aspectFit" type="banner" /> -->
          <!-- <div class="community-avatar">
            <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
            <open-data class="userinfo-name" type="userNickName"></open-data>
          </div> -->
          <div class="community-avatar">
            <kyimage :src="user.avatar" type="avatar" />
            <span class="userinfo-name">{{user.nickName}}</span>
          </div>
        </div>
        <div class="wx_group_count weui-flex">
          <div class="wx_attend_count weui-flex__item">
            <navigator url="/pages/myactivitys/myactivitys?type=1">
              <dl>
                <dt>
              {{item.activityCounts[0].counts}}
             </dt>
                <dd>
                  已参与
                </dd>
              </dl>
            </navigator>
          </div>
          <div class="wx_no_check_count weui-flex__item" >
            <navigator url="/pages/myactivitys/myactivitys?type=2">
              <dl>
                <dt>
            {{item.activityCounts[1].counts}}
             </dt>
                <dd>
                  需签到
                </dd>
              </dl>
            </navigator>
          </div>
          <div class="wx_check_count weui-flex__item" >
            <navigator url="/pages/myactivitys/myactivitys?type=3">
              <dl>
                <dt>
               {{item.activityCounts[2].counts}}
             </dt>
                <dd>
                  已签到
                </dd>
              </dl>
            </navigator>
          </div>
          <div class="wx_favorite_count weui-flex__item" >
            <navigator url="/pages/myactivitys/myactivitys?type=4">
              <dl>
                <dt>
                {{item.activityCounts[3].counts}}
             </dt>
                <dd>
                  我喜欢
                </dd>
              </dl>
            </navigator>
          </div>
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
          <navigator url="/pages/userinfo/userinfo" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/personal_information.png" style="width: 25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>个人信息</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
          </navigator>
          <navigator url="/pages/integrals/integrals" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/integral_image.png" style="width:25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>积分</div>
            </div>
            <div class="weui-cell_integral">{{item.score}}</div>
          </navigator>
          
          <navigator url="/pages/realinfo/realinfo" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/real_name.png" style="width: 25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>实名认证</div>
            </div>
            <div class="weui-cell_comment">{{item.user.isReal!=true?"未认证":"已认证"}}</div>
          </navigator>
          <navigator url="/pages/myvip/myvip" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/vip_image.png" style="width: 25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>会员</div>
            </div>
            <div class="weui-cell_comment">{{vipText}}</div>
          </navigator>
          <!-- 如下为全局功能 -->
          
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import kyimage from '@/components/kyimage.vue'

  export default {
    data() {
      return {
        xs: 1,
        userId: null,
        community: null,
        item: null,
        user: null,
      };
    },
    components:{
      kyimage
    },
    methods: {
      getData() {
        var that = this;
        var param = {
          communityId: that.community.id,
          userId: this.userId
        };
        this.$kyutil.get("/pub/wx/profile/info", param).then(res => this.item = res)
      },
      qh(cs) {
        this.xs = cs;
      },
    //   getAvatar: function () {
    //     var that = this;
    //     that.user = this.$kyutil.GetUser();
    //     var avatar = that.user.avatar;
    //     if (avatar) {
    //       if(avatar && (avatar.toLowerCase().indexOf('http://') == 0 || avatar.toLowerCase().indexOf('https://') == 0)){
    //           return avatar
    //       }
    //       var url = 'https://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/' + avatar
    //       url += '?x-oss-process=style/small'
    //       return url;
    //     }
    //     else {
    //         return '/img/activity/man.png'
    //     }
    //   },
    },
    computed: {
      vipText() {
        if (this.item) {
          return this.item.vipYear < (new Date().getFullYear()) ? "非会员" : this.item.vipYear
        }
        return ''
      }
    },
    created() {
      console.log();
    },
    onLoad() {
      this.$kyutil.CheckUserValidation();
    },
    onShow() {
      //接受参数
      if (this.$store.state.community) {
        this.community = this.$store.state.community;
        this.$kyutil.CheckUserValidation();
        var user = this.$kyutil.GetUser();
        this.user = user;
        if (user) {
          this.userId = user.id;
          this.getData();
        }
        wx.setNavigationBarTitle({
          title: this.community.name
        });
      }
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
  .wx_counts {
    width: 100%;
    height: 60px;
  }
  .wx_group_count .weui-flex__item{
    padding: 22px;
  }
  .wx_group_count dl dt {
    text-align: center;
    color: #f37b1d;
  }
  .wx_group_count dd {
    color: #8a8a8a;
  }
  .weui-cell_integral {
    height: 100%;
    width: 30px;
    /* background-color: #1296db; */
    text-align: center;
    /* color: #ffffff; */
    border-radius: 50%;
  }
  .weui-cell_comment {
    height: 30%;
    width: 55px;
    /* background-color: #1296db; */
    text-align: center;
    /* color: #ffffff; */
    border-radius: 3px;
  }
  .user-avatar {
    height: 65px;
    width: 65px;
  }
</style>
