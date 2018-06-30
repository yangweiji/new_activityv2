<template>
  <div class="page">
    <div v-if="item" class="page__bd page__bd_spacing">
      <div class="weui-cells__title" v-if="!item.community.isVip">
        <h1 class="c-note-text">
          本团体暂未开通会员功能
        </h1>
      </div>
      <div v-if="item.community.isVip" class="kind-list">
        <div>
          <div v-if="!canBuyVip" class="kind-list__item c-text-center">
            您已成为{{item.vipYear}}年度会员，享有此年度的会员权益
          </div>
          <div v-if="canBuyVip" class="kind-list__item">
            <div class="weui-flex,kind-list__item-hd kind-list__item-hd_show">
              <div class="weui-flex__item">{{item.community.name}}</div>
            </div>
            <div class="kind-list__item-bd kind-list__item-bd_show c-text-center">
              <div class="weui-cells weui-cells_show">
                <div class="c-text-primary">{{vipPriceText}}</div>
                <div>您现在可以购买{{year}}年度会员</div>
              </div>
            </div>
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
  export default {
    data() {
      return {
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
        if (this.item && this.item.community) {
          this.$kyutil.filters.currency(this.item.community.vipAmount, "¥");
        }
      },
      canBuyVip() {
        if (this.item && this.item.community.isVip) {
          return this.item.vipYear < (new Date().getFullYear())
        }
        return false
      }
    },
    components: {},
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
          this.item = res
          that.processing = false
        })
      },
      payVip() {
        var that = this
        var param = {}
        wx.showModal({
          title: '会员提示',
          content: '申请成为会员前，请先阅读《会员说明》，明了会员的权利和义务，并同意遵守团体的相关规定。',
          confirmText: "确定",
          cancelText: "取消",
          success: function(res) {
            if (res.confirm) {
              that.processing = true;
              if (that.item.community.vipAmount > 0) {
                var order = {
                  communityId: that.item.community,
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
            }
          }
        });
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
        that.$kyutil.get("/pub/wx/pay/check", {id}).then(orderStatus => {
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
      if (this.$store.state.community) {
        var community = this.$store.state.community;
        this.communityId = community.id
        this.$kyutil.CheckUserValidation();
        var user = this.$kyutil.GetUser();
        if (user) {
          this.userId = user.id;
          this.getData();
        }
        wx.setNavigationBarTitle({
          title: community.name
        })
      }
    }
  };
</script>

<style scoped>
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
</style>
