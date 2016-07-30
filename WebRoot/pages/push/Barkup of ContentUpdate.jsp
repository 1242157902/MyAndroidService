<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>UpdateContent</title>
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
    <script type="text/javascript">
	$(function(){
	document.getElementById("cid").value=window.parent.document.getElementById("ccid").innerHTML;	
	document.getElementById("updatectform").action="servlet/CtServlet?method=UpdateContent&filenamex="+window.parent.document.getElementById("cnamex").innerHTML;
	document.getElementById("title").value=window.parent.document.getElementById("ctitle").innerHTML;
	document.getElementById("sort").value=window.parent.document.getElementById("csort").innerHTML;
	document.getElementById("link").value=window.parent.document.getElementById("clink").innerHTML.substring(7);
	});
	</script>
  </head>
  
  <body>
 
  <form id="updatectform" method="post" enctype="multipart/form-data">    
  <table id="addcttable"  border=”1″ cellpadding=”1″  bordercolor=”blue″> 
       <tr>                             
           <td colspan=2 style="width:290px;height:30px;text-align:center;" > 修改内容信息 <input id="cid" type="hidden" name="cid" /></td>            
       </tr>      
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">标题</span></td>              
           <td style="width:220px;height:30px;text-align:center;" ><input id="title" type="text" name="title" style="width:200px;" /></td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">类别</span></td>              
           <td style="width:220px;height:30px;text-align:center;" ><input id="sort" type="text" name="sort" style="width:200px;" /></td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">链接</span></td>           
           <td style="width:220px;height:30px;text-align:center;" >http://<input id="link" type="text" name="link" style="width:170px;" /></td>              
       </tr>  
         <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">上传附件</span></td>              
           <td style="width:220px;height:30px;text-align:center;" ><input id="namex" type="file" name="namex" style="width:200px;" /></td>              
       </tr> 
       <tr>                             
           <td colspan=2 style="width:290px;height:30px;text-align:center;" >
           <input id=btn1 type="submit" value="保存"  onClick="return ctsave()">&nbsp; 	&nbsp;	&nbsp;	&nbsp;	&nbsp;	
           <input id=btn2 type="button" value="取消" onClick="ctclose()">
           </td>               
       </tr>                             
</table>  
</form>
    <script type="text/javascript">
	function ctclose()
	{  
	 window.parent.document.getElementById("ccid").innerHTML=document.getElementById("cid").value;
	 window.parent.document.getElementById("closeupdatecttab").click();
	}
    function ctsave()
	{ 
	  if( $("#title").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("标题不能为空!");return false;}
	  if( $("#sort").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("类别不能为空!");return false;}
	  if( $("#link").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {$("#link").val("m.baidu.com");}
	  $("#link").val('http://'+$("#link").val());
	  return true;
	}	
	</script>
  </body>
</html>
