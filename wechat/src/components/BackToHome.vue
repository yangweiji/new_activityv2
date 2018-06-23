<template>
  <div class="c-back-to-home" @click="backToHome()">
    首页
  </div>
</template>

<script>
export default {
  name: '',
  props: {

  },
  data(){
    return {
      show:false
    }
  },
  methods:{

  },
  onShow(){

  }
}
</script>

<style>
  .c-back-to-home {
    position: flex;
    top:0;
    left:0;
    background-color: #F37B1D66;
    border-radius: 50%;
    display: block;
    padding: 5px;
  }
</style>
