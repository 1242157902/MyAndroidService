<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">
<title>mobileinfo</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/push/pushindex.js"></script>
<script type="text/javascript" src="js/push/pushencode.js"></script>
<script type="text/javascript" src="js/push/pushfunc.js"></script>



</head>
<body>
    <div id="apushlogdlg" class="easyui-dialog" style="width:500px;height:400px;"  closed="true" modal="true"  >     
     <table id="apushloglist"></table>
    </div>
	<div id="mbinfo_lay" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:150,title:'查询条件'">
			<form id="mbinfo_form" method="post">
				<div style="padding-top:5px;padding-bottom:5px;">
					手机号码<input id="mbinfo_no" name="mbinfo_no" type=text
						style="width:140px;height:22px;" class="textbox" value="" /> 
						设备号<input id="device_imei" name="device_imei" type=text
						style="width:140px;height:22px;" class="textbox" value="" /> 
						 性 别<select
						id="mbinfo_sex" name="mbinfo_sex" class="easyui-combotree"
						style="width:100px;"
						data-options="url:'pages/push/data/gender.json',multiple:true,panelHeight:75"></select>
					年 龄<select id="mbinfo_age" name="mbinfo_age"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'pages/push/data/age.json',multiple:true,panelHeight:170"></select>
					所属单位<select id="mbinfo_company" name="mbinfo_company"></select> 销售商<select
						id="mbinfo_seller" name="mbinfo_seller"></select>
				</div>
				<div style="padding-top:5px;padding-bottom:5px;">
					手机品牌<select id="mbinfo_brand" name="mbinfo_brand"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetBrandList',multiple:true,panelHeight:200"></select>
					运营商<select id="mbinfo_provi" name="mbinfo_provi"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'pages/push/data/provider.json',multiple:true,panelHeight:100"></select>
					归属地<select id="mbinfo_area" name="mbinfo_area"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetAreaList',multiple:true,panelHeight:200"></select>
					操作系统<select id="mbinfo_os" name="mbinfo_os"
						class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetMbOsList',multiple:true,panelHeight:200"></select>
				</div>
				<div style="padding-top:5px;padding-bottom:5px;">
					注册时间<input id="mbinfo_regstart" name="mbinfo_regstart"
						class="easyui-datetimebox" editable="false" style="width:180px;" />
					到 <input id="mbinfo_regend" name="mbinfo_regend"
						class="easyui-datetimebox" editable="false" style="width:180px;" />
						
					
					
					
					 手机获取服务器端公钥<select
						id="mbinfo_serverkeys" name="mbinfo_serverkeys" class="easyui-combotree"
						style="width:100px;"
						data-options="url:'js/json/serverpublickey.json',multiple:true,panelHeight:75"></select>
						
						
					<span style="padding-left:220px;"></span><a id="mbinfo_srhbtn"
						class="easyui-linkbutton" icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a><span
						style="padding-left:50px;"></span><a id="mbinfo_clrbtn"
						class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
				</div>
			</form>
		</div>
		
		
		
		<div data-options="region:'center'" style="height:100%;">
			<table id="mbinfo_list"></table>
		</div>
		
		
		
		
	</div>
	
   <script type="text/javascript" src="js/phonelist.js"></script>
   
   <script type="text/javascript">	
 	function SeeLogDetail(imei,mbno)
	{
	     if(mbno=='')  mbno="null";
		 $.ajax({ 
		  type: "post",  
		  url: "servlet/PhServlet?method=GetCurUserType", 
		  dataType: "json",  
		  data:{none:"none"},
		  success: function(data) { 
		     if(data!=null)    {
		     var theLogDTL='<span>设备日志集合</span><input type=hidden value='+imei+'/>';
	         var url="${pageContext.request.contextPath }/pages/log/LogDetail.jsp?role='"+encodeURI(data.role)+"'&imei="+imei+"&mbno="+encodeURI(mbno);
	         var tabs = $('#tt').tabs('tabs');      
		     for(var j=0;j<tabs.length;j++){  
		         var x = tabs[j].panel('options').title;               
		         if(x==theLogDTL) { $('#tt').tabs('select',theLogDTL);return;}
		         if(x.match(/^<span>设备日志集合<\/span><input type=hidden value=/g)!=null) {
		           $('#tt').tabs('select',x);
		           var y = $('#tt').tabs('getSelected');
		           $("#tt").tabs('update',{tab:y,options:{title:theLogDTL,href:url}});
		           //y.panel('refresh');
		           return;
		         }
		     }  
		     $('#tt').tabs('add', {title :theLogDTL, href :url,closable : true });  
		     } 
		     else   alert("查看失败"); 
		  },
		  error: function(data) {   alert("查看失败");  }
		 }); 	 	                
	}
   </script>
</body>
</html>
