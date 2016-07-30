<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加用户</title>
    
    
 <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
	
	<script type="text/javascript">
	
	
	
	
	function validate() {

	var username = $("#username").val();
	if(isEmpty(username)) {
	    alert("请输入用户名！");
	
		return false;
	}
	
	
  var password = $("#password").val();
	if(isEmpty(password)) {
	  alert("请输入密码！");
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
  <br/>
  
  
  
  <div style="margin:0px auto;border:2px solid #CCCCCC;width:400px;height:200px;background:#E4EFFA">
  <h1 align=center>添加用户</h1>
    <form action="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=adduser" method="post">
    
                        用户名称：<input type="text" name="username" id="username" style="height:25px;"/><br/><br/>
		用户密码：<input type="text" name="password" id="password" style="height:25px;"/><br/><br/>
		<input type="submit" value="添加用户" onclick="return validate()"/>
		<input type="button" value="返回" style="width:50px;" class="myBtn"
														onclick="location.href='javascript:window.history.back()'" />
	</form>
	
	</div>
  </body>
</html>
