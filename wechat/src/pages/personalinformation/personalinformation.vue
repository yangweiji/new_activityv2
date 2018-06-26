<template>
  <div class="page">
    <div class="page__hd">
      <div class="page__title" style="text-align:center">请完善个人信息</div>
    </div>
    <div class="page__bd" >
      <div class="weui-cells__title">昵称</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd" >
            <input class="weui-input"  v-model="users.displayname"  />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">邮箱</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" v-model="users.email" placeholder="请输入邮箱" />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">性别</div>
      <div class="weui-cells weui-cells_after-title">
        <radio-group @change="radioChange">
          <label class="weui-cell weui-check__label" v-for="item in radioItems1" :key="item.id">
            <radio class="weui-check" :value="item.value" :checked="item.checked" />
            <div class="weui-cell__bd">{{item.sex}}</div>
            <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="item.checked">
              <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
            </div>
          </label>
        </radio-group>
      </div>

      <div class="weui-cells__title">血型</div>
      <div class="weui-cells weui-cells_after-title">
        <radio-group @change="radioChange">
          <label class="weui-cell weui-check__label" v-for="item in radioItems2" :key="item.id">
            <radio class="weui-check" :value="item.value" :checked="item.checked" />
            <div class="weui-cell__bd">{{item.level}}</div>
            <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="item.checked">
              <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
            </div>
          </label>
        </radio-group>
      </div>

      <div class="weui-cells__title">T恤尺寸</div>
      <div class="weui-cells weui-cells_after-title">
        <radio-group @change="radioChange">
          <label class="weui-cell weui-check__label" v-for="item in radioItems3" :key="item.id">
            <radio class="weui-check" :value="item.value" :checked="item.checked" />
            <div class="weui-cell__bd">{{item.size}}</div>
            <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="item.checked">
              <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
            </div>
          </label>
        </radio-group>
      </div>

      <div class="weui-cells__title">工作单位</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入工作单位" />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">职业</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入职业" />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">紧急联系人姓名</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入紧急联系人姓名" />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">紧急联系人电话</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入紧急联系人电话" />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">是否党员</div>
      <div class="weui-cells weui-cells_after-title">
        <radio-group @change="radioChange">
          <label class="weui-cell weui-check__label" v-for="item in radioItems4" :key="index">
            <radio class="weui-check" :value="item.value" :checked="item.checked" />
            <div class="weui-cell__bd">{{item.party}}</div>
            <div class="weui-cell__ft weui-cell__ft_in-radio" v-if="item.checked">
              <icon class="weui-icon-radio" type="success_no_circle" size="16"></icon>
            </div>
          </label>
        </radio-group>
      </div>

      <div class="weui-cells__title">家庭地址</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入家庭地址" />
          </div>
        </div>
      </div>

      <div class="weui-cells__title">微信号</div>
      <div class="weui-cells weui-cells_after-title">
        <div class="weui-cell weui-cell_input">
          <div class="weui-cell__bd">
            <input class="weui-input" placeholder="请输入微信号" />
          </div>
        </div>
      </div>
      <button class="weui-btn" type="primary">提交</button>
    </div>
  </div>

</template>

<script>
import base64 from "../../../static/images/base64";
import global from "../../global/index";
import wxParse from "mpvue-wxparse";
export default {
  data() {
    return {
      //活动相册
      grids: [
        { id: "1", title: "冬季香山20公里长跑", count: 5 },
        { id: "2", title: "奥园接力跑", count: 6 },
        { id: "3", title: "北京马拉松比赛", count: 7 },
        { id: "4", title: "长跑运动会", count: 8 }
      ],
      checked: true,
      radioItems1: [
        { sex: "男", value: 0 },
        { sex: "女", value: 1, checked: true }
      ],
      radioItems2: [
        { level: "A", value: 0 },
        { level: "B", value: 1, checked: true },
        { level: "AB", value: 2 },
        { level: "O", value: 3 }
      ],
      radioItems3: [
        { size: "S", value: 0 },
        { size: "M", value: 1 },
        { size: "L", value: 2 },
        { size: "XL", value: 3, checked: true },
        { size: "XXL", value: 4 },
        { size: "XXXL", value: 5 }
      ],
      radioItems4: [
        { party: "群众", value: 0 },
        { party: "党员", value: 1, checked: true }
      ],
      users:{}
    };
  },
  computed: {},
  components: {},
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
          userId: wx.getStorageSync("user").id
      };
      this.$kyutil.get("/pub/wx/profile/getIntoPersonalInformation", param, "GET").then(res => {
        that.users=res;
        console.log(res);
      })
    },
    radioChange(e) {
      console.log("radio发生change事件，携带value值为：" + e.mp.detail.value);
      let radioItems = this.radioItems;
      for (let i = 0; i < radioItems.length; ++i) {
        radioItems[i].checked = radioItems[i].value === e.mp.detail.value;
      }
      this.radioItems = radioItems;
    }
  },
  created() {
    // console.log("photos created");
  },
  onShow() {
    //   console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    //   var that = this;
    //   that.articleId = this.$root.$mp.query.articleId;
    this.getData();
  }
};
</script>

<style scoped>
@import url("~mpvue-wxparse/src/wxParse.css");
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
