<template>
  <div class="page">
    <div class="page__bd">
      <uploader :files="files"></uploader>
    </div>
    <div v-if="item && item.user_id == userId" class="c-footer-btns weui-flex c-border-top" :class="{'fix-iphonex': isIpx}">
      <div v-if="item.id > 0" :disabled="processing" @click="remove()" class="weui-flex__item c-bg-default">
        删除
      </div>
      <div :disabled="processing" @click="save()" class="weui-flex__item c-bg-primary">
        提交
      </div>
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
        isIpx: false,
        activityUserId: null,
        loaded: false,
        item: null,
        recordId: null,
        userId: null,
        files: []
      };
    },
    computed: {},
    components: {
      wxParse
    },
    methods: {
      //取得文章信息
      getData() {
        var that = this;
        if (!that.recordId && that.activityUserId) {
          that.item = {
            activity_user_id: activityUserId
          }
          return
        }
        var param = {
          id: that.recordId
        };
        this.$kyutil.HttpRequest(true, "/pub/wx/activityuserrecord/get", false, "", param, "GET", false, function(res) {
          that.item = res;
          that.loaded = true
        });
      },
    },
    created() {
      this.isIpx = this.$kyutil.data.isIpx
    },
    onShow() {
      var that = this;
      that.loaded = false;
      that.recordId =
        this.$root.$mp.query.id || this.$root.$mp.query.scene;
      that.activityUserId = this.$root.$mp.query.uid
      this.$kyutil.CheckUserValidation();
      var user = this.$kyutil.GetUser();
      if (user) {
        this.userId = user.id;
        this.getData();
      }
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
  .pet_zlnr_user_l {
    width: 40px;
    height: 40px;
    overflow: hidden;
    border-radius: 50%;
    float: left;
    position: relative;
  }
</style>
