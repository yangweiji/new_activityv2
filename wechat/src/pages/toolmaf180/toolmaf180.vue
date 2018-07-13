<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">MAF180最大有氧心率计算</div>
      <div class="page__desc">
        MAF( Maximum Aerobic function)，即最大有氧心率训 练法，是由著名耐力运动教练Dr。 Phil maffetone所倡导 的耐力训练方法，因其心率计算采用了180减去年龄的简 易经验方式，又称MAF180训练法。该方法的核心是通过 在有氧心率区间内的持续训练，使得身体更多地使用脂肪 而不是碳水化合物来供能，可显著地提升耐力水平。输入 年龄后选择与你比配的选项即可计算出你的最大有氧心 率，在进行MAF训练时，让心率保持在接近但不超过最大 有氧心率的水平。MAF心率相当于有氧阈值心率，由其它
        方式计算出的有氧阈值心率亦可供参考。
      </div>
    </div>
    <div class="page__bd">
      <field :config="ageField" v-model="item.age" />
      <field :config="statusField" v-model="item.status" />
      <div class="weui-cells__title">
        <div class="weui-flex">
          <div>NOTE0：</div>
          <div>MAF180公式是一个经验公式，请根据你的真实健康状况做调整，适当保守一点</div>
        </div>
        <div class="weui-flex">
          <div>NOTE1：</div>
          <div>16岁及以下年龄的跑者不适用。</div>
        </div>
        <div class="weui-flex">
          <div>NOTE2：</div>
          <div>65岁以上的跑者需要另作调整。</div>
        </div>
        <div class="weui-flex">
          <div>NOTE3：</div>
          <div>对于训练有素非常健康的跑者可以在d的基础上最多再加5。</div>
        </div>
      </div>
      <field :config="additionalField" v-model="item.additional" />
      <div class="weui-cells__title c-text-primary c-text-center c-main-value">
        <span v-if="item.value">您的MAF心率是{{item.value}}</span>
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
      var ages = () => {
        var ages = [];
        for (let index = 17; index < 70; index++) {
          ages.push(index)
        }
        return ages
      }
      return {
        isIpx: false,
        item: {
          age: 30,
          status: null,
          additional: 0,
          value: null
        },
        ageField: {
          name: 'age',
          title: '年龄',
          type: 'picker',
          options: ages()
        },
        additionalField: {
          name: 'additional',
          title: '额外调整',
          type: 'picker',
          options: [-5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5]
        },
        statusField: {
          name: 'status',
          title: '选项',
          type: 'check',
          options: [{
              title: "a.如果你正患有或处在一场大病的康复过程中(如心脏病、手术或其它需要住院治疗的疾病)，或者目前仍在持续服用某种药物，减10。",
              value: "a"
            },
            {
              title: "b.如果你受伤了，最近比赛或训练的成绩出现明显倒退，每年超过两次感冒、流感或其它感染，患有季节性过敏或哮喘，训练缺之持续性，或刚重新开始训练，有任何症状之一，再减去5。",
              value: 'b'
            },
            {
              title: "C。如果你坚持训练(每周至少四次)达两年，没有遇到前面两条提到的任何问题，则不用调整。",
              value: 'c'
            },
            {
              title: "d.如果你坚持训！练超过两年，从未遇到前面两条提到的任何问题，并且成绩逐年提升，则加5。",
              value: 'd'
            }
          ]
        }
      };
    },
    computed: {
    },
    components: {
      field
    },
    methods: {
      getData() {},
      caculate() {
        var value = 180 - this.item.age + this.item.additional
        if (this.item.status) {
          if (this.item.status.indexOf('a') >= 0) {
            value -= 10
          }
          if (this.item.status.indexOf('b') >= 0) {
            value -= 5
          }
          if (this.item.status.indexOf('d') >= 0) {
            value += 5
          }
        }
        this.item.value = new Decimal(value).floor().toNumber()

        wx.pageScrollTo({
          scrollTop: 3000,
          duration: 300
        })
      },
      reset() {
        this.item = {
          age: 30,
          status: null,
          additional: 0,
          value: null
        }
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShareAppMessage(res) {
      return {
        title: "MAF180最大有氧心率计算",
        path: 'pages/toolmaf180/toolmaf180'
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
  .c-main-value{
    margin-bottom: 50px;
    font-size: 30px;
    height: 40px;
  }
</style>
