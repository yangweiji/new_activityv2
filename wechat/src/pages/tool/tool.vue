<template>
  <div class="page">
      <div class="weui-grids">
        <block v-for="item in NavigationData" :key="item.id">
          <navigator url="" class="weui-grid" hover-class="weui-grid_active">
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
          NavigationData:[
          {url:'',name:'配速计算',src:'/static/images/clock_image.png'},
          {url:'',name:'MAF180心率计算',src:'/static/images/heart_image.png'},
          {url:'',name:'波马参赛资格',src:'/static/images/horse_image.png'},
          {url:'',name:'鞋码对照',src:'/static/images/shoes_image.png'},
      ]
    }
  },

  components: {
  },

  methods: {
    getData(){
      wx.request({
        url: 'https://a.9kylin.cn', //仅为示例，并非真实的接口地址
        data: {
          x: '' ,
          y: ''
        },
        header: {
            'content-type': 'application/json' // 默认值
        },
        success: function(res) {
          console.log(res.data)
        },fail:function(error){
          console.log(error)
        }
      })
    }
  },

  created() {
    // this.getData()
  }
}
</script>

<style scoped>
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */

.weui-flex {
  -webkit-box-align: center;
  -webkit-align-items: center;
  align-items: center;
}

.weui-cells {
  margin-top: 0;
  opacity: 0;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  -webkit-transition: 0.3s;
  transition: 0.3s;
}

.weui-cells:after,
.weui-cells:before {
  display: none;
}

.weui-cells_show {
  opacity: 1;
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.weui-cell:before {
  right: 15px;
}

.kind-list__item {
  margin: 10px 0;
  background-color: #fff;
  border-radius: 2px;
  overflow: hidden;
}

.kind-list__item:first-child {
  margin-top: 0;
}

.kind-list__img {
  width: 30px;
  height: 30px;
}

.kind-list__item-hd {
  padding: 20px;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
}

.kind-list__item-hd_show {
  opacity: 0.4;
}

.kind-list__item-bd {
  height: 0;
  overflow: hidden;
}

.kind-list__item-bd_show {
  height: auto;
}
</style>
