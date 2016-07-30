<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>ContentList</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
	<div id="qclickpic" class="easyui-dialog"  closed="true"></div>
    <div id="qdlg" class="easyui-dialog" style="width:600px;height:400px;"  closed="true" modal="true" buttons="#qdlg-buttons" >
      <div>
                     显示时长<input id="qshowtime"  type="text"/>
                     有效日期<input id="qenddate" name="enddate"  class="easyui-datetimebox" editable="false" style="width:200px;" />
      </div>
      <div id="choosecontentlay" class="easyui-layout" style="width: 100%;height:300px" >
	  <div region="center" >
		<table id="choosecontentlist"></table>
	  </div>
	  </div>
	   <div id="qdlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="return qSaveAtom()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgx').dialog('close')" style="width:90px">Cancel</a>
       </div>
    </div>

	<div id="queuelistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="queuelist"></table>
	</div>
	</div>
	<input id=qnewAtom type="hidden" onclick="qNewAtom()"/>	
	<script type="text/javascript">	
	 $(function(){
		$('#queuelist').datagrid({
			idField:'cid',	
			title:'序列信息列表',
			//width:2000,
			fit:true,
			height:450 ,
			url:'servlet/CtServlet?method=GetContentList' ,
			//fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			//nowrap: false ,				//折行显示 为true 显示在一会 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
				{
					field:'title' ,
					title:'描述' ,
					width:150
				},	
				{
					field:'sort' ,
					title:'详细信息',
					width:750 
				},
				{
					field:'edit',
					title:'操作',
					width:100 ,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="sortque()" style="font:12px;">重新排序</a>'; 
    								 }   
				},
				{
					field:'imitate',
					title:'模拟',
					width:100 ,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="linkto(\'' + value+ '\')" style="font:12px;">点击开始</a>'; 
    								 }   
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50], 
			toolbar: [
			{
               text: "添加",
               iconCls: "icon-add",
               handler: function () { 
                var newQue='<span>添加新序列</span>';
                if ($('#tt').tabs('exists',  newQue)) { $('#tt').tabs('select', newQue); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/QueueAdd.jsp";
                  $('#tt').tabs('add',
			      {
				   title :newQue,
				   content : '<iframe id=newct frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
            }, '-',
            {
               text: "删除",
               iconCls: "icon-remove",
               handler:function (){
                       
			            }	 
            },'-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler: function () { 
              
               }
                             
            },'-',
            {
               text: "查看",
               iconCls: "icon-ok",
                handler: function () { 
              
               }
            },'-']
			});
	});
	
 	   var url;
       function qnewAtom(){
       	$('#choosecontentlist').datagrid({
			idField:'cid',	
			title:'内容信息列表',
			fit:true,
			url:'servlet/CtServlet?method=GetContentList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
				{
					field:'ok' ,
					checkbox:true ,
					width:20
				},	
				{
					field:'title' ,
					title:'标题' ,
					width:145
				},				
				{
					field:'sort' ,
					title:'类别' ,
					width:145
				},						
				{
					field:'namex' ,
					title:'格式' ,
					width:145, 
					formatter: function(value,row,index){
       									return adshowform(value); 
    						   }  
				},
				{
					field:'cid',
					title:'快速浏览',
					width:80 ,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="qlookpic(\'' + row.namex+ '\')" style="font:12px;">点击查看</a>'; 
    								 }   
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
			});
           $('#qdlg').dialog('open').dialog('setTitle','添加新节点');
       }
       function qlookpic(v){	
	      $('#qclickpic').dialog({  
          title: '查看图片',  
          width: 320,  
          height: 500,  
          closed: false,    
          cache: false,    
          href: '',    
          modal: true  
        });  
        $('#qclickpic').dialog('refresh', 'pages/push/ContentLoadImage.jsp?url='+v);  
	   }
       function editUser(){
           var row = $('#dg').datagrid('getSelected');
           if (row){
               $('#dlg').dialog('open').dialog('setTitle','Edit User');
               $('#fm').form('load',row);
               url = 'update_user.php?id='+row.id;
           }
       }
       function saveUser(){
           $('#fm').form('submit',{
               url: url,
               onSubmit: function(){
                   return $(this).form('validate');
              },
               success: function(result){
                  var result = eval('('+result+')');
                  if (result.errorMsg){
                        $.messager.show({
                           title: 'Error',
                           msg: result.errorMsg
                       });
                   } else {
                        $('#dlg').dialog('close');        // close the dialog
                       $('#dg').datagrid('reload');    // reload the user data
                   }
                }
           });
    }
     function destroyUser(){
           var row = $('#dg').datagrid('getSelected');
           if (row){
              $.messager.confirm('Confirm','Are you sure you want to destroy this user?',function(r){
                  if (r){
                     $.post('destroy_user.php',{id:row.id},function(result){
                           if (result.success){
                              $('#dg').datagrid('reload');    // reload the user data
                            } else {
                               $.messager.show({    // show error message
                                 title: 'Error',
                                 msg: result.errorMsg
                              });
                      }
                     },'json');
                   }
             });
           }
      }
	</script>  
</body>
</html>

