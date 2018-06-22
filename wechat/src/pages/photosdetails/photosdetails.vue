<template>
  <div class="page">
          <div class="weui-photosdetails__files" id="uploaderFiles">
        <block  v-for="item in details" :key="index"  >     
          
          <image class="weui-photosdetails__img"  @click="predivImage" :src="item.picture" mode="aspectFill" />   
               
        </block>
      </div>
   </div>

</template>

<script>
import base64 from "../../../static/images/base64";
import global from '../../global/index'
import wxParse from 'mpvue-wxparse';
export default {
  data() {
    return {
      //活动相册
      details: [
        {picture:""}, 
      ],
      activity_photo_id:0,
      picture:"",
      pictures:[]
    };
  },
  computed: {
  },
  components: {
    wxParse
  },
  methods: {
    //取得文章信息
    getData() {
      var that = this;
      var param = {
        photoId:3
     };
      global.HttpRequest(true, "/pub/wx/photo/getPictures", false, "", param, "GET", false, function (res) {       
        
          that.details=res;
          console.log(res);
          for(var i=0;i<res.length;i++)
          {
          that.pictures.push(res[i].picture);
          }
          // that.pictures=res
         console.log(that.pictures);
      });
     
    },
    predivImage(e) {
      wx.previewImage({
        current: e.currentTarget.picture, // 当前显示图片的http链接
        urls: this.pictures // 需要预览的图片http链接列表
      })
    }
  },
  created() {
    console.log("photos created");
  },
  onShow () {
    // console.log('小程序触发的 onshow, 获取参数: '+ this.$root.$mp.query);
    // var that = this;
    // that.articleId = this.$root.$mp.query.articleId;
    this.getData();
  }
};
</script>

<style scoped>
/* @import url("~mpvue-wxparse/src/wxParse.css"); */
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */
 .weui-photosdetails__img {height:100px;width:48%;padding-right: 3.5px;padding-left: 3.5px;}
</style>
