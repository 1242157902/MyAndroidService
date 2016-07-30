$(function() {

	var flag; // undefined 判断新增和修改方法
	/**
	 * 初始化数据表格
	 */
	$('#t_user')
			.datagrid(
					{
						idField : 'id', // 只要创建数据表格 就必须要加 ifField
						title : '员工列表',
						// width:1300 ,
						fit : true,
						height : 450,
						url : 'EmpServlet?method=getlist',
						fitColumns : true,
						striped : true, // 隔行变色特性
						// nowrap: false , //折行显示 为true 显示在一会
						loadMsg : '数据正在加载,请耐心的等待...',
						rownumbers : true,
						// singleSelect:true , //单选模式
						// remoteSort: false ,
						// sortName: 'salary' ,
						// sortOrder: 'desc' ,

						frozenColumns : [ [ // 冻结列特性 ,不要与fitColumns 特性一起使用
						{
							field : 'ck',
							width : 50,
							checkbox : true
						} ] ],
						columns : [ [

								{
									field : 'empid',
									title : '企业员工编码',
									width : 100
								},

								{
									field : 'empname',
									title : '姓名',
									width : 100
								},

								

								{
									field : 'empduty',
									title : '职务',
									width : 100
								},

								{
									field : 'departname',
									title : '所属部门',
									width : 100
								},

								{
									field : 'depart_area',
									title : '所属地区',
									width : 100
								},

								{
									field : 'depart_region',
									title : '所属区域',
									width : 100
								},

								{
									field : 'phonenum',
									title : '联系电话',
									width : 100
								},
								{
									field : 'email',
									title : '邮箱',
									width : 100
								},
								{
									field : 'sex',
									title : '性别',
									width : 50

								},
								{
									field : 'age',
									title : '年龄',
									width : 50

								},

								{
									field : 'status',
									title : '状态',
									width : 50,
									formatter : function(value, record, index) {
										if (value == 0) {
											return '<span style=color:red; >离职</span>';
										} else if (value == 1) {
											return '<span style=color:green; >在职</span>';
										}

									}
								},
								{
									field : 'picname',
									title : '二维码',
									width : 100,
									height : 50,
									formatter : function(value, row, index) {
										return '<a href="javascript:" onclick="printtowdim(\''
												+ row.picname
												+ '\',\''
												+ row.empname+'\',\''+row.empid+'\',\''+row.departname
												+ '\')" style="font:12px;">查看二维码</a>';
									}
								}

						] ],
						pagination : true,
						pageSize : 10,
						pageList : [ 5, 10, 15, 20, 50 ],
						toolbar : [
								{
									text : '新增员工',
									iconCls : 'icon-add',
									handler : function() {
										flag = 'add';
										$('#mydialog').dialog({
											title : '新增员工'
										});
										// $('#myform').find('input[name!=sex]').val("");
										$('#myform').get(0).reset();
										// $('#myform').form('clear');
										$('#mydialog').dialog('open');

									}

								},
								{
									text : '修改员工信息',
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
												title : '修改员工信息'
											});
											$('#mydialog').dialog('open'); // 打开窗口
											$('#myform').get(0).reset(); // 清空表单数据
											$('#myform').form('load', { // 调用load方法把所选中的数据load到表单中,非常方便
												id : arr[0].id,
												departname : arr[0].departname,
												empname : arr[0].empname,
												empphonenum : arr[0].phonenum,
												empemail : arr[0].email,
												empsex : arr[0].sex,
												empage : arr[0].age,
												empduty:arr[0].empduty,
												empid:arr[0].empid

											});
										}

									}
								},
								{
									text : '注销员工',
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
																					'EmpServlet?method=delete',
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
								}, {
									text : '查询员工',
									iconCls : 'icon-search',
									handler : function() {
										$('#lay').layout('expand', 'north');
									}
								} ]
					});

	/**
	 * 提交表单方法
	 */
	$('#btn1').click(
			function() {
				if ($('#myform').form('validate')) {
					$.ajax({
						type : 'post',
						url : flag == 'add' ? 'EmpServlet?method=save'
								: 'EmpServlet?method=update',
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
			});

	/**
	 * 关闭窗口方法
	 */
	$('#btn2').click(function() {
		$('#mydialog').dialog('close');
	});

	$('#searchbtn').click(function() {
		$('#t_user').datagrid('load', serializeForm($('#mysearch')));
	});

	$('#clearbtn').click(function() {
		$('#mysearch').form('clear');
		$('#t_user').datagrid('load', {});
	});

});

// js方法：序列化表单
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

function printtowdim(picname, empname,empid,departname) {
	$('#towdim').dialog('open');

	$('#printtwodim').html("");

	$("#printtwodim").append(
        '<div style="margin:0px auto;"><img alt="twodim" src="images/erweima/' + picname
					+ '.png" style="width:139px;height:139px" /></div> ' +
					 '<table style="width:300px;height:50px;margin:0px auto;"><tr><td align="right" width="120px;">姓名:</td><td>'
					+ empname + '</td></tr>'+'<tr><td align="right">所属部门:</td><td>'
					+ departname + '</td></tr>'+'<tr><td align="right">员工编号:</td><td>'
					+ empid + '<td></tr>'
	);

}

function sureprint(printtwodim) {
	

	$("#printtwodim").jqprint();

	

}



