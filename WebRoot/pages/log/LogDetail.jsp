<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'LogDetail.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
     <input id='aimei' type='hidden' value='<%=request.getParameter("imei")%>'/>
    <div class="easyui-tabs"   data-options="fit:true,tools:'#logtab-tools'">
        <div title="推送日志" data-options="iconCls:'icon-help'" >
          <table id="tabpushloglist"></table>
        </div>
        <div title="序列接收日志" data-options="iconCls:'icon-help'" >
          <table id="tabrcvloglist"></table>
        </div>
        <div title="模式修改" data-options="iconCls:'icon-help'" >
          <table id="tabmodchangeloglist"></table>
        </div>
    </div>
    <div id="logtab-tools" style="font-size:12px;padding-top:5px;">
                            设备号:<%=request.getParameter("imei")%>&nbsp;
                            手机号:<%=request.getParameter("mbno")%>
    </div>
    <script type="text/javascript">	
   $(function() {tabpushlog($("#aimei").val());tabrcvlog($("#aimei").val());tabmodchangelog($("#aimei").val());});
   function tabpushlog(imei)
   {
     $('#tabpushloglist').datagrid({
     idField:'hid',	
     fit:true,
     url:'servlet/PlServlet?method=GetPushLogList&pushlog_imei='+imei , 
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
   }
   
   function tabrcvlog(imei)
   {
     $('#tabrcvloglist').datagrid({
     idField:'rid',	
     fit:true,
     url:'servlet/PlServlet?method=GetRcvLogList&rcvlog_imei='+imei , 
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
					title:'操作员' ,
					width:120
				},	
				{
					field:'mbno' ,
					title:'手机号',
					width:120
				},	
				{
					field:'type' ,
					title:'操作' ,
					formatter:function(value,row,index){return enRcvType(value);  }  ,
					width:70
				},
				{
					field:'phmsg' ,
					title:'推送信息'  ,
					width:120
				},
				{
					field:'acctime' ,
					title:'接收时间' ,
					width:130,
					formatter : function(value, row, index) {	return value.substring(0,19);	}
				}
	 ]] ,
     pagination: true , 
     pageSize: 30 ,
     pageList:[10,30,50,100,300]
     });
   }
   
   function tabmodchangelog(imei)
   {
     $('#tabmodchangeloglist').datagrid({
     idField:'hid',	
     fit:true,
     url:'servlet/PlServlet?method=GetThemeLogList&themelog_imei='+imei , 
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
					hidden:true
				},
				{
					field:'mbno' ,
					title:'手机号',
					width:100
				},	
				{
					field:'curtheme' ,
					title:'修改前模式' ,
					formatter:function(value,row,index){return enMBTheme(value);  }  ,
					width:80
				},
				{
					field:'exptheme' ,
					title:'修改后模式' ,
					formatter:function(value,row,index){return enMBTheme(value);  }  ,
					width:80
				},
				{
					field:'opersrc' ,
					title:'修改源'  ,
					formatter:function(value,row,index){return enMBThemeOpersrc(value);  }  ,
					width:100
				},	
				{
					field:'operator' ,
					title:'操作员' ,
					width:100
				},	
				{
					field:'opertime' ,
					title:'操作时间' ,
					width:100,
					formatter : function(value, row, index) {	return value.substring(0,19);	}
				}
	 ]] ,
     pagination: true , 
     pageSize: 30 ,
     pageList:[10,30,50,100,300]
     });
   }
   </script>
  </body>
</html>
