<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>关联挖掘</title>
    
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
	<script type="text/javascript"	src="${pageContext.request.contextPath}/usermanager/user_profile/location/js/echarts.min.js"></script>
<!--  <script type="text/javascript" src="usermanager/user_profile/js/miningresult.js"></script> -->
  </head>
  
  <body>
    <div id="content" style="margin-top: 10px;">
    	<div id="map_title"  style="margin-top:20px;margin-bottom: 20px;">
    	<!-- 条件选择区 -->
	    		<lable>最小支持度：</lable><input type="text" id="minSupport" name="minSupport"  class="textbox"/>
	    		<lable>请输入查询时间：</lable><input type="text" id="currentDate" name="currentDate" class="easyui-datebox" editable="false" /> 
    			<!-- 至<input type="text" id="enddate" name="enddate" class="easyui-datebox" editable="false" />-->
    			<input type="button" id="submit" value="查询">
    		
    	</div>
    	<div id="lay" class="easyui-layout" style="width: 100%;height:100%">
			<div region="center">
				<table id="t_user"></table>
			</div>
		</div>
    	<script type="text/javascript">
		$('#submit').click(function(){
			
			var minSupport=$('#minSupport').val();
			var currentDate=$('#currentDate').datebox('getValue'); 
			if(minSupport==""||currentDate=="")
			{
				alert("请输入支持度！");
				return;
			}  
			var flag; // undefined 判断新增和修改方法
			/**
			 * 初始化数据表格
			 */
			$('#t_user')
					.datagrid(
							{
								idField : 'id', // 只要创建数据表格 就必须要加 ifField
								title : '结果展示',
								// width:1300 ,
								fit : true,
								//height : 450,
								url : 'servlet/DataMiningServlet?method=referenceMining',
								fitColumns : true,
								striped : true, // 隔行变色特性
								// nowrap: false , //折行显示 为true 显示在一会
								loadMsg : '数据正在加载,请耐心的等待...',
								rownumbers : true,
								// singleSelect:true , //单选模式
								// remoteSort: false ,
								// sortName: 'salary' ,
								// sortOrder: 'desc' ,
								queryParams: {
									minSupport:minSupport,
									currentDate:currentDate
								    },
								frozenColumns : [ [ // 冻结列特性 ,不要与fitColumns 特性一起使用
								{
									field : 'ck',
									width : 50,
									checkbox : true
								} ] ],
								columns : [ [

									/* 	{
											field : 'id',
											title : 'id',
											width : 50
										}, */

										{
											field : 'classify',
											title : '关联类别',
											width : 100/* ,
											formatter : function(value, record, index) 
											{
												if (value == 'browse') 
												{
													return '浏览 因子';
												} else if (value =='click') 
												{
													return '点击因子';
												}else if (value =='forget') 
												{
													return '遗忘因子';
												}else if (value =='minWeight') 
												{
													return '最小权重';
												}else if (value =='minSupport') 
												{
													return '最小置信度';
												}
											} */
										}/* ,

										{
											field : 'weight',
											title : '因子值',
											width : 50
										},
										{
											field : 'description',
											title : '描　述',
											width : 200

										}  */
		                      ] ],/*
								pagination : true,
								pageSize : 10,
								pageList : [ 5, 10, 15, 20, 50 ],*/
								toolbar : [
										/* {
											text : '添加因子',
											iconCls : 'icon-add',
											handler : function() {
												flag = 'add';
												$('#mydialog').dialog({
													title : '添加因子'
												});
												// $('#myform').find('input[name!=sex]').val("");
												$('#myform').get(0).reset();
												// $('#myform').form('clear');
												$('#mydialog').dialog('open');

											}

										},  */
										/* {
											text : '修改因子',
											iconCls : 'icon-edit',
											handler : function() {
												flag = 'edit';
												var arr = $('#t_user').datagrid(
														'getSelections');
												if (arr.length != 1) {
													$.messager.show({
														title : '提示信息!',
														msg : '只能选择一行记录进行修改!'
													});
												} else {
													$('#mydialog').dialog({
														title : '修改因子信息'
													});
													$('#mydialog').dialog('open'); // 打开窗口
													$('#myform').get(0).reset(); // 清空表单数据
													$('#myform').form('load', { // 调用load方法把所选中的数据load到表单中,非常方便
													    id : arr[0].id,
														type : arr[0].type,
														weight : arr[0].weight,
														description: arr[0].description

													});
												}

											}
										} *//*,
										{
											text : '删除因子',
											iconCls : 'icon-remove',
											handler : function() {
												var arr = $('#t_user').datagrid(
														'getSelections');
												if (arr.length <= 0) {
													$.messager.show({
														title : '提示信息!',
														msg : '至少选择一行记录进行注销!'
													});
												} else {

													$.messager
															.confirm(
																	'提示信息',
																	'确认注销?',
																	function(r) {
																		if (r) {
																			var ids = '';
																			for ( var i = 0; i < arr.length; i++) {
																				ids += arr[i].id
																						+ ',';
																			}
																			ids = ids
																					.substring(
																							0,
																							ids.length - 1);
																			$
																					.post(
																							'OrgServlet?method=delete',
																							{
																								ids : ids
																							},
																							function(
																									result) {
																								// 1
																								// 刷新数据表格
																								$(
																										'#t_user')
																										.datagrid(
																												'reload');
																								// 2
																								// 清空idField
																								$(
																										'#t_user')
																										.datagrid(
																												'unselectAll');
																								// 3
																								// 给提示信息
																								$.messager
																										.show({
																											title : '提示信息!',
																											msg : '操作成功!'
																										});
																							});
																		} else {
																			return;
																		}
																	});
												}
											}
										} */ ]
							});

			/**
			 * 提交表单方法
			 */
/* 			$('#btn1').click(
					function() {
						if ($('#myform').form('validate')) {
							$.ajax({
								type : 'post',
								url : flag == 'add' ? 'servlet/UserProfileServlet?method=save'
										: 'servlet/UserProfileServlet?method=updateFactor',
								cache : false,
								data : $('#myform').serialize(),
								dataType : 'json',
								success : function(result) {
									// 1 关闭窗口
									$('#mydialog').dialog('close');
									// 2刷新datagrid
									$('#t_user').datagrid('reload');

									$('#t_user').datagrid('unselectAll');
									// 3 提示信息
									$.messager.show({
										title : '提示信息!',
										msg : '操作成功!'
									});
								},
								error : function(result) {
									$.messager.show({
										title : '提示信息!',
										msg : '操作失败!'
									});
								}
							});
						} else {
							$.messager.show({
								title : '提示信息!',
								msg : '数据验证不通过,不能保存!'
							});
						}
					}); */

		});
    		
		 
   
    	</script>
    
    </div>
  </body>
</html>
