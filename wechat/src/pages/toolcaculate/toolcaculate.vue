<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">配速计算器</div>
      <div class="page__desc">
        配速是运动速度的一种表示，定义为运动1公里或者1英里所用的时间，通常表示为分钟/公里。配速在长距离比赛中至关重要，为了刺激各种身体机能的全面改进，应该在不同的特定配速下综合训练。请输入距离、时间、配速中的任意两项，点击计算按钮可算出第三项的值。
      </div>
    </div>
    <div class="page__bd">
      <div class="weui-cells weui-cells_after-title c-caculate">
        <div class="weui-cell ">
          <div class="weui-flex">
            <div class="c-caculate-title">
              <div class="placeholder">距离：</div>
            </div>
            <div>
              <div class="placeholder">
                <input placeholder="..." v-model="item.distance" type="digit" />
              </div>
            </div>
            <div>
              <div class="placeholder">公里</div>
            </div>
          </div>
        </div>
        <div class="weui-cell">
          <div class="weui-flex">
            <div class="c-caculate-title">
              <div class="placeholder">时间：</div>
            </div>
            <div>
              <div class="placeholder"><input placeholder="..." v-model="item.timeHours" type="digit" /></div>
            </div>
            <div>
              <div class="placeholder">时</div>
            </div>
            <div>
              <div class="placeholder"><input placeholder="..." v-model="item.timeMinutes" type="digit" /></div>
            </div>
            <div>
              <div class="placeholder">分</div>
            </div>
            <div>
              <div class="placeholder"><input placeholder="..." v-model="item.timeSeconds" type="digit" /></div>
            </div>
            <div>
              <div class="placeholder">秒</div>
            </div>
          </div>
        </div>
        <div class="weui-cell ">
          <div class="weui-flex">
            <div class="c-caculate-title">
              <div class="placeholder">配速：</div>
            </div>
            <div>
              <div class="placeholder">
                <input placeholder="..." v-model="item.speedMinutes" type="digit" />
              </div>
            </div>
            <div>
              <div class="placeholder">分</div>
            </div>
            <div>
              <div class="placeholder"><input placeholder="..." v-model="item.speedSeconds" type="digit" /></div>
            </div>
            <div>
              <div class="placeholder">秒 / 公里</div>
            </div>
          </div>
        </div>
      </div>
      <div class="weui-cells__title c-text-center c-table">
        <div class="c-text-primary">
          参考：跑步各项目世界纪录配速表
        </div>
        <div class="weui-flex c-header">
          <div class="weui-flex__item">
            项目
          </div>
          <div class="weui-flex__item">
            用时
          </div>
          <div class="weui-flex__item">
            配速
          </div>
          <div class="weui-flex__item">
            时速
          </div>
        </div>
        <div v-for="(speed, index) in speeds" :key="index" class="weui-flex" :class="{'c-tr' : (index % 2) == 0}">
          <div class="weui-flex__item">
            {{speed[0]}}
          </div>
          <div class="weui-flex__item">
            {{speed[1]}}
          </div>
          <div class="weui-flex__item">
            {{speed[2]}}
          </div>
          <div class="weui-flex__item">
            {{speed[3]}}
          </div>
          
        </div>
      </div>
    </div>
    <div class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div class="weui-flex__item c-bg-default">
        <button open-type="share">分享</button>
      </div>
      <div @click="reset()" class="weui-flex__item c-bg-default">
        重置
      </div>
      <div @click="caculate()" class="weui-flex__item c-bg-primary">
        计算
      </div>
    </div>
  </div>
</template>

<script>
  import {
    Decimal
  } from "decimal.js";
  export default {
    data() {
      return {
        isIpx: false,
        item: {
          distance: null,
          timeHours: null,
          timeMinutes: null,
          timeSeconds: null,
          speedMinutes: null,
          speedSeconds: null
        },
        speeds: [
          ['50米男', '0:05.54', '1＇51＂', '32.49'],
          ['50米女', '0:05.96', '1＇59＂', '30.2'],
          ['55米男', '0:05.97', '1＇49＂', '33.17'],
          ['55米女', '0:06.45', '1＇57＂', '30.7'],
          ['60米男', '0:06:39', '1＇47＂', '33.8'],
          ['60米女', '0:06.92', '1＇55＂', '31.21'],
          ['100米男', '0:09.79', '1＇38＂', '36.77'],
          ['100米女', '0:10:49', '1＇45＂', '34.32'],
          ['200米男', '0:19:32', '1＇37＂', '37.27'],
          ['200米女', '0:21.34', '1＇47＂', '33.74'],
          ['300米男', '0:30', '1＇40＂', '36'],
          ['300米女', '0:33', '1＇50＂', '32.73'],
          ['400米男', '0:43.18', '1＇48＂', '33.35'],
          ['400米女', '0:47.60', '1＇59＂', '30.25'],
          ['500米男', '0:57.66', '1＇55＂', '31.22'],
          ['500米女', '1:03.70', '2＇07＂', '28.26'],
          ['600米男', '1:12.15', '2＇00＂', '29.94'],
          ['600米女', '1:20', '2＇13＂', '27'],
          ['800米男', '1:41.11', '2＇06＂', '28.48'],
          ['800米女', '1:53.28', '2＇22＂', '25.42'],
          ['1000米男', '2:10.50', '2＇11＂', '27.59'],
          ['1000米女', '2:26.50', '2＇27＂', '24.57'],
          ['1500米男', '3:25.80', '2＇17＂', '26.24'],
          ['1500米女', '3:52.47', '2＇35＂', '23.23'],
          ['1英里男', '3:42.60', '2＇18＂', '26.03'],
          ['1英里女', '4:11.60', '2＇36＂', '23.03'],
          ['2公里男', '4:43.20', '2＇22＂', '25.42'],
          ['2公里女', '5:21.50', '2＇41＂', '22.4'],
          ['3公里男', '7:20', '2＇27＂', '24.55'],
          ['3公里女', '8:21.42', '2＇47＂', '21.54'],
          ['2英里男', '7:54.60', '2＇27＂', '24.41'],
          ['2英里女', '9:01.50', '2＇48＂', '21.4'],
          ['4公里男', '9:58', '2＇30＂', '24.08'],
          ['4公里女', '11:23', '2＇51＂', '21.08'],
          ['3英里男', '12:10', '2＇31＂', '23.81'],
          ['3英里女', '13:53', '2＇53＂', '20.87'],
          ['5公里（路跑）男', '12:59', '2＇36＂', '23.11'],
          ['5公里男', '12:37', '2＇31＂', '23.78'],
          ['5公里女', '14:24.68', '2＇53＂', '20.82'],
          ['5公里（路跑）女', '14:46', '2＇57＂', '20.32'],
          ['6公里（路跑）男', '15:42', '2＇37＂', '22.93'],
          ['6公里男', '15:19', '2＇33＂', '23.5'],
          ['6公里（路跑）女', '17:51', '2＇59＂', '20.17'],
          ['6公里女', '17:31', '2＇55＂', '20.55'],
          ['4英里（路跑）男', '16:54', '2＇38＂', '22.85'],
          ['4英里男', '16:30', '2＇34＂', '23.41'],
          ['4英里女', '18:52', '2＇56＂', '20.47'],
          ['4英里（路跑）女', '19:12', '2＇59＂', '20.12'],
          ['8公里（路跑）男', '21:12', '2＇39＂', '22.64'],
          ['8公里男', '20:47', '2＇36＂', '23.1'],
          ['8公里女', '23:45', '2＇58＂', '20.21'],
          ['8公里（路跑）女', '24:02', '3＇00＂', '19.97'],
          ['5英里男', '20:55', '2＇36＂', '23.08'],
          ['5英里（路跑）男', '21:19', '2＇39＂', '22.65'],
          ['5英里（路跑）女', '24:12', '3＇00＂', '19.95'],
          ['5英里女', '23:55', '2＇58＂', '20.19'],
          ['10公里男', '26:20', '2＇38＂', '22.78'],
          ['10英里（路跑）男', '26:43', '2＇40＂', '22.46'],
          ['10英里（路跑）女', '30:20', '3＇02＂', '19.78'],
          ['10公里女', '30:10.09', '3＇00＂', '19.99'],
          ['12公里（路跑）男', '32:22', '2＇42＂', '22.25'],
          ['12公里（路跑）女', '36:34', '3＇03＂', '19.69'],
          ['15公里（路跑）男', '40:55', '2＇44＂', '22'],
          ['15公里（路跑）女', '45:55', '3＇04＂', '19.6'],
          ['10英里（路跑）男', '44:00', '2＇44＂', '21.95'],
          ['10英里（路跑）女', '49:21', '3＇04＂', '19.57'],
          ['20公里（路跑）男', '55:15', '2＇46＂', '21.72'],
          ['20公里（路跑）女', '1:01:40', '3＇05＂', '19.46'],
          ['半程马拉松（路跑）男', '58:23', '2＇46＂', '21.68'],
          ['半程马拉松（路跑）女', '1:05:12', '3＇05＂', '19.41'],
          ['25公里（路跑）男', '1:10:05', '2＇48＂', '21.4'],
          ['25公里（路跑）女', '1:17:45', '3＇07＂', '19.29'],
          ['30公里（路跑）男', '1:25:10', '2＇50＂', '21.14'],
          ['30公里（路跑）女', '1:34:20', '3＇09＂', '19.08'],
          ['马拉松（路跑）男', '2:02:57', '2＇55＂', '20.59'],
          ['马拉松（路跑）女', '2:15:25', '3＇13＂', '18.7'],
          ['50公里（路跑）男', '2:29:30', '2＇59＂', '20.07'],
          ['50公里（路跑）女', '2:43:40', '3＇16＂', '18.33'],
          ['50英里（路跑）男', '4:28:00', '3＇20＂', '18.02'],
          ['50英里（路跑）女', '4:56:00', '3＇41＂', '16.31'],
          ['100公里（路跑）男', '5:56:00', '3＇34＂', '16.85'],
          ['100公里（路跑）女', '6:33:11', '3＇56＂', '15.26'],
          ['150公里（路跑）男', '10:05:00', '4＇02＂', '14.88'],
          ['150公里（路跑）女', '11:01:40', '4＇25＂', '13.6'],
          ['100英里（路跑）男', '11:04:10', '4＇08＂', '14.54'],
          ['100英里（路跑）女', '12:05:00', '4＇30＂', '13.32'],
          ['200公里（路跑）男', '14:40:00', '4＇24＂', '13.64'],
          ['200公里（路跑）女', '16:00:00', '4＇48＂', '12.5']
        ]
      };
    },
    components: {},
    methods: {
      getData() {},
      getTimeSeconds() {
        return this.$kyutil.date.getSeconds(this.item.timeHours, this.item.timeMinutes, this.item.timeSeconds)
      },
      getSpeedSeconds() {
        return this.$kyutil.date.getSeconds(0, this.item.speedMinutes, this.item.speedSeconds)
      },
      checkNull() {
        for (var key in this.item) {
          if (this.item[key] == 0) {
            this.item[key] = null
          }
        }
      },
      caculate() {
        if (this.item.distance && (this.item.timeHours || this.item.timeMinutes || this.item.timeSeconds)) {
          var speed = this.getTimeSeconds() / this.item.distance
          this.item.speedMinutes = new Decimal(speed / 60).floor().toNumber()
          this.item.speedSeconds = new Decimal(speed % 60).toDP(2).toNumber()
          this.checkNull()
        } else if (this.item.distance && (this.item.speedMinutes || this.item.speedSeconds)) {
          var time = this.getSpeedSeconds() * this.item.distance
          this.item.timeHours = new Decimal(time / 3600).floor().toNumber()
          time = time % 3600
          this.item.timeMinutes = new Decimal(time / 60).floor().toNumber()
          this.item.speedSeconds = new Decimal(time % 60).toDP(2).toNumber()
          this.checkNull()
        } else if ((this.item.speedMinutes || this.item.speedSeconds) && (this.item.timeHours || this.item.timeMinutes || this.item.timeSeconds)) {
          var distance = this.getTimeSeconds() / this.getSpeedSeconds()
          this.item.distance = new Dicimal(distance).toDP(2).toNumber()
          this.checkNull()
        } else {
          wx.showModal({
            content: '距离、时间、配速三项中必须输入，且只输入两项，请重新输入',
            showCancel: false,
            success: function(res) {
              if (res.confirm) {}
            }
          });
        }
      },
      reset() {
        this.item = {
          distance: null,
          timeHours: null,
          timeMinutes: null,
          timeSeconds: null,
          speedMinutes: null,
          speedSeconds: null
        }
      },
      share() {}
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShareAppMessage(res) {
      return {
        title: "配速计算",
        path: 'pages/toolcaculate/toolcaculate'
      }
    }
  };
</script>

<style scoped>
  .c-caculate {
    color: gray;
  }
  .c-caculate-title {
    width: 120rpx;
    color: black;
  }
  .c-caculate input {
    width: 150rpx;
    /* border-bottom: 1px lightgrey solid; */
    text-align: center;
    color: black;
  }
  .page__hd {
    background-color: #F37B1D;
    color: white;
    padding: 40rpx;
  }
  .page__desc {
    color: white;
  }
</style>
