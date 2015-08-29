if (!window.GT) window.GT = {};
GT.Const = GT.Const || {};
GTConst = GT.Const;
GT.Const.Grid = {
    COL_CLASS_PREFIX: "td.",
    DEFAULT_ECG_ID: "gt",
    SHADOW_ROW: "_shadowRow",
    HIDE_HEADER_ROW: "_hideListRow",
    COL_T_CLASSNAME: "gt-col-",
    SKIN_CLASSNAME_PREFIX: "gt-skin-",
    SCROLLBAR_WIDTH: 18,
    MIN_COLWIDTH: 40,
    AJAX_HEADER: ["isAjaxRequest", "true"]
};
GT.Const.Key = {
    BACKSPACE: 8,
    TAB: 9,
    ENTER: 13,
    SHIFT: 16,
    CTRL: 17,
    PAUSE: 19,
    CAPSLOCK: 20,
    ESC: 27,
    SPACE: 33,
    PAGEUP: 33,
    PAGEDOWN: 34,
    END: 35,
    HOME: 36,
    LEFT: 37,
    UP: 38,
    RIGHT: 39,
    DOWN: 40,
    INSERT: 45,
    DELETE: 46,
    WIN: 91,
    WIN_R: 92,
    MENU: 93,
    F1: 112,
    F2: 113,
    F3: 114,
    F4: 115,
    F5: 116,
    F6: 117,
    F7: 118,
    F8: 119,
    F9: 120,
    F10: 121,
    F11: 122,
    F12: 123,
    NUMLOCK: 144,
    SCROLLLOCK: 145
};
if (!window.GT) window.GT = {};
GT.loaded = false;
GT.init = function(_) {
    _ = _ || window;
    GT.doc = document;
    _.undefined = _.undefined;
    var $ = _.navigator.userAgent.toLowerCase();
    GT.isIE = $.indexOf("msie") > -1;
    GT.isIE7 = $.indexOf("msie 7") > -1;
    GT.isIE8 = $.indexOf("msie 8") > -1;
    GT.isIE9 = $.indexOf("msie 9") > -1;
    GT.isFF = $.indexOf("firefox") > -1;
    GT.isFF1 = $.indexOf("firefox/1") > -1;
    GT.isFF2 = $.indexOf("firefox/2") > -1;
    GT.isFF3 = $.indexOf("firefox/3") > -1;
    GT.isOpera = $.indexOf("opera") > -1;
    GT.isWebkit = (/webkit|khtml/).test($);
    GT.isSafari = $.indexOf("safari") > -1 || GT.isWebkit;
    GT.isChrome = $.indexOf("chrome") > -1 || GT.isWebkit;
    GT.isGecko = GT.isMoz = !GT.isSafari && $.indexOf("gecko") > -1;
    GT.isStrict = GT.doc.compatMode == "CSS1Compat" || GT.isSafari;
    GT.isBoxModel = GT.isIE && !GT.isIE8 && !GT.isIE9 && !GT.isStrict;
    GT.isNotStrictIE = GT.isBoxModel;
    GT.isSecure = _.location.href.toLowerCase().indexOf("https") === 0;
    GT.isWindows = ($.indexOf("windows") != -1 || $.indexOf("win32") != -1);
    GT.isMac = ($.indexOf("macintosh") != -1 || $.indexOf("mac os x") != -1);
    GT.isLinux = ($.indexOf("linux") != -1)
};
GT.init();
GT.$extend = function(_, A, C) {
    if (arguments.length < 2) {
        A = _;
        _ = this
    }
    for (var B in A) {
        var $ = A[B];
        if (C && $ && GT.$type($, "object", "array")) $ = GT.$clone($, C);
        if ($ !== undefined) _[B] = $
    }
    return _
};
GT.$extend(GT, {
    $empty: function() {},
    $chk: function($) {
        return !! ($ || $ === 0 || $ === "")
    },
    $type: function($) {
        var A = arguments.length;
        if (A > 1) {
            for (var B = 1; B < A; B++) if (GT.$type($) == arguments[B]) return true;
            return false
        }
        var _ = typeof $;
        if ($ === null) return "object";
        if (_ == "undefined") return "undefined";
        if ($.htmlElement) return "element";
        if (_ == "object" && $.nodeType && $.nodeName) switch ($.nodeType) {
        case 1:
            return "element";
        case 3:
            return (/\S/).test($.nodeValue) ? "textnode": "whitespace"
        }
        if (GT.U.isArray($)) return "array";
        if (_ == "object" && typeof $.length == "number") return ($.callee) ? "arguments": "collection";
        else if (_ == "function" && typeof $.length == "number" && $[0] !== undefined) return "collection";
        return _
    },
    $merge: function() {
        var B = {};
        for (var C = 0; C < arguments.length; C++) for (var A in arguments[C]) {
            var _ = arguments[C][A],
            $ = B[A];
            if ($ && GT.$type(_, "object") && GT.$type($, "object")) B[A] = GT.$merge($, _);
            else B[A] = _
        }
        return B
    },
    $indexOf: function(_, A, $) {
        if (_) {
            $ = $ || 0;
            for (var C = $, B = _.length; C < B; C++) if (_[C] === A) return C
        }
        return - 1
    },
    $array: function(B, _, D, A) {
        var $ = [];
        if (B) {
            if (!GT.$chk(_)) _ = 0;
            if (!GT.$chk(D)) D = B.length;
            if (GT.$type(B, "arguments", "collection") || GT.$type(B, "array") && (_ > 0 || D < B.length)) {
                for (var E = _; E < D; E++) $.push(B[E])
            } else if (GT.$type(B, "array")) $ = $.concat(B);
            else for (var C in B) if (B.hasOwnProperty(C)) $.push(B[C])
        }
        return $
    },
    $clone: function(_, A) {
        var $;
        if (!_) $ = _;
        else if (GT.$type(_, "array", "arguments", "collection")) $ = GT.$array(_, 0, _.length, A);
        else $ = GT.$extend({},
        _, A);
        return $
    },
    $msg: function($, _) {
        for (var A = 1; A < arguments.length; A++) $ = GT.U.replaceAll($, "#{" + A + "}", arguments[A]);
        return $
    },
    $clear: function($) {
        window.clearTimeout($);
        window.clearInterval($);
        if (CollectGarbage) CollectGarbage();
        return null
    },
    $thread: function(_, A) {
        var $ = _;
        window.setTimeout($, A || 20)
    },
    $each: function(C, E, D, $) {
        var A = [];
        if (GT.$type(C, "array", "arguments", "collection") || C && !GT.$type(C, "string") && GT.$type(C.length, "number")) {
            for (var F = 0, B = C.length; F < B; F++) A.push(E.call(D || C, C[F], F, C, $))
        } else for (var _ in C) A.push(E.call(D || C, C[_], _, C, $));
        return A
    },
    $getText: function($) {
        return $.innerText === undefined ? $.textContent: $.innerText
    },
    $element: function($, B) {
        if (GT.$type($, "string")) {
            if (GT.isIE && B && (B.name || B.type)) {
                var _ = (B.name) ? " name=\"" + B.name + "\"": "",
                A = (B.type) ? " type=\"" + B.type + "\"": "";
                delete B.name;
                delete B.type;
                $ = "<" + $ + _ + A + ">"
            }
            $ = GT.doc.createElement($)
        }
        if (B) {
            if (B.style) {
                GT.$extend($.style, B.style);
                delete B.style
            }
            GT.$extend($, B)
        }
        return $
    }
});
GT.Class = function(_) {
    _ = _ || {};
    var $ = function() {
        var _ = this.ck2;
        if (GT.$type(_, "function")) _ = _.apply(this, arguments);
        if (GT.$type(_, "object")) GT.$extend(this, _);
        var $ = this.abstractMethods;
        GT.$each(this.abstractMethods, 
        function($) {
            this[$] = GT.$empty
        },
        this);
        return (arguments[0] !== GT.$empty && GT.$type(this.SO5, "function")) ? this.SO5.apply(this, arguments) : this
    };
    GT.$extend($, this);
    $.constructor = GT.Class;
    $.prototype = _;
    return $
};
GT.Class.prototype = {
    extend: function() {
        var C = new this(GT.$empty);
        for (var D = 0, _ = arguments.length; D < _; D++) {
            var A = arguments[D];
            for (var $ in A) {
                var B = C[$];
                C[$] = GT.Class.merge(B, A[$])
            }
        }
        return new GT.Class(C)
    }
};
GT.Class.merge = function($, _) {
    if ($ && $ != _) {
        var A = GT.$type(_);
        if (!GT.$type($, A)) return _;
        switch (A) {
        case "function":
            var B = function() {
                this.vP7 = arguments.callee.vP7;
                return _.apply(this, arguments)
            };
            B.vP7 = $;
            return B;
        case "object":
            return GT.$merge($, _)
        }
    }
    return _
};
GT.$class = function($) {
    return new GT.Class($)
};
GT.$e = GT.$element;
GT.$A = GT.$array;
GT.$byId = function($, A) {
    if (!GT.$chk($)) return null;
    var _ = GT.$type($);
    if (_ == "element") return GT.$e($, A);
    if (_ == "string" || _ == "number") $ = GT.doc.getElementById("" + $);
    if (!$) return null;
    if (GT.U.contains(["object", "embed"], !$.tagName ? $.tagName.toLowerCase() : "")) return $;
    return GT.$e($)
};
GT.AO9 = function($) {
    if (!$ || !document) return null;
    return $.dom ? $.dom: (typeof $ == "string" ? document.getElementById($) : $)
};
GT.$byName = function(_) {
    var A = [];
    if (!GT.$chk(_)) return A;
    var $ = GT.doc.getElementsByName("" + _);
    if (!$ || $.length < 1) return A;
    for (var B = 0; B < $.length; B++) {
        _ = $[B];
        A.push(GT.U.contains(["object", "embed"], _.tagName.toLowerCase()) ? _: GT.$e(_))
    }
    return A
};
GT.$ = function($) {
    var _ = GT.$byName($);
    if (_ && _.length > 0) return _[0];
    return (!_ || _.length < 1) ? GT.$byId($) : _
};
GT.Utils = {
    P_START: "@{",
    P_END: "}",
    P_VAR_NAME: "obj_in",
    parseExpression: function(ex, pName, argNames, pStart, pEnd) {
        pStart = pStart || GT.U.P_START;
        pEnd = pEnd || GT.U.P_END;
        pName = pName || GT.U.P_VAR_NAME;
        argNames = argNames || pName;
        var startLength = pStart.length,
        endLength = pEnd.length,
        templateC = [],
        current = 0;
        while (true) {
            var start = ex.indexOf(pStart, current),
            sBegin = start + startLength,
            sEnd = ex.indexOf(pEnd, sBegin),
            str = null,
            val = null;
            if (sBegin >= startLength && sEnd > sBegin) {
                str = ex.substring(current, start);
                val = ex.substring(sBegin, sEnd)
            } else str = ex.substring(current);
            str = GT.U.escapeString(str);
            templateC.push(str);
            if (val === null) break;
            if (!GT.U.isNumber(val)) val = (pName ? (pName + ".") : "") + val;
            else val = (pName ? (pName + "[") : "") + val + (pName ? "]": "");
            templateC.push(val);
            current = sEnd + endLength
        }
        var t = "function(" + argNames + "){ return " + templateC.join("+") + " }";
        eval("t=" + t);
        return t
    },
    isArray: function($) {
        return Object.prototype.toString.apply($) === "[object Array]"
    },
    isNumber: function($, _) {
        return $ === 0 || (_ ? GT.$type($, "number") : ($ && !isNaN($)))
    },
    parseInt: function($, A) {
        var _ = parseInt($);
        return isNaN(parseInt($)) ? A || 0: _
    },
    add2Map: function(A, _, $) {
        $ = $ || {};
        if ($[A] === undefined) $[A] = _;
        else {
            $[A] = [].concat($[A]);
            $[A].push(_)
        }
        return $
    },
    moveItem: function(_, $, B) {
        if ($ == B) return _;
        var A = _[$],
        C = _[B];
        _.splice(B, 1, A, C);
        if ($ < B) _.splice($, 1);
        else _.splice($ + 1, 1);
        return _
    },
    convert: function(_, $) {
        switch ($) {
        case "int":
            return parseInt(_);
        case "float":
           // return parseFloat(_);
              return parseFloat(_.replace(/,/g,''));
        case "float_2":
               return myFloatFormat(2,_);  
        case "float_4":
               return myFloatFormat(4,_);        
        case "date":
            return _;
        default:
            return _
        }
        return _
    },myFloatFormat:function(bit,val){
    	var strDecimal = (val+"").replace(/,/g,'');
    	var i=0;  
        var strFill="";
        var addDecimal="0.";             
        while(i<bit)
        {           
          strFill=strFill+"0";
          if(i==bit-1)
          {            
             addDecimal=addDecimal+"1";            
          }
          else
          {            
             addDecimal=addDecimal+"0";
          }         
          i=i+1;         
        } 
        i=0;          
        var beginPlace=strDecimal.indexOf(".");
        if(beginPlace==-1)
        {
             if(bit==0)
             {
              return strDecimal;                      
             }
             return strDecimal+"."+strFill;         
        }            
        var strDecimalC=strDecimal+strFill;
        
        var str= strDecimalC.split(/[.]/);      
        var strInt=str[0];      
        var strDecimal=str[1]+strFill;
        var IntDecimal=parseFloat("0."+strDecimal);       
        var validPlace=beginPlace+bit+1;
        var validData=strDecimalC.substring(validPlace,validPlace+1);    
        if(parseInt(validData)>4)
        {  
             if(bit==0)
              {
                  return parseInt(strInt)+1;                    
              }
             var differents="0."+strFill+strDecimal.substring(bit,strDecimal.length);
             IntDecimal=IntDecimal-parseFloat(differents);
             IntDecimal=IntDecimal+parseFloat(addDecimal);
       
             var DecimalValue=parseInt(strInt)+IntDecimal;
            
             if(DecimalValue.toString().indexOf(".")== -1 ) 
                DecimalValue=DecimalValue.toString()+".";
               
             strDecimalC=DecimalValue.toString(10)+strFill;
        }
        var beginPlace=strDecimalC.indexOf(".");
        var beginStr=strDecimalC.substring(0,beginPlace);
        if(bit==0)
        {      
            return beginStr;            
        }
        return strDecimalC.substring(0, beginPlace+bit+1);
    },
    getTagName: function($) {
        return $ && $.tagName ? String($.tagName).toUpperCase() : null
    },
    LG4: function(A, $, _, B) {
        if (!$) {
            _ = GT.$event(_);
            $ = GT.U.getEventTarget(_)
        }
        B = B || 6;
        if (!$) return null;
        A = A.toLowerCase();
        while ($ && (B--) > 0) {
            if ($.tagName && $.tagName.toLowerCase() == A) return $;
            if (GT.U.hasClass($.className, "gt-grid") && A != "div") break;
            $ = $.parentNode
        }
        return null
    },
    focus: function(_) {
        if (_) {
            try {
                _.focus();
                _.select && _.select()
            } catch($) {}
        }
    },
    hasClass: function($, _) {
        return $ ? GT.U.hasSubString($.className, _, " ") : false
    },
    addClass: function($, _) {
        if ($ && !GT.U.hasClass($, _)) $.className = GT.U.gu5($.className + " " + _);
        return $
    },
    removeClass: function($, _) {
        if ($) $.className = GT.U.gu5($.className.replace(new RegExp("(^|\\s)" + _ + "(?:\\s|$)"), "$1"));
        return $
    },
    toggleClass: function($, _) {
        return GT.U.hasClass($, _) ? GT.U.removeClass($, _) : GT.U.addClass($, _)
    },
    hasSubString: function(_, A, $) {
        return ($) ? ($ + _ + $).indexOf($ + A + $) > -1: _.indexOf(A) > -1
    },
    childElement: function(_, $) {
        var B = 0,
        A = _ ? _.firstChild: null;
        while (A) {
            if (A.nodeType == 1) if (++B == $) return A;
            A = A.nextSibling
        }
        return null
    },
    firstChildElement: function($) {
        return GT.U.childElement($, 1)
    },
    Td9: function($) {
        var _ = $.childNodes[$.childNodes.length - 1];
        return _.nodeType == 1 ? _: GT.U.prevElement(_)
    },
    nextElement: function($) {
        while (($ = $.nextSibling) && $.nodeType != 1);
        return $
    },
    prevElement: function($) {
        while (($ = $.previousSibling) && $.nodeType != 1);
        return $
    },
    getCellIndex: function(_) {
        if (GT.isIE) {
            var $ = _.parentNode.cells;
            for (var B = 0, A = $.length; B < A; B++) if ($[B] === _) return B
        }
        return _.cellIndex
    },
    insertNodeBefore: function($, _) {
        if (!$ || !_ || !_.parentNode) return null;
        _.parentNode.insertBefore($, _);
        return $
    },
    insertNodeAfter: function($, _) {
        _.parentNode.insertBefore($, _.nextSibling);
        return $
    },
    listToMap: function(_) {
        var $ = {};
        for (var A = 0; A < _.length; A++) $[_[A]] = _[A];
        return $
    },
    createSelect: function(A, B, C, _) {
        _ = _ || GT.$e("select", C || {});
        var $ = GT.doc.createDocumentFragment();
        GT.$each(A, 
        function(_, C) {
            var A = GT.$e("option", {
                "value": C,
                "text": "" + _,
                innerHTML: _
            });
            if (GT.$chk(B) && C == B) A.selected = true;
            $.appendChild(A)
        });
        _.appendChild($);
        return _
    },
    createSelectHTML: function(B, D, G) {
        G = G || {};
        var $ = G.id ? (" id=\"" + G.id + "\" ") : " ",
        _ = G.className || "",
        C = G.style ? (" style=\"" + G.style + "\" ") : " ",
        F = ["<select" + $ + C + "class=\"gt-input-select " + _ + "\">"];
        for (var E in B) {
            var A = "";
            if ((D || D === 0) && E == D) A = " selected=\"selected\" ";
            F.push("<option value=\"" + E + "\" " + A + ">" + B[E] + "</option>")
        }
        F.push("</select>");
        return F.join("")
    },
    getEventTarget: function(A) {
        var _ = null;
        try {
            _ = A.target || A.srcElement
        } catch($) {
            return null
        }
        return ! _ ? null: (_.nodeType == 3 ? _.parentNode: _)
    },
    stopEvent: function($) {
        $ = $ || window.event;
        if ($) if ($.stopPropagation) {
            $.stopPropagation();
            $.preventDefault()
        } else {
            $.cancelBubble = true;
            $.returnValue = false
        }
    },
    addEvent: function(_, B, D, C, A) {
        if (!D || !_ || !B) return false;
        if (arguments.length > 3) D = GT.U.bindAsEventListener(D, C, A);
        if (_.addEventListener) _.addEventListener(B, D, false);
        else {
            var $ = B == "selectstart" ? B: "on" + B;
            _.attachEvent($, D)
        }
        GT.EventCache.add(_, B, D, false);
        return _
    },
    removeEvent: function(_, B, D, C, A) {
        if (!D || !_ || !B) return false;
        if (arguments.length > 3) D = GT.U.bindAsEventListener(D, C, A);
        if (_.addEventListener) _.removeEventListener(B, D, false);
        else {
            var $ = B == "selectstart" ? B: "on" + B;
            _.detachEvent($, D)
        }
        GT.EventCache.remove(_, B, D, false);
        return _
    },
    Eg6: [],
    Ed3: function() {
        for (var _ = 0; _ < GT.U.Eg6.length; _++) {
            var $ = GT.U.Eg6[_];
            $.apply(this, arguments)
        }
        GT.loaded = true
    },
    onLoad: function(_, $) {
        $ = $ || window;
        GT.U.Eg6.push(_);
        if (!GT.U.Ed3.hasAdd) {
            GT.U.addEvent($, "load", GT.U.Ed3);
            GT.U.Ed3.hasAdd = true
        }
    },
    Kk4: function() {
        return GT.doc.createElement("div")
    } (),
    createElementFromHTML: function(_, A) {
        GT.U.Kk4.innerHTML = _;
        var $ = GT.U.firstChildElement(GT.U.Kk4);
        A.appendChild($);
        GT.U.Kk4.innerHTML = "";
        return $
    },
    createTrFromHTML: function(_, A) {
        GT.U.Kk4.innerHTML = "<table><tbody>" + _ + "</tbody></table>";
        var $ = GT.U.Kk4.getElementsByTagName("tr")[0];
        A.appendChild($);
        GT.U.Kk4.innerHTML = "";
        return $
    },
    removeNode: function(_) {
        for (var A = 0; A < arguments.length; A++) {
            var $ = arguments[A];
            if (!$ || !$.parentNode || $.tagName == "BODY") return null;
            GT.EventCache.remove($);
            if (GT.isIE) {
                GT.U.Kk4.appendChild($);
                GT.U.Kk4.innerHTML = ""
            } else $.parentNode.removeChild($)
        }
    },
    removeNodeTree: function($) {
        if ($) {
            var _ = $.getElementsByTagName("*");
            for (var A = 0; A < _.length; A++) GT.U.removeNode(_[A]);
            GT.U.removeNode($)
        }
    },
    getLastChild: function($) {
        return $.childNodes[$.childNodes.length - 1]
    },
    getPosLeftTop: function($, _) {
        _ = _ || window;
        var B = $.offsetTop,
        A = $.offsetLeft;
        $ = $.offsetParent;
        while ($ && $ != _) {
            B += ($.offsetTop - $.scrollTop);
            A += ($.offsetLeft - $.scrollLeft);
            $ = $.offsetParent
        }
        return [A, B]
    },
    getPosRight: function($) {
        return GT.U.getPosLeftTop($)[0] + $.offsetWidth
    },
    getPosBottom: function($) {
        return GT.U.getPosLeftTop($)[1] + $.offsetHeight
    },
    getHeight: function(A, $) {
        var C = A.offsetHeight || 0;
        if ($ !== true) return C;
        var B = GT.U.getBorderWidths(A),
        _ = GT.U.getPaddings(A);
        return C - B[0] - B[2] - _[0] - _[2]
    },
    getWidth: function(B, _) {
        var $ = B.offsetWidth || 0;
        if (_ !== true) return $;
        var C = GT.U.getBorderWidths(B),
        A = GT.U.getPaddings(B);
        return $ - C[1] - C[3] - A[1] - A[3]
    },
    getBorderWidths: function($) {
        return [GT.U.parseInt($.style.borderTopWidth), GT.U.parseInt($.style.borderRightWidth), GT.U.parseInt($.style.borderBottomWidth), GT.U.parseInt($.style.borderLeftWidth)]
    },
    getPaddings: function($) {
        return [GT.U.parseInt($.style.paddingTop), GT.U.parseInt($.style.paddingRight), GT.U.parseInt($.style.paddingBottom), GT.U.parseInt($.style.paddingLeft)]
    },
    getPageX: function($) {
        $ = $ || window.event;
        var _ = $.pageX;
        if (!_ && 0 !== _) {
            _ = $.clientX || 0;
            if (GT.isIE) _ += GT.U.getPageScroll()[0]
        }
        return _
    },
    getPageY: function($) {
        $ = $ || window.event;
        var _ = $.pageY;
        if (!_ && 0 !== _) {
            _ = $.clientY || 0;
            if (GT.isIE) _ += GT.U.getPageScroll()[1]
        }
        return _
    },
    getPageScroll: function() {
        var _ = GT.doc.documentElement,
        $ = GT.doc.body;
        if (_ && (_.scrollLeft || _.scrollTop)) return [_.scrollLeft, _.scrollTop];
        else if ($) return [$.scrollLeft, _.scrollTop];
        else return [0, 0]
    },
    getScroll: function(A) {
        var $ = A,
        B = GT.doc;
        if ($ == B || $ == B.body) {
            var C = window.pageXOffset || B.documentElement.scrollLeft || B.body.scrollLeft || 0,
            _ = window.pageYOffset || B.documentElement.scrollTop || B.body.scrollTop || 0;
            return [C, _]
        } else return [$.scrollLeft, $.scrollTop]
    },
    getXY: function(F, A) {
        var H,
        E,
        _,
        B,
        I = GT.doc.body;
        if (F.getBoundingClientRect) {
            _ = F.getBoundingClientRect();
            B = GT.U.getScroll(GT.doc);
            return [_.left + B[0], _.top + B[1]]
        }
        var K = 0,
        J = 0;
        H = F;
        A = A || I;
        var D = F.style.position == "absolute";
        while (H) {
            K += H.offsetLeft;
            J += H.offsetTop;
            if (!D && H.style.position == "absolute") D = true;
            if (GT.isGecko) {
                E = H;
                var C = parseInt(E.style.borderTopWidth, 10) || 0,
                G = parseInt(E.style.borderLeftWidth, 10) || 0;
                K += G;
                J += C;
                if (H != F && E.style.overflow != "visible") {
                    K += G;
                    J += C
                }
            }
            H = H.offsetParent
        }
        if (GT.isSafari && D) {
            K -= I.offsetLeft;
            J -= I.offsetTop
        }
        if (GT.isGecko && !D) {
            var $ = I;
            K += parseInt($.style.borderTopWidth, 10) || 0;
            J += parseInt($.style.borderTopWidth, 10) || 0
        }
        H = F.parentNode;
        while (H && H != I) {
            if (!GT.isOpera || (H.tagName.toUpperCase() != "TR" && H.style.display != "inline")) {
                K -= H.scrollLeft;
                J -= H.scrollTop
            }
            H = H.parentNode
        }
        return [K, J]
    },
    setXY: function($, A) {
        if ($.style.position == "static") $.style.position = "relative";
        var _ = GT.U.Ed4($, A);
        if (A[0] !== false) $.style.left = _.left + "px";
        if (A[1] !== false) $.style.top = _.top + "px"
    },
    Ed4: function(_, E, D) {
        if (typeof E == "object" || E instanceof Array) {
            D = E[1];
            E = E[0]
        }
        var A = _.style.position,
        B = GT.U.getXY(_),
        C = parseInt(_.style.left, 10),
        $ = parseInt(_.style.top, 10);
        if (isNaN(C)) C = (A == "relative") ? 0: _.offsetLeft;
        if (isNaN($)) $ = (A == "relative") ? 0: _.offsetTop;
        return {
            left: (E - B[0] + C),
            top: (D - B[1] + $)
        }
    },
    getContentWidthHeight: function(A) {
        var _ = GT.U.parseInt(A.style.marginLeft),
        B = GT.U.parseInt(A.style.marginRight),
        D = GT.U.parseInt(A.style.paddingLeft),
        C = GT.U.parseInt(A.style.paddingRight),
        $ = A.clientWidth - D - C,
        E = A.clientHeight;
        return [$, E]
    },
    getPixelValue: function(A, _) {
        if (GT.$type(A, "number")) return A;
        A = "" + A;
        var $ = GT.U.parseInt(A);
        if (A.indexOf("%") > 1) return _ * $ / 100;
        return $
    },
    setValue: function(_, C) {
        _ = GT.$(_);
        if (!_) return;
        var A = _.tagName;
        A = ("" + A).toUpperCase();
        switch (A) {
        case "SELECT":
            var $ = [].concat(C),
            B = null;
            GT.$each(_.options, 
            function(C, A) {
                if (A === 0) B = C;
                C.selected = false;
                if (_.multiple) GT.$each($, 
                function($) {
                    C.selected = C.value == $
                });
                else if (C.value == $[0]) {
                    C.selected = true;
                    B = false
                }
            });
            if (!_.multiple && B) B.selected = true;
            return (_.multiple) ? $: $[0];
        case "INPUT":
            if (_.type == "checkbox" || _.type == "radio") {
                _.checked = _.value == C;
                break
            }
        case "TEXTAREA":
            _.value = C
        }
        return null
    },
    getValue: function(_) {
        _ = GT.$(_);
        if (!_) return;
        var A = _.tagName;
        switch (A) {
        case "SELECT":
            var $ = [];
            GT.$each(_.options, 
            function(_) {
                if (_.selected) $.push(_.value)
            });
            $ = (_.multiple) ? $: $[0];
            if (($ === null || $ === undefined) && _.options[0]) $ = _.options[0].value;
            return $;
        case "INPUT":
            if ((_.type == "checkbox" || _.type == "radio") && !_.checked) break;
        case "TEXTAREA":
            return _.value
        }
        return null
    },
    setOpacity: function($, _) {
        _ = _ > 1 ? 1: (_ < 0 ? 0: _);
        if (!$.currentStyle || !$.currentStyle.hasLayout) $.style.zoom = 1;
        if (GT.isIE) $.style.filter = (_ == 1) ? "": "alpha(opacity=" + _ * 100 + ")";
        $.style.opacity = _;
        if (_ === 0) {
            if ($.style.visibility != "hidden") $.style.visibility = "hidden"
        } else if ($.style.visibility != "visible") $.style.visibility = "visible";
        return $
    },
    replaceAll: function(exstr, ov, value) {
        var gc = GT.U.escapeRegExp(ov);
        if (!GT.$chk(gc) || gc === "") return exstr;
        var rep = "/" + gc + "/gm",
        r = null,
        cmd = "r=exstr.replace(" + rep + "," + GT.U.escapeString(value) + ")";
        eval(cmd);
        return r
    },
    trim: function(_, $) {
        if (!_ || !_.replace || !_.length) return _;
        var A = ($ > 0) ? (/^\s+/) : ($ < 0) ? (/\s+$/) : (/^\s+|\s+$/g);
        return _.replace(A, "")
    },
    escapeRegExp: function($) {
        return ! $ ? "" + $: ("" + $).replace(/\\/gm, "\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm, "\\$1")
    },
    escapeString: function($) {
        return $ === "" ? "\"\"": (!$ ? "" + $: ("\"" + ("" + $).replace(/(["\\])/g, "\\$1") + "\"").replace(/[\f]/g, "\\f").replace(/[\b]/g, "\\b").replace(/[\n]/g, "\\n").replace(/[\t]/g, "\\t").replace(/[\r]/g, "\\r"))
    },
    bind: function(A, _, $) {
        $ = [].concat($);
        return function() {
            return A.apply(_ || A, GT.U.merge(GT.$A(arguments), $))
        }
    },
    bindAsEventListener: function(A, _, $) {
        return function(B) {
            B = B || window.event;
            return A.apply(_ || A, [GT.$event(B)].concat($))
        }
    },
    trim: function(_, $) {
        var A = ($ > 0) ? (/^\s+/) : ($ < 0) ? (/\s+$/) : (/^\s+|\s+$/g);
        return _.replace(A, "")
    },
    gu5: function($) {
        return GT.U.trim($.replace(/\s{2,}/g, " "))
    },
    contains: function($, _, A) {
        return GT.U.indexOf($, _, A) != -1
    },
    merge: function(A, C, $) {
        var _ = A.length < C.length ? A.length: C.length;
        if ($) for (var D = 0, B = _; D < B; D++) A[D] = C[D];
        for (D = _, B = C.length; D < B; D++) A[D] = C[D];
        return A
    },
    each: function($, A, _) {
        return GT.$each($, A, _)
    },
    indexOf: function($, _, B) {
        var A = $.length;
        for (var C = (B < 0) ? Math.max(0, A + B) : B || 0; C < A; C++) if ($[C] === _) return C;
        return - 1
    },
    remove: function($, _, B) {
        var C = 0,
        A = $.length;
        while (C < A) if ($[C] === _) {
            $.splice(C, 1);
            if (!B) return $;
            A--
        } else C++;
        return $
    },
    next: function(_, A) {
        var $ = GT.U.indexOf(_, A);
        if ($ < 0) return null;
        return _[$ + 1]
    },
    previous: function(_, A) {
        var $ = GT.U.indexOf(_, A);
        if ($ < 1) return null;
        return _[$ - 1]
    },
    Ro2: function($, _) {
        _ = _ || GT.doc;
        var A = _.createElement("style");
        A.id = $;
        var B = _.getElementsByTagName("head")[0];
        B && B.appendChild(A);
        return A
    },
    getCheckboxState: function($, _) {
        var A = {};
        for (var B = 0; B < $.length; B++) if ($[B].name == _ && $[B].checked) A[$[B].value] = $[B].checked;
        return A
    }
};
GT.Util = GT.Utils;
GT.U = GT.Utils;
GT.Utils.CSS = function() {
    var $ = null;
    return {
        Ro2: function(C, $, A) {
            var D;
            A = A || GT.doc;
            var B = A.getElementsByTagName("head");
            if (!B || B.length < 1) {
                B = A.createElement("head");
                if (A.documentElement) A.documentElement.insertBefore(B, A.body);
                else A.appendChild(B);
                B = A.getElementsByTagName("head")
            }
            var E = B[0],
            F = A.createElement("style");
            F.setAttribute("type", "text/css");
            if ($) F.setAttribute("id", $);
            if (GT.isIE) {
                E.appendChild(F);
                D = F.styleSheet;
                D.cssText = C
            } else {
                try {
                    F.appendChild(A.createTextNode(C))
                } catch(_) {
                    F.cssText = C
                }
                E.appendChild(F);
                D = F.styleSheet ? F.styleSheet: (F.sheet || A.styleSheets[A.styleSheets.length - 1])
            }
            this.cacheStyleSheet(D);
            return D
        },
        getRules: function(_, A) {
            A = A || GT.doc;
            if (!$ || _) {
                $ = {};
                var C = A.styleSheets;
                for (var D = 0, B = C.length; D < B; D++) this.cacheStyleSheet(C[D])
            }
            return $
        },
        getRule: function(_, $) {
            var A = this.getRules($);
            return A[_.toLowerCase()]
        },
        updateRule: function($, B, A) {
            var _ = this.getRule($);
            if (_) _.style[B] = A
        },
        cacheStyleSheet: function(A) {
            $ = $ || {};
            try {
                var B = A.cssRules || A.rules;
                for (var C = B.length - 1; C >= 0; --C) $[B[C].selectorText.toLowerCase()] = B[C]
            } catch(_) {}
        }
    }
} ();
GT.$event = function($) {
    $ = $ || window.event;
    return $
};
GT.EventCache = (function() {
    var A = [],
    _ = [],
    B = {};
    function $($) {
        return "" + $ + "_" + $.id
    }
    return {
        add: function($, D, E) {
            if (!$) return;
            if (!GT.U.contains(A, arguments)) A.push(arguments);
            var C = GT.U.indexOf(_, $),
            F = C + "_" + $ + "_" + $.id;
            if (C < 0) {
                F = _.length + "_" + $ + "_" + $.id;
                _.push($);
                B[F] = {}
            }
            B[F][D] = B[F][D] || [];
            if (!GT.U.contains(B[F][D], E)) B[F][D].push(E)
        },
        remove: function($, C, D) {
            if (!$) return;
            var A = GT.U.indexOf(_, $),
            E = A + "_" + $ + "_" + $.id;
            if (A < 0 || !B[E]) return;
            if (!C) {
                B[E] = null;
                _[A] = null;
                return
            }
            if (!D && B[E][C]) {
                B[E][C] = null;
                delete B[E][C]
            }
            if (B[E][C]) B[E][C].remove(D)
        },
        El6: function() {
            GT.destroyGrids && GT.destroyGrids();
            GT.destroyWidgets && GT.destroyWidgets();
            var _,
            $;
            for (_ = A.length - 1; _ >= 0; _ = _ - 1) {
                $ = A[_];
                GT.EventCache.remove($[0]);
                if ($[0].removeEventListener) $[0].removeEventListener($[1], $[2], $[3]);
                if ($[1].substring(0, 2) != "on") $[1] = "on" + $[1];
                if ($[0].detachEvent) $[0].detachEvent($[1], $[2]);
                $[0][$[1]] = null;
                delete A[_]
            }
            window.CollectGarbage && CollectGarbage()
        }
    }
})();
GT.toQueryString = function(A) {
    if (!A || GT.$type(A, "string", "number")) return A;
    var _ = [];
    for (var C in A) {
        var B = A[C];
        if (B !== undefined) B = [].concat(B);
        for (var D = 0; D < B.length; D++) {
            var $ = B[D];
            if (GT.$type($, "object")) $ = GT.$json($);
            _.push(encodeURIComponent(C) + "=" + encodeURIComponent($))
        }
    }
    return _.join("&")
};
GT.toJSONString = function($, _) {
    return GT.JSON.encode($, "__gt_", _)
};
GT.$json = GT.toJSONString;
GT.FunctionCache = {};
GT.$invoke = function(B, _, $) {
    B = B || window;
    var A = B[_] || GT.$getFunction(_);
    if (typeof(A) == "function") return A.apply(B, $ || [])
};
GT.$getFunction = function($) {
    return GT.FunctionCache[$]
};
GT.$callFunction = function($, _) {
    GT.$invoke(null, $, _)
};
GT.$putFunction = function($, _) {
    GT.FunctionCache[$] = _
};
GT.$removeFunction = function($) {
    GT.FunctionCache[$] = null;
    delete GT.FunctionCache[$]
};
GT.U.onLoad(function() {
    GT.U.addEvent(window, "unload", GT.EventCache.El6)
});
GT.AjaxDefault = {
    paramName: "_gt_json"
};
GT.Ajax = GT.$class({
    ck2: function() {
        return {
            method: "post",
            jsonParamName: GT.AjaxDefault.paramName,
            async: true,
            urlEncoded: true,
            encoding: null,
            mimeType: null,
            beforeSend: GT.$empty,
            onComplete: GT.$empty,
            onSuccess: GT.$empty,
            onFailure: GT.$empty,
            onCancel: GT.$empty,
            xhr: "",
            url: "",
            data: "",
            paramType: "jsonString",
            headers: {
                "X-Requested-With": "XMLHttpRequest",
                "Accept": "text/javascript, text/html, application/xml,application/json, text/xml, */*"
            },
            autoCancel: false,
            evalScripts: false,
            evalResponse: false,
            responseContentType: "",
            dataUrl: false,
            queryParameters: null
        }
    },
    setQueryParameters: function($) {
        this.queryParameters = $
    },
    SO5: function(_) {
        _ = _ || {};
        if (GT.$type(_, "string")) _ = {
            url: _
        };
        if (! (this.xhr = this.getXHR())) return;
        var $ = GT.$extend(this.headers, _.headers);
        GT.$extend(this, _);
        if (this.mimeType) $["X-Response-MimeType"] = this.mimeType;
        this.headers = $
    },
    send: function(I) {
        this.running = true;
        if (GT.$type(I, "string")) I = {
            data: I
        };
        I = GT.$extend({
            data: this.data,
            url: this.url,
            method: this.method
        },
        I);
        var C = I.data,
        H = I.url,
        E = String(I.method).toLowerCase();
        if (GT.$invoke(this, "beforeSend", [this.xhr, C]) === false) return this;
        if (this.urlEncoded && E == "post") {
            var B = (this.encoding) ? "; charset=" + this.encoding: "";
            this.setHeader("Content-type", "application/x-www-form-urlencoded" + B)
        }
        switch (GT.$type(C)) {
        case "object":
            if (this.paramType == "jsonString") {
                var F = GT.$json(C);
                C = {};
                C[this.jsonParamName] = F
            }
            C = GT.toQueryString(C);
            break
        }
        var D;
        if (this.queryParameters && GT.$type(this.queryParameters, "object")) D = GT.toQueryString(this.queryParameters);
        else if (GT.$type(this.queryParameters, "string")) D = this.queryParameters;
        if (D && GT.$type(C, "string")) C = C + "&" + D;
        if (E == "post") {
            var _ = H.indexOf("?");
            if (_ >= 0) {
                C = H.substring(_ + 1) + "&" + C;
                H = H.substring(0, _)
            }
        } else if (C && (E == "get" || this.dataUrl)) {
            H = H + (H.indexOf("?") >= 0 ? "&": "?") + C;
            C = null
        }
        var A = this;
        this.xhr.open(E.toUpperCase(), H, this.async);
        this.xhr.onreadystatechange = function() {
            return A.onStateChange.apply(A, arguments)
        };
        for (var G in this.headers) {
            try {
                this.xhr.setRequestHeader(G, this.headers[G])
            } catch($) {}
        }
        this.xhr.send(C);
        if (!this.async) this.onStateChange();
        return this
    },
    onStateChange: function() {
        if (this.xhr.readyState != 4 || !this.running) return;
        this.running = false;
        this.status = 0;
        try {
            this.status = this.xhr.status
        } catch($) {}
        this.onComplete();
        if (this.isSuccess()) this._onSuccess();
        else this._onFailure();
        this.xhr.onreadystatechange = GT.$empty
    },
    isScript: function() {
        return (/(ecma|java)script/).test(this.getHeader("Content-type"))
    },
    isSuccess: function() {
        var $ = this.xhr.status;
        return (($ >= 200) && ($ < 300))
    },
    _onSuccess: function() {
        this.response = {
            "text": this.xhr.responseText,
            "xml": this.xhr.responseXML
        };
        this.onSuccess(this.response)
    },
    _onFailure: function($) {
        this.onFailure(this.xhr, $)
    },
    setHeader: function($, _) {
        this.headers[$] = _;
        return this
    },
    getHeader: function(_) {
        try {
            return this.xhr.getResponseHeader(_)
        } catch($) {
            return null
        }
    },
    getXHR: function() {
        return (window.XMLHttpRequest) ? new XMLHttpRequest() : ((window.ActiveXObject) ? new ActiveXObject("Microsoft.XMLHTTP") : false)
    },
    cancel: function() {
        if (!this.running) return this;
        this.running = false;
        this.xhr.abort();
        this.xhr.onreadystatechange = GT.$empty;
        this.xhr = this.getXHR();
        this.onCancel();
        return this
    }
});
GT.JSON = {
    encode: function(A, $, B) {
        var _,
        C = B ? "\n": "";
        switch (GT.$type(A)) {
        case "string":
            return "\"" + A.replace(/[\x00-\x1f\\"]/g, GT.JSON.rB0) + "\"";
        case "array":
            _ = [];
            GT.$each(A, 
            function(D, A) {
                var C = GT.JSON.encode(D, $, B);
                if (C || C === 0) _.push(C)
            });
            return "[" + C + (B ? _.join("," + C) : _) + "]" + C;
        case "object":
            if (A === null) return "null";
            _ = [];
            GT.$each(A, 
            function(C, D) {
                if (!$ || D.indexOf($) != 0) {
                    var A = GT.JSON.encode(C, $, B);
                    if (A) _.push(GT.JSON.encode(D, $, B) + ":" + A)
                }
            },
            null, $);
            return "{" + C + (B ? _.join("," + C) : _) + C + "}" + C;
        case "number":
        case "boolean":
            return String(A)
        }
        return null
    },
    decode: function(string, secure) {
        if (!GT.$type(string, "string") || !string.length) return null;
        if (secure && !(/^[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t]*$/).test(string.replace(/\\./g, "@").replace(/"[^"\\\n\r]*"/g, ""))) return null;
        return eval("(" + string + ")")
    },
    kr3: {
        "\b": "\\b",
        "\t": "\\t",
        "\n": "\\n",
        "\f": "\\f",
        "\r": "\\r",
        "\"": "\\\"",
        "\\": "\\\\"
    },
    rB0: function($) {
        return GT.JSON.kr3[$] || "\\u00" + Math.floor($.charCodeAt() / 16).toString(16) + ($.charCodeAt() % 16).toString(16)
    }
};
GT.Const.DataSet = {
    KEY: "__gt_ds_key__",
    INDEX: "__gt_ds_index__",
    ROW_KEY: "__gt_row_key__",
    NOT_VAILD: "__gt_no_valid__",
    SN_FIELD: "__gt_sn__",
    SORT_VALUE: "__gt_sort_value__",
    SORT_S: "__gt_"
};
GT.DataSetDefault = {
    SEQUENCE: 0,
    uniqueField: GT.Const.DataSet.SN_FIELD,
    recordType: "object",
    recordXpath: null,
    dataXML: null,
    currentBegin: 0,
    cursor: 0,
    startRecordNo: 0,
    cacheData: false,
    cacheModifiedData: true,
    modified: false,
    ck2: function() {
        return {
            fields: [],
            fieldsName: [],
            fieldsMap: {},
            fieldsInfo: {},
            data: null,
            Ge6: [],
            sf2: null,
            additional: [],
            sortInfo: [],
            queryInfo: [],
            reocrdIndex: {},
            updatedRecords: {},
            B$6: {},
            updatedFields: {},
            insertedRecords: {},
            deletedRecords: {},
            vv8: GT.$empty,
            xV0: GT.$empty
        }
    },
    SO5: function($) {
        GT.$extend(this, $);
        this.recordType = this.recordType || "object";
        this.fields && this.pS9(this.fields);
        this.data && this.Ry1(this.data);
        this.unfieldIdx = this.uniqueField
    },
    initValues: GT.$empty,
    isEqualRecord: function(_, A) {
        for (var $ in this.fieldsInfo) if (_[$] !== A[$]) return false;
        return true
    },
    gu5: function($) {
        if (!this.cacheData || $ === true) {
            this.data = null;
            this.currentBegin = 0;
            this.Ge6 = []
        }
        this.cleanModifiedData()
    },
    cleanModifiedData: function($) {
        if (!this.cacheModifiedData || $) {
            this.updatedRecords = {};
            this.B$6 = {};
            this.updatedFields = {};
            this.insertedRecords = {};
            this.deletedRecords = {}
        }
    },
    Ry1: function($) {
        if (!$) return false;
        this.gu5();
        return this.YG2($)
    },
    pS9: function(C) {
        this.fields = C;
        this.fieldsName = [];
        var A = null;
        for (var B = 0, _ = this.fields.length; B < _; B++) {
            var $ = this.fields[B] || {};
            if (GT.$type($, "string")) $ = {
                name: $
            };
            $.name = $.name || String(B);
            $.type = $.type || "string";
            $.index = $.index || (this.getRecordType() == "array" ? B: $.name);
            if ($.initValue) {
                A = A || {};
                A[$.index] = $.initValue
            }
            this.fieldsMap[$.name] = $;
            this.fieldsInfo[$.index] = $;
            this.fieldsName[B] = $.name
        }
        if (A) this.initValues = (function($) {
            return function(C, A, B) {
                for (var _ in $) C[_] = $[_](C, A, B)
            }
        })(A);
        else this.initValues = GT.$empty
    },
    YG2: function(A) {
        if (!A) return false;
        this.data = this.data || [];
        var $ = this,
        D = GT.Const.DataSet.SN_FIELD;
        for (var C = 0, B = A.length; C < B; C++) {
            var _ = A[C];
            _[D] = _[D] || this.SEQUENCE++;
            this.data.push(_);
            this.Ge6.push(this.currentBegin++);
            this.initValues(_, C, this)
        }
        return true
    },
    getDataProxySize: function() {
        return this.Ge6.length
    },
    resetDataProxy: function(_) {
        this.Ge6 = [];
        _ = _ || this.getSize();
        for (var $ = 0; $ < _; $++) this.Ge6[$] = $
    },
    loadData: function($) {
        if ($) return Ry1($.load())
    },
    Up3: function($) {
        if ($ && this.recordType != $) {
            this.recordType = $;
            this.pS9(this.fields)
        }
    },
    getRecord: function($) {
        return this.data ? this.data[this.Ge6[$]] : null
    },
    getDataRecord: function($) {
        return this.dataset.data[$]
    },
    setValueByName: function($, B, A) {
        var _ = this.fieldsMap[B].index;
        if (GT.$type($, "number")) $ = this.getRecord($);
        $[_] = A
    },
    getValueByName: function($, A) {
        var _ = this.fieldsMap[A].index;
        if (GT.$type($, "number")) $ = this.getRecord($);
        return $[_]
    },
    getFields: function() {},
    getRecordType: function(_, $) {
        this.recordType = _ || this.recordType;
        if (!GT.$type(this.recordType, "string") && (this.data && this.getSize() > 0)) {
            $ = this.data[0];
            if (GT.$type($, "array")) this.recordType = "array";
            else this.recordType = "object"
        }
        return this.recordType
    },
    filterCheck: {
        equal: function($, _) {
            return $ == _
        },
        notEqual: function($, _) {
            return $ != _
        },
        less: function($, _) {
            return $ < _
        },
        great: function($, _) {
            return $ > _
        },
        lessEqual: function($, _) {
            return $ <= _
        },
        greatEqual: function($, _) {
            return $ >= _
        },
        like: function($, _) {
            return ("" + $).indexOf(_ + "") >= 0
        },
        startWith: function($, _) {
            return ("" + $).indexOf(_ + "") === 0
        },
        endWith: function($, _) {
            $ = $ + "";
            _ = _ + "";
            return $.indexOf(_) == $.length - _.length
        }
    },
    filterData: function(_) {
        var $ = this,
        A = [];
        _ = [].concat(_);
        GT.$each(this.data, 
        function(C, E) {
            var F = true;
            for (var J = 0, H = _.length; J < H; J++) {
                var D = $.fieldsMap[_[J].fieldName].index,
                G = _[J].value,
                I = _[J].logic,
                B = C[D];
                F = $.filterCheck[I](B, G);
                if (!F) break
            }
            if (F) A.push(E)
        });
        return A
    },
    Pq2: function($) {
        var _ = $[GT.Const.DataSet.SN_FIELD] = this.SEQUENCE++;
        this.insertedRecords[_] = $;
        GT.$invoke(this, "vv8", [$]);
        this.modified = true
    },
    updateRecord: function($, F, C) {
        if (GT.$type($, "number")) $ = this.data[$];
        var B = $[GT.Const.DataSet.SN_FIELD],
        A = $[this.unfieldIdx],
        E = this.fieldsMap[F].type,
        _ = this.fieldsMap[F].index,
        D;
        if (!this.insertedRecords[B]) {
            this.B$6[A] = this.B$6[A] || {};
            this.B$6[A][_] = $[_];
            this.B$6[A][this.unfieldIdx] = A;
            this.updatedRecords[A] = $
        }
        if (this.insertedRecords[B] || GT.$invoke(this, "xV0", [$, F, C]) !== false) {
            if (E == "int") {
                C = parseInt(C);
                C = isNaN(C) ? "": C
            } else if (E == "float") {
                C = parseFloat(C.replace(/,/g,''));
                C = isNaN(C) ? "": C
            } else C = GT.$chk(C) ? String(C) : "";
            this.updatedFields[A] = this.updatedFields[A] || {};
            this.updatedFields[A][_] = C;
            this.updatedFields[A][this.unfieldIdx] = A;
            $[_] = C;
            this.modified = true
        }
    },
    undeleteRecord: function(D) {
        var B = -1,
        $,
        C;
        if (GT.$type(D, "number")) {
            B = D;
            if (B >= 0) {
                C = this.Ge6[B];
                $ = this.data[C]
            }
        } else if (D && (GT.$type(D, "object") || GT.$type(D, "array"))) $ = D;
        if ($) {
            var A = $[GT.Const.DataSet.SN_FIELD],
            _ = $[this.unfieldIdx];
            this.deletedRecords[_] = null;
            delete this.deletedRecords[_]
        }
    },
    bV2: function(D) {
        var B = -1,
        $,
        C;
        if (GT.$type(D, "number")) {
            B = D;
            if (B >= 0) {
                C = this.Ge6[B];
                $ = this.data[C]
            }
        } else if (D && (GT.$type(D, "object") || GT.$type(D, "array"))) $ = D;
        if ($) {
            var A = $[GT.Const.DataSet.SN_FIELD],
            _ = $[this.unfieldIdx];
            if (this.insertedRecords[A]) delete this.insertedRecords[A];
            else {
                if (this.updatedRecords[_]) {
                    delete this.updatedRecords[_];
                    delete this.B$6[_]
                }
                this.deletedRecords[_] = $;
                this.modified = true
            }
        }
    },
    addUniqueKey: function($) {},
    isInsertedRecord: function($) {
        return $ && this.insertedRecords[$[GT.Const.DataSet.SN_FIELD]] == $
    },
    Os2: function($) {
        return $ && this.deletedRecords[$[this.unfieldIdx]] == $
    },
    isUpdatedRecord: function($) {
        return $ && this.updatedRecords[$[this.unfieldIdx]] == $
    },
    sortFunction: null,
    negative: function($) {
        return function(A, _) {
            return 0 - $(A, _)
        }
    },
    sort: function(J) {
        var E = [].concat(J),
        C = [];
        for (var D = 0; D < E.length; D++) {
            var H = E[D];
            if (H) {
                var F,
                B,
                $,
                I = H.sortOrder.indexOf("def") === 0;
                if (!H.sortOrder || I) {
                    $ = GT.Const.DataSet.SN_FIELD;
                    B = "int"
                } else {
                    F = this.fieldsMap[H.fieldName];
                    if (F) {
                        $ = F.index;
                        B = F.type
                    }
                }
                C.push(!I && H.sortFn ? H.sortFn: this.getSortFuns($, H.sortOrder, B, H.getSortValue))
            }
        }
        var G = this,
        _ = C.length,
        A = function(B, A) {
            var D = G.data[B],
            F = G.data[A];
            for (var H = 0; H < _; H++) {
                var $ = C[H](D, F, E[H].sortOrder);
                if ($ != 0) return $
            }
            return 0
        };
        this.Ge6.sort(A)
    },
    getSortFuns: function(D, B, E, F) {
        var $ = this,
        A = GT.Const.DataSet.SORT_VALUE,
        C = {};
        compSort = this.sortFunction;
        if (!compSort) {
            var _ = F && B.indexOf("def") != 0 ? 
            function(_) {
                var A = _[D],
                $ = F(A, _);
                C[_[GT.Const.DataSet.SN_FIELD]] = $;
                return $
            }: function(_) {
                var A = _[D],
                $ = GT.U.convert(A, E);
                C[_[GT.Const.DataSet.SN_FIELD]] = $;
                return $
            };
            compSort = B == "desc" ? 
            function(B, D) {
                var $ = C[B] || _(B),
                A = C[D] || _(D);
                return $ < A ? 1: ($ > A ? -1: 0)
            }: function(B, D) {
                var $ = C[B] || _(B),
                A = C[D] || _(D);
                return $ < A ? -1: ($ > A ? 1: 0)
            }
        }
        return compSort
    },
    query: function($, _, B, A) {},
    getSize: function() {
        return ! this.data ? -1: this.data.length
    },
    getFieldsNum: function() {
        return this.fields.length
    },
    sum: function($) {},
    avg: function($) {}
};
GT.DataSet = GT.$class(GT.DataSetDefault);
if (!GT.Template) GT.Template = {};
GT.$extend(GT.Template, {
    Grid: {
        main: function(_) {
            var $ = _.id,
            A = [_.toolbarPosition == "top" || _.toolbarPosition == "t" ? "<div id=\"" + $ + "_toolBarBox\" class=\"gt-toolbar-box gt-toolbar-box-top\" ></div>": "", "<div id=\"" + $ + "_viewport\" class=\"gt-viewport" + (_.simpleScrollbar ? " gt-simple-scrollbar": "") + "\" >", "<div id=\"" + $ + "_headDiv\" class=\"gt-head-div\"><div class=\"gt-head-wrap\" ></div>", "<div id=\"" + $ + "_columnMoveS\" class=\"gt-column-moveflag\"></div>", "<div id=\"" + $ + "_headerGhost\" class=\"gt-head-ghost\"></div>", "</div>", "<div id=\"" + $ + "_bodyDiv\" class=\"gt-body-div\"></div>", "<div id=\"" + $ + "_freeze_headDiv\" class=\"gt-freeze-div\" ></div>", "<div id=\"" + $ + "_freeze_bodyDiv\" class=\"gt-freeze-div\" ></div>", "</div>", _.toolbarPosition == "bottom" || _.toolbarPosition == "b" ? "<div id=\"" + $ + "_toolBarBox\" class=\"gt-toolbar-box\" ></div>": "", "<div id=\"" + $ + "_separateLine\" class=\"gt-split-line\"></div>", "<div id=\"" + $ + "_mask\" class=\"gt-grid-mask\" >", "<div  id=\"" + $ + "_waiting\" class=\"gt-grid-waiting\">", "<div class=\"gt-grid-waiting-icon\"></div><div class=\"gt-grid-waiting-text\">" + _.getMsg("WAITING_MSG") + "</div>", "</div>", "<div class=\"gt-grid-mask-bg\">", "</div>", "</div>"];
            return A.join("\n")
        },
        formIFrame: function(_) {
            var $ = _.id,
            A = ["<div class=\"gt-hidden\" >", "<form id=\"" + $ + "_export_form\" target=\"" + $ + "_export_iframe\" style=\"width:0px;height:0px;margin:0px;padding:0xp\" method=\"post\" width=\"0\" height=\"0\" >", "<input type=\"hidden\" id=\"" + $ + "_export_filename\" name=\"exportFileName\"  value=\"\" />", "<input type=\"hidden\" id=\"" + $ + "_export_exporttype\" name=\"exportType\" value=\"\" />", "<textarea id=\"" + $ + "_export_form_textarea\" name=\"\" style=\"width:0px;height:0px;display:none;\" ></textarea>", "</form>", "<iframe id=\"" + $ + "_export_iframe\"  name=\"" + $ + "_export_iframe\" scrolling=\"no\" style=\"width:0px;height:0px;\" width=\"0\" height=\"0\" border=\"0\" frameborder=\"0\" >", "</iframe>", "</div>"];
            return A.join("\n")
        },
        UY2: function(B, $, _) {
            var A = GT.$e("td", {
                className: $.styleClass,
                columnId: $.id
            }),
            C = $.hdRenderer($.header, $, B);
            $.title = $.title || $.header || "";
            C = (!C || GT.U.trim(C) == "") ? "&#160;": C;
            if (_) A.style.height = "0px";
            A.innerHTML = ["<div class=\"gt-inner" + ($.headAlign ? " gt-inner-" + $.headAlign: "") + "\" ", _ ? "style=\"padding-top:0px;padding-bottom:0px;height:1px;\" ": "", "unselectable=\"on\" title=\"" + $.title + "\" >", "<span>", C, "</span>", _ ? "": GT.T_G.hdToolHTML, "</div>"].join("");
            return A
        },
        hdToolHTML: "<div class=\"gt-hd-tool\" ><span class=\"gt-hd-icon\"></span><span class=\"gt-hd-button\"></span><span class=\"gt-hd-split\"></span></div>",
        bodyTableStart: function($, _) {
            return ["<table ", $ ? "id=\"" + $ + "\" ": "", "class=\"gt-table\" cellspacing=\"0\"  cellpadding=\"0\" border=\"0\" >", _ === false ? "": "<tbody>"].join("")
        },
        tableStartHTML: "<table class=\"gt-table\" style=\"margin-left:0px\" cellspacing=\"0\"  cellpadding=\"0\" border=\"0\" ><tbody>",
        tableEndHTML: "</tbody></table>",
        rowStart: function($, A, _) {
            return GT.T_G.rowStartS($, A) + ">\n"
        },
        rowStartS: function(_, A, $) {
            return ["<tr class=\"gt-row", (A % 2 == 0 ? _.evenRowCss: ""), "\" ", GT.Const.DataSet.INDEX, "=\"", A, "\" ", $].join("")
        },
        rowEndHTML: "</tr>\n",
        innerStart: function($) {
            return ["<div class=\"gt-inner " + ($.align ? " gt-inner-" + $.align + " ": "") + "", "\" >"].join("")
        },
        cellStartHTML: "<td ><div class=\"gt-inner\" >",
        cellEndHTML: "</div></td>",
        cell: function($, A, _) {
            return ["<td ", _ || $.cellAttributes, " class=\"" + $.styleClass + "\" >", $.innerStartHTML, A, "</div></td>", ].join("")
        },
        uV4: function($, _) {
            return (GT.Const.Grid.COL_T_CLASSNAME + $.id + "-" + _).toLowerCase()
        },
        freezeBodyCell: function(D, C, _, A) {
            var F = C + D.Ut2,
            $ = C + D.gc3,
            E = "style=\"width:" + $ + "px;\"";
            _ = _ || "&#160;";
            var B = GT.$e("td", {
                style: {
                    width: F + "px"
                },
                innerHTML: "<div class=\"" + (A ? "gt-hd-inner": "gt-inner") + "\" " + E + ">" + _ + "</div>"
            });
            return B
        },
        xm4: function(A, _, $) {
            return this.freezeBodyCell(A, _, $, true)
        }
    },
    Dialog: {
        create: function(C) {
            var $ = C.domId,
            A = C.gridId,
            B = GT.$chk(C.mu8) ? C.mu8: true,
            _ = C.title || "Dialog";
            return ["<div class=\"gt-dialog-head\" >", "<div class=\"gt-dialog-head-icon\">&#160;</div>", "<div id=\"" + $ + "_dialog_title\"  class=\"gt-dialog-head-text\" >" + _ + "</div>", "<div class=\"gt-dialog-head-button\"  >", B ? "<a href=\"#\" onclick=\"GT.$grid('" + A + "').closeDialog();return false;\">&#160;</a>": "", "</div>", "</div><div id=\"" + $ + "_dialog_body\" class=\"gt-dialog-body\"></div>"].join("")
        },
        Hg6: ["<select class=\"gt-input-select\">", "<option value=\"equal\">=</option>", "<option value=\"notEqual\">!=</option>", "<option value=\"less\">&lt;</option>", "<option value=\"great\">></option>", "<option value=\"lessEqual\">&lt;=</option>", "<option value=\"greatEqual\" >>=</option>", "<option value=\"like\" >like</option>", "<option value=\"startWith\">startWith</option>", "<option value=\"endWith\">endWith</option>", "</select>"].join("")
    }
});
GT.T_G = GT.Template.Grid;
GT.T_C = GT.Template.Column;
GT.T_D = GT.Template.Dialog;
if (!window.GT) window.GT = {};
GT_GRID_VER = "GT-Grid 1.42";
GT.WidgetCache = {};
GT.GridCache = {};
GT.GridNum = 0;
GT.activeGrid = null;
GT.$widget = function($) {
    return GT.$type($, "string") ? GT.WidgetCache[$] : $
};
GT.$grid = function($) {
    $ = $ || GT.Const.Grid.DEFAULT_ECG_ID;
    return GT.$type($, "string") ? GT.GridCache[$] : $
};
GT.destroyGrids = function() {
    GT.eachGrid(function($) {
        $.destroy()
    })
};
GT.destroyWidgets = function() {
    for (var _ in GT.WidgetCache) {
        var $ = GT.WidgetCache[_];
        if ($ && $.destroy) $.destroy()
    }
};
GT.eachGrid = function(_) {
    for (var A in GT.GridCache) {
        var $ = GT.GridCache[A];
        _($)
    }
};
GT.GridDefault = {
    id: GT.Const.Grid.DEFAULT_ECG_ID,
    defaultColumnWidth: 70,
    domList: ["gridWaiting", "separateLine", "gridMask", "gridGhost", "gridFormTextarea", "gridFormFileName", "gridFormExportType", "gridForm", "gridIFrame", "activeDialog", "gridDialog", "gridDialogTitle", "gridDialogBody", "gridFilterRSCache", "titleBar", "toolTipDiv", "freezeHeadTable", "freezeHeadDiv", "freezeBodyDiv", "freezeView", "resizeButton", "pageSizeSelect", "pageStateBar", "toolBar", "Ml9", "columnMoveS", "headerGhost", "headFirstRow", "bodyFirstRow", "headTable", "headDiv", "bodyDiv", "gridDiv", "viewport", "container"],
    defaultConst: ["action", "recordType", "exportType", "exportFileName", "exception", "parameters", "queryParameters", "data", "pageInfo", "filterInfo", "sortInfo", "columnInfo", "fieldsName", "insertedRecords", "updatedRecords", "updatedFields", "deletedRecords", "success", "succeedData", "failedData", "remotePaging", "remoteSort", "remoteFilter", "remoteGroup"],
    language: "default",
    skin: "default",
    dataRoot: null,
    dataPageInfo: null,
    otherDataInfo:null,
    dataException: null,
    formid: null,
    isNative: false,
    loadURL: null,
    saveURL: null,
    exportURL: null,
    exportType: null,
    exportFileName: null,
    sortInfo: null,
    editable: true,
    resizable: false,
    showGridMenu: false,
    showIndexColumn: false,
    allowCustomSkin: false,
    allowFreeze: false,
    allowHide: false,
    allowGroup: false,
    allowResizeColumn: true,
    simpleScrollbar: true,
    scrollbarClass: null,
    monitorResize: false,
    readOnly: false,
    stripeRows: true,
    lightOverRow: true,
    evenRowCss: "gt-row-even",
    clickStartEdit: true,
    remotePaging: true,
    remoteSort: false,
    remoteFilter: false,
    remoteGroup: false,
    autoLoad: true,
    submitColumnInfo: true,
    autoUpdateSortState: true,
    autoUpdateEditState: true,
    autoUpdateGroupState: true,
    autoUpdateFreezeState: true,
    autoSelectFirstRow: true,
    autoEditNext: false,
    submitUpdatedFields: false,
    autoSaveOnNav: false,
    reloadAfterSave: true,
    recountAfterSave: true,
    recount: false,
    showEditTool: true,
    showAddTool: true,
    showDelTool: true,
    showSaveTool: true,
    showReloadTool: true,
    showPrintTool: true,
    showFilterTool: true,
    showChartTool: true,
    showPageState: true,
    transparentMask: false,
    justShowFiltered: true,
    toolbarPosition: "bottom",
    toolbarContent: "nav | goto | pagesize | reload | add del save | print | filter chart | state",
    width: "100%",
    height: "100%",
    minWidth: 50,
    minHeight: 50,
    dataRoot: "data",
    custom2Cookie: true,
    multiSort: false,
    multiGroup: false,
    multiSelect: true,
    selectRowByCheck: false,
    html2pdf: true,
    GTGridPath: "../../gt-grid",
    ck2: function() {
        return {
            skinList: [{
                text: this.getMsg("STYLE_NAME_DEFAULT"),
                value: "default"
            },
            {
                text: this.getMsg("STYLE_NAME_CHINA"),
                value: "china"
            },
            {
                text: this.getMsg("STYLE_NAME_VISTA"),
                value: "vista"
            },
            {
                text: this.getMsg("STYLE_NAME_MAC"),
                value: "mac"
            }],
            encoding: null,
            mimeType: null,
            jsonParamName: null,
            title: null,
            lastAction: null,
            ajax: null,
            autoExpandColumn: null,
            autoColumnWidth: false,
            cellWidthPadding: GT.isBoxModel ? 0: 4,
            cellHeightPadding: GT.isBoxModel ? 0: 2,
            Ut2: GT.isBoxModel ? 0: 0,
            gc3: GT.isBoxModel ? 0: (GT.isIE8 ? -1: -4),
            freezeFixH: GT.isBoxModel ? 0: 0,
            freezeFixW: GT.isIE ? -1: -2,
            toolbarHeight: 24,
            el7: 0,
            Ml0: 0,
            freezeColumns: 0,
            pe6: 0,
            defaultRecord: null,
            isWaiting: false,
            Vl4: false,
            requesting: false,
            hasGridDivTemp: false,
            gi4: -1,
            moveColumnDelay: 800,
            mouseDown: false,
            footDiv: null,
            footTable: null,
            footFirstRow: null,
            freezeBodyTable: null,
            titleBar: null,
            nearPageBar: null,
            lastOverHdCell: null,
            toolTipDiv: null,
            gridTable: null,
            overRow: null,
            overRowF: null,
            activeCell: null,
            activeRow: null,
            activeRecord: null,
            activeEditor: null,
            activeDialog: null,
            scrollLeft: 0,
            scrollTop: 0,
            gQ6: 0,
            onComplete: GT.$empty,
            onResize: GT.$empty,
            beforeSelectRow: GT.$empty,
            afterSelectRow: GT.$empty,
            onClickHead: GT.$empty,
            onClickCell: GT.$empty,
            onDblClickCell: GT.$empty,
            beforeEdit: GT.$empty,
            afterEdit: GT.$empty,
            beforeRefresh: GT.$empty,
            beforeExport: GT.$empty,
            beforeSave: GT.$empty,
            beforeLoad: GT.$empty,
            pa5: GT.$empty,
            beforeUpdateDataset: GT.$empty,
            xV0: GT.$empty,
            beforeInsert: GT.$empty,
            afterInsert: GT.$empty,
            beforeUpdate: GT.$empty,
            afterUpdate: GT.$empty,
            beforeDelete: GT.$empty,
            afterDelete: GT.$empty,
            customRowAttribute: GT.$empty,
            editing: false,
            rendered: false,
            isFirstLoad: true,
            customPrintCss: null,
            gridTbodyList: [],
            gridRowList: [],
            gridFreezeRowList: [],
            checkedRows: {},
            rowBegin: 0,
            rowNum: 0,
            rowEnd: 0,
            currentRowNum: 0,
            filterDataProxy: null,
            sf2: null,
            cacheData: [],
            dataset: null,
            selectedRows: [],
            cacheBodyList: [],
            frozenColumnList: [],
            sortedColumnList: [],
            countTotal: true,
            pageSizeList: [],
            tools: {},
            toolCreator: {},
            columns: [],
            columnList: [],
            columnMap: {},
            CONST: null,
            queryParameters: {},
            parameters: {}
        }
    },
    gb7: function() {
        GT.activeGrid = this
    },
    clearCheckedRows: function($) {
        this.checkedRows = {};
        if ($) this.refresh()
    },
    SO5: function(_, H) {
        GT.GridNum++;
        var F = {},
        E = this;
        GT.$each(this.domList, 
        function($, _) {
            E[$] = null
        });
        GT.$each(this.defaultConst, 
        function($, _) {
            F[$] = $
        });
        this.id = "" + _;
        H = H || {};
        if (GT.$type(_, "object")) {
            H = _;
            this.id = "gtgrid_" + GT.GridNum
        }
        GT.$extend(F, H.CONST);
        this.CONST = F;
        GT.$extend(this, H);
        this.gridId = this.id;
        this.rowKey = "__gt_" + this.id + "_r_";
        GT.GridCache[this.id] = this;
        this.legacy();
        if (!this.dataset && this.columns) {
            var D = {
                fields: []
            };
            for (var C = 0; C < this.columns.length; C++) {
                var A = this.columns[C],
                $ = {
                    name: A.name || A.fieldName || A.id,
                    type: A.type,
                    index: (A.fieldIndex || A.fieldIndex === 0) ? A.fieldIndex: null
                };
                D.fields.push($)
            }
            this.dataset = D
        }
        if (this.dataset && !(this.dataset instanceof GT.DataSet)) {
            this.dataset.recordType = this.dataset.recordType || this.recordType;
            this.dataset = new GT.DataSet(this.dataset)
        }
        this.loadURL = this.loadURL || this.dataset.loadURL;
        this.saveURL = this.saveURL || this.dataset.saveURL;
        this.evenRowCss = " " + this.evenRowCss;
        this.toolbarContent = this.toolbarContent === false ? false: this.toolbarContent;
        if (this.toolCreator) for (var G in this.toolCreator) GT.ToolFactroy.register(G, this.toolCreator[G]);
        var B = H.pageInfo || (this.dataset ? this.dataset.pageInfo: null) || {};
        B.pageSize = B.pageSize || H.pageSize || 0;
        if (B.pageSize === 0) delete B.pageSize;
        delete H.pageInfo,
        H.pageSize,
        this.pageInfo,
        this.pageSize;
        this.navigator = new GT.Navigator({
            gridId: this.id,
            pageInfo: B
        })
    },
    legacy: function() {
        var $ = this;
        $.beforeRowSelect = $.beforeRowSelect || $.beforeSelectRow;
        $.afterRowSelect = $.afterRowSelect || $.afterSelectRow;
        $.onCellClick = $.onCellClick || $.onClickCell;
        $.onRowClick = $.onRowClick || $.onClickRow;
        $.onCellDblClick = $.onCellDblClick || $.onDblClickCell;
        $.onRowDblClick = $.onRowDblClick || $.onDblClickRow
    },
    Ue9: function(D) {
        this.columns = D || this.columns;
        if (!this.columns) return;
        this.gridEditorCache = this.gridEditorCache || GT.$e("div", {
            className: "gt-editor-cache"
        });
        var G = this.columns.length,
        E = 0,
        C = true,
        B = {};
        for (var F = 0; F < G; F++) {
            var A = this.columns[F];
            A.grid = this;
            A.gridId = this.id;
            if (A.isCheckColumn) A = GT.Grid.ar6(this, A);
            var _ = new GT.Column(A);
            this.columnMap[_.id] = _;
            this.columnList.push(_);
            _.colIndex = F;
            this.checkColumn = _.isCheckColumn ? _: this.checkColumn;
            if (_.frozen) this.frozenColumnList.push(_.id);
            var $ = this.dataset.fieldsMap[_.fieldName];
            if ($) _.fieldIndex = $.index;
            if (_.editable !== true && _.editable !== false) _.editable = this.editable;
            _.editor = GT.Editor ? GT.Editor.Ol7(_.editor, this) : null;
            if (_.editor && _.editor.AO9()) this.gridEditorCache.appendChild(_.editor.AO9());
            if (_.renderer == "by editor" && _.editor) _.renderer = function(A, _, $, B, D, C) {
                return this.editor.getDisplayValue(A)
            };
            else if (GT.$type(_.renderer, "string")) _.renderer = GT.U.parseExpression(_.renderer, "record", "value,record,col,grid,colNo,rowNo");
            B[_.fieldIndex] = _.newValue || "";
            _.styleClass = GT.T_G.uV4(this, _.id);
            _.innerStyleClass = _.styleClass + " .gt-inner";
            _.minWidth = _.minWidth + this.Ut2;
            _.innerStartHTML = GT.T_G.innerStart(_);
            if (_.sortOrder) this.addSortInfo(this.TG7(_));
            if (_.separator) _.separator.gridId = _.gridId;
            if (_.hidden);
        }
        this.defaultRecord = this.defaultRecord || B;
        return this
    },
    getMsg: function($) {
        var _ = GT.Msg.Grid[this.language][$];
        return _
    },
    kp8: function() {
        if (this.autoSelectFirstRow && !this.selectRowByCheck) this.selectFirstRow();
        this.R_1();
        GT.$invoke(this, "onComplete", [this]);
        this.hideWaiting();
        this.gQ6++
    },
    render: function($) {
        if (!this.rendered) {
            $ = GT.AO9($);
            this.container = $ || this.container;
            this.Ue9();
            this.LG5();
            this.gU1();
            this.xT7();
            this.xo8();
            this.uc3();
            this.tc0();
            this.AD2();
            this.rendered = true
        }
        return this
    },
    getEl: function() {
        return this.gridDiv
    },
    vC1: function() {
        var $ = this;
        this.headDivHeight = this.headDiv.clientHeight;
        if (this.customHead) {
            this.headDiv.style.height = this.headDivHeight - 2 + "px";
            GT.$thread(function() {
                $.headDiv.scrollTop = 2
            });
            this.headDivHeight -= 2
        }
        this.freezeHeadDiv.style.height = this.headDivHeight + "px";
        this.freezeHeadDiv.style.top = 0 + this.freezeFixH + "px";
        this.freezeBodyDiv.style.top = this.headDivHeight + this.freezeFixH + 0 + "px"
    },
    LG5: function() {
        var $ = "display:none;";
        GT.Const.Grid.SCROLLBAR_WIDTH = 20;
        var A = this;
        A.evenRowCss = A.stripeRows ? A.evenRowCss: "";
        var _ = [];
        GT.$each(this.columnList, 
        function(B, D) {
            B.width = B.width || A.defaultColumnWidth;
            var C = "" + B.width;
            if (C.indexOf("px") < 1 && C.indexOf("%") < 1) C = parseInt(C);
            else if (C.indexOf("%") > 0);
            _[D] = [B.CLASS_PREFIX + B.styleClass + " { width:" + (C + A.Ut2) + "px;" + (B.hidden ? $: "") + " } ", B.CLASS_PREFIX + B.innerStyleClass + " { width:" + (C + A.gc3) + "px; } "].join("\n")
        });
        GT.U.CSS.Ro2(_.join("\n"))
    },
    gU1: function() {
        var A = this;
        this.pageStateBar = GT.$(this.pageStateBar);
        if (this.isNative) this.gridDiv = GT.$(this.id + "_div");
        else {
            var B = [(GT.isBoxModel ? "gt-b-ie ": (GT.isSafari ? "gt-b-safari ": (GT.isOpera ? "gt-b-opera ": (GT.isStrict ? "gt-b-strict": "")))), "gt-grid", "gt-skin-" + this.skin];
            this.gridDiv = GT.$e("div", {
                id: this.id + "_div",
                className: B.join(" ")
            });
            this.container = GT.$(this.container);
            if (!this.container || !this.container.appendChild || !this.container.tagName || GT.U.getTagName(this.container) == "BODY") GT.doc.body.appendChild(this.gridDiv);
            else if (this.replaceContainer === true) {
                this.container.parentNode.insertBefore(this.gridDiv, this.container);
                GT.U.removeNode(this.container);
                this.container = null
            } else this.container.appendChild(this.gridDiv);
            this.gridDiv.innerHTML = GT.T_G.main(this)
        }
        this.gridDiv.hideFocus = true;
        this.gridMask = GT.$byId(this.id + "_mask");
        this.gridWaiting = GT.$(this.id + "_waiting");
        this.gridDialog = GT.$(this.id + "_dialog");
        this.gridDialogTitle = GT.$(this.id + "_dialog_title");
        this.gridDialogBody = GT.$(this.id + "_dialog_body");
        this.gridDiv.appendChild(this.gridEditorCache);
        this.gridFilterRSCache = this.gridFilterRSCache || GT.$e("table", {
            className: "gt-filter-rs-cache"
        });
        this.gridDiv.appendChild(this.gridFilterRSCache);
        this.showMask();
        this.viewport = GT.$(this.id + "_viewport");
        this.Ml9 = GT.$(this.id + "_toolBarBox");
        this.headDiv = GT.$(this.id + "_headDiv");
        this.bodyDiv = GT.$(this.id + "_bodyDiv");
        this.freezeView = GT.$(this.id + "_freezeView");
        this.freezeHeadDiv = GT.$(this.id + "_freeze_headDiv");
        this.freezeBodyDiv = GT.$(this.id + "_freeze_bodyDiv");
        this.LM4();
        this.separateLine = GT.$(this.id + "_separateLine");
        this.el7 = this.toolbarPosition == "top" || this.toolbarPosition == "t" ? this.toolbarHeight + (GT.isBoxModel ? 0: 1) : 0;
        if (this.separateLine) this.separateLine.style.top = this.el7 + "px";
        this.columnMoveS = GT.$(this.id + "_columnMoveS");
        this.headerGhost = GT.$(this.id + "_headerGhost");
        if (this.Ml9) {
            this.toolBar = GT.$e("div", {
                id: this.id + "_toolBar",
                className: "gt-toolbar"
            });
            this.Ml9.appendChild(this.toolBar)
        }
        var _ = "" + this.width,
        $ = "" + this.height;
        this.setSize(_, $, true);
        this.showWaiting();
        this.syncLeftTop()
    },
    syncLeftTop: function() {
        this.left = GT.U.getPosLeftTop(this.gridDiv);
        this.top = this.left[1];
        this.left = this.left[0];
        this.UQ0 = GT.U.getXY(this.viewport)
    },
    setSize: function(A, $, D) {
        var C = this,
        B = [this.gridDiv.offsetWidth, this.gridDiv.offsetHeight];
        A = "" + A;
        $ = "" + $;
        this.width = A;
        this.height = $;
        if (A.toLowerCase() == "auto") this.width = B[0] + "px";
        else if (A.indexOf("%") < 1 && A.indexOf("px") < 1) this.width = GT.U.parseInt(A) + "px";
        if ($.toLowerCase() == "auto") this.height = B[1] + "px";
        else if ($.indexOf("%") < 1 && $.indexOf("px") < 1) this.height = GT.U.parseInt($) + "px";
        var _ = false;
        if (($.indexOf("%") > 1 || A.indexOf("%") > 1) && this.monitorResize) _ = true;
        if (_) if (GT.isIE) this.gridDiv.style.overflowY = "hidden";
        else if (GT.isOpera) this.gridDiv.style.overflow = "hidden";
        C.Ci9(D)
    },
    Ci9: function(A) {
        this.gridMask.style.width = this.width;
        this.gridMask.style.height = this.height;
        this.gridDiv.style.width = this.width;
        this.gridDiv.style.height = this.height;
        var B = ("" + this.height).indexOf("%") > 0 ? this.gridDiv.clientHeight: parseInt(this.height),
        _ = 0 - (GT.isBoxModel ? 2: 3);
        this.bodyDiv.style.height = B - (this.headDivHeight + this.toolbarHeight) + _ + "px";
        if (GT.isOpera) {
            var $ = this.gridDiv.clientWidth + _ + "px";
            this.viewport.style.width = $;
            if (this.Ml9) this.Ml9.style.width = $
        }
        if (this.freezeBodyDiv) this.freezeBodyDiv.style.height = this.bodyDiv.clientHeight + "px";
        if (A !== true) this.onResize()
    },
    xT7: function() {
        GT.U.createElementFromHTML(GT.T_G.formIFrame(this), GT.doc.body);
        this.gridForm = GT.$(this.id + "_export_form");
        this.gridFormTextarea = GT.$(this.id + "_export_form_textarea");
        this.gridFormFileName = GT.$(this.id + "_export_filename");
        this.gridFormExportType = GT.$(this.id + "_export_exporttype");
        this.gridIFrame = GT.$(this.id + "_export_iframe")
    },
    xo8: function() {
        this.gridGhost = GT.$e("div", {
            id: this.id + "_gridGhost",
            className: "gt-grid-ghost-rect"
        });
        this.gridGhost.style.top = this.top + "px";
        this.gridGhost.style.left = this.left + "px";
        this.gridGhost.style.width = this.gridMask.style.width;
        this.gridGhost.style.height = this.gridMask.style.height;
        GT.doc.body.appendChild(this.gridGhost)
    },
    LM4: function() {
        var B = this,
        E;
        if (this.customHead) {
            if (GT.$type(this.customHead, "string")) if (this.customHead.indexOf("<table") === 0) {
                GT.U.Kk4.innerHTML = this.customHead;
                this.customHead = GT.U.Kk4.firstChild
            } else this.customHead = GT.$(this.customHead);
            this.customHead.style.display = "";
            this.headTable = GT.$e(this.customHead, {
                id: this.id + "_headTable",
                className: "gt-head-table",
                cellSpacing: "0",
                cellPadding: "0",
                border: "0"
            });
            this.headTbody = this.headTable.tBodies[0];
            for (var H = 0; H < this.headTbody.rows.length; H++) {
                var G = this.headTbody.rows[H];
                G.className = "gt-hd-row";
                for (var D = 0; D < G.cells.length; D++) {
                    var A = G.cells[D],
                    F = A.innerHTML,
                    C = A.getAttribute("columnId");
                    if (C) {
                        A.columnId = C;
                        var $ = this.columnMap[C];
                        if (String(A.getAttribute("resizable")).toLowerCase() == "false") $.resizable = false;
                        if (String(A.getAttribute("sortable")).toLowerCase() == "false") $.sortable = false;
                        if (A.colSpan < 2) A.className = $.styleClass;
                        $.headCell = A
                    }
                    A.innerHTML = ["<div class=\"gt-inner", A.rowSpan < 2 ? "": " gt-inner-tall2", "\" unselectable=\"on\" >", "<span>", F, "</span>", C ? GT.T_G.hdToolHTML: "", "</div>"].join("")
                }
            }
            E = GT.$e("tr", {
                className: "gt-hd-row" + (this.customHead ? " gt-hd-hidden": "")
            });
            GT.$each(this.columnList, 
            function($, _) {
                var A = GT.T_G.UY2(B, $, true);
                E.appendChild(A);
                GT.Grid.Qe7(B, $)
            });
            this.headTbody.insertBefore(E, this.headTbody.rows[0])
        } else {
            this.headTable = GT.$e("table", {
                id: this.id + "_headTable",
                className: "gt-head-table",
                cellSpacing: "0",
                cellPadding: "0",
                border: "0"
            });
            this.headTbody = GT.$e("tbody");
            this.headTable.appendChild(this.headTbody);
            E = GT.$e("tr", {
                className: "gt-hd-row"
            });
            this.headTbody.appendChild(E);
            GT.$each(this.columnList, 
            function($, _) {
                var A = GT.T_G.UY2(B, $);
                E.appendChild(A);
                $.headCell = A;
                GT.Grid.Qe7(B, $)
            },
            this)
        }
        this.headTable.style.marginRight = 100 + "px";
        var _ = this.headDiv.firstChild ? String(GT.U.getTagName(this.headDiv.firstChild)) : null;
        if (_ == "DIV" || _ == "SPAN") this.headDiv.firstChild.appendChild(this.headTable);
        else this.headDiv.appendChild(this.headTable);
        this.headFirstRow = this.headTbody.rows[0];
        this.freezeHeadTable = GT.$e("table", {
            id: this.headTable.id + "_freeze",
            className: "gt-head-table",
            cellSpacing: "0",
            cellPadding: "0",
            border: "0"
        });
        this.freezeHeadTable.appendChild(GT.$e("tbody"));
        this.freezeHeadTable.style.height = "100%";
        this.freezeHeadDiv.appendChild(this.freezeHeadTable);
        this.vC1()
    },
    AD2: function() {
        var $ = GT.$(this.id + "_bodyTable");
        if ($) {
            this.gridTable = $;
            this.gridTbodyList.push($.tBodies[0]);
            this.bodyFirstRow = this.oP2()
        } else this.Rn5()
    },
    getColumn: function($) {
        if (GT.U.isNumber($) && $ >= 0) return this.columnList[$];
        else if (GT.U.getTagName($) == "TD") return this.columnList[GT.U.getCellIndex($)];
        else if (GT.$type($) == "object") return $;
        else return this.columnMap[$]
    },
    getDisplayColumns: function(A) {
        var B = [];
        for (var _ = 0; _ < this.columnList.length; _++) {
            var $ = this.columnList[_];
            if ($.hidden !== (A !== false)) B.push($)
        }
        return B
    },
    NG6: function(C, _) {
        C = this.qV6(C);
        _ = _ || this.getRecordByRow(C);
        var B = C.getAttribute(GT.Const.DataSet.INDEX) / 1;
        this.dataset.initValues(_, B, this.dataset);
        for (var E = 0; E < C.cells.length; E++) {
            var $ = this.getColumn(E);
            if ($ != this.activeColumn && $.syncRefresh === false) continue;
            var A = C.cells[E],
            D = false;
            A.firstChild.innerHTML = $.renderer(_[$.fieldIndex], _, $, this, E, B)
        }
    },
    scrollGrid: function(I, H) {
        var C = this.Ml0,
        _ = this.freezeBodyDiv.clientWidth,
        $ = this.activeCell.offsetLeft + ((GT.isFF2 || GT.isFF1) ? 0: C),
        B = $ + this.activeCell.offsetWidth,
        F = this.activeCell.offsetTop,
        D = F + this.activeCell.offsetHeight,
        A = this.bodyDiv.scrollLeft,
        E = this.bodyDiv.scrollTop,
        J = A + this.bodyDiv.clientWidth + C,
        G = E + this.bodyDiv.clientHeight;
        if (GT.$chk(I)) this.bodyDiv.scrollLeft = I;
        else if ($ <= A + _) this.bodyDiv.scrollLeft = $ - _ - (_ > 0 ? 1: 0);
        else if (B >= J) this.bodyDiv.scrollLeft = A + B - J + C;
        if (GT.$chk(H)) this.bodyDiv.scrollTop = H;
        else if (F <= E) this.bodyDiv.scrollTop = F;
        else if (D >= G) this.bodyDiv.scrollTop = E + D - G
    },
    de3: function($) {
        this.activeCell = $ = ($ || this.activeCell);
        this.activeRow = $ ? $.parentNode: null;
        this.activeColumn = this.getColumn($);
        this.activeEditor = this.activeColumn ? this.activeColumn.editor: null;
        this.activeRecord = this.getRecordByRow(this.activeRow)
    },
    Gk4: function(_) {
        var $ = GT.U.prevElement(_);
        while ($ && $.offsetWidth < 1) $ = GT.U.prevElement($);
        if (!$) {
            $ = GT.U.prevElement(_.parentNode);
            if ($) $ = $.cells[$.cells.length - 1]
        }
        while ($ && $.offsetWidth < 1) $ = GT.U.prevElement($);
        return $
    },
    nW3: function(_) {
        var $ = GT.U.nextElement(_);
        while ($ && $.offsetWidth < 1) $ = GT.U.nextElement($);
        if (!$) {
            $ = GT.U.nextElement(_.parentNode);
            if ($) $ = $.cells[0]
        }
        while ($ && $.offsetWidth < 1) $ = GT.U.nextElement($);
        return $
    },
    GB5: function(D) {
        var A = this.activeCell,
        $ = null,
        F = D.keyCode,
        E = this;
        function B($) {
            if ($) {
                E.endEdit();
                GT.U.stopEvent(D);
                GT.Grid.Mc7(D, E, $.parentNode);
                E.Mr2(D, $)
            }
        }
        if (F == GT.Const.Key.ESC) {
            if (this.endEdit() === false) return;
            else GT.U.stopEvent(D)
        } else if (F == GT.Const.Key.ENTER) {
            var G = GT.U.getEventTarget(D);
            if (this.editing && GT.U.getTagName(G) == "TEXTAREA") return;
            GT.U.stopEvent(D);
            if (this.editing) {
                if (!this.autoEditNext) {
                    this.endEdit();
                    return
                }
                $ = this.nW3(A);
                B($)
            } else {
                this.de3($);
                this.startEdit()
            }
        } else if (this.editing && F == GT.Const.Key.TAB && D.shiftKey) {
            $ = this.Gk4(A);
            B($)
        } else if (this.editing && F == GT.Const.Key.TAB) {
            $ = this.nW3(A);
            B($)
        } else if (A && !this.editing) {
            switch (F) {
            case GT.Const.Key.LEFT:
            case GT.Const.Key.TAB:
                if (F == GT.Const.Key.LEFT || D.shiftKey) {
                    $ = this.Gk4(A);
                    while (this.isGroupRow($)) $ = this.Gk4($);
                    break
                }
            case GT.Const.Key.RIGHT:
                $ = this.nW3(A);
                while (this.isGroupRow($)) $ = this.nW3($);
                break;
            case GT.Const.Key.DOWN:
                $ = GT.U.nextElement(A.parentNode);
                while (this.isGroupRow(null, $)) $ = GT.U.nextElement($);
                if ($) $ = $.cells[GT.U.getCellIndex(A)];
                break;
            case GT.Const.Key.UP:
                $ = GT.U.prevElement(A.parentNode);
                while (this.isGroupRow(null, $)) $ = GT.U.prevElement($);
                if ($) $ = $.cells[GT.U.getCellIndex(A)];
                break
            }
            if ($) {
                GT.U.stopEvent(D);
                var C = A.parentNode,
                _ = $.parentNode;
                this.Rm8(_, D);
                GT.Grid.Mc7(D, this, _);
                this.nh4(D, $)
            }
        }
    },
    startEdit: function() {
        if (!this.readOnly && this.activeCell && this.activeEditor && (this.activeColumn.editable || this.isInsertRow(this.activeRow)) && GT.$invoke(this.activeColumn, "beforeEdit", [this.activeValue, this.activeRecord, this.activeColumn, this]) !== false && GT.$invoke(this, "beforeEdit", [this.activeValue, this.activeRecord, this.activeColumn, this]) !== false && !this.isDelRow(this.activeRow)) {
            this.editing = true;
            this.hE3(this.activeValue, this.activeRecord)
        }
    },
    endEdit: function() {
        if (this.activeEditor && this.activeEditor.locked === true || (this.activeDialog != this.activeEditor) && this.activeDialog && !this.activeDialog.hidden) return false;
        if (this.activeCell && this.activeEditor && (this.activeColumn.editable || this.isInsertRow(this.activeRow))) {
            this.of0();
            this.editing = false;
            this.ry2(null, this.activeCell)
        }
    },
    hE3: function(B, $) {
        var A = this.activeCell,
        _ = this.bodyDiv,
        C = this.Ml0;
        if (this.activeColumn.frozen) {
            A = this.QC1(this.activeCell)[1];
            _ = this.freezeBodyDiv;
            C = 0
        }
        if (this.activeEditor && this.activeEditor instanceof GT.Dialog);
        else _.appendChild(this.activeEditor.AO9());
        this.activeEditor.show();
        this.activeEditor.setValue(B, $);
        if (this.activeEditor !== this.activeDialog) {
            this.activeEditor.Au8(((GT.isFF2 || GT.isFF1) ? 0: C) + A.offsetLeft, A.offsetTop);
            this.activeEditor.setSize(A.offsetWidth, A.offsetHeight)
        }
        this.activeEditor.active()
    },
    validValue: function($, B, _, A) {
        if ($.editor) {
            var C = $.editor.us5(B, _, $, this);
            if (C !== true) this.nt4(B, C, A, $);
            return C
        }
        return true
    },
    of0: function() {
        if (this.editing) {
            var B = this.activeRow,
            _ = this.activeValue,
            $ = this.activeEditor.parseValue(this.activeEditor.getValue()),
            A = this.validValue(this.activeColumn, $, this.activeRecord, this.activeCell);
            if (A === true && String(this.activeValue) !== String($)) {
                this.updateRecordField(this.activeCell, $);
                this.NG6(B, this.activeRecord);
                this.dirty(this.activeCell);
                this.activeValue = $
            }
            GT.$invoke(this.activeColumn, "afterEdit", [$, _, this.activeRecord, this.activeColumn, this]);
            GT.$invoke(this, "afterEdit", [$, _, this.activeRecord, this.activeColumn, this])
        }
        if (this.activeEditor && this.activeEditor instanceof GT.Dialog);
        else this.gridEditorCache.appendChild(this.activeEditor.AO9());
        this.activeEditor.hide()
    },
    nt4: function(B, C, A, $) {
        var _ = this.QC1(A);
        GT.U.addClass(_[0], "gt-cell-vaildfailed");
        GT.U.addClass(_[1], "gt-cell-vaildfailed");
        C = [].concat(C);
        alert(C.join("\n") + "\n\n" + B);
        GT.$thread(function() {
            GT.U.removeClass(_[0], "gt-cell-vaildfailed");
            GT.U.removeClass(_[1], "gt-cell-vaildfailed")
        },
        1500)
    },
    Yi4: function(B) {
        var _ = B,
        A = B.rowIndex,
        $ = !_.id ? this.gridTbodyList[0] : this.freezeBodyTable.tBodies[0];
        row2 = $ ? $.rows[A] : null;
        if (!row2 && $ && $.parentNode.tFoot) row2 = $.parentNode.tFoot.rows[A - $.rows.length];
        return _.id ? [_, row2, A] : [row2, _, A]
    },
    QC1: function(_, E) {
        var A = _,
        D = GT.U.getCellIndex(_),
        $ = null,
        B = 0,
        C = _.parentNode;
        E = E || this.Yi4(C);
        if (E[1] == C) {
            B = D - (this.showIndexColumn ? 1: 1);
            return [E[0] ? E[0].cells[B] : null, _, D]
        }
        B = D + (this.showIndexColumn ? 1: 1);
        return [_, E[1] ? E[1].cells[B] : null, D]
    },
    ry2: function(C, A) {
        if (!C && !A) return;
        C = C || A.parentNode;
        var D = this.Yi4(C),
        B = D[1],
        C = D[0];
        if (B && C) {
            B.className = C.className;
            GT.U.removeClass(B, "gt-row-over")
        }
        if (A) {
            var $ = this.QC1(A, D),
            _ = $[1],
            A = $[0];
            if (_ && A) {
                _.className = A.className;
                if (_.getElementsByTagName("input")[0]) A.innerHTML = _.innerHTML;
                else _.innerHTML = A.innerHTML
            }
        }
    },
    nh4: function(_, A) {
        A = A || GT.U.LG4("td", null, _);
        if (A && !this.isGroupRow(A)) {
            this.closeGridMenu();
            var $ = A != this.activeCell;
            if ($) {
                GT.U.removeClass(this.activeCell, "gt-cell-actived" + (this.activeEditor ? "-editable": ""));
                this.ry2(null, this.activeCell)
            }
            this.de3(A);
            $ && GT.U.addClass(this.activeCell, "gt-cell-actived" + (this.activeEditor ? "-editable": ""));
            if (this.activeColumn && this.activeRecord) this.activeValue = this.activeRecord[this.activeColumn.fieldIndex];
            this.scrollGrid();
            this.ry2(null, this.activeCell)
        } else this.bo8();
        return A
    },
    Mr2: function(_, $, A) {
        if (this.rowNum < 1) return;
        var B = this.activeCell;
        this.nh4(_, $);
        if (this.activeEditor && (this.clickStartEdit || B && B == this.activeCell)) {
            GT.U.stopEvent(_);
            this.de3($);
            this.startEdit()
        }
    },
    getLastSelectRow: function() {
        return this.selectedRows[this.selectedRows.length - 1]
    },
    bo0: function($) {
        GT.$invoke(this, "onCellSelect", [this.activeValue, this.activeRecord, $.cell, $.row, $.colNo, $.rowNo, $.column, this])
    },
    Rm8: function(C, A) {
        if (!C || GT.U.hasClass(C.cells[0], "gt-nodata-cell")) return;
        if (this.multiSelect && A.shiftKey) {
            var $ = this.getLastSelectRow();
            if ($ && $.parentNode == C.parentNode) {
                this.unselectAllRow();
                var _ = $.rowIndex > C.rowIndex ? "prevElement": "nextElement";
                while ($ && $ != C) {
                    this.selectRow($);
                    $ = GT.U[_]($)
                }
                this.selectRow(C);
                return C
            }
        }
        var B = A.ctrlKey;
        if (GT.Grid.isSelectedRow(C)) {
            if (this.multiSelect && B !== true && this.selectedRows.length > 1) {
                this.unselectAllRow();
                this.selectRow(C)
            } else if (B) this.unselectRow(C)
        } else {
            if (!this.multiSelect || B !== true) this.unselectAllRow();
            this.selectRow(C)
        }
        return C
    },
    Yd4: function(_, A, $) {
        GT.$invoke(this, "onCellClick", [$.value, $.record, $.cell, $.row, $.colNo, $.rowNo, $.column, this, _])
    },
    ee4: function(_, A, $) {
        GT.$invoke(this, "onCellDblClick", [$.value, $.record, $.cell, $.row, $.colNo, $.rowNo, $.column, this, _])
    },
    Tq3: function(_, A, $) {
        GT.$invoke(this, "onRowClick", [$.value, $.record, $.cell, $.row, $.colNo, $.rowNo, $.column, this, _])
    },
    nf3: function(_, A, $) {
        GT.$invoke(this, "onRowDblClick", [$.value, $.record, $.cell, $.row, $.colNo, $.rowNo, $.column, this, _])
    },
    tf2: function(A, D, _, $) {
        this.endEdit();
        this.gb7();
        var C = $.cell;
        if (!C || C == _) return;
        C = this.QC1(C)[0];
        if(typeof(C)=='undefined'){
    		return;
    	}
        var F = C.parentNode,
        E = $.eventTarget,
        B = (GT.U.getTagName(E) == "INPUT" && E.className == "gt-f-check");
        if (B) GT.cf6(E, this);
        this.bo0(C, A);
        if (!this.selectRowByCheck) this.Rm8(F, A);
        if (D) {
            this.ee4(A, C, $);
            this.nf3(A, F, $)
        } else {
            this.Yd4(A, C, $);
            this.Tq3(A, F, $)
        }
        if (!GT.U.hasClass(C, "gt-index-col")) this.Mr2(A, C, D);
        this.ry2(null, C)
    },
    clickCount: 0,
    clickInterval: 500,
    Se5: function(A, B, _) {
        var C = this;
        if (_ == C.bodyDiv) {
            if (B == "scroll") {
                C.gb7();
                C.closeGridMenu();
                C.syncScroll();
                return
            }
        } else if (_ == C.freezeBodyDiv);
        var $ = this.getEventTargets(A);
        if (C.lightOverRow && B == "mousemove") GT.Grid.mt4(A, C, _);
        switch (B) {
        case "contextmenu":
            break;
        case "dblclick":
            this.clickCount = 0;
            return C.tf2(A, true, _, $);
        case "click":
            GT.$thread(function() {
                C.clickCount = 0
            },
            C.clickInterval);
            this.clickCount++;
            return C.tf2(A, false, _, $);
        case "mousemove":
        default:
            B = (B.indexOf("on") != 0 ? "on" + B: B).toLowerCase();
            return GT.$invoke(C, B, [A, C, $, B, _])
        }
    },
    rN3: function($, _) {
        var A = this;
        GT.U.addEvent($, _, 
        function(B) {
            A.Se5(B, _, $)
        })
    },
    getEventTargets: function(A, _) {
        _ = _ || GT.U.getEventTarget(A);
        var F = null,
        G = -1,
        E = -1,
        D = null,
        $ = null,
        C = null,
        B = GT.U.LG4("td", _, A);
        if (B) {
            F = B.parentNode;
            G = GT.U.getCellIndex(B);
            E = F.rowIndex;
            D = this.columnList[G];
            $ = this.getRecordByRow(F) || {};
            C = $[D.fieldIndex]
        }
        return {
            cell: B,
            row: F,
            colNo: G,
            rowNo: E,
            column: D,
            record: $,
            value: C,
            eventTarget: _
        }
    },
    tc0: function() {
        var _ = this;
        GT.gO9();
        if (_.monitorResize) {
            GT.U.addEvent(window, "resize", 
            function($) {
                _.Ci9()
            });
            _.hasResizeListener = true
        }
        GT.U.addEvent(_.gridDiv, "mousedown", 
        function($) {
            _.gb7()
        });
        _.rN3(_.bodyDiv, "scroll");
        _.rN3(_.bodyDiv, "click");
        _.rN3(_.bodyDiv, "dblclick");
        _.rN3(_.bodyDiv, "contextmenu");
        _.rN3(_.freezeBodyDiv, "click");
        _.rN3(_.freezeBodyDiv, "dblclick");
        GT.U.addEvent(_.headDiv, "selectstart", 
        function($) {
            GT.U.stopEvent($);
            return false
        });
        _.rN3(_.bodyDiv, "mouseover");
        _.rN3(_.bodyDiv, "mouseout");
        _.rN3(_.bodyDiv, "mousemove");
        _.rN3(_.freezeBodyDiv, "mousemove");
        function $($) {
            $ = $ || window.event;
            var A = GT.U.LG4("td", null, $);
            if (A) GT.U.addClass(A, "gt-hd-row-over");
            if (_.lastOverHdCell != A) GT.U.removeClass(_.lastOverHdCell, "gt-hd-row-over");
            _.lastOverHdCell = A
        }
        GT.U.addEvent(_.headTable, "mousemove", $);
        GT.U.addEvent(_.freezeHeadTable, "mousemove", $)
    },
    showCellToolTip: function(_, $) {
        if (!this.toolTipDiv) {
            this.toolTipDiv = GT.$e("div", {
                className: "gt-cell-tooltip gt-breakline"
            });
            this.toolTipDiv.style.display = "none"
        }
        this.toolTipDiv.innerHTML = GT.$getText(_);
        this.gridDiv.appendChild(this.toolTipDiv);
        this.toolTipDiv.style.left = _.offsetLeft + this.bodyDiv.offsetLeft - this.bodyDiv.scrollLeft + ((GT.isFF2 || GT.isFF1) ? 0: this.Ml0) + "px";
        this.toolTipDiv.style.top = _.offsetTop + _.offsetHeight + this.bodyDiv.offsetTop - this.bodyDiv.scrollTop + this.el7 + (GT.isFF ? 1: 0) + "px";
        $ && (this.toolTipDiv.style.width = $ + "px");
        this.toolTipDiv.style.display = "block"
    },
    hideCellToolTip: function() {
        if (this.toolTipDiv) {
            this.toolTipDiv.style.display = "none";
            this.gridEditorCache.appendChild(this.toolTipDiv);
            this.toolTipDiv.innerHTML = ""
        }
    },
    addParameters: function(_, $) {
        this.parameters = GT.U.add2Map(_, $, this.parameters)
    },
    setParameters: function($) {
        this.parameters = $
    },
    cleanParameters: function() {
        this.parameters = {}
    },
    setQueryParameters: function($) {
        this.queryParameters = $
    },
    cleanQueryParameters: function() {
        this.queryParameters = {}
    },
    addQueryParameter: function(_, $) {
        this.queryParameters = GT.U.add2Map(_, $, this.queryParameters)
    },
    removeQueryParameter: function(_) {
        var $ = this.queryParameters[_];
        this.queryParameters[_] = undefined;
        delete this.queryParameters[_];
        return $
    },
    exceptionHandler: function(_, $) {
        alert($ + "\n\n" + _)
    },
    getInsertedRecords: function() {
        return GT.$array(this.dataset.insertedRecords)
    },
    getUpdatedRecords: function() {
        return GT.$array(this.dataset.updatedRecords)
    },
    getUpdatedFields: function() {
        return GT.$array(this.dataset.updatedFields)
    },
    getDeletedRecords: function() {
        return GT.$array(this.dataset.deletedRecords)
    },
    getColumnInfo: function() {
        var $ = [];
        for (var B = 0; B < this.columnList.length; B++) {
            var A = this.columnList[B],
            _ = {
                id: A.id,
                header: A.header || A.title,
                fieldName: A.fieldName,
                fieldIndex: A.fieldIndex,
                sortOrder: A.sortOrder,
                hidden: A.hidden,
                exportable: A.exportable,
                printable: A.printable,
                exportnumber:A.exportnumber||false
            };
            $.push(_)
        }
        return $
    },
    getSaveParam: function($) {
        $ = $ || {};
        $[this.CONST.fieldsName] = this.dataset.fieldsName;
        $[this.CONST.recordType] = this.dataset.getRecordType();
        $[this.CONST.parameters] = this.parameters;
        this.submitUpdatedFields && ($[this.CONST.updatedFields] = this.getUpdatedFields());
        return $
    },
    getLoadParam: function($) {
        $ = $ || {};
        $[this.CONST.recordType] = this.dataset.getRecordType();
        $[this.CONST.pageInfo] = this.getPageInfo(true);
        this.submitColumnInfo && ($[this.CONST.columnInfo] = this.getColumnInfo());
        $[this.CONST.sortInfo] = this.dt1();
        $[this.CONST.filterInfo] = this.ee7();
        $[this.CONST.remotePaging] = this.remotePaging;
        $[this.CONST.parameters] = this.parameters;
        if (this.recount) $[this.CONST.pageInfo].totalRowNum = -1;
        return $
    },
    Sr3: function(F, _, E, D, B) {
        var C = this;
        C.requesting = true;
        var A = _[C.CONST.action];
        if (F) {
            try {
                C.ajax = new GT.Ajax(F);
                C.ajax.encoding = C.encoding || C.ajax.encoding;
                C.ajax.method = C.ajaxMethod || C.ajax.method;
                C.ajax.mimeType = C.mimeType || C.ajax.mimeType;
                C.ajax.jsonParamName = C.jsonParamName || C.ajax.jsonParamName;
                C.ajax.onSuccess = D || GT.$empty;
                C.ajax.onFailure = B || GT.$empty;
                C.ajax.setQueryParameters(C.queryParameters);
                C.ajax.send({
                    data: _
                })
            } catch($) {
                B({
                    status: "Exception " + $.message
                })
            }
        } else B({
            status: "url is null"
        })
    },
    save: function(N) {
        if (this.endEdit() === false) return;
        var E = this.saveURL,
        I = this.getInsertedRecords(),
        J = this.getUpdatedRecords(),
        $ = this.getDeletedRecords(),
        A = (I != null && I.length > 0 || J != null && J.length > 0 || $ != null && $.length > 0);
        if (!N && !A) alert(this.getMsg("NO_MODIFIED"));
        else {
            var F = this.gridTable.tFoot ? this.gridTable.tFoot.rows: [];
            for (var G = 0, D = I.length; G < D; G++) {
                var _ = I[G];
                for (var K = 0; K < this.columnList.length; K++) {
                    var H = this.columnList[K];
                    if (H.editor) {
                        var L = _[H.fieldIndex],
                        C = F[G] ? F[G].cells[H.colIndex] : null;
                        if (this.validValue(H, L, _, C) !== true) return false
                    }
                }
            }
            var B = this.getSaveParam();
            B[this.CONST.action] = "save";
            B[this.CONST.insertedRecords] = I;
            B[this.CONST.updatedRecords] = J;
            B[this.CONST.deletedRecords] = $;
            if (GT.$invoke(this, "beforeSave", [B, this]) !== false) {
                this.showWaiting();
                var M = this;
                return this.Sr3(E, B, "json", 
                function($) {
                    return function(_) {
                        M.cM1(_, B, $)
                    }
                } (N), 
                function(A, _) {
                    var $ = {};
                    $[M.CONST.exception] = " XMLHttpRequest Status : " + A.status;
                    M.saveFailure($);
                    M.hideWaiting()
                })
            }
        }
        if (N === true) this.load();
        return false
    },
    load: function(B, D) {
        var C = this,
        E = this.loadURL,
        A = (!this.autoLoad && !this.rendered);
        if (A) {
            C.hideWaiting();
            C.hideFreezeZone();
            return
        }
        this.remotePaging = this.remotePaging === false ? false: !!E;
        var _ = this.getLoadParam();
        if (B === true) _[this.CONST.pageInfo].totalRowNum = -1;
        _[this.CONST.action] = "load";
        if (GT.$invoke(this, "beforeLoad", [_, this]) !== false) {
            if (!E || (D !== true && this.remotePaging === false && !this.isFirstLoad)) {
                C.requesting = true;
                C.GH0(function() {
                    var _ = {};
                    _[C.dataRoot] = C.dataset.data || [];
                    var A = C.getPageInfo(),
                    $ = C.dataset.getSize();
                    A.totalRowNum = $ > 0 ? $: 0;
                    _[C.CONST.pageInfo] = A;
                    return _
                } (C), _);
                return
            }
            this.showWaiting();
            var $ = this.Sr3(E, _, "json", 
            function($) {
                C.GH0($, _)
            },
            function(A, _) {
                var $ = {};
                $[C.CONST.exception] = " XMLHttpRequest Status : " + A.status;
                C.loadFailure($);
                C.hideWaiting()
            });
            this.isFirstLoad = false;
            return $
        } else C.hideWaiting();
        return false
    },
    query: function($) {
        this.setQueryParameters($);
        this.lastAction = "query";
        this.reload(true, true)
    },
    Rn5: function(_, $) {
        GT.$chk(_) && (this.getPageInfo().pageNum = _);
        if (this.autoSaveOnNav) this.save(true);
        else this.load()
    },
    reload: function($, _) {
        if (_ !== false || !this.dataset || this.dataset.getSize() < 0) this.load($, true);
        else this.refresh()
    },
    refresh: function(_) {
        if (this.dataset && _) this.dataset.Ry1(_);
        var A = this,
        B = A.scrollLeft,
        $ = A.scrollTop;
        if (A.remotePaging === false) A.dataset.startRecordNo = (A.getPageInfo().startRowNum || 1) - 1;
        function C() {
            if (GT.$invoke(A, "beforeRefresh", [A]) !== false) {
                A.OE7(); ! A.remoteSort && A.kG1();
                A.VT9();
                A.autoUpdateSortState && A.updateSortState();
                A.sorting = false;
                A.autoUpdateEditState && A.updateEditState();
                A.updateCheckState();
                A.autoUpdateFreezeState && A.yM1();
                A.refreshToolBar();
                A.syncScroll(B, $);
                GT.$invoke(A, "afterRefresh", [A]);
                A.kp8()
            }
        }
        GT.$thread(C)
    },
    cM1: function($, _, D) {
        var B = this.nI9($, _);
        if (this.requesting) {
            var A = {};
            GT.$extend(A, B);
            this.requesting = false;
            var C = !(A[this.CONST.success] === false || A[this.CONST.success] === "false");
            if (C) this.saveSuccess(A, D);
            else this.saveFailure(A);
            this.hideWaiting();
            GT.$invoke(this, "afterSave", [A, C, this])
        }
    },
    saveSuccess: function($, _) {
        this.dataset.cleanModifiedData(true);
        if (this.reloadAfterSave || this.autoSaveOnNav && _) {
            if (this.recountAfterSave) this.getPageInfo().totalRowNum = -1;
            else if ($[this.CONST.pageInfo]) this.getPageInfo().totalRowNum = $[$.CONST.pageInfo].totalRowNum || this.getPageInfo().totalRowNum;
            this.reload()
        }
    },
    loadSuccess: function($) {
        this.setContent($)
    },
    cleanContent: function() {
        this.setContent({
            data: [],
            pageInfo: {
                pageNum: 1,
                totalPageNum: 1,
                totalRowNum: 0,
                startRowNum: 0
            }
        })
    },
    setContent: function(_) {
    	this.otherDataInfo = _.otherDataInfo
        var $ = this.getPageInfo();
        if (GT.$type(_, "array")) _[this.dataRoot] = _;
        else {
            _[this.CONST.pageInfo] = _[this.dataPageInfo || this.CONST.pageInfo];
            if (_[this.CONST.recordType]) this.dataset.Up3(_[this.CONST.recordType]);
            if (_[this.CONST.pageInfo]) GT.$extend($, _[this.CONST.pageInfo]);
            $.totalRowNum = _.totalRowNum || $.totalRowNum
        }
        if (_[this.dataRoot] && GT.$invoke(this, "beforeDatasetUpdate", [_[this.dataRoot]]) !== false) {
            $.totalRowNum = $.totalRowNum || _[this.dataRoot].length;
            this.refresh(_[this.dataRoot])
        } else this.refresh()
    },
    GH0: function($, _) {
        var B = this.nI9($, _);
        if (this.requesting) {
            var A = {};
            GT.$extend(A, B);
            if (A[this.CONST.success] === false || A[this.CONST.success] === "false") {
                this.loadFailure(A);
                this.hideWaiting()
            } else this.loadSuccess(A);
            this.requesting = false
        }
    },
    nI9: function(response, reqParam, action) {
        action = action || reqParam[this.CONST.action];
        response = GT.$invoke(this, action + "ResponseHandler", [response, reqParam]) || response;
        if (!response || GT.$type(response, "string", "number")) response = {
            text: response
        };
        var respT = null;
        try {
            respT = response.text ? eval("(" + response.text + ")") : response
        } catch(e) {
            respT = {};
            respT[this.CONST.exception] = response.text
        }
        if (respT[this.CONST.exception]) respT[this.CONST.success] = false;
        return respT
    },
    exportGrid: function(F, C, G, A, B) {
        var D = this;
        if (GT.$invoke(D, "beforeExport", [F, C, G, A, B, D]) !== false) {
            try {
                F = F || this.exportType;
                C = C || this.exportFileName;
                G = G || this.exportURL;
                A = A || this.jsonParamName || (this.ajax ? this.ajax.jsonParamName: GT.AjaxDefault.paramName);
                B = "export";
                if (this.html2pdf && F == "pdf") {
                    this.gridFormTextarea.name = "__gt_html";
                    var E = ["<style type=\"text/css\">", this.gm1("exportable"), "</style>"];
                    this.gridFormTextarea.value = E.join("\n") + "\n" + this.HD7(true)
                } else {
                    var _ = this.getLoadParam();
                    _[this.CONST.action] = B;
                    _[this.CONST.exportType] = F;
                    _[this.CONST.exportFileName] = C;
                    this.gridFormTextarea.name = A;
                    this.gridFormTextarea.value = GT.$json(_)
                }
                this.gridFormFileName.value = C;
                this.gridFormExportType.value = F;
                this.gridForm.action = G;
                G && (this.gridForm.submit());
                this.gridFormTextarea.value = ""
            } catch($) {
                this.exportFailure({
                    type: F,
                    fileName: C
                },
                $)
            }
        }
    },
    loadFailure: function(_, $) {
        var A = _[this.CONST.exception] || ($ ? ($.message || "") : "");
       // alert(" LOAD Failed! " + "\n Exception : \n" + A)
    },
    saveFailure: function(_, $) {
        var A = _[this.CONST.exception] || ($ ? ($.message || "") : "");
       // alert(" SAVE Failed! " + "\n Exception : \n" + A)
    },
    exportFailure: function(_, $) {
        var A = _[this.CONST.exception] || ($ ? ($.message || "") : "");
       // alert(" Export " + _.type + " ( " + _.fileName + " ) Failed! " + "\n
		// Exception : \n" + A)
    },
    updateRecordField: function(B, A) {
        var $ = this.getColumn(B);
        if ($) {
            var _ = this.getRecordByRow(B.parentNode);
            return this.update(_, $.fieldName, A)
        }
        return false
    },
    update: function($, A, _) {
        if (GT.$invoke(this, "beforeUpdate", [$, A, _]) !== false) {
            this.dataset.updateRecord($, A, _);
            return true
        }
    },
    cloneDefaultRecord: function() {
        var $ = this.defaultRecord;
        if (GT.$type($, "function")) $ = $(this, this.dataset, this.dataset.getSize());
        return GT.$clone($)
    },
    add: function($, _) {
        if (this.readOnly) return;
        this.Ed2($, _)
    },
    Ed2: function(_, F) {
        var _ = _ || this.cloneDefaultRecord() || (this.dataset.getRecordType() == "array" ? [] : {});
        if (GT.$invoke(this, "beforeInsert", [_]) !== false) {
            this.dataset.Pq2(_);
            _[GT.Const.DataSet.NOT_VAILD] = true;
            var D = this.rowKey + this.getUniqueField(_);
            _[GT.Const.DataSet.ROW_KEY] = D;
            var B = this.fq6(_);
            this.bodyDiv.scrollTop = this.bodyDiv.scrollHeight;
            this.rowNum++;
            if (F !== false) {
                var C = 0,
                A = -1;
                for (C = 0; C < this.columnList.length; C++) {
                    var $ = this.columnList[C];
                    if (A < 0 && !$.hidden && $.editor) A = C;
                    if ($.frozen && B[1]) {
                        var G = B[0].cells[C].cloneNode(true);
                        B[1].appendChild(G)
                    }
                }
                var E = B[0].cells[A]
            }
            this.syncScroll()
        }
        this.R_1()
    },
    del: function($) {
        if (this.readOnly || this.endEdit() === false) return;
        this.delRow($)
    },
    delRow: function(B) {
        var D = [].concat(B || this.selectedRows);
        for (var C = 0; C < D.length; C++) {
            B = D[C];
            var _ = this.getRecordByRow(B);
            if (B != this.activeRow) this.unselectRow(B);
            if (!_) continue;
            if (this.isInsertRow(B)) {
                if (this.activeCell && this.activeRow == B) this.bo8();
                var $ = this.Yi4(B);
                GT.U.removeNode($[0], $[1]);
                this.dataset.bV2(_);
                this.R_1();
                continue
            }
            if (GT.$invoke(this, "beforeDelete", [_, B, this]) !== false) {
                var A = this.dataset.Os2(_);
                if (!A) {
                    this.dataset.bV2(_);
                    GT.U.addClass(B, "gt-row-del")
                } else {
                    this.dataset.undeleteRecord(_);
                    GT.U.removeClass(B, "gt-row-del")
                }
                this.ry2(B)
            }
        }
    },
    fq6: function($) {
        var A,
        C,
        D = this.colNum,
        _ = GT.T_G.rowStart(this, this.rowNum);
        if (!this.gridTable.tFoot) this.gridTable.appendChild(GT.$e("tfoot"));
        if (!this.freezeBodyTable.tFoot) this.freezeBodyTable.appendChild(GT.$e("tfoot"));
        A = GT.U.createTrFromHTML(this.MP1(_, $, this.rowNum, D), this.gridTable.tFoot);
        if (this.showIndexColumn) C = GT.U.createTrFromHTML(this.Xa1(_, $, this.rowNum, D), this.freezeBodyTable.tFoot);
        else {
            C = GT.U.createTrFromHTML(_ + "</tr>", this.freezeBodyTable.tFoot);
            C.appendChild(GT.T_G.freezeBodyCell(this, 10, null))
        }
        GT.U.addClass(A, "gt-row-new");
        GT.U.addClass(C, "gt-row-new");
        var B = $[GT.Const.DataSet.ROW_KEY];
        A.id = B;
        A.setAttribute(GT.Const.DataSet.INDEX, $[GT.Const.DataSet.SN_FIELD]);
        return [A, C]
    },
    Rf2: function() {
        var $ = this.mo8().length > 0,
        _ = !!(this.gridTable.tFoot && this.gridTable.tFoot.rows.length > 0);
        return $ || _
    },
    R_1: function() {
        if (!this.Rf2()) {
            var _ = ["<tr class=\"gt-row gt-row-empty\" >"];
            for (var $ = 0; $ < this.colNum; $++) _.push(GT.T_G.cell(this.columnList[$], "&#160;"));
            _.push(GT.T_G.rowEndHTML);
            GT.U.createTrFromHTML(_.join(""), this.gridTbodyList[0])
        } else {
            var A = this.oP2();
            if (this.isEmptyRow(A)) GT.U.removeNode(A)
        }
    },
    VT9: function(D) {
        D = D || this;
        var F = D.dataset.getSize(),
        E = D.getPageInfo();
        if (!D.remotePaging && !E.pageSize) E.pageSize = F;
        F = F > E.pageSize ? E.pageSize: F;
        var C = D.dataset.startRecordNo;
        D.rowNum = F;
        D.rowBegin = C;
        D.rowEnd = C + F;
        D.colNum = D.columnList.length;
        D.Ml0 = 0;
        var G = [],
        A = [];
        D.tD1(D, G, A);
        D.bodyDiv.innerHTML = G.join("");
        D.freezeBodyDiv.innerHTML = A.join("");
        var _ = GT.U.firstChildElement(D.bodyDiv);
        if (_) {
            if (GT.U.getTagName(_) != "TABLE") _ = GT.U.nextElement(_);
            if (GT.U.getTagName(_) == "TABLE") {
                D.gridTable = _;
                D.gridTbodyList.push(_.tBodies[0])
            }
        }
        _ = GT.U.firstChildElement(D.freezeBodyDiv);
        if (_) {
            if (GT.U.getTagName(_) != "TABLE") _ = GT.U.nextElement(_);
            D.freezeBodyTable = _
        }
        D.bodyFirstRow = D.oP2();
        if (D.rowNum < 1) for (var B = 0; B < D.colNum; B++) {
            var $ = D.columnList[B];
            if (D.bodyFirstRow) {
                $.firstCell = D.bodyFirstRow.cells[B];
                $.firstCell.style.height = "0px";
                $.firstCell.style.borderBottomWidth = "0px"
            }
        }
        D.Sa2 = D.showIndexColumn;
        D.uB0 = true;
        GT.$thread(function() {
            D.freezeBodyDiv.style.height = D.bodyDiv.clientHeight + "px";
            D.syncScroll()
        })
    },
    hideFreezeZone: function() {
        this.freezeHeadDiv && (this.freezeHeadDiv.style.display = "none");
        this.freezeBodyDiv && (this.freezeBodyDiv.style.display = "none")
    },
    cleanFreezeHead: function() {
        var _ = this.freezeHeadTable.tBodies[0];
        for (var $ = _.rows.length - 1; $ >= 0; $--) GT.U.removeNodeTree(_.rows[$])
    },
    tD1: function(E, I, _) {
        var A = ("" + E.rowEnd).length;
        A = (A < 2 ? 1.5: A) * 7 + 2 + 1;
        var G = A + this.Ut2,
        $ = A + this.gc3,
        J = "style=\"width:" + G + "px;\"",
        F = "style=\"width:" + $ + "px;\"";
        this.indexColumnCell = ["<td class=\"gt-index-col\" " + J + " ><div class=\"gt-inner\" " + F + " >", "</div></td>"];
        if (E.showIndexColumn) {
            E.Ml0 = A;
            E.cleanFreezeHead();
            var B = GT.$e("tr", {
                className: "gt-hd-row"
            }),
            H = GT.T_G.xm4(E, A, null);
            B.appendChild(H);
            E.freezeHeadTable.tBodies[0].appendChild(B);
            E.freezeHeadDiv.style.left = E.freezeBodyDiv.style.left = this.freezeFixW + "px";
            E.headTable.style.marginLeft = E.Ml0 + "px";
            E.freezeHeadDiv.style.display = E.freezeBodyDiv.style.display = "block";
            E.freezeBodyDiv.style.height = parseInt(E.bodyDiv.style.height) + "px"
        } else E.freezeHeadDiv.style.display = E.freezeBodyDiv.style.display = "none";
        _.push(GT.T_G.tableStartHTML);
        I.push(GT.U.replaceAll(GT.T_G.tableStartHTML, "margin-left:0px", "margin-left:" + E.Ml0 + "px"));
        var D = E.rowBegin,
        C = E.rowEnd;
        E.currentRowNum = D;
        E.MG6(D, C, -1, I, _);
        _.push(GT.T_G.tableEndHTML)
    },
    isNextGroup: function($, A, _) {},
    isGroupRow: function($, _) {
        $ = $ || (_ ? _.cells[0] : null);
        return GT.U.hasClass($, "gt-group-row")
    },
    isEmptyRow: function($) {
        return ! $ || GT.U.hasClass($, "gt-row-empty")
    },
    isInsertRow: function($) {
        return GT.U.hasClass($, "gt-row-new")
    },
    isDelRow: function($) {
        return GT.U.hasClass($, "gt-row-del")
    },
    Xa1: function(_, $, A, C) {
        var B = [_];
        B.push(this.indexColumnCell[0]);
        B.push(A + 1 + this.indexColumnCell[1]);
        B.push(GT.T_G.rowEndHTML);
        return B.join("")
    },
    MP1: function(A, $, F, G, C) {
        var E = [A];
        for (var B = 0; B < G; B++) {
            var _ = this.columnList[B],
            D = C && C[_.id] ? C[_.id].attr: null;
            E.push(GT.T_G.cell(_, _.renderer($[_.fieldIndex], $, _, this, B, F), D))
        }
        E.push(GT.T_G.rowEndHTML);
        return E.join("")
    },
    yR9: function(_, $, C, D) {
        var B = [_],
        A = "<td colspan=\"" + D + "\" class=\"gt-group-row\" > + " + C + " -------------</td>";
        B.push(A);
        B.push(GT.T_G.rowEndHTML);
        return B.join("")
    },
    resetFreeze: function($) {},
    yM1: function() {
        if (this.frozenColumnList) {
            for (var _ = 0; _ < this.frozenColumnList.length; _++) {
                var $ = this.columnMap[this.frozenColumnList[_]];
                if ($) this.ua9($.colIndex, _, true)
            }
            for (_ = 0; _ < this.frozenColumnList.length; _++) {
                $ = this.columnMap[this.frozenColumnList[_]];
                if ($) $.freeze(true)
            }
        }
    },
    getGroupInfo: function($, _) {
        return this.getMergeGroupInfo($, _)
    },
    getSeparateGroupInfo: function(G, B) {
        var C = this.colNum,
        $ = null;
        for (var H = 0; H < C; H++) {
            var _ = this.columnList[H];
            if (_.grouped) {
                $ = _;
                break
            }
        }
        var D = {};
        if ($) {
            var I = B - G,
            A = G;
            for (var E = 0; E < I; E++) {
                var F = this.dataset.getRecord(A++);
                if (!F) continue;
                var J = this.getUniqueField(F)
            }
        }
    },
    getMergeGroupInfo: function(J, C) {
        var F = this.colNum,
        M = C - J,
        D,
        B,
        N = 1,
        A = {},
        _ = null,
        N = 1,
        G = [];
        for (var K = 0; K < F; K++) {
            var H = this.columnList[K],
            $ = J;
            for (var E = 0; E < M; E++) {
                var I = this.dataset.getRecord($++);
                if (!I) continue;
                D = G[E] = G[E] || {};
                var L = D["__gt_group_s_"];
                if (H.grouped) {
                    B = D[H.id] = D[H.id] || {};
                    if (_ == I[H.fieldIndex] && (!L && L !== 0 || L > K)) {
                        B.attr = " style=\"display:none;\" ";
                        N++
                    } else {
                        A.attr = " rowspan=\"" + N + "\" style=\"background-color: #eef6ff;\"  ";
                        N = 1;
                        A = B;
                        _ = I[H.fieldIndex];
                        D["__gt_group_s_"] = K
                    }
                }
            }
            A.attr = " rowspan=\"" + N + "\" style=\"background-color: #eef6ff;\"  "
        }
        return G
    },
    TG7: function($) {
        return {
            columnId: $.id,
            fieldName: $.fieldName,
            sortOrder: $.sortOrder,
            getSortValue: $.getSortValue,
            sortFn: $.sortFn
        }
    },
    kG1: function() {
        if (!this.sortInfo || this.sortInfo.length < 1) return;
        this.dataset.sort(this.sortInfo)
    },
    qt7: function($) {
        this.sortInfo = $ || this.sortInfo;
        if (this.remoteSort) this.reload();
        else this.refresh()
    },
    addSortInfo: function(G, E) {
        E = E || E === false ? E: this.multiSort;
        var D = [],
        C = false;
        for (var B = 0; B < this.columnList.length; B++) {
            var _ = this.columnList[B];
            if (_.grouped) {
                if (!C && _.id == G.columnId) {
                    _.sortOrder = G.sortOrder;
                    C = true
                } else {
                    var A = _.sortOrder;
                    A = A == "asc" || A == "desc" ? A: "asc";
                    _.sortOrder = A
                }
                D.push(this.TG7(_))
            }
        }
        if (!C && E !== true) {
            this.sortInfo = D.concat(G);
            return
        }
        this.sortInfo = this.sortInfo || [];
        var $ = G.columnId,
        H = false;
        for (var I = 0; I < this.sortInfo.length; I++) {
            var F = this.sortInfo[I];
            if (F && F.columnId === $) {
                F.sortOrder = G.sortOrder;
                H = true;
                break
            }
        } ! H && (this.sortInfo.push(G));
        for (I = 0; I < this.sortInfo.length; I++) {
            F = this.sortInfo[I];
            if (!F || (!F.sortOrder || F.sortOrder == "defaultsort")) {
                this.sortInfo.splice(I, 1);
                I--
            }
        }
    },
    updateSortState: function() {
        for (var _ = 0; _ < this.colNum; _++) {
            var $ = this.columnList[_];
            $.sortIcon && ($.sortIcon.className = "gt-hd-icon");
            $.frozenSortIcon && ($.frozenSortIcon.className = "gt-hd-icon");
            $.sortOrder = null
        }
        if (!this.sortInfo || this.sortInfo.length < 1) return;
        for (var E = 0; E < this.sortInfo.length; E++) {
            var B = this.sortInfo[E];
            if (B) {
                var $ = this.columnMap[B.columnId],
                A = B.sortOrder || "defaultsort";             
                $.sortOrder = A;
                GT.U.addClass($.sortIcon, "gt-hd-" + A);
                GT.U.addClass($.frozenSortIcon, "gt-hd-" + A)
            }
        }
        var C = this.oP2();
        if (C && !this.isEmptyRow(C)) {
            this.bodyFirstRow = C;
            for (_ = 0; _ < this.colNum; _++) {
                var D = this.columnList[_];
                D.firstCell = this.bodyFirstRow.cells[_];
                D.firstCell.className = D.styleClass
            }
        }
    },
    getRecord: function($) {
        var _;
        if (GT.U.isNumber($)) _ = $;
        else if (GT.U.getTagName($) == "TD") return this.getRecordByRow($.parentNode);
        else if (GT.U.getTagName($) == "TR") return this.getRecordByRow($);
        else if (GT.$type($, "object") && !$.tagName) return $;
        else if (this.selectedRows.length > 0) _ = this.selectedRows[this.selectedRows.length - 1].getAttribute(GT.Const.DataSet.INDEX) / 1;
        else _ = 0;
        return this.dataset.getRecord(_)
    },
    getRecordByRow: function(A) {
        if (!A) return null;
        if (this.isInsertRow(A)) {
            var _ = A.getAttribute(GT.Const.DataSet.INDEX);
            return this.dataset.insertedRecords[_]
        }
        var $ = A.getAttribute(GT.Const.DataSet.INDEX) / 1;
        return $ || $ === 0 ? this.dataset.getRecord($) : null
    },
    getRowByRecord: function($) {
        var _ = $[GT.Const.DataSet.ROW_KEY];
        return GT.doc.getElementById(_)
    },
    getUniqueField: function($) {
        return $[this.dataset.uniqueField]
    },
    forEachRow: function(A) {
        var D = this.mo8();
        for (var C = 0, _ = D.length; C < _; C++) {
            var B = D[C],
            $ = this.getRecordByRow(B);
            A(B, $, C, this)
        }
    },
    updateCheckState: function() {
        var $ = this.checkColumn;
        if ($) {
            var _ = $.colIndex,
            F = this.mo8();
            for (var E = 0, C = F.length; E < C; E++) {
                var D = F[E],
                B = D.cells[_],
                A = B ? B.getElementsByTagName("input") : null;
                A = A ? A[0] : A;
                if (A && A.checked) this.selectRow(D)
            }
        }
    },
    updateEditState: function() {
        var B = this.getInsertedRecords();
        for (var H = 0; H < B.length; H++) this.fq6(B[H]);
        for (var E in this.dataset.updatedRecords) {
            var A = this.dataset.updatedRecords[E],
            G = this.dataset.B$6[E],
            F = this.getRowByRecord(A);
            if (F) {
                var C = this.getRecordByRow(F);
                if (C) for (var _ in G) {
                    C[_] = A[_];
                    for (var H = 0, D = this.columnList.length; H < D; H++) {
                        var $ = this.columnList[H];
                        if (_ == $.fieldIndex && F.cells) {
                            this.dirty(F.cells[H]);
                            F.cells[H].firstChild.innerHTML = $.renderer(C[$.fieldIndex], C, $, this, H, F.rowIndex)
                        }
                    }
                }
                this.dataset.updatedRecords[E] = C
            }
        }
        for (E in this.dataset.deletedRecords) {
            A = this.dataset.deletedRecords[E],
            F = this.getRowByRecord(A);
            this.del(F)
        }
    },
    filterGrid: function($) {
        this.filterInfo = $ || this.filterInfo || [];
        GT.U.addClass(this.filterTool.itemIcon, "gt-tool-filtered");
        if (this.remoteFilter) {
            this.reload();
            return
        }
        this.sf2 = this.dataset.Ge6;
        this.filterDataProxy = this.dataset.filterData(this.filterInfo);
        if (!this.remoteFilter && this.justShowFiltered) {
            this.dataset.Ge6 = this.filterDataProxy;
            this.refresh()
        }
        if (this.afterFilter) this.afterFilter(this.filterDataProxy);
        return this.filterDataProxy
    },
    unfilterGrid: function($) {
        this.filterGrid([]);
        GT.U.removeClass(this.filterTool.itemIcon, "gt-tool-filtered");
        return null
    },
    syncScroll: function($, _) {
        if (GT.$chk($)) this.bodyDiv.scrollLeft = $;
        if (GT.$chk(_)) this.bodyDiv.scrollTop = _;
        this.headDiv.scrollLeft = this.bodyDiv.scrollLeft;
        this.freezeBodyDiv.scrollTop = this.bodyDiv.scrollTop;
        this.scrollLeft = this.bodyDiv.scrollLeft;
        this.scrollTop = this.bodyDiv.scrollTop
    },
    uc3: function() {
        if (this.resizable && (this.toolbarPosition == "bottom" || this.toolbarPosition == "b") && this.Ml9) {
            this.resizeButton = GT.$e("div", {
                id: this.id + "_resizeButton",
                className: "gt-tool-resize",
                innerHTML: "&#160;"
            });
            this.resizeButton.setAttribute("unselectable", "on");
            this.Ml9.appendChild(this.resizeButton);
            var D = this;
            GT.U.addEvent(this.resizeButton, "mousedown", 
            function($) {
                GT.Grid.startGridResize($, D)
            })
        }
        this.as6();
        if (this.toolbarContent && this.toolbarPosition && this.toolbarPosition != "none") {
            this.toolbarContent = this.toolbarContent.toLowerCase();
            var C = this.toolbarContent.split(" "),
            $ = null;
            for (var E = 0; E < C.length; E++) {
                var A = C[E];
                if (A == "|") {
                    var _ = GT.ToolFactroy.create(this, "separator", true);
                    if ($) $.separator = _
                } else if (A == "state" || A == "info" || A == "pagestate") {
                    if (!this.pageStateBar) this.pageStateBar = GT.ToolFactroy.create(this, "pagestate", this.showPageState);
                    if (E != C.length - 1) this.pageStateBar.className += " gt-page-state-left";
                    $ = this.pageStateBar
                } else if (A == "nav") {
                    this.navigator.Ul9(this);
                    $ = this.navigator
                } else {
                    var B = A.charAt(0).toUpperCase() + A.substring(1);
                    $ = this.tools[A + "Tool"] = GT.ToolFactroy.create(this, A, this["show" + B + "Tool"])
                }
            }
        }
        this.expendMenu = {};
        this.over_initToolbar = true
    },
    refreshToolBar: function($, A) {
        $ && (this.setPageInfo($));
        if (this.over_initToolbar) {
            this.navigator.Md9($, A);
            this.navigator.sp7();
            var _ = this.navigator.pageInput;
            if (this.pageStateBar) {
                var $ = this.getPageInfo();
                GT.U.removeNode(this.pageStateBar.firstChild);
                this.pageStateBar.innerHTML = "<div>" + GT.$msg(this.getMsg(_ ? "PAGE_STATE": "PAGE_STATE_FULL"), $.startRowNum, $.endRowNum, $.totalPageNum, $.totalRowNum, $.pageNum) + "</div>"
            }
        }
    },
    as6: function() {
        if (!this.showGridMenu || !this.Ml9 || !this.toolBar) return;
        var F = this,
        _ = F.id;
        this.gridMenuButton = new GT.Button({
            gridId: this.id,
            parentItem: this,
            container: this.toolBar,
            cls: "gt-tool-gridmenu",
            NA8: true
        });
        var F = this,
        C = !this.allowGroup ? null: GT.lr2("group", {
            gridId: _,
            checkValid: function($) {
                return $.grouped
            },
            checkFn: "group",
            uncheckFn: "ungroup",
            checkType: F.multiGroup ? "checkbox": "radio",
            canCheck: function($) {
                return ! $.hidden
            }
        }),
        E = !this.allowFreeze ? null: GT.lr2("freeze", {
            gridId: _,
            checkValid: function($) {
                return $.frozen
            },
            checkFn: "freeze",
            uncheckFn: "unfreeze",
            canCheck: function($) {
                return ! $.hidden
            }
        }),
        H = !this.allowHide ? null: GT.lr2("show", {
            gridId: _,
            checkValid: function($) {
                return ! $.hidden
            },
            checkFn: "show",
            uncheckFn: "hide",
            canCheck: function($) {
                return ! $.frozen
            }
        });
        function B(_, $) {
            if (!_) return;
            _.show();
            _.setTitle($);
            F.gridMenuButton.Ge5()
        }
        var D = this.toolbarPosition != "bottom" ? "B": "T";
        function A($) {
            return function() {
                GT.Grid.xo4(F, $)
            }
        }
        var $ = null;
        if (this.allowCustomSkin) {
            $ = new GT.MenuItem({
                gridId: this.id,
                type: "",
                text: this.getMsg("CHANGE_SKIN"),
                cls: "gt-icon-skin"
            });
            var G = [];
            for (var I = 0; I < this.skinList.length; I++) G.push(new GT.MenuItem({
                gridId: this.id,
                type: "radiobox",
                text: this.skinList[I].text,
                checked: I === 0,
                onclick: A(this.skinList[I].value)
            }));
            $.ae8(G, "R")
        }
        this.gridMenuButton.ae8([$, C ? new GT.MenuItem({
            gridId: F.id,
            type: "",
            text: F.getMsg("MENU_GROUP_COL"),
            cls: "gt-icon-groupcol",
            onclick: function() {
                B(C, F.getMsg("MENU_GROUP_COL"))
            }
        }) : null, E ? new GT.MenuItem({
            gridId: F.id,
            type: "",
            text: F.getMsg("MENU_FREEZE_COL"),
            cls: "gt-icon-freeze",
            onclick: function() {
                B(E, F.getMsg("MENU_FREEZE_COL"))
            }
        }) : null, H ? new GT.MenuItem({
            gridId: F.id,
            type: "",
            text: F.getMsg("MENU_SHOW_COL"),
            cls: "gt-icon-hidecol",
            onclick: function() {
                B(H, F.getMsg("MENU_SHOW_COL"))
            }
        }) : null, new GT.MenuItem({
            gridId: this.id,
            type: "",
            text: GT_GRID_VER
        })], D)
    },
    showMask: function($) {
        if ($ || this.transparentMask) GT.U.addClass(this.gridMask, "gt-transparent");
        else GT.U.removeClass(this.gridMask, "gt-transparent");
        this.gridMask && (this.gridMask.style.display = "block");
        this.pageSizeSelect && (this.pageSizeSelect.style.visibility = "hidden")
    },
    hideMask: function() {
        if (this.gridMask) {
            this.gridMask.style.cursor = "auto";
            this.gridMask.style.display = "none"
        }
        this.pageSizeSelect && (this.pageSizeSelect.style.visibility = "inherit")
    },
    showWaiting: function() {
        this.showMask();
        if (!this.transparentMask) this.gridWaiting.style.display = "block";
        this.isWaiting = true
    },
    hideWaiting: function() {
        this.gridWaiting.style.display = "none";
        this.hideMask();
        this.isWaiting = false
    },
    showDialog: function(D) {
        var A = this;
        switch (D) {
        case "filter":
            A.filterDialog = A.filterDialog || GT.Vc8({
                title: A.getMsg("DIAG_TITLE_FILTER"),
                gridId: A.id
            });
            A.filterDialog.show();
            break;
        case "chart":
            var _ = A.activeCell ? A.getRecordByRow(A.activeRow) : A.getRecord();
            if (!_) break;
            var C = [],
            B = "",
            $ = 300,
            E = 300;
            if (_) {
                A.charDialog = A.charDialog || new GT.Dialog({
                    gridId: A.id,
                    container: A.gridMask,
                    id: "charDialog",
                    width: $,
                    height: E,
                    autoRerender: true,
                    title: A.getMsg("DIAG_TITLE_CHART")
                });
                A.charDialog.show();
                A.chart = new GT.Chart({
                    swfPath: A.GTGridPath + "/flashchart/fusioncharts/charts/",
                    width: $ - 3,
                    height: E - 23,
                    container: A.charDialog.bodyDiv
                });
                GT.$each(A.columnList, 
                function($, A) {
                    if ($.chartCaption) B = $.chartCaption.replace("{@}", _[$.fieldIndex]);
                    if ($.inChart) C.push([$.header || $.title, _[$.fieldIndex], $.chartColor || "66bbff"])
                });
                A.chart.caption = A.chartCaption;
                A.chart.subCaption = B;
                A.chart.data = C;
                A.chart.QO0()
            }
            break
        }
    },
    closeDialog: function() {
        this.activeDialog && this.activeDialog.close();
        this.activeDialog = null
    },
    hideDialog: function() {
        this.activeDialog && this.activeDialog.hide();
        this.activeDialog = null
    },
    closeGridMenu: function() {
        if (this.gridMenuButton) this.gridMenuButton.Ge5()
    },
    ee7: function() {
        return this.filterInfo || []
    },
    dt1: function() {
        return this.sortInfo || []
    },
    getPageInfo: function($) {
        return $ ? this.navigator.Md9() : this.navigator.pageInfo
    },
    getOtherDataInfo: function() {
        return  this.otherDataInfo
    },
    setPageInfo: function($) {
        GT.$extend(this.getPageInfo(), $)
    },
    setTotalRowNum: function($) {
        this.getPageInfo().totalRowNum = $
    },
    getTotalRowNum: function($) {
        return this.getPageInfo($).totalRowNum
    },
    addSkin: function($) {
        if (GT.$type($, "string")) $ = {
            text: this.getMsg("STYLE_NAME_" + $.toUpperCase()),
            value: $.toLowerCase()
        };
        this.skinList.push($)
    },
    cn2: function(_) {
        for (var $ = 0; $ < _.length; $++) this.gridRowList.push(_[$])
    },
    oP2: function() {
        return this.gridTbodyList[0] ? this.gridTbodyList[0].rows[0] : null
    },
    mo8: function() {
        return this.gridTbodyList[0].rows
    },
    qV6: function($) {
        if (GT.U.isNumber($)) return this.mo8()[$];
        return $
    },
    getRowNumber: function() {
        return grid.rowNum
    },
    hasData: function() {
        return grid.rowNum > 0
    },
    getColumnValue: function(B, C) {
        var _ = this.getColumn(B),
        A = this.getRecord(C),
        $ = A ? A[_.fieldIndex] : null;
        return $
    },
    setColumnValue: function(B, A, _) {
        var $ = A;
        if (GT.U.isNumber($)) $ = this.dataset.getRecord($);
        this.update($, this.columnMap[B].fieldName, _)
    },
    dirty: function($) {
        GT.U.addClass($, "gt-cell-updated")
    },
    selectFirstRow: function() {
        var $ = this.qV6(0);
        if ($) this.selectRow($)
    },
    selectRow: function(B) {
        var D = [].concat(B);
        for (var C = 0, _ = D.length; C < _; C++) {
            B = this.qV6(D[C]);
            if (!this.isEmptyRow(B)) {
                var A = B.rowIndex,
                $ = this.getRecordByRow(B);
                if (GT.$invoke(this, "beforeRowSelect", [$, B, A, this]) !== false) {
                    GT.U.addClass(B, "gt-row-selected");
                    this.ry2(B);
                    this.selectedRows.push(B);
                    this.activeRow = B;
                    GT.$invoke(this, "afterRowSelect", [$, B, A, this])
                }
            }
        }
    },
    unselectRow: function($) {
        if ($) {
            GT.U.removeClass($, "gt-row-selected");
            this.ry2($);
            GT.U.remove(this.selectedRows, $)
        }
    },
    unselectAllRow: function() {
        var $ = this;
        GT.$each(this.selectedRows, 
        function(_) {
            GT.U.removeClass(_, "gt-row-selected");
            $.ry2(_)
        });
        this.selectedRows = [];
        this.activeRow = null
    },
    getSelectedRecords: function() {
        var $ = [];
        for (var _ = 0; _ < this.selectedRows.length; _++) $.push(this.getRecordByRow(this.selectedRows[_]));
        return $
    },
    getSelectedRecord: function() {
        return this.getRecordByRow(this.selectedRows[this.selectedRows.length - 1]) || this.activeRecord
    },
    HD7: function(_, C) {
        var B = this.kt1();
        C = C || [GT.T_G.bodyTableStart(this.id, false), B, "<tbody>"];
        var $ = 0,
        D = this.dataset.getSize(),
        A = _ ? this.getPageInfo().pageSize: -1;
        C.push(this.MG6($, D, A, C, null, true));
        return C.join("")
    },
    kt1: function() {
        var _ = this.headTable.innerHTML,
        $ = _.toLowerCase().indexOf("<tr"),
        A = _.toLowerCase().lastIndexOf("</tr>");
        if (this.customHead) $ = _.toLowerCase().indexOf("<tr", $ + 3);
        _ = _.substring($, A + "</tr>".length);
        return "<!-- gt : head start  -->" + _ + "<!-- gt : head end  -->"
    },
    MG6: function(K, G, D, O, C, F) {
        var N = this,
        H = N.colNum,
        M = N.getGroupInfo(K, G),
        _ = " >",
        J = lastRecord = null,
        I = 0,
        E = "";
        for (var B = K; B < G; B++) {
            J = N.dataset.getRecord(B);
            if (!J) break;
            var $ = N.customRowAttribute(J, B, N) || "",
            A = GT.T_G.rowStartS(N, B, $);
            if (!F) {
                E = N.rowKey + N.getUniqueField(J);
                J[GT.Const.DataSet.ROW_KEY] = E;
                N.currentRowNum++;
                if (N.showIndexColumn) C.push(N.Xa1(A + _, J, B, H))
            } else if (B > 0 && D > 0 && (B % D) == 0) O.push("\n<!-- gt : page separator  -->\n");
            if (N.isNextGroup(J, lastRecord, B)) O.push(N.yR9(A + _, J, B, H));
            var L = M[I++];
            O.push(N.MP1(A + " id=\"" + E + "\"" + _, J, B, H, L));
            lastRecord = J
        }
        O.push(GT.T_G.tableEndHTML)
    },
    gm1: function(A) {
        var _ = this,
        $ = [];
        GT.$each(_.columnList, 
        function(_, B) {
            if (_.hidden === true || (A && _[A] === false)) {
                $.push(_.CLASS_PREFIX + _.styleClass + " { display:none;width:0px; }");
                $.push(_.CLASS_PREFIX + _.innerStyleClass + " { display:none;width:0px; }")
            }
        });
        var B = $.join("\n");
        return GT.U.replaceAll(B, ".gt-grid ", "")
    },
    printGrid: function() {
        var A = this;
        A.closeGridMenu();
        A.showWaiting();
        var E = [" body { margin :5px;padding:0px;}", ".gt-table { width:100%;border-left:1px solid #000000 ; border-top:1px solid #000000; }", ".gt-table TD { font-size:12px;padding:2px; border-right:1px solid #000000 ; border-bottom:1px solid #000000; }", ".gt-hd-row TD { padding:3px; border-bottom:2px solid #000000 ;background-color:#e3e3e3; white-space:nowrap;word-break:keep-all;word-wrap:normal; }", ".gt-hd-hidden { }", ".gt-row-even {\tbackground-color:#f6f6f6; }"];
        E.push(this.gm1("printable"));
        A.customPrintCss && E.push(A.customPrintCss);
        E = E.join("\n");
        var D = A.HD7(),
        _ = GT.doc.activeElement;
        function B($) {
            $.writeln("<style>");
            $.writeln(E);
            $.writeln("</style>");
            $.writeln(D);
            $.close()
        }
        if (GT.isIE || GT.isGecko || GT.isSafari) {
            var $ = A.gridIFrame.contentWindow.document;
            B($);
            A.gridIFrame.contentWindow.focus();
            A.gridIFrame.contentWindow.print()
        } else if (GT.isOpera) {
            var C = window.open(""),
            $ = C.document;
            B($);
            C.focus();
            GT.$thread(function() {
                C.print();
                GT.$thread(function() {
                    C.close()
                },
                2000)
            })
        }
        GT.$thread(function() {
            A.hideWaiting()
        },
        1000)
    },
    getAllRows: function() {
        var $ = this;
        if ($.gridRowList.length == 0) GT.$each($.gridTbodyList, 
        function(_) {
            $.cn2(_.rows)
        });
        return $.gridRowList
    },
    getAllFreezeRows: function() {
        var $ = this;
        if ($.gridFreezeRowList.length == 0) {
            var A = $.freezeBodyTable.tBodies[0].rows;
            for (var _ = 0; _ < A.length; _++) $.gridFreezeRowList.push(A[_])
        }
        return $.gridFreezeRowList
    },
    filterHandler: {
        hide: function($) {
            $ = $ || this.filterInfo;
            var _ = GT.Grid.filterCheck[checkType](value, cv);
            if (_);
        }
    },
    ua9: function(_, F, A) {
        if (_ == F) return;
        grid = this;
        var $ = false,
        E = this.columnList.length,
        B = A !== true && this.freezeHeadDiv.style.display == "block" ? this.frozenColumnList.length: 0;
        F = F < B ? B: F;
        if (F >= E) {
            F = E - 1;
            F ^= _;
            _ ^= F;
            F ^= _;
            $ = true
        }
        var D = this.getAllRows();
        GT.U.insertNodeBefore(this.columnList[_].headCell, this.columnList[F].headCell);
        for (var C = 0; C < D.length; C++) GT.U.insertNodeBefore(D[C].cells[_], D[C].cells[F]);
        GT.U.moveItem(this.columnList, _, F);
        for (C = 0; C < E; C++) this.columnList[C].colIndex = C
    },
    bo8: function() {
        this.activeCell = null;
        this.activeRow = null;
        this.activeColumn = null;
        this.activeEditor = null;
        this.activeRecord = null;
        this.activeValue = null
    },
    OE7: function(B) {
        this.closeGridMenu();
        if (this.endEdit() === false) return;
        this.lastOverHdCell = null;
        if (B !== false) {
            this.bo8();
            for (var C = 0; C < this.selectedRows.length; C++) this.selectedRows[C] = null;
            this.selectedRows = [];
            this.overRow = null;
            this.overRowF = null
        }
        this.gridRowList = [];
        this.bodyFirstRow = null;
        for (var _ = 0; _ < this.colNum; _++) {
            var $ = this.columnList[_];
            $.firstCell = null;
            $.frozenSortIcon = null;
            $.frozenHdTool = null;
            $.frozenHeadCell = null
        }
        var A = this;
        GT.$each(this.gridTbodyList, 
        function(_, $) {
            GT.U.removeNode(_);
            A.gridTbodyList[$] = null
        });
        GT.U.removeNode(this.gridTable);
        this.gridTable = null;
        this.gridTbodyList = [];
        this.cleanFreezeHead();
        if (this.freezeBodyTable) {
            GT.U.removeNode(this.freezeBodyTable.tBodies[0], this.freezeBodyTable);
            this.freezeBodyTable = null
        }
    },
    destroy: function() {
        var B = this;
        GT.$invoke(this, "pa5");
        this.OE7();
        var D = ["gridMenuButton", "filterDialog", "charDialog", "navigator"];
        GT.$each(D, 
        function($, _) {
            if (B[$] && B[$].destroy) B[$].destroy();
            B[$] = null
        });
        for (var C in this.tools) {
            var _ = this.tools[C];
            if (_ && _.destroy) _.destroy();
            this.tools[C] = null
        }
        GT.U.removeNodeTree(this.gridDialogTitle);
        GT.U.removeNodeTree(this.gridDialogBody);
        GT.U.removeNodeTree(this.gridDialog);
        this.gridDialog = this.gridDialogBody = this.gridDialogTitle = null;
        for (var A = 0; A < this.colNum; A++) {
            var $ = this.columnList[A];
            $.destroy()
        }
        GT.$each(this.domList, 
        function($, _) {
            GT.U.removeNode(B[$]);
            B[$] = null
        });
        this.freezeBodyTable = this.gridTable = this.bodyFirstRow = this.lastOverHdCell = this.overRowF = this.overRow = null;
        this.gridFreezeRowList = [];
        this.selectedRows = [];
        this.cacheBodyList = [];
        this.frozenColumnList = [];
        this.sortedColumnList = [];
        this.checkedRows = {};
        this.gridTbodyList = [];
        this.gridRowList = [];
        GT.GridCache[this.id] = null;
        delete GT.GridCache[this.id]
    }
};
GT.Grid = GT.$class(GT.GridDefault);
GT.$extend(GT.Grid, {
    isSelectedRow: function($) {
        return GT.U.hasClass($, "gt-row-selected")
    },
    Mc7: function($, _, A) {
        if (!A || _.overRow == A) {
            _.Ui5 = false;
            return
        }
        _.Ui5 = true;
        if (_.overRow) _.overRow.className = _.overRow.className.replace(" gt-row-over", "");
        A.className += " gt-row-over";
        _.overRow = A;
        _.Ui5 = false;
        return A
    },
    mt4: function(_, B, $) {
        _ = _ || window.event;
        row = GT.U.LG4("tr", null, _);
        if (B.isEmptyRow(row)) return;
        if ($ == B.freezeBodyDiv && row) {
            var A = B.qV6(row.rowIndex);
            GT.Grid.Mc7(_, B, A);
            B.overRowF = row
        } else GT.Grid.Mc7(_, B, row)
    },
    startGridResize: function($, _) {
        $ = $ || window.event;
        _ = GT.$grid(_);
        _.closeGridMenu();
        _.Ic1 = true;
        _.resizeButton.style.cursor = _.gridGhost.style.cursor = "se-resize";
        _.syncLeftTop();
        _.gridGhost.style.top = _.top + GT.doc.body.scrollTop + "px";
        _.gridGhost.style.left = _.left + GT.doc.body.scrollLeft + "px";
        var A = _.gridDiv.offsetWidth,
        B = _.gridDiv.offsetHeight;
        _.gridGhost.cx = $.clientX - A;
        _.gridGhost.cy = $.clientY - B;
        _.gridGhost.style.width = A + "px";
        _.gridGhost.style.height = B + "px";
        _.gridGhost.style.display = "block"
    },
    KR9: function(_, B) {
        var A = GT.U.parseInt(B.gridGhost.style.width) + "px",
        $ = GT.U.parseInt(B.gridGhost.style.height) + "px";
        B.gridGhost.style.cursor = "auto";
        B.gridMask.style.display = B.gridGhost.style.display = "none";
        B.Ic1 = false;
        B.setSize(A, $)
    },
    startColumnResize: function(_, $) {
        _ = _ || window.event;
        if ($.resizable === false) return;
        var B = $.grid;
        B.mouseDown = true;
        if (_.ctrlKey) return;
        B.Vl4 = true;
        B.closeGridMenu();
        B.showMask(true);
        B.headDiv.style.cursor = B.gridMask.style.cursor = "col-resize";
        B.gi4 = $.id;
        var A = GT.U.getPageX(_);
        $.ml3 = A - B.UQ0[0];
        B.pe6 = $.ml3 + $.minWidth - $.headCell.offsetWidth;
        B.separateLine.style.left = $.ml3 + "px";
        B.separateLine.style.height = B.viewport.offsetHeight - 2 + "px";
        B.separateLine.style.display = "block";
        B.columnMoveS.style.left = $.headCell.offsetLeft + ((GT.isFF2 || GT.isFF1) ? 0: B.Ml0) + "px";
        B.columnMoveS.style.display = "block"
    },
    Ps7: function(A, $) {
        A = A || window.event;
        var C = $.grid;
        C.mouseDown = true;
        if (!A.ctrlKey || C.frozenColumnList[$.getColumnNo()]) return;
        C.closeGridMenu();
        var B = GT.U.getPageX(A),
        D = $.headCell.offsetLeft;
        C.columnMoveS.setAttribute("newColIndex", null);
        var _ = C.headerGhost;
        _.setAttribute("colIndex", $.getColumnNo());
        _.setAttribute("offsetX2", D - B);
        _.style.left = D + ((GT.isFF2 || GT.isFF1) ? 0: C.Ml0) + "px";
        _.style.width = $.headCell.offsetWidth - 1 + "px";
        _.style.display = "block";
        _.innerHTML = "<span style=\"padding-left:2px;\" >" + GT.$getText($.headCell) + "</span>"
    },
    vo8: function(C, G) {
        C = C || window.event;
        var F = GT.U.getPageX(C);
        if (G.separateLine.style.display == "block") {
            var E = F - G.UQ0[0];
            E = E > G.pe6 ? E: G.pe6;
            G.separateLine.style.left = E + "px"
        } else if (!G.customHead && G.headerGhost.style.display == "block") {
            var H = F - G.UQ0[0] + G.headDiv.scrollLeft;
            G.headerGhost.style.left = F + ((GT.isFF2 || GT.isFF1) ? 0: G.Ml0) + G.headerGhost.getAttribute("offsetX2") / 1 + "px";
            var _ = -1,
            A = -1;
            for (var I = 0; I < G.headFirstRow.cells.length; I++) {
                var B = G.headFirstRow.cells[I];
                if (H > B.offsetLeft && H < B.offsetLeft + B.offsetWidth) {
                    _ = B.offsetLeft;
                    A = I;
                    break
                }
            }
            if (H <= B.offsetLeft) I = 0;
            if (_ >= 0) {
                G.columnMoveS.style.left = _ + ((GT.isFF2 || GT.isFF1) ? 0: G.Ml0) + "px";
                G.columnMoveS.style.display = "block"
            } else G.columnMoveS.style.display = "none";
            G.columnMoveS.setAttribute("newColIndex", A)
        } else if (G.Ic1) {
            var D = C.clientX - G.gridGhost.cx,
            $ = C.clientY - G.gridGhost.cy;
            D = D < G.minWidth ? G.minWidth: D;
            $ = $ < G.minHeight ? G.minHeight: $;
            G.gridGhost.style.width = D + "px";
            G.gridGhost.style.height = $ + "px"
        }
    },
    ds5: function(A, E) {
        A = A || window.event;
        E = GT.$grid(E);
        var D = GT.U.getPageX(A);
        E.mouseDown = false;
        if (E.separateLine.style.display == "block") {
            var $ = E.columnMap[E.gi4];
            $.Nf9 = D - E.UQ0[0];
            var C = $.Nf9 - $.ml3,
            B = C + parseInt($.width);
            $.setWidth(B);
            E.gi4 = -1;
            E.separateLine.style.display = E.columnMoveS.style.display = "none";
            E.headDiv.style.cursor = "auto";
            E.hideMask();
            E.syncScroll();
            if (!GT.isOpera) E.Vl4 = false;
            GT.$invoke(E, "afterColumnResize", [$, B, E])
        } else if (!E.customHead && E.headerGhost.style.display == "block") {
            var F = GT.isIE ? A.x: A.pageX,
            G = E.columnMoveS.getAttribute("newColIndex"),
            _ = E.headerGhost.getAttribute("colIndex");
            if (G !== null && (G + "").length > 0 && _ !== null && (_ + "").length > 0) {
                G = G / 1;
                if (G < 0) G = E.columnList.length;
                E.ua9(_ / 1, G / 1);
                E.syncScroll()
            }
            E.columnMoveS.style.display = "none";
            E.columnMoveS.setAttribute("newColIndex", null);
            E.headerGhost.style.display = "none";
            E.headerGhost.setAttribute("colIndex", null);
            E.headerGhost.style.cursor = "auto"
        } else if (E.Ic1) GT.Grid.KR9(A, E)
    },
    xo4: function(_, $) {
        _ = GT.$grid(_);
        var A = _.gridDiv.className.split(" ");
        for (var B = 0; B < A.length; B++) if (A[B].indexOf(GT.Const.Grid.SKIN_CLASSNAME_PREFIX) === 0) A[B] = "";
        A.push(GT.Const.Grid.SKIN_CLASSNAME_PREFIX + $);
        _.gridDiv.className = A.join(" ")
    },
    ar6: function(C, E) {
        var $ = E.id;
        C = GT.$grid(C);
        var B = C.id,
        D = E.checkValid,
        _ = E.checkValue,
        F = E.checkType || "checkbox";
        if (!_) _ = GT.$chk(E.fieldIndex) ? "record[\"" + E.fieldIndex + "\"];": "grid.getUniqueField(record);";
        if (typeof _ == "string") _ = new Function("value", "record", "col", "grid", "colNo", "rowNo", ["return ", _].join(""));
        if (!D) D = function(B, C, A, $, _, E, D) {
            return _.checkedRows[B]
        };
        E.header = "";
        E.title = E.title || C.getMsg("CHECK_ALL");
        E.width = 30;
        E.resizable = false;
        E.printable = false;
        E.sortable = false;
        var A = "gt_" + B + "_chk_" + $;
        E.hdRenderer = function(B, _, $) {
            return "<input type=\"" + F + "\" class=\"gt-f-totalcheck\" name=\"" + A + "\" />"
        };
        E.renderer = function(G, C, $, B, J, H) {
            var E = _(G, C, $, B, J, H),
            I = D(E, G, C, $, B, J, H) ? "checked=\"checked\"": "";
            return "<input type=\"" + F + "\" class=\"gt-f-check\" value=\"" + E + "\" " + I + " name=\"" + A + "\" />"
        };
        return E
    }
});
GT.Grid.prototype.initGrid = GT.Grid.prototype.render;
GT.$extend(GT.Grid, {
    render: function($) {
        $ = GT.$grid($);
        return function() {
            $.render()
        }
    },
    Qe7: function(B, $, C, A) {
        C = C || $.headCell;
        if (!C) return;
        A = A || GT.Grid.tK4($, C);
        var _ = GT.U.nextElement(A),
        D = GT.U.nextElement(_);
        $.hdTool = $.hdTool || GT.Grid.getHdTool($, C);
        $.sortIcon = $.sortIcon || A;
        $.menuButton = $.menuButton || _;
        $.separator = $.separator || D;
        if ($.separator && $.resizable === false) $.separator.style.display = "none";
        GT.U.addEvent(C, "mousedown", 
        function(_) {
            B.gb7();
            if (B.endEdit() === false) return;
            B.closeGridMenu();
            if (!B.customHead) {
                GT.U.stopEvent(_);
                GT.Grid.Ps7(_, $)
            }
        });
        GT.U.addEvent(C, "click", 
        function(A) {
            var D = GT.U.getEventTarget(A);
            if (!B.Vl4) {
                GT.$invoke(B, "onHeadClick", [A, C, $, B]);
                if (GT.U.getTagName(D) == "INPUT" && D.type == "checkbox" && GT.U.hasClass(D, "gt-f-totalcheck")) GT.gh9(D, B, $);
                else if ($.sortable && D.className != "gt-hd-button") {
                    B.lastAction = "sort";
                    B.sorting = true;
                    /* edit by rankin */
                   // var _ = $.sortOrder == "asc" ? "desc": ($.sortOrder ==
					// "desc" ? "defaultsort": "asc"),
                    var _ = "desc",
                    E = B.TG7($);
                    if(B.sortInfo!=null){
                    	if(B.sortInfo[0].fieldName==E.fieldName){
                    		_ = $.sortOrder == "asc" ? "desc": "asc";
                    	}
                    }
                    /* edit end */
                    E.sortOrder = _;
                    B.addSortInfo(E);
                    B.qt7()
                }
            }
            if (GT.isOpera) B.Vl4 = false
        });
        if ($.resizable) {
            D.colID = $.id;
            D.style.cursor = "col-resize";
            GT.U.addEvent(D, "mousedown", 
            function(_) {
                B.gb7();
                GT.U.stopEvent(_);
                GT.Grid.startColumnResize(_, $)
            })
        }
        if (!$.sortable && !$.resizable && $.hdTool) $.hdTool.style.display = "none"
    },
    getHdTool: function($, _) {
        var A = GT.U.firstChildElement(_ || $.headCell);
        return GT.U.Td9(A)
    },
    tK4: function($, _) {
        var A = GT.Grid.getHdTool($, _);
        return GT.U.firstChildElement(A)
    },
    mappingRenderer: function(_, $) {
        return function(A) {
            return _[A] || ($ === undefined || $ === null ? A: $)
        }
    },
    findGridByElement: function(B) {
        var _ = "DIV",
        A = "gt-grid",
        $ = "";
        while ((B = B.parentNode)) if (GT.U.getTagName(B) == _ && GT.U.hasClass(B, A)) {
            $ = B.id;
            break
        }
        if ($.indexOf("_div") === $.length - 4) $ = $.substring(0, $.length - 4);
        return GT.$grid($)
    }
});
var Ext = Ext || null; (Ext && Ext.reg) && (Ext.reg("gtgrid", GT.Grid));
GT.cf6 = function(_, C, $) {
    C = GT.$grid(C);
    _ = GT.$(_);
    if (_.checked == $) return $;
    var B = GT.U.LG4("td", _),
    D = B.parentNode,
    A = C.Yi4(D)[0];
    if ($ === true || $ === false) _.checked = $;
    if (_.checked) {
        C.checkedRows[_.value] = true;
        if (C.selectRowByCheck) C.selectRow(A)
    } else {
        delete C.checkedRows[_.value];
        if (C.selectRowByCheck) C.unselectRow(A)
    }
    return !! _.checked
};
GT.gh9 = function(H, J, G, I) {
    J = GT.$grid(J);
    H = GT.$(H);
    if (I !== null && I !== undefined) H.checked = I;
    var _ = GT.U.LG4("td", H),
    $ = GT.U.getCellIndex(_),
    K = H.checked,
    E = G.frozen ? J.getAllFreezeRows() : J.getAllRows();
    for (var F = 0, C = E.length; F < C; F++) {
        var D = E[F],
        B = D.cells[$];
        if (B) {
            var A = B.getElementsByTagName("input")[0];
            GT.cf6(A, J, K)
        }
    }
};
GT.gO9 = function() {
    if (GT.gO9.inited) return;
    var $ = GT.isIE ? GT.doc.body: GT.doc;
    GT.U.addEvent($, "mousemove", 
    function($) {
        GT.activeGrid && GT.Grid.vo8($, GT.activeGrid)
    });
    GT.U.addEvent($, "mouseup", 
    function($) {
        GT.activeGrid && GT.Grid.ds5($, GT.activeGrid)
    });
    GT.U.addEvent($, "click", 
    function($) {
        GT.activeGrid && (GT.activeGrid.endEdit() || GT.activeGrid.closeGridMenu())
    });
    GT.U.addEvent($, "keydown", 
    function($) {
        GT.activeGrid && GT.activeGrid.GB5($)
    });
    GT.gO9.inited = true
};
GT.ColumnDefault = {
    CLASS_PREFIX: ".",
    destroyList: ["sortIcon", "hdTool", "menuButton", "separator", "frozenSortIcon", "frozenHdTool", "frozenHeadCell", "headCell", "firstCell"],
    id: 0,
    fieldName: null,
    width: 120,
    minWidth: 45,
    header: null,
    styleClass: null,
    align: "left",
    headAlign: "left",
    emptyText: "",
    sortable: true,
    resizable: true,
    moveable: true,
    editable: true,
    hideable: true,
    frozenable: true,
    groupable: true,
    filterable: true,
    printable: true,
    exportable: true,
    exportnumber: false,
    sortOrder: null,
    hidden: false,
    frozen: false,
    toolTip: false,
    beforEdit: null,
    afterEdit: null,
    renderer: function(A, $, _, B, D, C) {
        return A !== null && A !== undefined ? A: _.emptyText
    },
    hdRenderer: function(_, $) {
        return _
    },
    editor: null,
    fieldIndex: 0,
    gridId: null,
    filterField: null,
    newValue: null,
    cellAttributes: "",
    getSortValue: null,
    sortFn: null,
    format: null,
    syncRefresh: true,
    expression: null,
    isExpend: false,
    SO5: function(A, _) {
        var $ = this;
        if (GT.$type(A, "string")) this.id = A;
        else GT.$extend(this, A);
        this.id = this.id || encodeURIComponent(this.header);
        this.header = this.header || this.id;
        this.fieldName = this.fieldName || this.fieldIndex || this.id;
        this.fieldIndex = this.fieldIndex || this.fieldName || this.id;
        this.CLASS_PREFIX = ".gt-grid " + this.CLASS_PREFIX
    },
    destroy: function() {
        if (this.editor && this.editor.destroy) this.editor.destroy();
        this.editor = null;
        GT.$each(this.destroyList, 
        function($, _) {
            GT.U.removeNode(this[$]);
            this[$] = null
        })
    },
    getColumnNo: function() {
        return this.colIndex
    },
    setWidth: function($) {
        var _ = this.grid;
        $ = $ < this.minWidth ? this.minWidth: $;
        this.width = $ + "px";
        GT.U.CSS.updateRule(this.CLASS_PREFIX + this.styleClass, "width", ($ + _.Ut2) + "px");
        GT.U.CSS.updateRule(this.CLASS_PREFIX + this.innerStyleClass, "width", ($ + _.gc3) + "px")
    },
    setHeader: function(_) {
        this.header = _;
        var $ = this.headCell.getElementsByTagName("div")[0];
        if ($) {
            var A = $.getElementsByTagName("span")[0] || $;
            A.innerHTML = _
        }
    },
    hide: function() {
        if (this.frozen) return false;
        GT.U.CSS.updateRule(this.CLASS_PREFIX + this.styleClass, "display", "none");
        this.hidden = true
    },
    show: function() {
        if (this.frozen) return false;
        GT.U.CSS.updateRule(this.CLASS_PREFIX + this.styleClass, "display", "");
        this.hidden = false
    },
    toggle: function() {
        return this.hidden ? this.show() : this.hide()
    },
    group: function($, _) {
        this.grouped = true;
        this.grid.refresh()
    },
    ungroup: function($, _) {
        this.grouped = false;
        this.grid.refresh()
    },
    HP4: function(F, D, $, C, G, B, A, _) {
        if (!A.Sa2) {
            $ = F.cloneNode(false);
            $.id = "";
            $.appendChild(B.cloneNode(true));
            D.appendChild($)
        }
        var E = F.cells[G].cloneNode(true);
        $.appendChild(E);
        if (_ && C === 0) {
            this.frozenHeadCell = E;
            this.frozenSortIcon = GT.Grid.tK4(this, this.frozenHeadCell);
            this.frozenHdTool = GT.Grid.getHdTool(this, this.frozenHeadCell);
            if (!GT.isIE) GT.Grid.Qe7(A, this, this.frozenHeadCell, this.frozenSortIcon)
        }
    },
    freeze: function(B) {
        var A = this.grid,
        G = this.getColumnNo();
        if (!B && G < A.frozenColumnList.length) return false;
        var F = A.headTable.tBodies[0].rows,
        _ = A.freezeHeadTable.tBodies[0].rows,
        $,
        D,
        C = 10;
        if (!A.Sa2) {
            $ = GT.T_G.xm4(A, C, null);
            D = GT.T_G.freezeBodyCell(A, C, null)
        }
        for (var E = 0; E < F.length; E++) this.HP4(F[E], A.freezeHeadTable.tBodies[0], _[E], E, G, $, A, true);
        if (A.rowNum < 1);
        A.uB0 = false;
        if (A.overRow) A.overRow.className = A.overRow.className.replace(" gt-row-over", "");
        F = A.getAllRows();
        _ = A.freezeBodyTable.tBodies[0].rows;
        for (E = 0; E < F.length; E++) this.HP4(F[E], A.freezeBodyTable.tBodies[0], _[E], E, G, D, A);
        if (!B) {
            A.ua9(G, A.frozenColumnList.length);
            A.frozenColumnList.push(this.id)
        }
        this.frozen = true;
        A.freezeHeadDiv.style.display = A.freezeBodyDiv.style.display = "block";
        A.freezeHeadDiv.style.height = A.headDiv.offsetHeight + "px";
        A.freezeBodyDiv.style.height = A.bodyDiv.clientHeight + "px";
        if (!A.Sa2) A.freezeHeadDiv.style.left = A.freezeBodyDiv.style.left = 0 - (C + A.Ut2) + A.freezeFixW + "px";
        A.Sa2 = true;
        A.syncScroll();
        GT.U.removeNode($, D)
    },
    OD5: function(_, $) {
        for (var A = 0; A < _.length; A++) GT.U.removeNodeTree(_[A].cells[$])
    },
    unfreeze: function() {
        var _ = this.grid,
        A = this.getColumnNo();
        if (!_.frozenColumnList || A >= _.frozenColumnList.length) return false;
        this.frozenHeadCell = this.frozenHdTool = this.frozenSortIcon = null;
        _.ua9(A, _.frozenColumnList.length - 1);
        _.frozenColumnList.splice(A, 1);
        var $ = _.freezeHeadTable.tBodies[0].rows;
        this.OD5($, A + 1);
        if (_.rowNum < 1);
        $ = _.freezeBodyTable.tBodies[0].rows;
        this.OD5($, A + 1);
        this.frozen = false;
        if (_.frozenColumnList.length < 1) if (!_.showIndexColumn) _.freezeHeadDiv.style.display = _.freezeBodyDiv.style.display = "none";
        _.syncScroll()
    }
};
GT.Column = GT.$class(GT.ColumnDefault);
GT.Navigator = GT.$class({
    ck2: function() {
        return {
            pageInfo: {
                pageSize: 20,
                pageNum: 1,
                totalRowNum: 0,
                totalPageNum: 1,
                startRowNum: 0,
                endRowNum: 0
            }
        }
    },
    inited: false,
    SO5: function(_) {
        var $ = _.pageInfo || {};
        delete _.pageInfo;
        GT.$extend(this, _);
        GT.$extend(this.pageInfo, $)
    },
    destroy: function() {
        var $ = this,
        _ = ["firstPageButton", "prevPageButton", "nextPageButton", "qM2", "gotoPageButton"];
        GT.$each(_, 
        function(_, A) {
            if ($[_] && $[_].destroy) $[_].destroy();
            $[_] = null
        });
        GT.U.removeNode(this.pageInput);
        this.pageInput = null
    },
    Ul9: function() {
        var $ = GT.$grid(this.gridId);
        this.firstPageButton = new GT.Button({
            container: $.toolBar,
            cls: "gt-first-page",
            onclick: this.sy4,
            onclickArgs: [this]
        });
        this.prevPageButton = new GT.Button({
            container: $.toolBar,
            cls: "gt-prev-page",
            onclick: this.Ae7,
            onclickArgs: [this]
        });
        this.nextPageButton = new GT.Button({
            container: $.toolBar,
            cls: "gt-next-page",
            onclick: this.Nk5,
            onclickArgs: [this]
        });
        this.qM2 = new GT.Button({
            container: $.toolBar,
            cls: "gt-last-page",
            onclick: this.ba2,
            onclickArgs: [this]
        });
        this.inited = true;
        if (!$.loading) this.Md9()
    },
    createGotoPage: function() {
        var B = GT.$grid(this.gridId);
        this.gotoPageButton = new GT.Button({
            container: B.toolBar,
            cls: "gt-goto-page",
            onclick: this.dc5,
            onclickArgs: [this],
            text: B.getMsg("GOTOPAGE_BUTTON_TEXT")
        });
        if (B.toolBar) {
            var _,
            A;
            this.pageInput = GT.$e("input", {
                type: "text",
                className: "gt-page-input"
            });
            var $ = this;
            GT.U.addEvent(this.pageInput, "keydown", 
            function(_) {
                var A = _.keyCode;
                if (A == GT.Const.Key.ENTER) {
                    GT.U.stopEvent(_);
                    $.dc5(_, $)
                }
            });
            _ = GT.$e("div", {
                innerHTML: B.getMsg("PAGE_BEFORE"),
                className: "gt-toolbar-text"
            }),
            A = GT.$e("div", {
                innerHTML: B.getMsg("PAGE_AFTER"),
                className: "gt-toolbar-text"
            });
            B.toolBar.appendChild(_);
            B.toolBar.appendChild(GT.Button.re8(this.pageInput));
            B.toolBar.appendChild(A)
        }
    },
    Md9: function($, B) {
        var A = this.pageInfo = $ || this.pageInfo;
        if (B !== false) {
            if (A.totalRowNum < 1) {
                var _ = GT.$grid(this.gridId);
                A.totalRowNum = _.dataset.getSize()
            }
            A.totalPageNum = Math.ceil(A.totalRowNum / A.pageSize);
            A.pageNum = A.pageNum > A.totalPageNum ? A.totalPageNum: A.pageNum;
            A.pageNum = A.pageNum < 1 ? 1: A.pageNum;
            A.startRowNum = (A.pageNum - 1) * A.pageSize + 1;
            A.startRowNum = isNaN(A.startRowNum) ? 1: A.startRowNum;
            A.endRowNum = A.startRowNum / 1 + A.pageSize / 1 - 1;
            A.endRowNum = A.endRowNum > A.totalRowNum ? A.totalRowNum: A.endRowNum
        }
        return A
    },
    sp7: function($) {
        var A = this.pageInfo = $ || this.pageInfo,
        _ = GT.$grid(this.gridId);
        if (this.inited) {
            if (this.pageInput) {
                this.pageInput.value = A.pageNum;
                this.pageInput.maxLength = ("" + A.totalPageNum).length
            }
            if (A.pageNum == 1) {
                this.firstPageButton.disable();
                this.prevPageButton.disable()
            } else {
                this.firstPageButton.enable();
                this.prevPageButton.enable()
            }
            if (A.pageNum == A.totalPageNum) {
                this.nextPageButton.disable();
                this.qM2.disable()
            } else {
                this.nextPageButton.enable();
                this.qM2.enable()
            }
        }
        if (_ && _.pageSizeSelect) {
            _.pageSizeList = !_.pageSizeList || _.pageSizeList.length < 1 ? [_.pageSize] : _.pageSizeList;
            _.pageSizeSelect.innerHTML = "";
            GT.U.createSelect(GT.U.listToMap(_.pageSizeList), this.pageInfo.pageSize, {},
            _.pageSizeSelect)
        }
    },
    Rn5: function(A, C, $) {
        A = A || this;
        var B = A.pageInfo.pageNum,
        _ = GT.$grid(A.gridId);
        C = (!C || C < 1) ? 1: (C > A.pageInfo.totalPageNum ? A.pageInfo.totalPageNum: C);
        if (GT.$invoke(_, "beforeGotoPage", [C, B, A, _]) !== false) {
            _.lastAction = $;
            _.Rn5(C, B)
        }
    },
    dc5: function($, _) {
        _.Rn5(_, GT.U.parseInt(_.pageInput.value, _.pageInfo.pageNum), "Rn5")
    },
    sy4: function($, _) {
        _.Rn5(_, 1, "firstPage")
    },
    Ae7: function($, _) {
        _.Rn5(_, _.pageInfo.pageNum - 1, "prevPage")
    },
    Nk5: function($, _) {
        _.Rn5(_, _.pageInfo.pageNum + 1, "nextPage")
    },
    ba2: function($, _) {
        _.Rn5(_, _.pageInfo.totalPageNum, "lastPage")
    },
    refreshPage: function($, _) {
        _.Rn5(_, _.pageInfo.pageNum, "refreshPage")
    }
});
GT.BaseMenuItem = GT.$class({
    id: null,
    gridId: null,
    cls: null,
    type: null,
    onclickArgs: null,
    parentItem: null,
    reference: null,
    container: null,
    text: null,
    toolTip: null,
    itemBox: null,
    itemIcon: null,
    itemText: null,
    itemAfterIcon: null,
    subMenu: null,
    SO5: function($) {
        this.disabled = false;
        this.NA8 = false;
        this.overShowSubMenu = true;
        this.onclick = GT.$empty;
        GT.$extend(this, $);
        this.toolTip = this.toolTip || this.text || ""
    },
    destroy: function() {
        if (this.subMenu) this.subMenu.destroy();
        this.container = null;
        this.parentItem = null;
        if (this.separator) {
            GT.U.removeNode(this.separator);
            this.separator = null
        }
        GT.U.removeNode(this.itemIcon);
        this.itemIcon = null;
        GT.U.removeNode(this.itemText);
        this.itemText = null;
        GT.U.removeNode(this.itemAfterIcon);
        this.itemAfterIcon = null;
        GT.U.removeNode(this.itemBox);
        this.itemBox = null
    },
    Xd8: function(B, $) {
        GT.activeGrid && GT.activeGrid.endEdit();
        var A = $.subMenu ? $.subMenu.hidden: false;
        if ($.parentItem) { ($.parentItem.DC0) && $.parentItem.DC0(B);
            if ($.parentItem.currenItem) GT.U.removeClass($.parentItem.currenItem.itemBox, "gt-menu-activemenu");
            $.parentItem.currenItem = $;
            GT.U.addClass($.itemBox, "gt-menu-activemenu")
        }
        if ($.disabled || $.onclick.apply($, [B].concat($.onclickArgs || $)) === false) {
            GT.U.stopEvent(B);
            return
        }
        GT.U.stopEvent(B);
        if ($.type == "checkbox") $.toggleCheck();
        else if ($.type == "radiobox") {
            var _ = $.parentItem.itemList;
            for (var C = 0; C < _.length; C++) if (_[C].type == "radiobox" && _[C] != $) _[C].uncheckMe();
            $.OX8()
        }
        if ($.subMenu) if (A) $.ld3(B);
        else $.Ge5(B)
    },
    DC0: GT.$empty,
    OX8: function() {
        GT.U.removeClass(this.itemIcon, "gt-icon-unchecked");
        GT.U.addClass(this.itemIcon, "gt-icon-" + this.type);
        this.checked = true
    },
    uncheckMe: function() {
        GT.U.removeClass(this.itemIcon, "gt-icon-" + this.type);
        GT.U.addClass(this.itemIcon, "gt-icon-unchecked");
        this.checked = false
    },
    toggleCheck: function() {
        if (this.checked === true) this.uncheckMe();
        else this.OX8()
    },
    disable: function() {
        GT.U.addClass(this.itemBox, "gt-button-disable");
        this.disabled = true
    },
    enable: function() {
        GT.U.removeClass(this.itemBox, "gt-button-disable");
        this.disabled = false
    },
    XP6: function() {
        if (this.subMenu) return this.subMenu.position;
        return null
    },
    rC5: function($) {
        if (this.subMenu && $) this.subMenu.position = $
    },
    ld3: function($) {
        if (this.subMenu) {
            if (!this.XP6()) this.rC5("R");
            this.subMenu.show($)
        }
    },
    toggleMenu: function($) {
        if (this.subMenu) {
            if (!this.XP6()) this.rC5("R");
            this.subMenu.toggle($)
        }
    },
    Ge5: function($) {
        var _ = this;
        while ((_ = _.subMenu)) _.close($)
    },
    ae8: function(_, $) {
        if (_) {
            if (!this.subMenu) {
                this.subMenu = new GT.GridMenu({
                    gridId: this.gridId,
                    parentItem: this,
                    reference: this.itemBox
                });
                this.itemAfterIcon && GT.U.addClass(this.itemAfterIcon, "gt-menu-parent")
            }
            _.gridId = this.gridId;
            this.rC5($);
            this.subMenu.ae8(_)
        }
        return this
    }
});
GT.Button = GT.BaseMenuItem.extend({
    SO5: function(_) {
        this.className = "gt-image-button";
        this.clickClassName = "gt-image-button-down";
        this.vP7(_);
        if (!this.container) return;
        this.itemBox = GT.$e("a", {
            href: "javascript:void(0);return false;",
            className: this.className,
            title: this.toolTip
        });
        this.itemIcon = GT.$e("div", {
            className: this.cls
        });
        this.itemBox.appendChild(this.itemIcon);
        this.container.appendChild(this.itemBox);
        if (this.NA8) GT.Button.bd6(this.container);
        var $ = this;
        GT.U.addEvent($.itemBox, "mousedown", 
        function(_) {
            if (!$.disabled) GT.U.addClass($.itemBox, $.clickClassName)
        });
        GT.U.addEvent($.itemBox, "mouseup", 
        function(_) {
            if (!$.disabled) GT.U.removeClass($.itemBox, $.clickClassName)
        });
        GT.U.addEvent($.itemBox, "click", 
        function(_) {
            $.Xd8(_, $)
        });
        GT.U.addEvent($.itemBox, "dblclick", 
        function(_) {
            $.Xd8(_, $)
        })
    }
});
GT.$extend(GT.Button, {
    bd6: function(_) {
        var $ = GT.$e("div", {
            className: "gt-image-button gt-button-split"
        });
        if (_) _.appendChild($);
        return $
    },
    createCommonButton: function(D, _, A, $, C, B) {
        return new GT.Button({
            id: D,
            container: C,
            cls: _,
            onclick: A,
            onclickArgs: $,
            NA8: B
        })
    },
    re8: function(_) {
        var $ = GT.$e("div", {
            className: "gt-toolbar-comp"
        });
        if (_) if (GT.$type(_, "string", "number")) $.innerHTML = _;
        else $.appendChild(_);
        return $
    }
});
GT.MenuItem = GT.BaseMenuItem.extend({
    SO5: function(_) {
        this.vP7(_);
        if (this.type == "checkbox" || this.type == "radiobox") this.cls = this.checked ? ("gt-icon-" + this.type) : "gt-icon-unchecked";
        this.itemBox = GT.$e("a", {
            href: "javascript:void(0);return false;",
            className: "gt-menuitem"
        });
        this.itemIcon = GT.$e("div", {
            className: "gt-menu-icon " + this.cls
        });
        this.itemText = GT.$e("div", {
            className: "gt-checkboxtext",
            innerHTML: this.text,
            title: this.toolTip
        });
        this.itemAfterIcon = GT.$e("div", {
            className: "gt-aftericon " + this.afterIconClassName
        });
        this.itemBox.appendChild(this.itemIcon);
        this.itemBox.appendChild(this.itemText);
        this.itemBox.appendChild(this.itemAfterIcon);
        var $ = this;
        GT.U.addEvent($.itemBox, "click", 
        function(_) {
            $.Xd8(_, $)
        })
    }
});
GT.$extend(GT.MenuItem, {
    bd6: function(_) {
        var $ = GT.$e("div", {
            className: "gt-image-button gt-button-split"
        });
        if (_) _.appendChild($);
        return $
    }
});
GT.GridMenu = GT.$class({
    gridId: null,
    parentItem: null,
    container: null,
    Xl3: null,
    aE5: null,
    destroy: function() {
        this.container = null;
        this.parentItem = null;
        this.itemList;
        GT.$each(this.itemList, 
        function(_, A, $) {
            GT.U.removeNode(_);
            $[A] = null
        })
    },
    SO5: function(_) {
        this.itemList = [];
        this.refreshOnShow = false;
        this.currenItem = null;
        this.hidden = true;
        this.className = "gt-popmenu";
        this.position = "";
        GT.$extend(this, _);
        this.menuBox = GT.$e("div", {
            className: this.className,
            style: {
                display: "none",
                left: "10px",
                top: "10px"
            }
        });
        var $ = GT.$grid(this.gridId) || {};
        this.container = this.container || $.gridDiv || GT.doc.body;
        this.container.appendChild(this.menuBox)
    },
    refresh: function() {},
    onshow: function() {},
    El6: function() {},
    ae8: function($) {
        $ = [].concat($);
        for (var _ = 0; _ < $.length; _++) if ($[_]) {
            $[_].gridId = this.gridId;
            $[_].parentItem = this;
            $[_].container = this.menuBox;
            this.itemList.push($[_]);
            this.menuBox.appendChild($[_].itemBox)
        }
        return this
    },
    show: function($) {
        if (this.container && this.container.parentNode && this.container.parentNode.className.indexOf("menu") > 1);
        this.menuBox.style.display = "block";
        var B,
        _,
        A = GT.U.getXY(this.reference, this.container);
        B = A[0];
        _ = A[1];
        this.Xl3 = this.Xl3 || 0;
        this.aE5 = this.aE5 || 0;
        switch (this.position.toUpperCase()) {
        case "L":
            B -= this.menuBox.offsetWidth;
            break;
        case "T":
            _ -= this.menuBox.offsetHeight;
            break;
        case "R":
            B += this.reference.offsetWidth;
            break;
        case "B":
            _ += this.reference.offsetHeight;
            break;
        case "LT":
            B -= this.menuBox.offsetWidth;
            _ -= this.menuBox.offsetHeight - this.reference.offsetHeight;
            break;
        case "RT":
            B += this.reference.offsetWidth;
            _ -= this.menuBox.offsetHeight - this.reference.offsetHeight;
            break;
        case "RB":
            B += this.reference.offsetWidth;
            _ += this.reference.offsetHeight;
            break;
        case "LB":
            B -= this.reference.offsetWidth;
            _ += this.menuBox.offsetHeight;
            break;
        case "M":
            B = $.pageX || ($.clientX - $.x);
            _ = $.pageY || ($.clientY - $.y);
            break;
        default:
            _ += this.reference.offsetHeight
        }
        GT.U.setXY(this.menuBox, [B + this.Xl3, _ + this.aE5]);
        this.hidden = false
    },
    close: function($) {
        this.DC0($);
        this.menuBox.style.display = "none";
        this.hidden = true
    },
    DC0: function($) {
        for (var _ = 0; _ < this.itemList.length; _++) this.itemList[_].Ge5($)
    },
    toggle: function($) {
        GT.U.stopEvent($);
        var _ = GT.$grid(this.gridId);
        if (this.hidden === true) this.show($);
        else this.close($)
    }
});
GT.ToolFactroy = {
    register: function(_, $) {
        GT.ToolFactroy.tools[_] = $
    },
    create: function(D, E, _) {
        if (_ == false) return false;
        D = GT.$grid(D);
        var $ = D;
        if (E == "info" || E == "pagestate") E = "state";
        var C = GT.ToolFactroy.tools[E];
        if (C && GT.$type(C, "function")) C = C(D, E, _);
        else if (C) {
            var A = C.name || E,
            B = C.onclick || C.action;
            C = new GT.Button({
                container: C.container || D.toolBar,
                cls: C.cls || "gt-tool-" + A,
                toolTip: C.toolTip || D.getMsg("TOOL_" + A.toUpperCase()),
                onclick: function($) {
                    B($, D)
                }
            })
        }
        return C
    },
    tools: {
        "goto": function($) {
            return $.navigator.createGotoPage()
        },
        "pagesize": function(A) {
            var _ = GT.U.createSelect({});
            _.className = "gt-pagesize-select";
            A.pageSizeSelect = _;
            function $(B) {
                A.setPageInfo({
                    pageSize: _.value / 1
                });
                A.navigator.sy4(B, A.navigator);
                A.pageSizeSelect.blur();
                try {
                    A.bodyDiv.focus()
                } catch($) {}
            }
            GT.U.addEvent(A.pageSizeSelect, "change", $);
            text1 = GT.$e("div", {
                innerHTML: A.getMsg("PAGESIZE_BEFORE"),
                className: "gt-toolbar-text"
            });
            text2 = GT.$e("div", {
                innerHTML: A.getMsg("PAGESIZE_AFTER"),
                className: "gt-toolbar-text"
            });
            A.toolBar.appendChild(text1);
            A.toolBar.appendChild(GT.Button.re8(A.pageSizeSelect));
            A.toolBar.appendChild(text2);
            return _
        },
        "add": {
            onclick: function($, _) {
                _.add()
            }
        },
        "del": {
            onclick: function($, _) {
                _.del()
            }
        },
        "save": {
            onclick: function($, _) {
                _.lastAction = "save";
                _.save()
            }
        },
        "reload": {
            onclick: function($, _) {
                _.lastAction = "reload";
                _.reload()
            }
        },
        "print": {
            onclick: function($, _) {
                _.lastAction = "print";
                _.printGrid()
            }
        },
        "xls": {
            onclick: function($, _) {
                _.lastAction = "export";
                _.exportGrid("xls")
            }
        },
        "pdf": {
            onclick: function($, _) {
                _.lastAction = "export";
                _.exportGrid("pdf")
            }
        },
        "csv": {
            onclick: function($, _) {
                _.lastAction = "export";
                _.exportGrid("csv")
            }
        },
        "xml": {
            onclick: function($, _) {
                _.lastAction = "export";
                _.exportGrid("xml")
            }
        },
        "filter": {
            onclick: function($, _) {
                _.lastAction = "filter";
                _.showDialog("filter")
            }
        },
        "chart": {
            onclick: function($, _) {
                _.showDialog("chart")
            }
        },
        "state": function(_) {
            var $ = GT.$e("div", {
                innerHTML: "&#160;",
                className: "gt-page-state"
            });
            _.toolBar.appendChild($);
            return $
        },
        "separator": function(_) {
            var $ = GT.Button.bd6(_.toolBar);
            return $
        },
        "fill": function(_) {
            var $ = "";
            return $
        }
    }
};
GT.Widget = GT.$class({
    id: null,
    dom: null,
    setDom: function($) {
        this.dom = $
    },
    AO9: function() {
        return this.dom
    },
    show: function() {
        this.dom && (this.dom.style.display = "block")
    },
    hide: function() {
        this.dom && (this.dom.style.display = "none")
    },
    close: function() {
        this.hide()
    },
    Au8: function(_, $) {
        if (_ || _ === 0) {
            this.left = _;
            this.dom && (this.dom.style.left = this.left + "px")
        }
        if ($ || $ === 0) {
            this.top = $;
            this.dom && (this.dom.style.top = this.top + "px")
        }
    },
    setSize: function($, _) {
        this.width = $ || this.width;
        this.height = _ || this.height;
        if (!this.dom) return;
        if (this.width / 1 && this.width > 0) this.dom.style.width = (this.width - 1) + "px";
        if (this.height / 1 && this.height > 0) this.dom.style.height = (this.height - 1) + "px"
    },
    destroy: function() {
        GT.$invoke(this, "pa5");
        GT.U.removeNode(this.dom);
        this.dom = null
    }
});
GT.DialogDefault = {
    mu8: true,
    autoRerender: true,
    title: null,
    body: null,
    buttonZone: null,
    headHeight: 20,
    hidden: false,
    SO5: function($) {
        if ($) GT.$extend(this, $);
        this.domId = (this.gridId ? this.gridId + "_": "") + this.id;
        this.buttonLayout = this.buttonLayout || "h";
        this.dialogId = this.id,
        GT.WidgetCache[this.id] = this
    },
    destroy: function() {
        this.container = null;
        GT.U.removeNode(this.bodyDiv);
        this.bodyDiv = null;
        GT.U.removeNode(this.dom);
        this.dom = null;
        GT.WidgetCache[this.id] = null;
        delete GT.WidgetCache[this.id]
    },
    titleRender: function($) {
        this.title = $ || this.title;
        return this.title
    },
    show: function() {
        var $ = GT.$grid(this.gridId);
        $.closeGridMenu();
        if (GT.$invoke(this, "beforeShow", [$]) !== false) {
            if (GT.isIE) GT.eachGrid(function(_) {
                if (_ != $) {
                    _.gridDiv.style.position = _.viewport.style.position = "static";
                    _.Ml9 && (_.Ml9.style.position = "static")
                }
            });
            this.locked = true;
            $.showMask();
            this.autoRerender && this.render($.gridMask);
            $.gridMask.appendChild(this.dom);
            if (this.width / 1 && this.width > 0) this.dom.style.marginLeft = (0 - this.width / 2) + "px";
            this.dom.style.marginTop = "0px";
            this.dom.style.top = "25px";
            this.dom.style.display = "block";
            $.activeDialog = this;
            this.hidden = false;
            GT.$invoke(this, "afterShow", [$])
        }
    },
    hide: function() {
        var $ = GT.$grid(this.gridId);
        if (GT.$invoke(this, "beforeHide", [$]) !== false) {
            this.locked = false;
            $.hideMask();
            if (this.dom) {
                this.dom.style.display = "none";
                $.gridEditorCache.appendChild(this.dom)
            }
            $.activeDialog = null;
            this.hidden = true;
            if (GT.isIE) GT.eachGrid(function(_) {
                if (_ != $) {
                    _.gridDiv.style.position = _.viewport.style.position = "relative";
                    _.Ml9 && (_.Ml9.style.position = "relative")
                }
            });
            GT.$invoke(this, "afterHide", [$])
        }
    },
    close: function() {
        var $ = GT.$grid(this.gridId);
        this.hide()
    },
    confirm: function() {
        var $ = GT.$grid(this.gridId);
        if ($.activeEditor == this) {
            this.locked = false;
            $.endEdit();
            $.activeEditor == this
        }
    },
    render: function($) {
        this.container = $ || this.container;
        if (!this.rendered) {
            this.dom = this.dom || GT.$e("div", {
                className: "gt-grid-dialog"
            });
            this.dom.id = this.domId + "_dialog";
            this.container = this.container || GT.doc.body;
            this.container.appendChild(this.dom);
            this.dom.innerHTML = GT.T_D.create(this);
            this.titleDiv = GT.$(this.domId + "_dialog_title");
            this.bodyDiv = GT.$(this.domId + "_dialog_body");
            if (this.height) this.bodyDiv.style.height = this.height - (this.headHeight || 0) + "px";
            this.setBody();
            this.setButtons();
            this.setTitle();
            GT.$invoke(this, "afterRender", [this])
        }
        this.setSize();
        this.Au8();
        if (GT.$type(this.valueDom, "function")) this.valueDom = this.valueDom();
        this.valueDom = GT.$(this.valueDom);
        this.rendered = true
    },
    setBody: function($) {
        var _ = GT.$grid(this.gridId);
        this.body = $ || this.body;
        this.bodyDiv.innerHTML = "";
        if (GT.$type(this.body, "function")) this.body = this.body(_);
        if (!this.body);
        else if (GT.$type(this.body, "string")) this.bodyDiv.innerHTML = this.body;
        else this.bodyDiv.appendChild(this.body)
    },
    setButtons: function(_) {
        this.buttons = _ || this.buttons;
        if (!this.buttons) return;
        _ = [].concat(this.buttons);
        if (_.length > 0) {
            this.buttonZone = this.buttonZone || GT.$e("div", {
                className: "gt-dialog-buttonzone-" + this.buttonLayout,
                id: this.domId + "_div"
            });
            if (this.buttonLayout == "h") this.buttonZone.style.width = this.width - 12 + "px";
            for (var A = 0; A < _.length; A++) {
                var $ = null;
                if (_[A].breakline) $ = GT.$e("br");
                else if (_[A].html) $ = GT.$e("span", {
                    innerHTML: _[A].html
                });
                else {
                    $ = GT.$e("button", {
                        id: this.domId + "_" + _[A].id,
                        className: "gt-input-button",
                        innerHTML: _[A].text
                    });
                    GT.U.addEvent($, "click", _[A].onclick)
                }
                this.buttonZone.appendChild($)
            }
        }
        this.bodyDiv.appendChild(this.buttonZone)
    },
    setTitle: function($) {
        this.titleDiv.innerHTML = this.titleRender($)
    }
};
GT.Dialog = GT.Widget.extend(GT.DialogDefault);
GT.aL9 = function(C, $) {
    C = GT.$grid(C);
    $ = $ || {};
    $.checkType = $.checkType || "checkbox";
    $.canCheck = $.canCheck || 
    function($) {
        return ! $.hidden
    };
    function A($, _) {
        var A = $.canCheck === true || $.canCheck(_) !== false;
        return "<input type=\"" + $.checkType + "\" name=\"" + $.name + "\" value=\"" + $.value(_) + "\" class=\"gt-f-check\" " + ($.checked(_) ? " checked=\"checked\" ": "") + (!A ? " disabled=\"disabled\" ": "") + " />"
    }
    function B($) {
        return $.checkType == "checkbox" ? "<input type=\"checkbox\" name=\"" + $.name + "\" class=\"gt-f-totalcheck\" />": "<input type=\"radio\" name=\"" + $.name + "\" />"
    }
    var _ = C.columnList,
    D = ["<table class=\"gt-table\" style=\"margin-left:0px\" cellSpacing=\"0\"  cellPadding=\"0\" border=\"0\" >", "<col style=\"width:25px\" /><col style=\"width:105px\" />", "<thead>", GT.T_G.rowStart(C, 0), GT.T_G.cellStartHTML, B($), GT.T_G.cellEndHTML, GT.T_G.cellStartHTML, C.getMsg("COLUMNS_HEADER"), GT.T_G.cellEndHTML, GT.T_G.rowEndHTML, "</thead>", "<tbody>"];
    for (var E = 0; E < _.length; E++) D.push([GT.T_G.rowStart(C, E), GT.T_G.cellStartHTML, A($, _[E]), GT.T_G.cellEndHTML, GT.T_G.cellStartHTML, _[E].header || _[E].title, GT.T_G.cellEndHTML, GT.T_G.rowEndHTML].join(""));
    D.push("</tbody></table>");
    return D.join("\n")
};
GT.checkChecked = function(D) {
    D = GT.$grid(D);
    var F = D.chkAll,
    _ = GT.U.LG4("td", F),
    $ = GT.U.getCellIndex(_),
    H = D.getAllRows(),
    C = 0;
    for (var G = 0, E = H.length; G < E; G++) {
        var B = H[G].cells[$];
        if (B) {
            var A = B.getElementsByTagName("input")[0];
            if (A && D.checkedRows[A.value]) {
                A.checked = true;
                C++
            }
        }
    }
    F.checked = C == H.length
};
GT.lr2 = function(C, E) {
    var $ = C + "ColCheck",
    _ = E.gridId,
    G = _ + "_" + C + "ColDialog",
    D = GT.$grid(_),
    B = function() {
        var H = GT.$(G + "_div"),
        B = (GT.U.getTagName(H) == "TABLE") ? H: H.getElementsByTagName("table")[0],
        F = B.tBodies[0],
        A = F.getElementsByTagName("input"),
        C = GT.U.getCheckboxState(A, $),
        I = [];
        for (var J = 0; J < D.columnList.length; J++) I.push(D.columnList[J].id);
        for (J = 0; J < I.length; J++) {
            var _ = D.columnMap[I[J]];
            if (C[_.id]) _[E.checkFn]();
            else _[E.uncheckFn]()
        }
        if (E.autoClose !== false) {
            D.Ci9();
            GT.WidgetCache[G].close()
        }
    },
    A = function() {
        GT.WidgetCache[G].close()
    },
    F = new GT.Dialog({
        id: G,
        gridId: _,
        title: E.title,
        width: 260,
        height: 220,
        buttonLayout: "v",
        body: ["<div id=\"" + G + "_div" + "\" onclick=\"GT.NP2.Qi7(event)\" class=\"gt-column-dialog\" >", "</div>"].join(""),
        buttons: [{
            text: D.getMsg("TEXT_OK"),
            onclick: B
        },
        {
            text: D.getMsg("TEXT_CLOSE"),
            onclick: A
        }],
        afterShow: function() {
            var A = GT.$grid(this.gridId),
            _ = GT.aL9(this.gridId, {
                type: "checkbox",
                name: $,
                value: function($) {
                    return $.id
                },
                checked: E.checkValid,
                checkType: E.checkType,
                canCheck: E.canCheck
            });
            GT.$(this.id + "_div").innerHTML = _
        }
    });
    return F
};
GT.Vc8 = function(I) {
    var B = I.gridId,
    H = GT.$grid(B),
    K = B + "_filterDialog";
    H.justShowFiltered = I.justShowFiltered === true ? true: (I.justShowFiltered === false ? false: H.justShowFiltered);
    H.afterFilter = I.afterFilter || H.afterFilter;
    var A = function() {
        if (H._noFilter) {
            F();
            H._noFilter = false
        }
        var $ = GT.$(K + "_column_select");
        if ($ && $.options.length > 0) {
            var _ = $.value,
            A = $.options[$.selectedIndex].text;
            GT.$(K + "_div").appendChild(GT.dX2(H, _, A))
        }
    },
    F = function() {
        GT.$(K + "_div").innerHTML = ""
    },
    D = function() {
        var F = GT.$(K + "_div"),
        D = [],
        C = F.childNodes;
        for (var J = 0; J < C.length; J++) if (GT.U.getTagName(C[J]) == "DIV" && C[J].className == "gt-filter-item") {
            var A = C[J].childNodes[1],
            B = C[J].childNodes[2],
            _ = C[J].childNodes[3].firstChild,
            G = GT.U.getValue(A),
            $ = H.columnMap[G];
            if ($ && $.fieldName) D.push({
                columnId: G,
                fieldName: $.fieldName,
                logic: GT.U.getValue(B),
                value: GT.U.getValue(_)
            })
        }
        if (D.length > 0) var E = H.filterGrid(D);
        else H.unfilterGrid();
        if (I.autoClose !== false) {
            H.Ci9();
            GT.WidgetCache[K].close()
        }
    },
    C = function() {
        GT.WidgetCache[K].close()
    },
    $ = 430,
    E = 220,
    G = $ - (GT.isBoxModel ? 16: 18),
    _ = E - (GT.isBoxModel ? 93: 95),
    J = new GT.Dialog({
        id: K,
        gridId: B,
        title: I.title,
        width: $,
        height: E,
        buttonLayout: "h",
        body: ["<div id=\"" + K + "_div\" class=\"gt-filter-dialog\" style=\"width:" + G + "px;height:" + _ + "px;\" onclick=\"GT.NP2.At4(event)\" >", "</div>"].join(""),
        buttons: [{
            html: GT.Ni3(H, K + "_column_select")
        },
        {
            text: H.getMsg("TEXT_ADD_FILTER"),
            onclick: A
        },
        {
            text: H.getMsg("TEXT_CLEAR_FILTER"),
            onclick: F
        },
        {
            breakline: true
        },
        {
            text: H.getMsg("TEXT_OK"),
            onclick: D
        },
        {
            text: H.getMsg("TEXT_CLOSE"),
            onclick: C
        }],
        afterShow: function() {
            var E = GT.$grid(this.gridId),
            D = E.filterInfo || [];
            F();
            for (var I = 0; I < D.length; I++) {
                var G = D[I].columnId,
                A = E.getColumn(G),
                H = (A.header || A.title),
                C = GT.dX2(E, G, H),
                _ = C.childNodes[1],
                B = C.childNodes[2],
                $ = C.childNodes[3].firstChild;
                GT.U.setValue(_, G);
                GT.U.setValue(B, D[I].logic);
                GT.U.setValue($, D[I].value);
                GT.$(this.id + "_div").appendChild(C)
            }
            if (D.length < 1) {
                GT.$(this.id + "_div").innerHTML = "<div style=\"color:#999999;margin:10px;\">" + E.getMsg("DIAG_NO_FILTER") + "</div>";
                E._noFilter = true
            }
        }
    });
    return J
};
GT.Ni3 = function(B, $) {
    B = GT.$grid(B);
    var _ = ["<select" + ($ ? (" id=\"" + $ + "\" ") : " ") + " class=\"gt-input-select\">"];
    for (var C = 0; C < B.columnList.length; C++) {
        var A = B.columnList[C];
        if (A && A.filterable !== false) _.push("<option value=\"" + A.id + "\" >" + (A.header || A.title) + "</option>")
    }
    _.push("</select>");
    return _.join("")
};
GT.Ip6 = function(A, B) {
    A = GT.$grid(A);
    var _,
    $ = A.getColumn(B);
    if (typeof $.filterField == "function") _ = $.filterField($);
    else if ($.filterField) _ = $.filterField;
    _ = _ || "<input type=\"text\" class=\"gt-input-text gt-filter-field-text\" value=\"\" />";
    return "<div class=\"gt-filter-field-box\">" + _ + "</div>"
};
GT.dX2 = function(C, D, E) {
    C = GT.$grid(C);
    var B = GT.$e("div", {
        className: "gt-filter-item"
    }),
    A = "<input type=\"text\" readonly=\"readonly\" class=\"gt-input-text gt-filter-col-text\" value=\"" + E + "\" />";
    A += "<input type=\"hidden\"  value=\"" + D + "\" />";
    var $ = GT.Ip6(C, D),
    _ = "<button class=\"gt-input-button gt-filter-del\" >" + C.getMsg("TEXT_DEL") + "</button>" + "<button class=\"gt-input-button gt-filter-up\" >" + C.getMsg("TEXT_UP") + "</button>" + "<button class=\"gt-input-button gt-filter-down\" >" + C.getMsg("TEXT_DOWN") + "</button>";
    B.innerHTML = A + GT.T_D.Hg6 + $ + _;
    return B
};
GT.NP2 = {
    currentElement: null,
    At4: function(A) {
        A = A || window.event;
        var E = GT.U.getEventTarget(A),
        _ = GT.U.LG4("table", null, A, 10);
        if (GT.U.getTagName(E) == "BUTTON") {
            var D = " " + E.className,
            $ = E.parentNode;
            if (D.indexOf(" gt-filter-del") >= 0) GT.U.removeNode($);
            else if (D.indexOf(" gt-filter-up") >= 0) {
                var B = $.previousSibling;
                if (B) $.parentNode.insertBefore($, B)
            } else if (D.indexOf(" gt-filter-down") >= 0) {
                var C = $.nextSibling;
                if (C) $.parentNode.insertBefore(C, $)
            }
        }
    },
    Qi7: function(A) {
        A = A || window.event;
        var D = GT.U.getEventTarget(A),
        _ = GT.U.LG4("table", null, A, 10);
        if (!D || (D.type != "checkbox" && D.type != "radio")) return;
        if (GT.U.hasClass(D, "gt-f-totalcheck")) {
            var C = _.tBodies[0],
            $ = C.getElementsByTagName("input");
            for (var F = 0; F < $.length; F++) if ($[F].name == D.name && $[F].type == D.type) $[F].checked = D.checked
        } else if (GT.U.hasClass(D, "gt-f-check")) {
            var B = _.tHead,
            E = B.getElementsByTagName("input")[0];
            if (E) E.checked = false
        }
    }
};
GT.EditorDefault = {
    gridId: null,
    left: 0,
    top: 0,
    render: GT.$empty,
    validator: null,
    isFocus: GT.$empty,
    onKeyPress: GT.$empty,
    errMsg: null,
    isActive: null,
    valueDom: null,
    locked: false,
    SO5: function($) {
        if ($) GT.$extend(this, $);
        this.validator = this.validator || this.defaultValidator;
        if (GT.$type(this.validRule, "string")) this.validRule = this.validRule.split(",");
        if (this.required) this.validRule = ["required"].concat(this.validRule);
        this.dom = this.dom || GT.$e("div", {
            className: "gt-editor-container"
        });
        GT.U.addEvent(this.dom, "click", 
        function($) {
            GT.U.stopEvent($)
        });
        GT.U.addEvent(this.dom, "dblclick", 
        function($) {
            GT.U.stopEvent($)
        })
    },
    destroy: function() {
        this.container = null;
        GT.U.removeNode(this.valueDom);
        this.valueDom = null;
        GT.U.removeNode(this.dom);
        this.dom = null;
        GT.WidgetCache[this.id] = null;
        delete GT.WidgetCache[this.id]
    },
    setValue: function(B, _, $, C, E, D, A) {
        GT.U.setValue(this.valueDom, B)
    },
    getValue: function(B, _, $, C, E, D, A) {
        return GT.U.getValue(this.valueDom)
    },
    parseValue: function(B, _, $, C, E, D, A) {
        return B
    },
    getDisplayValue: function($) {
        return $ === undefined ? this.getValue() : $
    },
    defaultValidator: function(H, D, C, I, E) {
        var F = [],
        K = [].concat(E.validRule);
        for (var B = 0; B < K.length; B++) {
            var G = K[B],
            J = [H];
            if (GT.$type(G, "array") && G.length > 0) {
                G = G[0];
                J = J.concat(G.slice(1))
            }
            var $ = GT.Validator.getValidator(G),
            A = true;
            if (GT.$type($, "function")) A = $.apply($, J);
            if (A !== true) {
                var _ = GT.Validator.getMessage(this.validRule[B]) || String(A);
                F.push(_)
            }
        }
        if (!F || F.length < 1) F = "";
        return F
    },
    us5: function(A, _, $, B) {
        if (!this.validRule && !this.validator) return true;
        A = (A === undefined || A === null) ? this.getValue() : A;
        var C = this.validator(A, _, $, B, this);
        if (C === true || C === undefined || C === null || C === "") return true;
        return C
    },
    active: function() {
        GT.U.focus(this.valueDom)
    }
};
GT.Editor = GT.Widget.extend(GT.EditorDefault);
GT.DialogEditor = GT.Editor.extend(GT.$extend({
    AO9: function() {
        if (!this.dom && this.render) {
            var $ = GT.$grid(this.gridId);
            this.render($.gridMask)
        }
        return this.dom
    }
},
GT.DialogDefault));
GT.EditDialog = GT.DialogEditor;
GT.Calendar = window.Calendar || {
    trigger: GT.$empty
};
GT.$extend(GT.Editor, {
    Ol7: function($, _) {
        if (GT.$type($, "function")) $ = $(_);
        if (($ instanceof GT.DialogEditor) || ($ instanceof GT.Dialog)) {
            $.gridId = _.id;
            $.container = _.gridMask;
            return $
        }
        if ($ instanceof GT.Editor) return $;
        $ = GT.$type($, "string") ? {
            type: $
        }: $;
        return $ && GT.Editor.EDITORS[$.type] ? GT.Editor.EDITORS[$.type]($) : null
    },
    register: function(_, $) {
        if ($ instanceof GT.Editor) $ = function() {
            return $
        };
        GT.Editor.EDITORS[_] = $
    },
    EDITORS: {
        text: function($) {
            $ = new GT.Editor($);
            $.valueDom = GT.$e("input", {
                type: "text",
                value: $.defaultValue || "",
                className: "gt-editor-text"
            });
            $.dom.appendChild($.valueDom);
            return $
        },
        textarea: function($) {
            $ = new GT.Editor($);
            $.valueDom = GT.$e("textarea", {
                style: {
                    width: $.width || "100px",
                    height: $.height || "50px"
                },
                value: $.defaultValue || "",
                className: "gt-editor-text"
            });
            $.dom.appendChild($.valueDom);
            $.dom.style.width = $.valueDom.style.width;
            $.dom.style.height = $.valueDom.style.height;
            $.setSize = GT.$empty;
            return $
        },
        select: function($) {
            $ = new GT.Editor($);
            $.valueDom = GT.U.createSelect($.options, null, {
                className: "gt-editor-text"
            });
            $.dom.appendChild($.valueDom);
            $.getDisplayValue = function($) {
                $ = $ === undefined ? this.getValue() : $;
                for (var _ = 0; _ < this.valueDom.options.length; _++) if (String(this.valueDom.options[_].value) === String($)) return this.valueDom.options[_].text || this.valueDom.options[_].innerHTML;
                return (this.defaultText || this.defaultText === "") ? this.defaultText: null
            };
            return $
        },
        checkbox: function() {
            editor = new GT.Editor(editor);
            editor.valueDom = GT.U.createSelect(editor.options, null, {});
            editor.dom.appendChild(editor.valueDom);
            return editor
        },
        date: function(_) {
            _ = new GT.Editor(_);
            var $ = GT.$e("input", {
                type: "text",
                value: _.defaultValue || "",
                className: "gt-editor-text",
                style: {
                    width: "78px",
                    styleFloat: "left"
                }
            }),
            A = GT.$e("input", {
                type: "button",
                value: _.defaultValue || "",
                className: "gt-editor-date",
                styleFloat: "left"
            });
            _.dom.style.overflow = "hidden";
            _.dom.appendChild($);
            _.dom.appendChild(A);
            _.setSize = function($, _) {
                this.width = $ || this.width;
                this.height = _ || this.height;
                if (this.width / 1 && this.width > 0) this.dom.style.width = (this.width - 1) + "px";
                if (this.height / 1 && this.height > 0) this.dom.style.height = (this.height - 1) + "px";
                this.dom.firstChild.style.width = (this.width - 20) + "px"
            };
            var B = function($) {
                _.onClose && _.onClose();
                $.hide()
            },
            C = function() {
                var A = _.format || "%Y-%m-%d";
                A = GT.U.replaceAll(A, "yyyy", "%Y");
                A = GT.U.replaceAll(A, "MM", "%m");
                A = GT.U.replaceAll(A, "dd", "%d");
                A = GT.U.replaceAll(A, "HH", "%H");
                A = GT.U.replaceAll(A, "mm", "%M");
                A = GT.U.replaceAll(A, "ss", "%S");
                GT.Calendar.trigger({
                    inputField: $,
                    ifFormat: A,
                    showsTime: true,
                    button: "date_button",
                    singleClick: true,
                    onClose: B,
                    step: 1
                })
            };
            GT.U.addEvent(A, "click", C);
            _.valueDom = $;
            return _
        }
    }
});
GT.Validator = {
    hasDepend: /^datetime|^date|^time|^minlength|^maxlength|^DT|^D|^T|^MINL|^MAXL/,
    hasArgument: /^equals|^lessthen|^EQ|^LT/,
    DATE_FORMAT: "yyyy-MM-dd",
    TIME_FORMAT: "HH:mm:ss",
    DATETIME_FORMAT: "yyyy-MM-dd HH:mm:ss",
    KeyMapping: {
        "R": "required",
        "DT": "datetime",
        "D": "date",
        "T": "time",
        "E": "email",
        "ID": "idcard",
        "N": "number",
        "int": "integer",
        "I": "integer",
        "F": "float",
        "M": "money",
        "RG": "range",
        "EQ": "equals",
        "LT": "lessthen",
        "GT": "greatethen",
        "U": "url",
        "ENC": "enchar",
        "CNC": "cnchar",
        "MINL": "minlength",
        "MAXL": "maxlength"
    },
    RegExpLib: {
        "email": /\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*/,
        "number": /^\d+$/,
        "integer": /^[1-9]\d*|0$/,
        "float": /^([1-9]\d*\.\d+|0\.\d+|[1-9]\d*|0)$/,
        "money": /^([1-9]\d*\.\d{1,2}|0\.\d{1,2}|[1-9]\d*|0)$/,
        "telephone": /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,16})+$/,
        "enchar": /^[ \w]*$/,
        "cnchar": /^[\u4E00-\u9FA5\uF900-\uFA2D]*$/,
        "idcard": /^(\d{15}|\d{18}|\d{17}X?)$/i
    },
    getValidator: function($) {
        return GT.Validator[$]
    },
    getMessage: function(msgKey) {
        var msg = GT.Msg.Validator["default"][msgKey];
        if (!msg) msg = GT.Msg.Validator["default"][GT.Validator.KeyMapping[msgKey]];
        var _format = ((GT.Validator.KeyMapping[msgKey] || msgKey) + "_FORMAT").toUpperCase();
        _format = GT.Validator[_format];
        var Yb8 = (" " + msg).split(/\{[0-9]/).length - 1;
        for (var i = 1; i <= Yb8; i++) {
            var ns = arguments[i];
            if (i == 2) ns = ns || _format;
            var rex;
            eval("rex = /{(" + (i - 1) + "[^#}]*)#?([^}]*)}/;");
            var ostring = rex.exec(msg);
            if (ostring && ostring.length > 2) if (!ns) msg = GT.U.replaceAll(msg, ostring[0], " " + ostring[2] + " ");
            else msg = GT.U.replaceAll(msg, ostring[0], " " + ns + " ")
        }
        return msg
    },
    "required": function($) {
        if ($ === null || $ === undefined) return false;
        if (typeof($) != "string" && $.length) {
            if ($.length < 1) return false;
            for (var A = 0; A < $.length; A++) {
                var _ = GT.Validator.required($[A]);
                if (_) return true
            }
            return false
        }
        return GT.U.trim($ + "").length > 0
    },
    "telephone": function($) {
        if (!GT.Validator.RegExpLib.telephone.exec($)) return false;
        return true
    },
    "email": function($) {
        return $ && GT.Validator.RegExpLib["email"].test($)
    },
    "enchar": function($) {
        return $ && GT.Validator.RegExpLib["enchar"].test($)
    },
    "cnchar": function($) {
        return $ && GT.Validator.RegExpLib["cnchar"].test($)
    },
    "number": function($) {
        return ! isNaN($ / 1)
    },
    "integer": function($) {
        return (String($).indexOf(".") < 0) && !isNaN($ / 1) && GT.Validator.RegExpLib["integer"].test(Math.abs($))
    },
    "float": function($) {
        return ! isNaN($ / 1) && GT.Validator.RegExpLib["float"].test(Math.abs($))
    },
    "money": function($) {
        return ! isNaN($ / 1) && GT.Validator.RegExpLib["money"].test($)
    },
    "idcard": function(_) {
        if (!_ || _.length < 15 || !GT.Validator.RegExpLib["idcard"].test(_)) return false;
        var $;
        if (_.length == 18) $ = _.substr(6, 8);
        else $ = "19" + _.substr(6, 6);
        return GT.Validator.date($, "YYYYMMDD")
    },
    "date": function(K, A) {
        K = [].concat(K);
        if (!A || A.length < 1) A = GT.Validator.DATE_FORMAT;
        A = A.toUpperCase();
        var $ = A.replace(/([$^.*+?=!:|\/\\\(\)\[\]\{\}])/g, "\\$1");
        $ = $.replace("YYYY", "([0-9]{4})");
        $ = $.replace("YY", "([0-9]{2})");
        $ = $.replace("MM", "(0[1-9]|10|11|12)");
        $ = $.replace("M", "([1-9]|10|11|12)");
        $ = $.replace("DD", "(0[1-9]|[12][0-9]|30|31)");
        $ = $.replace("D", "([1-9]|[12][0-9]|30|31)");
        $ = "^" + $ + "$";
        var C = new RegExp($),
        J = 0,
        H = 1,
        D = 1,
        I = A.match(/(YYYY|YY|MM|M|DD|D)/g);
        for (var _ = 0; _ < K.length; _++) {
            if (!C.test(K[_])) return false;
            var G = C.exec(K[_]);
            for (var E = 0; E < I.length; E++) switch (I[E]) {
            case "YY":
            case "yy":
                var F = Number(G[E + 1]);
                J = 1900 + (F <= 30 ? F + 100: F);
                break;
            case "YYYY":
            case "yyyy":
                J = Number(G[E + 1]);
                break;
            case "M":
            case "MM":
                H = Number(G[E + 1]);
                break;
            case "D":
            case "d":
            case "DD":
            case "dd":
                D = Number(G[E + 1]);
                break
            }
            var B = (J % 4 === 0 && (J % 100 !== 0 || J % 400 === 0));
            if (D > 30 && (H == 2 || H == 4 || H == 6 || H == 9 || H == 11)) return false;
            if (H == 2 && (D == 30 || D == 29 && !B)) return false
        }
        return true
    },
    "time": function(_, B) {
        _ = [].concat(_);
        if (!B || B.length < 1) B = GT.Validator.TIME_FORMAT;
        var $ = B.replace(/([.$?*!=:|{}\(\)\[\]\\\/^])/g, "\\$1");
        $ = $.replace("HH", "([01][0-9]|2[0-3])");
        $ = $.replace("H", "([0-9]|1[0-9]|2[0-3])");
        $ = $.replace("mm", "([0-5][0-9])");
        $ = $.replace("m", "([1-5][0-9]|[0-9])");
        $ = $.replace("ss", "([0-5][0-9])");
        $ = $.replace("s", "([1-5][0-9]|[0-9])");
        $ = "^" + $ + "$";
        var C = new RegExp($);
        for (var A = 0; A < _.length; A++) if (!C.test(_[A])) return false;
        return true
    },
    "datetime": function(_, D) {
        _ = [].concat(_);
        var C = /^\S+ \S+$/;
        if (!D || D.length < 1) D = GT.Validator.DATETIME_FORMAT;
        else if (!C.test(D)) return false;
        for (var A = 0; A < _.length; A++) {
            if (!C.test(_[A])) return false;
            var $ = _[A].split(" "),
            E = D.split(" "),
            B = GT.Validator.date($[0], E[0]) && GT.Validator.time($[1], E[1]);
            if (!B) return false
        }
        return true
    },
    "range": function(A, $, _) {
        if (!GT.$chk($)) return A <= _;
        else if (!GT.$chk(_)) return A >= $;
        return A >= $ && A <= _
    },
    "equals": function($, _) {
        _ = [].concat(_);
        for (var A = 0; A < _.length; A++) if ($ == _) return true;
        return false
    },
    "lessthen": function($, _) {
        _ = [].concat(_);
        for (var A = 0; A < _.length; A++) if ($ <= _) return true;
        return false
    },
    "greatethen": function($, _) {
        _ = [].concat(_);
        for (var A = 0; A < _.length; A++) if ($ >= _) return true;
        return false
    },
    "minlength": function($, _) {
        return GT.$chk($) && ($ + "").length >= _
    },
    "maxlength": function($, _) {
        return GT.$chk($) && ($ + "").length <= _
    }
}; (function() {
    for (var $ in GT.Validator.KeyMapping) GT.Validator[$] = GT.Validator[GT.Validator.KeyMapping[$]]
})();
GT.Chart = GT.$class({
    SO5: function($) {
        this.defaultColor = "66BBFF";
        this.type = "column2D";
        this.swfPath = "./charts/";
        this.swf = GT.Chart.SWFMapping[this.type];
        this.width = "100%";
        this.height = "100%";
        this.data = null;
        this.container = null;
        this.chart = null;
        GT.$extend(this, $);
        this.swf = GT.Chart.SWFMapping[this.type] || this.swf;
        if (this.swfPath.lastIndexOf("/") == this.swfPath.length - 1) this.swfPath = this.swfPath.substring(0, this.swfPath.length - 1);
        this.container = GT.$(this.container);
        this.chart = this.chart || new FusionCharts(this.swfPath + "/" + this.swf, this.container.id + "_chart", this.width, this.height)
    },
    te9: function(_) {
        if (_.isNull) {
            if (_.name) _ = {
                name: _.name
            };
            else return ""
        } else if (_.color) _.color = _.color || this.defaultColor;
        var $ = [];
        for (var A in _) $.push(A + "='" + _[A] + "'");
        return
    },
    Rr0: function(B) {
        B = B || this.data;
        var D = [];
        for (var F = 0; F < B.length; F++) {
            var $ = B[F],
            _,
            C,
            E,
            A;
            if ($ instanceof Array) {
                C = $[0];
                E = $[1];
                A = $[2];
                A = (E === null || E === undefined) ? null: (A || this.defaultColor);
                C = (C === null || C === undefined) ? E: C;
                str = [C !== null && C !== undefined ? "name='" + C + "'": "", E !== null && E !== undefined ? "value='" + E + "'": "", A !== null && A !== undefined ? "color='" + A + "'": ""].join(" ")
            } else if ($) str = this.te9($);
            _ = ["<set", str, "/>"];
            _ = _.join(" ");
            if (_ == "<set />" || (E === null || E === undefined));
            D.push(_)
        }
        this.setsXML = D.join("");
        return this.setsXML
    },
    on1: function(A, $) {
        $ = $ || this.setsXML;
        var _ = ["<graph", "caption='" + (this.caption || "") + "'", "subCaption='" + (this.subCaption || "") + "'", "outCnvBaseFontSize='12'", "animation='0'"];
        _.push(">" + $ + "</graph>");
        this.chartXML = _.join(" ");
        return this.chartXML
    },
    updateChart: function($, _) {
        $ = $ || this.container;
        _ = _ || this.chartXML;
        updateChartXML($, _)
    },
    QO0: function($, _) {
        this.data = _ || this.data;
        this.Rr0();
        this.on1();
        $ = $ || this.container;
        this.chart.setDataXML(this.chartXML);
        this.chart.render($)
    }
});
GT.Chart.SWFMapping = {
    "column2D": "FCF_Column2D.swf",
    "pie3D": "FCF_Pie3D.swf"
}