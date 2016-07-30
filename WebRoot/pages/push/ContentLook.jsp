<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>LookContent</title>
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
    <script type="text/javascript">
	$(function(){
	document.getElementById("imgbox").src="${pageContext.request.contextPath}/images/pics/" +window.parent.document.getElementById("cnamex").innerHTML;
	document.getElementById("cid").value=window.parent.document.getElementById("ccid").innerHTML;
	document.getElementById("title").innerHTML=window.parent.document.getElementById("ctitle").innerHTML;
	document.getElementById("classify").innerHTML=window.parent.document.getElementById("classify").innerHTML;
	document.getElementById("sort").innerHTML=window.parent.document.getElementById("csort").innerHTML;
	document.getElementById("size").innerHTML=window.parent.document.getElementById("csize").innerHTML;
	document.getElementById("operator").innerHTML=window.parent.document.getElementById("coperator").innerHTML;
	document.getElementById("operdate").innerHTML=window.parent.document.getElementById("coperdate").innerHTML;
	document.getElementById("link").innerHTML=window.parent.document.getElementById("clink").innerHTML;
	});
	</script>
  </head>
  
  <body>
 
  <form enctype="multipart/form-data">    
  <table id="addcttable"  border=”1″ cellpadding=”1″  > 
       <tr>                             
           <td colspan=2 style="width:290px;height:30px;text-align:center;" >查看内容信息 <input id="cid" type="hidden" name="cid" />  </td>    
           <td rowspan=8 style="width:290px;text-align:center;" > <img id=imgbox  width="235px" height="400px" />  </td>            
       </tr>      
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">标题</span></td>              
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="title" ></span></td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">积分</span></td> 
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="sort" ></span></td>                           
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">类别</span></td> 
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="classify" ></span></td>                           
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">大小</span></td>    
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="size" ></span>字节</td>                     
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">链接</span></td> 
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="link" ></span></td>                 
       </tr> 
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">操作员</span></td>       
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="operator" ></span></td>                       
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">更新时间</span></td>      
           <td style="width:220px;height:30px;text-align:left;" >&nbsp;&nbsp;&nbsp;<span id="operdate" ></span></td>    
       </tr>          
       <tr>                             
           <td colspan=2 style="width:290px;height:30px;text-align:center;" >
           <input id=btn2 type="button" value="确定" onClick="ctclose()">
           </td>               
       </tr>                             
</table>  
</form>
    <script type="text/javascript">
	function ctclose()
	{ 
	 window.parent.document.getElementById("ccid").innerHTML=document.getElementById("cid").value;
	 window.parent.document.getElementById("closelookcttab").click();
	}
	</script>
</body>
</html>
