<template>
  <div class="page">
    <div wx:if="loaded" class="page__bd">
      <div>
        <div class="weui-cells__title" wx:if="!item && !item.attendUser && item.is_over_due">
          <h1 class="c-title-text">
            报名截止时间已过
          </h1>
        </div>
        <!--已经报名-->
        <!-- <div wx:if="item && item.attendUser">
          <div class="weui-cells__title">
            <h1 class="am-article-title">
              您已报名
              <span style="color:#F37B1D;font-size:25px;font-weight: bold;">
                            【<span style="color:#F37B1D;font-size:25px;font-weight: bold;" >{{activityStatusText()}}</span>】
              </span>
            </h1>
          </div>
          <div class="weui-form-preview">
            <div class="weui-form-preview__hd">
              <div class="weui-form-preview__item">
                <div class="weui-form-preview__label">活动票：</div>
                <div class="weui-form-preview__value_in-hd">{{item.ticket_title}}</div>
              </div>
            </div>
            <div class="weui-form-preview__bd">
              <div class="weui-form-preview__item" v-for="attendInfoItem in otherInfo" :key="attendInfoItem.key">
                <div class="weui-form-preview__label">{{attendInfoItem.key}}</div>
                <div class="weui-form-preview__value">{{attendInfoItem.value}}</div>
              </div>
            </div>
            <div class="weui-form-preview__ft">
              <navigator class="weui-form-preview__btn weui-form-preview__btn_default" hover-class="weui-form-preview__btn_active" @click="changeAttendInfo()">修改报名信息</navigator>
              <div class="weui-form-preview__btn weui-form-preview__btn_danger" hover-class="weui-form-preview__btn_active" @click="cancelAttend()">取消报名</div>
            </div>
          </div>
        </div> -->
        <!--未报名-->
        <div wx:if="!isAttend && !item.is_over_due">
          <div class="weui-cells__title">选择活动票</div>
          <div class="weui-cells weui-cells_after-title">
            <radio-group @change="radioChange">
              <label class="weui-cell weui-check__label" v-for="opt in ticketInfos" :key="opt.id">
                    <radio :disabled="opt.disabled" class="weui-check" :value="opt.id" :checked="opt.checked" />
                    <div class="weui-cell__bd">{{opt.title}}</div>
                    <div class="weui-cell__ft weui-cell__ft_in-radio" wx:if="opt.checked">
                      <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
                    </div>
                  </label>
            </radio-group>
          </div>
          <div class="weui-cells__title" wx:if="!item.hasTickets">活动票已售完</div>
          <div wx:if="item.hasTickets" v-for="attItem in item.attendInfos" :key="attItem.title">
            <div class="weui-cells__title">{{attItem.title}}</div>
            <div class="weui-cells weui-cells_after-title">
              <div wx:if="attItem.type == 'text'" class="weui-cell weui-cell_input">
                <div class="weui-cell__bd">
                  <input class="weui-input" v-model="attItem.value" placeholder="请输入..." />
                </div>
              </div>
              <div wx:if="attItem.type == 'textarea'" class="weui-cell">
                <div class="weui-cell__bd">
                  <textarea class="" placeholder="请输入文本" v-model="attItem.value" style="height: 3.3em" />
                  <div class="weui-textarea-counter">0/200</div>
                </div>
              </div>
            </div>
            <radio-group wx:if="attItem.type == 'select' && !attItem.multiple" @change="radioChange">
              <label class="weui-cell weui-check__label" v-for="(singleIndex, singleOpt) in attItem.options" :key="singleOpt.title">
                      <radio class="weui-check" :value="singleOpt.value" :checked="singleOpt.checked" />
                      <div class="weui-cell__bd">{{singleOpt.title}}</div>
                      <div class="weui-cell__ft weui-cell__ft_in-radio" wx:if="singleOpt.checked">
                        <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
                      </div>
                    </label>
            </radio-group>
            <checkbox-group wx:if="attItem.type == 'select' && attItem.multiple" @change="checkboxChange">
              <label class="weui-cell weui-check__label" v-for="(multipleINdex, multipleOpt) in attItem.options" :key="multipleOpt.title">
                      <checkbox class="weui-check" :value="multipleOpt.value" :checked="multipleOpt.checked" />
                      <div class="weui-cell__hd weui-check__hd_in-checkbox">
                        <icon class="weui-icon-checkbox_circle" type="circle" size="23" wx:if="!multipleOpt.checked"></icon>
                        <icon class="weui-icon-checkbox_success" type="success" size="23" wx:if="multipleOpt.checked"></icon>
                      </div>
                      <div class="weui-cell__bd">{{multipleOpt.title}}</div>
                    </label>
            </checkbox-group>
          </div>
          <div wx:if="item.checkInScore > 0" lass="weui-cells__title">
            <label>活动签到后可得积分：<span class="c-money">{{item.checkInScore}}</span></label>
          </div>
          <div wx:if="price > 0 && score > 0" class="c-form-group">
            <label class="am-checkbox-inline c-score-checkbox">
                      <input type="checkbox" v-model="isUsingScore">
                      本次可用积分：{{maxScore != null && score > maxScore ? maxScore : score}}<span wx:if="isUsingScore">, 抵扣积分：<span class="c-money">{{realScore()}}</span></span>
                  </label>
            <br/>
            <label>应付金额：<span class="c-money">¥ {{realPrice()}}</span></label>
          </div>

        </div>
      </div>
      <div class="page__bd_spacing">
        <button :disabled="processing" wx:if="item && !item.attendUser && !item.is_over_due && item.hasTickets" @click="submitAttend()" class="weui-btn" type="primary">提交报名信息</button>
      </div>
    </div>
  </div>
</template>

<script>
  import global from "../../global/index";
  export default {
    data() {
      return {
        activityId: 0,
        userId: 2128,
        loaded:false, //是否加载完成
        isAttend:false, //是否已经报名
        ticketInfos:[],
        item: {
          ticketInfos:[]
        }
      };
    },
    computed: {},
    methods: {
      getData() {
        var that = this;
        var param = {
          activityId: that.activityId,
          userId: that.userId
        };
        global.HttpRequest(
          true,
          "/pub/wx/activity/attend",
          false,
          "",
          param,
          "GET",
          false,
          function(res) {
            that.ticketInfos = res.ticketInfos
            that.isAttend = !!res.attendUser
            res.ticketInfos = null
            that.item = res
            that.loaded = true;
          }
        );
      },
      changeAttendInfo() {
        wx.navigateTo({
          url: "../../pages/attendupdate/attendupdate?activityId=" + this.activityId
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
          var param = {
            activityId: that.activityId,
            userId: that.userId
          };
          wx.showModal({
            title: "操作提示",
            content: "您正在进行取消报名操作，请确定是否继续？",
            confirmText: "确定",
            cancelText: "取消",
            success: function(res) {
              if (res.confirm) {
                global.HttpRequest(
                  true,
                  "/pub/wx/activity/cancelattend",
                  false,
                  "",
                  param,
                  "POST",
                  false,
                  function(res) {
                    that.item = res;
                  }
                );
              }
            }
          });
        }
      },
      activityStatusText() {
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
      },
      checkboxChange(e) {
        console.log('checkbox发生change事件，携带value值为：' + e.mp.detail.value);
        var checkboxItems = this.checkboxItems,
          values = e.mp.detail.value;
        for (var i = 0, lenI = checkboxItems.length; i < lenI; ++i) {
          checkboxItems[i].checked = false;
          for (var j = 0, lenJ = values.length; j < lenJ; ++j) {
            if (checkboxItems[i].value == values[j]) {
              checkboxItems[i].checked = true;
              break;
            }
          }
        }
        this.checkboxItems = checkboxItems;
      },
      radioChange(e) {
        console.log('radio发生change事件，携带value值为：' + e.mp.detail.value);
        let radioItems = this.radioItems;
        for (let i = 0; i < radioItems.length; ++i) {
          radioItems[i].checked = radioItems[i].value === e.mp.detail.value;
        }
        this.radioItems = radioItems;
      },
      realScore(){},
      realPrice(){},
      submitAttend() {
      }
    },
    created() {
      console.log("attend created");
    },
    onShow() {
      console.log("小程序触发的 onshow, 获取参数: " + this.$root.$mp.query);
      var that = this;
      that.loaded = false;
      that.activityId = this.$root.$mp.query.activityId;
      global.CheckUserValidation();
      this.getData();
    }
  };
</script>

<style scoped>
  /*!
     * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
     * Copyright 2017 Tencent, Inc.
     * Licensed under the MIT license
     */
     .c-title-text{
       text-align: center;
       font-size: 18px;
     }
</style>
