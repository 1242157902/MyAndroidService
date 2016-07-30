<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<title>列出所有权限</title>

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
<br/>
	<p style="text-align:right;font-size:18px"><a href="${pageContext.request.contextPath }/servlet/PrivilegeManagerServlet?method=listprivilege">刷新</a></p>

	<h1 style="width:796px;text-align:center;">权限列表</h1>
	 <c:if test="${!empty requestScope.comno}">
	<table >
		<tr>
			<td  align="right">
				<a href="${pageContext.request.contextPath }/usermanager/addprivilege.jsp">添加权限</a>
			</td>
		</tr>
	</table>
    </c:if>
	<table >
		<tr>
			<td>权限名称</td>
			<td>描述</td>
			 <c:if test="${!empty requestScope.comno}">
			<td>操作</td>
			</c:if>
		</tr>
		<c:forEach var="p" items="${privileges}">
			<tr>
				<td>${p.name }</td>
				<td>${p.description }</td>
				 <c:if test="${!empty requestScope.comno}">
				<td>
                    <a href="${pageContext.request.contextPath }/servlet/PrivilegeManagerServlet?method=deleteprivilege&id=${p.id}"  onclick="return sure(this)">删除权限</a>
				</td>
				 </c:if>
			</tr>
		</c:forEach>

	</table>
</div>
</body>
</html>
