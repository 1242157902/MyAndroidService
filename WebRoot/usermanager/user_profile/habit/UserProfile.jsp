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
    
	<meta http-equiv="pragm a" content="no-cache">
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
	    		<lable>请输入查询时间：</lable><input type="text" id="date" name="date" class="easyui-datebox" editable="false" />
    			<input type="button" id="submit" value="查询">
    		
    	</div>
    	<script type="text/javascript">
    		$('#submit').click(function(){
    			var phone_num=$('#phone_num').val();
    			var date=$('#date').datebox('getValue');
    			date=date.substr(0,7);
    				if(phone_num==""||date==""){
    					alert("请输入手机号或日期！");
    					return;
    				}
    				//请求用户的手机维度信息  
    				$.ajax({
    					type:"post",
    					url: "servlet/UserProfileServlet?method=GetUserProfile" ,
					    data: {phone_num:phone_num,date:date} ,
					    success:function(data) { 
						    if(data=="null"){
						    	alert("没有"+phone_num+"在"+date+"的数据");
						    }else{
						    	var obj=eval(data);
						    	deal(data); 
						   //	show_dayCount(obj);
						    }          
					     },    
					     error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
    				
    				});   				
    		 });
    		 
    		 function deal(obj){
    		
    		 	var data=eval(obj);data=data[0];
    		 	 
    		 	dealBasic(data);
    		 	show_dayCount(data.habit);
    		 	dealprefer(data.prefer);
    		 	dealapp(data.app);
    		 	dealdic(data.map);
    		 }
    		 /**处理基本数据
    		 */
    		 function dealBasic(datas){
    		 	var data=datas.basic;
    		 	var phone=data.mobile;
    		 	//处理年龄+性别
    		 	var sex=data.sex;var age=data.age;
    			var memo=null;
    			if(age==1){
    				if(sex==1){
    					memo="靓丽的大帅哥";
    				}else{
    					memo="漂亮的萌妹纸";
    				}
    			}else if(age==2){
    				if(sex==1){
    					memo="迷人大叔";
    				}else{
    					memo="气质女王";
    				}
    			}else if(age==3){
    				if(sex==1){
    					memo="时尚的老干部";
    				}else{
    					memo="fashion girl";
    				}
    			}else{
    				if(sex==1){
    					memo="谜一样的帅哥";
    				}else{
    					memo="神秘的美女";
    				}
    			}
    			var oper=data.operator; 
    			document.getElementById("oper").innerHTML=oper;
    			document.getElementById("sexAge").innerHTML=memo;
    		 	document.getElementById("phone").innerHTML=phone;
    		 }
    		 /**浏览的图片
    		 */
    		 function dealprefer(data)
    		 { 
    			 document.getElementById('mytableid').innerHTML=''; 
    		 	var len=data.length;
    		 	if(len>10) len=10;
    		 	for(var i=0;i<len;i++){
    		 		var ad=data[i].scan_num;
    		 		if(ad==0)
    		 			var status="↑"
    		 		else
    		 			var status="↓";
    		 		addRow(i+1,data[i].classify,status,"mytableid");
    		 	}
       
    		 }
    		 /**浏览的app
    		 
    		 */
    		 function dealapp(data)
    		 {
    		 document.getElementById('mytableapp').innerHTML=''; 
    		 	var len=data.length;
    		 	if(len>5) len=5;
    		 	for(var i=0;i<len;i++){
    		 	var ad=data[i].nums;
    		 		if(ad==0)
    		 			var status="↑"
    		 		else
    		 			var status="↓";
    		 		addRow(i+1,data[i].classify,status,"mytableapp");
    		 	}
       
    		 }
    		 //地理位置
    		 function dealdic(data)
    		 {
    		 document.getElementById('mytabledic').innerHTML=''; 
    		 	var len=data.length;
    		 	if(len>5) len=5;
    		 	for(var i=0;i<len;i++){
    		 		addRow(i+1,data[i].address,"","mytabledic");
    		 	}
       
    		 }
    		 function addRow(num,data,status,id) {
         	   var tb = document.getElementById(id);
            	var row = tb.insertRow();
           		 var cell = row.insertCell();
           	 	cell.innerText =num;
           	 	cell = row.insertCell();
           		 cell.innerHTML = "<span style='font-size:14px;color:red;font-weight:bold;text-align:center;'>"+data+"</span>";
           		 cell = row.insertCell();
           		 cell.innerHTML = " "+status;
            
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
						            data:times,
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
						        },
						        {
						            name:'时长',
						            type:'line',
						            stack: '总量',
						            data:dura_time,
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
    	</script>
    	<div id="show">
    	<!-- 显示区域 -->
    		<div id="basic">
    		<!-- 基本信息  年龄+性别+ 手机、运营商-->
    			<dt class="dt">基本情况</dt>
    			<div id="total_show">
    			<li>这是一个 <span style="font-size:14px;color:red;font-weight:bold" id="sexAge"></span></li>
    			<li>你更偏爱 <span style="font-size:14px;color:red;font-weight:bold" id="oper"></span></li>
    			<li>你的最爱是  <span style="font-size:14px;color:red;font-weight:bold" id="phone"></span></li> 
    		
    		</div>
    		<div id="prefer">
    		<!-- 偏好 图片和app的并对比各个偏好增加或者减少还是新增，列表展示前五个  标出上下箭头-->
    			<dt class="dt">图片偏好</dt>
    			<br>
    			<div id="times_show" style="height:300px;">
    			<!-- 柱状图 -->
    			<table id="mytableid">
        			<!-- <tr><td>第一行</td><td>输入</td></tr> -->
    			</table>	
    			</div>
    			<div  style="float:left;height:300px;width:50%">
    			<!-- 柱状图 -->
    			<dt class="dt">APP偏好</dt>
    			<br>
    			<table id="mytableapp">
    			</table>	
    			</div>
    			<div  style="height:300px;">
    			<!-- 柱状图 -->
    			<dt class="dt">经常出没位置</dt>
    			<br>
    			<table id="mytabledic">
        			<!-- <tr><td>第一行</td><td>输入</td></tr> -->
    			</table>	
    			</div>
    		
    		</div>
    		<div id="time">
    		<!-- 时间维度-->
    			<dt class="dt">本月使用手机情况</dt>
    			<div id="day_line"  style="height:400px;">
    			<!-- 折线图-->
    				
    			</div>
    			<!--相比较上个月，您本月比上个月多万了多少时间，如果少于上个月，控制能力又增强了一补
    			若比上个月时间少，则  拥有良好的手机使用习惯，会生活更丰富多彩哦~  -->
    		</div>
    	</div>
    </div>
  </body>
</html>
