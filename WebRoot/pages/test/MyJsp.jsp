
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>AddPushItem</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/themes/icon.css" />
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="js/push/datagrid-filter.js"></script>
  </head>
  
  <body>
     <table id="dg" title="My Users" class="easyui-datagrid" style="width:700px;height:250px" url="servlet/PhServlet?method=GetPushList"  toolbar="#toolbar" pagination="true"  rownumbers="true" fitColumns="true" singleSelect="true">
      <thead>
        <tr>
             <th field="ok" width="50">First Name</th>
             <th field="receiver" width="50">Last Name</th>
              <th field="sender" width="50">Phone</th>
         <th field="message" width="50">Email</th>
     </tr>     
     </thead>  
      </table>
     
       <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="hehe()" style="width:90px">Saweve</a>
  <form action="servlet/PhServlet?method=AddPushItem" method="post">
        <div>
		<span>推送方式</span>
		<select type="select" name="pushway" id="type" style="width:80px;" >
		     <option value="0">即时推送</option>
		     <option value="1">定时推送</option>
		</select> &nbsp; &nbsp; &nbsp; &nbsp;
		<span>推送时间</span>
		<input id="startdate" name="startdate"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="""/>	
		</div>	
	    <br/>
	        
	    <div>
		<span>推送优先级</span>
		<input id="priori" name="priori"  type="text" style="width:65px;"  value=""/>	
		&nbsp; &nbsp; &nbsp; &nbsp;
	    <span>有效截止日期</span>
		<input id="enddate" name="enddate"  class="easyui-datetimebox" editable="false" style="width:160px;"  value="""/>	
		</div>		
	    <br/>
	    
	    <span>推送对象</span>
		<div id="accepterlay" class="easyui-layout" style="width: 100%;height:300px" >
	    <div region="center" >
		<table id="accepter"></table>
	    </div>
	    </div>
		<br/>		
		
		<span>推送信息</span>
		<div id="pushmsglay" class="easyui-layout" style="width: 100%;height:300px" >
	    <div region="center" >
		<table id="pushmsg"></table>
	    </div>
	    </div>
		<br/>
		
		<input id=btn1 type="submit" value="保存" onClick="retrurn phsave()"/>		
        <input id=btn2 type="button" value="取消" onClick="phclose()" />
  </form>
  
   <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"  closed="true" buttons="#dlg-buttons"  title="dfdf">
     <div class="ftitle">User Information</div>
     <form id="fm" method="post" novalidate>
          <div class="fitem">
            <label>First Name:</label>
          <input name="firstname" class="easyui-textbox" required="true">
          </div>
            <div class="fitem">
            <label>Last Name:</label>
           <input name="lastname" class="easyui-textbox" required="true">
           </div>
         <div class="fitem">
       <label>Phone:</label>
               <input name="phone" class="easyui-textbox">
           </div>
      <div class="fitem">
               <label>Email:</label>
               <input name="email" class="easyui-textbox" validType="email">
          </div>
   </form>
  </div>
 <div id="dlg-buttons">
       <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="OKCondition()" style="width:90px">Save</a>
      <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
  </div>
   <script type="text/javascript">	
   var sel='<select type="select" name="pushway" id="type"> <option value="0">即时推送</option> <option value="1">定时推送</option></select>';
	 $(function(){
	  $('#accepter').datagrid({
			idField:'pid',	
			title:'推送信息列表',
			fit:true,
			height:450 ,
			url:'servlet/PhServlet?method=GetPushList',
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 					
			columns:[[
				{
					field:'pid' ,
					title:'编号' ,
					width:30 ,					
					height:50,
					hidden: true
				},	
				{
					field:'ok' ,
					title:'编号' ,
					width:30 ,					
					height:50,
					checkbox: true
				},			
				{
					field:'receiver' ,
					title:'推送对象&nbsp; &nbsp;<a href="javascript:" onclick="OKPhoneBrand()" style="font:12px;">点击查看</a>' ,
					width:150, 
					height:50
				},				
				{
					field:'sender' ,
					title:'推送人' ,
					width:150, 
					height:50
					//hidden: true
				},				
				{
					field:'message' ,
					title:'推送信息' ,
					width:150, 
					height:50
				},
				{
					field:'type' ,
					title:'推送方式' ,
					width:150, 
					height:50
				},
				{
					field:'pushtime' ,
					title:'推送时间' ,
					width:150, 
					height:50
				    //hidden: true
				},
				{
					field:'status' ,
					title:'推送状态'+sel ,
					width:150 ,
					height:50
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50],
			toolbar: [{
               text: "按条件查询",
               iconCls: "icon-add",
               handler: function () { }
            }]
			});	OKCondition();
	});
	function phclose()
	{ 
	  window.parent.document.getElementById("closeaddphitemtab").click();
	}
    function phsave()
	{ 
	}
	function OKPhoneBrand()
	{
	  $('#dlg').dialog('open').dialog('setTitle','New User');
	}
	function hehe()
	{ $('#dg').datagrid({url:'servlet/PhServlet?method=GetPushLisyt'});  	
	}
	function OKCondition()
	{ 
	 // $('#dlg').dialog('close');        // close the dialog
	 // $('#dg').datagrid('load',{sender:'1' });
	  $('#dg').datagrid({url:'servlet/PhServlet?method=GetPushList'});  	  
	 // $('#dg').datagrid('reload');    // reload the user data
	}
	
	  function loadGrid() {            
	  $('#tbTest').datagrid({  
	   width: 'auto',
 height: 300, 
striped: true,
  singleSelect: true,   
  url: 'test.aspx',                loadMsg: '数据加载中请稍后……',                pagination: true,                rownumbers: true,                columns: [[   { field: 'column1', title: '列1', align: 'center', width: 80 },{ field: 'column2', title: '列2', align: 'center', width: 80 }]]            });        }
	</script>
  </body>
</html>
		