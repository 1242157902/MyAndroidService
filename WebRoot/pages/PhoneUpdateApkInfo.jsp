<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>PhoneUpdateApkInfo</title>
    
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
		$('#update_apkinfo').datagrid(
				{
					idField : 'id', //只要创建数据表格 就必须要加 ifField
					title : '设备信息列表',
					//width:2000 ,
					fit : true,
					height : 450,
					url : 'servlet/LogServlet?method=getphoneupdateapkinfo',
					fitColumns:true ,  
					striped : true, //隔行变色特性 
					nowrap: false ,				//折行显示 为true 显示在一会 
					loadMsg : '数据正在加载,请耐心的等待...',
					rownumbers : true,
					editable : true,

					columns : [ [

					 {
						field : 'apkversion',
						title : 'apk版本号',
						width : 120,
						height : 50
					//hidden: true
					}, 
					{
						field : 'phonenumber',
						title : '手机号码',
						width : 180,
						height : 50
                      }, 
					{
						field : 'phoneimei',
						title : '设备号',
						width : 180,
						height : 50
                      }, 
					{
						field : 'phonetype',
						title : '设备类型',
						width : 180,
						height : 50
                      }, 
					{
						field : 'phoneversion',
						title : '设备操作系统版本号',
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
					pageSize : 20,
					pageList : [ 5, 10, 15, 20, 50 ],
					toolbar : [
				            	{
									text : '查询',
									iconCls : 'icon-search',
									handler : function() {
										$('#apklay').layout('expand', 'north');
									}
								}
					
					
					]

				
				});
				
				
	$('#apkinfo_srhbtn').click(function(){
	
       $('#update_apkinfo').datagrid('clearSelections'); 
     
       $('#update_apkinfo').datagrid('load',serializeForm($('#mysearch')));
      
	 });
 $('#apkinfo_clrbtn').click(function(){
	    $('#mysearch').form('clear');
	    $('#update_apkinfo').datagrid('clearSelections'); 
	    
		$('#update_apkinfo').datagrid('load' ,serializeForm($('#mysearch')));
				
     });
				
});

 function serializeForm(form) {
	var obj = {};
	$.each(form.serializeArray(), function(index) {
		if (obj[this['name']]) {
			obj[this['name']] = obj[this['name']] + ',' + this['value'];
		} else {
			obj[this['name']] = this['value'];
		}
	});
	return obj;
}
	
</script>  
  </head>
  
  <body style="margin:0px;">
			
				
				
				<div id="apklay" class="easyui-layout" style="width: 100%;height:100%" >
				
				<div region="north" title="用户查询" collapsed=true style="height:80px;">
			<form id="mysearch" method="post">
			
			<br/>
				手机号码<input id="mbinfo_no" name="mbinfo_no" type=text
						style="width:140px;height:22px;" class="textbox" value="" /> 
						设备号<input id="device_imei" name="device_imei" type=text
						style="width:140px;height:22px;" class="textbox" value="" /> 
						
&nbsp;&nbsp;&nbsp;<a id="apkinfo_srhbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	 <a id="apkinfo_clrbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
			</form>

		</div>
				 
				 
				  <div region="center" >
					<table id="update_apkinfo"></table>
				
				  </div>
				  
				  
				  
			  </div>
				
  </body>
</html>
