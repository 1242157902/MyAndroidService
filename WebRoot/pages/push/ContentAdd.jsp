<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>AddContent</title>
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <link rel="stylesheet" type="text/css"	href="js/push/main.css" />
    <link rel="stylesheet" type="text/css"	href="js/push/demos.css" />
    <link rel="stylesheet" type="text/css"	href="js/push/jquery.Jcrop.css" />
    <style type="text/css">
    #imgBpane {
    left:100px;
    width: 100px;
    height: 170px;
    overflow: hidden;
    } 
    #imgA {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
    </style>
    <!--  <script type="text/javascript"	src="js/push/jquery.min.js"></script> -->
    <script type="text/javascript"	src="js/push/jquery.Jcrop.js"></script>
    <script type="text/javascript"	src="js/push/hehe.js"></script>
    <script type="text/javascript" src="js/jscolor-1.4.4/jscolor/jscolor.js"></script>
    <script type="text/javascript">
    var maxsize=1024*1024;//100kb     
    var check="格式错误";
    var picarea;
    var picsize;
    var ratio;
    function previewImage(file)  
    {  
        picarea=0;
        picsize=0;
        ratio=1;
        var MAXWIDTH  = 600;  
        var MAXHEIGHT = 400;  
        check="格式错误";
        var div = document.getElementById('imgApane');  
        var ppdiv= document.getElementById('imgBpane');  
        if (file.files && file.files[0])  
        {  
		   div.innerHTML = '<div id=imgAdiv ><img id=imgA></div>';  
		   ppdiv.innerHTML = '<div id=imgBdiv ><img id=imgB></div>';  
		   var imga = document.getElementById('imgA'); 	
		   var imgb = document.getElementById('imgB'); 	
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
			 imgb.src = evt.target.result;  
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
		     ppdiv.innerHTML = "<div id=imgBdiv style='width:"+rect.width+"px;height:"+rect.height+"px;"+sFilter+src+"\"'></div>";  
		     hehe();
		     check="格式正确";
		     getfsize();
		     },100);
		 }
		 catch(err) {
			 check="格式错误";
			 ppdiv.innerHTML = "<div id=imgBdiv style='width:0px;height:0px;'></div>";  	    
	     }
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
  <iframe id="xheheframe" name="xheheframe" style="display:none"></iframe>
  <form id=contentform action="servlet/CtServlet?method=AddContent" method="post" enctype="multipart/form-data">   
    <input type="hidden" id="imgx"  name="imgx" />  
    <input type="hidden" id="imgy"  name="imgy" />  
    <input type="hidden" id="imgw"  name="imgw" />  
    <input type="hidden" id="imgh"  name="imgh" /> 
    <input type="hidden" id="iconstr"  name="iconstr" /> 
  <table id="addcttable"  border="1" cellpadding="1"  bordercolor="gray"> 
       <tr>                             
           <td colspan=3 style="text-align:center;" > 添加内容信息   </td>   
           <td rowspan=8 style="width:600px;height:400px;text-align:center;" >
             <div id="imgApane"> <div id=imgAdiv><img id="imgA" onload="hehe()" /> </div> </div>  
           </td>                
       </tr>      
       <tr>                             
           <td style="width:60px;text-align:center;" ><span style="font-size:12px;">标题</span></td>              
           <td style="text-align:center;" ><input id="title" type="text" name="title" style="width:200px;" /></td>              
           <td rowspan=3 style="width:100px;text-align:center;" > 
             <div id="imgBpane"> <div id="imgBdiv"><img id=imgB ></div> </div>
           </td>  
       </tr>  
       <tr>                             
           <td style="text-align:center;" ><span style="font-size:12px;">积分值</span></td>              
           <td style="text-align:center;" ><input id="sort" type="text" name="sort" style="width:200px;" /></td>              
       </tr>  
      <tr>                             
           <td style="text-align:center;" ><span style="font-size:12px;">字体颜色</span></td>              
           <td style="text-align:center;" >
           <input class="color"  id="scorecolor" name="scolor" />
           </td>              
       </tr>  
       <tr>
       <td style="text-align:center;" ><span style="font-size:12px;">分类</span></td> 
       <td style="text-align:left;padding-left:2px;font-size:12px;"  colspan=2>
        <input name="classify" class="easyui-combotree" data-options="url:'servlet/JsonServlet?method=GetContentClassList',required:true,missingMessage:'不能为空!'"  style="width:270px;height:30px;cursor:pointer">
       </td>
       </tr>
       <tr>                             
           <td style="text-align:center;" ><span style="font-size:12px;">链接</span></td>           
           <td style="text-align:left;padding-left:2px;font-size:12px;"  colspan=2>http://<input id="link" type="text" name="link" style="width:270px;" /></td>              
       </tr>  
         <tr>                             
           <td style="text-align:center;" ><span style="font-size:12px;">上传附件</span></td>              
           <td style="text-align:center;" colspan=2><input id="namex" type="file" name="namex" style="width:300px;"  onchange="previewImage(this);" /> </td>              
       </tr> 
       <tr>                             
           <td colspan=3 style="text-align:center;" >
           <input id=btn1 type="submit" value="保存"  onClick="return ctsave()">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
           <input id=btn2 type="button" value="取消" onClick="ctclose()">
           <input id="SaveNewIconcBtn" type="hidden"  onClick="SaveNewIconc()" />  
           </td>               
       </tr>     
       <tr>                             
           <td style="text-align:center;" >
           <span style="padding-bottom:3px;display:block;"><a id="add_addicon"  class="easyui-linkbutton"   onclick="AddIconc()" >添加图标</a></span>
           <a id="add_delicon" class="easyui-linkbutton"  onclick="DelIconc()" >删除图标</a>
           </td>              
           <td  colspan=3 style="text-align:left;">
            <ul id="iconViewList" style="list-style:none;width:920px;height:65px;line-height:60px;overflow:auto;margin:0;padding-top:8px;">
			</ul>
		  </td>              
       </tr>                              
</table>  
</form>
    <script type="text/javascript">
     function getfsize(){//alert($("#namex").val());
     if(check=="格式正确") {
        $.ajaxFileUpload({
			url:'servlet/CtServlet?method=GetFileSize',
			fileElementId : 'namex',
			dataType : 'json',
			//data : {xx: $("#namex").val()},
			success : function(data, status) {
			picsize=data.fsize;//alert(picsize);
			},
			error : function(data, status, e) {
			}
		});}
     }
     
	function ctclose()
	{ 
	  window.parent.document.getElementById("closeaddcttab").click();
	}
    function ctsave()
	{
	  if( $("#title").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("标题不能为空!");return false;}
	  if( $("#sort").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("积分值不能为空!");return false;}
	  var  r=/^[0-9]*[1-9][0-9]*$/; //正整数  
	  var score=$("#sort").val().replace(/(^\s*)|(\s*$)/g, "");
	  if(!r.test(score))  {alert("积分值为正整数,请输入正确格式的积分值!");return false;}	  
	  else $("#sort").attr("value",score);
	  
	  if( $("#namex").val()=='')  {alert("您还没有上传图片");return false;}
	  if(check!="格式错误")   checksize();
	  if(check!="格式正确")   {alert(check);return false;}
	
      var actualx=$('#imgx').val()*ratio; 
      var actualy=$('#imgy').val()*ratio; 
	  var actualw=$('#imgw').val()*ratio; 
	  var actualh=$('#imgh').val()*ratio; 
      
      $('#imgx').val(Math.round(actualx));   
      $('#imgy').val(Math.round(actualy));   
      $('#imgw').val(Math.round(actualw));   
      $('#imgh').val(Math.round(actualh));   
    // alert("x="+$('#imgx').val()+",y="+$('#imgy').val()+",w="+$('#imgw').val()+",h="+$('#imgh').val());
	  if( $("#link").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {$("#link").val("m.baidu.com");}
	  $("#link").val('http://'+$("#link").val());	
	  
	  var tempstr="";
	  $("#iconViewList li img").each(function(){ tempstr=tempstr+this.title+",";});
	  $("#iconstr").val(tempstr);
	  
	  jQuery("#contentform").attr('action','servlet/CtServlet?method=AddContent');
	  jQuery("#contentform").attr('target', '');  
	  return true;
	}	
  function hehe() {
    var boundx=0,  boundy=0,  $pimg;
    if (navigator.userAgent.indexOf('Firefox') >= 0||navigator.userAgent.indexOf("Safari")>0) $pimg = $('#imgB');
	else  $pimg = $('#imgBdiv');
    $('#imgAdiv').Jcrop({
      onChange: updatePreview,
      onSelect: updatePreview,
      aspectRatio: 100 /170
    },function(){
      var bounds = this.getBounds();
      boundx = bounds[0];
      boundy = bounds[1];
      var x=boundx;
      var y=boundy;
      if(x*1.7<y) y=x*1.7;
      else x=y/1.7;
      $pimg.css({
          width: Math.round(100*boundx/x) + 'px',
          height:Math.round(170*boundy/y) + 'px',
          marginLeft: '0px',
          marginTop: '0px'
        });
	  $('#imgw').val(x); //c.h 裁剪区域的高  
	  $('#imgh').val(y); //c.h 裁剪区域的高   
      $('#imgx').val(0);  //c.x 裁剪区域左上角顶点相对于图片左上角顶点的x坐标   
      $('#imgy').val(0);  //c.y 裁剪区
    });
	
    function updatePreview(c)
    {
      if (parseInt(c.w) > 0)
      {
        var rx = 100 / c.w;
        var ry = 170 / c.h;

        $pimg.css({
          width: Math.round(rx * boundx) + 'px',
          height: Math.round(ry * boundy) + 'px',
          marginLeft: '-' + Math.round(rx * c.x) + 'px',
          marginTop: '-' + Math.round(ry * c.y) + 'px'
        });
		$('#imgw').val(c.w); //c.h 裁剪区域的高  
		$('#imgh').val(c.h); //c.h 裁剪区域的高   
        $('#imgx').val(c.x);  //c.x 裁剪区域左上角顶点相对于图片左上角顶点的x坐标   
        $('#imgy').val(c.y);  //c.y 裁剪区
      }
    };
}
function checksize()
{
  var virtualw=$('#imgw').val()*ratio;
  var virtualh=$('#imgh').val()*ratio;
  var virtualarea=virtualw*virtualh;
  if((virtualarea/picarea)*picsize>maxsize)  check="截取图片的大小不能超过100KB,请重新截图!";
  else check="格式正确";
  //alert("x="+$('#imgx').val()+",y="+$('#imgy').val()+",w="+$('#imgw').val()+",h="+$('#imgh').val());
}
</script>
  
	<script type="text/javascript">
	function DelIconc()
	{
	 var list=document.getElementById("iconViewList");
	 var id=list.getElementsByTagName("li").length-1;
	 list.removeChild(list.children[id]);
	}
	function AddIconc()
	{  
	 window.parent.document.getElementById("AddNewIconcBtn").click();
	}
	
	function SaveNewIconc() {
	  var node=document.createElement("li");
	  node.style.cssText = "float:left;height:60px;";
	  node.innerHTML="<img src='images/icons/"+window.parent.document.getElementById("aiconcnamex").innerHTML+"' title='"+window.parent.document.getElementById("aiconcid").innerHTML+"' alt='加载错误' width=60 height=60 />";
	  document.getElementById("iconViewList").appendChild(node);
	 }
	</script>
  </body>
</html>
