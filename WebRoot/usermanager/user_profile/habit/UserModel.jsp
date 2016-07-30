<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>根据条件查找用户在使用手机的规律</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/usermanager/user_profile/css/habit.css">
		<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>              
		<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
		<script type="text/javascript" src="http://echarts.baidu.com/build/dist/echarts.js"></script>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/usermanager/user_profile/css/usermodel.css">
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	
  </head>
  <script> 
  </script>
  <body>
	<div id="lay" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:150,title:'查询条件'">
			<div style="padding-top:5px;padding-bottom:5px;">
					性 别<select
						id="sex" name="sex" class="easyui-combotree"
						style="width:100px;"
						data-options="url:'usermanager/user_profile/data/gender.json',multiple:true,panelHeight:75"></select>
					
					运营商<select id="provi" name="provi"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'usermanager/user_profile/data/provider.json',multiple:true,panelHeight:100"></select>
						所在月份<input id="date" name="date"
						class="easyui-datebox" editable="false" style="width:180px;" />
					<input type="button" id="srbtn"
						class="easyui-linkbutton" icon="icon-search" value="查询" />
				<script type="text/javascript"	src="${pageContext.request.contextPath}/usermanager/user_profile/js/usermodel.js"></script>
				</div>
		</div>
		<div data-options="region:'center'" style="height:100%;">
			每个时段使用人数：
			<div id="line_show" style="height:300px;">
			
			</div>
			每个时段超过5/40次的人数
			<div id="time_show" style="height:300px;">
			
			</div>
			用户使用手机时长及总次数
			<div id="day_show" style="height:300px;">
			
		</div>
		</div>
		
	</div>
    
    </div>
  </body>
</html>
