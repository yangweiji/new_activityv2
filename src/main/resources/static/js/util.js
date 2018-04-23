var Util = window.Util || {}

Util.global_object_id = 0;
Util.global_obj_map = new WeakMap;
Util.objectId = function (object) {
    if (object) {
        if (!Util.global_obj_map.has(object)) Util.global_obj_map.set(object, ++Util.global_object_id);
        return Util.global_obj_map.get(object);
    }
}

Util.storageGet = function (key) {
    var storage = window.localStorage;
    if(storage) {
        return storage.getItem(key)
    } else {
        $.AMUI.utils.cookie.get(key)
    }
}
Util.storageSet = function (key, value) {
    var storage = window.localStorage;
    if(storage) {
        return storage.setItem(key, value)
    } else {
        $.AMUI.utils.cookie.set(key, value)
    }
}
Util.storageRemove = function (key) {
    var storage = window.localStorage;
    if(storage) {
        return storage.removeItem(key)
    } else {
        $.AMUI.utils.cookie.set(key, '')
    }
}

Util.getQueryStrings = function (key)
{
    var vars = [], hash;
    var hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
    for(var i = 0; i < hashes.length; i++)
    {
        hash = hashes[i].split('=');
        vars.push(hash[0]);
        vars[hash[0]] = hash[1];
    }
    if(key){
        return vars[key]
    }
    return vars;
}

Util.init = function () {
    var isJsonObject = function (params) {
        return (params !== null
            && params !== undefined
            && params instanceof Object
            && Object.keys(params).length > 0
        );
    };

    var isArrayObject = function (params) {
        return (params !== null
            && params !== undefined
            && params instanceof Array
            && params.length > 0
        );
    };

    var isString = function (params) {
        return (params !== null
            && params !== undefined
            && params instanceof String
        );
    }

    var formatStringByArray = function (rootObj, params) {
        // "alfa {0}"
        var keys = rootObj.match(/(\{[\d]{1,}\}){1}/g);
        for (var key in keys) {
            // replace from {0} the "{" and "}" to empty string
            var keyValue = key.replace('{', '').replace('}', '');
            rootObj = rootObj.replace(
                '{' + key + '}',
                params[keyValue]
            );
        }

        return rootObj;
    };

    var formatStringByJson = function (rootObj, params) {
        // "alfa {0}"
        var keys = rootObj.match(/(\{[a-zA-Z0-9]{1,}\}){1}/g);
        for (var key in keys) {
            // replace from {0} the "{" and "}" to empty string
            if (keys[key]) {
                if (keys[key].replace) {
                    var keyValue = keys[key].replace('{', '').replace('}', '');
                    rootObj = rootObj.replace(
                        keys[key],
                        params[keyValue]
                    );
                } else {
                    console.log(keys[key]);
                }
            }
        }

        return rootObj;
    };

    var format = function (source, params, args) {

        if (params === null || params === undefined) {
            return source;
        } else if (isJsonObject(params)) {
            return formatStringByJson(source, params);
        } else if (isArrayObject(params)) {
            return formatStringByArray(source, params);
        } else {
            return source.replace(/{(\d+)}/g, function (match, number) {
                return typeof args[number] !== undefined ? args[number] : match;
            });
        }
    };

    String.prototype.format = function (params) {
        var $rootObj = this;
        return format($rootObj, params, arguments);
    };

}

Util.init()

Util.ajax = function(opt) {
    if(!opt.disableLoading) {
        Util.loading(true)
    }
    var option =  $.extend({
        dataType: "json",
        contentType: "application/json; charset=utf-8"

    }, opt)
    option.error = function () {
        console.error(arguments)
        if(opt.error) {
            opt.error.call(this, arguments)
        }
        if(!opt.disableLoading) {
            Util.loading(false)
        }
    }
    option.success = function () {
        if(opt.success) {
            opt.success.call(this, arguments)
        }
        if(!opt.disableLoading) {
            Util.loading(false)
        }
    }

    // option.beforeSend = function(xhr) {
    //     var token = $("meta[name='_csrf']").attr("content");
    //     var header = $("meta[name='_csrf_header']").attr("content");
    //     xhr.setRequestHeader(header, token);
    // }

    $.ajax(option);

}

Util.loading = function (start) {
    if(start) {
        $.AMUI.progress.start()
    } else {
        $.AMUI.progress.done()
    }
}

Util.process = function (percent) {
    $.AMUI.progress.set(percent);

}

Util.file = Util.file || {}
Util.file.downloadUrl = function (name, style) {
    if(name && (name.toLowerCase().indexOf('http://') == 0 || name.toLowerCase().indexOf('https://') == 0)){
        return name
    }
    var url = 'http://bjsmlsxh.oss-cn-beijing.aliyuncs.com/activity/' + name
    if(style){
        url += '?x-oss-process=style/' + style
    }
    return url
}
