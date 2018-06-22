<template>
  <!-- <div class="page">
          <div class="weui-photos__files" id="uploaderFiles">
            <block  v-for="item in grids" :key="index">
          <dl class="weui-photos__file" style="text-align: center;" >
          <dt class="weui-photos__dt"> {{item.description}} </dt>
          <dd>
           <navigator url="/pages/photosdetails/photosdetails" class="weui-footer__link">
           <image class="weui-photos__img" :src="item.axtenal_url" mode="aspectFill" />       
           </navigator>
           </dd>
          </dl>
    
        </block>
      </div>
   </div> -->

   <div class="weui-flex">
        <div class="weui-flex__item">
          <block  v-for="item in grids" :key="index">
          <ul class="weui-photos__img">
            <li>
              <p style="width:80%;text-align: center; overflow: hidden;text-overflow:ellipsis;white-space: nowrap;padding-left:15px;">
                {{item.description}}
              </p>
              <navigator url="/pages/photosdetails/photosdetails" class="weui-footer__link">
                  <image  :src="item.axtenal_url" mode="aspectFill" style="horizontal-radius:15px;vertical-radius:15px"/>       
              </navigator>
            </li>
          </ul>
            </block> 
         
              
                
         
       
        </div>
        
      </div>

</template>

<script>
import base64 from "../../../static/images/base64";
import global from '../../global/index';
import wxParse from 'mpvue-wxparse'
export default {
  data() {
    return {
      //活动相册
      grids: [
        { src: "", description: "", axtenal_url: "" }, 
      ],
      communityId:0,
      axtenal_url:"",
      description:""
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
        communityId:1
     };
      global.HttpRequest(true, "/pub/wx/photo/getPhotos", false, "", param, "GET", false, function (res) {
         that.grids=res;
        
     });
    },
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
@import url("~mpvue-wxparse/src/wxParse.css");
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */
 .weui-photos__img{height:100px;width:49%;font-family:'Microsoft Yahei';font-size: 8px;
 float: left;overflow: hidden;text-overflow:ellipsis;white-space: nowrap;
 border:0px #ddd;padding-right:1.5px;padding-bottom: 10px;text-align: center}
  
  .weui-photos__dt{



  }
  /* .weui-photos__img {}
 .weui-footer__link {}
 .weui-photos__img{
  width:45%;
  margin: 5px;
  padding: 0 10px;
  text-align: center;
  background-color: #ebebeb;
  height: 2.3em;
  line-height: 2.3em;
  color: #cfcfcf;
} */
 
 /*内容超出后隐藏*/

/* 超出内容显示为省略号*/


</style>
