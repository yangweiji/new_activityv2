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
          {url:'../../pages/toolbomamatch/toolbomamatch',name:'波马参赛资格',src:''},
          {url:'../../pages/toolmaf180/toolmaf180',name:'MAF180心率计算',src:''},
          {url:'../../pages/toolsizemap/toolsizemap',name:'鞋码对照',src:''}
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
