<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ContentUpdateSuccess</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <input id="cid" name="cid" type="hidden" value="${cid}"/>
        修改成功！</br>  <input id=btn2 type="button" value="确认" onClick="ctclose()">
  <script type="text/javascript">
	function ctclose()
	{ 
	  window.parent.document.getElementById("ccid").innerHTML=document.getElementById("cid").value;
	  window.parent.document.getElementById("closeupdatecttab").click();
	}
  </script>
  </body>
</html>
