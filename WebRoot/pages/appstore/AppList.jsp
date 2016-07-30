<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>AppList</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>    
	<div id="applistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="applist"></table>
	</div>
	</div>
	
	<div id="appinfodlg" class="easyui-dialog" style="text-align:left;padding:10px 10px 0px 10px;width:476px;" modal="true" closed="true" buttons="#appinfodlg_buttons">
	  <form id="appinfoform" action="" method="post" enctype="multipart/form-data">  
	   <input name="app_id" type="hidden" />
	   <input name="app_icon" type="hidden" />
	   <input name="app_downurl" type="hidden" />
	   <input name="app_title" class="easyui-textbox"  data-options="required:true,buttonText:'名称',buttonAlign:'left',missingMessage:'不能为空!'" style="width:200px;" />&emsp;
	   <input name="app_score" class="easyui-numberbox"  data-options="required:true,buttonText:'分数',buttonAlign:'left',missingMessage:'不能为空!'" style="width:80px;" />&emsp;
       <select name="app_isshow" class="easyui-combobox" data-options="editable:false,buttonText:'是否显示',buttonAlign:'left',panelWidth:73,panelAlign:'right',required:true,missingMessage:'不能为空!'" style="width:130px;">
         <option value="1">显示</option>
         <option value="0">不显示</option>
       </select><br><br> 
       <input name="app_class" editable="false"  class="easyui-combotree" data-options="url:'servlet/JsonServlet?method=GetAppClassList',required:true,missingMessage:'不能为空!',buttonText:'类别',buttonAlign:'left',panelWidth:168,panelAlign:'right'" style="width:200px;"/>&emsp;
       <input class="easyui-filebox" name="app_file" data-options="prompt:'选择apk...'" style="width:225px;"/> 
       <br>
       <input name="app_memo" class="easyui-textbox"  data-options="required:true,buttonText:'备注',buttonAlign:'left',missingMessage:'不能为空!'" style="width:440px;" />&emsp;
	  </form> 
    </div>
	<div id="appinfodlg_buttons">
	   <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="SaveAppInfo()" style="width:90px">保存</a>
	   <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#appinfodlg').dialog('close')" style="width:90px">取消</a>
	</div>
	<script type="text/javascript">	
     var appurl = '';
	 $(function(){
		$('#applist').datagrid({
			idField:'app_id',	
			title:'手机App信息列表',
			fit:true,
			height:450 ,
			url:'servlet/AppServlet?method=GetAppList' ,
			striped: true ,				
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
			    {
					field:'app_id',
					hidden:true
				},	
			    {
					field:'app_title' ,
					title:'标题' ,
					width:120, 
					height:50
				},	
				{
					field:'app_icon',
					title:'图标',
					width:80 ,
					height:50,
					align:'center',
				    formatter:function(value,row,index){
	   			     	return '<img src="'+value+'" style="width:50;height:50" />';
	   			     } 
	   								 
	   		    },
			    {
					field:'app_className' ,
					title:'分类' ,
					width:100, 
					height:50
				},	
				{
					field:'app_size' ,
					title:'大小' ,
					width:100, 
					height:50
				},	
				{
					field:'app_isshow' ,
					title:'是否显示' ,
					width:100, 
					height:50,
					formatter: function(value,row,index){
	      				 if(value) return "显示"; else return "不显示";
	   				}  
				},	
				{
					field:'app_score' ,
					title:'分值' ,
					width:100, 
					height:50
				},
				{
					field:'app_downnum' ,
					title:'下载次数' ,
					width:100, 
					height:50
				},
				{
					field:'app_downurl',
					title:'下载链接',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
	      				       return '<a href="'+value+'" style="font:12px;">点击下载</a>'; 
	   						}   
				},
				{
					field:'app_memo' ,
					title:'备注' ,
					width:140 ,
					height:50
				},
				{
					field:'user_id' ,
					title:'上传者' ,
					width:120, 
					height:50
				},
				{
					field:'app_uptime' ,
					title:'上传时间' ,
					width:140 ,
					height:50
				}
				]] ,
			pagination: true , 
			pageSize: 20,
			pageList:[5,10,15,20,50], 
			toolbar: [
			{
	              text: "添加",
	              iconCls: "icon-add",
	              handler: function () { 
	                 $("#appinfoform").form("clear");
	                 $("#appinfodlg").dialog("open").dialog("setTitle", "新增App");
	                 appurl = "servlet/AppServlet?method=AddApp";
	              }
	           }, '-',
	           {
	              text: "修改",
	              iconCls: "icon-edit",
	              handler:function (){
	                var row = $("#applist").treegrid("getSelected");
			        if (row) {
			            $("#appinfoform").form("load", row);
			            $("#appinfodlg").dialog("open").dialog("setTitle", "编辑App");
	                    appurl = "servlet/AppServlet?method=EditApp";
			        }
			        else $.messager.alert("提示", "请选择一行进行编辑！", "info");
	             }	 
	           },'-',
	           {
	              text: "删除",
	              iconCls: "icon-remove",
	              handler: function () {
	                var row = $("#applist").treegrid("getSelected");
			        if (row) {
			            $.messager.confirm('确认', '确定删除?', function (r) {
			                if (r) {
			                    $.post('servlet/AppServlet?method=EditApp', { id: row.app_id,apk:row.app_downurl,icon:row.app_icon }, function (result) {
			                        if (result == "success") { $.messager.alert("提示", "删除成功！", "info");$('#applist').datagrid('reload');  }
			                        else $.messager.alert("错误", "删除失败！", "warning");
			                    }, 'text'); //返回数据类型
			                }
			            });
			        }
			        else $.messager.alert("提示", "没有选中一行记录！", "info");
	              }       
	           },'-']
			});
	});
	
	 function SaveAppInfo() {
        $('#appinfoform').form('submit', {
            url: appurl,
            onSubmit: function () {
                return $(this).form('validate');
            },
            success: function (result) {
                if (result.errorMsg) {
                    $.messager.show({ title: '错误', msg: result.errorMsg });
                }
                else {
                    $.messager.alert("提示","操作成功", "info");
                    $('#appinfodlg').dialog('close');   // close the dialog
                    $('#applist').datagrid('reload');   // reload the user data
                }
            }
        });
    }
	</script>  

</body>
</html>
