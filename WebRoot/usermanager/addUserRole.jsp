<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加用户角色</title>
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
   
   <h1 style="text-align:center;">用户角色管理</h1>
   
   
   	<table >
   		<tr>
   			<td>用户名称:</td>
   			<td>${user.username }</td>
   		</tr>	
   		
   		<tr>
   			<td>用户当前拥有的角色:</td>
   			<td>
   				<c:forEach var="role" items="${user_roles}">
   					${role.name }<br/>
   				</c:forEach>
   			</td>
   		</tr>	
   		
   		<tr>
   			<td>系统当前所有角色:</td>
   			<td align=left>
   			
   				<form action="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=adduserrole" method="post">
   					<input type="hidden" name="user_id" value="${user.id }">
	   				
	   				<c:forEach var="role" items="${system_roles}">
	   					<input type="checkbox" name="role_id" value="${role.id }">${role.name }( ${role.privileges})<br/>
	   				  <br/>
	   				</c:forEach>
	   				<br/>
	   				<p style="color:red;">（不选择任何角色授予角色后将删除用户所有角色）</p>
	   				<input type="submit" value="授予角色">
	   				<input type="button" value="返回" style="width:50px;" class="myBtn"
														onclick="location.href='javascript:window.history.back()'" />
   				</form>
   			</td>
   		</tr>	
   	
   	</table>
   </div>
  </body>
</html>
