<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'downloaderweima.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	

  </head>
  
  <body style="text-align:center;background-color:#E0ECFF">
   <div style="width:200px;height:200px;margin:0px auto;" >
   <p style="font-size:16px;text-align:right;"><a href="<%=basePath%>/images/erweima/${requestScope.imagename}">下载二维码</a></p>
   
      <img src="<%=basePath%>/images/erweima/${requestScope.imagename}"/>
     
   
   </div>
  </body>
</html>
