<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-US-Compatible" content="IE=Edge, chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi">
    <meta content="yes" name="apple-mobile-web-app-capable">
    <meta content="black" name="apple-mobile-web-app-status-bar-style">
    <meta content="telephone=no" name="format-detection">
    <title>易划应用合集</title>
    <link href="res/style-reset.css" rel="stylesheet" type="text/css">  
    <link href="res/style.css" rel="stylesheet" type="text/css">   
    <script type="text/javascript" src="res/jquery-1.11.2.js"></script>
    <script type="text/javascript" src="res/jsrender.js"></script> 
</head>
<body>

<div class="topbar">
    <a  href="javascript:history.go(-1);"><img src="res/ico-head.png" alt="" style="width:100px;height:40px;"></a>
    <div class="search">
        <input id="keyword" value="" placeholder="请输入关键词..."  autocomplete="off" class="keywords" type="text"/>
        <button  class="go" onclick="searchAPP()"></button>
    </div>
</div>

<div class="content">
  <ul scrollpagination="enabled" class="list-app" id="applist"></ul>
  <a class="btn-more" href="javascript:void(0);" onclick="loadMoreApp()">更多</a>
</div>
<script id="appsListTemplate" type="text/x-jsrender">
    <li id="{{:id}}" >
        <div class="pic">
            <img  src="../appres/app_icons/{{:icon}}" data-src="" width="56" height="56" alt="" />
        </div>
        <div class="txt">
            <span class="tit">{{:title}}</span>
            <div class="info">               
                <span>{{:num}}次下载  | </span>
                <span>大小:{{:apkurl}} | </span>
                <span>{{:tip}}</span>
            </div>
            <span class="intro">简介:{{:memo}}</span>
        </div>
        <div class="mod-btn mod-btn-official">
            <a class="btn-install appdown" title="{{:id}}"  href="../appres/app_files/{{:apkname}}" onclick="commitdownum(this.title)">安装</a>
        </div>
    </li>
</script>
<script type="text/javascript">
    searchAPP();
    function loadMoreApp() {
        var cid = $("ul li:last-child").attr("id");
        if(cid==null) cid=0;
        $.post("http://fyy:8080/MyAndroidService/servlet/AppServlet?method=GetClientAppList", { id: cid,key:trim($("#keyword").val())}, function (data) {
            var template = $.templates("#appsListTemplate");
            var htmlOutput = template.render(data);
            $("#applist").append(htmlOutput);
        }, 'json');
    }
    function searchAPP() {
       $.post("http://fyy:8080/MyAndroidService/servlet/AppServlet?method=GetClientAppList", { id:0,key:trim($("#keyword").val())}, function (data) {
                var template = $.templates("#appsListTemplate");
                var htmlOutput = template.render(data);
                $("#applist").html(htmlOutput);
       }, 'json');
    }
    function trim(str) { //删除左右两端的空格
        if(str==null) return "";
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
    function commitdownum(apkid){
      $.post("http://fyy:8080/MyAndroidService/servlet/AppServlet?method=CommitDownum", { id: apkid } );
    }
</script>

<!--<script id="theTmpl" type="text/x-jsrender">
<div>
   <em>Name:</em> {{:name}}
   {{if showNickname && nickname}}
      (Goes by <em>{{:nickname}}</em>)
   {{/if}}
</div>
</script>-->
</body>
</html>