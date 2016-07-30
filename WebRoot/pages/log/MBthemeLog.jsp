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
  <div id="themelog_lay" class="easyui-layout"   data-options="fit:true" >
  <div data-options="region:'north',height:90,title:'查询条件'"  style="overflow:hidden">  
  <form id="themelog_form" method="post" >
	  <div style="padding-top:3px;padding-bottom:3px; ">  
	  &emsp;设备号<input id="themelog_imei" name="themelog_imei" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>手机号码<input id="themelog_mbno" name="themelog_mbno" type=text style="width:180px;height:22px;" class="textbox" value=""/>
	  <span style="padding-left:20px;"></span>修改前模式<select id="themelog_curtheme" name="themelog_curtheme" class="easyui-combotree"  style="width:150px;" data-options="url:'pages/push/data/mbtheme.json',multiple:true,panelHeight:75"></select>
	  <span style="padding-left:20px;"></span>修改后模式<select id="themelog_exptheme" name="themelog_exptheme" class="easyui-combotree"  style="width:150px;" data-options="url:'pages/push/data/mbtheme.json',multiple:true,panelHeight:75"></select>
	  </div>
	  
	  <div style="padding-bottom:3px; "> 
	  &emsp;修改源<select id="themelog_opersrc" name="themelog_opersrc" class="easyui-combotree"  style="width:150px;" data-options="url:'pages/push/data/mbthemesrc.json',multiple:true,panelHeight:150"></select>
	  <span style="padding-left:20px;"></span>操作时间<input id="themelog_start" name="themelog_start"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	        到 <input id="themelog_end" name="themelog_end"  class="easyui-datetimebox" editable="false" style="width:180px;" /> 
	  <span style="padding-left:200px;"></span><a id="themelog_srhbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
	  <span style="padding-left:50px;"></span><a id="themelog_clrbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
	  </div>
	  
  </form>
  </div>
  <div data-options="region:'center'" style="height:100%;">
  <table id="themelog_list"></table></div>
  </div>
  <script type="text/javascript">
  $(function() {
     $('#themelog_list').datagrid({
     idField:'tid',	
     title:'设备活动信息',
     fit:true,
     height:450 ,
     url:'servlet/PlServlet?method=GetThemeLogList' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     columns:[[	     
				{
					field:'tid' ,
                    hidden:true
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
					field:'curtheme' ,
					title:'修改前模式' ,
					formatter:function(value,row,index){return enMBTheme(value);  }  ,
					width:120
				},
				{
					field:'exptheme' ,
					title:'修改后模式' ,
					formatter:function(value,row,index){return enMBTheme(value);  }  ,
					width:120
				},
				{
					field:'opersrc' ,
					title:'修改源'  ,
					formatter:function(value,row,index){return enMBThemeOpersrc(value);  }  ,
					width:120
				},	
				{
					field:'operator' ,
					title:'操作员' ,
					width:120
				},	
				{
					field:'opertime' ,
					title:'操作时间' ,
					width:120,
					formatter : function(value, row, index) {	return value.substring(0,19);	}
				}
	 ]] ,
     pagination: true , 
     pageSize: 30 ,
     pageList:[10,30,50,100,300]
     });
     $('#themelog_srhbtn').click(function(){
        $('#themelog_list').datagrid('clearSelections'); 
		$('#themelog_list').datagrid('load',serializeForm($('#themelog_form')));
     });
     $('#themelog_clrbtn').click(function(){
	    $('#themelog_form').form('clear');
	    $('#themelog_list').datagrid('clearSelections'); 
		$('#themelog_list').datagrid('load' ,serializeForm($('#themelog_form')));		
     });
  });	
 </script>
</body>
</html>
   