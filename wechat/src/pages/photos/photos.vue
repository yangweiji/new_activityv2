<template>
  <div class="page">
    <div class="page__bd">
      <div class="flex-row photos">

        <div class="flex-div-item photo-w" v-for="item in items" :key="index">

          <navigator :url="'/pages/photosdetails/photosdetails?photoId='+item.id+'&name='+item.description" class="photo">
            <image :src="item.axtenal_url" mode="aspectFill" class="photo-img" />
            <p class="photo-desc">
              {{item.description}}
            </p>
          </navigator>

        </div>

      </div>

    </div>
  </div>

</template>

<script>
import base64 from "../../../static/images/base64";

export default {
  data() {
    return {
      //活动相册
      items: [{ src: "", description: "", axtenal_url: "" }],
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
  computed: {},
  methods: {
    getData() {
      var that = this;
      var param = {
        communityId: that.community.id
      };
      this.$kyutil.HttpRequest(
        true,
        "/pub/wx/photo/getPhotos",
        false,
        "",
        param,
        "GET",
        false,
        function(res) {
          that.items = res;
        }
      );
    }
  },
  created() {
    console.log("photos created");
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

    // wx.setNavigationBarTitle({
    //   title: "活动相册"
    // });
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
}
.photo-img {
  width: 100%;
  height: 90px;
}
.photo-desc {
  font-size: 12px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 80%;
  text-align: center;
  margin-left: 10px;
  margin-top: 10px;
  margin-bottom: 10px;
}
</style>
