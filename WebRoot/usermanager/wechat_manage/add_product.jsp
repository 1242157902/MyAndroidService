<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add_product.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script><style>
	#title{
		border:1px dashed #F00;
	width:100%;
	text-align:center;
	font-size:28px;
	height:50px;
	line-height:50px;
	}
	#content{
		text-align:center;
		margin-top:20px;
	}
	#content table{
		float:left;
		width:100%;
		font-size:20px;
		border:1px dashed #C48FA1;
	}
	#content select{
		width:120px;
	}
	#imgAdiv{
		width:100px;
		height:100px;
		border:1px dashed #C48FA1;
	}
	</style>
	<script>
	function previewImage(file)  
    {   
        var MAXWIDTH  = 100;  
        var MAXHEIGHT = 100;  
        check="格式错误";
        var div = document.getElementById('imgApane');   
        if (file.files && file.files[0])  
        {  
		   div.innerHTML = '<div id=imgAdiv ><img id=imgA></div>';  
		   var imga = document.getElementById('imgA'); 		
		   var imgadiv =document.getElementById('imgAdiv'); 	
	      imga.onload = function() { 
		       check="格式正确";
		       var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, imga.offsetWidth, imga.offsetHeight);  
		       picarea=imga.offsetWidth*imga.offsetHeight;
		       ratio=imga.offsetWidth/rect.width;
		       imga.width = rect.width;  
		       imga.height = rect.height; 
			   imgadiv.width = rect.width;  
		       imgadiv.height = rect.height; 
			   hehe();
			   getfsize();
		   };
		   var reader = new FileReader();  
		   reader.onload = function(evt){
			 imga.src = evt.target.result;   
		   };
           reader.readAsDataURL(file.files[0]);   
	    } 
	   else  //ie
	   { 
	    var sFilter='filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale,src="';  
	    file.select();  
	    var src = document.selection.createRange().text;  
	    div.innerHTML = '<img id="imgA" >';  
	    var imga = document.getElementById("imgA");
		try{ 
		     imga.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = src;
		     //imga.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").sizingMethod="image";
		     setTimeout( function(){
		    var rect = clacImgZoomParam(MAXWIDTH, MAXHEIGHT, imga.offsetWidth, imga.offsetHeight); 
             picarea=imga.offsetWidth*imga.offsetHeight;
             ratio=imga.offsetWidth/rect.width;
		     //status =('rect:'+rect.top+','+rect.left+','+rect.width+','+rect.height);  
		     div.innerHTML = "<div id=imgAdiv style='width:"+rect.width+"px;height:"+rect.height+"px;margin-top:"+rect.top+"px;margin-left:"+rect.left+"px;"+sFilter+src+"\"'></div>";  
		     
		     },100);
		 }
		 catch(err) {
			 check="格式错误";}
	   }  
	} 
	 function clacImgZoomParam( maxWidth, maxHeight, width, height ){  
    var param = {top:0, left:0, width:width, height:height};  
    if( width>maxWidth || height>maxHeight )  
    {  
        rateWidth = width / maxWidth;  
        rateHeight = height / maxHeight;  
          
        if( rateWidth > rateHeight )  
        {  
            param.width =  maxWidth;  
            param.height = Math.round(height / rateWidth);  
            
        }else  
        {  
            param.width = Math.round(width / rateHeight);  
            param.height = maxHeight;  
        }
    }        
    param.left = Math.round((maxWidth - param.width) / 2);  
    param.top = Math.round((maxHeight - param.height) / 2);  
    return param;  
}  
	</script>
  </head>
  
  <body>
    <div id="title">
    商品添加
    </div>
    <div id="content">
    <!-- 类型选择 -->
    <form action="${pageContext.request.contextPath}/servlet/AddProductServlet?method=addProduct" method="post" enctype="multipart/form-data">
    	<table>
	    	<tr>
		    	<td>
		   			 商品类型：
		   			 <select id="type" name="type"  class="easyui-combobox" >
		   			 	<option value="0" selected="selected">--请选择--</option>
			    		<option value="1" >流量</option>
			    		<option value="2" >话费</option>
		    		</select>
		    	</td>
		    	<td>
		    		运营商：
		    		<select id="operator" name="operator"  class="easyui-combobox" >
		    			<option value="0" selected="selected">--请选择--</option>
		    			<option value="10010">中国联通</option>
		    			<option value="10086">中国移动</option>
		    			<option value="10000">中国电信</option>
		    		</select>
		    	</td>
		    	<td>
		    		流&nbsp;&nbsp;量：
		    		<input id="weight" name="weight" type="text" class="easyui-numberbox"/>
		    	</td>
		    	
		    	
	    	</tr>
	    	<tr >
	    		<td>
		    		名&nbsp;&nbsp;&nbsp;&nbsp;称：
		    		<input id="name" name="name" type="text" class="textbox"/>
		    	</td>
		    	<td>
		    		积&nbsp;&nbsp;分：
		    		<input id="price" name="price" type="text" class="easyui-numberbox"/>
		    	</td>
		    	<td>
		    		库存数量：
		    		<input id="nums" name="nums" type="text" class="easyui-numberbox" value="100"/>
		    	</td>
		    	
	    		
	    	</tr>
	    	<tr>
	    	<td colspan=1.5>备&nbsp;&nbsp;&nbsp;注：<br>
	    	
	    		<textarea name="memo" cols ="80" rows = "6"></textarea>
	    	</td>
	    	<td>图&nbsp;&nbsp;片:
		    		<input type="file" style="width:200px;" name="pic_path"  onchange="previewImage(this);"/>
		    		 <div id="imgApane"> <div id=imgAdiv><img id="imgA"/> </div> </div>  
		    </td>
	    	</tr>
	    	<tr align=middle>
	    	 	<td colspan=4><input type="submit" value="添加"/></td>
	    	</tr>
    	</table>
    	</form>
    </div>
  </body>
</html>
