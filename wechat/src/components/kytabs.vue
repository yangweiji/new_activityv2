<template>
  <div @touchmove="touchmove" class="c-kytabs weui-navbar" style="top:auto;">
            <block v-for="item in tabs" :key="item.id">
              <div :class="{'weui-bar__item_on' :value==item.id}" class="weui-navbar__item"  
                @click="tabClick(item)">
                <div :style="[theTabWidth]" class="weui-navbar__title">
                  <div v-if="item.number" class="weui-badge">{{item.number}}</div>
                  {{item.name}}
                </div>
              </div>
            </block>
          <div class="weui-navbar__slider" style="left:0px;" :style="[navbarSliderClass]"></div>
        </div>
</template>

<script>
export default {
  name: '',
  props: ['tabs', 'value', 'tabWidth'],
  data(){
    return {
    }
  },
  computed:{
    navbarSliderClass() {
      var index = 0
      for(var i = 0; i < this.tabs.length; i++){
        if(this.tabs[i].id == this.value){
          index = i
          break
        }
      }
      return "transform: translateX("+ index * (this.tabWidth || 200) + "rpx);" + this.theTabWidth
    },
    theTabWidth(){
      if(this.tabWidth){
        return "width:" + this.tabWidth + "rpx;"
      }
      return "width:200rpx;"
    }
  },
  methods:{
    tabClick(item){
      this.$emit('input', item.id)
    }
  }
}
</script>

<style>

</style>
