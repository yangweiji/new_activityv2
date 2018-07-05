<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">波士顿马拉松参赛资格</div>
      <div class="page__desc">
        第122届波马将于2018年4月16日举办，9月11日至20日分多轮开发报名，在所属性别和年龄组成绩越优秀者报名成功率越高，以2016年9月17日及以后取得的、并被波马主办方认可的赛事成绩为准，输入你的最近马拉松成绩看看是否有可能满足参赛资格。
      </div>
    </div>
    <div class="page__bd">
      <div class="weui-cells weui-cells_after-title c-caculate">
        <div class="weui-cell weui-cell_select">
          <div class="weui-cell__hd weui-cell__hd_in-select-after">
            <div class="weui-label c-caculate-title">性别：</div>
          </div>
          <div class="weui-cell__bd">
            <field :config="genderField" v-model="item.gender" type="picker" mode="clean" />
          </div>
        </div>
        <div class="weui-cell ">
          <div class="weui-flex">
            <div class="c-caculate-title">
              <div class="placeholder">比赛日年龄：</div>
            </div>
            <div>
              <div class="placeholder">
                <input placeholder="..." v-model="item.age" type="digit" />
              </div>
            </div>
            <div>
              <div class="placeholder">岁</div>
            </div>
          </div>
        </div>
        <div class="weui-cell">
          <div class="weui-flex">
            <div class="c-caculate-title">
              <div class="placeholder">马拉松用时：</div>
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
      </div>
      <div v-if="item.matchItem" class="weui-cells__title c-text-center c-table">
        <div class="c-text-primary">
          所在年龄组：{{item.matchItem.age[0]}}-{{item.matchItem.age[1]}}岁
        </div>
        <div class="weui-flex c-header">
          <div class="weui-flex__item">
            开放报名时间
          </div>
          <div class="weui-flex__item">
            报名资格
          </div>
          <div class="weui-flex__item">
            资格标准
          </div>
        </div>
        <div class="weui-flex c-tr">
          <div class="weui-flex__item">
            9月11日-9月12日
          </div>
          <div class="weui-flex__item">
            <span v-if="item.matchs[0]">是</span>
            <span v-if="!item.matchs[0]">否</span>
          </div>
          <div class="weui-flex__item">
            <text>＜＝{{item.matchItem.standard[0]}}</text>
          </div>
        </div>
        <div class="weui-flex c-tr2">
          <div class="weui-flex__item">
            9月13日-9月14日
          </div>
          <div class="weui-flex__item">
            <span v-if="item.matchs[1]">是</span>
            <span v-if="!item.matchs[1]">否</span>
          </div>
          <div class="weui-flex__item">
            <text>＜＝{{item.matchItem.standard[1]}}</text>
          </div>
        </div>
        <div class="weui-flex c-tr">
          <div class="weui-flex__item">
            9月15日-9月15日
          </div>
          <div class="weui-flex__item">
            <span v-if="item.matchs[2]">是</span>
            <span v-if="!item.matchs[2]">否</span>
          </div>
          <div class="weui-flex__item">
            <text>＜＝{{item.matchItem.standard[2]}}</text>
          </div>
        </div>
        <div class="weui-flex">
          <div class="weui-flex__item c-text-primary">
            美东时间晚上10点
          </div>
        </div>
        <div class="weui-flex c-tr">
          <div class="weui-flex__item">
            9月18日-9月20日
          </div>
          <div class="weui-flex__item">
            <span v-if="item.matchs[3]">是</span>
            <span v-if="!item.matchs[3]">否</span>
          </div>
          <div class="weui-flex__item">
            <text>＜＝{{item.matchItem.standard[3]}}</text>
          </div>
        </div>
        <div class="weui-flex">
          <div class="weui-flex__item c-text-primary">
            美东时间下午5点
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
  import field from '@/components/field.vue'
  export default {
    data() {
      return {
        isIpx: false,
        genderField: {
          name: 'gender',
          title: '性别',
          type: 'picker',
          options: ['男', '女']
        },
        item: {
          gender: '男',
          age: null,
          timeHours: null,
          timeMinutes: null,
          timeSeconds: null,
          matchs: [],
          matchItem: null,
        },
        matchDatas: {
          '男': [{
            age: [18, 34],
            standard: ["2:45:00", "2:55:00", "3:00:00", "3:05:00"]
          }],
          '女': [{
            age: [18, 34],
            atandard: ["3:15:00", "3:25:00", "3:30:00", "3:35:00"]
          }]
        }
      };
    },
    components: {
      field
    },
    methods: {
      getData() {},
      getTimeSeconds() {
        return this.$kyutil.date.getSeconds(this.item.timeHours, this.item.timeMinutes, this.item.timeSeconds)
      },
      checkNull() {
        for (var key in this.item) {
          if (this.item[key] == 0) {
            this.item[key] = null
          }
        }
      },
      caculate() {
        if (this.item.gender && this.item.age && (this.item.timeHours || this.item.timeMinutes || this.item.timeSeconds)) {
          var speed = this.getTimeSeconds()
          var matchItems = this.matchDatas[this.item.gender]
          var matchItem;
          for (let index = 0; index < matchItems.length; index++) {
            const element = matchItems[index];
            if (this.item.age >= element.age[0] && this.item.age <= element.age[1]) {
              matchItem = element
              break;
            }
          }
          if (matchItem) {
            this.item.matchs = []
            for (let index = 0; index < matchItem.standard.length; index++) {
              const std = matchItem.standard[index];
              var times = std.split(':')
              var stdSeconds = this.$kyutil.date.getSeconds(times[0], times[1], times[2])
              this.item.matchs.push(stdSeconds >= speed)
            }
            this.item.matchItem = matchItem
          } else {
            this.item.matchItem = null
            wx.showModal({
              content: '输入年龄不合理，或年龄不在允许的范围内，请重新输入',
              showCancel: false,
              success: function(res) {
                if (res.confirm) {}
              }
            });
          }
          this.checkNull()
        } else {
          this.item.matchItem = null
          wx.showModal({
            content: '性别、比赛日时年龄、马拉松用时三项必须输入值，请重新输入',
            showCancel: false,
            success: function(res) {
              if (res.confirm) {}
            }
          });
        }
      },
      reset() {
        this.item = {
          gender: '男',
          age: null,
          timeHours: null,
          timeMinutes: null,
          timeSeconds: null,
          matchs: [],
          matchItem: null,
        }
      },
      share() {}
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShareAppMessage(res) {
      return {
        title: "波士顿马拉松参赛资格",
        path: '/page/toolbomamatch/toolbomamatch'
      }
    }
  };
</script>

<style scoped>
  .c-caculate {
    color: gray;
  }
  .c-caculate-title {
    width: 220rpx;
    color: black;
  }
  .c-caculate-title picker {
    color: black;
    text-align: right;
  }
  .c-caculate input {
    width: 130rpx;
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
