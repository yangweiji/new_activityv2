<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title">燃宁running</div>
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

      <div class="weui-cells__title">
        欢迎登录
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
                v-if="validateUserName" :class="{'vcode-btn-disabled': !canGetVerCode}" style="font-size:12px;">获取验证码
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

        <!-- <div class="weui-cell weui-cell_input">
          <div class="weui-cell__hd">
            <div class="weui-label">密码</div>
          </div>
          <div class="weui-cell__bd">
            <input class="weui-input" password type="text" placeholder="请输入字符和数字密码" v-model="password" />
          </div>
        </div> -->
        
      </div>

      <!-- <checkbox-group @click="bindAgreeChange">
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
      </checkbox-group> -->

      <!-- <div class="weui-cells__tips">底部说明文字底部说明文字</div> -->
      <div class="weui-btn-area">
        <!-- <button class="weui-btn" type="primary" @click="bindLogin" :disabled="disabled">确定</button> -->
        <!-- 需要使用 button 来授权登录 -->
        <button class="weui-btn" type="primary" open-type="getUserInfo" @getuserinfo="bindGetUserInfo" v-if="canIUse" :disabled="disabled">授权登录</button>
        <view v-else>请升级微信版本</view>
        <!-- <button class="weui-btn" type="warn" @click="register">注册新用户</button> -->
        <div style="text-align:center;margin-top:10px;">
          <navigator url="../../pages/index/index" open-type="switchTab" hover-class="navigator-hover" class="weui-agree__link">回到首页</navigator>
        </div>
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
        this.$kyutil.get("/pub/wx/vercode/getVerCode?mobile=" + that.username + "&templateId=3").then(res=>{
          if (res.code != 200) {
            console.log("获取短信验证码出错！");
            return;
          }
          
          that.isError = false;
          that.infoMessage = "短信验证码已发送，10分钟内有效！";
          that.showTopTipsFun();

          //测试环境下，直接显示出验证码
          //that.vercode = res.message;
          
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
    bindGetUserInfo: function(e) {
      console.log("bindGetUserInfo: ", e)
      //验证用户名和密码
      var that = this;
      var param = {};
      if (e.mp.detail.userInfo) {

        wx.login({
            success: res => {
                // 发送 res.code 到后台换取 openId, sessionKey, unionId
                var errMsg = res.errMsg;
                if (errMsg != "login:ok") {
                    console.log("错误提示", "出错了，请稍后再试试...")
                } else {
                    var code = res.code;
                     that.$kyutil.get("/pub/wx/auth/login", { "code": code }).then(res=>{
                        console.log("sessionInfo: ", res)
                        if (res.code == 200) {
                            wx.setStorageSync("sessionInfo", res)
                            //允许授权，获取用户敏感信息数据
                            param = {
                              sessionKey: res.sessionKey,
                              encryptedData: e.mp.detail.encryptedData,
                              ivStr: e.mp.detail.iv,
                            }
                            //取得小程序用户信息
                            that.$kyutil.get("/pub/wx/auth/getMiniAppUserInfo", param).then(res=>{
                              console.log("getMiniAppUserInfo: ", res);
                              if (res.code == 200) {
                                //获取openid
                                param = {
                                  username: that.username,
                                  password: that.password,
                                  vercode: that.vercode,
                                  openId: res.openid,
                                  unionId: res.unionId,
                                  nickName: res.nickName,
                                  avatarUrl: res.avatarUrl,
                                  gender: res.gender
                                }
                                //用户登录:如果系统没有用户信息则直接创建
                                that.$kyutil.post("/pub/wx/auth/userLogin", param).then(res => {
                                  console.log("userLogin: " + res)
                                  if (res.code == 200) {
                                    //取得用户信息
                                    that.$kyutil.get( "/pub/wx/auth/getUserInfo",{ "openid": param.openId, "unionId": param.unionId }).then(res => {
                                        // console.log("user: ", res)
                                        if (res.user) {
                                            //将user存储于storage
                                            wx.setStorageSync("user", res.user)
                                            console.log("storage user->", res.user)

                                            if (res.community) {
                                              that.$store.state.community = res.community
                                            }

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
                    });
                }
            }
        });
        
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
