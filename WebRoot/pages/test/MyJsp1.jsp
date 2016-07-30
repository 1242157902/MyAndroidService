
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
    
    <title>AddPushItem</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.2.6/themes/icon.css" />
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
    <script type="text/javascript"	src="js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>
</head>
  
<body>    
  <form action="servlet/PhServlet?method=AddPushItem" method="post">
        <div>
		<span>推送方式</span>
		<select type="select" name="pushway" id="pushtype" style="width:80px;" onchange="selectcheck(this.value)">
		     <option value="0">定时推送</option>
		     <option value="1">即时推送</option>
		</select> &nbsp; &nbsp; &nbsp; &nbsp;		
		<span id="psdate">推送时间<input id="startdate" name="startdate"  class="easyui-datetimebox" editable="false" style="width:160px;"  value=""/></span>	
	    </div>
	    <br/>  
	     
	    <div>
		<span>推送优先级</span>
		<input id="priori" name="priori"  type="text" style="width:65px;"  value=""/>	
		&nbsp; &nbsp; &nbsp; &nbsp;
	    <span>有效截止日期</span>
		<input id="enddate" name="enddate"  class="easyui-datetimebox" editable="false" style="width:160px;"  value=""/>	
		</div>		
	    <br/>
	    
	    <span>推送对象</span>
		<div id="accepterlay" class="easyui-layout" style="width: 100%;height:300px" >
	    <div region="center" >
		<table id="accepter"></table>
	    </div>
	    </div>
		<br/>		
		
		<span>推送信息</span>
		<div id="pushmsglay" class="easyui-layout" style="width: 100%;height:300px" >
	    <div region="center" >
		<table id="pushmsg"></table>
	    </div>
	    </div>
		<br/>
		
		 <input id=btn1 type="submit" value="保存"  onClick="return phsave()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
         <input id=btn2 type="button" value="取消" onClick="phclose()">		
  </form>  
  	<div id="clickpic" class="easyui-dialog" style="width: 280px;height:400px;padding:5px 5px;top:400px;" closed="true" buttons="#dlg-buttons">
		<iframe frameborder=0 id=hehe1 style=width:100%;height:100% ></iframe>
	</div>
	<div id="clicklink" class="easyui-dialog" style="width: 280px;height:400px;padding:5px 5px;top:400px;" closed="true" buttons="#dlg-buttons">
		<iframe frameborder=0 id=hehe2 style=width:100%;height:100% ></iframe>
	</div>
  <script type="text/javascript" src="js/push/pushfunc.js"></script>	 
  <script type="text/javascript">	
	 $(function(){
	  $('#accepter').datagrid({
			idField:'imsi',	
			title:'推送信息列表',
			fit:true,
			height:450 ,
			url:'servlet/PhServlet?method=GetAccepterList',
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 					
			columns:[[
			    {
					field:'ok' ,
					title:'编号' ,
					width:30 ,					
					height:50,
					checkbox: true
				},	
				{
					field:'imsi' ,
					title:'手机imsi号' ,
					width:150 ,					
					height:50
				},							
				{
					field:'num' ,
					title:'手机号码' ,
					width:150, 
					height:50
				},	
				{
					field:'name' ,
					title:'机主姓名',
					width:150, 
					height:50
				},				
				{
					field:'queue' ,
					title:'队列' ,
					width:150, 
					height:50,
					hidden: true
				},				
				{
					field:'status' ,
					title:'推送情况' ,
					width:150, 
					height:50,
					hidden: true
				},
				{
					field:'preque' ,
					title:'准队列' ,
					width:150, 
					height:50,
					hidden: true
				},
				{
					field:'mtype' ,
					title:'手机型号' ,
					width:150, 
					height:50
				    //hidden: true
				},
				{
					field:'brand' ,
					title:'手机品牌' ,
					width:150, 
					height:50
				    //hidden: true
				},
				{
					field:'count' ,
					title:'积分' ,
					width:150, 
					height:50
				    //hidden: true
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
			});	
			
		$('#pushmsg').datagrid({
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
			columns:[[	
				{
					field:'cid' ,
					title:'编号' ,
					width:100 ,
					height:50,
					hidden:true
				},	
				{
					field:'ok' ,
					title:'编号' ,
					width:30 ,					
					height:50,
					checkbox: true
				},	
				{
					field:'title' ,
					title:'标题' ,
					width:140, 
					height:50
				},				
				{
					field:'sort' ,
					title:'类别' ,
					width:100, 
					height:50
				},				
				{
					field:'info' ,
					title:'信息' ,
					width:190, 
					height:50
				},
				{
					field:'namex' ,
					title:'格式' ,
					width:100, 
					height:50,
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
					field:'showtime' ,
					title:'显示时长' ,
					width:100 ,
					height:50
				},
				{
					field:'enddate' ,
					title:'有效日期' ,
					width:150 ,
					height:50
				},
				{
					field:'storeurl',
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
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
			});
	});
	function phclose()
	{ 
	  window.parent.document.getElementById("closeaddphitemtab").click();
	}
    function phsave()
	{
	  if(document.getElementById("pushtype").value=='0'&& $("#startdate").datebox('getValue')=='')  {alert("您还没有选择推送时间!");return false;}
	  if( $("#priori").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("优先级不能为空!");return false;} var str=$("#priori").val();	  
	  if( str.match(/^[0-9]{1,2}$|^100$/)==null)   {alert("优先级应该为一个大于等于0且小于等于100的整数，值越小优先级越高!");return false;} 
	  if( $("#enddate").datebox('getValue')=='')  {alert("您还没有选择有效截止时间!");return false;} 
	  return true;
	}
	function selectcheck(v)
	{
	 if(v==1) {$("#psdate").hide();}
     if(v==0) {$("#psdate").show();}
	}
	function linkto(v){	
	 document.getElementById("hehe2").src=v; 
	 $('#clicklink').dialog('open').dialog('setTitle','查看链接');	 
	}
	function lookpic(v){	
	 document.getElementById("hehe1").src="${pageContext.request.contextPath}/images/pics/"+v; 
	 $('#clickpic').dialog('open').dialog('setTitle','查看图片');
	}
 </script>
</body>
</html>
		