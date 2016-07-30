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
  <div id="pushlog_lay" class="easyui-layout"   data-options="fit:true" >
  <div data-options="region:'north',height:90,title:'查询条件'"  style="overflow:hidden">  
  <form id="pushlog_form" method="post" >
	  <div style="padding-top:3px;padding-bottom:3px; ">  
	  &emsp;设备号<input id="pushlog_imei" name="pushlog_imei" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>手机号码<input id="pushlog_mbno" name="pushlog_mbno" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>操作方式<select id="pushlog_oper" name="pushlog_oper" class="easyui-combotree"  style="width:90px;" data-options="url:'pages/push/data/pushoper.json',multiple:true,panelHeight:75"></select>
	  <span style="padding-left:20px;"></span>推送信息<select id="pushlog_puid" name="pushlog_puid" class="easyui-combotree"  style="width:200px;" data-options="url:'servlet/PlServlet?method=GetPushMsgList',multiple:true,panelHeight:75"></select>
	  </div>
	  
	  <div style="padding-bottom:3px; ">  
	       操作时间<input id="pushlog_start" name="pushlog_start"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	        到 <input id="pushlog_end" name="pushlog_end"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	  <span style="padding-left:550px;"></span><a id="pushlog_srhbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
	  <span style="padding-left:50px;"></span><a id="pushlog_clrbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
	  </div>
	  
  </form>
  </div>
  <div data-options="region:'center'" style="height:100%;">
  <table id="pushlog_list"></table></div>
  </div>
  <script type="text/javascript">
  $(function() {
     $('#pushlog_list').datagrid({
     idField:'hid',	
     title:'设备活动信息',
     fit:true,
     height:450 ,
     url:'servlet/PlServlet?method=GetPushLogList' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     columns:[[	     
				{
					field:'hid' ,
                    hidden:true
				},	
				{
					field:'operator' ,
					title:'操作员' ,
					width:120
				},		
				{
					field:'oper' ,
					title:'操作' ,
					formatter:function(value,row,index){return enPushOper(value);  }  ,
					width:70
				},
				{
					field:'phmsg' ,
					title:'推送信息'  ,
					width:120
				},	
				{
					field:'imei' ,
					title:'设备号',
					width:120
				},
				{
					field:'mbno' ,
					title:'手机号',
					width:120
				},
				{
					field:'opertime' ,
					title:'操作时间' ,
					width:130,
					formatter : function(value, row, index) {	return value.substring(0,19);	}
				}
	 ]] ,
     pagination: true , 
     pageSize: 30 ,
     pageList:[10,30,50,100,300]
     });
     $('#pushlog_srhbtn').click(function(){
        $('#pushlog_list').datagrid('clearSelections'); 
		$('#pushlog_list').datagrid('load',serializeForm($('#pushlog_form')));
     });
     $('#pushlog_clrbtn').click(function(){
	    $('#pushlog_form').form('clear');
	    $('#pushlog_list').datagrid('clearSelections'); 
		$('#pushlog_list').datagrid('load' ,serializeForm($('#pushlog_form')));		
     });
  });	
 </script>
</body>
</html>
   