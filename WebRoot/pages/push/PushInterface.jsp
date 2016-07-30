<%@ page language="java" import="java.util.*"   pageEncoding="UTF-8"%>
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
<div style="height:100%">
    <table id="pushitemlist" class="easyui-datagrid"  style="height:100%;width:100%;"
		    data-options="
                url:'servlet/QueServlet?method=GetPushItemlist&random='+Math.random(),
                method: 'get',
                idField:'id',	
                rownumbers: true,
			    title:'模拟推送',
		     	loadMsg: '数据正在加载,请耐心的等待...' ,
		    	rownumbers:true ,
		    	singleSelect:true,
		    	pagination: true , 
				pageSize: 30,
				pageList:[10,30,100],
			    toolbar:'#conclasslist_tbx'">
	    <thead>
		    <tr>
			    <th data-options="field:'queid'"  width="30%">序列</th>
			    <th data-options="field:'startt'"  width="18%">开始时间</th>
			    <th data-options="field:'endt'"  width="20%">结束时间</th>
			    <th data-options="field:'pusht'"  width="20%">推送时间</th>
			    <th data-options="field:'id'" formatter="managerstr" width="10%">推送对象</th>
		    </tr>
	    </thead>
    </table>
    <div id="conclasslist_tbx" >      
     <form  id = "departformx" style="border:0px;padding:0px;margin:0px;""> 
      <input name="method" type="hidden"  value="AddPushItem"/>
      <input name="queid" class="easyui-combobox" data-options="url:'servlet/PhServlet?method=GetPushQueTitleList',valueField:'qid',textField:'title',editable:false,buttonText:'序&emsp;列',buttonAlign:'left',panelWidth:206,panelAlign:'right'" style="width:250px;height:25px;" />&emsp; &emsp;
      <input name="startt" class="easyui-datetimebox" data-options="required:true,buttonText:'开始时间',buttonAlign:'left',panelAlign:'right'" style="width:225px;height:25px;" /> &emsp;&emsp;
      <input name="endt" class="easyui-datetimebox" data-options="required:true,buttonText:'结束时间',buttonAlign:'left',panelAlign:'right'" style="width:225px;height:25px;" /> &emsp;&emsp;
      <a href="javascript:void(0)" onclick="saveDepartx()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">推&emsp;送</a>
      <br>
      <input name="imeis" class="easyui-textbox" data-options="buttonText:'用&emsp;户',buttonAlign:'left'" style="width:900px;height:25px;" /> 
     </form>   
    </div>
</div>
<div id="dialog-confirm" class="easyui-dialog" title="推送对象"  width="430px" height="300px" closed="true" modal="true" >     
<table id="pushloglistx" ></table>
</div>
<script type="text/javascript">  
    function saveDepartx() {
        $('#departformx').form('submit', {
            url: 'servlet/QueServlet',
            onSubmit: function () { return $(this).form('validate'); },
            success: function (result) {
                if (result.substring(0,7)== 'success') {
                    $('#pushitemlist').datagrid({ url: 'servlet/QueServlet?method=GetPushItemlist&random='+Math.random()});
                    $('#pushitemlist').datagrid('clearSelections');
                    $.messager.alert("提示", "推送成功！", "info");
                }
                else $.messager.alert("错误", "推送失败！", "error");
            }
        });
    }
    function managerstr(value,rowData,rowIndex) {
       return "<a href=\"javascript:void()\" onclick=\"lookpushlog("+value+")\">点击查看</a>";
    }
    function lookpushlog(id)
    {  
	    $('#pushloglistx').datagrid({
				idField:'id',					
				url:'servlet/QueServlet?method=GetPushLogUserList&logid='+id+'&random='+Math.random(),
				fit:true,
				striped: true,		
				loadMsg:'数据正在加载,请耐心的等待...' ,
				rownumbers:true ,
				singleSelect:true,					
				columns:[[{
						field:'id' ,
						title:'imei号' ,
						width:186, 
						height:50
					},	
					{
						field:'name' ,
						title:'手机号' ,
						width:186, 
						height:50
					}
					]] ,
				pagination: true , 
				pageSize: 30,
				pageList:[10,30,100]
		});
		$("#dialog-confirm").dialog("open");
	}
</script>
</body>
</html>