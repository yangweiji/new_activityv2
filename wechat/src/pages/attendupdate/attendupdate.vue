<template>
  <div class="page">
    <div v-if="loaded" class="page__bd">
      <div class="weui-toptips weui-toptips_warn" v-if="errorMessage">{{errorMessage}}</div>
      <activity :item="item.activity"></activity>
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
                              <icon class="weui-icon-radio" type="success_no_circle" color="#F37B1D"  size="16"></icon>
                            </div>
                          </label>
            </radio-group>
          </div>
          <div class="weui-cells__title" v-if="!item.hasTickets">活动票已售完</div>
          <field :config="attFields[index]" v-model="attItem.value" v-for="(attItem, index) in item.attendInfos" :key="attItem.title" />
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
  import {
    Decimal
  } from "decimal.js";
  import activity from '@/components/activity.vue'
  import field from '@/components/field.vue'
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
        attFields: null
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
          if (this.ticket.score != null && this.ticket.score != undefined && this.ticket.score !== "" && this.ticket.score < result) {
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
        this.$kyutil.get("/pub/wx/activity/attendupdate", param).then(res => {
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
          var attFields = []
          if (res.attendInfos) {
            for (var i = 0; i < res.attendInfos.length; i++) {
              var attendInfo = res.attendInfos[i];
              attendInfo.value = res.otherInfo[attendInfo.title]
              var field = {
                type: attendInfo.type,
                title: attendInfo.title,
                required: attendInfo.required
              }
              if (attendInfo.type == "select") {
                field.options = []
                for (var j = 0; j < attendInfo.options.length; j++) {
                  var optTitle = attendInfo.options[j].title
                  field.options.push({
                    title: optTitle,
                    value: optTitle
                  })
                }
                if (attendInfo.multiple) {
                  field.type = "check"
                } else {
                  field.type = "radio"
                }
              }
              attFields.push(field)
            }
          }
          that.attFields = attFields
          that.item = res;
          that.loaded = true;
        })
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
          } else if (i == 1) {
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
        this.$kyutil.post("/pub/wx/activity/attendupdate", activityUser).then(res => {
          wx.redirectTo({
            url: "../../pages/attend/attend?activityId=" + that.activityId
          });
        })
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
      
      // this.$kyutil.CheckUserValidation();
      // var user = this.$kyutil.GetUser();
      // if (user) {
      //   this.userId = user.id;
      //   this.getData();
      // }
      
      this.$kyutil.CheckUserValidation().then(function(res) {
          var user = that.$kyutil.GetUser();
          that.userId = user.id;
          that.getData();
      });
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
