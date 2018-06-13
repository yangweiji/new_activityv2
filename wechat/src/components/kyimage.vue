<template>
  <image class="weui-media-box__thumb" :src="'http://bjmlsxh.oss-cn-beijing.aliyuncs.com/activity/'+src"  />
</template>

<script>
export default {
  name: '',
  props: {
    src: {
      type: String,
      default: ''
    },
  }
}
</script>

<style>

</style>
