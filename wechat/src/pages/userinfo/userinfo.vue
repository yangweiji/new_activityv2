<template>
  <div class="page">
    <div class="page__hd">
      <div class="weui-toptips weui-toptips_warn" v-if="errorMessage">{{errorMessage}}</div>
      <div class="page__title" style="text-align:center">请完善个人信息</div>
    </div>
    <div v-if="user" class="page__bd">
      <field :config="field" v-model="user[field.name]" v-for="field in fields" :key="field.name" />
    </div>
    <div v-if="user" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div :disabled="processing" @click="save()" class="weui-flex__item c-bg-primary">
        保存
      </div>
    </div>
  </div>
</template>

<script>
 import field from '@/components/field.vue'
  export default {
    data() {
      return {
        isIpx: false,
        errorMessage:null,
        fields: [{
          name: 'displayname',
          title: '昵称',
          type: 'text',
          required: true,
        }, {
          name: 'email',
          title: '邮箱',
          type: 'text',
          required: true,
        }, {
          name: 'gender',
          title: '性别',
          type: 'radio',
          required: true,
          options: [{
              title: "男",
              value: 0
            },
            {
              title: "女",
              value: 1
            }
          ]
        }, {
          name: 'bloodType',
          title: '血型',
          type: 'radio',
          required: true,
          options: [{
              title: "A",
              value: "A"
            },
            {
              title: "B",
              value: "B"
            },
            {
              title: "AB",
              value: "AB"
            },
            {
              title: "O",
              value: "O"
            },
          ]
        }, {
          name: 'clothingSize',
          title: 'T恤尺寸',
          type: 'radio',
          required: true,
          options: [{
              title: "S",
              value: "S"
            },
            {
              title: "M",
              value: "M"
            },
            {
              title: "L",
              value: "L"
            },
            {
              title: "XL",
              value: "XL"
            },
            {
              title: "XXL",
              value: "XXL"
            },
            {
              title: "XXXL",
              value: "XXXL"
            }
          ]
        }, {
          name: 'workCompany',
          title: '工作单位',
          type: 'text',
          required: true,
        }, {
          name: 'occupation',
          title: '职业',
          type: 'text',
          required: true,
        }, {
          name: 'emergencyContactName',
          title: '紧急联系人姓名',
          type: 'text',
          required: true,
        }, {
          name: 'emergencyContactMobile',
          title: '紧急联系人电话',
          type: 'text',
          required: true,
        }, {
          name: 'isParty',
          title: '是否党员',
          type: 'bool',
          required: false
        }, {
          name: 'address',
          title: '家庭地址',
          type: 'text',
          required: true,
        }, {
          name: 'wechatId',
          title: '微信号',
          type: 'text',
          required: true,
        }],
        user: null,
        userId: null,
      };
    },
    computed: {},
    components: { field },
    methods: {
      getData() {
        var that = this
        var param = {
          userId: this.userId
        };
        this.$kyutil.get("/pub/wx/profile/getuserinfo", param).then(res => {
          that.user = res
          
        })
      },
      resetError() {
      var that = this;
        setTimeout(() => {
          that.errorMessage = null;
        }, 2000);
      },
      save() {
        var that = this
        for (var i = 0; i < that.fields.length; i++) {
          var field = that.fields[i]
          if(field.required && (that.user[field.name] === null || that.user[field.name] === undefined || that.user[field.name] === '')){
            that.errorMessage = "请填写"+ field.title
            that.resetError()
            return
          }
        }
        this.$kyutil.post("/pub/wx/profile/saveuserinfo", that.user).then(res => {
          wx.showToast({
            title: '保存成功',
            icon: 'success',
            duration: 1000
          })
          setTimeout(()=>{
            wx.navigateBack({
            delta: 1
          })
          }, 1000)
          
        })
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      this.$kyutil.CheckUserValidation();
      var user = this.$kyutil.GetUser();
      if (user) {
        this.userId = user.id;
        this.getData();
      }
      wx.setNavigationBarTitle({
        title: "个人信息"
      })
    }
  };
</script>

<style scoped>
  .userinfo-avatar {
    margin: 0 auto;
    margin-top: 50rpx;
    display: flex;
    justify-content: center;
    overflow: hidden;
    width: 161rpx;
    height: 161rpx;
    border-radius: 50%;
  }
  .userinfo-name {
    margin: 0 auto;
    margin-top: 20rpx;
    display: flex;
    justify-content: center;
    overflow: hidden;
  }
  .weui-media-box-text {
    float: right;
    width: 50%;
    text-align: right;
    font-size: 18px;
    color: #f37b1d;
  }
  .weui-text {
    width: 95%;
    height: 25px;
    border: 1px solid #cdcdcd;
    margin-left: 2.5%;
  }
  .weui-title {
    width: 100%;
    height: 20px;
    margin: 2.5%;
  }
  .weui-radio-first {
    width: 100%;
    height: 110px;
  }
  .page_body {
    width: 100%;
    height: 100%;
    padding-bottom: 5px;
  }
  .weui-button-box {
    width: 100%;
    height: 30px;
  }
  .weui-commint-button {
    width: 50%;
    height: 20px;
    padding-left: 2.5%;
    background-color: #f37b1d;
    color: white;
  }
</style>
