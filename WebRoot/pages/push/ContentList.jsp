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
    <span id=ccid  style="display:none"></span>
    <span id=ctitle  style="display:none"></span>
    <span id=classify  style="display:none"></span>
    <span id=csort  style="display:none"></span>
    <span id=cscolor style="display:none"></span>
    <span id=cnamex  style="display:none"></span>
    <span id=csize  style="display:none"></span>
    <span id=clink  style="display:none"></span>
    <span id=coperator  style="display:none"></span>
    <span id=coperdate  style="display:none"></span>
    <span id=ciconstr style="display:none"></span>
        <span id=aiconcnamex  style="display:none"></span>
        <span id=aiconcid  style="display:none"></span>
            
    <div id="iconcdlg" class="easyui-dialog" style="width:500px;height:400px;"  closed="true" modal="true" buttons="#iconcdlg-buttons" >     
     <table id="iconclist"></table>
    </div>
	<div id="iconcdlg-buttons" align="right">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="SaveNewIconc()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#iconcdlg').dialog('close')" style="width:90px">取消</a>
    </div>
    
    
	<div id="contentlistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="contentlist"></table>
	</div>
	</div>
	<div id="clickpic" class="easyui-dialog"  closed="true"  ></div>
	<div id="clicklink" class="easyui-dialog"  closed="true" ></div>
	<input id=closeaddcttab type="hidden" onclick="closeaddcttab()"/>
	<input id=closelookcttab type="hidden" onclick="closelookcttab()"/>
	<input id=closeupdatecttab type="hidden" onclick="closeupdatecttab()"/>
	<input id=AddNewIconcBtn type="hidden" onclick="AddNewIconc()"/>	
	
	<script type="text/javascript">	
	 $(function(){
		$('#contentlist').datagrid({
			idField:'cid',	
			title:'内容信息列表',
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
					title:'标题' ,
					width:150, 
					height:50
				},						
				{
					field:'classify' ,
					hidden:true
				},	
				{
					field:'classifyName' ,
					title:'分类' ,
					width:150, 
					height:50
				},				
				{
					field:'sort' ,
					title:'积分值' ,
					width:50, 
					height:50
				},						
				{
					field:'namex' ,
					title:'格式' ,
					width:150, 
					height:50,
					hidden:true,
					formatter: function(value,row,index){
       									return adshowform(value); 
    						   }  
				},
				{
					field:'size' ,
					title:'大小(字节)' ,
					width:100, 
					height:50
				},	
				{
					field:'scolor' ,
					title:'字体颜色',
					width:60, 
					formatter: function(value,row,index){
					  if(value.match(/^[0-9a-fA-F]{6}$/g)) return '<span style="background:#'+value+';color:'+grayColor(value)+';">'+value+'</span>';
       				  else return "错误";
    				}   
				},	
				{
					field:'cid',
					title:'快速浏览',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="lookpic(\'' + row.namex+ '\')" style="font:12px;">点击查看</a>'; 
    								 }   
				},
				{
					field:'link',
					title:'链接',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="linkto(\'' + value+ '\')" style="font:12px;">点击查看</a>'; 
    								 }   
				},	
				{
					field:'iconstr',
					title:'图标',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
       									return '<a href="javascript:" onclick="lookicon(\'' + value+ '\')" style="font:12px;">点击查看</a>'; 
    								 }   
				},
				{
					field:'operdate' ,
					title:'更新时间' ,
					width:150 ,
					height:50
				},
				{
					field:'operator' ,
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
                  var newCt='<span>添加新内容</span>';
                  var url="${pageContext.request.contextPath }/pages/push/ContentAdd.jsp";
                  var tabs = $('#tt').tabs('tabs');  
		          for(var j=0;j<tabs.length;j++){  
		           var x = tabs[j].panel('options').title;        
		          if(x==newCt) { $('#tt').tabs('select',newCt);return;}
		          else  if(x.match(/^<span>修改内容<\/span>/g)!=null) 
		          {
		            $('#tt').tabs('select',x);
		            var y = $('#tt').tabs('getSelected');
		            $("#tt").tabs('update',{tab:y,options:{title:newCt, content : '<iframe  name=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>'}});
		            //y.panel('refresh');
		            return;
		          }		        
		        }
                  $('#tt').tabs('add',
			      {
				   title :newCt,
				   content : '<iframe name=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
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
                  document.getElementById("ccid").innerHTML=selectedRow.cid;
                  document.getElementById("ctitle").innerHTML=selectedRow.title; 
                  document.getElementById("classify").innerHTML=selectedRow.classify;
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
                             
            },'-',
            {
               text: "查看",
               iconCls: "icon-ok",
                handler: function () { 
                if(fctrow()==-1) {alert("请选择一行!");return;}
                  var selectedRow = $('#contentlist').datagrid('getSelected');
                var theCt='<span>查看内容</span><input type=hidden value='+selectedRow.cid+'/>';
                if ($('#tt').tabs('exists',  theCt)) { $('#tt').tabs('select', theCt); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/ContentLook.jsp";
                  document.getElementById("ccid").innerHTML=selectedRow.cid;
                  document.getElementById("ctitle").innerHTML=selectedRow.title;
                  document.getElementById("classify").innerHTML=selectedRow.classify;
                  document.getElementById("csort").innerHTML=selectedRow.sort;
                  document.getElementById("csize").innerHTML=selectedRow.size;
                  document.getElementById("cnamex").innerHTML=selectedRow.namex;
                  document.getElementById("clink").innerHTML=selectedRow.link;
                  document.getElementById("coperator").innerHTML=selectedRow.operator;
                  document.getElementById("coperdate").innerHTML=selectedRow.operdate;
                  $('#tt').tabs('add',
			      {
				   title :theCt,
				   content : '<iframe name=newct frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
            },'-']
			});
	});
	function closelookcttab()
	{
	  $('#tt').tabs('close','<span>查看内容</span><input type=hidden value='+document.getElementById("ccid").innerHTML+'/>');
	}
	function closeupdatecttab()
	{
	  $('#tt').tabs('close','<span>修改内容</span><input type=hidden value='+document.getElementById("ccid").innerHTML+'/>');
	  $('#contentlist').datagrid('reload');  
	}
	function closeaddcttab()
	{
	    $('#tt').tabs('close','<span>添加新内容</span>');
	    $('#contentlist').datagrid('reload');  
	}
	function fctpage(){
        return $('#contentlist').datagrid('getPager').data("pagination").options.pageNumber;
    } 
    function fctrow(){
        var row = $('#contentlist').datagrid('getSelected');
        return $('#contentlist').datagrid('getRowIndex',row);       
    } 
	function linkto(v){	
	 $('#clicklink').dialog({  
          title: '查看链接',  
          width: 320,  
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
	function lookpic(v){	
	 $('#clickpic').dialog({  
          title: '查看图片',  
          width: 320,  
          height: 500, 
          left:(screen.width-320)/2,
          top:(screen.height-500)/3, 
          closed: false,    
          cache: false,    
          href: '',    
          modal: true  
    });  
    $('#clickpic').dialog('refresh', 'pages/push/ContentLoadImage.jsp?url='+v);  
	}
	function lookicon(v){	
	 $('#clickpic').dialog({  
          title: '查看图标',  
          width: 320,  
          height: 500, 
          left:(screen.width-320)/2,
          top:(screen.height-500)/3, 
          closed: false,    
          cache: false,    
          href: '',    
          modal: true  
    });  
    $('#clickpic').dialog('refresh', 'pages/push/ContentLoadIcon.jsp?url='+v);  
	}
	</script>  

   <script type="text/javascript">	
     function AddNewIconc(){ 
         $('#iconcdlg').dialog('open').dialog('setTitle','添加新图标');	
         $('#iconclist').datagrid({
		 	idField:'id',	
			fit:true,
			url:'servlet/IconServlet?method=GetList' ,
			striped: true ,	//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
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
					field:'title' ,
					title:'标题' ,
					width:145
				},	
				{
					field:'iconname',
					title:'图标',
					width:70 ,
					formatter: function(value,row,index){					    
       					 	return '<img src="'+'images/icons/'+value+'" style="width:50;height:50" />';
    				}   
				},						
				{
					field:'picurl' ,
					title:'链接' ,
					width:145, 
					formatter: function(value,row,index){
       				   		return '<a href="javascript:" onclick="linkto(\'http://' + value+ '\')" style="font:12px;">'+row.picurl+'</a>'; 
    				}  
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
		  }); 
       }
       function SaveNewIconc(){
         var row = $('#iconclist').datagrid('getSelected');
         if(row){
          document.getElementById("aiconcnamex").innerHTML=row.iconname;
          document.getElementById("aiconcid").innerHTML=row.id;
	      frames["newct"].document.getElementById("SaveNewIconcBtn").click(); 
	      $('#iconcdlg').dialog('close');
	     }
	     else{
	     alert("请选择一个图标!");
	     }
       }
    </script>  
</body>
</html>
