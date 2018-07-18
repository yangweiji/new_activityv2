<template>
  <div class="page">
    <div class="page__bd">
      <!-- banner -->
      <div>
        <navigator hover-class="navigator-hover">
            <span class="community-search">
            </span>
        </navigator>  
        <kyimage src="photo.jpg" folder="wx_home" type="banner" />
        <!-- <div class="c-bg"></div> -->
      </div>

      <div class="flex-row photos">

        <div class="flex-div-item photo-w" v-for="item in items" :key="item.id">
          <navigator :url="'/pages/photosdetails/photosdetails?activityId='+item.activity_id" class="photo">
            <image :src="item.axtenal_url" mode="aspectFill" class="photo-img" />

          </navigator>
          <div>
            <p>
              <span class="photo-img-l">
                <i class="fa fa-picture-o"></i> {{item.pictureCount}} 张
              </span>
              <span class="photo-img-r">
                <i class="fa fa-eye"></i> {{item.browse_count}} 次
              </span>
            </p>
            <p class="photo-desc">
              {{item.description}}
            </p>
            
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
      //活动相册
      items: [{ src: "", description: "", axtenal_url: "", browse_count: "" }],
      //默认的团体组织
      community: {
        id: 1, //默认的组织团体ID
        name: "北京市马拉松协会",
        background: "NzrSDNSBEP.png"
      },
      axtenal_url: "",
      description: ""
    };
  },
  components:{
    kyimage
  },
  computed: {
  },
  methods: {
    getData() {
      var that = this;
      var param = {
        communityId: that.community.id
      };
      this.$kyutil.get("/pub/wx/photo/getPhotos", param).then(res => {
        console.log(res);
        that.items = res;
      });
    }
  },
  created() {
  },
  onShow() {
    //接受参数
    if (this.$store.state.community) {
      this.community = this.$store.state.community;
      //设置标题
      wx.setNavigationBarTitle({
        title: this.community.name
      });
    }
    this.getData();
  }
};
</script>

<style scoped>
/*!
 * WeUI v1.1.1 (https://github.com/weui/weui-wxss)
 * Copyright 2017 Tencent, Inc.
 * Licensed under the MIT license
 */
.weui-photos__img {
  height: 100px;
  width: 49%;
  font-family: "Microsoft Yahei";
  font-size: 8px;
  float: left;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  border: 0px #ddd;
  padding-right: 1.5px;
  padding-bottom: 10px;
  text-align: center;
}
.photos {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
}
.photo-w {
  width: 50%;
}
.photo {
  border: 1px solid #ebebeb;
  margin: 0 5px 5px 5px;
  height: 90px;
}
.photo-img {
  width: 100%;
  height: 90px;
}
.photo-img-l {
  color:#999;
  font-size: 12px;
  float:left;
  margin-left:10px;
}
.photo-img-r {
  color:#999;
  font-size: 12px;
  float:right;
  margin-right:10px;
}
.photo-icon {
  width: 10px;
  height: 10px;
  margin-right: 5px;
}
.photo-desc {
  font-size: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 90%;
  text-align: left;
  margin-left: 10px;
  margin-top: 10px;
  margin-bottom: 10px;
  float: left;
}
</style>
