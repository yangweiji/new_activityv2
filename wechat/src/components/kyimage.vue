<template>
  <image :class="{'weui-media-box__thumb': type=='thumb','banner': type=='banner'}" 
    :src="fileUrl"  />
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
    },
  },
  computed:{
    fileUrl(){
      return this.$kyutil.downloadUrl(src, size)
    }
  }
}
</script>

<style>

</style>
