function floatchip(chipname, url, imgsrc, left, top, width, height, vmin, vmax, vr, interval,content) 
{ 
    this.chipname=chipname; 
    this.browserOK=false; 
    this.mie=false; 
    this.vmin=vmin; 
    this.vmax=vmax; 
    this.vr=vr; 
    this.interval = interval; 
    var timer1; 
    this.movechip=movechip; 
    this.stopchip=stopchip; 
    if(navigator.appName.indexOf("Internet Explorer")!=-1)     { 
        if(parseInt(navigator.appVersion.substring(0,1))>=4) 
        this.browserOK=navigator.javaEnabled(); 
        this.mie=true; 
    } 
    if(navigator.appName.indexOf("Netscape")!=-1) { 
        if(parseInt(navigator.appVersion.substring(0,1))>=4) 
        this.browserOK=navigator.javaEnabled(); 
    } 
    this.vx=vmin+vmax*Math.random(); 
    this.vy=vmin+vmax*Math.random(); 
    this.w=1; 
    this.h=1; 
    this.xx=0; 
    this.yy=600; 
    this.timer1=null; 
    docStr = "<div id='"+chipname+"' style='height:"+height+"px;left:"+left+"px;position:absolute;top:"+top+"px;width:"+width+"px; z-index:1;'>"; 
   // docStr += "<div id='close"+chipname+"' style='position:relative;width:13px;height:13; top:15px; z-index:2'><img src='del.gif' Alt='Close' onClick=\"HideMe('"+chipname+"')\" style='cursor:hand'></div>"; 
    docStr += "<a href='"+url+"'>"; 
   // docStr += "<img id='img"+chipname+"' src='"+imgsrc+"' border='0'></a>"; 
   	docStr +="<span style='font-size:20px'>"+content+"</span>";
    docStr += "</div>"; 
    document.write(docStr); 
    //document.getElementById("close"+chipname).style.left=document.getElementById("img"+chipname).width-14; 
    this.movechip(chipname); 
} 

function HideMe(chipname) 
{ 
    this.chipname=chipname; 
    document.getElementById(chipname).style.display = "none"; 
} 

function movechip() 
{ /*
    if(this.browserOK) { 
        //eval("chip="+chipname); 
        if(!this.mie)     { 
            pageX=window.pageXOffset; 
            pageW=window.innerWidth; 
            pageY=window.pageYOffset; 
            pageH=window.innerHeight; 
        } 
        else { 
            pageX=window.document.body.scrollLeft; 
            pageW=window.document.body.offsetWidth-8; 
            pageY=window.document.body.scrollTop; 
            pageH=window.document.body.offsetHeight; 
        } 
        this.xx=this.xx+this.vx; 
        this.yy=this.yy+this.vy; 
        this.vx+=this.vr*(Math.random()-0.5); 
        this.vy+=this.vr*(Math.random()-0.5); 
        if(this.vx>(this.vmax+this.vmin)) this.vx=(this.vmax+this.vmin)*2-this.vx; 
        if(this.vx<(-this.vmax-this.vmin)) this.vx=(-this.vmax-this.vmin)*2-this.vx; 
        if(this.vy>(this.vmax+this.vmin)) this.vy=(this.vmax+this.vmin)*2-this.vy; 
        if(this.vy<(-this.vmax-this.vmin)) this.vy=(-this.vmax-this.vmin)*2-this.vy; 
        if(this.xx<=pageX) { 
            this.xx=pageX; 
            this.vx=this.vmin+this.vmax*Math.random(); 
        } 
        if(this.xx>=pageX+pageW-this.w) { 
            this.xx=pageX+pageW-this.w; 
            this.vx=-this.vmin-this.vmax*Math.random(); 
        } 
        if(this.xx>=680) { 
            this.xx=this.xx-20; 
            this.vx=-this.vmin-this.vmax*Math.random(); 
        } 
        if(this.yy<=pageY) { 
            this.yy=pageY; 
            this.vy=this.vmin+this.vmax*Math.random(); 
        } 
        if(this.yy>=pageY+pageH-this.h) { 
            this.yy=pageY+pageH-this.h; 
            this.vy=-this.vmin-this.vmax*Math.random(); 
        } 
        if(!this.mie) { 
            eval('document.'+this.chipname+'.top ='+this.yy); 
            eval('document.'+this.chipname+'.left='+this.xx); 
        } else     { 
            eval('document.all.'+this.chipname+'.style.pixelLeft='+this.xx); 
            eval('document.all.'+this.chipname+'.style.pixelTop ='+this.yy); 
        } 
        this.timer1=setTimeout(this.chipname+'.movechip()',this.interval); 
    } */
} 

function stopchip() 
{ 
    if(this.browserOK) { 
        if(this.timer1!=null) { 
            clearTimeout(this.timer1) 
        } 
    } 
} 