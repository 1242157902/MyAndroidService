<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>AddContent</title>
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
 
  <form action="servlet/CtServlet?method=AddContent" method="post" enctype="multipart/form-data">    
  <table id="addcttable"  border="1" cellpadding="1"  bordercolor="blue"> 
       <tr>                             
           <td colspan=2 style="width:290px;height:30px;text-align:center;" > 添加内容信息   </td>               
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
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">信息描述</span></td>              
           <td style="width:220px;height:30px;text-align:center;" ><input id="info" type="text" name="info" style="width:200px;" /></td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">显示时长</span></td>              
           <td style="width:220px;height:30px;text-align:center;" ><input id="showtime" type="text" name="showtime" style="width:200px;" /></td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">有效日期</span></td>              
           <td style="width:220px;height:30px;text-align:center;" ><input id="enddate" name="enddate"  class="easyui-datetimebox" editable="false" style="width:200px;" /></td>              
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
//显示设备类型
	function xxxx(){
	 $.getJSON("servlet/QueryServlet?method=getAllPhoneType", function(data) {
        $("#devicetype").html("");//清空devicetype内容
          $("#devicetype").append("<option value=''>全部型号</option>");
        $.each(data.types, function(i, item) {
            $("#devicetype").append(
                    "<option value='"+item.type+"'>"+item.type+"</option>" 
                   );
        });
        });
	}
	</script>
  </body>
</html>
