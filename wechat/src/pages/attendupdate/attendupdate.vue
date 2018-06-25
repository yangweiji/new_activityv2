<template>
  <div class="page">
    <div v-if="loaded" class="page__bd">
      <div class="weui-toptips weui-toptips_warn" v-if="errorMessage">{{errorMessage}}</div>
      <div @click="checkdetails(item.activity.id)" class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" >
                    <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
                      <image class="weui-media-box__thumb" :src="item.activity.avatar" />
                    </div>
                    <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
                        <div class="weui-media-box__title">{{item.activity.title}}</div>
                        <div class="weui-media-box__desc" style="float:left">{{item.activity.start_time}}</div>
                        <div class="weui-media-box__desc" style="float:right">喜欢：{{item.activity.favorite_count}} 报名：{{item.activity.attend_count}}</div>
                    </div>
                </div>
      <div>
        <div class="weui-cells__title" v-if="overDue">
          <h1 class="c-title-text">
            报名截止时间已过
          </h1>
        </div>
        <!--修改报名信息-->
        <div v-if="!overDue">
          <div class="weui-cells__title">选择活动票</div>
          <div class="weui-cells weui-cells_after-title">
            <radio-group class="c-check-group" id="ticketInfos" @change="ticketRadioChange">
              <label class="weui-cell weui-check__label" :class="{'c-disabled' : opt.disabled, 'c-checked':opt.checked}" v-for="opt in item.ticketInfos" :key="opt.id">
                          <radio :disabled="opt.disabled" class="weui-check" :value="opt.id" :checked="opt.checked" />
                          <div class="weui-cell__bd">{{opt.title}}</div> 
                          <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="opt.checked">
                            <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
                          </div>
                        </label>
            </radio-group>
          </div>
          <div class="weui-cells__title" v-if="!item.hasTickets">活动票已售完</div>
          <div v-for="(attItem, index) in item.attendInfos" :key="attItem.title">
            <div class="weui-cells__title">
              {{attItem.title}}
              <span v-if="attItem.required" style="color:red;font-size:10px;">(必填)</span>
            </div>
            <div class="weui-cells weui-cells_after-title">
              <div v-if="attItem.type == 'text'" class="weui-cell weui-cell_input">
                <div class="weui-cell__bd">
                  <input class="weui-input" v-model="attItem.value" placeholder="请输入..." />
                </div>
              </div>
              <div v-if="attItem.type == 'textarea'" class="weui-cell">
                <div class="weui-cell__bd">
                  <textarea class="" placeholder="请输入..." v-model="attItem.value" style="height: 3.3em" />
                  <div class="weui-textarea-counter">0/200</div>
                </div>
              </div>
              <radio-group :id="index" v-if="attItem.type == 'select' && !attItem.multiple" @change="attendInfoRadioChange">
                <label class="weui-cell weui-check__label" v-for="(singleOpt, singleIndex) in attItem.options" :key="singleOpt.title">
                              <radio class="weui-check" :value="singleOpt.title" :checked="singleOpt.checked" />
                              <div :name="singleIndex" class="weui-cell__bd">{{singleOpt.title}}</div>
                              <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="singleOpt.checked">
                                <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
                              </div>
                            </label>
              </radio-group>
              <checkbox-group :id="index" v-if="attItem.type == 'select' && attItem.multiple" @change="attendInfoCheckboxChange">
                <label class="weui-cell weui-check__label" v-for="(multipleOpt, multipleIndex) in attItem.options" :key="multipleOpt.title">
                              <checkbox  class="weui-check" :value="multipleOpt.title" :checked="multipleOpt.checked" />
                              <div :name="multipleIndex" class="weui-cell__hd weui-check__hd_in-checkbox">
                                <icon class="weui-icon-checkbox_circle" type="circle" size="23" v-if="!multipleOpt.checked"></icon>
                                <icon class="weui-icon-checkbox_success" type="success" size="23" v-if="multipleOpt.checked"></icon>
                              </div>
                              <div class="weui-cell__bd">{{multipleOpt.title}}</div>
                            </label>
              </checkbox-group>
            </div>
          </div>
          <div v-if="item.checkInScore > 0" lass="weui-cells__title">
            <label class="c-block-text">活动签到后可得积分：<span class="c-money">{{item.checkInScore}}</span></label>
          </div>

        </div>
      </div>
    </div>
    <div v-if="item" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="submitAttend()" class="weui-flex__item c-bg-primary">
        提交修改信息
      </div>
    </div>
  </div>
</template>

<script>
import { Decimal } from "decimal.js";
export default {
  data() {
    return {
      isIpx:false,
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
      processing: false
    };
  },
  computed: {
    activityStatusText() {
      if (this.item && this.item.attendUser) {
        var item = this.item;
        if (item.attendUser.status == 2) {
          return "中签";
        } else if (item.attendUser.status == 1) {
          return "未中签";
        } else if (item.attendUser.status == 3) {
          return "未中签，退款中";
        } else if (item.attendUser.status == 4) {
          return "未中签，已完成退款";
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
    realScore() {
      if (this.ticket) {
        var result = new Decimal(this.ticket.price)
          .mul(this.scoreRate)
          .ceil()
          .toNumber();
        if (result > this.score) {
          result = this.score;
        }
        if (this.ticket.score != null && this.ticket.score < result) {
          result = this.ticket.score;
        }
        return result;
      }
      return 0;
    }
  },
  methods: {
    getData() {
      var that = this;
      that.processing = false;
      var param = {
        activityId: that.activityId,
        userId: that.userId
      };
      this.$kyutil.HttpRequest(
        true,
        "/pub/wx/activity/attendupdate",
        false,
        "",
        param,
        "GET",
        false,
        function(res) {
          that.isAttend = !!res.attendUser;
          that.overDue = !res.attendUser && res.is_over_due;
          that.score = res.userScore;
          that.scoreRate = res.scoreRate;
          if (res.ticketInfos) {
            for (var i = 0; i < res.ticketInfos.length; i++) {
              var ticket = res.ticketInfos[i];
              if (ticket.checked) {
                that.ticket = ticket;
              }
            }
          }
          if (res.attendInfos) {
            for (var i = 0; i < res.attendInfos.length; i++) {
              var attendInfo = res.attendInfos[i];
              attendInfo.value = res.otherInfo[attendInfo.title];
              if (attendInfo.type == "select") {
                var selectValues = [];
                if (attendInfo.value) {
                  selectValues = attendInfo.value.split(",");
                }
                for (var j = 0; j < attendInfo.options.length; j++) {
                  var opt = attendInfo.options[j];
                  opt.checked = selectValues.indexOf(opt.title) >= 0;
                }
              }
            }
          }
          that.item = res;
          that.loaded = true;
          console.log("attend get data:", res);
        }
      );
    },

    ticketRadioChange(e) {
      let ticketInfos = this.item.ticketInfos;
      for (let i = 0; i < ticketInfos.length; ++i) {
        var ticket = ticketInfos[i];
        ticket.checked = ticket.id == e.mp.detail.value;
        if (ticket.checked) {
          this.ticket = ticket;
        }
      }
      this.item.ticketInfos = ticketInfos;
    },
    attendInfoRadioChange(e) {
      let attendInfo = this.item.attendInfos[e.mp.target.id];
      attendInfo.value = e.mp.detail.value;
      for (let i = 0; i < attendInfo.options.length; ++i) {
        var opt = attendInfo.options[i];
        opt.checked = opt.title == e.mp.detail.value;
      }
    },
    attendInfoCheckboxChange(e) {
      let attendInfo = this.item.attendInfos[e.mp.target.id];
      let values = e.mp.detail.value;
      attendInfo.value = e.mp.detail.value.join();
      for (var i = 0, lenI = attendInfo.options.length; i < lenI; ++i) {
        let opt = attendInfo.options[i];
        opt.checked = false;
        for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
          if (opt.title == values[j]) {
            opt.checked = true;
            break;
          }
        }
      }
    },

    resetError() {
      var that = this;
      setTimeout(() => {
        that.errorMessage = null;
      }, 2000);
    },
    submitAttend() {
      var that = this;
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
        activityTicketId: that.ticket.id,
        id: that.item.attendUser.id
      };
      for (var i = 0; i < that.item.attendInfos.length; i++) {
        var attendInfo = that.item.attendInfos[i];
        if (attendInfo.required && !attendInfo.value) {
          that.errorMessage = "请选择填写" + attendInfo.title;
          that.resetError();
          return;
        }
        if (i == 0) {
          activityUser.realName = attendInfo.value;
        } else if (i == 2) {
          activityUser.mobile = attendInfo.value;
        }
        activityUser.otherInfo[attendInfo.title] = attendInfo.value;
      }
      activityUser.otherInfo = JSON.stringify(activityUser.otherInfo);
      activityUser.price = that.realPrice; // 抵扣后金额
      if (that.isUsingScore) {
        activityUser.score = that.realScore;
      } else {
        activityUser.score = 0;
      }
      that.processing = true;
      this.$kyutil.HttpRequest(
        true,
        "/pub/wx/activity/attendupdate",
        false,
        "",
        activityUser,
        "POST",
        false,
        function(res) {
          wx.redirectTo({
            url: "../../pages/attend/attend?activityId=" + that.activityId
          });
        }
      );
    },
    checkdetails(activityId) {
      var that = this;
      that.activityId = activityId;
      wx.navigateTo({
        url: "../../pages/details/details?activityId=" + that.activityId
      });
    }
  },
  created() {
    this.isIpx = this.$kyutil.data.isIpx
  },
  onShow() {
    console.log(this.$root.$mp.query);
    var that = this;
    that.loaded = false;
    that.activityId =
      this.$root.$mp.query.activityId || this.$root.$mp.query.scene;
    this.$kyutil.CheckUserValidation();
    var user = this.$kyutil.GetUser();
    if (user) {
      this.userId = user.id;
      this.getData();
    }
  },
  mounted() {}
};
</script>

<style scoped>
/*!
           * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
           * Copyright 2017 Tencent, Inc.
           * Licensed under the MIT license
           */
.c-title-text {
  text-align: center;
  font-size: 18px;
}
</style>
