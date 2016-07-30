<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>男女使用手机的对比</title>
    
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
	    		<lable>请输入对比：</lable><input type="text" id="phone_num" name="phone_num"/> 
    			<input type="button" id="submit" value="查询">
    		
    	</div>
    	<script type="text/javascript">
    		$('#submit').click(function(){
    			//按照男女统计 
    				$.ajax({
    					type:"post",
    					url: "servlet/UserProfileServlet?method=GetCompairHabit",
					    success:function(data) { 
						    if(data=="null"){
						    	alert("没有对比的数据");
						    }else{
						    	var obj=eval(data);
						    	show_total(obj); 
						    	show_dayCount(obj);
						    }          
					     },    
					     error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
    				
    				});
    				$.ajax({
    					type:"post",
    					url: "servlet/UserProfileServlet?method=GetUserHourHabit" ,
					    data: {phone_num:phone_num,date:date} ,
					    success:function(data) { 
						    if(data=="null"){
						    	//alert("没有"+phone_num+"在"+date+"的数据");
						    }else{
						    	var obj=eval(data);
						    	show_day(obj); 
						    }          
					     },    
					     error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
    				
    				});
    				
    		 });
    		 /**
    		 * 总次数、总时长
    		 */
    		 function show_total(datas){
    		 	var times_total=0;
    		 	var dura_total=0;
    		 	for(var i=0,len=datas.length;i<len;i++){
    		 		times_total+=datas[i].times;
    		 		dura_total+=datas[i].dura_time;
    		 	}
    		 	var days=DayNumOfMonth(datas[0].date);
    		 	var times_day=parseInt(times_total/days);
    		 	var dura_day=parseInt(dura_total/days);
    		 	console.log(dura_day+"::::"+days);
    		 	document.getElementById("span_times").innerHTML=times_total;
    		 	document.getElementById("span_day_times").innerHTML=times_day;
    		 	document.getElementById("span_dura").innerHTML=dura_total;
    		 	document.getElementById("span_day_dura").innerHTML=dura_day;
    		 	
    		 }
    		 /**
    		 *得到月份
    		 */
    		 function DayNumOfMonth(date)
			{
				var Year=parseInt(date.substring(4));
				var Month=parseInt(date.substring(6,7));
			    Month--;
			    var d = new Date(Year,Month,1);
			    d.setDate(d.getDate()+32-d.getDate());
			    return (32-d.getDate());
			}
    		 /**
    		 *概况：计算每天的每两个小时的柱状图展示
    		 */
    		 function show_day(obj){
    		 	var time=[];
    		 	var nums=[];
    		 	for(var i=0,len=obj.length;i<len;i++){
    		 		time.push("'"+obj[i].time+"'");
    		 		nums.push(obj[i].nums);
    		 	}
    		 	console.log(time+"---"+nums);
    		 	 require.config({
		            paths: {
		                echarts: 'http://echarts.baidu.com/build/dist'
		            }
		        });
		        // 使用
		        require(
		            [
		                'echarts',
		                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById('times_show')); 
		               
		                option = { 
						    tooltip : {
						        trigger: 'axis'
						    }, 
						    toolbox: {
						        show : true,
						        feature : {
						            mark : {show: true},
						            dataView : {show: true, readOnly: false},
						            magicType : {show: true, type: ['line', 'bar']},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : true,
						    xAxis : [
						        {
						            type : 'category',
						            data : time
						        }
						    ],
						    yAxis : [
						        {
						            type : 'value'
						        }
						    ],
						    series : [
						        {
						            name:'使用频次',
						            type:'bar',
						            data:nums,
						            markPoint : {
						                data : [
						                    {type : 'max', name: '最大值'},
						                    {type : 'min', name: '最小值'}
						                ]
						            },
						            markLine : {
						                data : [
						                    {type : 'average', name: '平均值'}
						                ]
						            }
						        }
						    ]
						};
     
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
		            }
		        );
    		 
    		 
    		 }
    		 /**
    		 *折线图：每天多少分钟、次数
    		 */
    		 function show_dayCount(datas){
    		 	var date=[];
    		 	var times=[];
    		 	var dura_time=[];
    		 	for(var i=0,len=datas.length;i<len;i++){
    		 		date.push(datas[i].date);
    		 		times.push(datas[i].times);
    		 		dura_time.push(datas[i].dura_time);
    		 	}
    		 	// 路径配置 
		        require.config({
		            paths: {
		                echarts: 'http://echarts.baidu.com/build/dist'
		            }
		        });
		        // 使用
		        require(
		            [
		                'echarts',
		                'echarts/chart/line' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById('day_line')); 
		               
		                option = {
						    tooltip : {
						        trigger: 'axis'
						    }, 
						    toolbox: {
						        show : true,
						        feature : {
						            mark : {show: true},
						            dataView : {show: true, readOnly: false},
						            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
						    calculable : true,
						    xAxis : [
						        {
						            type : 'category',
						            boundaryGap : false,
						            data : date
						        }
						    ],
						    yAxis : [
						        {
						            type : 'value'
						        }
						    ],
						    series : [
						        {
						            name:'次数',
						            type:'line',
						            stack: '总量',
						            data:times
						        },
						        {
						            name:'时长',
						            type:'line',
						            stack: '总量',
						            data:dura_time
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
    		<div id="total">
    		<!-- 概况-->
    			<dt class="dt">概况</dt>
    			<div id="total_show">
    			<!-- 用户使用频次日均使用频次 -->
    				<img src="${pageContext.request.contextPath}/usermanager/user_profile/habit/images/survey_01.jpg" />
    				<ul style="float:left">
    					<li>使用总次数:<span class="minispan" id="span_times"></span></li>
    					<li>日均使用次数：<span class="minispan" id="span_day_times"></span></li>
    				</ul>
    				<ul class="list">
    					<li class="on"></li>
    					<li class="off"></li>
    					<li class="off"></li>
    					<li class="off"></li>
    					<li class="off"></li>
    				</ul>
    			</div>
    			<div id="total_dur" style="height:100px;">
    			<!-- 用户使用时长和日均使用时长 -->
    				<img src="${pageContext.request.contextPath}/usermanager/user_profile/habit/images/survey_02.jpg" />
    				<ul style="float:left;">
    					<li>累计总时长:<span class="minispan" id="span_dura"></span></li>
    					<li>日均使用时长：<span class="minispan" id="span_day_dura"></span></li>
    				</ul>
    				<ul class="list1">
    					<li class="on"></li>
    					<li class="off"></li>
    					<li class="off"></li>
    					<li class="off"></li>
    					<li class="off"></li>
    				</ul>
    			</div>
    		
    		</div>
    		<div id="times">
    		<!-- 使用时间柱状图表示每两个小时的时间段内的使用频次-->
    			<dt class="dt">使用频次</dt>
    			<div id="times_show" style="height:300px;">
    			<!-- 柱状图 -->
    				
    			</div>
    		
    		</div>
    		<div>
    		<!-- 一个月中的每天的频次和每天的总时间数-->
    			<dt class="dt">每日使用</dt>
    			<div id="day_line"  style="height:400px;">
    			<!-- 折线图-->
    				
    			</div>
    		
    		</div>
    	</div>
    </div>
  </body>
</html>
