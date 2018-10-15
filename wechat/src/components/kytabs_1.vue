<template>
  <scroll-view scroll-x="true" class="c-nav" :scroll-left="navScrollLeft" scroll-with-animation="true">
    <block v-for="(navItem, index) in tabs" :key="index">
      <view class="c-nav-item weui-navbar__title" :class="currentTab == index ?'active':''" :data-current="index" @click="switchNav($event, navItem)" :style="[theTabWidth]">
        <view v-if="navItem.number" class="weui-badge">{{navItem.number}}</view>
        {{navItem.name}}
      </view>
    </block>
  </scroll-view>

</template>

<script>
export default {
  name: "",
  props: ["tabs", "value", "tabWidth", "tabCount", "i"],
  data() {
    return {
      currentTab: 0,
      navScrollLeft: 0,
      pixelRatio: null,
      windowHeight: null,
      windowWidth: null,
    };
  },
  computed: {
    navbarSliderClass() {
      var index = 0;
      for (var i = 0; i < this.tabs.length; i++) {
        if (this.tabs[i].id == this.value) {
          index = i;
          break;
        }
      }
      return (
        "transform: translateX(" +
        index * (this.tabWidth || 200) +
        "rpx);" +
        this.theTabWidth
      );
    },
    theTabWidth() {
      if (this.tabWidth) {
        return "width:" + this.tabWidth + "rpx;";
      }
      return "width:200rpx;";
    }
  },
  created() {
    var that = this;
    wx.getSystemInfo({
      success: res => {
        (that.pixelRatio = res.pixelRatio),
          (that.windowHeight = res.windowHeight),
          (that.windowWidth = res.windowWidth);
      }
    });

    var that = this;
    //定义参数索引
    var index = 0;
    for (var i = 0; i < this.tabs.length; i++) {
      if (this.tabs[i].id == this.value) {
        index = i;
        break;
      }
    }
    that.currentTab = index;
  },
  updated() {
    var that = this;
    //定义参数索引
    var index = 0;
    for (var i = 0; i < this.tabs.length; i++) {
      if (this.tabs[i].id == this.value) {
        index = i;
        break;
      }
    }
    that.currentTab = index;
  },
  methods: {
    switchNav(event, item) {
      var cur = event.mp.currentTarget.dataset.current;
      //每个tab选项宽度占1/tabCount
      var singleNavWidth = this.windowWidth / this.tabCount;
      //tab选项居中
      this.navScrollLeft = (cur - 2) * singleNavWidth;
      if (this.currentTab == cur) {
        return false;
      } else {
        this.currentTab = cur;
      }

      this.$emit("input", item.id);
    },
  }
};
</script>

<style>
</style>
