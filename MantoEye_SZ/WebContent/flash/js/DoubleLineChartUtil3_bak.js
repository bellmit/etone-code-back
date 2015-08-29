/****************/
/***柱状曲线图****/
/** ************* */
function findSWF(movieName){
    if (navigator.appName.indexOf("Microsoft") != -1) {
        return window[movieName];
    }
    else {
        return document[movieName];
    }
}

/*
 * 参数：text-标题 xlabels:x轴数据数组 datas:y值的数组 组成的数组
 */
function buildMutileLineChartData2(text, labels, xlabels, datas){
    var title = buildTitle(text);
    var lefty = getYAxisUtilData(datas[0], datas[1], datas[2], datas[3]);
    var chart = {
        "title": title,
        "tooltip": {
            "shadow": false,
            "stroke": 1,
            "colour": "#333333",
            "background": "#FFFFFF",
            "title": "{font-size: 14px; color: #905050;}",
            "body": "{font-size: 12px; font-weight: bold; color: #333333;}"
        },
        "elements": [{
            "type": "line",
            "colour": "#1B95D9",
            "text": buildLabel(labels[0]),
            "font-size": 10,
            "width": 1,
            "dot-style": buildDotStyle(labels[0]),
            "values": datas[0]
        }, {
            "type": "line",
            "colour": "#FF8000",
            "text": buildLabel(labels[1]),
            "font-size": 10,
            "width": 1,
            "dot-style": buildDotStyle(labels[1]),
            "values": datas[1]
        }, {
            "type": "line",
            "colour": "#99004D",
            "text": buildLabel(labels[2]),
            "font-size": 10,
            "width": 1,
            "dot-style": buildDotStyle(labels[2]),
            "values": datas[2]
        }, {
            "type": "line",
            "colour": "#FF0000",
            "text": buildLabel(labels[3]),
            "font-size": 10,
            "width": 1,
            "dot-style": buildDotStyle(labels[3]),
            "values": datas[3]
        }],
        "x_axis": {
            "stroke": 1,
            "tick_height": 3,
            "colour": "#909090",
            "grid_colour": "#333333",
            "labels": buildXLables(xlabels)
        },
        "y_axis": {
            "stroke": 1,
            "tick_length": 3,
            "max": lefty.max,
            "min": lefty.min,
            "steps": lefty.step,
            "colour": "#FF0080",
            "grid_colour": "#333333"
        }
    }
    return chart;
}

function buildDotStyle(label){
    return {
        "type": "anchor",
        "sides": 4,
        "alpha": 1,
        "hollow": true,
        "background-colour": "#a44a80",
        "background-alpha": 0.4,
        "width": 1,
        "tip": buildTip(label)
    };
}

function buildXLables(xlables){
    return {
        "align": "center",
        "labels": xlables
    }
}

function buildTip(label){
    // 如果有单位 则label与单位间用@@分开
    var ls = [];
    var unit = "";
    var l = label;
    if (label.indexOf("@@") != -1) {
        ls = label.split("@@");
        l = ls[0];
        unit = ls[1];
    }
    return l + "：#val#" + unit + " \n";
}

function buildLabel(label){
    if (label.indexOf("@@") != -1) {
        var us = label.split("@@");
        return us[0] + "(" + us[1] + ")";
    }
    else {
        return label;
    }
}

function buildTitle(text){
    var title = {
        "text": text,
        "style": "{font-size: 14px; color:#333333; font-family: Verdana; text-align: center;padding-top:10px;}"
    };
    return title;
}

function getYAxisUtilData(datas1, datas2, datas3, datas4){
    var obj = new Object();
    var max = 0;
    var min = 0;
    var step = 1;
    if (datas1.length > 0) {
        max = datas1[0].value;
        min = datas1[0].value;
        for (var i = 1; i < datas1.length; i++) {
            if (datas1[i].value > max) {
                max = datas1[i].value;
            }
            if (datas1[i].value < min) {
                min = datas1[i].value;
            }
        }
    }

   
    if (datas2.length > 0) {
        for (var i = 1; i < datas2.length; i++) {
            if (datas2[i].value > max) {
                max = datas2[i].value;
            }
            if (datas2[i].value < min) {
                min = datas2[i].value;
            }
        }
    }
    
    if (datas3.length > 0) {
        for (var i = 1; i < datas3.length; i++) {
            if (datas3[i].value > max) {
                max = datas3[i].value;
            }
            if (datas3[i].value < min) {
                min = datas3[i].value;
            }
        }
    }
    
    if (datas4.length > 0) {
        for (var i = 1; i < datas4.length; i++) {
            if (datas4[i].value > max) {
                max = datas4[i].value;
            }
            if (datas4[i].value < min) {
                min = datas4[i].value;
            }
        }
    }

    obj.max = 100;
    // 图形y轴从最小值开始
    // obj.min = min;
    // 图形y轴从0开始
    obj.min = 0;
    // 默认y轴刻度为10左右
    var fobj = getSteps(obj, 5);
    return fobj;
}

/*
 * 处理数据，根据最大值，最小值和分段数，步长
 */
function getSteps(obj, size){
    var max = obj.max;
    var min = obj.min;
    var k = max - min == 0 ? max : max - min;
    
    // 字符串(如:2346.9)
    var ttstep = k / size + "";
    var zs = ttstep.split(".")[0];
    var ststep = zs.substr(0, 1) + zs.substr(1).replace(/\d/g, '0');
    var step = parseInt(ststep);
    step = step == 0 ? 1 : step;
    
    var realMin = parseInt(min / step);
    var realMax = parseInt(max / step) == (min / step) ? parseInt(max / step) : (parseInt(max / step) + 1)
    realMin = realMin * step;
    realMax = realMax * step;
    var realSize = (realMax - realMin) / step;
    // alert("realMin:"+realMin);
    // alert("realMax:"+realMax);
    obj.max = realMax;
    obj.min = realMin;
    obj.step = step;
    obj.size = realSize;
    return obj;
}
