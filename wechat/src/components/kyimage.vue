<template>
  <image :class="{'weui-media-box__thumb': type=='thumb','banner': type=='banner'}" 
    :src="url"  />
</template>

<script>
export default {
  name: '',
  props: {
    src: {
      type: String,
      default: 'NzrSDNSBEP.png'
    },
    size: {
      type:String
    },
    type: {
      type: String,
      default: ''
    }
  },
  computed:{
    url(){
      if(this.src){
        return this.$kyutil.downloadUrl(this.src, this.size)
      } 
      return null
    }
  }
}
</script>

<style>

</style>
