<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>IcontList</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
    <span id=ccid  style="display:node"></span>
    <span id=ctitle  style="display:none"></span>
    <span id=icon_class  style="display:none"></span>
    <span id=csort  style="display:none"></span>
    <span id=cnamex  style="display:none"></span>
    <span id=csize  style="display:none"></span>
    <span id=clink  style="display:none"></span>
    <span id=coperator  style="display:none"></span>
    <span id=coperdate  style="display:none"></span>
    
	<div id="mycontentlistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="mycontentlist"></table>
	</div>
	</div>
	<div id="clickpic" class="easyui-dialog"  closed="true"  ></div>
	<div id="clicklink" class="easyui-dialog"  closed="true" ></div>
	
	<input id=mycloseaddcttab type="hidden" onclick="mycloseaddcttab()"/>
	<input id=mycloselookcttab type="hidden" onclick="mymycloselookcttab()"/>
	<input id=mycloseupdatecttab type="hidden" onclick="mycloseupdatecttab()"/>
	
	<script type="text/javascript">	
	 $(function(){
		$('#mycontentlist').datagrid({
			idField:'id',	
			title:'图标信息列表',
			//width:2000,
			fit:true,
			height:450 ,
			url:'servlet/IconServlet?method=GetList' ,
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
					title:'标题' ,
					width:150, 
					height:50
				},	
				{
					field:'icon_class' ,
					hidden:true
				},	
				{
					field:'icon_className' ,
					title:'分类' ,
					width:150, 
					height:50
				},
				{
					field:'iconname',
					title:'图标',
					width:100 ,
					height:50,
					align:'center',
				formatter:function(value,row,index){
    				return '<img src="'+'images/icons/'+value+'" style="width:50;height:50" />';
    								
    			   } 
    								 
    		  },
				{
					field:'picurl',
					title:'链接',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="linkto(\'http://' + value+ '\')" style="font:12px;">'+row.picurl+'</a>'; 
    								 }   
				},
				{
					field:'updatetime' ,
					title:'更新时间' ,
					width:150 ,
					height:50
				},
				{
					field:'manager' ,
					title:'操作员' ,
					width:150, 
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
               var newCt='<span>添加新图标</span>';
                if ($('#tt').tabs('exists',  newCt)) { $('#tt').tabs('select', newCt); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/IconAdd.jsp";
                  $('#tt').tabs('add',
			      {
				   title :newCt,
				   content : '<iframe id=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
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
                            var selectedRow = $('#mycontentlist').datagrid('getSelected');
                            $.ajax({ 
                            type: "post",  
                            url: "servlet/IconServlet?method=DeleteIcon", 
                            data: {id:selectedRow.id},  
                            //dataType: "json",  
                            success: function(data) {  if(data!=null)    {alert("删除成功！"); $('#mycontentlist').datagrid('reload'); }  else   alert("删除失败"); },
                            error:function(data) {   alert("删除失败"); }
                            });   
                           $('#mycontentlist').datagrid('reload');  }
                         });  
			            }}	 
            },'-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler: function () { 
                if(fctrow()==-1) {alert("请选择一行!");return;}
                  var selectedRow = $('#mycontentlist').datagrid('getSelected');
                var theCt='<span>修改图标</span><input type=hidden value='+selectedRow.id+'/>';
                if ($('#tt').tabs('exists',  theCt)) { $('#tt').tabs('select', theCt); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/IconUpdate.jsp";
                  document.getElementById("ccid").innerHTML=selectedRow.id;
                  document.getElementById("ctitle").innerHTML=selectedRow.title;
                  document.getElementById("icon_class").innerHTML=selectedRow.icon_class;
                  document.getElementById("cnamex").innerHTML=selectedRow.iconname;
                  document.getElementById("clink").innerHTML=selectedRow.picurl;
                  $('#tt').tabs('add',
			      {
				   title :theCt,
				   content : '<iframe id=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
                             
            },'-']
			});
	});
	
	function mycloseupdatecttab()
	{
	  $('#tt').tabs('close','<span>修改图标</span><input type=hidden value='+document.getElementById("ccid").innerHTML+'/>');
	  $('#mycontentlist').datagrid('reload');  
	}
	function mycloseaddcttab()
	{
	    $('#tt').tabs('close','<span>添加新图标</span>');
	    $('#mycontentlist').datagrid('reload');  
	}
	
	function fctpage(){
        return $('#mycontentlist').datagrid('getPager').data("pagination").options.pageNumber;
    } 
    
    
    function fctrow(){
        var row = $('#mycontentlist').datagrid('getSelected');
        return $('#mycontentlist').datagrid('getRowIndex',row);       
    } 
    
    
	function linkto(v){	
	    $('#clicklink').dialog({  
          title: '查看链接',  
          width: 800,  
          height: 500,  
          left:(screen.width-320)/2,
          top:(screen.height-500)/3,
          closed: false,    
          cache: false,    
          href: '',    
          modal: true  
        });  
       $('#clicklink').dialog('refresh', 'pages/push/ContentLoadLink.jsp?url='+v);      
	}
	
	</script>  
</body>
</html>
