<template>
  <div class="page">
    <div v-if="loaded" class="page__bd">
      <div class="weui-toptips weui-toptips_warn" v-if="errorMessage">{{errorMessage}}</div>
      <activity :item="item.activity"></activity>
      <div>
        <div class="weui-cells__title" v-if="overDue">
          <h1 class="c-note-text">
            报名截止时间已过
          </h1>
        </div>
        <!--已经报名-->
        <div v-if="item && item.attendUser">
          <div class="weui-cells__title">
            <h1 class="am-article-title" v-if="isAttendNow">
              报名成功
            </h1>
            <h1 class="am-article-title" v-else>
              您已报名
              <!--抽签活动显示抽签状态-->
              <span v-if="item.activity.activity_type == 3" class="c-text-primary">
                【
                <span>{{activityStatusText}}</span>】
              </span>
            </h1>
          </div>
          <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
              <div class="weui-form-preview__item">
                <div class="weui-form-preview__label">活动票：</div>
                <div class="weui-form-preview__value_in-hd" style="font-size:19px;">{{item.ticket_title}}</div>
              </div>
            </div>
            <div class="weui-form-preview__bd">
              <div class="weui-form-preview__item" v-for="attendInfoItem in item.attendInfos" :key="attendInfoItem.key">
                <div class="weui-form-preview__label" v-if="attendInfoItem.type!='file'">{{attendInfoItem.title}}</div>
                <div class="weui-form-preview__value" v-if="attendInfoItem.type!='file'">{{item.otherInfo[attendInfoItem.title] || ''}}</div>
              </div>
              <!-- 图像上传字段靠最后 begin -->
              <div class="weui-form-preview__item" v-for="attendInfoItem in item.attendInfos" :key="attendInfoItem.key">
                <div class="weui-form-preview__label" v-if="attendInfoItem.type=='file'">{{attendInfoItem.title}}</div>
                <div class="weui-form-preview__value" v-if="attendInfoItem.type=='file'" style="float:left;width:100%;">
                  <field :config="{type: 'file'}" :disabled="true" v-model="item.otherInfo[attendInfoItem.title]" />
                </div>
              </div>
              <!-- 图像上传字段靠最后 end -->
            </div>

            <div class="line"></div>

            <div class="weui-form-preview__ft">
              <div class="weui-form-preview__btn weui-form-preview__btn_default" hover-class="weui-form-preview__btn_active" @click="changeAttendInfo()">修改报名信息</div>
              <div class="weui-form-preview__btn weui-form-preview__btn_danger" hover-class="weui-form-preview__btn_active" @click="cancelAttend()">取消报名</div>
              <div class="weui-form-preview__btn weui-form-preview__btn_default" hover-class="weui-form-preview__btn_active" @click="afterUpload()" v-if="overEnd">活动图片</div>
            </div>

          </div>

          <div class="weui-btn-area">
            <navigator url="../../pages/index/index" open-type="switchTab" hover-class="navigator-hover">
              <button class="weui-btn" type="default">返回首页</button>
            </navigator>
          </div>
        </div>

        <!--未报名-->
        <div v-if="!isAttend && !overDue">
          <div class="weui-cells__title">选择活动票</div>
          <div class="weui-cells weui-cells_after-title">
            <radio-group class="c-check-group" id="ticketInfos" @change="ticketRadioChange">
              <label class="weui-cell weui-check__label" :class="{'c-disabled' : opt.disabled, 'c-checked':opt.checked}" v-for="opt in item.ticketInfos" :key="opt.id">
                <radio :disabled="opt.disabled" class="weui-check" :value="opt.id" :checked="opt.checked" />
                <div class="weui-cell__bd">{{opt.title}}</div>
                <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="opt.checked">
                  <icon class="weui-icon-radio" type="success_no_circle" color="#F37B1D" size="16"></icon>
                </div>
              </label>
            </radio-group>
          </div>
          <div class="weui-cells__title" v-if="!item.hasTickets">没有可购买的活动票</div>
          <field :config="attFields[index]" v-model="attItem.value" v-for="(attItem, index) in item.attendInfos" :key="attItem.title" />

          <div v-if="item.checkInScore > 0" lass="weui-cells__title">
            <label class="c-block-text">活动签到后可得积分：
              <span class="c-money">{{item.checkInScore}}</span>
            </label>
          </div>
          <div v-if="ticket && ticket.price > 0 && score > 0" class="c-form-group">
            <checkbox-group @change="usingScoreChange">
              <label class="weui-cell weui-check__label c-block-text">
                <checkbox class="weui-check" :value="isUsingScore" :checked="isUsingScore" />
                <div class="weui-cell__hd weui-check__hd_in-checkbox">
                  <icon class="weui-icon-checkbox_circle" type="circle" size="23" v-if="!isUsingScore"></icon>
                  <icon class="weui-icon-checkbox_success" color="#F37B1D" type="success" size="23" v-if="isUsingScore"></icon>
                </div>
                <div class="weui-cell__bd">
                  本次可用积分：{{usableScore}}
                  <span v-if="isUsingScore">, 抵扣积分：
                    <span class="c-money">{{realScore}}</span>
                  </span>
                </div>
              </label>
            </checkbox-group>
          </div>
        </div>
      </div>
    </div>
    <div v-if="item && !item.attendUser && !item.is_over_due && item.hasTickets" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div @click="gotoAttendUsers()" class="weui-flex__item c-default-btn">
        应付
        <span class="c-text-primary">{{realPriceText}}</span>
      </div>
      <div :disabled="processing" @click="submitAttend()" class="weui-flex__item c-bg-primary">
        提交报名信息
      </div>
    </div>
    <!-- <button @click="getoCheckIn">签到</button>-->
    <!-- 打卡活动， 显示打卡按钮 -->
    <div v-if="item && item.attendUser && item.activity.activity_type == 4" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="gotoActivityUserRecord()" class="weui-flex__item c-bg-primary">
        活动打卡
      </div>
    </div>
  </div>
</template>

<script>
import { Decimal } from "decimal.js";
import activity from "@/components/activity.vue";
import field from "@/components/field.vue";
import store from "../../store";
export default {
  components: {
    activity,
    field
  },
  data() {
    return {
      isIpx: false,
      activityId: 0,
      userId: 2128,
      loaded: false, //是否加载完成
      isAttend: false, //是否已经报名
      overDue: false,
      ticket: null,
      isUsingScore: false,
      score: 0,
      scoreRate: 0,
      item: {},
      errorMessage: null,
      processing: false,
      isAttendNow: false,
      attFields: null,
      //活动是否结束
      overEnd: false
    };
  },
  computed: {
    activityStatusText() {
      if (this.item && this.item.attendUser) {
        var item = this.item;
        if (item.attendUser.status == 2) {
          return "已中签";
        } else if (item.attendUser.status == 1) {
          return "待抽签";
        } else if (item.attendUser.status == 3) {
          return "已申请退费";
        } else if (item.attendUser.status == 4) {
          return "已完成退费";
        }
        return "抽签中";
      }
      return "";
    },
    realPrice() {
      if (this.isUsingScore && this.ticket) {
        var subPrice = new Decimal(this.realScore).div(this.scoreRate);
        if (subPrice.toNumber() < this.ticket.price) {
          return new Decimal(this.ticket.price).sub(subPrice).toNumber();
        }
        return 0;
      }
      if (this.ticket) {
        return this.ticket.price;
      }
      return 0;
    },
    realPriceText() {
      return this.$kyutil.filters.currency(this.realPrice, "¥");
    },
    realScore() {
      if (this.ticket) {
        var result = new Decimal(this.ticket.price)
          .mul(this.scoreRate)
          .ceil()
          .toNumber();
        if (result > this.score) {
          result = this.score;
        }
        if (
          this.ticket.score != null &&
          this.ticket.score != undefined &&
          this.ticket.score !== "" &&
          this.ticket.score < result
        ) {
          result = this.ticket.score;
        }
        return result;
      }
      return 0;
    },
    usableScore() {
      if (this.ticket) {
        return this.ticket.score != null && this.score > this.ticket.score
          ? this.ticket.score
          : this.score;
      }
      return 0;
    }
  },
  methods: {
    getData() {
      var that = this;
      that.processing = true;
      var param = {
        activityId: that.activityId,
        userId: that.userId
      };
      this.$kyutil.get("/pub/wx/activity/attend", param).then(res => {
        that.isAttend = !!res.attendUser;
        that.overDue = !res.attendUser && res.is_over_due;
        that.overEnd = res.is_over_end;
        that.score = res.userScore;
        that.scoreRate = res.scoreRate;
        if (res.ticketInfos) {
          for (var i = 0; i < res.ticketInfos.length; i++) {
            res.ticketInfos[i].checked = false;
          }
        }
        var attFields = [];
        if (res.attendInfos) {
          for (var i = 0; i < res.attendInfos.length; i++) {
            var attendInfo = res.attendInfos[i];
            var field = {
              type: attendInfo.type,
              title: attendInfo.title,
              required: attendInfo.required
            };
            if (attendInfo.type == "select") {
              field.options = [];
              for (var j = 0; j < attendInfo.options.length; j++) {
                var optTitle = attendInfo.options[j].title;
                field.options.push({
                  title: optTitle,
                  value: optTitle
                });
              }
              if (attendInfo.multiple) {
                field.type = "check";
              } else {
                field.type = "radio";
              }
            }
            attFields.push(field);
          }
        }
        that.attFields = attFields;
        that.item = res;
        //当只有一个可选票时， 自动选上
        if (
          res.ticketInfos &&
          res.ticketInfos.length == 1 &&
          !res.ticketInfos[0].disabled
        ) {
          that.selectTicket(res.ticketInfos[0].id);
        }
        that.loaded = true;
        that.processing = false;
      });
    },
    changeAttendInfo() {
      wx.navigateTo({
        url:
          "../../pages/attendupdate/attendupdate?activityId=" + this.activityId
      });
    },
    cancelAttend() {
      var that = this;
      var message = this.item.cancelMessage;
      if (message) {
        //有取消报名限制信息，不能取消报名
        wx.showModal({
          content: message,
          showCancel: false,
          success: function(res) {}
        });
      } else {
        var param = that.item.attendUser.id;
        wx.showModal({
          title: "操作提示",
          content: "您正在进行取消报名操作，请确定是否继续？",
          confirmText: "确定",
          cancelText: "取消",
          success: function(res) {
            if (res.confirm) {
              that.$kyutil
                .post("/pub/wx/activity/cancelattend", param)
                .then(res => {
                  wx.redirectTo({
                    url:
                      "../../pages/details/details?activityId=" +
                      that.activityId
                  });
                });
            }
          }
        });
      }
    },
    ticketRadioChange(e) {
      this.selectTicket(e.mp.detail.value);
    },
    selectTicket(v) {
      let ticketInfos = this.item.ticketInfos;
      for (let i = 0; i < ticketInfos.length; ++i) {
        var ticket = ticketInfos[i];
        ticket.checked = ticket.id == v;
        if (ticket.checked) {
          this.ticket = ticket;
        }
      }
      this.item.ticketInfos = ticketInfos;
    },
    usingScoreChange() {
      this.isUsingScore = !this.isUsingScore;
    },
    resetError() {
      var that = this;
      setTimeout(() => {
        that.errorMessage = null;
      }, 2000);
    },
    submitAttend() {
      var that = this;
      ///禁止重复提交
      if (that.processing) {
        return;
      }

      that.errorMessage = null;
      if (!this.ticket) {
        that.errorMessage = "请选择一张活动票";
        that.resetError();
        return;
      }
      var activityUser = {
        otherInfo: {},
        userId: that.userId,
        activityId: that.activityId,
        activityTicketId: that.ticket.id
      };
      for (var i = 0; i < that.item.attendInfos.length; i++) {
        var attendInfo = that.item.attendInfos[i];
        if (attendInfo.required && !attendInfo.value) {
          that.errorMessage = "请填写" + attendInfo.title;
          that.resetError();
          return;
        }
        if (i == 0) {
          activityUser.realName = attendInfo.value;
        } else if (i == 1) {
          activityUser.mobile = attendInfo.value;
        }
        activityUser.otherInfo[attendInfo.title] = attendInfo.value;

        //验证出生日期
        if (attendInfo.title.indexOf('出生日期') >= 0 && !that.$kyutil.birthdayValid(attendInfo.value)) {
          that.errorMessage = "您的出生日期不合法，正确格式:如1990-09-09，请重新输入";
          that.resetError();
          return;
        }
        //验证身份证号
        if (attendInfo.title.indexOf('身份证号') >= 0 && !that.$kyutil.idcardValid(attendInfo.value)) {
          that.errorMessage = "您的身份证号不合法，请重新输入";
          that.resetError();
          return;
        }
      }
      activityUser.otherInfo = JSON.stringify(activityUser.otherInfo);
      activityUser.price = that.realPrice; // 抵扣后金额
      if (that.isUsingScore) {
        activityUser.score = that.realScore;
      } else {
        activityUser.score = 0;
      }
      that.processing = true;
      if (activityUser.price > 0) {
        //收费活动，创建订单
        var order = {
          activityTicketId: activityUser.activityTicketId,
          communityId: that.item.activity.community_id,
          price: activityUser.price,
          body: "活动报名",
          userId: that.userId,
          otherInfo: JSON.stringify(activityUser)
        };
        that.payOrder(order);
      } else {
        //免费活动，直接报名
        that.$kyutil.post("/pub/wx/activity/attend", activityUser).then(() => {
          that.getData();
          that.isAttendNow = true;
        });
      }
    },
    payOrder(order) {
      var that = this;
      that.$kyutil
        .post("/pub/wx/pay/create", order)
        .then(res => {
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
          payOpt.fail = e => {
            that.$kyutil.alert("支付出现问题，请联系相关人员处理");
            console.error("支付出现错误[payOpt.fail]：", e);
            that.processing = false;
          };
          wx.requestPayment(payOpt);
        })
        .catch(e => {
          that.$kyutil.alert("支付出现问题，请联系相关人员处理");
          console.error("支付出现错误[payOpt:catch]:", e);
          that.processing = false;
        });
    },
    checkOrder(id) {
      var that = this;
      that.$kyutil
        .get("/pub/wx/pay/check", {
          id
        })
        .then(orderStatus => {
          //支付成功后，检查订单也成功，报名最终成功
          if (orderStatus == 2) {
            that.getData(); // 支付成功
            that.isAttendNow = true;
          } else {
            console.error("支付出现错误[orderStatus]:", e);
            that.$kyutil.alert(
              "支付出现问题，在确定是否支付成功前，请不要重复支付"
            );
          }
        })
        .catch(e => {
          that.$kyutil.alert("支付出现问题，请联系相关人员处理");
          console.error("支付出现错误[checkOrder:catch]：", e);
          that.processing = false;
        });
    },
    getoCheckIn() {
      wx.navigateTo({
        url: "../../pages/checkin/checkin?activityId=" + this.activityId
      });
    },
    gotoActivityUserRecord() {
      wx.navigateTo({
        url:
          "../../pages/activityrecord/activityrecord?activityId=" +
          this.activityId
      });
    },
    afterUpload() {
      wx.navigateTo({
        url:
          "../../pages/activityafter/activityafter?activityId=" +
          this.activityId
      });
    }
  },
  created() {
    this.isIpx = this.$kyutil.data.isIpx;
  },
  onShow() {
    console.log(this.$root.$mp.query);
    var that = this;
    //修改小程序 chooseImage 会触发页面重新 onShow 事件问题, 使用state全局存储变量， 不重新加载数据
    if (that.$store.state.isUpload) {
      that.$store.state.isUpload = !that.$store.state.isUpload;
      return;
    }

    that.activityId =
      this.$root.$mp.query.activityId || this.$root.$mp.query.scene;
    that.loaded = false;

    this.$kyutil.CheckUserValidation().then(function(res) {
      var user = that.$kyutil.GetUser();
      that.userId = user.id;
      that.isAttendNow = false;
      that.getData();
    });
  },
  mounted() {},
  onShareAppMessage(res) {
    return {
      title: "[报名]" + this.item.activity.title,
      path: "pages/attend/attend?activityId=" + this.activityId
    };
  }
};
</script>

<style scoped>
</style>
