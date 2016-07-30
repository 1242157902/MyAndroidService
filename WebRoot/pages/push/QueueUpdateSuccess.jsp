<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>QueueUpdateSuccess</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
   <input id="qid" name="qid" type="hidden" value="${qid}"/>
        修改成功！</br>  <input id=btn type="button" value="确认" onClick="queclose()">
  <script type="text/javascript">
	function queclose()
	{ 
	  window.parent.document.getElementById("qqid").innerHTML=document.getElementById("qid").value;
	  window.parent.document.getElementById("qUpdateAtomCloseTabBtn").click();
	}
  </script>
  </body>
</html>
