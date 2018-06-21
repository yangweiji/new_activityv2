<template>
  <image :class="{'weui-media-box__thumb': type=='thumb','banner': type=='banner'}" 
    :src="'http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/'+src"  />
</template>

<script>
export default {
  name: '',
  props: {
    src: {
      type: String,
      default: 'NzrSDNSBEP.png'
    },

    type: {
      type: String,
      default: ''
    },
  }
}
</script>

<style>

</style>
