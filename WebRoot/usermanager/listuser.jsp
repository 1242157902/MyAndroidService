<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<head>
<title>列出网站所有用户</title>


<script type="text/javascript">
    function sure(){
       
       var sure=window.confirm("您确认删除吗？");
       if(sure==true){
       
       return true;
       
       }
       return false;
       
       }


</script>


 <style type="text/css">
a:link,a:visited {
	
	text-decoration: none;
}

a:hover {
	color: #B00;
}


	table {
	border-collapse: collapse;
	border-spacing:1;
     text-align:center;
	font-size: 14px;
	
	width:790px;

    }

   table td { 
	border: 2px solid #CCCCCC;
	padding:10px 10px 10px 10px;
	 }

</style>


</head>

<body style="text-align:center;background-color:#E0ECFF">

	<div style="margin:0px auto;height:1000px;width:800px;">
	
	<p style="text-align:right;font-size:18px;width:780px;">
		<a
			href="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=listuser">刷新</a>
	</p>
	<h1 style="width:796px;text-align:center;">用户列表</h1>
	
	<table  class="table">
		<tr>
			<td align="right"> <a
				href="${pageContext.request.contextPath }/servlet/AddUser">添加用户</a> 
			
			</td>
		</tr>
	</table>

	<table  class="table">
		<tr>
			<td width=180px>用户名称</td>
			
			<td width=160px>所属公司</td>
			
			<td width=200px>授权</td>
			<td width=100px>删除</td>
		</tr>

		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.username }</td>
				
				<td>${user.comname}</td>
				<td><a
					href="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=userrole&id=${user.id }">为用户授权角色</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td>
					<a
					href="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=deleteuser&id=${user.id }"
					onclick="return sure(this)">删除用户</a></td>
			</tr>
		</c:forEach>

	</table>
</div>
</body>
</html>
