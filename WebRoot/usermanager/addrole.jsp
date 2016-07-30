<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'addrole.jsp' starting page</title>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
	
	<script type="text/javascript">
	
	
	
	
	function validate() {

	var name = $("#name").val();
	if(isEmpty(name)) {
	    alert("请输入角色名称！");
	
		return false;
	}
	
	
  var description = $("#description").val();
	if(isEmpty(description)) {
	  alert("请输入描述！");
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
  <h1 align=center>添加角色</h1>
   	<form action="${pageContext.request.contextPath }/servlet/RoleManagerServlet?method=addrole" method="post">
	<p align=left>	角色名称：<input type="text" name="name" id="name" style="height:25px;width:300px"></p>
		<p align=left>角色描述：</p><textarea style="width:500px;height:130px" id="description" name="description"></textarea><br/>
		<input type="submit" value="添加角色"  onclick="return validate()"/>
		<input type="button" value="返回" style="width:50px;" class="myBtn"
														onclick="location.href='javascript:window.history.back()'" />
	</form>
	</div>
  </body>
</html>
