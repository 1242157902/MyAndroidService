<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'ContentLoadImage.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
<input id=parainfo type="hidden" value="<%=request.getParameter("url")%>" />
<div id="objDiv" style="display:none"><img id=showimg width=305px height=460px  /></div>   
<SCRIPT>
var intTimeStep=80;
var isIe=(window.ActiveXObject)?true:false;
var intAlphaStep=(isIe)?80:0.5;
var curSObj=null;
var curOpacity=null;
function startObjVisible(objId)
{
curSObj=document.getElementById(objId);
setObjState();
}
function setObjState(evTarget)
{
if (curSObj.style.display==""){curOpacity=1;setObjClose();}
else{
if(isIe)
{
curSObj.style.cssText='DISPLAY: none;Z-INDEX: 1; FILTER: alpha(opacity=0); POSITION: absolute;';
curSObj.filters.alpha.opacity=0;
}else
{
curSObj.style.opacity=0;
}
curSObj.style.display='';
curOpacity=0;
setObjOpen();
}
}
function setObjOpen()
{
if(isIe)
{
curSObj.filters.alpha.opacity+=intAlphaStep;
if (curSObj.filters.alpha.opacity<100) setTimeout('setObjOpen()',intTimeStep);
}else{
curOpacity+=intAlphaStep;
curSObj.style.opacity =curOpacity;
if (curOpacity<1) setTimeout('setObjOpen()',intTimeStep);
}
}
function setObjClose()
{
if(isIe)
{
curSObj.filters.alpha.opacity-=intAlphaStep;
if (curSObj.filters.alpha.opacity>0) {
setTimeout('setObjClose()',intTimeStep);}
else {curSObj.style.display="none";}
}else{
curOpacity-=intAlphaStep;
if (curOpacity>0) {
curSObj.style.opacity =curOpacity;
setTimeout('setObjClose()',intTimeStep);}
else {curSObj.style.display='none';}
}
}
</SCRIPT>
<script type="text/javascript">
var xx;
var yy;
var zz;
$(function(){
   var para=document.getElementById("parainfo").value;
   var atom=para.split(",");
   var n=atom.length;
   clearTimeout(xx);
   clearTimeout(yy);
   clearTimeout(zz);
   startObjVisible('objDiv');
   ChangeSrc(0,n,atom,atom[0].split("*"));
});
function ChangeSrc(i,n,atom,ainfo)
{
   if(i==n) return;
   var showtime=ainfo[2]*1000+600;
   var namex=ainfo[4];	   
   document.getElementById("showimg").src="images/pics/"+namex;
   xx=window.setTimeout(function(){
	   if(i+1==n)  {document.getElementById("showimg").src="images/imitateEnd.jpg";}
       else {ChangeSrc(i+1,n,atom,atom[i+1].split("*"));}       
       zz=window.setTimeout(function(){startObjVisible('objDiv');},300);
   },showtime); 
   yy=window.setTimeout(function(){
       startObjVisible('objDiv');
   },showtime-300); 
}

</script>
</body>
</html> 