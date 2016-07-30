		var mbinfo_vvv = "";
		var mbinfo_ttt = "";
		var mbinfo_iii = "";
		var editing;
		$(function() {

			
			
					$.ajax({
						type : "post",
						url : "servlet/PhServlet?method=GetCurUserType",
						dataType : "json",
						data : {
							none : "none"
						},
						success : function(data) {
							if (data != null) {
								if (data.role != null && data.role != 1) {
									var rolesplit = data.role.split("*");
									if (rolesplit[0] == 2)
										mbinfo_iii = "seller";
									if (rolesplit[0] == 3)
										mbinfo_iii = "company";
									mbinfo_vvv = rolesplit[1];
									mbinfo_ttt = rolesplit[2];
								}
								$('#mbinfo_company')
										.combotree(
												{
													url : 'servlet/JsonServlet?method=GetCompanyList',
													multiple : true,
													panelHeight : 200,
													width : 170,
													onLoadSuccess : function() {
														if (mbinfo_iii == "company") {
															$("#mbinfo_company")
																	.combo(
																			"setValue",
																			mbinfo_vvv)
																	.combo(
																			"setText",
																			mbinfo_ttt);
															$("#mbinfo_company")
																	.combo(
																			"disable");
														}
													}
												});
								$('#mbinfo_seller')
										.combotree(
												{
													url : 'servlet/JsonServlet?method=GetCompanyList',
													multiple : true,
													panelHeight : 200,
													width : 170,
													onLoadSuccess : function() {
														if (mbinfo_iii == "seller") {
															$("#mbinfo_seller")
																	.combo(
																			"setValue",
																			mbinfo_vvv)
																	.combo(
																			"setText",
																			mbinfo_ttt);
															$("#mbinfo_seller")
																	.combo(
																			"disable");
														}
													}
												});
							} else
								alert("初始化查询条件失败");
						},
						error : function(data) {
							alert("初始化查询条件失败");
						}
					});
			
			
			

			$('#mbinfo_list').datagrid(
					{
						idField : 'imei',
						title : '设备基本信息',
						fit : true,
						height : 450,
						url : 'servlet/MbServlet?method=GetMobileList',
						striped : true,
						loadMsg : '数据正在加载,请耐心的等待...',
						rownumbers : true,
						 singleSelect:true,
						editable : true,
						frozenColumns : [ [ // 冻结列特性 ,不要与fitColumns 特性一起使用
						{
							field : 'ck',
							width : 50,
							checkbox : true
						} ] ],
						columns : [ [ {
							field : 'imei',
							title : '设备号',
							width : 120,
							formatter : function(value, row, index) {
								return '<a title="'+ row.mbno+'" href="javascript:" onclick="SeeLogDetail(\''+value+'\',this.title)" style="font:12px;">'+value+'</a>';			    				
						    }		
						}, {
							field : 'gender',
							title : '性别',
							width : 35,
							formatter : function(value, row, index) {
								return enGender(value);
							}
						}, {
							field : 'birth',
							title : '年龄',
							width : 35
						}, {
							field : 'mbno',
							title : '手机号',
							width : 90
						},  {
							field : 'recque',
							title : '手机当前序列',
							width : 110
						},{
							field : 'imsi',
							title : 'sim号',
							width : 120
						}, {
							field : 'mbos',
							title : '操作系统',
							width : 100
						}, {
							field : 'provider',
							title : '运营商',
							width : 100
						}, {
							field : 'area',
							title : '归属地',
							width : 100
						}, {
							field : 'mbtype',
							title : '手机型号',
							width : 150
						}, {
							field : 'company',
							title : '所属单位',
							width : 110,
							formatter:function(value,row){
								return row.company;
							},
							editor:{
								type:'combobox',
								options:{
									valueField:'id',
									textField:'name',
									method:'get',
									url:'js/json/unit.json',
									required:false
								}
							}
						}, {
							field : 'regtime',
							title : '录入时间',
							width : 150,
							sortable:true
						}, {
							field : 'seller',
							title : '销售商',
							width : 110,
							formatter:function(value,row){
								return row.seller;
							},
							editor:{
								type:'combobox',
								options:{
									valueField:'id',
									textField:'name',
									method:'get',
									url:'js/json/seller.json',
									required:false
								}
							}
						}, {
							field : 'seller_depid',
							title : '销售部门',
							width : 110
						}, {
							field : 'seller_stuffid',
							title : '销售员工',
							width : 110
						} ,{
							field : 'theme',
							title : '主题',
							width : 100, 
							formatter: function(value,row,index){
	       						if(value==0) return '主题方式';
	       						else if(value==1) return '解锁模式';
	       						else return '错误';
	    				    },
							editor:{
								type:'combobox',
								options:{
									valueField:'id',
									textField:'name',
									method:'get',
									url:'js/json/theme.json',
									required:true
								}
							}
						},{
							field : 'update_key',
							title : '手机端是否已获取服务器端最新公钥',
							width : 240,
							formatter:function(value,row,index){
								if(value==0) return '已更新';
	       						else if(value==1) return '未更新';
	       						else if(value==null) return '已更新';
							},
							editor:{
								type:'combobox',
								options:{
									valueField:'id',
									textField:'name',
									method:'get',
									url:'js/json/serverpublickey.json',
									required:false
								}
							}
						
						
						
						
						
						}
						] ],
						pagination : true,
						pageSize : 20,
						pageList : [ 5, 10, 15, 20, 50 ],
						toolbar : [
								{
									text : '修改',
									iconCls : 'icon-edit',
									handler : function() {
										var arr = $('#mbinfo_list').datagrid(
												'getSelections');
										if (arr.length != 1) {
											$.messager.show({
												title : '提示信息',
												msg : '只能选择一条记录进行操作!'
											});
										} else {

											if (editing == undefined) {
												//flag = 'edit';

												//根据行记录对象获取该行的索引位置

												editing = $('#mbinfo_list')
														.datagrid(
																'getRowIndex',
																arr[0]);
												//开启编辑状态
												$('#mbinfo_list').datagrid(
														'beginEdit', editing);

											}
										}

									}
								},
								{
									text : '保存',
									iconCls : 'icon-save',
									handler : function() {
										//保存之前进行数据的校验 , 然后结束编辑并师傅编辑状态字段 
										if ($('#mbinfo_list').datagrid('validateRow', editing)) {
											$('#mbinfo_list').datagrid('endEdit', editing);
											editing = undefined;

										}
									}
								}

						],
						onAfterEdit : function(index, record) {
							$.post('servlet/MbServlet?method=update', record,
									function(result) {
										$('#mbinfo_list').datagrid('reload');
										//2 清空idField   
										$('#mbinfo_list').datagrid(
												'unselectAll');
										$.messager.show({
											title : '提示信息',
											msg : '操作成功!'
										});
									});
						}
					});
			$('#mbinfo_srhbtn')
					.click(
							function() {
								var tmbno = $('#mbinfo_no').val().replace(
										/(^\s*)|(\s*$)/g, "");
								if (tmbno.match(/^\d+(\s*,\s*\d+)*$/g) == null
										&& tmbno != "") {
									alert("请按格式填写手机号码，并用逗号隔开，如 13800125655，13800125656 ");
									return;
								}
								$('#mbinfo_list').datagrid('clearSelections');
								$('#mbinfo_list').datagrid('load',
										serializeForm($('#mbinfo_form')));
							});
			$('#mbinfo_clrbtn').click(
					function() {
						$('#mbinfo_form').form('clear');
						if (mbinfo_iii == "company") {
							$("#mbinfo_company").combo("setValue", mbinfo_vvv)
									.combo("setText", mbinfo_ttt);
						}
						if (mbinfo_iii == "seller") {
							$("#mbinfo_seller").combo("setValue", mbinfo_vvv)
									.combo("setText", mbinfo_ttt);
						}
						$('#mbinfo_list').datagrid('clearSelections');
						$('#mbinfo_list').datagrid('load',
								serializeForm($('#mbinfo_form')));
					});
		});