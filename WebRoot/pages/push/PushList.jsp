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
  <span id=ppid style="display:none"></span>
  <span id=ptitle style="display:none"></span>
  <span id=pqueid style="display:none"></span>
  <span id=pptype style="display:none"></span>
  <span id=ppushtime style="display:none"></span>
  <span id=ppriori style="display:none"></span>
  <span id=penddate style="display:none"></span>
  <div id="pushlistlay" class="easyui-layout" style="width: 100%;height:100%" >
	<div region="center" >
		<table id="pushlist"  class="easyui-datagrid"></table>
	</div>
  </div>
  <input id=CloseAddPushTabBtn type="hidden" onclick="CloseAddPushTab()"/>
  <input id=CloseUpdatePushTabBtn type="hidden" onclick="CloseUpdatePushTab()"/>
  <script type="text/javascript">	
  $(function(){
     $('#pushlist').datagrid({
			idField:'pid',	
			title:'推送信息列表',
			//width:2000,
			fit:true,
			height:450 ,
			url:'servlet/PhServlet?method=GetPushList' ,
			//fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			//nowrap: false ,				//折行显示 为true 显示在一会 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,					
			columns:[[
				{
					field:'queid' ,
					title:'推送序列',
					hidden: true
				},				
				{
					field:'oper' ,
					title:'推送序列' ,
					width:150, 
					height:50
					//hidden: true
				},				
				{
					field:'priori' ,
					title:'推送类型' ,
					width:50, 
					formatter: function(value,row,index){
       									if(value==0||value=='0') return '普通';
       									else if(value==1||value=='1') return '特推';
       									else return '错误';
    						   }  
				},				
				{
					field:'enddate' ,
					title:'推送有效期' ,
					width:150 
				},
				{
					field:'ptype' ,
					title:'推送方式' ,
					width:150, 
					formatter: function(value,row,index){
       									return pushway(value); 
    						   }  
				},
				{
					field:'pushtime' ,
					title:'推送时间' ,
					width:150, 
					height:50
				    //hidden: true
				},
				{
					field:'title' ,
					title:'推送描述' ,
					width:150, 
					height:50
				},	
				{
					field:'pid' ,
					title:'推送情况' ,
					width:150, 
					formatter: function(value,row,index){
       								return '<a title="'+ row.title+'" href="javascript:" onclick="SeePushDetail('+row.pid+',this.title)" style="font:12px;">点击查看</a>';
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
                 var newPhitem='<span>添加推送项</span>';
                 if ($('#tt').tabs('exists', newPhitem)) { $('#tt').tabs('select',newPhitem); } 
                 else {
                   var url="${pageContext.request.contextPath }/pages/push/PushAdd.jsp";
                   $('#tt').tabs('add',
			       {
				      title :newPhitem,
				      content : '<iframe id=newPush frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                      closable : true
			        });   	
			      }   		
               }
            }, '-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler:function () {
                 var selectedRow = $('#pushlist').datagrid('getSelected');
                 if($('#pushlist').datagrid('getRowIndex',selectedRow)==-1) {alert("请选择一行!");return;}
                 var thePhitem='<span>修改推送项</span><input type=hidden value='+selectedRow.pid+'/>';
                 if ($('#tt').tabs('exists',  thePhitem)) { $('#tt').tabs('select',thePhitem); } 
                 else {
                  var url="${pageContext.request.contextPath }/pages/push/PushUpdate.jsp";
                  document.getElementById("ppid").innerHTML=selectedRow.pid;
                  document.getElementById("ptitle").innerHTML=selectedRow.title;
                  document.getElementById("pqueid").innerHTML=selectedRow.queid;
                  document.getElementById("pptype").innerHTML=selectedRow.ptype;
                  document.getElementById("ppushtime").innerHTML=selectedRow.pushtime;
                  document.getElementById("ppriori").innerHTML=selectedRow.priori;
                  document.getElementById("penddate").innerHTML=selectedRow.enddate;
                  $('#tt').tabs('add',
			      {
				   title :thePhitem,
				   content : '<iframe name=UpdatePush frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}                          
               }
              
            },'-',
            {
               text: "删除",
               iconCls: "icon-remove",
               handler:function (){
                        var selectedRow = $('#pushlist').datagrid('getSelected');
                        if($('#pushlist').datagrid('getRowIndex',selectedRow)==-1) alert("请选择一行!");
                        else { 
                         $.messager.confirm('确认','删除后已被推送的手机将接受不到该条推送信息，确认删除?',function(data){  
                           if(data){
                            $.ajax({ 
                            type: "post",  
                            url: "servlet/PhServlet?method=DeletePushItem", 
                            data: {pid:selectedRow.pid},  
                            dataType: "json",  
                            success: function(data) {  if(data!=null)    {alert("删除成功！"); $('#pushlist').datagrid('reload'); }  else   alert("删除失败");  $('#pushlist').datagrid('reload');  },
                            error:function(data) {   alert("删除失败");  $('#pushlist').datagrid('reload');  }
                            });   
                          }
                         });  
			            }}	 
            },'-']
			});
	});
	function CloseAddPushTab()
	{
	  $('#tt').tabs('close','<span>添加推送项</span>');
	  $('#pushlist').datagrid('reload');
	}
	function SeePushDetail(phid,phtitle)
	{ 
	 $.ajax({ 
      type: "post",  
      url: "servlet/PhServlet?method=GetCurUserType", 
      dataType: "json",  
      data:{none:"none"},
      success: function(data) { 
         if(data!=null)    {
		     var thePhDTL='<span>推送详情</span><input type=hidden value='+phid+'/>';
		     var url="${pageContext.request.contextPath }/pages/push/PushDetail.jsp?role='"+encodeURI(data.role)+"'&ptlid="+phid+"&ptltitle="+encodeURI(phtitle);
		     var tabs = $('#tt').tabs('tabs');      
		     for(var j=0;j<tabs.length;j++){  
		         var x = tabs[j].panel('options').title;               
		         if(x==thePhDTL) { $('#tt').tabs('select',thePhDTL);return;}
		         if(x.match(/^<span>推送详情<\/span><input type=hidden value=/g)!=null) {
		           $('#tt').tabs('select',x);
		           var y = $('#tt').tabs('getSelected');
		           $("#tt").tabs('update',{tab:y,options:{title:thePhDTL,href:url}});
		           //y.panel('refresh');
		           return;
		         }
		     }  
	         $('#tt').tabs('add', {title :thePhDTL, href :url,closable : true });  
         }  else   alert("查看失败"); 
      },
      error:function(data) {   alert("查看失败");  }
     }); 	 	                
	}
    function CloseUpdatePushTab()
	{
	  $('#tt').tabs('close','<span>修改推送项</span><input type=hidden value='+document.getElementById("ppid").innerHTML+'/>');
	  $('#pushlist').datagrid('reload');  
	}
  </script>
</body>
</html>
