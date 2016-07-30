$(function() {

	var flag; // undefined 判断新增和修改方法
	/**
	 * 初始化数据表格
	 */
	$('#t_user')
			.datagrid(
					{
						idField : 'id', // 只要创建数据表格 就必须要加 ifField
						title : '机构列表',
						// width:1300 ,
						fit : true,
						height : 450,
						url : 'OrgServlet?method=getOrgList2',
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
									field : 'orgidcard',
									title : '机构身份id',
									width : 100
								},

								{
									field : 'orgname',
									title : '机构名称',
									width : 100
								},

								{
									field : 'orglinkmanname',
									title : '联系人姓名',
									width : 100
								},

								{
									field : 'orglinkmanphonenum',
									title : '联系人电话',
									width : 100
								},

								{
									field : 'orglinkmanemail',
									title : '联系人邮箱',
									width : 100
								},

								{
									field : 'orgaddress',
									title : '机构地址',
									width : 100
								},

								{
									field : 'orgarea',
									title : '所属地区',
									width : 100
								},

								{
									field : 'orgregion',
									title : '所属区域',
									width : 100
								},
								
								{
									field : 'description',
									title : '备注',
									width : 100

								},{
									field : 'orgstatus',
									title : '状态',
									width : 50,
									formatter : function(value, record, index) {
										if (value == '已注销') {
											return '<span style=color:red; >已注销</span>';
										} else if (value =='运营') {
											return '<span style=color:green; >运营</span>';
										}

									}

									}
								
                      ] ],
						pagination : true,
						pageSize : 10,
						pageList : [ 5, 10, 15, 20, 50 ],
						toolbar : [
								{
									text : '新增机构',
									iconCls : 'icon-add',
									handler : function() {
										flag = 'add';
										$('#mydialog').dialog({
											title : '新增机构'
										});
										// $('#myform').find('input[name!=sex]').val("");
										$('#myform').get(0).reset();
										// $('#myform').form('clear');
										$('#mydialog').dialog('open');

									}

								},
								{
									text : '修改机构信息',
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
												title : '修改机构信息'
											});
											$('#mydialog').dialog('open'); // 打开窗口
											$('#myform').get(0).reset(); // 清空表单数据
											$('#myform').form('load', { // 调用load方法把所选中的数据load到表单中,非常方便
											    id : arr[0].id,
												orgname : arr[0].orgname,
												orgidcard : arr[0].orgidcard,
												orglinkmanname : arr[0].orglinkmanname,
												orglinkmanemail : arr[0].orglinkmanemail,
												orglinkmanphonenum : arr[0].orglinkmanphonenum,
												orgarea : arr[0].orgarea,
												orgregion : arr[0].orgregion,
												orgaddress : arr[0].orgaddress,
												description: arr[0].description

											});
										}

									}
								},
								{
									text : '注销机构',
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
								}, {
									text : '查询机构',
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
						url : flag == 'add' ? 'OrgServlet?method=save'
								: 'OrgServlet?method=update',
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