<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>role</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	
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
	
		<p style="text-align:right;font-size:18px"><a href="${pageContext.request.contextPath }/servlet/RoleManagerServlet?method=listrole">刷新</a></p>
	
	<h1 style="width:796px;text-align:center;">角色列表</h1>
	<br/>
	<table width="70%">
		<tr>
			<td  align="right">
				<a href="${pageContext.request.contextPath }/usermanager/addrole.jsp">添加角色</a>
			</td>
		</tr>
	</table>
	
	<table >
		<tr>
			<td width=200>角色名称</td>
			<td width=200>描述</td>
			<td width=180>授权</td>
			<td width=180>删除</td>
		</tr>
		
		<c:forEach var="role" items="${roles}">
			<tr>
				<td>${role.name }</td>
				<td>${role.description }</td>
				<td>
					<a href="${pageContext.request.contextPath }/servlet/RoleManagerServlet?method=roleprivilege&id=${role.id }">为角色授权</a>
			        </td>
			        <td>
			        
			        
			        <a href="${pageContext.request.contextPath }/servlet/RoleManagerServlet?method=deleterole&id=${role.id}" onclick="return sure(this)">删除角色</a>
				</td>
			</tr>
		</c:forEach>
		
	</table>
</div>
  </body>
</html>
