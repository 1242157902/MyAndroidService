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
	width:140px;
	height:180px;
	border-radius:6px;
}
</style>
<script type="text/javascript" src="js/push/jq.js"></script>
<script type="text/javascript">
	$(function(){
	  var newid;
	  var queinfo=window.parent.document.getElementById("queue").innerHTML;
	  var atom=queinfo.split(",");
	  for(var i in atom){	
	    var ainfo=atom[i].split("*");
	    var s=document.getElementById("queque");   
	    var li= document.createElement("li");  
	    newid="ad"+document.getElementById("queque").getElementsByTagName("li").length;
	    li.innerHTML='<div id='+newid+' title='+ainfo[0]+' class=item><img src=images/pics/'+ainfo[4]+' /></div>'; 
	    s.appendChild(li); 
	  }
	  document.getElementById("qid").value=window.parent.document.getElementById("qqid").innerHTML;	
	  function Pointer(x, y) {
			this.x = x ;
			this.y = y ;
		}
		function Position(left, top) {
			this.left = left ;
			this.top = top ;
		}
		$(".item_content .item").each(function(i) {
			this.init = function() { // 初始化
				this.box = $(this).parent() ;
				$(this).attr("index", i).css({
					position : "absolute",
					left : this.box.offset().left,
					top : this.box.offset().top
				}).appendTo(".item_content") ;
				this.drag() ;
			},
			this.move = function(callback) {  // 移动
				$(this).stop(true).animate({
					left : this.box.offset().left,
					top : this.box.offset().top
				}, 500, function() {
					if(callback) {
						callback.call(this) ;
					}
				}) ;
			},
			this.collisionCheck = function() {
				var currentItem = this ;
				var direction = null ;
				$(this).siblings(".item").each(function() {
					if(
						currentItem.pointer.x > this.box.offset().left &&
						currentItem.pointer.y > this.box.offset().top &&
						(currentItem.pointer.x < this.box.offset().left + this.box.width()) &&
						(currentItem.pointer.y < this.box.offset().top + this.box.height())
					) {
						// 返回对象和方向
						if(currentItem.box.offset().top < this.box.offset().top) {
							direction = "down" ;
						} else if(currentItem.box.offset().top > this.box.offset().top) {
							direction = "up" ;
						} else {
							direction = "normal" ;
						}
						this.swap(currentItem, direction) ;
					}
				}) ;
			},
			this.swap = function(currentItem, direction) { // 交换位置
				if(this.moveing) return false ;
				var directions = {
					normal : function() {
						var saveBox = this.box ;
						this.box = currentItem.box ;
						currentItem.box = saveBox ;
						this.move() ;
						$(this).attr("index", this.box.index()) ;
						$(currentItem).attr("index", currentItem.box.index()) ;
					},
					down : function() {
						// 移到上方
						var box = this.box ;
						var node = this ;
						var startIndex = currentItem.box.index() ;
						var endIndex = node.box.index(); ;
						for(var i = endIndex; i > startIndex ; i--) {
							var prevNode = $(".item_content .item[index="+ (i - 1) +"]")[0] ;
							node.box = prevNode.box ;
							$(node).attr("index", node.box.index()) ;
							node.move() ;
							node = prevNode ;
						}
						currentItem.box = box ;
						$(currentItem).attr("index", box.index()) ;
					},
					up : function() {
						// 移到上方
						var box = this.box ;
						var node = this ;
						var startIndex = node.box.index() ;
						var endIndex = currentItem.box.index(); ;
						for(var i = startIndex; i < endIndex; i++) {
							var nextNode = $(".item_content .item[index="+ (i + 1) +"]")[0] ;
							node.box = nextNode.box ;
							$(node).attr("index", node.box.index()) ;
							node.move() ;
							node = nextNode ;
						}
						currentItem.box = box ;
						$(currentItem).attr("index", box.index()) ;
					}
				}
				directions[direction].call(this) ;
			},
			this.drag = function() { // 拖拽
				var oldPosition = new Position() ;
				var oldPointer = new Pointer() ;
				var isDrag = false ;
				var currentItem = null ;
				$(this).mousedown(function(e) {
					e.preventDefault() ;
					oldPosition.left = $(this).position().left ;
					oldPosition.top =  $(this).position().top ;
					oldPointer.x = e.clientX ;
					oldPointer.y = e.clientY ;
					isDrag = true ;
					
					currentItem = this ;
					
				}) ;
				$(document).mousemove(function(e) {
					var currentPointer = new Pointer(e.clientX, e.clientY) ;
					if(!isDrag) return false ;
					$(currentItem).css({
						"opacity" : "0.8",
						"z-index" : 999
					}) ;
					var left = currentPointer.x - oldPointer.x + oldPosition.left ;
					var top = currentPointer.y - oldPointer.y + oldPosition.top ;
					$(currentItem).css({
						left : left,
						top : top
					}) ;
					currentItem.pointer = currentPointer ;
					// 开始交换位置
					
					currentItem.collisionCheck() ;
					
					
				}) ;
				$(document).mouseup(function() {
					if(!isDrag) return false ;
					isDrag = false ;
					currentItem.move(function() {
						$(this).css({
							"opacity" : "1",
							"z-index" : 0
						}) ;
					}) ;
				}) ;
			}
			this.init() ;
		}) ;
	});
</script>
</head>
<body>
  <form action="servlet/QueServlet?method=SortQueue"  method="post" > 
    <input id=SaveSortQueBtn type="submit" value="确定" onClick="return SaveSortQue()" />
    <input id=CancelSortQueBtn type="button" value="取消" onClick="CancelSortQue()" />
    <input id="qid" type="hidden"  name="qid" />
    <input id=sortque type="hidden"  name="sortque" /> 
    <div class="item_container">
		<div class="item_content">
			 <ul id="queque">
			</ul>
		</div>
	</div>
  </form>
  <script type="text/javascript">
  function CancelSortQue()
   { 
      window.parent.document.getElementById("qqid").innerHTML=document.getElementById("qid").value;
      window.parent.document.getElementById("qSortQueCloseTabBtn").click();
   }
  function SaveSortQue()  
  {
     var quey="";
     var n=document.getElementById("queque").getElementsByTagName("li").length;
     var quex= new Array(n); 
     for(var i=0;i<n;i++)
     {
       count=0;
       for(var j=0;j<n;j++)
       {
         if(i==j)  continue;
         var ix=document.getElementById("ad"+i).offsetLeft;
         var iy=document.getElementById("ad"+i).offsetTop;
         var jx=document.getElementById("ad"+j).offsetLeft;
         var jy=document.getElementById("ad"+j).offsetTop;
         if((jy==iy&&jx<ix)||(jy<iy))  count++;
       }
       quex[count]=document.getElementById("ad"+i).title+",";
     }
     for(var k=0;k<n;k++)
     {
      quey+=quex[k];
     }
     document.getElementById("sortque").value=quey;
     if(quey.match(/[^0123456789,]/g)!=null)   {alert("存储排序错误，可能是浏览器不兼容！");return false;}  
	 return true;
  }
  </script>
</body>
</html>
