<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'map.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/usermanager/user_profile/track/css/map.css">
		<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>              
		<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=dpWxwKSSSunlPAVDussdUq7Q"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>
	
  </head>
  
  <body>
    <div id="content">
    	<div id="map_title">
    	<!-- 条件选择区 -->
	    		<lable>请输入手机号：</lable><input type="text" id="phone_num" name="phone_num"/>
	    		<lable>请输入查询时间：</lable><input type="text" id="date" name="date" class="easyui-datebox" editable="false" />
    			<input type="button" id="submit" value="查询">
    		
    	</div>
    	<div id="map_container">
    	<!-- 地图显示区域 -->
    	</div>
    	<script type="text/javascript"> 
    			var map = new BMap.Map("map_container");
    			var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
				map.centerAndZoom(point, 10); 
				map.enableScrollWheelZoom();
				var position=[]; 
    			var curve;
    			$('#submit').click(function(){
    			
    				var phone_num=$('#phone_num').val();
    				var date=$('#date').datebox('getValue');;
    				if(phone_num==""||date==""){
    					alert("请输入手机号或日期！");
    					return;
    				}  
    				$.ajax({
    					type:"post",
    					url: "servlet/UserProfileServlet?method=SelectSingleUserMap" ,
					    data: {phone_num:phone_num,date:date} ,
					    success:function(data) { 
						    if(data=="null"){
						    	alert("没有"+phone_num+"在"+date+"的定位信息");
						    }else{
						    	var obj=eval(data);
						        show_map(obj);
						    }          
					     },    
					     error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
    				
    				});
    			});
    		
    		
    		
    		function show_map(datas){
    		if(position.length!=0){
    			position=[];
    		//	map.removeOverlay(curve);
    			map.clearOverlays();  
    		} 
    			var phone_num;
    			var point;
    			
    			for(var i=0,len=datas.length;i<len;i++){
    				var pointlat=datas[i].latitude;
    				var pointlong=datas[i].longitude;
    				if(pointlat==4.9E-324||pointlong==4.9E-324)
    					continue;
    				position.push(new BMap.Point(pointlong,pointlat));
    			} 
    			
    			window.setTimeout(function(){  
				    map.panTo(position[0]);    
				}, 2000);
				curve = new BMapLib.CurveLine(position, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); //创建弧线对象
				map.addOverlay(curve); //添加到地图中
				curve.enableEditing(); //开启编辑功能*/
    		}
			// 百度地图API功能
	/*		var map = new BMap.Map("map_container");
			
			map.enableScrollWheelZoom();
			var beijingPosition=new BMap.Point(116.432045,39.910683),
				hangzhouPosition=new BMap.Point(116.235642,39.913533),
				taiwanPosition=new BMap.Point(116.331398,39.897445);
				map.centerAndZoom(taiwanPosition, 12);
		  hhPosition=new BMap.Point(116.404, 39.915);
			var points = [beijingPosition,hangzhouPosition, taiwanPosition,hhPosition];
		
			var curve = new BMapLib.CurveLine(points, {strokeColor:"blue", strokeWeight:3, strokeOpacity:0.5}); //创建弧线对象
			map.addOverlay(curve); //添加到地图中
			curve.enableEditing(); //开启编辑功能*/
</script>
    </div>
  </body>
</html>
