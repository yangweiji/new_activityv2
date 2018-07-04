<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">配速计算器</div>
      <div class="page__desc">上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件 上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上传组件上
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
        }
      };
    },
    components: {},
    methods: {
      getData() {},
      getTimeSeconds() {
        var seconds = 0
        if (this.item.timeHours) {
          seconds += this.item.timeHours * 3600
        }
        if (this.item.timeMinutes) {
          seconds += this.item.timeMinutes * 60
        }
        if (this.item.timeSeconds) {
          seconds += this.item.timeSeconds
        }
        return seconds
      },
      getSpeedSeconds() {
        var seconds = 0
        if (this.item.speedMinutes) {
          seconds += this.item.speedMinutes * 60
        }
        if (this.item.speedSeconds) {
          seconds += this.item.speedSeconds
        }
        return seconds
      },
      checkNull(){
        for(var key in this.item){
          if(this.item[key] == 0){
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
    onShareAppMessage(res){
    return {
      title: "配速计算",
      path: '/page/toolcaculate/toolcaculate'
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
