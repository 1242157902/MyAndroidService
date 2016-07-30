<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'updateuser.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script type="text/javascript">

	function validate() {

	var mypassword=document.getElementById("mypassword").value;
	
      if(mypassword==""){
		alert("请输入密码！");
		return false;
	}
	
     return true;
   }
	
	
	</script>

  </head>
  
  <body style="text-align:center;background-color:#E0ECFF">
   <div style="margin:0px auto;border:2px solid #CCCCCC;width:400px;height:200px;background:#E4EFFA">
 <br/>
  <h1 align=center>修改用户密码</h1>
   
     <form action="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=updateuser" method="post">
		<input type="hidden" value="${sessionScope.user.id}" name="id"/>
		
		  <p style="text-align:center;">新密码：<input type="password" name="password" id="mypassword" style="width:200px;height:25px;"></p>
		 <p style="text-align:center;">   <input type="submit" value="确定修改"  onclick="return validate()"/>
		 
	
		 
		 </p>
	</form>
   
   </div>
   
  </body>
</html>
