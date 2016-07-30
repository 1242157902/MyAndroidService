<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>ContentList</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" href="css/jscal2.css" />  

<link rel="stylesheet" href="css/border-radius.css" />  
 
<link rel="stylesheet" href="css/reduce-spacing.css" />  
 
<link title="Gold" type="text/css" rel="stylesheet" href="css/gold.css" /> 

<script src="js/jscal2.js" ></script>  
<script src="js/en.js" ></script> 
<script type="text/javascript" src="js/push/pushfunc.js"></script>
</head>
<body>
   
	<div id="contentlistlay" class="easyui-layout" style="width: 100%;height:100%" >
		<div data-options="region:'north',height:100,title:'查询条件'">
			<form id="mysearch" style="margin-top:20px;margin-left:50px;"> 
			请选择查看：<input type="text" name="startdate" id="startdate" /> 
			<input type="hidden"  id="start_date" name="start_date"/>
			<input type="hidden"  id="end_date" name="end_date"/>
			<input type="button" class="easyui-linkbutton" style="margin-left:5px;" id="submit" name="submit" value="查询"/>
			</form>
		</div>
		<div data-options="region:'center'" >
			<table id="contentlist"></table>
		</div>
		<div data-options="region:'south',height:80" style="text-align:center;">
			<div style="margin-top:40px;">
			<input type="button" class="easyui-linkbutton" id="print" name="print" value="打印"/>
			</div>
		</div> 
	</div>
	<script type="text/javascript" src="${pageContext.request.contextPath}/usermanager/js/dxt_printed.js">	
	 
	</script>  
</body>
</html>
