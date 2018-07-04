<template>
  <div class="page">
      <div class="weui-grids">
        <block v-for="item in navigationData" :key="item.name">
          <navigator :url="item.url" class="weui-grid" hover-class="weui-grid_active">
            <image class="weui-grid__icon" :src="item.src" />
            <div class="weui-grid__label">{{item.name}}</div>
          </navigator>
        </block>
      </div>
  </div>
</template>

<script>

export default {
  data() {
    return {
          navigationData:[
          {url:'../../pages/toolcaculate/toolcaculate',name:'配速计算',src:''},
          {url:'',name:'配速转换',src:''},
          {url:'',name:'完整时间计算',src:''},
          {url:'',name:'马拉松训练配速',src:''},
          {url:'',name:'比赛成绩预测',src:''},
          {url:'',name:'跑力值计算',src:''},
      ]
    }
  },

  components: {
  },

  methods: {
    getData(){

    }
  },

  created() {
    // this.getData()
  }
}
</script>

<style scoped>

</style>
