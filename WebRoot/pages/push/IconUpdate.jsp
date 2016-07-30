<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<title>AddIcon</title>
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
<link rel="stylesheet" type="text/css" href="js/push/main.css" />
<link rel="stylesheet" type="text/css" href="js/push/demos.css" />
<link rel="stylesheet" type="text/css" href="js/push/jquery.Jcrop.css" />

<script type="text/javascript">
    
    window.onload=function(){
       
    	   var ccid=window.parent.document.getElementById("ccid").innerHTML;
        	 
    	   document.getElementById("id").value=ccid;
        	 
        	 
        	  var ctitle=window.parent.document.getElementById("ctitle").innerHTML;
        	 
        	 document.getElementById("title").value=ctitle;
        	 
              var clink=window.parent.document.getElementById("clink").innerHTML;
        	 
        	 document.getElementById("picurl").value=clink;
        	 
        	 
        	 
      var cnamex=window.parent.document.getElementById("cnamex").innerHTML;
        	 
        	 document.getElementById("iconname").value=cnamex;
    
    
    };
    
    function ctclose()
	{ 
	  window.parent.document.getElementById("mycloseupdatecttab").click();
	}
    
    
    var fileSize=0;
    var filetype;
    
    var file;
    function checkfile(file){
    
    
    file = document.getElementById('myfile').files[0];

 if (file) {

              

                //if (file.size > 1024 * 1024)

                   // fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';

               // else

                    fileSize = (Math.round(file.size * 100 / 1024) / 100).toString();
   
                
                  
                    filetype=file.type;
                

               }
               
               if(fileSize>50)alert("图标必须小于50k！");
               
               if(filetype!='image/png')alert("图标类型不对！");
               
               
               
    
    
    
    }
    
    function voliate(){
    
    var title=document.getElementById('title').value;
    
    if(title==""){
    
    alert('请输入标题！');
    return false;
    
    }
    
    
    var picurl=document.getElementById('picurl').value;
    
    
    
    if(picurl==""){
    
    alert('请输入网址！');
    return false;
    
    }
    
    if(fileSize==0){
        
       
        return true;
        
        };
    
    
    
    if(filetype=='image/png'&&fileSize<50){
    
     return true;
    
    }else{
   
     if(fileSize>50){
    	 alert("图标必须小于50k！");
    	 
    	 return false;
    	 
     
     };
               
       if(filetype!='image/png')
    	   
    	   {
    	   
    	   alert("图标类型不对！");
    
    
             return false;
     
    	   }
    
    }
    
    
    return true;
    
   }
    
    
    
    
    
    
    
    
    
    </script>
</head>

<body>

	<form id=contentform action="servlet/IconServlet?method=IconUpdate"
		method="post" enctype="multipart/form-data">

		<input type="hidden" id="id" name="id" />
		 <input type="hidden"
			id="iconname" name="iconname" />

		<table id="addcttable" border="1" cellpadding="1" bordercolor="gray">
			<tr>
				<td colspan=2 style="text-align:center;">修改图标信息</td>

			</tr>
			<tr>
				<td style="width:60px;text-align:center;"><span
					style="font-size:12px;">标题</span>
				</td>
				<td style="text-align:left;"><input id="title" type="text"
					name="title" style="width:200px;" />
				</td>
			</tr>
	<tr>
				<td style="width:60px;text-align:center;"><span
					style="font-size:12px;">分类</span>
				</td>
				<td style="text-align:left;">
			    <input id="aicon_class" name="icon_class" class="easyui-combotree" data-options="url:'servlet/JsonServlet?method=GetContentClassList',required:true,missingMessage:'不能为空!',onLoadSuccess:function(){$('#aicon_class').combotree('setValue',window.parent.document.getElementById('icon_class').innerHTML);}"  style="width:200px;height:30px;cursor:pointer">
     			</td>
			</tr>
			<tr>
				<td style="text-align:center;"><span style="font-size:12px;">链接</span>
				</td>
				<td style="text-align:left;padding-left:2px;font-size:12px;">http://<input
					id="picurl" type="text" name="picurl" style="width:170px;" />
				</td>
			</tr>
			<tr>
				<td style="text-align:center;"><span style="font-size:12px;">上传附件</span>
				</td>
				<td style="text-align:center;" colspan=2><input id="myfile"
					type="file" name="myfile" style="width:300px;" onchange="checkfile(this)" />
					<p style="color:red;">图片大小必须小于50k,后缀名必须为.jpg</p></td>
			</tr>
			<tr>
				<td colspan=3 style="text-align:center;"><input id=btn1
					type="submit" value="保存" onClick="return voliate()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input id=btn2 type="button" value="取消" onClick="ctclose()">
				</td>
			</tr>
		</table>
	</form>

</body>
</html>
