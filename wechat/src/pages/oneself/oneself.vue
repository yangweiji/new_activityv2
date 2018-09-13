<template>
  <div class="page">
    <div v-if="item" class="page__bd">

        <!-- banner begin -->
        <div>
          <navigator url="../../pages/community/community" hover-class="navigator-hover" class="community-select">
            <span style="top:5px;">
              <i class="fa fa-exchange" aria-hidden="true"></i>切换
            </span>
          </navigator>
          <div class="community-avatar">
            <kyimage :src="user.avatar" type="avatar" />
            <span class="userinfo-name">{{user.displayname}}</span>
          </div>
        </div>
        <!-- banner end -->

        <div class="wx_group_count weui-flex">
          <div class="wx_attend_count weui-flex__item">
            <navigator url="/pages/myactivitys/myactivitys?type=1">
              <dl>
                <dt>
              {{item.activityCounts[0].counts}}
             </dt>
                <dd>
                  已报名
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

          <navigator url="/pages/realinfo/realinfo" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/real_name.png" style="width: 25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>实名认证</div>
            </div>
            <div class="weui-cell_comment">{{item.user.isReal!=true?"未认证":"已认证"}}</div>
          </navigator>

          <navigator url="/pages/mycommunity/mycommunity" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/personal_information.png" style="width: 25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>我的团体</div>
            </div>
            <div class="weui-cell__ft weui-cell__ft_in-access"></div>
          </navigator>

          <navigator url="/pages/myactivitys/myactivitys?type=5" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/integral_image.png" style="width:25px;height: 25px;margin-right: 5px" />
                </div>
                  <div class="weui-cell__bd weui-cell_primary">
                    <div>打卡活动</div>
                  </div>
            <div class="weui-cell_integral">{{item.activityCounts[4].counts}}</div>
          </navigator>
          
          <div class="line"></div>

          <navigator url="/pages/integrals/integrals" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
            <div class="weui-cell__hd">
              <image src="/static/images/integral_image.png" style="width:25px;height: 25px;margin-right: 5px" />
            </div>
            <div class="weui-cell__bd weui-cell_primary">
              <div>积分</div>
            </div>
            <div class="weui-cell_integral">{{item.score}}</div>
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

          <div class="line"></div>
          
        </div>

        <!--Footer Begin-->
        <div class="weui-footer">
          <div class="weui-footer__links">
            <div class="weui-footer__link" @click="logout">退出当前账号</div>
          </div>
          <!-- <div class="weui-footer__text">Copyright © 2017-2018</div> -->
        </div>
        <!--Footer End-->

    </div>
  </div>
</template>

<script>
  import kyimage from '@/components/kyimage.vue'
  import store from '../../store'

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
          userId: that.userId
        };
        this.$kyutil.get("/pub/wx/profile/info", param).then(res => this.item = res)
      },
      qh(cs) {
        this.xs = cs;
      },
      logout() {
        //退出当前账号
        console.log("logout");
        var that = this;
        var param = {
          userId: that.userId
        }
        this.$kyutil.post("/pub/wx/auth/logout", param).then(res => {
          if (res == true) {
            //清除本地缓存
            wx.clearStorageSync();
            wx.showToast({
              title: '退出当前账号',
              icon: 'success',
              duration: 3000,
              success: function (e) {
                wx.redirectTo({
                    url: "/pages/login/login"
                });
              }
            });
          }
          else {
            wx.showToast({
              title: '退出当前账户异常！',
              icon: 'none',
              duration: 3000,
            });
          }
        })
      },
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
    },
    onShow() {
      var that = this;

      this.$kyutil.CheckUserValidation().then(function(res) {
          var user = that.$kyutil.GetUser();
          that.user = user;
          that.userId = user.id;
          that.community = that.$store.state.community;
          wx.setNavigationBarTitle({
            title: that.community.name
          });
          that.getData();
      });
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
  .community-avatar {
    padding-top: 5px;
    padding-bottom: 5px;
    background-color:#1E364E;
  }
  .userinfo-avatar {
    margin: 0 auto;
    margin-top: 10px;
    margin-bottom: 10px;
    display: flex;
    justify-content: center;
    overflow: hidden;
    width: 60px;
    height: 60px;
    border-radius: 50%;
    border: 2px solid #aaa;
  }
  .userinfo-name {
    color: #fff;
    margin: 0 auto;
    margin-top: 20rpx;
    display: flex;
    justify-content: center;
    overflow: hidden;
  }
  .community-select {
    padding: 0 0;
    margin-top: 5px;
    margin-right: 5px;
    background-color: rgba(0, 0, 0, 0);
  }
</style>
