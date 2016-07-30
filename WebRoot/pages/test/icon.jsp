<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>My JSP 'icon.jsp' starting page</title>
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
<table>
<tr>
<td style="height:80px;">
<span style="padding-bottom:3px;display:block;"><a id="add_addicon"  class="easyui-linkbutton"  icon="icon-add"   onclick="addFunction()" >&nbsp;添加图标&nbsp;</a></span>
<a id="add_delicon" class="easyui-linkbutton" icon="icon-remove"  onclick="myFunction()" >&nbsp;删除图标&nbsp;</a>
</td>
<td style="height:80px;" >
<input type="hidden" value="">
<ul id="myList" style="list-style:none;width:800px;height:80px;overflow:auto;">
<li style="float:left;padding-top:12px;"><img src="images/beijing.jpg"  alt="" width=60 height=60 /></li>
<li style="float:left;padding-top:12px;"><img src="images/beijing.jpg"  alt="" width=60 height=60 /></li>
<li style="float:left;padding-top:12px;"><img src="images/beijing.jpg"  alt="" width=60 height=60 /></li>
<li style="float:left;padding-top:12px;"><img src="images/beijing.jpg"  alt="" width=60 height=60 /></li>
</ul>
</td>

</tr>
</table>

<script>
function myFunction()
{
 var list=document.getElementById("myList");
 var id=list.getElementsByTagName("li").length-1;
 list.removeChild(list.children[id]);
}
function addFunction()
{
var node=document.createElement("lI");
node.style.cssText = "float:left;padding-top:12px;";
node.innerHTML="<img src='images/beijing.jpg'  alt='' width=60 height=60 />";
document.getElementById("myList").appendChild(node);
}
</script>
</body>
</html>