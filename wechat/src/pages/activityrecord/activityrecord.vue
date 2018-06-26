<template>
  <div class="page">
    <div v-if="item" class="page__bd">
      <div @click="checkdetails(item.activity.id)" class="weui-media-box weui-media-box_appmsg c-border-bottom" hover-class="weui-cell_active">
        <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;">
          <image class="weui-media-box__thumb" :src="item.activity.avatar" />
        </div>
        <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
          <div class="weui-media-box__title">{{item.activity.title}}</div>
          <div class="weui-media-box__desc" style="float:left">{{item.activity.start_time}}</div>
          <div class="weui-media-box__desc" style="float:right">喜欢：{{item.activity.favorite_count}} 报名：{{item.activity.attend_count}}</div>
        </div>
      </div>
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
          <div v-if="activeTab.id == 1">
            <Calendar :months="calendar.months" :value="calendar.value" @next="next" @prev="prev" :events="events" clean="true" multi="true" @select="select" ref="calendar" @selectMonth="selectMonth" @selectYear="selectYear" />
          </div>
        </div>
      </div>
      <div v-if="item && item.user_id == userId" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
        <div :disabled="processing" @click="addRecord()" class="weui-flex__item c-bg-primary">
          提交
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import Calendar from 'mpvue-calendar'
  export default {
    components: {
      Calendar
    },
    data() {
      return {
        isIpx: false,
        loaded: false,
        item: null,
        activityId: null,
        userId: null,
        canAddRecord:false,
        tabs: [{
          id: 1,
          title: '我的打卡',
          class: 'weui-navbar__slider_0'
        }, {
          id: 2,
          title: '所有记录',
          class: 'weui-navbar__slider_1'
        }],
        activeTab: null,
        calendar: {
          months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
          value: null,
          begin: null,
          end: null,
          lunar: false,
          clean: true
        }
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
            if(record.record_time){
              var dateStr = this.$kyutil.date.format(record.record_time, 'yyyy-MM-dd')
              evts[dateStr] = record
            }
          }
        }
        return evts
      }
    },
    methods: {
      getData() {
        var that = this;
        that.myRecords = []
        var param = {
          activityId: that.activityId
        };
        this.$kyutil.HttpRequest(
          true,
          "/pub/wx/activityuserrecord/records",
          false,
          "",
          param,
          "GET",
          false,
          function(res) {
            that.item = res;
            for (var i = 0; i < that.item.records.length; i++) {
              var record = that.item.records[i]
              if (record.user_id == that.userId) {
                that.myRecords.push(record)
              }
            
            }

            that.calendar.startTime = that.$kyutil.date.anyToDate(that.item.activity.start) 
            that.calendar.endTime = that.$kyutil.date.anyToDate(that.item.activity.end) 
            that.calendar.begin = that.dateArray(that.calendar.startTime)
            that.calendar.end = that.dateArray(that.calendar.endTime)


            var now = new Date()
            

            that.canAddRecord =  that.calendar.startTime <= now && now <= that.calendar.endTime
            if(that.calendar.startTime > now){
              that.calendar.value = that.calendar.begin
            } else if(now >= that.calendar.endTime){
              that.calendar.value = that.calendar.end
            } else {
that.calendar.value = that.dateArray(now)
            }

            that.loaded = true;
          }
        );
      },
      dateArray(date){
        var realDate = this.$kyutil.date.anyToDate(date) 
        return [realDate.getFullYear(), realDate.getMonth() + 1, realDate.getDate()]
      },
      checkdetails(activityId) {
        var that = this;
        that.activityId = activityId;
        wx.navigateTo({
          url: "../../pages/details/details?activityId=" + that.activityId
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
      setToday(val, val1, val2) {
        this.$refs.calendar.setToday();
      },
      select(val, val2) {
        console.log(val)
        console.log(val2)
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      var that = this;
      that.loaded = false;
      that.activeTab = that.tabs[0]
      that.activityId =
        this.$root.$mp.query.activityId || this.$root.$mp.query.scene;
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
  .c-checkin-num {
    font-weight: bold;
    font-size: 35px;
    margin: 30px;
  }
  .weui-navbar__slider_0 {
    width:40%;
    left: 0px;
    transform: translateX(35rpx);
  }
  .weui-navbar__slider_1 {
    width:40%;
    left: 35rpx;
    transform: translateX(400rpx);
  }
</style>
