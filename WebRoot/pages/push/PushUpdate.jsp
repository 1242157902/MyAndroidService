
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>UpdatePushItem</title>
    
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
    
  <form action="servlet/PhServlet?method=UpdatePushItem" method="post">    
  <table id="updatephtable"  border="1" cellpadding="1" > 
       <tr>                             
           <td colspan=2 style="width:330px;height:30px;text-align:center;" >修改推送项 
            <input id="pid" name="pid" type="hidden" />
           </td>               
       </tr>      
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">推送描述</span></td>              
           <td style="width:260px;height:30px;text-align:left;padding-left:10px;" >
           <input id="title" type="text" name="title" style="width:260px;" /></td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">推送序列</span></td>              
           <td style="width:260px;height:30px;text-align:left;padding-left:10px;" >
           <input id="queid"  name="queid"  class="easyui-combobox"  editable="false"  /> 
           </td>              
       </tr>  
       <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">推送类型</span></td>           
           <td style="width:260px;height:30px;text-align:left;padding-left:10px;" >
           <select type="select" name="priori" id="priori" style="width:75px;">
		     <option value="0">普通</option>
		     <option value="1">特推</option>
		   </select>
          </td>              
       </tr>  
         <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">推送有效期</span></td>              
           <td style="width:260px;height:30px;text-align:left;padding-left:10px;" >
           <input id="penddate" name="enddate"  class="easyui-datetimebox" editable="false" style="width:180px;" /></td>              
        </tr> 
          <tr>                             
           <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">推送方式</span></td>              
           <td style="width:260px;height:30px;text-align:left;padding-left:10px;" >
           <select type="select" name="ptype" id="ptype" style="width:75px;" onchange="selectptype(this.value)">
		     <option value="0" selected="selected">即时推送</option>
		     <option value="1">定时推送</option>
		   </select>
		   <span id=pushtimebox>
           <input id="pushtime" name="pushtime"  class="easyui-datetimebox" editable="false" style="width:175px;"/> 
           </span></td>                           
       </tr> 
       <tr>                             
           <td colspan=2 style="width:330px;height:30px;text-align:center;" >
           <input id=btn1 type="submit" value="保存"  onClick="return phsave()">&nbsp;&nbsp;&nbsp;&nbsp;	
           <input id=btn2 type="button" value="取消" onClick="phclose()">
           </td>               
       </tr>                             
</table>  
</form>
    <script type="text/javascript">
      $(function(){
        document.getElementById("pid").value=window.parent.document.getElementById("ppid").innerHTML;	
        document.getElementById("title").value=window.parent.document.getElementById("ptitle").innerHTML;        
        $("#priori").val(window.parent.document.getElementById("ppriori").innerHTML);
        
        var ppushtime=window.parent.document.getElementById("ppushtime").innerHTML;
        $("#pushtime").datetimebox("setValue",ppushtime);
        var penddate=window.parent. document.getElementById("penddate").innerHTML;
        $("#penddate").datetimebox("setValue",penddate);
          
        var pptype=window.parent.document.getElementById("pptype").innerHTML;
        if(pptype==0) {$("#pushtimebox").hide();}
        else $("#ptype").val('1');
        
        var pqueid=window.parent.document.getElementById("pqueid").innerHTML;
        $('#queid').combobox({   
          url:'servlet/PhServlet?method=GetPushQueTitleList',   
          valueField:'qid',   
          textField:'title',
          onLoadSuccess: function () { 
            var data = $('#queid').combobox('getData');
               if (data.length > 0) {
                 $('#queid').combobox('setValue', pqueid);
               } 
           }
        }); 
        
      }); 
	  function phclose()
	  { 
	    window.parent.document.getElementById("ppid").innerHTML=document.getElementById("pid").value;
	    window.parent.document.getElementById("CloseUpdatePushTabBtn").click();
	  }
      function phsave()
	  { 
	    var vqueid= $("#queid").combobox('getText');
	    var vpenddate=$("#penddate").datetimebox('getValue');
	    var vpushtime=$("#pushtime").datetimebox('getValue');
	    if( $("#title").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("推送描述不能为空!");return false;}
	    if( vqueid.replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("推送序列不能为空!");return false;}
	    if( $("#priori").val()=='')  {alert("优先级不能为空!");return false;}
	    if( vpenddate.replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("推送有效期不能为空!");return false;}
	    if( $("#ptype").val()=='')  {alert("推送方式不能为空!");return false;}
	    if( ($("#ptype").val()=='1')&&(vpushtime.replace(/(^\s*)|(\s*$)/g, "")==''))  {alert("定时推送的日期不能为空!");return false;}
	    alert((new Date(vpenddate))); return false;
	    if(new Date(vpenddate)<=new Date())  {alert("有效截至时间应大于系统当前时间!");return false;}
	    return true;
	  }	
	  function selectptype(v)
	  {
	   if(v==0) {$("#pushtimebox").hide();}
       if(v==1) {$("#pushtimebox").show();}
	  }
	</script>
  </body>
</html>
		