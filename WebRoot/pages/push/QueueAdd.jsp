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
	width:140px;
	height:180px;
	float:left;
	margin:5px;
}
.item_content {
	width:980px;
	height:385px;
	border:1px solid #ccc;
}
 
.item_content .item {
	width:140px;
	height:180px;
	line-height:180px;
	text-align:center;
	cursor:pointer;
	background:#ccc;
	
}
.item_content .item img {
    width:138px;
	height:178px;
	border-radius:6px;
}
.chosed {
border:2px solid orange;
}
.unchosed {
border:2px solid #ccc;
}
</style> 

</head>
 
<body>
  <span id=choice style="display:none"></span>
 <form action="servlet/QueServlet?method=AddQueue" method="post" > 
 <table id="addquetable"  border="1" cellpadding="1" > 
    <tr>                         
      <td colspan=4 style="width:980px;height:30px;text-align:center;" >添加序列信息   </td>               
    </tr> 
    <tr>   
      <td style="width:200px;height:30px;text-align:center;" >
           <input id=SaveAddQueBtn type="submit" value="保存" onClick="return SaveAddQue()" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;	
           <input id=CancelAddQueBtn type="button" value="取消" onClick="CancelAddQue()" />
           <input id=addqueinfo type="hidden"  name="que" /> 
      </td>                             
      <td style="width:70px;height:30px;text-align:center;" ><span style="font-size:12px;">描述信息</span></td>              
      <td style="width:200px;height:30px;text-align:left;" ><input id="title" type="text" name="title" style="width:200px;" /> </td>  
      <td style="width:510px;height:30px;text-align:center;" >   
           <input id=NewAtomBtn type="button" value="新增节点"  onClick="NewAtom()" />&nbsp;&nbsp;&nbsp;
           <input id=UpdateAtomBtn type="button"  value="修改节点"  onClick="UpdateAtom()" />&nbsp;&nbsp;&nbsp;
           <input id=DeleteAtomBtn type="button" value="删除节点"  onClick="DeleteAtom(this.name)" />  
           <input id=SaveNewAtomBtn type="hidden"  onClick="SaveNewAtom()" />  
           <input id=SaveUpdateAtomBtn type="hidden"  onClick="SaveUpdateAtom(this.value)" />   
      </td>      
    </tr>  
    <tr>                             
      <td colspan=4 style="width:980px;height:400px;text-align:center;" >
        <div class="item_container">
		<div class="item_content">
		   <ul id="queque"></ul>
		</div>
	    </div>
      </td>               
    </tr>                              
</table>  
</form> 
<script type="text/javascript">	
    function CancelAddQue()	{
	   window.parent.document.getElementById("qAddAtomCloseTabBtn").click();
	}
 	function NewAtom()	{ 
	   if(document.getElementById("queque").getElementsByTagName("li").length>11) {alert("您好，很抱歉，最多只能添加12个节点！");return;}
	   window.parent.document.getElementById("qAddNewAtomBtn").click();
	}
	function UpdateAtom()	{
	   if(document.getElementById("queque").getElementsByTagName("li").length==0) return;
	   var para= document.getElementById("choice").innerHTML.split("*");
       window.parent.document.getElementById("qcid").innerHTML=para[0];
	   window.parent.document.getElementById("qshowtime1").innerHTML=para[1];
	   window.parent.document.getElementById("qenddate1").innerHTML=para[2];
	   window.parent.document.getElementById("qAddUpdateAtomBtn").click();
	}
	function DeleteAtom(aid)	{ 
	      var id=aid.split("d");
	      var s=document.getElementById("queque");   
	      s.removeChild(s.children[id[1]]);
	      if(s.getElementsByTagName("li").length==0) return ;
	      else if(s.getElementsByTagName("li").length==id[1]){
	        var  newid=Number(id[1]-1);
	        choose(document.getElementById("ad"+newid));
	      }
	      else
	      {	    
	        var x=Number(id[1])+1;
	        var y=Number(s.getElementsByTagName("li").length+1); 
	        for(var i=x;i<y;i++)
	        {
	          document.getElementById("ad"+i).id="ad"+(i-1);
	        }
	        choose(document.getElementById(aid));
	      }
	}
	function choose(obj){ 
	   $("li img").each(function(){this.className="unchosed";});
	   obj.className="chosed";
	   document.getElementById("choice").innerHTML=obj.name;
	   document.getElementById("SaveUpdateAtomBtn").value=obj.id;
	   document.getElementById("DeleteAtomBtn").name=obj.id;
	}
	function SaveNewAtom() {
	  var para=window.parent.document.getElementById("qcid").innerHTML+"*";
	  para+=window.parent.document.getElementById("qshowtime1").innerHTML+"*";
	  para+=window.parent.document.getElementById("qenddate1").innerHTML;
	  para=para.replace(/\s/g,"#");
	  var namex=window.parent.document.getElementById("qnamex").innerHTML; 
	  var s=document.getElementById("queque");   
	  var li= document.createElement("li");  
	  var newid="ad"+document.getElementById("queque").getElementsByTagName("li").length;
	  if(document.getElementById("queque").getElementsByTagName("li").length>11) {alert("您好，很抱歉，最多只能添加12个节点！");return;}
	  li.innerHTML='<div class=item><img id='+newid+' name='+para+' src=images/pics/'+namex+' class=unchosed onClick="choose(this)"/></div>'; 
	  s.appendChild(li); 
	  choose(document.getElementById(newid));
	  }
	function SaveUpdateAtom(aid) {
	  var namex=window.parent.document.getElementById("qnamex").innerHTML; 
	  var para=window.parent.document.getElementById("qcid").innerHTML+"*";
	  para+=window.parent.document.getElementById("qshowtime1").innerHTML+"*";
	  para+=window.parent.document.getElementById("qenddate1").innerHTML;
	  para=para.replace(/\s/g,"#");
	  document.getElementById(aid).name=para;
	  document.getElementById(aid).src="images/pics/"+namex;
	  choose(document.getElementById(aid));
	  }
	 function SaveAddQue()
	 {
	   if( $("#title").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("序列描述不能为空!");return false;}
	   if(document.getElementById("queque").getElementsByTagName("li").length==0) {alert("最少需添加一个节点！");return false;}	  
	   var que="";
	   $("li img").each(function(){que+=(this.name+",");});
	   que=que.substring(0,que.length-1);
	   que=que.replace(/\#/g," ");
	   document.getElementById("addqueinfo").value=que;
	   return true;
	 }
	</script>  
</body>
</html>

