<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>ApkUpdateInfo</title>
    
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

	 <script type="text/javascript">
		$(function() {
		/**
		 *	初始化数据表格  
		 */
		var editing; //判断用户是否处于编辑状态 
		$('#t_user').datagrid(
				{
					idField : 'id', //只要创建数据表格 就必须要加 ifField
					title : '设备信息列表',
					//width:2000 ,
					fit : true,
					height : 450,
					url : 'servlet/PhoneServlet?method=getapkinfo',
					//fitColumns:true ,  
					striped : true, //隔行变色特性 
					//nowrap: false ,				//折行显示 为true 显示在一会 
					loadMsg : '数据正在加载,请耐心的等待...',
				//	rownumbers : true,
					editable : true,

					columns : [ [

					 {
						field : 'name',
						title : 'apk名称',
						width : 120,
						height : 50
					//hidden: true
					}, 
					{
						field : 'version',
						title : 'apk版本号',
						width : 180,
						height : 50
                      },
					
				    {
						field : 'updatetime',
						title : '更新时间',
						width : 180,
						height : 50
                      }
                      
					] ],
					pagination : true,
					pageSize : 10,
					pageList : [ 5, 10, 15, 20, 50 ]

				
				});
				
	
				
				
			
			
			
				
				
				
	});
	
</script>  
  </head>
  
  <body  style="margin:0px;">
			
				
				
				<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
				
				
				 
				 
				  <div region="center" >
					<table id="t_user"></table>
				
				  </div>
				  
				  
				  
			  </div>
				
  </body>
</html>
