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
  <input id="nodeid"  type="hidden" value=""/>
<div style="height:100%">
    <table id="conclasslist" class="easyui-treegrid"  style="height:100%;width:100%;"
		    data-options="
                url:'servlet/QueServlet?method=GetContClasslist&id=&random='+Math.random(),
                method: 'get',
                rownumbers: true,
			    idField: 'id',
			    treeField: 'class_name',
			    onBeforeExpand : function(row, param) {
                   $(this).treegrid('options').url = 'servlet/QueServlet?method=GetContClasslist&id='+row.id+'&random='+Math.random();
                },
			    toolbar:'#conclasslist_tb'">
	    <thead>
		    <tr>
			    <th data-options="field:'id',hidden:true" >id</th>
			    <th data-options="field:'class_name'"  width="18%">名称</th>
			    <th data-options="field:'class_value'"  width="30%">编码</th>
			    <th data-options="field:'class_queid',hidden:true">queid</th>
			    <th data-options="field:'class_quename'"  width="30%">序列</th>
			    <th data-options="field:'class_duration'"  width="20%">时长(秒)</th>
		    </tr>
	    </thead>
    </table>
    <div id="conclasslist_tb" >  
        <a href="javascript:void(0)" onclick="addMyDepart()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加同级</a>
        <a href="javascript:void(0)" onclick="addDirDepart()" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true">添加下级</a>
        <a href="javascript:void(0)" onclick="editDepart()" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true">修改</a>
        <a href="javascript:void(0)" onclick="deleteDepart()" class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:true">删除</a>
    </div>
    
    
    <div id="departdlg" class="easyui-dialog" style="text-align:left;padding:20px 20px;width:305px;" modal="true" closed="true" buttons="#departdlg_buttons">
         <form  id = "departform">
         <input id="departurl" name="method" type="hidden" />
         <input id="contclasscode" name="id" type="hidden" />
        
         <input name="class_name" class="easyui-textbox" data-options="required:true,buttonText:'名&emsp;称',buttonAlign:'left',missingMessage:'名称不能为空!'" style="width:250px;height:25px;" /> 
         <br> <br>
         <input id="class_value"  name="class_value" class="easyui-textbox" data-options="buttonText:'键&emsp;值',buttonAlign:'left'" style="width:250px;height:25px;" /> 
         <br> <br>
         <input name="class_queid" class="easyui-combobox" data-options="url:'servlet/PhServlet?method=GetPushQueTitleList',valueField:'qid',textField:'title',editable:false,buttonText:'序&emsp;列',buttonAlign:'left',panelWidth:206,panelAlign:'right'" style="width:250px;height:25px;" /> 
         <br> <br>
         <input name="class_duration" class="easyui-textbox" data-options="buttonText:'时&emsp;长',buttonAlign:'left'" style="width:250px;height:25px;" /> 
        </form>  
   </div>
   <div id="departdlg_buttons">
     <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveDepart()" style="width:90px">保存</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#departdlg').dialog('close')" style="width:90px">取消</a>
   </div>
   
</div>

<script type="text/javascript">  
    function addMyDepart(){  
        $('#departform').form('clear');      
        var node = $('#conclasslist').treegrid('getSelected');
        if (node&&node.id.length>4) {
            $('#contclasscode').val(node.id.substring(0,node.id.length-4));
             $('#nodeid').val(node.id.substring(0,node.id.length-4));
        }
        else {$('#contclasscode').val("");$('#nodeid').val("");}
        addDepart();
    }
    function addDirDepart(){
        $('#departform').form('clear');      
        var node = $('#conclasslist').treegrid('getSelected');
        if (node) {
            $('#contclasscode').val(node.id);
            $('#nodeid').val(node.id);
        }
        else {$('#contclasscode').val("");$('#nodeid').val("");}
        addDepart();
    }
    function addDepart() {
        $.ajax({
            type: "POST",
            url: "servlet/QueServlet?method=GetWeiDuCode&code="+$('#contclasscode').val(),
            cache: false,
            dataType: "text",
            success: function (data) {
            if(data!="failure"&&data.length>0){
               $('#class_value').textbox({ readonly: true });
               $('#class_value').textbox('setValue',data.replace(/-/g, ""));
              $('#departurl').val("AddContClass");
               $('#departdlg').dialog('open').dialog('setTitle', '添加类别'); 
            }
            else $.messager.alert("提示", "获取编码错误!！", "info");
            }
        });
      
    }
    function editDepart() {
        var row = $('#conclasslist').datagrid('getSelected');
        if (row) {
            $('#nodeid').val(row.id.substring(0,row.id.length-4));
            $('#departform').form('load', row);
            $('#departurl').val("EditContClass");
            $('#class_value').textbox({ readonly: true });
            $('#departdlg').dialog('open').dialog('setTitle', '编辑类别信息');
        }
        else {
            $.messager.alert("操作提示", "请先选中一行进行编辑！", "info");$('#nodeid').val("");
        }
    }
    
    function deleteDepart() {
        var node = $('#conclasslist').treegrid('getSelected');
        if (node) {
        if(node.id.length<=4)  { $('#nodeid').val("");$.messager.alert("提示", "一级节点不能删除!！", "info");return; }
            $.messager.confirm('确认', '您确定要删除这个类别? 删除后，其子项也会被删除！', function (r) {
             $('#nodeid').val(node.id.substring(0,node.id.length-4));
                if (r) {
                    $.post('servlet/QueServlet?method=DeleteContClass', { code: node.id }, function (result) {
                        if (result == "success") {
                            //$('#conclasslist').treegrid('remove', node.id);
                            $.messager.alert("提示", "删除成功！", "info");
                            freshDepartTree();
                        }
                        //else if (result == "isused") { $.messager.alert("提示", "该类别不能删除！", "info"); }
                        else $.messager.alert("错误", "删除失败！", "warning");
                    }, 'text'); //返回数据类型
                }
            });
        }
        else {$('#nodeid').val("");
            $.messager.alert("提示", "请先选中一行要删除的类别！", "info");
        }
    }

    
    function saveDepart() {
        $('#departform').form('submit', {
            url: 'servlet/QueServlet',
            onSubmit: function () { return $(this).form('validate'); },
            success: function (result) {
                if (result == 'success') {
                    $.messager.alert("提示", "操作成功！", "info");
                    freshDepartTree();
                }
                else $.messager.alert("错误", "操作失败!", "warning");
                $('#departdlg').dialog('close');  // close the dialog
            }
        });
    }
    
    function freshDepartTree() {
    $('#conclasslist').treegrid('reload',$('#nodeid').val());
      // $('#conclasslist').treegrid({ url: 'servlet/QueServlet?method=GetContClasslist&id='+$('#nodeid').val()+'&random='+Math.random() });
       //$('#conclasslist').datagrid('clearSelections');
    }  
</script>
</body>
</html>