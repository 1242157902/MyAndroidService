<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>根据条件查找用户偏好</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/usermanager/user_profile/css/habit.css">
		<script type="text/javascript"	src="js/org/jquery-1.7.2.min.js"></script>              
		<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	
  </head>
  <script> 
  </script>
  <body>
	<div id="lay" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:100,title:'查询条件'">
			<div style="padding-top:5px;padding-bottom:5px;">
				<form id="my"  method="post">	
					性 别<select
						id="sex" name="sex" class="easyui-combotree"
						style="width:100px;"
						data-options="url:'usermanager/user_profile/data/gender.json',panelHeight:75"></select>
			<!--  		年 龄<select id="age" name="age"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'usermanager/user_profile/data/age.json',panelHeight:170"></select>
					-->
					偏好分类<select id="classify" name="classify"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/UserProfileServlet?method=GetClassList',panelHeight:200"></select>
					权重<input id="weight" name="weight"
						class="easyui-numberbox" style="width:180px;" value='0.0' data-options="min:0,precision:2"/>
					推送类别<input name="queid" id="queid" class="easyui-combobox" data-options="url:'servlet/PhServlet?method=GetPushQueTitleList',valueField:'qid',textField:'title',editable:false,buttonText:'序&emsp;列',buttonAlign:'left',panelWidth:206,panelAlign:'right'" style="width:250px;height:25px;" />
					
					<input type="button" id="srbtn"
						class="easyui-linkbutton" icon="icon-search" value="查询" />
						<input type="button" id="pbtn"
						class="easyui-linkbutton" icon="icon-search" value="推送" onclick="saveDepartx()"/>
				<script type="text/javascript"	src="${pageContext.request.contextPath}/usermanager/user_profile/js/userpreferlist.js"></script>
				</form>
				</div>
		</div>  

		<div data-options="region:'center'" style="height:100%;">
			<table id="userslist"></table>
		</div>
		
	</div>
    
    </div>
  </body>
</html>
