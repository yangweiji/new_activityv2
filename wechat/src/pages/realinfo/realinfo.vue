<template>
  <div class="page">
    <div v-if="user" class="page__hd">
      <div class="weui-toptips weui-toptips_warn" v-if="errorMessage">{{errorMessage}}</div>
      <div class="page__title" style="text-align:center" v-if="user.isReal">您已完成实名认证</div>
      <div class="page__title" style="text-align:center" v-if="!user.isReal">请完善实名认证相关信息</div>
    </div>
    <div v-if="user" class="page__bd">


      <field v-if="user.isReal" :config="field" v-model="user[field.name]" :disabled="user.isReal" v-for="field in realFields" :key="field.name" />

      <field v-if="!user.isReal" :config="field" v-model="user[field.name]" v-for="field in fields" :key="field.name" />

    </div>
    <div v-if="user && !user.isReal" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
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
        fromVip:0,
        errorMessage:null,
        realFields:[
          {
          name: 'realName',
          title: '真实姓名',
          type: 'text'
        },
        {
          name: 'idCard',
          title: '身份证号',
          type: 'idcard'
        },{
          name: 'wechatId',
          title: '微信号',
          type: 'text'
        }
        ],
        fields: [{
          name: 'realName',
          title: '真实姓名',
          type: 'text',
          required: true,
        },
        {
          name: 'idCard',
          title: '身份证号',
          type: 'idcard',
          required: true,
        },{
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
          name: 'wechatId',
          title: '微信号',
          type: 'text',
          required: true,
        },{
          name: 'gender',
          title: '性别',
          type: 'radio',
          required: true,
          options: [{
              title: "男",
              value: 1
            },
            {
              title: "女",
              value: 2
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

        if(!that.$kyutil.emailValid(that.user.email)){
          that.errorMessage = "您的邮箱信息不合法，请重新输入"
            that.resetError()
            return
        }

        if(!that.$kyutil.idcardValid(that.user.idCard)){
          that.errorMessage = "您的身份证号不合法，请重新输入"
            that.resetError()
            return
        }

        for (var i = 0; i < that.fields.length; i++) {
          var field = that.fields[i]

          if(field.required && (that.user[field.name] === null || that.user[field.name] === undefined || that.user[field.name] === '')){
            that.errorMessage = "请填写"+ field.title
            that.resetError()
            return
          }


        }
        this.$kyutil.post("/pub/wx/profile/saverealinfo", that.user).then(res => {
          if(!that.fromVip){
            wx.navigateBack({
              delta: 1
            })
          } else {
            wx.redirectTo({
              url: "../../pages/myvip/myvip?start=1&communityId=" + that.fromVip
            })
          }
        })
      }
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      var that = this;
      // this.$kyutil.CheckUserValidation();
      // var user = this.$kyutil.GetUser();
      // this.fromVip = this.$root.$mp.query.fromVip;
      // if (user) {
      //   this.userId = user.id;
      //   this.getData();
      // }

      this.$kyutil.CheckUserValidation().then(function(res) {
          var user = that.$kyutil.GetUser();
          that.userId = user.id;
          that.getData();
      });

      wx.setNavigationBarTitle({
        title: "个人信息"
      })
    }
  };
</script>

<style scoped>
  
</style>
