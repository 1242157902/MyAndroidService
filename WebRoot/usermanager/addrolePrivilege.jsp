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
    
    <title>My JSP 'addrolePrivilege.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
 <style type="text/css">
    
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
    <div style="width:800px;height:600px;margin:0px auto;">
    
    <br/>
   
   <h1 style="text-align:center;">角色权限管理</h1>
   
   	<table >
   		<tr>
   			<td>角色名称：</td>
   			<td>${role.name }</td>
   		</tr>	
   		
   		<tr>
   			<td>角色当前拥有的权限：</td>
   			<td align=left>
   				<c:forEach var="p" items="${role_privileges}">
   					${p.name }<br/><br/>
   				</c:forEach>
   			</td>
   		</tr>	
   		
   		<tr>
   			<td>系统当前所有权限：</td>
   			<td align=left>
   			
   				<form action="${pageContext.request.contextPath }/servlet/RoleManagerServlet?method=addroleprivilege" method="post">
   					
   					<input type="hidden" name="role_id" value="${role.id }">
	   				<c:forEach var="p" items="${system_privileges}">
	   					<input type="checkbox" name="privilege_id" value="${p.id }">${p.name }<br/>
	   				<br/>
	   				</c:forEach>
	   				<p style="color:red;">（不选择任何权限授权后将删除角色所有权限）</p>
	   			 <input type="submit" value="授权">
	   			 <input type="button" value="返回" style="width:50px;" class="myBtn"
														onclick="location.href='javascript:window.history.back()'" />
   				</form>
   			</td>
   		</tr>	
   	
   	</table>
   
   </div>
   
  </body>
</html>
