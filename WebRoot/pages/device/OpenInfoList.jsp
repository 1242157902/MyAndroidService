<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>slideinfo</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/push/pushindex.js"></script>
<script type="text/javascript" src="js/push/pushencode.js"></script>
<script type="text/javascript" src="js/push/pushfunc.js"></script>
	
	
	
</head>
<body>
  <div id="slideinfo_lay" class="easyui-layout"   data-options="fit:true" >
  <div data-options="region:'north',height:65,title:'查询条件'">
			<form id="slideinfo_form" method="post">
				<div style="padding-top:5px;padding-bottom:5px;">
					手机号码<input id="mbinfo_no" name="mbinfo_no" type=text
						style="width:140px;height:22px;" class="textbox" value="" /> 
						设备号<input id="device_imei" name="device_imei" type=text
						style="width:140px;height:22px;" class="textbox" value="" /> 
						
						
					
				<!--		
					所属单位<select id="slideinfo_company" name="mbinfo_company">
					
					</select> 
					
					销售商<select
						id="slideinfo_seller" name="mbinfo_seller">
						
						</select>
						
						
						
						
						
				</div>
				 <div style="padding-top:5px;padding-bottom:5px;">
					手机品牌<select id="mbinfo_brand" name="mbinfo_brand"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetBrandList',multiple:true,panelHeight:200"></select>
					运营商<select id="mbinfo_provi" name="mbinfo_provi"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'pages/push/data/provider.json',multiple:true,panelHeight:100"></select>
					归属地<select id="mbinfo_area" name="mbinfo_area"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetAreaList',multiple:true,panelHeight:200"></select>
					操作系统<select id="mbinfo_os" name="mbinfo_os"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetMbOsList',multiple:true,panelHeight:200"></select>
				<div style="padding-top:5px;padding-bottom:5px;">
				</div> -->
					滑屏时间<input id="mbinfo_regstart" name="mbinfo_regstart"
						class="easyui-datetimebox" editable="false" style="width:180px;" />
					到 <input id="mbinfo_regend" name="mbinfo_regend"
						class="easyui-datetimebox" editable="false" style="width:180px;" />
						
						
					<span style="padding-left:220px;">
					</span>
					
					<a id="slideinfo_srhbtn"
						class="easyui-linkbutton" icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;
						
						</a>
						
						<span
						style="padding-left:50px;">
						
						</span>
						
						<a id="slideinfo_clrbtn"
						class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;
						</a>
				</div>
			</form>
		</div>
  
  
  <div data-options="region:'center'" style="height:100%;">
  <table id="slideinfo_list"></table></div>
  </div>
  
  <script type="text/javascript">
  	    var slideinfo_vvv = "";
		var slideinfo_ttt = "";
		var slideinfo_iii = "";
		
  $(function(){
  
  
  	
			$
					.ajax({
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
										slideinfo_iii = "seller";
									if (rolesplit[0] == 3)
										slideinfo_iii = "company";
									slideinfo_vvv = rolesplit[1];
									slideinfo_ttt = rolesplit[2];
								}
								$('#slideinfo_company')
										.combotree(
												{
													url : 'servlet/JsonServlet?method=GetCompanyList',
													multiple : true,
													panelHeight : 200,
													width : 170,
													onLoadSuccess : function() {
														if (slideinfo_iii == "company") {
															$("#slideinfo_company")
																	.combo(
																			"setValue",
																			slideinfo_vvv)
																	.combo(
																			"setText",
																			slideinfo_ttt);
															$("#slideinfo_company")
																	.combo(
																			"disable");
														}
													}
												});
								$('#slideinfo_seller')
										.combotree(
												{
													url : 'servlet/JsonServlet?method=GetCompanyList',
													multiple : true,
													panelHeight : 200,
													width : 170,
													onLoadSuccess : function() {
														if (slideinfo_iii == "seller") {
															$("#slideinfo_seller")
																	.combo(
																			"setValue",
																			slideinfo_vvv)
																	.combo(
																			"setText",
																			slideinfo_ttt);
															$("#slideinfo_seller")
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
  
  
  
  
  
  
  
  
  });
  

  
  $(function() {
  
     $('#slideinfo_list').datagrid({
     idField:'imei',	
     title:'设备活动信息',
     fit:true,
     height:450 ,
     url:'servlet/MbServlet?method=GetSlideList' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     columns:[[	
				{
					field:'imei' ,
					title:'设备号',
					width:120,
					hidden: false,
					formatter: function(value,row,index){
     return '<a href="javascript:"  onclick="mngRol(\'' + row.imei+'\')">'+row.imei+'</a>'; 
       									
    								 }  
				},
				{
					field:'mbno' ,
					title:'手机号',
					width:100
				},	
				{
					field:'pictitle' ,
					title:'图片标题' ,
					width:150
				},	
				{
					field:'picname' ,
					title:'图片名',
					width:200
				},{
					field:'score' ,
					title:'积分',
					width:50
				},
				{
					field:'slidetime' ,
					title:'滑屏时间',
					sortable:true,
					width:150
				}/* ,		
				{
					field:'gender' ,
					title:'性别' ,
					width:50,
					formatter:function(value,row,index){return enGender(value);  }  
				},				
				{
					field:'birth' ,
					title:'年龄' ,
					width:50 
				} */,	
				{
					field:'company' ,
					title:'单位',
					width:130
				},	
				{
					field:'mbtype' ,
					title:'手机型号' ,
					width:120
				},	
				{
					field:'provider' ,
					title:'手机卡类型' ,
					width:110
				},
				{
					field:'area' ,
					title:'手机归属地' ,
					width:110
				}/* ,	
				{
					field:'seller' ,
					title:'手机销售商',
					width:110
				},	
				{
					field:'seller_depid' ,
					title:'手机销售部门',
					width:110
				},	
				{
					field:'seller_stuffid' ,
					title:'手机销售员工',
					width:110
				} */
	 ]] ,
     pagination: true , 
     pageSize: 20 ,
     pageList:[5,10,15,20,50]
     });
     
     $('#slideinfo_srhbtn').click(function(){
     
   
     
        var tmbno=$('#mbinfo_no').val().replace(/(^\s*)|(\s*$)/g,"");
         
        if(tmbno.match(/^\d+$/g)==null&&tmbno!="") 
         {
              alert("请填写正确格式的手机号码，如13800180018");
              return;
         }
         
         
         //mbinfo_regstart
         //mbinfo_regend
         
         var start = $('#mbinfo_regstart').datetimebox('getValue');
         var end= $('#mbinfo_regend').datetimebox('getValue');
         if(start==''||end=='')
         {
         
         alert("请您选择开始时间和结束时间!")
         
           return ;
         }
         
         
         
        $('#slideinfo_list').datagrid('clearSelections'); 
        
        
		$('#slideinfo_list').datagrid('load',serializeForm($('#slideinfo_form')));
		
		
     });
     
     $('#slideinfo_clrbtn').click(function(){
	    $('#slideinfo_form').form('clear');
	    $('#slideinfo_list').datagrid('clearSelections'); 
		$('#slideinfo_list').datagrid('load' ,serializeForm($('#slideinfo_form')));		
     });
     
  });	
  
  function mngRol(deviceimei){
  
  var obj = new Object();
	obj.device_imei=deviceimei;
	
 $('#slideinfo_list').datagrid('load' ,obj);
 
 $('#slideinfo_list').datagrid('unselectAll');
	
}
  
  
  
 </script>
</body>
</html>
   