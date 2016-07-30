<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
<title>拖动</title>
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<style> 
.item_content ul  {
	list-style:none;
}
.item_content ul li {
	width:420px;
	height:180px;
	float:left;
	margin:5px;
}
.item_content {
	width:500px;
}
 
.item_content .item {
	width:420px;
	height:180px;
	line-height:180px;
	text-align:left;
	cursor:pointer;
	background:#C7EDCC;  
}
.item_content .item img {
    width:138px;
	height:178px;
    border:1px solid;
	border-radius:6px;
}
</style> 
<script type="text/javascript">
	$(function(){  
	var queinfo=window.parent.document.getElementById("queue").innerHTML;
	var atom=queinfo.split(",");
	for(var i in atom){	
	  var ainfo=atom[i].split("*");
	  var s=document.getElementById("queque");   
	  var li= document.createElement("li");  
	  ainfo[3]=ainfo[3].replace(/\#/g," ");
	  var inhtm='<div class=item><table><tr><td rowspan=3><img  src=images/pics/'+ainfo[4]+' /></td><td style="height:30px">显示时长:'+ainfo[2]+'秒</td></tr>';
	  inhtm+='<tr><td style="height:30px">有效日期:'+ainfo[3]+'</td></tr><tr><td style="height:70px"></td></tr></table></div>';
	  li.innerHTML=inhtm;
	  s.appendChild(li); 
	}
	document.getElementById("qid").value=window.parent.document.getElementById("qqid").innerHTML;	
	document.getElementById("title").innerHTML=window.parent.document.getElementById("qtitle").innerHTML;	
	});
</script>
</head>
 
<body>
 <span id=choice style="display:none"></span>
 <table id="updatequetable"  border="1" cellpadding="1"> 
        <tr>   
           <td colspan=3 style="width:500px;height:30px;text-align:center;">查看序列信息 </td>  
         </tr>
         <tr>
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">描述信息</span></td>              
           <td style="width:330px;height:30px;text-align:left;" ><span id="title" style="font-size:12px;width:330px"></span> </td>  
           <td style="width:100px;height:30px;text-align:center;" >   
            <input id=CancelLookQueBtn type="button" value="确定" onClick="CancelLookQue()" />
            <input id="qid" name="qid" type="hidden" />
           </td>   
       </tr>  
        <tr>                             
           <td colspan=3 style="width:500px;text-align:center;" >
           <div class="item_container">
		    <div class="item_content">
			 <ul id="queque">
			</ul>
		   </div>
	       </div>
          </td>               
        </tr>                              
</table>  
<script type="text/javascript">	
   function CancelLookQue()
   { 
      window.parent.document.getElementById("qqid").innerHTML=document.getElementById("qid").value;
      window.parent.document.getElementById("qLookAtomCloseTabBtn").click();
   }  
</script>  
</body>
</html>

