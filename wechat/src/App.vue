<script>
export default {
  created() {
    // 调用API从本地缓存中获取数据
    const logs = wx.getStorageSync("logs") || [];
    logs.unshift(Date.now());
    wx.setStorageSync("logs", logs);
    var that = this;
    wx.getSystemInfo({
      success: function(res) {
        let model = res.model.substring(0, res.model.indexOf("X")) + "X";
        if (model == "iPhone X") {
          that.$kyutil.data.isIpx = true;
        }
      }
    });
    //用户登录小程序
    this.$kyutil.Login();
    console.log("App created");
  },
  onLaunch: function(options) {},
  onShow: function(options) {
    console.log("Do something when show.");
  },
  methods: {}
};
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
