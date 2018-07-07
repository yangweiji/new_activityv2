<template>
  <div class="page c-activity-record">
    <div v-if="item" class="page__bd">
      <activity :item="item.activity"></activity>
      <div class="weui-tab">
        <div class="weui-navbar" style="top:auto;">
          <block v-for="(tab,index) in tabs" :key="index">
            <div :id="index" :class="{'weui-bar__item_on' :activeIndex==tab.id}" class="weui-navbar__item" @click="tabClick(tab)">
              <div class="weui-navbar__title">{{tab.title}}</div>
            </div>
          </block>
          <div class="weui-navbar__slider" :class="activeTab.class"></div>
        </div>
        <div class="weui-tab__panel">
          <div class="weui-tab__content c-custom-calendar" :hidden="activeTab.id != 1">
            <Calendar :no-selected="true" :months="calendar.months" :begin="calendar.begin" :end="calendar.end" :value="calendar.value" @next="next" @prev="prev" :events="events" clean="true" @select="select" ref="calendar" @selectMonth="selectMonth" @selectYear="selectYear"
            />
          </div>
          <div class="weui-tab__content" :hidden="activeTab.id != 2">
            <div class="calendar-tools c-calendar-tools">
              <label class="calendar-prev" @click="bindDatePrev()">
                  <image class="_img" src="/copy-asset/components/calendar/arrow-left.png" />
                </label>
              <label class="calendar-next" @click="bindDateNext()">
                  <image class="_img" src="/copy-asset/components/calendar/arrow-right.png" />
                </label>
              <div class="calendar-info">
                <picker mode="date" :value="currentDate" :start="calendar.startTime" :end="calendar.endTime" @change="bindDateChange">
                  <div class="weui-input">{{currentDateText}}</div>
                </picker>
              </div>
            </div>
            <div class="weui-cells weui-cells_after-title">
              <div @click="gotoViewRecord(record)" v-for="record in dayRecords" :key="record.user_id" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
                <div class="weui-cell__hd">
                  <kyimage :src="record.avatar" size="small" />
                </div>
                <div class="weui-cell__bd">{{record.displayname}}</div>
                <div class="weui-cell__ft" :class="{'weui-cell__ft_in-access': record.record_time}">
                  <span v-if="record.record_time_text">{{record.record_time_text}}</span>
                  <span v-if="!record.record_time_text">未打卡</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="weui-cells__title" v-if="isEnd">
          <h1 class="c-note-text">
            活动已经结束
          </h1>
        </div>
      </div>
      <div v-if="item && !isEnd" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
        <div v-if="canAddRecord" :disabled="processing" @click="addRecord()" class="weui-flex__item c-bg-primary">
          今日打卡
        </div>
        <div v-if="!hasAttendActivity" :disabled="processing" @click="gotoAttend()" class="weui-flex__item c-bg-primary">
          立即报名
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Vue from 'vue'
  import Calendar from '@/components/calendar/mpvue-calendar.vue'
  import kyimage from '@/components/kyimage.vue'
  import activity from '@/components/activity.vue'
  export default {
    components: {
      Calendar,
      kyimage,
      activity
    },
    data() {
      return {
        isIpx: false,
        isBackPage: false, //是否返回页面，此时部分信息不变
        isEnd: false,
        loaded: false,
        item: null,
        activityId: null,
        userId: null,
        canAddRecord: false,
        hasAttendActivity: false,
        tabs: [{
          id: 1,
          title: '我的打卡',
          class: 'weui-navbar__slider_0'
        }, {
          id: 2,
          title: '打卡记录',
          class: 'weui-navbar__slider_1'
        }],
        activeTab: null,
        calendar: {
          value: [],
          begin: null,
          end: null,
          lunar: false,
          clean: true
        },
        currentDate: null
      };
    },
    computed: {
      events() {
        var evts = {}
        if (this.item && this.item.records) {
          var records
          if (this.activeTab.id == 1) {
            records = this.myRecords
          } else {
            records = this.item.records
          }
          for (var i = 0; i < records.length; i++) {
            var record = records[i]
            if (record.record_time) {
              var dateStr = this.$kyutil.date.format(record.record_time, 'yyyy-M-d')
              evts[dateStr] = record
            }
          }
        }
        return evts
      },
      dayRecords() {
        var evts = []
        if (this.item && this.item.records) {
          var records = this.item.records
          var attendUsers = {}
          for (var i = 0; i < records.length; i++) {
            var record = records[i]
            if (!attendUsers[record.user_id]) {
              attendUsers[record.user_id] = {
                avatar: record.avatar,
                id: record.id,
                displayname: record.real_name || record.activity_real_name || record.displayname,
              }
            }
            if (this.$kyutil.date.sameDay(record.record_time, this.currentDate)) {
              attendUsers[record.user_id].record_time = record.record_time
              attendUsers[record.user_id].record_id = record.record_id
              attendUsers[record.user_id].record_time_text = this.$kyutil.date.format(record.record_time, 'HH:mm')
            }
          }
          for (var userId in attendUsers) {
            var a = attendUsers[userId]
            if (!a.record_time) {
              a.record_time = "0"
            }
            evts.push(a)
          }
          evts = this.$kyutil.filters.orderBy(evts, 'record_time', -1)
        }
        return evts
      },
      currentDateText() {
        if (this.currentDate) {
          return this.$kyutil.date.format(this.currentDate, 'yyyy-MM-dd')
        }
        return ''
      }
    },
    methods: {
      getData() {
        var that = this;
        that.myRecords = []
        var param = {
          activityId: that.activityId
        };
        this.$kyutil.get("/pub/wx/activityuserrecord/records", param).then(res => {
          that.item = res;
          for (var i = 0; i < that.item.records.length; i++) {
            var record = that.item.records[i]
            if (record.user_id == that.userId) {
              that.myRecords.push(record)
            }
          }
          that.calendar.startTime = that.$kyutil.date.datePart(that.item.activity.start)
          that.calendar.endTime = that.$kyutil.date.datePart(that.item.activity.end)
          that.calendar.begin = that.dateArray(that.calendar.startTime)
          that.calendar.end = that.dateArray(that.calendar.endTime)
          var now = that.$kyutil.date.today()
          //已经报名的用户，至少有一条记录
          that.hasAttendActivity = that.myRecords.length > 0
          //活动已经结束
          that.isEnd = now > that.calendar.endTime
          //在活动期间，可以进行打卡操作
          that.canAddRecord = that.hasAttendActivity && that.calendar.startTime <= now && now <= that.calendar.endTime
          if (!that.calendar.value || !that.isBackPage) {
            if (that.calendar.startTime > now) {
              that.calendar.value = that.calendar.begin
            } else if (now >= that.calendar.endTime) {
              that.calendar.value = that.calendar.end
            } else {
              that.calendar.value = that.dateArray(now)
            }
          }
          if (!that.currentDate || !that.isBackPage) {
            that.currentDate = that.$kyutil.date.anyToDate(that.calendar.value)
          }
          that.loaded = true;
        })
      },
      bindDatePrev() {
        var newDate = this.$kyutil.date.addDays(this.currentDate, -1)
        if (newDate >= this.calendar.startTime) {
          this.currentDate = newDate
        }
      },
      bindDateNext() {
        var newDate = this.$kyutil.date.addDays(this.currentDate, 1)
        if (newDate <= this.calendar.endTime) {
          this.currentDate = newDate
        }
      },
      bindDateChange(e) {
        this.currentDate = e.mp.detail.value
      },
      dateArray(date) {
        var realDate = this.$kyutil.date.anyToDate(date)
        return [realDate.getFullYear(), realDate.getMonth() + 1, realDate.getDate()]
      },
      gotoAttend() {
        var that = this;
        wx.navigateTo({
          url: "../../pages/attend/attend?activityId=" + that.activityId
        });
      },
      addRecord() {
        var that = this;
        wx.navigateTo({
          url: "../../pages/activityrecorditem/activityrecorditem?uid=" + that.myRecords[0].id
        });
      },
      tabClick(tab) {
        this.activeTab = tab;
      },
      selectMonth(month, year) {
        console.log(year, month)
      },
      prev(month) {
        console.log(month)
      },
      next(month) {
        console.log(month)
      },
      selectYear(year) {
        console.log(year)
      },
      gotoViewRecord(record) {
        if (record && record.record_id) {
          wx.navigateTo({
            url: "../../pages/activityrecorditem/activityrecorditem?id=" + record.record_id
          });
        }
      },
      select(date) {
        var key = date.join('-')
        var record = this.events[key]
        if (record && record.record_id) {
          wx.navigateTo({
            url: "../../pages/activityrecorditem/activityrecorditem?id=" + record.record_id
          });
        }
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      var that = this;
      that.loaded = false;
      that.activeTab = that.activeTab || that.tabs[0]
      var activityId = this.$root.$mp.query.activityId || this.$root.$mp.query.scene;
      that.isBackPage = activityId == that.activityId
      that.activityId = activityId
      this.$kyutil.CheckUserValidation();
      var user = this.$kyutil.GetUser();
      if (user) {
        this.userId = user.id;
        this.getData();
      }
    }
  };
</script>

<style>
  .c-activity-record .weui-navbar__slider_0 {
    width: 40%;
    left: 0px;
    transform: translateX(35rpx);
  }
  .c-activity-record .weui-navbar__slider_1 {
    width: 40%;
    left: 35rpx;
    transform: translateX(400rpx);
  }
  .c-activity-record .c-custom-calendar .calendar td.has-event span {
    background-color: #F37B1D;
    color: #fff;
  }
  .c-activity-record .c-custom-calendar .calendar ._td.has-event:not(.selected) ._span:not(.red):hover {
    background-color: #F37B1D;
    color: #fff;
  }
  .c-activity-record .c-custom-calendar .dot {
    display: none;
  }
  .c-activity-record .c-calendar-tools {
    margin-bottom: 0px;
  }
</style>
