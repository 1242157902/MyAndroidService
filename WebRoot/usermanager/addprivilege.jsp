<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'addprivilege.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
	
	<script type="text/javascript">
	
	
	
	
	function validate() {

	var name = $("#name").val();
	if(isEmpty(name)) {
	    alert("请输入权限名称！");
	
		return false;
	}
	
	
  var description = $("#description").val();
	if(isEmpty(description)) {
	  alert("请输入描述信息！");
		return false;
	}
	
   
 

	return true;
}
	
	
	
	
	
	
	function isEmpty(target) {
	if(target == null || target == undefined || target == "") {
		return true;
	} else {
		return false;
	}
}



	
	
	</script>
	
	





</head>

<body style="text-align:center;background-color:#E0ECFF">
<div style="margin:0px auto;border:2px solid #CCCCCC;width:600px;height:330px;background:#E4EFFA">
  <h1 align=center>添加权限</h1>
	<form
		action="${pageContext.request.contextPath }/servlet/PrivilegeManagerServlet?method=addprivilege"
		method="post">
		<p align=left>权限名称：<input type="text" name="name" id="name" style="height:25px;width:300px"></p>
		
		
		<p align=left> 权限描述：</p>
		<textarea style="width:500px;height:130px" id="description" name="description"></textarea>
		<br> <input type="submit" value="添加权限"  onclick="return validate()"/>
		<input type="button" value="返回" style="width:50px;" class="myBtn"
														onclick="location.href='javascript:window.history.back()'" />
	</form>
</div>











</body>
</html>
