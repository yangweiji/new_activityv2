<template>
  <div class="page">
    <div class="page__bd">
      <div class="weui-search-bar">
        <div class="weui-search-bar__form">
          <div class="weui-search-bar__box">
            <icon class="weui-icon-search_in-box" type="search" size="14"></icon>
            <input type="text" class="weui-search-bar__input" placeholder="搜索" v-model="inputVal" :focus="inputShowed" @input="inputTyping" />
            <div class="weui-icon-clear" v-if="inputVal.length > 0" @click="clearInput">
              <icon type="clear" size="14"></icon>
            </div>
          </div>
          <label class="weui-search-bar__label" :hidden="inputShowed" @click="showInput">
            <icon class="weui-icon-search" type="search" size="14"></icon>
            <div class="weui-search-bar__text">搜索</div>
          </label>
        </div>
        <div class="weui-search-bar__cancel-btn" :hidden="!inputShowed" @click="hideInput">取消</div>
      </div>
      <div class="searchbar-result content" v-if="!inputVal.length && !clickSearched">
        <div class="content_top">
          <div class="content_hd_top">
            分类：
          </div>
          <div class="content_cell_top">
            <div v-for="item in types" :key="item.id" class="weui-grid" hover-class="weui-grid_active">
              <div @click="inputtag(item.id)" :class="{'active-class':tag==item.id}" class="weui-grid__label">{{item.name}}</div>
            </div>
          </div>
        </div>
        <div class="content_bottom">
          <div class="content_hd_bottom">
            时间：
          </div>
          <div class="content_cell_bottom">
            <div v-for="item in times" :key="item.id" class="weui-grid" hover-class="weui-grid_active">
              <div @click="inputtimes(item.id)" :class="{'active-class':time==item.id}"   class="weui-grid__label">{{item.date}}</div>
            </div>
          </div>
        </div>

      </div>
      <div class="weui-cells searchbar-result" v-if="inputVal.length > 0 || clickSearched">
        <activity :item="item" v-for="item in items" :key="item.id"></activity>
        <div class="userinfo-name" v-if="items && items.length == 0">还没有相关的活动</div>
      </div>
    </div>

  </div>
</template>
<script>
import activity from "@/components/activity.vue";
export default {
  data() {
    return {
      inputShowed: false,
      clickSearched: false,
      items: [],
      inputVal: "",
      tag: "",    
      time: "",
      types: [
        {
          id: "b5",
          name: "训练"
        },
        {
          id: "b13",
          name: "装备"
        },
        {
          id: "b12",
          name: "福利"
        },
        {
          id: "b1",
          name: "徒步"
        },
        {
          id: "b2",
          name: "越野"
        },
        {
          id: "b3",
          name: "聚餐"
        },
        {
          id: "b4",
          name: "骑行"
        },
        {
          id: "b6",
          name: "会议"
        },
        {
          id: "b7",
          name: "招募"
        },
        {
          id: "b8",
          name: "讲座"
        },
        {
          id: "b10",
          name: "国内赛事"
        },
        {
          id: "b11",
          name: "国际赛事"
        }
      ],
      times: [
        { date: "不限", id: "0" },
        { date: "今天", id: "d" },
        { date: "近一周", id: "w" },
        { date: "近一月", id: "m" },
        { date: "周末", id: "z" }
      ]
    };
  },
  computed: {},
  components: {
    activity
  },
  methods: {
    search(data) {
      var that = this;
      that.items = null;
      var param = {
        searchText: this.inputVal,
        time: this.time,
        tag: this.tag
      };
      this.$kyutil.get("pub/wx/search/activities", param).then(res => {
        console.log(res);
        that.items = res;
      });
    },
    showInput() {
      this.inputShowed = true;
    },
    hideInput() {
      this.inputVal = '';
      this.inputShowed = false
      this.clickSearched = false
    },
    clearInput() {
      this.inputVal = '';
    },

    inputTyping(e) {
      this.inputVal = e.mp.detail.value
      this.search();
    },
    inputtimes(time) {
      if(this.time==time)
      {
       this.time="";
       return;
      }
      this.clickSearched = true
      this.inputShowed = true
      this.time = time;
      this.search();
    },
    inputtag(type) {
      if(this.tag==type)
      {
      this.tag="";
      return;
      }
      this.clickSearched = true
      this.inputShowed = true
      this.tag = type;
      this.search();
    }
  },
  created() {},
  onShow() {
    this.inputShowed = false;
    this.clickSearched = false;
    this.items = [];
    this.inputVal = "";
    this.tag = "";
    this.time = "";
  }
};
</script>

<style scoped>
.content_top {
  height: 50%;
  width: 100%;
}
.content_hd_top {
  height: 20px;
  width: 100%;
  margin-top: 20px;
  margin-left: 10px;
}
.content_cell_top {
  margin-top: 20px;
}
.weui-grid {
  padding: 4px;
  margin-top: 5px;
  font-family: "Times New Roman", Georgia, Serif;
  font-size: 12px;
  color: #bfbfbf;
}
.weui-grid__label {
  margin-top: 2px;
}
.content_bottom {
  height: 20%;
  width: 100%;
  margin-top: 30px;
}
.content_hd_bottom {
  height: 40px;
  width: 100%;
  margin-top: 20px;
  margin-left: 10px;
  float: left;

}
.active-class{
  color: #f37b1d;
}
</style>