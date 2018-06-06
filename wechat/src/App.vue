<script>
export default {
  data(){
    return {
      sessionInfo : null
    };
  },
  created() {
    // 调用API从本地缓存中获取数据
    const logs = wx.getStorageSync('logs') || []
    logs.unshift(Date.now())
    wx.setStorageSync('logs', logs)

    console.log('app created and cache logs by setStorageSync')
    this.login()
  },
  methods: {
    login() {
      var that = this

      if (wx.getStorageSync("LoginSessionKey")) return;

      wx.login({
        success: res => {
          // 发送 res.code 到后台换取 openId, sessionKey, unionId  
          var errMsg = res.errMsg;  
          if (errMsg != "login:ok") {  
            that.showHint("错误提示","出错了，请稍后再试试...")  
          } else {  
            var code = res.code;
            wx.request({
              url: 'https://a.9kylin.cn/pub/wx/auth/getSessionInfo',
              data: {  
                code: code
              },  
              header: {  
                'content-type': 'application/json' // 默认值  
              },  
              success: function(res) {
                console.log(res)
                that.sessionInfo = res.data;
                wx.setStorageSync("sessionInfo", that.sessionInfo)
              },
              fail: function(error) {
                console.log(error);
              }
            })
          }
        }
      });
    }
  }
}
</script>

<style>
.container {
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  padding: 200rpx 0;
  box-sizing: border-box;
}
::-webkit-scrollbar {
  width: 0;
  height: 0;
}
/* this rule will be remove */
* {
  transition: width 2s;
  -moz-transition: width 2s;
  -webkit-transition: width 2s;
  -o-transition: width 2s;
}
</style>
