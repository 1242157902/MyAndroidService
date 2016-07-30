<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>Login</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript">
	
 function validate() {
 var managername=document.getElementById("username").value;
  var password=document.getElementById("password").value;
	if(managername==""||password=="" ){
		
		alert("用户名或密码不能为空");
		return false;
 }
	return true;
	}
	
	</script>
	
	
	<style type="text/css">
#body1{
   width:1024px;
   height:700px;
	background-image:url(images/Login.jpg);
	background-repeat:no-repeat;
	background-position:center top ;
	background-color:#0266A2;
	margin:0 auto;

	
	}
	
	#login{
		
		
		width:180px;
		height:120px;
		left:700px;
		top:236px;
		font-weight:bold;
		position:relative;
		
		}


</style>



  </head>
  
  <body style="background-color:#0266A2;" >
  
   <div id="body1">



<div id="login">
  
  
  <form action="${pageContext.request.contextPath}/servlet/LoginServlet" method="post">
  <input type="text" name="username" id="username" style='height:23px;margin-top:11px;margin-left:11px;width:150px;'><br>
<input type="password" name="password" id="password" style='height:23px;margin-top:13px; margin-left:11px;width:150px;'><br>
  <input type="submit" value="登录" onclick="return validate(this)" style='margin-top:18px;margin-left:10px;color:#44A2E0;font-family:宋体; height:25px;'>
 
 
 
  </form>
  
 
</div>






</div>  <!--这个层居中 -->
   
   ${errormessage}
  
   
   
   
  </body>
</html>
