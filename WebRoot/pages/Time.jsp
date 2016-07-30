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
   var intervaltime = document.getElementById("intervaltime").value;
	if(intervaltime=="") {
	    alert("请输入访问间隔时间");
       	return false;
	}
	if(!isNumber(intervaltime)){
	 alert("请输入正整数！");
	   return false;
	}
         return true;
}

function validate2() {
   var location_interval = document.getElementById("location_interval").value;
	if(location_interval=="") {
	    alert("请输入定位间隔时间");
       	return false;
	}
	if(!isNumber(location_interval)){
	 alert("请输入正整数！");
	   return false;
	}
         return true;
}



function isNumber(num) {
	 var reg = /^[0-9]*$/;
	 if(reg.test(num)) {
		 return true;
	 } else {
		 return false;
	 }
}

function generatepublicprivatekey(){
	
	
}




</script>
</head>
  
  <body style="text-align:center;background-color:#E0ECFF">
  <div style="margin:0px auto;border:2px solid #CCCCCC;width:600px;height:230px;background:#E4EFFA">
  <h1 align=center>更新访问间隔时间</h1>
 <form action="${pageContext.request.contextPath }/servlet/TimeServlet" method="post">
   
   <br/>
   
  <p align=center> 客户端访问服务器间隔时间:(单位：分钟)<input type="text" id="intervaltime" name="intervaltime" />
  </p>

   <input type="submit" value="提交"  onclick="return validate()"/>
   
 </form>
   </div>
   <div style="margin:0px auto;border:2px solid #CCCCCC;width:600px;height:230px;background:#E4EFFA">
   		 
   		 <h1 align=center>更新定位间隔时间</h1>
		 <form action="${pageContext.request.contextPath }/servlet/TimeServlet" method="post">
		 
		   <input type="hidden" name="flag" value="flag"/>
		   
		   <br/>
		   
		  <p align=center> 定位间隔时间:(单位：分钟)<input type="text" id="location_interval" name="location_interval" />
		  </p>
		
		
		   <input type="submit" value="提交"  onclick="return validate2()"/>
		   
		 </form>
   
   </div>
   
   <div style="margin:0px auto;border:2px solid #CCCCCC;width:600px;height:230px;background:#E4EFFA">
   		 
   		 <h1 align=center>生成服务器端公钥和私钥</h1>
   		 <form action="${pageContext.request.contextPath }/servlet/TimeServlet" method="post">
   		 	<input type="hidden" name="flag" value="flag_serverkeys"/>
		 	<input type="submit" value="生成"/>
   		 </form>
   </div>
   
  </body>
</html>
