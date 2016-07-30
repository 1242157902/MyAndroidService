<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>添加用户</title>
    
    <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-easyui-1.2.6/jquery-1.7.2.min.js"></script>
	
	<script type="text/javascript">
	
	
	
	
	function validate() {

	var shopno = $("#shopno").val();
	if(isEmpty(shopno)) {
	    alert("请输入店号！");
	
		return false;
	}
	
	
  var empno = $("#empno").val();
	if(isEmpty(empno)) {
	  alert("请输入员工号！");
		return false;
	}
	
   if(!isNumber(shopno)){
	
	   alert("请输入数字！");
	   return false;
	}
   if(!isNumber(empno)){
	
	   alert("请输入数字！");
	   return false;
	}
	
 

	return true;
}
	
	
	
	
	
	
	function isEmpty(target) {
	if(target == null || target == undefined || target == "") {
		return true;
	} else {
		return false;
	}
}

function isNumber(num) {
	 var reg = /^[0-9]*$/;
	 if(reg.test(num)) {
		 return true;
	 } else {
		 return false;
	 }
}

	
	
	</script>
	
    
    
    
    
    
    
    
   
  </head>
  
  <body style="text-align:center;background-color:#E0ECFF">
  <br/>
  
  
  
  <div style="margin:0px auto;border:2px solid #CCCCCC;width:400px;height:200px;background:#E4EFFA">
  <h1 align=center>二维码生成器</h1>
    <form action="${pageContext.request.contextPath }/servlet/Erweima" method="post">
    
     
		
		&nbsp;&nbsp;&nbsp;&nbsp;店号（两位数字）：<input type="text" name="shopno" id="shopno" style="height:25px;"/><br/><br/>
		员工编号（两位数字）：<input type="text" name="empno" id="empno" style="height:25px;"/><br/><br/>
		<input type="submit" value="生成二维码" onclick="return validate()"/>
		
	</form>
	
	</div>
  </body>
</html>
