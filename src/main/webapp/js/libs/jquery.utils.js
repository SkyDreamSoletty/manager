/*
 * Read Request parameter by name, use it like this
 * Request.QueryString(name);
 */
Request = {
    QueryString: function (item) {
        var svalue = location.search.match(new RegExp("[\?\&]" + item + "=([^\&]*)(\&?)", "i"));
        return svalue ? svalue[1] : svalue;
    },
    QueryURL: function () {
        var svalue = location.pathname ;
        return svalue;
    },
    QueryAnchor: function () {
        var svalue = location.href.match(new RegExp("#([^\?\&]*)([\?\&]?)", "i"));
        if (svalue == null) return '';
        else return decodeURIComponent(svalue ? svalue[1] : svalue);
    }
};


/*
 * check all input:checkbox or uncheck them in a table sheet
 * if the sheet has more than one group,
 * you can add name attribute to the checkbox to filter which group to check
 *
 * e: CheckAll('#sheet')
 *
 */
CheckAll = function (area) {
    var obj = event.srcElement ? event.srcElement : event.target;
    if (obj.getAttribute('name') != null) {
        $(area + ' input:checkbox[name="' + obj.getAttribute('name') + '"]').attr("checked", obj.checked);
    } else {
        $(area + ' :checkbox').attr("checked", obj.checked);
    }
};

$(function () {
        $.expr[':'].textEquals = function (a, i, m) {
            return $(a).text().match("^" + m[3] + "$");
        };
        jQuery.download = function (url, data, method) {
            // 获取url和data
            if (url && data) {
                // data 是 string 或者 array/object
                // 把参数组装成 form的  input
                var inputs = '';

                if (typeof data == 'string') {
                    jQuery.each(data.split('&'), function () {
                        var pair = this.split('=');
                        inputs += '<input type="hidden" name="' + pair[0] + '" value="' + pair[1] + '" />';
                    });
                } else {
                    jQuery.each(data, function (i, e) {
                        inputs += '<input type="hidden" name="' + i + '" value="' + e + '" />';
                    });
                }
                // request发送请求
                jQuery('<form action="' + url + '" method="' + (method || 'post') + '">' + inputs + '</form>')
                    .appendTo('body').submit().remove();
            }
        };

        function mergeObject(ma, br, de, i) {
            if (_.isObject(br[de[i]]) && _.isObject(ma[de[i]])) {
                ma[de[i]] = mergeObject(ma[de[i]], br[de[i]], de, i + 1);
            } else {
                ma[de[i]] = br[de[i]];
            }
            return ma;
        }

        /*
         * serializeObject use for convert Form to Json
         * e: $('form').serializeObject()
         */
        $.fn.serializeObject = function () {
            var o = {};
            $.merge($(':input:enabled[name]', this), $(':input:enabled[name][form="' + $(this).attr('name') + '"]')).each(function () {
                var name = $(this).attr("name");
                var val = $.trim($(this).val() || '');
                var key_sp = name.split('.');

                //TODO: 算法有待优化
                if (key_sp.length > 1) {
                    for (var i = key_sp.length - 1; i >= 0; i--) {
                        var tmp = {};
                        tmp[key_sp[i]] = val;
                        val = tmp;
                    }
                    o = mergeObject(o, val, key_sp, 0);
                } else {
                    if (o[name]) {
                        if (!o[name].push) {
                            o[name] = [o[name]];
                        }
                        o[name].push(val);
                    } else {
                        o[name] = val;
                    }
                }
            });
            return o;
        };


        /*
         * reset a form,
         */
        $.fn.reset = function () {
            $.merge($(':input', this), $(':input[form="' + $(this).attr('name') + '"]')).each(function () {
                var type = this.type;
                var tag = this.tagName.toLowerCase();
                if (tag === 'input') {
                    if (type == 'checkbox' || type == 'radio') {
                        this.checked = $(this).attr('data-default') ? $(this).attr('data-default') : false;
                    } else {
                        this.value = $(this).attr('data-default') ? $(this).attr('data-default') : "";
                    }
                } else if (tag === 'textarea') {
                    this.value = $(this).attr('data-default') ? $(this).attr('data-default') : '';
                } else if (tag === 'select' && this.name) {
                    this.selectedIndex = 0;
                }
            });
        };


        /*
         * init form element value,
         * add data-default attribute to the :input element,
         * add form="form-name" attribute to contain the :input out of the form
         */
        $.fn.setData = function (data) {
            if (data) $.merge($(':input', this),$('img.hold'),$(':input[form="' + $(this).attr('name') + '"]')).each(function () {
                var type = this.type;
                var tag = this.tagName.toLowerCase();
                var name = this.name.split('.')[0]&&this.name.split('.')||[this.id] ;
                var value = data;
                if(name==''){return;}
                for (var i = 0; i < name.length; i++) {
                	var column = name[i];
                	var column2 = column.replace(/([A-Z])/g,"_$1").toLowerCase();
                    if (value != null && (value.hasOwnProperty(column) || value.hasOwnProperty(column2) )) {
                        value = value[column]!=undefined? value[column]:value[column2];
                    } else {
                        value = '';
                        break;
                    }
                }

                if (value == null) {
                    value = '';
                }

                if (tag === 'input') {
                    if (type === 'checkbox' || type === 'radio') {
                        if (!_.isNull(value) && (this.value == value + '' || _.indexOf(value, this.value) >= 0 || (this.value == 'on' && value))) {
                            this.checked = true;
                        } else {
                            this.checked = false;
                        }
                    } else {
                        this.value = value;
                    }
                } else if (tag === 'textarea') {
                    this.value = value;
                } else if (tag === 'select' && type === 'select-one' && this.name) {
                    var flag = false;
                    for (var i = 0; i < this.options.length; i++) {
                        this.options[i].selected = false;
                        if (this.options[i].value == value + '') {
                            this.options[i].selected = true;
                            flag = true;
                            continue;
                        }
                    }
                    if (!flag) this.selectedIndex = 0;
                } else if (tag === 'select' && type === 'select-multiple') {
                    for (var i = 0; i < this.options.length; i++) {
                        this.options[i].selected = false;
                        for (var j = 0; j < value.length; j++)
                            if (this.options[i].value == value[j]) {
                                this.options[i].selected = true;
                                continue;
                            }
                    }
                }else if(tag ==='img'){
                	this.src = value;
                }
            });
        };
    }
);
var digitUppercase = function(n) {  
    var fraction = ['角', '分'];  
    var digit = [  '零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'];  
    var unit = [['元', '万', '亿'],['', '拾', '佰', '仟']];  
    var head = n < 0 ? '欠' : '';  
    n = Math.abs(n);  
    var s = '';  
    for (var i = 0; i < fraction.length; i++) {  
        s += (digit[Math.floor(n * 10 * Math.pow(10, i)) % 10] + fraction[i]).replace(/零./, '');  
    }  
    s = s || '整';  
    n = Math.floor(n);  
    for (var i = 0; i < unit[0].length && n > 0; i++) {  
        var p = '';  
        for (var j = 0; j < unit[1].length && n > 0; j++) {  
            p = digit[n % 10] + unit[1][j] + p;  
            n = Math.floor(n / 10);  
        }  
        s = p.replace(/(零.)*零$/, '').replace(/^$/, '零') + unit[0][i] + s;  
    }  
    return head + s.replace(/(零.)*零元/, '元').replace(/(零.)+/g, '零').replace(/^整$/, '零元整');
};
