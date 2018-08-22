var Util = window.Util || {}
Util.file = Util.file || {}

/*
  opt.randomName :随机文件名
  opt.
* */
Util.file.uploader = function (opt) {
    var libPath = '/asset/plupload'
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
        url: 'https://hxzt2018.oss-cn-beijing.aliyuncs.com',

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
