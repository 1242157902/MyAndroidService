<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
  </head>
  <body>
  
  <div id="pushdefaultlistlay" class="easyui-layout" style="width: 100%;height:100%" >
    <div region="center" >
		<table id="pushdefaultlist"></table>
    </div>
  </div>
  <div id="pushdefaultlistdlg" class="easyui-dialog" style="text-align:left;padding:20px 10px;width:540px;" modal="true" closed="true" buttons="#pushdefaultlistdlg_buttons">
	<form id="defaultqueform" action="servlet/QueServlet?method=SetDefaultQueId" method="post">  
	 <input name="com_no" type="hidden" />
	 <input name="com_name" class="easyui-textbox" editable="false"  data-options="required:true,buttonText:'公司名称',buttonAlign:'left',missingMessage:'公司名称不能为空!'" style="width:250px;height:28px;" /> 
     <input id="defaultqueid" name="qid" editable="false"  class="easyui-combobox" data-options="required:true,url:'servlet/PhServlet?method=GetPushQueTitleList', valueField:'qid',textField:'title', missingMessage:'序列不能为空!',buttonText:'默认序列',buttonAlign:'left',panelWidth:193,panelAlign:'right'" style="width:250px;height:28px"/> 
    </form> 
  </div>
  <div id="pushdefaultlistdlg_buttons">
     <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="SaveDefaultQue()" style="width:90px">保存</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#pushdefaultlistdlg').dialog('close')" style="width:90px">取消</a>
  </div>
    <script type="text/javascript">
    $(function(){	
    $('#pushdefaultlist').datagrid({
			idField:'com_no',	
			fit:true,
			height:450 ,
			url:'servlet/PhServlet?method=GetComDefQueList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
				{
					field:'com_no' ,
					title:'公司编号' ,
					width:150, 
					height:50,
					hidden:true
				},	
				{
					field:'com_name' ,
					title:'公司名称' ,
					width:150, 
					height:50
				},				
				{
					field:'title' ,
					title:'序列名称' ,
					width:150, 
					height:50
				},	
				{
					field:'qid',
					title:'操作',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
       					return "<a href=\"javascript:\" onclick=\"EditDefaultQue('"+index+"')\" style=\"font:12px;\">修改</a>"; 
    				}   
				}
				]] ,
			pagination: true , 
			pageSize: 20,
			pageList:[5,10,15,20,50]
			});
    }); 
    
    function SaveDefaultQue()
	{ 
	  $('#defaultqueform').form('submit', {
            onSubmit: function () { return $(this).form('validate'); },
            success: function (result) {
                if (result.errorMsg) {
                    $.messager.show({ title: '错误', msg: result.errorMsg });
                }
                else {
                    $('#pushdefaultlistdlg').dialog('close');        // close the dialog
                    $('#pushdefaultlist').datagrid('reload');    // reload the user data
                }
            }
        });
	}
	
	function EditDefaultQue(index)
	{
	     var row = $('#pushdefaultlist').datagrid('getData').rows[index];
         if (row) {
           $("#pushdefaultlistdlg").dialog("open").dialog("setTitle", "设置默认序列");
           $("#defaultqueform").form("load", row);}
	}
	</script>
  </body>
</html>
		