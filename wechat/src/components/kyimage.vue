<template>
  <image @click="previewClick" :class="{'weui-media-box__thumb': type=='thumb','banner': type=='banner', 'weui-article__img' : type=='article'}" 
    :src="url" :model="imageMode"  />
</template>

<script>
var _cache_urls = []
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
      if(_cache_urls.indexOf(url) < 0){
        _cache_urls.push(url)
      }
    }
  },
  methods:{
    previewClick(){
      if(this.preview){
        var current = this.$kyutil.downloadUrl(this.innerSrc, '', this.folder)
        wx.previewImage({
          current: current, 
          urls: _cache_urls
        })
      }
    }
  }
}
</script>

<style>

</style>
