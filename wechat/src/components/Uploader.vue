<template>
<div class="weui-uploader">
              <div class="weui-uploader__hd">
                <div class="weui-uploader__title">图片上传</div>
                <div class="weui-uploader__info">{{files.length}}/2</div>
              </div>
              <div class="weui-uploader__bd">
                <div class="weui-uploader__files" id="uploaderFiles">
                  <div v-for="(item ,index) in files" :key="index">
                    <div class="weui-uploader__file">
                      <image class="weui-uploader__img" :src="item" mode="aspectFill" @click="predivImage" :id="item" />
                      <div class="delete-icon" @click="deleteImg" :id="item"></div>
                    </div>
                  </div>
                  <div class="weui-uploader__file">
                    <image class="weui-uploader__img" src="/static/images/pic_160.png" mode="aspectFill" />
                  </div>
                  <div class="weui-uploader__file">
                    <image class="weui-uploader__img" src="/static/images/pic_160.png" mode="aspectFill" />
                  </div>
                  <div class="weui-uploader__file">
                    <image class="weui-uploader__img" src="/static/images/pic_160.png" mode="aspectFill" />
                  </div>
                  <div class="weui-uploader__file weui-uploader__file_status">
                    <image class="weui-uploader__img" src="/static/images/pic_160.png" mode="aspectFill" />
                    <div class="weui-uploader__file-content">
                      <icon type="warn" size="23" color="#F43530"></icon>
                    </div>
                  </div>
                  <div class="weui-uploader__file weui-uploader__file_status">
                    <image class="weui-uploader__img" src="../../../static/images/pic_160.png" mode="aspectFill" />
                    <div class="weui-uploader__file-content">50%</div>
                  </div>
                </div>
                <div class="weui-uploader__input-box">
                  <div class="weui-uploader__input" @click="chooseImage"></div>
                </div>
              </div>
            </div>
</template>

<script>

var fileUploader = function (opt) {
    var accessid = ''
    var accesskey = ''
    var host = ''
    var policyBase64 = ''
    var signature = ''
    var callbackbody = ''
    var filename = ''
    var key = ''
    var expire = 0
    var g_object_name = ''
    var g_object_name_type = ''
    var now = timestamp = Date.parse(new Date()) / 1000;

    function send_request() {
        var xmlhttp = null;
        if (window.XMLHttpRequest) {
            xmlhttp = new XMLHttpRequest();
        }
        else if (window.ActiveXObject) {
            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        if (xmlhttp != null) {
            serverUrl = '/pub/file/policy'
            xmlhttp.open("GET", serverUrl, false);
            xmlhttp.send(null);
            return xmlhttp.responseText
        }
        else {
            console.error("Your browser does not support XMLHTTP.");
        }
    };

    function get_signature() {
        //可以判断当前expire是否超过了当前时间,如果超过了当前时间,就重新取一下.3s 做为缓冲
        now = timestamp = Date.parse(new Date()) / 1000;
        if (expire < now + 3) {
            body = send_request()
            var obj = eval("(" + body + ")");
            host = obj['host']
            policyBase64 = obj['policy']
            accessid = obj['accessid']
            signature = obj['signature']
            expire = parseInt(obj['expire'])
            callbackbody = obj['callback']
            key = obj['dir']
            return true;
        }
        return false;
    };

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
        pos = filename.lastIndexOf('.')
        suffix = ''
        if (pos != -1) {
            suffix = filename.substring(pos)
        }
        return suffix;
    }

    function calculate_object_name(filename) {
        g_object_name = random_string(10) + suffix
    }


    function set_upload_param(up, file, ret) {

        if (ret == false) {
            ret = get_signature()
        }
        g_object_name = key;
        if (file.name != '') {
            suffix = get_suffix(file.name)
            calculate_object_name(file.name)
        }
        file.randomName = g_object_name
        new_multipart_params = {
            'key':  key + '/' + g_object_name,
            'policy': policyBase64,
            'OSSAccessKeyId': accessid,
            'success_action_status': '200', //让服务端返回200,不然，默认会返回204
            'callback': callbackbody,
            'signature': signature,
        };

        up.setOption({
            'url': host,
            'multipart_params': new_multipart_params
        });

        up.start();
    }

    var uploader = new plupload.Uploader({
        runtimes: 'html5,flash,silverlight,html4',
        browse_button: opt.selectId,
        multi_selection: true,
        //container: document.getElementById('container'),
        flash_swf_url: libPath + '/js/Moxie.swf',
        silverlight_xap_url: libPath +'/js/Moxie.xap',
        url: 'http://oss.aliyuncs.com',

        filters: {
            mime_types: [ //只允许上传图片
                {title: "Image files", extensions: "jpg,gif,png,bmp,jpeg"}
            ],
            max_file_size: '10mb', //最大只能上传10mb的文件
            prevent_duplicates: false //不允许选取重复文件
        },

        init: {
            PostInit: function () {

            },

            FilesAdded: function (up, files) {
                Util.loading(true)
                for(var i = 0; i < files.length; i++) {
                    set_upload_param(uploader, files[0], false)
                }
            },

            BeforeUpload: function (up, file) {
                // Util.loading(true)
                // set_upload_param(up, file.name, true);
            },

            UploadProgress: function (up, file) {

                Util.process(file.percent/100);
            },

            FileUploaded: function (up, file, info) {
                if(opt.success) {
                    opt.success(file);
                }
                Util.loading(false)
            },

            Error: function (up, err) {
                if(opt.error) {
                    opt.error(file);
                }
                if (err.code == -600) {
                    console.error("选择的文件太大了,可以根据应用情况，在upload.js 设置一下上传的最大大小")
                }
                else if (err.code == -601) {
                    console.error("选择的文件后缀不对,可以根据应用情况，在upload.js进行设置可允许的上传文件类型");
                }
                else if (err.code == -602) {
                    console.error("这个文件已经上传过一遍了")
                }
                else {
                    console.error("Error xml:" + err.response)
                }
            }
        }
    });

    uploader.init();
}

export default {
  props: {
    files:{
    }
  },
  data(){
    return {
      ossInfo:null,
      timestamp,
    }
  },
  methods: {
    chooseImage(e) {
      let _this = this;
      wx.chooseImage({
        count: 1, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function(res) {
          console.log('成功上传：' + res.tempFilePaths);
          // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片
          _this.files = _this.files.concat(res.tempFilePaths);
        },
        fail: function() {
          console.log('fail');
        },
        complete: function() {
          console.log('commplete');
        }
      });
    },
    uploadToSSO(file){
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

    var startUpload = function(uploadFilePath){
      var fileName = random_string(10) + get_suffix(uploadFilePath)
        wx.uploadFile({
          url:"http://oss.aliyuncs.com",
          filePath:uploadFilePath,
          name:fileName,
          formData:{
            'key':  that.ossInfo.key + '/' + fileName,
            'policy': that.ossInfo.policy,
            'OSSAccessKeyId': that.ossInfo.accessid,
            'success_action_status': '200', //让服务端返回200,不然，默认会返回204
            'signature': that.ossInfo.signature
          },
          success(res){
            that.files.push(that.$kyutil.downloadUrl(fileName))
          }
        })
      }

      
      var now = Date.parse(new Date()) / 1000;
      
      if (!that.ossInfo ||  that.ossInfo.expire < now + 3) {
            this.$kyutil.HttpRequest(true, "/pub/file/policy", false, "", "", "GET", false, function(res) {
              that.ossInfo = res
              startUpload(file)
            })

        } else {
          startUpload(file)
        }
     
    },
    
    predivImage(e) {
      console.log(e);
      wx.previewImage({
        current: e.currentTarget.id, // 当前显示图片的http链接
        urls: this.files // 需要预览的图片http链接列表
      });
    },
    deleteImg(e) {
      Array.prototype.indexOf = function(val) {
        for (let i = 0; i < this.length; i++) {
          if (this[i] == val) return i;
        }
        return -1;
      };
      Array.prototype.remove = function(val) {
        let index = this.indexOf(val);
        if (index > -1) {
          this.splice(index, 1);
        }
      };
      this.files.remove(e.currentTarget.id);
    }
  }
}
</script>

<style>
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
