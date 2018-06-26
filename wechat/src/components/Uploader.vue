<template>
  <div class="weui-uploader">
    <div class="weui-uploader__bd">
      <div class="weui-uploader__files" id="uploaderFiles">
        <div v-for="(item ,index) in files" :key="index">
          <div class="weui-uploader__file">
            <kyimage class="weui-uploader__img" :src="item" mode="aspectFill" @click="predivImage(index)" />
            <div class="delete-icon" @click="deleteImg(index)" ></div>
          </div>
        </div>
      </div>
      <div class="weui-uploader__input-box">
        <div class="weui-uploader__input" @click="chooseImage"></div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    props: {
      value: {
        type: String
      }
    },
    data() {
      return {
        ossInfo: null
      }
    },
    computed:{
      files(){
        if(this.value){
          var files = this.value.split(',')
          return files
        }
        return []
      }
    },
    methods: {
      chooseImage(e) {
        let that = this;
        wx.chooseImage({
          count: 1, // 默认9
          sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
          sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
          success: function(res) {
            that.uploadToSSO()
          },
          fail: function() {
            console.log('fail');
          },
          complete: function() {
            console.log('commplete');
          }
        });
      },
      uploadToSSO(file) {
        var that = this
        function random_string(len) {
          len = len || 32;
          var chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
          var maxPos = chars.length;
          var pwd = '';
          for (i = 0; i < len; i++) {
            pwd += chars.charAt(Math.floor(Math.random() * maxPos));
          }
          return pwd;
        }
        function get_suffix(filename) {
          var pos = filename.lastIndexOf('.')
          suffix = ''
          if (pos != -1) {
            suffix = filename.substring(pos)
          }
          return suffix;
        }
        var startUpload = function(uploadFilePath) {
          var fileName = random_string(10) + get_suffix(uploadFilePath)
          wx.uploadFile({
            url: "http://oss.aliyuncs.com",
            filePath: uploadFilePath,
            name: fileName,
            formData: {
              'key': that.ossInfo.key + '/' + fileName,
              'policy': that.ossInfo.policy,
              'OSSAccessKeyId': that.ossInfo.accessid,
              'success_action_status': '200', //让服务端返回200,不然，默认会返回204
              'signature': that.ossInfo.signature
            },
            success(res) {
              var files = that.files
              files.push(fileName)
              this.$emit('input', files.join())
            }
          })
        }
        var now = Date.parse(new Date()) / 1000;
        if (!that.ossInfo || that.ossInfo.expire < now + 3) {
          this.$kyutil.HttpRequest(true, "/pub/file/policy", false, "", "", "GET", false, function(res) {
            that.ossInfo = res
            startUpload(file)
          })
        } else {
          startUpload(file)
        }
      },
      predivImage(index) {
        var fullPathFiles = []
        var files = this.files
        wx.previewImage({
          current: fullPathFiles[index], // 当前显示图片的http链接
          urls: fullPathFiles// 需要预览的图片http链接列表
        });
      },
      deleteImg(index) {
        var files = this.files
        files.splice(index, 1)
        this.$emit('input', files.join())
      }
    }
  }
</script>

<style scoped>
  .weui-uploader__file {
    position: relative;
  }
  .weui-uploader__bd {
    overflow: visible;
  }
  .delete-icon {
    position: absolute;
    width: 40rpx;
    height: 40rpx;
    background: #f43530;
    right: 0;
    top: -20rpx;
    border-radius: 40rpx;
    z-index: 5;
  }
  .delete-icon::before {
    content: '';
    width: 26rpx;
    height: 4rpx;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background: #fff;
  }
</style>
