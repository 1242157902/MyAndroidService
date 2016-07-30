<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>PushUpdateSuccess</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
   <input id="pid" name="pid" type="hidden" value="${pid}"/>
        修改成功！</br>  <input id=btn type="button" value="确认" onClick="phclose()">
  <script type="text/javascript">
	function phclose()
	{ 
	  window.parent.document.getElementById("ppid").innerHTML=document.getElementById("pid").value;
	  window.parent.document.getElementById("CloseUpdatePushTabBtn").click();
	}
  </script>
  </body>
</html>
