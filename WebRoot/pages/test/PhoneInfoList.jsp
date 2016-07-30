<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>OrderManage</title>
    
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
					url : 'servlet/PhoneServlet?method=getAllPhoneInfo',
					//fitColumns:true ,  
					striped : true, //隔行变色特性 
					//nowrap: false ,				//折行显示 为true 显示在一会 
					loadMsg : '数据正在加载,请耐心的等待...',
				//	rownumbers : true,
					editable : true,

					columns : [ [

					 {
						field : 'phonenumber',
						title : '用户手机号码',
						width : 120,
						height : 50
					//hidden: true
					}, 
					{
						field : 'phoneimei',
						title : '设备号',
						width : 180,
						height : 50
                      },
					
				    {
						field : 'phoneimsi',
						title : 'SIM卡号',
						width : 180,
						height : 50
                      }	, 
                      {
						field : 'mobiletype',
						title : '手机卡类型',
						width : 120,
						height : 50
                      },
                      
                       {
						field : 'mobilearea',
						title : '手机归属地',
						width : 180,
						height : 50
                      },
                   
                  {
						field : 'phonetype',
						title : '用户手机类型',
						width : 150,
						height : 50
					//hidden: true
					}
					,
                 {
						field : 'birthday',
						title : '年龄',
						width : 50,
						height : 50
                      }
                      ,
                 {
						field : 'sex',
						title : '性别',
						width : 50,
						height : 50
                      }
					
					,
                 {
						field : 'phoneversion',
						title : '用户系统版本号',
						width : 120,
						height : 50
                      },{
						field : 'entertime',
						title : '录入时间',
						width : 120,
						height : 50
                      }
                      ,{
						field : 'cmpno',
						title : '店号',
						width : 100,
						height : 50
                      },{
						field : 'empno',
						title : '员工号',
						width : 100,
						height : 50
                      }
                      
                      
					] ],
					pagination : true,
					pageSize : 10,
					pageList : [ 5, 10, 15, 20, 50 ]

				
				});
				
	/*********************************search begin****************************************/
			//search 
				$('#searchbtn').click(function(){
						$('#t_user').datagrid('load' ,serializeForm($('#mysearch')));
					});
					
				
				
				
				//清空搜索框
	         $('#clearbtn').click(function(){
						$('#mysearch').form('clear');
						$('#t_user').datagrid('load' ,{});
					});
			
			
				
				
				
	});
	

	//js方法：序列化表单 			
			function serializeForm(form){
				var obj = {};
				$.each(form.serializeArray(),function(index){
					if(obj[this['name']]){
						obj[this['name']] = obj[this['name']] + ','+this['value'];
					} else {
						obj[this['name']] =this['value'];
					}
				});
				return obj;
			}
	//显示设备类型
	 $(function(){
	
	$.getJSON("servlet/QueryServlet?method=getAllPhoneType", function(data) {
        $("#devicetype").html("");//清空devicetype内容
        
          $("#devicetype").append("<option value=''>全部型号</option>");
        $.each(data.types, function(i, item) {
            $("#devicetype").append(
                    "<option value='"+item.type+"'>"+item.type+"</option>" 
                   );
        });
        });

	
	}); 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	</script>  
  </head>
  
  <body>
			
				
				
				<div id="lay" class="easyui-layout" style="width: 100%;height:100%" >
				
				 <div region="north" title="设备信息查询" collapsed=FALSE style="height:90px;" >
					<br/>
					
					
					<form id="mysearch" method="post">
					
					
					&nbsp;归属地:&nbsp;&nbsp;&nbsp;
					   <select name="mobilearea" id="mobilearea" style="height:22px;width:160px;">
						  <option value="北京">北京</option>
							<option value="河北">河北</option>
							
							</select>
					
					
							设备类型:&nbsp;&nbsp;&nbsp;
							
							
							<!-- <input name="devicename" class="easyui-validatebox"  value="" /> -->
						
							<select name="devicename" id="devicetype" style="height:22px;width:160px;">
						
							</select>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							开始时间:<input name="startTime"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />	
							结束时间:<input name="endTime"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="" />
							手机号:&nbsp;&nbsp;&nbsp;<input name="deviceno" class="easyui-validatebox"  value="" />
								<br/>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<a id="searchbtn" class="easyui-linkbutton">查询</a> <a id="clearbtn" class="easyui-linkbutton">清空</a>
					</form>
				
				</div>
				 
				 
				  <div region="center" >
					<table id="t_user"></table>
				
				  </div>
				  
				  
				  
			  </div>
				
  </body>
</html>
