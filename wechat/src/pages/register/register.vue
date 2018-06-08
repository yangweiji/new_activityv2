<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">华兴众泰活动平台管理系统</div>
      <!-- <div class="page__desc">表单输入</div> -->

      <!-- 如果只是展示用户头像昵称，可以使用 <open-data /> 组件 -->
      <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
      <open-data class="userinfo-name" type="userNickName"></open-data>
    </div>

    <div class="page__bd">
      <div class="weui-toptips weui-toptips_warn" v-if="showTopTips">错误提示</div>
      
      <div class="weui-cells__title">新用户注册</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input weui-cell_vcode">
          <div class="weui-cell__hd">
            <div class="weui-label">手机号</div>
          </div>
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入手机号" type="number" v-model="username" />
          </div>
          <div class="weui-cell__ft">
            <div class="weui-vcode-btn" @click="bindGetVerCode" 
                v-if="validateUserName" :class="{'vcode-btn-disabled': !canGetVerCode}">获取验证码
              <span v-if="count">({{count}})</span>
            </div>
          </div>
        </div>

        <div class="weui-cell weui-cell_input weui-cell_vcode">
          <div class="weui-cell__hd">
            <div class="weui-label">验证码</div>
          </div>
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入短信验证码" type="number" v-model="vercode" />
          </div>
          <div class="weui-cell__ft">
            <!-- <image class="weui-vcode-img" src="/static/images/vcode.jpg" style="width: 108px"></image> -->
          </div>
        </div>

        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__hd">
            <div class="weui-label">密码</div>
          </div>
          <div class="weui-cell__bd">
            <input class="weui-input" password type="text" placeholder="请输入字符和数字密码" v-model="password" />
          </div>
        </div>
        
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__hd">
            <div class="weui-label">再次输入</div>
          </div>
          <div class="weui-cell__bd">
            <input class="weui-input" password type="text" placeholder="请输入字符和数字密码" v-model="password2" />
          </div>
        </div>

      </div>

      <!-- <div class="weui-cells__tips">底部说明文字底部说明文字</div> -->

      <checkbox-group @click="bindAgreeChange">
        <label class="weui-agree" for="weuiAgree">
          <div class="weui-agree__text">
            <checkbox class="weui-agree__checkbox" id="weuiAgree" value="agree" checked="isAgree" />
            <div class="weui-agree__checkbox-icon">
              <icon class="weui-agree__checkbox-icon-check" type="success_no_circle" size="9" v-if="isAgree"></icon>
            </div>
            阅读并同意
            <navigator url="" class="weui-agree__link">《相关条款》</navigator>
          </div>
        </label>
      </checkbox-group>

      <div class="weui-btn-area">
        <button class="weui-btn" type="primary" @click="register" :disabled="disabled">确定</button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      showTopTips: false,
      isAgree: false,
      errorInfo: null,

      username: "",
      password: "",
      password2: "",
      vercode: "",
      count: 0,
      canGetVerCode: true,
      canIUse: wx.canIUse('button.open-type.getUserInfo')
    }
  },
  computed: {
    validateUserName () {
      if (this.username.length == 11)
        return true;
      else
        return false;
    },
    disabled () {
      if (!this.username || this.username.length != 11 
            || !this.vercode || this.vercode.length == 0
            || !this.password || this.password.length == 0
            || !this.password2 || this.password2.length == 0
            || (this.password != this.password2)
            || !this.isAgree
          )
      {
        return true;
      }
      else {
        return false;
      }
    },
  },
  components: {},
  methods: {
    showTopTipsFun() {
      this.showTopTips = true;
      setTimeout(() => {
        this.showTopTips = false;
      }, 2000)
    },
    bindAgreeChange(e) {
      this.isAgree = !this.isAgree;
    },
    bindGetVerCode(e) {
      //获取短信验证码
      var that = this;
      if (that.canGetVerCode) {
        global.HttpRequest(false, "/pub/vercode/getVerCode/" + that.username, false, "", "", "GET", false, function(res) {
          if (!res) {
            console.log("获取短信验证码出错！");
            return;
          }

          that.canGetVerCode = false;
          that.count = 10;
          var i = setInterval(() => {
            that.count --;
            if (that.count <= 0) {
              clearInterval(i)
              that.canGetVerCode = true    
            }          
          }, 1000)
        })
      }
      else {
        console.log("请等待..");
      }
    },
    register (e) {
      //新用户注册
    },

  }
}
</script>

<style>
.vcode-btn-disabled {
  color: #999;
}
.userinfo-avatar {
  /* width: 140rpx;
  height: 140rpx;
  border-radius: 50%;
  margin-top: 40rpx;
  display: block;
  overflow: hidden; */
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
.placeholder {
  margin: 5px;
  padding: 0 10px;
  text-align: center;
  background-color: #ebebeb;
  height: 2.3em;
  line-height: 2.3em;
  color: #cfcfcf;
}
</style>
