<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
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

<title>firstupdate</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
	
	function validate(){
	var password=document.getElementById("password").value;
	var password1=document.getElementById("password1").value;
	
	  
	  if(password==""){
	  
	  alert("请输入密码！");
	  return false;
	  
	  }
	  if(password1==""){
	  
	    alert("请确认密码！");
	    return false;
	  }
	  
	  
	   if(password!=password1)
	 {
	 alert("两次密码输入不一致！");
	 
	 return false;
	  
	
	  }
	
	
	    return true;
	}
	
	
	
	
	</script>



</head>

<body>
	<div
		style="margin: 0px auto;width:900px;height:600px;border:1px solid #C9C9C9;">
		<br /> <br />
		<p style="color:red;font-size:18px;text-align:center;">
			您是第一次登录,请修改密码！</p>

		<form
			action="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=firstloginupdate"
			method="post">

			<br />
			<p style="text-align:center;">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;新密码：<input type="password" name="password" id="password"
					style="width:200px;height:25px;">
			</p>
			<br/>
			
			<p style="text-align:center;">
				请确认密码：<input type="password" name="password1" id="password1"
					style="width:200px;height:25px;">
			</p>
			<p style="text-align:center;">

				<input type="submit" value="确定修改" onclick="return validate()" />



			</p>




		</form>
	</div>
</body>
</html>
