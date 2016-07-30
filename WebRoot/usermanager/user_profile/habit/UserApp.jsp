<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户浏览图片及点击链接的偏好</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/usermanager/user_profile/css/habit.css">
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>              
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="http://echarts.baidu.com/build/dist/echarts.js"></script>
 
  </head>
  
  <body>
    <div id="content">
    	<div id="map_title">
    	<!-- 条件选择区 -->
	    		<lable>请输入手机号：</lable><input type="text" id="phone_num" name="phone_num"/>
	    		<lable>请输入查询时间：</lable><input type="text" id="startdate" name="date" class="easyui-datebox" editable="false" />
    			至<input type="text" id="enddate" name="date" class="easyui-datebox" editable="false" />
    			<input type="button" id="submit" value="查询">
    		
    	</div>
    	<script type="text/javascript">
    		$('#submit').click(function(){
    			var phone_num=$('#phone_num').val();
    			var startdate=$('#startdate').datebox('getValue');
    			var enddate=$('#enddate').datebox('getValue');
    				if(phone_num==""||startdate==""||enddate==""){
    					alert("请输入手机号或日期！");
    					return;
    				}  
    				$.ajax({
    					type:"post",
    					url: "servlet/UserProfileServlet?method=GetUserApp" ,
					    data: {phone_num:phone_num,startdate:startdate,enddate:enddate} ,
					    success:function(data) { 
						    if(data=="null"){
						    	alert("没有"+phone_num+"在 此期间的数据");
						    }else{
						    	var obj=eval(data);
						    	show_pie(obj);  
						    }          
					     },    
					     error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
    				
    				});
    		 });
    		  
    		 /**
    		 *概况：计算每天的每两个小时的柱状图展示
    		 */
    		 function show_pie(obj){
    		 	var classify={};
    		 	for(var i=0,len=obj.length;i<len;i++){
    		 		var classi=obj[i].classify;
	    			var nums=obj[i].scan_num;
	    			
	    			if(classi.length==0){
	    				continue;
	    			}
	    			if(classify[classi]){
	    				 classify[classi]+=nums;
	    			}else
	    				classify[classi]=nums;
    		 	} 
    		 	var prin=[];
    			for(var c in classify){
    			
    				prin.push({value:classify[c],name:c});
    				console.log(prin);
    			}
    		 	 require.config({
		            paths: {
		                echarts: 'http://echarts.baidu.com/build/dist'
		            }
		        });
		        // 使用
		        require(
		            [
		                'echarts',
		                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById('classify_show')); 
		               
		                 option = {
					    title : { 
					        x:'center'
					    },
    					 tooltip : {
					        trigger: 'item',
					        formatter: "{a} <br/>{b} : {c} ({d}%)"
					    },
    
	    				calculable : true,
					    series : [
					        {
					            name:'所在区域',
					            type:'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            data:prin
					        }
					    ]
					};
     
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
		            }
		        );
    		 
    		 
    		 } 
    	</script>
    	<div id="show">
    	<!-- 显示区域 -->
    	<div>
    		<!-- 概况-->
    		<dt class="dt">偏好分布</dt>
    		<div id="classify_show" style="height:300px;">
    	</div> 
    	</div>
    </div>
  </body>
</html>
