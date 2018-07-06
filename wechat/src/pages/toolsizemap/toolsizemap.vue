<template>
  <div class="page c-toolsizemap-page">
    <div class="page__bd">
      <div class="weui-article">
        <div class="weui-article__h1">男鞋</div>
        <div class="weui-article__section">
          <div class="weui-article__p">
            <kyimage :preview="true" type="article" folder="wx_home" src="size-male.jpg" mode="aspectFit" />
          </div>
        </div>
        <div class="weui-article__h1">女鞋</div>
        <div class="weui-article__section">
          <div class="weui-article__p">
            <kyimage :preview="true" type="article" folder="wx_home" src="size-female.jpg" mode="aspectFit" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import kyimage from '@/components/kyimage.vue'
  export default {
    data() {
      
      return {
        
      }
    },
    components: {
      kyimage
    },
    methods: {
    },
    created() {
    },
    onShareAppMessage(res) {
      return {
        title: "鞋码对照表",
        path: '/page/toolsizemap/toolsizemap'
      }
    }
  };
</script>

<style>
  .c-toolsizemap-page image{
    width: 100%;
  }
</style>
