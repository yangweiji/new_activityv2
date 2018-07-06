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
      <div class="weui-toptips" :class="{'weui-toptips_warn': isError, 'weui-toptips_primary': !isError}" 
            v-if="showTopTips">{{infoMessage}}</div>
      
      <div class="weui-cells__title">新用户注册，已有账号可
        <navigator url="../../pages/login/login" open-type="redirect" hover-class="navigator-hover" class="weui-agree__link">直接登录</navigator>
      </div>
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
        <!-- <button class="weui-btn" type="primary" @click="bindRegister" :disabled="disabled">确定</button> -->
         <!-- 需要使用 button 来授权登录 -->
        <button class="weui-btn" type="primary" open-type="getUserInfo" @getuserinfo="bindGetUserInfo" v-if="canIUse" :disabled="disabled">授权注册</button>
        <view v-else>请升级微信版本</view>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      isError: true,
      showTopTips: false,
      isAgree: false,
      infoMessage: null,

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
        this.$kyutil.get("/pub/vercode/getVerCode/" + that.username).then(res => {
          if (res.code != 200) {
            console.log("获取短信验证码出错！");
            return;
          }

          that.isError = false;
          that.infoMessage = "短信验证码已发送，10分钟内有效！";
          that.showTopTipsFun();

          //测试环境下，直接显示出验证码
          that.vercode = res.message;
          that.canGetVerCode = false;
          that.count = 60;
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
    bindRegister (e) {
      //用户注册
    },
    bindGetUserInfo: function(e) {
      //授权注册
      console.log("bindGetUserInfo: ", e)
      //验证用户名和密码
      var that = this;
      var param = {};
      var userInfo = e.mp.detail.userInfo
      console.log("userInfo: ", userInfo);
      if (userInfo) {
        //允许授权
        param = {
          sessionKey: wx.getStorageSync("sessionInfo").sessionKey,
          encryptedData: e.mp.detail.encryptedData,
          ivStr: e.mp.detail.iv,
        }
        that.$kyutil.get("/pub/wx/auth/getMiniAppUserInfo", param).then(res => {
          console.log("getMiniAppUserInfo: ", res);
          if (res.code == 200) {
            param = {
              username: that.username,
              password: that.password,
              vercode: that.vercode,
              openId: res.openid,
              nickName: res.nickName,
              avatarUrl: res.avatarUrl,
            }
            that.$kyutil.post("/pub/wx/auth/register", param).then(res => {
              console.log("userLogin: " + res)
              if (res.code == 200) {
                that.$kyutil.get("/pub/wx/auth/getUserInfo", { "openid": wx.getStorageSync("sessionInfo").openid }).then(res => {
                    console.log("user: ", res)
                    if (res) {
                        wx.setStorageSync("user", res)
                        // wx.navigateBack({
                        //   delta: 1
                        // });
                        wx.showToast({
                          title: '登录成功，跳转中...',
                          icon: 'success',
                          duration: 3000,
                          success: function (e) {
                            wx.switchTab({
                              url:"../../pages/index/index",
                              success: function (e) {
                                console.log("登录成功，转向首页")
                              }
                            });      
                          }
                        });
                    }
                });
              }
              else {
                that.isError = true;
                that.infoMessage = res.message;
                that.showTopTipsFun();
              }
            })
          }
        })
        
      }
      else {
        console.log("拒绝授权")
      }

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
