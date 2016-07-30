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
  <div id="rcvlog_lay" class="easyui-layout"   data-options="fit:true" >
  <div data-options="region:'north',height:90,title:'查询条件'"  style="overflow:hidden">  
  <form id="rcvlog_form" method="post" >
	  <div style="padding-top:3px;padding-bottom:3px; ">  
	  &emsp;设备号<input id="rcvlog_imei" name="rcvlog_imei" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>手机号码<input id="rcvlog_mbno" name="rcvlog_mbno" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>序列类型<select id="rcvlog_type" name="rcvlog_type" class="easyui-combotree"  style="width:110px;" data-options="url:'pages/push/data/quetype.json',multiple:true,panelHeight:'auto'"></select>
	  <span style="padding-left:20px;"></span>序列名称<select id="rcvlog_puid" name="rcvlog_puid" class="easyui-combotree"  style="width:200px;" data-options="url:'servlet/PlServlet?method=GetPushQueList',multiple:true,panelHeight:'auto'"></select>
	  </div>
	  
	  <div style="padding-bottom:3px; ">  
	       接收时间<input id="rcvlog_start" name="rcvlog_start"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	        到 <input id="rcvlog_end" name="rcvlog_end"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	  <span style="padding-left:550px;"></span><a id="rcvlog_srhbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
	  <span style="padding-left:50px;"></span><a id="rcvlog_clrbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
	  </div>
	  
  </form>
  </div>
  <div data-options="region:'center'" style="height:100%;">
  <table id="rcvlog_list"></table></div>
  </div>
  <script type="text/javascript">
  $(function() {
     $('#rcvlog_list').datagrid({
     idField:'rid',	
     title:'序列接收日志',
     fit:true,
     height:450 ,
     url:'servlet/PlServlet?method=GetRcvLogList' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     columns:[[	     
				{
					field:'rid' ,
                    hidden:true
				},		
				{
					field:'imei' ,
					title:'设备号',
					width:150
				},
				{
					field:'mbno' ,
					title:'手机号',
					width:150
				},	
				{
					field:'phmsg' ,
					title:'接收序列' ,
					width:150
				},
				{
					field:'type' ,
					title:'序列类型' ,
					formatter:function(value,row,index){return enRcvType(value);  }  ,
					width:150
				},
				{
					field:'acctime' ,
					title:'接收时间' ,
					width:150,
					formatter : function(value, row, index) {	return value.substring(0,19);	}
				}
	 ]] ,
     pagination: true , 
     pageSize: 30 ,
     pageList:[10,30,50,100,300]
     });
     $('#rcvlog_srhbtn').click(function(){
        $('#rcvlog_list').datagrid('clearSelections'); 
		$('#rcvlog_list').datagrid('load',serializeForm($('#rcvlog_form')));
     });
     $('#rcvlog_clrbtn').click(function(){
	    $('#rcvlog_form').form('clear');
	    $('#rcvlog_list').datagrid('clearSelections'); 
		$('#rcvlog_list').datagrid('load' ,serializeForm($('#rcvlog_form')));		
     });
  });	
 </script>
</body>
</html>
   