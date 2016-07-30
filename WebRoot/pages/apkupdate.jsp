<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'Time.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<script type="text/javascript">

	function validate() {

	var myname=document.getElementById("name").value;
	var version=document.getElementById("version").value;
      if(myname==""){
		alert("请输入apk名称！");
		return false;
	}
	if(version==""){
		alert("请输入apk版本号！");
		return false;
     }
     return true;
   }
	
	
	</script>
	
	
	

  </head>
  
  <body style="text-align:center;background-color:#E0ECFF">
  <div style="margin:0px auto;border:2px solid #CCCCCC;width:600px;height:230px;background:#E4EFFA">
  <h1 align=center>更新Apk</h1>
   <form action="${pageContext.request.contextPath }/servlet/PhoneServlet?method=updateapk" method="post" enctype="multipart/form-data">
   
   <p align=center>&nbsp;&nbsp;apk名字:<input type="text" id="name" name="name" /></p>

   <p align=center>apk版本号:<input type="text" id="version" name="version" /></p>
   <p align=center>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;apk:<input type="file" id="inputfile" name="inputfile"/> </p>
  
  <input type="submit" value="提交"  onclick="return validate()"/>
   
   
  
   
   </form>
   
   </div>
   
  </body>
</html>
