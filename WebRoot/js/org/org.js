var flag; // 判断走的是保存还是修改方法
$(function() {
	$('#tree_org').treegrid({
		
		
		title : '组织机构列表',
		iconCls : 'icon-ok',
		width : 1400,
		height : 800,
		nowrap : true,
		//pagination: true,
		rownumbers : true,
		collapsible : true,
		//pageSize: 10,
		//pageList: [10,20,50],

		url : 'OrgServlet?method=getOrgList',
		idField : 'id', // 数据表格要有主键
		treeField : 'orgname', // treegrid 树形结构主键 text
		fitColumns : true,
		columns : [ [ {
			field : 'orgname',
			title : '机构名称',
			width : 250
		}, {
			field : 'id',
			title : '机构编号',
			width : 100
		}, {
			field : 'orgidcard',
			title : '机构身份证号',
			width : 100
		}, {
			field : 'orglinkmanname',
			title : '机构负责人',
			width : 100
		}, {
			field : 'orglinkmanphonenum',
			title : '联系电话',
			width : 100
		}, {
			field : 'orglinkmanemail',
			title : '邮箱',
			width : 120
		}, {
			field : 'orgaddress',
			title : '机构地址',
			width : 300
		}, {
			field : 'orgstatus',
			title : '机构状态',
			width : 80
		}, {
			field : 'orgarea',
			title : '所属地区',
			width : 80
		}
		, {
			field : 'orgregion',
			title : '所属区域',
			width : 80
		}, {
			field : 'description',
			title : '备注',
			width : 200
		} ] ],
		onContextMenu : function(e, row) {
			e.preventDefault(); // 屏蔽浏览器的菜单
			$(this).treegrid('unselectAll'); // 清除所有选中项
			$(this).treegrid('select', row.id); // 选中状态
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});
		}
	});

	$('#btn1')
			.click(
					function() {
						if ($('#myform').form('validate')) {
						if (flag == 'add') {
							// 保存方法
							// 1 前台保存 注意: 没有保存id
							var node = $('#tree_org').treegrid('getSelected');
							$('#tree_org')
									.treegrid(
											'append',
											{
												parent : node.id,
												data : [ {
													orgname : $('#myform')
															.find(
																	'input[name=orgname]')
															.val(),
													orglinkmanname : $(
															'#myform')
															.find(
																	'input[name=orglinkmanname]')
															.val(),
													orglinkmanphonenum : $(
															'#myform')
															.find(
																	'input[name=orglinkmanphonenum]')
															.val(),
													orglinkmanemail : $(
															'#myform')
															.find(
																	'input[name=orglinkmanemail]')
															.val(),
													orgaddress : $('#myform')
															.find(
																	'input[name=orgaddress]')
															.val(),
													description : $('#myform')
															.find(
																	'textarea[name=description]')
															.val(),
													orgidcard : $('#myform')
															.find(
																	'input[name=orgidcard]')
															.val()
												} ]
											});

							// 2 后台保存
							$
									.ajax({
										type : 'post',
										url : 'OrgServlet?method=save',
										cache : false,
										dataType : 'json',
										data : {
											parentId : node.id,
											orgname : $('#myform').find(
													'input[name=orgname]')
													.val(),
											orglinkmanname : $('#myform')
													.find(
															'input[name=orglinkmanname]')
													.val(),
											orglinkmanphonenum : $('#myform')
													.find(
															'input[name=orglinkmanphonenum]')
													.val(),
											orglinkmanemail : $('#myform')
													.find(
															'input[name=orglinkmanemail]')
													.val(),
											orgaddress : $('#myform').find(
													'input[name=orgaddress]')
													.val(),
										   orgarea : $('#orgarea').val(),
										    orgregion : $('#orgregion').val(),
											description : $('#myform')
													.find(
															'textarea[name=description]')
													.val(),
													orgidcard : $('#myform')
													.find(
															'input[name=orgidcard]')
													.val()
										},
										success : function(result) {
											// 刷新节点 : 刷新当前选中节点
											$('#tree_org').treegrid('reload',
													node.id);
											$.messager.show({
												title : '提示信息',
												height:150,
												msg : '操作成功!'
											});
										}
									});
							// 3关闭窗口
							$('#div1').dialog('close');
						} else {
							$
									.ajax({
										type : 'post',
										url : 'OrgServlet?method=update',
										cache : false,
										dataType : 'json',
										data : {
											id : $('#myform').find(
													'input[name=id]').val(),
											orgname : $('#myform').find(
													'input[name=orgname]')
													.val(),
											orglinkmanname : $('#myform')
													.find(
															'input[name=orglinkmanname]')
													.val(),
											orglinkmanphonenum : $('#myform')
													.find(
															'input[name=orglinkmanphonenum]')
													.val(),

											orglinkmanemail : $('#myform')
													.find(
															'input[name=orglinkmanemail]')
													.val(),
											orgaddress : $('#myform').find(
													'input[name=orgaddress]')
													.val(),
											orgarea : $('#orgarea').val(),
											orgregion : $('#orgregion').val(),
											description : $('#myform')
													.find(
															'textarea[name=description]')
													.val(),
													orgidcard : $('#myform')
													.find(
															'input[name=orgidcard]')
													.val()
										},
										success : function(result) {
											// 刷新节点 :如果当前选中的节点是叶子节点的话,刷新该节点的父亲
											// ,如果不是叶子节点,刷新当前选中节点即可
											var node = $('#tree_org').treegrid(
													'getSelected');
											var parent = $('#tree_org')
													.treegrid('getParent',
															node.id);
											$('#tree_org').treegrid('reload',
													parent.id);
											$.messager.show({
												title : '提示信息',
												height:150,
												msg : '操作成功!'
											});
										}
									});
							// 3关闭窗口
							$('#div1').dialog('close');
						}

					}else{
						
						$.messager.show({
							title : '提示信息!',
							msg : '数据验证不通过,不能保存!'
						});
						
						
					}
						
						
						
					});

	// 关闭窗口
	$('#btn2').click(function() {
		$('#div1').dialog('close');
	});

});

function append() {
	flag = 'add';
	// 1清空表单数据
	$('#myform').form('clear');
	// 2打开窗口
	$('#div1').dialog('open');
}

function update() {
	flag = 'edit';
	// 1清空表单数据
	$('#myform').form('clear');
	// 2填充表单回显数据
	var node = $('#tree_org').treegrid('getSelected');
	$('#myform').form('load', {
		id : node.id,
		orgname : node.orgname,
		orglinkmanname : node.orglinkmanname,
		orglinkmanphonenum : node.orglinkmanphonenum,
		orglinkmanemail : node.orglinkmanemail,
		orgaddress : node.orgaddress,
		description : node.description,
		orgarea:node.orgarea,
		orgregion:node.orgregion,
		orgidcard:node.orgidcard
	});
	// 3打开窗口
	$('#div1').dialog('open');
}



//删除组织机构方法

function remove() {
	$.messager.confirm("提示信息", "确认删除?", function(r) {
		if (r) {
			// 1前台删除
			var node = $('#tree_org').treegrid('getSelected');
			$('#tree_org').treegrid('remove', node.id);
			// 2后台删除
			$.post('OrgServlet?method=delete', {
				id : node.id
			}, function(result) {

				$('#tree_org').treegrid('unselectAll');
				$('#tree_org').treegrid('reload');
				$.messager.show({
					title : '提示信息',
					height:150,
					msg : '操作成功!'
				});
			});
		} else {
			return;
		}

	});
}
