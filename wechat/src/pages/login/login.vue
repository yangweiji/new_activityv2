<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">华兴众泰活动平台管理系统</div>
      <!-- <div class="page__desc">用户登录与注册</div> -->
      
      <!-- 如果只是展示用户头像昵称，可以使用 <open-data /> 组件 -->
      <open-data class="userinfo-avatar" type="userAvatarUrl"></open-data>
      <open-data class="userinfo-name" type="userNickName"></open-data>
      
    </div>

    <div class="page__bd">
      <!-- <div class="weui-flex">
        <div class="weui-flex__item">
          <div class="placeholder">
            测试
          </div>
        </div>
      </div> -->

      <div class="weui-toptips" :class="{'weui-toptips_warn': isError, 'weui-toptips_primary': !isError}" 
            v-if="showTopTips">{{infoMessage}}</div>

      <div class="weui-cells__title">用户登录，
        <navigator url="../../pages/register/register" hover-class="navigator-hover" class="weui-agree__link">新用户注册</navigator>
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
        
      </div>

      <!-- <div class="weui-cells__tips">底部说明文字底部说明文字</div> -->
      <div class="weui-btn-area">
        <!-- <button class="weui-btn" type="primary" @click="bindLogin" :disabled="disabled">确定</button> -->
        <!-- 需要使用 button 来授权登录 -->
        <button class="weui-btn" type="primary" open-type="getUserInfo" @getuserinfo="bindGetUserInfo" v-if="canIUse" :disabled="disabled">授权登录</button>
        <view v-else>请升级微信版本</view>
        <!-- <button class="weui-btn" type="warn" @click="register">注册新用户</button> -->
      </div>
    </div>
  </div>
</template>

<script>
import global from '../../global/index'
export default {
  data() {
    return {
      isError: true,
      showTopTips: false,
      infoMessage: null,

      username: "",
      password: "",
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
    bindGetVerCode(e) {
      //获取短信验证码
      var that = this;
      if (that.canGetVerCode) {
        global.HttpRequest(false, "/pub/vercode/getVerCode/" + that.username, false, "", "", "GET", false, function(res) {
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
    // bindLogin (e) {
    //   //验证用户名和密码
    //   var that = this;
    //   var param = {
    //     username: that.username,
    //     password: that.password,
    //     vercode: that.vercode,
    //     openId: wx.getStorageSync("sessionInfo").openid,
    //   };

    //   global.HttpRequest(false, "/pub/wx/auth/userLogin", 2, "", param, "POST", false, function(res) {
    //     if (res.code == 200) {
    //       wx.navigateBack({
    //         delta: 1
    //       })
    //     }
    //     else {
    //       that.infoMessage = res.message;
    //       that.showTopTipsFun();
    //     }
    //   })
    // },
    bindGetUserInfo: function(e) {
      console.log("bindGetUserInfo: ", e)
      //验证用户名和密码
      var that = this;
      var param = {};
      if (e.mp.detail.userInfo) {
        //允许授权
        param = {
          sessionKey: wx.getStorageSync("sessionInfo").sessionKey,
          encryptedData: e.mp.detail.encryptedData,
          ivStr: e.mp.detail.iv,
        }
        global.HttpRequest(false, "/pub/wx/auth/getUserInfo", false, "", param, "GET", false, function(res) {
          console.log("getUserInfo: ", res);
          if (res.code == 200) {
            param = {
              username: that.username,
              password: that.password,
              vercode: that.vercode,
              openId: wx.getStorageSync("sessionInfo").openid,
            }
            global.HttpRequest(false, "/pub/wx/auth/userLogin", 2, "", param, "POST", false, function(res) {
              console.log("userLogin: " + res)
              if (res.code == 200) {
                wx.navigateBack({
                  delta: 1
                })
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
    register (e) {
      wx.navigateTo({
        url: "../../pages/register/register"
      });
    },
  },
  created() {
    console.log("login created");
  },
  onLoad: function() {
    // // 查看是否授权
    // wx.getSetting({
    //   success: function(res){
    //     if (res.authSetting['scope.userInfo']) {
    //       // 已经授权，可以直接调用 getUserInfo 获取头像昵称
    //       wx.getUserInfo({
    //         success: function(res) {
    //           // console.log(res.userInfo)
    //         }
    //       })
    //     }
    //   }
    // })
  },
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
.navigator-hover {
    color:blue;
}
.other-navigator-hover {
    color:red;
}
</style>
