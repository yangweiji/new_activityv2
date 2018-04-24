<template>
  <div class="page">

 
     <div>
      <span style="position:absolute;right:0;margin-top:10px;margin-right:25px;">切换团体</span>
      <image :src=url style="height:200px; width:100%;"> </image>
     </div>

     <div class="weui-grids" style=" border-top:0px; border-left:0px; background-color:#ffffff;">
        <block v-for="item in grids":key="index">
          <navigator url="" class="weui-grid" hover-class="weui-grid_active" style="width:25%;border-right:0px;border-bottom:0px">
           <image class="weui-grid__icon":src='icon60'/>
           <div class="weui-grid_label">{{item.name}}</div>
          </navigator>
        </block>
     </div>

      <div class="weui-navbar" style="top:auto;">
         <block v-for="(item,index) in choice" :key="index">
           <div :id="index" :class="{'weui-bar__item_on' :activeIndex ==index}" class="weui-navbar__item"  @click="tabClick(item.id)">
             <div class="weui-navbar__title" v-if="item.id!='b9'">{{item.matter}}</div>
             <div v-if="item.id=='b9'" class="weui-navbar-title">
               <picker @change="bindPickerChange" :value="index" :range="ranges">
                 <span class="wx-btn" type="default">
                   {{ces}}
                 </span>
               </picker>
             </div>
           </div>
         </block>
        <div class="weui-navbar__slider" :class="navbarSliderClass" style="width:64px;"></div>
      </div>

      <div class="weui-tab__panel">
        <!--<div class="weui-tab__content" :hidden="activeIndex != b5"></div>
        <div class="weui-tab__content" :hidden="activeIndex != b13"></div>
        <div class="weui-tab__content" :hidden="activeIndex != b2"></div>
        <div class="weui-tab__content" :hidden="activeIndex != b10"></div>
        <div class="weui-tab__content" :hidden="activeIndex != ''"></div>-->
      </div>
  
    <!-- <div>
     <ul>
       <li v-for="item in num":key="item.id" style="height:100px;margin-top:3px;background:#ffffff">
         <div style="float:left;width:60%">
           <p style="margin-left:25px;">用户头像{{item.start_time}}</p>
           <p style="margin-left:25px;">{{item.title}}</p>
           <p style="margin-left:25px;"> <img class="am_img animated c-img"alt=""/>{{item.favorite_count}}评论：{{item.attend_count}}</p>
         </div>
         
         <div style="float:right;width:40%;">
           <span style="float:right;margin-right:25px;color:#ff8c00">活动</span>
           <image v-bind:src=item.avatar mode="aspectFit" style="width:auto;height:100px"></image>
         </div>
       </li>
     </ul>
    </div>-->
   
    <div>
      <div class="weui-media-box weui-media-box_appmsg" hover-class="weui-cell_active" v-for="item in num":key="item.id" @click="checkdetails(item.id)">    
           <div class="weui-media-box__bd weui-media-box__bd_in-appmsg">
              <div class="weui-media-box__title">用户头像  {{item.start_time}}</div>
              <div class="weui-media-box__desc">{{item.title}}</div>
              <div class="weui-media-box__desc">喜欢：{{item.favorite_count}}评论：{{item.attend_count}}</div>
           </div>
            <div class="weui-media-box__hd weui-media-box__hd_in-appmsg" style="width:90px;height">
             <image class="weui-media-box__thumb" :src=item.avatar />
           </div>
      </div>
    </div>

  </div>


</template>

<script>
 import base64 from '../../../static/images/base64';
export default {
  
  data(){
    return {
       url:'../../../static/images/a2.jpg',
       icon60: base64.icon60,
       items:[],
       eid:'',
       num:[],
       ces:'',
      grids:[
        {src:'',name:'通知公告',url:''},
        {src:'',name:'赛事新闻',url:''},
        {src:'',name:'运动指南',url:''},
        {src:'',name:'活动相册',url:''}
      ],
      choice:[
        {id:'b5',matter:'训练'},
        {id:'b13',matter:'装备'},
        {id:'b12',matter:'福利'},
        {id:'b10,b11',matter:'赛事'},
        {id:'b9',matter:'其他'}],
      activeIndex:'',
      ranges:[
       '全部',
       '徒步',
       '越野',
       '聚餐',
       '骑行',
       '会议',
       '招募',
       '讲座',
       '国内赛事',
       '国际赛事'
        ],
      index:0,
    
    }
  },
  computed:{
    navbarSliderClass(){
      if(this.activeIndex =='b5'){
       return 'weui-navbar__slider_0'
      }
      if(this.activeIndex =='b13'){
       return 'weui-navbar__slider_1'
      }
      if(this.activeIndex =='b12'){
       return 'weui-navbar__slider_2'
      }
      if(this.activeIndex =='b10,b11'){
       return 'weui-navbar__slider_3'
      }
      if(this.activeIndex =='b9'){
       return 'weui-navbar__slider_4'
      }
    }
  },
  components: {
    
  },

  methods: {
     
    getData(){
      var that = this
      wx.request({
        url: 'https://a.9kylin.cn/pub/wx/activity/search', //仅为示例，并非真实的接口地址
        data: {
          s: '',
          t: that.activeIndex
        },
        header: {
            'content-type': 'application/json' // 默认值
        },
        success: function(res) {
         that.items= res.data;
         that.num=that.items[0];
          console.log(res.data)
          console.log(this.num)
        },fail:function(error){
          console.log(error)
        }
      })
    },
     tabClick(matter){
       console.log(matter)
       this.activeIndex = matter;
       this.getData();
     },
       bindPickerChange(e) {
         
         console.log(e.mp.detail.value);
         this.index = e.mp.detail.value;
        
      console.log('选中的值为：' + this.ranges[e.mp.detail.value]);
      this.ces=this.ranges[e.mp.detail.value];
      if(this.ces=='全部'){
         this.activeIndex ='0';
      }else if(this.ces=='徒步'){
         this.activeIndex ='b1';
      }else if(this.ces=='越野'){
        this.activeIndex ='b2';
      }else if(this.ces=='聚餐'){
        this.activeIndex ='b3';
      }else if(this.ces=='骑行'){
        this.activeIndex ='b4';
      }else if(this.ces=='会议'){
        this.activeIndex ='b6';
      }else if(this.ces=='招募'){
        this.activeIndex ='b7';
      }else if(this.ces=='讲座'){
        this.activeIndex ='b8';
      }else if(this.ces=='国内赛事'){
        this.activeIndex ='b10';
      }else if(this.ces=='国际赛事'){
        this.activeIndex ='b11';
      }
      this.getData();
    },
      checkdetails(eid){
         var that = this;
         that.eid=eid;
         wx.navigateTo({
          url:'../../pages/details/details?id='+that.eid
         })
         success:{
            console.log("调用成功"+eid)
          } 
        
     }   
  },

  created() {
    this.getData()  
    this.ces=this.ranges[this.index];
    
  }



}
</script>

<style scoped>
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */

.weui-flex {
  -webkit-box-align: center;
  -webkit-align-items: center;
  align-items: center;
}
.class-a{
  background-color:#ff7f00;
}

.placeholder {
  margin: 0.7em;
  padding: 0 10px;
  text-align: center;
  height: 2.3em;
  line-height: 2.3em;
  color: #000000;
}

.weui-cells {
  margin-top: 0;
  opacity: 0;
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  -webkit-transition: 0.3s;
  transition: 0.3s;
}

.weui-cells:after,
.weui-cells:before {
  display: none;
}

.weui-cells_show {
  opacity: 1;
  -webkit-transform: translateY(0);
  transform: translateY(0);
}

.weui-cell:before {
  right: 15px;
}

.kind-list__item {
  margin: 10px 0;
  background-color: #fff;
  border-radius: 2px;
  overflow: hidden;
}

.kind-list__item:first-child {
  margin-top: 0;
}

.kind-list__img {
  width: 30px;
  height: 30px;
}

.kind-list__item-hd {
  padding: 20px;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
}

.kind-list__item-hd_show {
  opacity: 0.4;
}

.kind-list__item-bd {
  height: 0;
  overflow: hidden;
}

.kind-list__item-bd_show {
  height: auto;
}
.weui-tab__content {
  padding-top: 70px;
  text-align: center;
}

.weui-navbar__slider_0 {
  left: 29rpx;
  transform: translateX(0);
  width:64px;
}
.weui-navbar__slider_1 {
  left: 29rpx;
  transform: translateX(150rpx);
  width:64px;
}
.weui-navbar__slider_2 {
  left:29rpx;
  transform: translateX(300rpx);
  width:64px;
}
.weui-navbar__slider_3 {
  left:29rpx;
  transform: translateX(450rpx);
  width:64px;
}
.weui-navbar__slider_4 {
  left:29rpx;
  transform: translateX(600rpx);
  width:64px;
}
.wx-btn {
  font-size:15px;
  line-height:normal;
  border:none;
}
</style>
