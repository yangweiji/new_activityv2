<template>
  <div class="page">
    <div v-if="item" class="page__bd page__bd_spacing">
      <div class="weui-cells__title" v-if="!item.community.isVip">
        <h1 class="c-note-text">
          本团体暂未开通会员功能
        </h1>
      </div>
      <div v-if="item.community.isVip" class="c-kind kind-list">
        <div>
          
          <div v-if="!canBuyVip" class="kind-list__item">
            <div class="weui-flex,kind-list__item-hd kind-list__item-hd_show c-text-center">
              <div class="weui-flex__item">您已成为{{item.vipYear}}年度会员</div>
            </div>
            <div class="kind-list__item-bd kind-list__item-bd_show c-text-center">
              <div class="weui-cells weui-cells_show">
                <div class="c-text-primary c-price-text">{{item.vipYear}}</div>
                <div>
                  点击了解
                <div @click.stop="gotoAgreement()" class="weui-agree__link">《会员的权益和义务》</div>
                </div>
              </div>
            </div>
          </div>
          <div v-if="canBuyVip" class="kind-list__item">
            <div class="weui-flex,kind-list__item-hd kind-list__item-hd_show c-text-center">
              <div class="weui-flex__item">年费标准</div>
            </div>
            <div class="kind-list__item-bd kind-list__item-bd_show c-text-center">
              <div class="weui-cells weui-cells_show">
                <div class="c-text-primary c-price-text">{{vipPriceText}}</div>
                <div>您现在可以购买{{year}}年度会员</div>
              </div>
            </div>
          </div>
          <div v-if="canBuyVip" class="kind-list__item">
            <div class="weui-flex,kind-list__item-hd" :class="{'kind-list__item-hd_show':isAgree}">
              <div @click="bindAgreeChange()" class="weui-flex__item">
                <icon class="kind-list__img weui-icon-checkbox_circle" type="circle" size="23" v-if="!isAgree"></icon>
                <icon class="kind-list__img weui-icon-checkbox_success" type="success" size="23" v-if="isAgree"></icon>
                阅读并同意
                <div @click.stop="gotoAgreement()" class="weui-agree__link">《相关条款》</div>
              </div>
            </div>
            <!-- <div class="kind-list__item-bd" :class="{'kind-list__item-bd_show':isAgree}">
              <div class="weui-cells weui-cells_show">
                <div class="c-agree-text">
                  <wxParse :content="item.community.vipAgreement" />
                </div>
              </div>
            </div> -->
          </div>
        </div>
      </div>
    </div>
    <div v-if="canBuyVip" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="payVip()" class="weui-flex__item c-bg-primary">
        成为会员
      </div>
    </div>
  </div>
</template>

<script>
  import base64 from "../../../static/images/base64";
  import wxParse from "mpvue-wxparse";
  export default {
    data() {
      return {
        start:0,
        isAgree: false,
        isIpx: false,
        userId: null,
        communityId: null,
        processing: false,
        item: null,
        year: null,
        vipPrice: 0
      };
    },
    computed: {
      vipPriceText() {
        var amount = 0
        if (this.item && this.item.community) {
          amount = this.item.community.vipAmount
        }
        return this.$kyutil.filters.currency(amount, "¥");
      },
      canBuyVip() {
        if (this.item && this.item.community.isVip) {
          return this.item.vipYear < (new Date().getFullYear())
        }
        return false
      }
    },
    components: {
      wxParse
    },
    methods: {
      getData() {
        var that = this;
        that.processing = true
        var now = new Date()
        if (now.getMonth() < 11) {
          this.year = now.getFullYear()
        } else {
          this.year = now.getFullYear() + 1
        }
        var param = {
          communityId: that.communityId,
          userId: this.userId
        };
        this.$kyutil.get("/pub/wx/profile/vip", param).then(res => {
          that.item = res
          wx.setNavigationBarTitle({
            title: that.item.community.name
          })
          that.processing = false

          if(that.start){ //从实名认证跳回，直接付款
            that.isAgree = true
            that.payVip()
          }
        })
      },
      bindAgreeChange(e) {
        this.isAgree = !this.isAgree;
      },
      gotoAgreement() {
      wx.navigateTo({
        url: "../../pages/agreement/agreement?communityId=" + this.communityId
      });
    },
      payVip() {
        var that = this
        var param = {
          communityId: that.item.community.id,
          userId: that.userId,
          year: that.year
        }
        if (!that.isAgree) {
          wx.showModal({
            title: '会员提示',
            content: '申请成为会员前，请先阅读《相关条款》，明了会员的权利和义务，并同意遵守团体的相关规定。',
            confirmText: "确定",
            showCancel: false,
            success: function(res) {}
          })
          return
        }

        if(!that.item.user.isReal){
          wx.showModal({
            title: '会员提示',
            content: '申请成为会员前，请先完成实名认证',
            confirmText: "确定",
            showCancel: false,
            success: function(res) {
              wx.navigateTo({
                url: "../../pages/realinfo/realinfo?fromVip=" + that.communityId
              });
            }
          })
          return
        }


        that.processing = true;
        if (that.item.community.vipAmount > 0) {
          var order = {
            communityId: that.item.community.id,
            price: that.item.community.vipAmount,
            body: "升级会员",
            userId: that.userId,
            otherInfo: that.year
          };
          that.payOrder(order);
        } else {
          that.$kyutil.get("/pub/wx/profile/updatevip", param).then(res => {
            that.getData();
          })
        }
      },
      payOrder(order) {
        var that = this;
        that.$kyutil.post(
          "/pub/wx/pay/create",
          order,
        ).then(res => {
          var payOpt = {
            timeStamp: res[0].timeStamp,
            nonceStr: res[0].nonceStr,
            package: res[0].packageValue,
            signType: res[0].signType,
            paySign: res[0].paySign
          };
          payOpt.success = () => {
            that.checkOrder(res[1]);
          };
          payOpt.fail = () => {
            that.processing = false;
          };
          wx.requestPayment(payOpt);
        }).catch(() => {
          that.processing = false
        })
      },
      checkOrder(id) {
        var that = this;
        that.$kyutil.get("/pub/wx/pay/check", {
          id
        }).then(orderStatus => {
          //支付成功后，检查订单也成功，报名最终成功
          if (orderStatus == 2) {
            that.getData(); // 支付成功
          } else {
            alert("支付出现问题，请联系相关人员处理");
          }
        }).catch(() => {
          that.processing = false
        })
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onLoad() {
      this.$kyutil.CheckUserValidation();
    },
    onShow() {
      //接受参数
      this.$kyutil.CheckUserValidation();
      this.communityId = this.$root.$mp.query.communityId || this.$root.$mp.query.scene
      this.start = this.$root.$mp.query.start
      if (!this.communityId) {
        var community = this.$store.state.community;
        this.communityId = community.id
      }
      var user = this.$kyutil.GetUser();
      if (user) {
        this.userId = user.id;
        this.getData();
      }
    },
    onShareAppMessage(res) {
      return {
        title: this.item.activity.title,
        path: '/page/myvip/myvip?communityId=' + this.communityId
      }
    }
  };
</script>

<style scoped>
  @import url("~mpvue-wxparse/src/wxParse.css");
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
    opacity: 0.4;
    padding: 20px;
    -webkit-transition: opacity 0.3s;
    transition: opacity 0.3s;
  }
  .kind-list__item-hd_show {
    opacity: 1;
  }
  .kind-list__item-bd {
    height: 0;
    overflow: hidden;
  }
  .kind-list__item-bd_show {
    height: auto;
  }
  .c-agree-text {
    padding: 0px 20px;
  }
  .c-kind {
    padding-top: 20px;
  }
  .c-kind .c-price-text {
    font-size: 60px;
    padding: 40px 0px 60px 0px;
  }
  .c-kind .kind-list__item-bd_show {
    padding-bottom: 20px;
  }
</style>
