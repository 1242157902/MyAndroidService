<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">    
    <title>PushList</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <body> 
   
   <div id="mbthemelistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="mbthemelist"  class="easyui-datagrid"></table>
	</div>
   </div>
  
   <div id="mbthemeItemdlg" class="easyui-dialog" style="width:315px;height:195px;"  closed="true" modal="true" buttons="#mbthemeItemdlg-buttons" >     
   <form id="mbthemeItemForm" action="" method="post">  
    <input name="tid" type="hidden" /> 
   <table id="addphtable"  border="1" cellpadding="1" >    
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">任务名称</span></td>              
           <td style="width:210px;height:30px;text-align:left;padding-left:10px;" >
           <input name="tname" class="easyui-textbox"  data-options="required:true,missingMessage:'任务名称不能为空!'"  style="width:200px;" /></td>              
       </tr> 
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">显示模式</span></td>           
           <td style="width:210px;height:30px;text-align:left;padding-left:10px;" >
			<select name="theme" class="easyui-combobox"  data-options="required:true,missingMessage:'显示模式不能为空!'"   style="width:200px;">
			 <option value="0">主题模式</option>
			 <option value="1"  selected="selected">解锁模式</option>
			</select>
           </td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">预设时间</span></td>              
           <td style="width:210px;height:30px;text-align:left;padding-left:10px;" >
           <input  name="ttime"  class="easyui-datetimebox" data-options="required:true,missingMessage:'预设时间不能为空!'"  editable="false" style="width:200px;" /></td>              
        </tr> 
   </table>  
   </form>
   </div>
   <div id="mbthemeItemdlg-buttons" align="right">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="SaveMBThemeitem()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#mbthemeItemdlg').dialog('close')" style="width:90px">取消</a>
    </div>
 
 
  <script type="text/javascript">
  var themeurl;	
  $(function(){
     $('#mbthemelist').datagrid({
			idField:'tid',	
			title:'任务信息列表',
			//width:2000,
			fit:true,
			height:450 ,
			url:'servlet/PlServlet?method=GetThemeTaskList' ,
			//fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			//nowrap: false ,				//折行显示 为true 显示在一会 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,				
			columns:[[			
				{
					field:'tname' ,
					title:'任务名称' ,
					width:150, 
					height:50
				},				
				{
					field:'theme' ,
					title:'显示模式' ,
					width:150, 
					formatter: function(value,row,index){
       						if(value==0) return '主题方式';
       						else if(value==1) return '解锁模式';
       						else return '错误';
    				}  
				},				
				{
					field:'ttime' ,
					title:'预设时间' ,
					width:150 
				},
				{
					field:'opertime' ,
					title:'创建时间' ,
					width:150, 
				    hidden: true
				},
				{
					field:'operator',
					title:'操作员',
					width:150, 
				    hidden: true
				},
				{
					field:'tid',
					title:'应用范围',
					width:150, 
					formatter: function(value,row,index){
       					return '<a title="'+ row.tname+'" href="javascript:" onclick="SeeThemePushDetail('+value+',this.title)" style="font:12px;">点击查看</a>';
    				}  
				}
				]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[5,10,15,20,50], 		
			toolbar: [
			{
               text: "添加",
               iconCls: "icon-add",
               handler: function () { 
                   $('#mbthemeItemForm').form('clear');
                   $('#mbthemeItemdlg').dialog('open').dialog('setTitle', '添加新任务'); 
                   themeurl="servlet/PlServlet?method=AddMbthemeItem";
               }
            }, '-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler:function () {
                   var row = $('#mbthemelist').datagrid('getSelected');
			       if (row) {
			           $('#mbthemeItemForm').form('load', row);
			           $('#mbthemeItemdlg').dialog('open').dialog('setTitle', '编辑任务信息');
			           themeurl="servlet/PlServlet?method=EditMbthemeItem";
			       }
			       else $.messager.alert("提示", "请选择一行进行编辑！", "info");
               }              
            },'-',
            {
               text: "删除",
               iconCls: "icon-remove",
               handler:function (){
                   var row = $('#mbthemelist').datagrid('getSelected');
			       if (row) {
			          $.messager.confirm('确认','您确认删除这个任务?',function(data){  
                           if(data){
                            $.ajax({ 
                            type: "post",  
                            url: "servlet/PlServlet?method=DeleteMbthemeItem", 
                            data: {tid:row.tid},  
                            dataType: "json",  
                            success: function(data) {  if(data!=null)    {alert("删除成功！"); $('#mbthemelist').datagrid('reload'); }  else   alert("删除失败");  $('#pushlist').datagrid('reload');  },
                            error:function(data) {alert("删除失败");  $('#mbthemelist').datagrid('reload'); }
                            });   
                          }
                     });  
			       }
			       else $.messager.alert("提示", "请选择一行！", "info");
               }	 
            },'-']
			});
  });
    
  function SaveMBThemeitem(){
    $('#mbthemeItemForm').form('submit', {
          url: themeurl,
          onSubmit: function () { return $(this).form('validate'); },
          success: function (result) {
              if (result.errorMsg) {
                  $.messager.show({ title:'错误', msg: result.errorMsg });
              }
              else {
                  $('#mbthemeItemdlg').dialog('close');        // close the dialog
                  $('#mbthemelist').datagrid('reload');    // reload the user data
              }
          },
          error:function(data) {alert("数据保存失败");  $('#mbthemelist').datagrid('reload'); }
    });
  }
  function SeeThemePushDetail(tid,tname)
  { 
	 $.ajax({ 
      type: "post",  
      url: "servlet/PhServlet?method=GetCurUserType", 
      dataType: "json",  
      data:{none:"none"},
      success: function(data) { 
         if(data!=null)    {
		     var theMBTheme='<span>显示模式应用详情</span><input type=hidden value='+tid+'/>';
		     var url="${pageContext.request.contextPath }/pages/device/MBthemeDetail.jsp?role='"+encodeURI(data.role)+"'&mbtid="+tid+"&mbtname="+encodeURI(tname);
		     var tabs = $('#tt').tabs('tabs');      
		     for(var j=0;j<tabs.length;j++){  
		         var x = tabs[j].panel('options').title;               
		         if(x==theMBTheme) { $('#tt').tabs('select',theMBTheme);return;}
		         if(x.match(/^<span>显示模式应用详情<\/span><input type=hidden value=/g)!=null) {
		           $('#tt').tabs('select',x);
		           var y = $('#tt').tabs('getSelected');
		           $("#tt").tabs('update',{tab:y,options:{title:theMBTheme,href:url}});
		           //y.panel('refresh');
		           return;
		         }
		     }  
	         $('#tt').tabs('add', {title:theMBTheme, href :url,closable : true });  
         }  else   alert("查看失败"); 
      },
      error:function(data) {alert("查看失败");}
     }); 	 	                
	}
  </script>
</body>
</html>
