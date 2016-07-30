<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>phoneinfo</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
  <div id="errorlog_lay" class="easyui-layout"   data-options="fit:true" >
  <div data-options="region:'north',height:70,title:'查询条件'"  style="overflow:hidden">  
  <form id="errorlog_form" method="post" >
	  <div style="padding-top:3px;padding-bottom:3px; ">  
	  &emsp;设备号<input id="errorlog_imei" name="errorlog_imei" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>错误类型<select id="errorlog_type" name="errorlog_type" class="easyui-combotree"  style="width:180px;" data-options="url:'servlet/PlServlet?method=GetErrorTypeList',multiple:true,panelHeight:75"></select>
	 &emsp; 操作时间<input id="errorlog_start" name="errorlog_start"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	        到 <input id="errorlog_end" name="errorlog_end"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	  <span style="padding-left:50px;"></span><a id="errorlog_srhbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
	  <span style="padding-left:50px;"></span><a id="errorlog_clrbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
	  </div>
  </form>
  </div>
  <div data-options="region:'center'" style="height:100%;">
  <table id="errorlog_list"></table></div>
  </div>
  <script type="text/javascript">
  $(function() {
     $('#errorlog_list').datagrid({
     idField:'eid',	
     title:'设备活动信息',
     fit:true,
     height:450 ,
     url:'servlet/PlServlet?method=GetErrorLogList' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     columns:[[	     
				{
					field:'eid' ,
                    hidden:true
				},	
				{
					field:'errtxt' ,
					title:'错误信息',
					width:120
				},
				{
					field:'errtype' ,
					title:'类型' ,
					width:120
				},		
				{
					field:'errmethod' ,
					title:'错误源'  ,
					width:120
				},	
				{
					field:'imei' ,
					title:'设备号',
					width:120
				},
				{
					field:'inmsg' ,
					title:'输入信息',
					width:120
				},
				{
					field:'outmsg' ,
					title:'输出信息',
					width:120
				},
				{
					field:'errtime' ,
					title:'时间' ,
					width:130,
					formatter : function(value, row, index) {	return value.substring(0,19);	}
				}
	 ]] ,
     pagination: true , 
     pageSize: 30 ,
     pageList:[10,30,50,100,300]
     });
     $('#errorlog_srhbtn').click(function(){
        $('#errorlog_list').datagrid('clearSelections'); 
		$('#errorlog_list').datagrid('load',serializeForm($('#errorlog_form')));
     });
     $('#errorlog_clrbtn').click(function(){
	    $('#errorlog_form').form('clear');
	    $('#errorlog_list').datagrid('clearSelections'); 
		$('#errorlog_list').datagrid('load' ,serializeForm($('#errorlog_form')));		
     });
  });	
 </script>
</body>
</html>
   