<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" type="text/css" href="/MyAndroidService/js/jquery-easyui-1.2.6/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="/MyAndroidService/js/jquery-easyui-1.2.6/themes/icon.css">
	<link rel="stylesheet" type="text/css" href="/MyAndroidService/js/jquery-easyui-1.2.6/demo/demo.css">
	<script type="text/javascript" src="/MyAndroidService/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
	<script type="text/javascript" src="/MyAndroidService/js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
	
<style>
.title{
	border:1px dashed #F00;
	width:100%;
	text-align:center;
	font-size:28px;
	height:50px;
	line-height:50px;
}
</style>
<script>
	function myformatter(date){
		var y = date.getFullYear();
		var m = date.getMonth()+1;
		var d = date.getDate();
		return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
	}
	function myparser(s){
		if (!s) return new Date();
				var ss = (s.split('-'));
		var y = parseInt(ss[0],10);
		var m = parseInt(ss[1],10);
		var d = parseInt(ss[2],10);
		if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
				return new Date(y,m-1,d);
		} else {
				return new Date();
		}
	}
	function check(){
		var device_number=document.getElementById("device_number").value;
		var score=document.getElementById("score").value;
		var end_date=document.getElementById("end_date").value;
	}
</script>
  </head>
  
  <body>
    <div class="title">
    积分赠送
    </div>
    <div style="padding:20px;">
    <form action="${pageContext.request.contextPath}/servlet/AddScoreServlet" method="post">
    手机号：<input id="device_number" name="device_number" type="text"/>
    赠送积分数额：<input id="score" name="score" type="text"/>
    有效期至<input id="end_date" name="end_date" type="text" class="easyui-datebox" required="required" data-options="formatter:myformatter,parser:myparser"/>
    
    <input type="submit" id="submit" value="提交"/>
    
    </form>
    </div>
  </body>
</html>
