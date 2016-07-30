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
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
</head>
<body>
   
	<div id="contentlistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="contentlist"></table>
	</div>
	</div>
	<script type="text/javascript">	
	 $(function(){
		$('#contentlist').datagrid({
			idField:'cid',	
			title:'内容信息列表',
			//width:2000,
			fit:true,
			height:450 ,
			url:'servlet/AddProductServlet?method=showProduct' ,
			//fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			//nowrap: false ,				//折行显示 为true 显示在一会 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
				{
					field:'id' ,
					hidden:true
				},	
				{
					field:'ok' ,
					checkbox:true ,
					width:20
				},	
				{
					field:'name' ,
					title:'名称' ,
					width:145
				},	
				{
					field:'operator',
					title:'运营商',
					width:70 ,
				},						
				{
					field:'type' ,
					title:'类型' ,
					width:60, 
				},	
				{
					field:'price',
					title:'价值',
					width:70 ,
				},	
				{
					field:'price',
					title:'价格',
					width:70 ,
				},	
				{
					field:'nums',
					title:'库存',
					width:70 ,
				},	
				{
					field:'memo',
					title:'备注',
					width:600 ,
				},{
					field:'status',
					title:'状态',
					width:600 ,
				},
				]] ,
			pagination: true , 
			pageSize: 20,
			pageList:[5,10,15,20,50], 
			toolbar: [
			{
               text: "添加",
               iconCls: "icon-add",
               handler: function () {
                  var newPl='<span>添加商品</span>';
                  var url="${pageContext.request.contextPath }/pages/usermanager/wechat_manage/add_product.jsp";
                  var tabs = $('#tt').tabs('tabs');  
		          for(var j=0;j<tabs.length;j++){  
		           var x = tabs[j].panel('options').title;        
		          if(x==newPl) { $('#tt').tabs('select',newCt);return;}
		          else  if(x.match(/^<span>修改内容<\/span>/g)!=null) 
		          {
		            $('#tt').tabs('select',x);
		            var y = $('#tt').tabs('getSelected');
		            $("#tt").tabs('update',{tab:y,options:{title:newPl, content : '<iframe  name=newPl frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>'}});
		            //y.panel('refresh');
		            return;
		          }		        
		        }
                  $('#tt').tabs('add',
			      {
				   title :newPl,
				   content : '<iframe name=newPl frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      }); 	
               }
            }, '-',
            {
               text: "删除",
               iconCls: "icon-remove",
               handler:function (){
                        if(fctrow()==-1) alert("请选择一行!");
                        else { 
                         $.messager.confirm('确认','确认删除?',function(data){  
                           if(data){
                            var selectedRow = $('#contentlist').datagrid('getSelected');
                            $.ajax({ 
                            type: "post",  
                            url: "servlet/CtServlet?method=DeleteContent", 
                            data: {cid:selectedRow.cid},  
                            dataType: "json",  
                            success: function(data) {  if(data!=null)    {alert(data.deletecontent); $('#contentlist').datagrid('reload'); }  else   alert("删除失败"); },
                            error:function(data) {   alert("删除失败"); }
                            });   
                           $('#contentlist').datagrid('reload');  }
                         });  
			            }}	 
            },'-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler: function () { 
                if(fctrow()==-1) {alert("请选择一行!");return;}
                var selectedRow = $('#contentlist').datagrid('getSelected');
                var theCt='<span>修改内容</span><input type=hidden value='+selectedRow.cid+'/>';
                var url="${pageContext.request.contextPath }/pages/push/ContentUpdate.jsp";
                  document.getElementById("name").innerHTML=selectedRow.name;
                  document.getElementById("ctitle").innerHTML=selectedRow.title;
                  document.getElementById("csort").innerHTML=selectedRow.sort;
                  document.getElementById("cscolor").innerHTML=selectedRow.scolor;
                  document.getElementById("cnamex").innerHTML=selectedRow.namex;
                  document.getElementById("clink").innerHTML=selectedRow.link;
                  document.getElementById("ciconstr").innerHTML=selectedRow.iconstr;
                var tabs = $('#tt').tabs('tabs');  
		        for(var j=0;j<tabs.length;j++){  
		          var x = tabs[j].panel('options').title;        
		          if(x==theCt) { $('#tt').tabs('select',theCt);return;}
		          else  if(x.match(/^<span>修改内容<\/span>/g)!=null||x.match(/^<span>添加新内容<\/span>/g)!=null) 
		          {
		            $('#tt').tabs('select',x);
		            var y = $('#tt').tabs('getSelected');
		            $("#tt").tabs('update',{tab:y,options:{title:theCt, content : '<iframe name=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>'}});
		            //y.panel('refresh');
		            return;
		          }		        
		        }
                  $('#tt').tabs('add',
			      {
				   title :theCt,
				   content : '<iframe name=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      });   
                  
               }
                             
            },'-'
            ]
			});
	});
	function fctrow(){
        var row = $('#contentlist').datagrid('getSelected');
        return $('#contentlist').datagrid('getRowIndex',row);       
    }
	</script>  
</body>
</html>
