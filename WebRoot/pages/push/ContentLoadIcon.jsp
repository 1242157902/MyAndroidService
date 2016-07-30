<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ContentLoadImage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
 <input id='iconviewhehe'  type='hidden' value='<%=request.getParameter("url")%>'/>
 <table id="iconview" border="1" width="100%">
 </table>
 <script type="text/javascript">
  var iconviewtempstr="<tr><th width='20%'>图标</th><th width='80%'>链接地址</th></tr>";
  var iconviewdata= document.getElementById("iconviewhehe").value;
  var iconview1= iconviewdata.split(',');
  if(iconviewdata==null||iconviewdata=="undefined"|| iconviewdata=="") iconview1=[]; 
  for(i=0;i<iconview1.length;i++)  
  {
     var  iconview2= iconview1[i].split('*');
     iconviewtempstr=iconviewtempstr+"<tr><td><img src='images/icons/"+iconview2[0]+"' style='width:60;height:60' /></td><td><a href=\"javascript:\" onclick=\"window.open('http://"+iconview2[1]+"')\" style='font:12px;'>"+iconview2[1]+"</a></td></tr>";
  }
  if(iconviewtempstr=="<tr><th width='20%'>图标</th><th width='80%'>链接地址</th></tr>") iconviewtempstr="<tr><th>此图片没有附带图标!</th></tr>";
  $("#iconview").html(iconviewtempstr);
  </script>
  </body>
</html>
