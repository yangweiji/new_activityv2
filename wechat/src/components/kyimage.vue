<template>
  <image @click="previewClick" :class="{'weui-media-box__thumb': type=='thumb','banner': type=='banner', 'weui-article__img' : type=='article'}" 
    :src="url" :model="imageMode"  />
</template>

<script>
var _cache_urls = {}
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
    folder:{
      type:String
    },
    preview:{
      type:Boolean
    },
    defaultSrc:{
      type:String
    },
    mode:{
      type:String
    }
  },
  computed:{
    innerSrc(){
      return this.src || this.defaultSrc
    },
    url(){
      if(this.innerSrc){
        return this.$kyutil.downloadUrl(this.innerSrc, this.size, this.folder)
      } 
      return null
    },
    imageMode(){
      return this.mode || 'aspectFill'
    }
  },
  created(){
    if(this.preview && this.innerSrc){
      var url = this.$kyutil.downloadUrl(this.innerSrc, '', this.folder)
      var cacheUrls = this.currentPageUrls()
      if(cacheUrls.indexOf(url) < 0){
        cacheUrls.push(url)
      }
    }
  },
  methods:{
    currentPageUrls(){
      var currentPages = getCurrentPages()
      var currentPage = currentPages[currentPages.length - 1]
      var viewId =  currentPage.__wxWebviewId__ || currentPage.data.__webviewId__
      var id = "_cache_" + viewId
      if(!_cache_urls[id]){
        _cache_urls[id] = []
      }
      return _cache_urls[id]
    },
    previewClick(){
      if(this.preview){
        var current = this.$kyutil.downloadUrl(this.innerSrc, '', this.folder)
        var cacheUrls = this.currentPageUrls()
        wx.previewImage({
          current: current, 
          urls: cacheUrls
        })
      }
    }
  }
}
</script>

<style>

</style>
