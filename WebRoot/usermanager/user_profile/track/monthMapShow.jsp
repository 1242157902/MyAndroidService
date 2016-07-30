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
	<script type="text/javascript" src="http://echarts.baidu.com/build/dist/echarts.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/usermanager/user_profile/track/js/map.js"></script>
  </head>
  
  <body>
  <script type="text/javascript" src="${pageContext.request.contextPath}/usermanager/user_profile/track/js/map.js"></script>
 
    <div id="content">
    	<div id="month_title">
    	<!-- 条件选择区 -->
	    		<lable>请输入手机号：</lable><input type="text" id="phone_num" name="phone_num"/>
	    		&nbsp;&nbsp;&nbsp;
	    		<lable>请输入查询时间：</lable><input type="text" id="date" name="date" class="easyui-datebox" editable="false" />
    			<input type="button" id="submit" value="查询">
    		
    	</div>
    	<div id="month_container">
    	<!-- 地图显示区域 -->
    	</div>
    	<div id="month_bar"> 
    	 
    		
    	</div>
    	<div id="month_pie"> 
    	</div>  
    <!--  	<div id="month_distinct_pie"> 
    	</div>-->
    	<div id="day_pie"> 
    	</div> 
    	<div id="click_pie">
    		<div id="city_pie" style="width:460px;height:500px;float:left;"></div> 
    		<div id="dis_pie" style="width:460px;height:500px;float:left;"></div>
    		<div id="str_pie" style="width:460px;height:500px;float:left;"></div>
    		<script>
    		/*统计中国省市占比*/
    			function deal_data(datas,loc){
    				var addrs={};
    				for(var i=0,len=datas.length;i<len;i++){
	    				var address=datas[i].address.substring(0,10);
	    				var date=datas[i].date;
	    				var count=datas[i].count;
	    				if(addrs[address]){
	    					addrs[address]+=count;
	    				}else
	    					addrs[address]=count;
    				}
    				
    				var prin=[];
    				for(var add in addrs){
    					prin.push({value:addrs[add],name:add.substring(3,10)});
    				}
    				console.log("deal_data");
		       		show_pies(prin,'city_pie',datas,'dis');
    			}
    	
    			
    			/*按照各市的区占比*/
    			function dis(value,datas){ 
    				var addrs={};
    				for(var i=0,len=datas.length;i<len;i++){
	    				var address=datas[i].address.substring(0,15);
	    				var date=datas[i].date;
	    				var count=datas[i].count;
	    				if(address.indexOf(value)>=0){
	    					if(addrs[address]){
	    						addrs[address]+=count;
		    				}else
		    					addrs[address]=count;
		    			}
    				}
    				var prin=[];
    				for(var add in addrs){
    					prin.push({value:addrs[add],name:add.split(',')[3]});
    				}
		       		show_pies(prin,"dis_pie",datas,'disc');
    			}
    			
    			/*街道占比*/
    			function disc(value,datas){
    				var addrs={};
    				for(var i=0,len=datas.length;i<len;i++){
	    				var address=datas[i].address;
	    				var date=datas[i].date;
	    				var count=datas[i].count;
	    				if(address.indexOf(value)>=0){
	    					if(addrs[address]){
	    						addrs[address]+=count;
		    				}else
		    					addrs[address]=count;
		    			}
    				}
    				var prin=[];
    				for(var add in addrs){
    					prin.push({value:addrs[add],name:add.split(',')[4]});
    				}
		       		show_pies(prin,"str_pie",datas,'');
    			}
    			
    			function show_pies(data,loc,datas,fn){
    				 console.log("show0");
    				 // 使用
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
		                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		            ],
		        
		       
		            function (ec) {  
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById(loc)); 	
						 console.log("show1");
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
					            data:data
					        }
					    ]
					};
				var ecConfig = require('echarts/config');
				function eConsole(param) {
				 console.log("show2");
				    var mes = '【' + param.type + '】';
				    if (typeof param.seriesIndex != 'undefined') {
				        mes += '  seriesIndex : ' + param.seriesIndex;
				        mes += '  dataIndex : ' + param.dataIndex;
				    }
				    if (param.type == 'click') {
				        var value=param.data["name"];
				        eval(fn+"(value,datas)");
				    }
				    console.log(param);
				}
				console.log("show");
				// 为echarts对象加载数据 
				myChart.setOption(option); 
	
				myChart.on(ecConfig.EVENT.CLICK, eConsole);
				//myChart.on(ecConfig.EVENT.HOVER, eConsole);
				myChart.on(ecConfig.EVENT.DATA_ZOOM, eConsole);
				myChart.on(ecConfig.EVENT.LEGEND_SELECTED, eConsole);
				myChart.on(ecConfig.EVENT.MAGIC_TYPE_CHANGED, eConsole);
				myChart.on(ecConfig.EVENT.DATA_VIEW_CHANGED, eConsole);
						  }
			);  
    			
    			}
    		</script>
    	</div> 
    	<div id="month_detail"> 
    	</div>  
		<script type=text/javascript>
			var map = new BMap.Map("month_container");
    			var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
				map.centerAndZoom(point, 5); 
				map.enableScrollWheelZoom();
				// 创建地址解析器实例
				var myGeo = new BMap.Geocoder();
				 
    			$('#submit').click(function(){ 
    				var phone_num=$('#phone_num').val();
    				var date=$('#date').datebox('getValue');
    				date=date.substr(0,7);
    				if(phone_num==""||date==""){
    					alert("请输入手机号或日期！");
    					return;
    				}  
    				$.ajax({
    					type:"post",
    					url: "servlet/UserProfileServlet?method=GetSingleUserMonthMap" ,
					    data: {phone_num:phone_num,date:date} ,
					    success:function(data) { 
						    if(data=="null"){
						    	alert("没有"+phone_num+"在"+date+"的定位信息");
						    }else{
						    	var obj=eval(data);
						        show_map(obj);
						        show_detail(obj);
						        showDay(obj);
						        deal_data(obj);
						    }          
					     },    
					     error : function() {    
					          // view("异常！");    
					          alert("异常！");    
					     }    
    				
    				});
    			});
    		
    		
    		/**
    		*显示地图
    		*/
    		function show_map(datas){ 
    			var phone_num;
    			var point; 
				map.clearOverlays();
				
    			 
				var addrs={};
    			for(var i=0,len=datas.length;i<len;i++){
    				var address=datas[i].address;
    				var date=datas[i].date;
    				var count=datas[i].count;
    				if(addrs[address]){
    					addrs[address]+=count;
    				}else
    					addrs[address]=count;
    			} 
    			var mapdatas=[];
    			var address=[];
    			var count=[];
    			for(var add in addrs){ 
    				geocodeSearch(add,addrs[add]);
    				if(addrs[add]<4)
    					continue;
    				mapdatas.push({"value":addrs[add],"name":add.substring(7)});
    				address.push(add.substring(7));
    				count.push(addrs[add]);
    			}
    			showBar(address,count);
    			showPie(mapdatas,address,count);
    		}
    		
    		/**
    		*显示每天的轨迹
    		*/
    		function show_detail(datas){
    			document.getElementById("month_detail").innerHTML="";
    			for(var i=0,len=datas.length;i<len;i++){
    				var address=datas[i].address.substring(7);
    				var date=datas[i].date;
    				var count=datas[i].count; 
    				if(i>0){
    					if(date==datas[i-1].date)
    						document.getElementById("month_detail").innerHTML+="<font>||| "+address+":"+count+"次</font>";
    					else
    						document.getElementById("month_detail").innerHTML+="<br><br><font>日期："+date+"<br>&nbsp;&nbsp;出现在："+address+";次数："+count+"次</font>";
    				}else{
    					document.getElementById("month_detail").innerHTML+="<br><font>日期："+date+"<br>&nbsp;&nbsp;出现在："+address+";次数："+count+"次</font>";
    				}
    				
    		//		$('#month_detail').html("<p>日期："+date+"；出现在："+address+";次数："+count+"</p>");
    			}
    		}
    		/**
    		*批量逆地址解析
    		*/
			function geocodeSearch(add,count){
				myGeo.getPoint(add, function(point){
					if (point) {
						var address = new BMap.Point(point.lng, point.lat);
						addMarker(address,new BMap.Label("出现:"+count+"次",{offset:new BMap.Size(20,-10)}));
						var circle = new BMap.Circle(point,100);
						map.addOverlay(circle);            //增加圆
						hideOver();
					}
				}, add.substr(3,3));
			}
			/**
    		*编写自定义函数,创建标注
    		*/
			function addMarker(point,label){
				var marker = new BMap.Marker(point);
				map.addOverlay(marker);
				marker.setLabel(label);
			}
			
			function showBar(address,count){
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
		                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById('month_bar')); 
		               
		                option = {
						    title: {
						        x: 'center',
						        text: '当月统计',
						        subtext: 'Rainbow bar example', 
						    },
						    tooltip: {
						        trigger: 'item'
						    },
						    toolbox: {
						        show: true,
						        feature: {
						            dataView: {show: true, readOnly: false},
						            restore: {show: true},
						            saveAsImage: {show: true}
						        }
						    },
		    				calculable: true,
						    grid: {
						        borderWidth: 0,
						        y: 80,
						        y2: 60
						    },
						    xAxis: [
						        {
						            type: 'category',
						            show: false,
						            data: address//['Line', 'Bar', 'Scatter', 'K', 'Pie', 'Radar', 'Chord', 'Force', 'Map', 'Gauge', 'Funnel']
						        }
						    ],
						    yAxis: [
						        {
						            type: 'value',
						            show: false
						        }
						    ],
						    series: [
						        {
						            name: 'ECharts例子个数统计',
						            type: 'bar',
						            itemStyle: {
						                normal: {
						                    color: function(params) {
						                        // build a color map as your need.
						                        var colorList = [
						                          '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
						                           '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
						                           '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0'
						                        ];
						                        return colorList[params.dataIndex]
						                    },
						                    label: {
						                        show: true,
						                        position: 'top',
						                        formatter: '{b}\n{c}'
						                    }
						                }
						            },
						            data: count,
						            
						        }
						    ]
						};
		                // 为echarts对象加载数据 
		                myChart.setOption(option); 
		            }
		        );
		} 
					
		function showPie(mapdatas,add,count){
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
		                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {  
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById('month_pie')); 	
						option = {
						    title : {
						        text: '南丁格尔玫瑰图',
						        subtext: '纯属虚构',
						        x:'center'
						    },
						    tooltip : {
						        trigger: 'item',
						        formatter: "{a} <br/>{b} : {c} ({d}%)"
						    }, 
						    toolbox: {
						        show : true,
						        feature : {
						            mark : {show: true},
						            dataView : {show: true, readOnly: false},
						            magicType : {
						                show: true, 
						                type: ['pie', 'funnel']
						            },
						            restore : {show: true},
						            saveAsImage : {show: true}
						        }
						    },
					   	 	calculable : true,
						    series : [
						        {
						            name:'面积模式',
						            type:'pie',
						            radius : [30, 110], 
						            roseType : 'area',
						            x: '50%',               // for funnel
						            max: 40,                // for funnel
						            sort : 'ascending',     // for funnel
						            data:mapdatas
						        }
						    ]
						};
		      		// 为echarts对象加载数据 
				     myChart.setOption(option); 
				  }
			);  
		}
		
		function showDay(datas){
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
		                'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
		            ],
		            function (ec) {  
		                // 基于准备好的dom，初始化echarts图表 
		                var myChart = ec.init(document.getElementById('day_pie')); 	
						var dates=[];
						var series=function(name,data){this.name=name;this.data=data;};
						var seris=[];
						var num=0;
						var idx = 1;
						for(var i=0,len=datas.length;i<len;i++){
		    				var address=datas[i].address.substring(7);
		    				var date=datas[i].date;
		    				var count=datas[i].count; 
		    				if(i>0){
		    					if(date==datas[i-1].date){
									seris[num-1].data.push({value:count,name:address});
		    					}else{
		    						dates.push(date);
		    						var item=new series(date+"图标展示",[{value:count,name:address}]);
									seris[num]=item;
									num++;
		    					}
		    				}else{
		    					dates.push(date);
		    						var item=new series(date+"图标展示",[{value:count,name:address}]);
									seris[num]=item;
									num++;
		    				}
	    				}	
	    				var optionss=[];
	    			 	for(var i=0,len=seris.length;i<len;i++){
							optionss[i]={series:[{name:seris[i].name,type:'pie',data:seris[i].data}]};
							
						} 
						console.log(JSON.stringify(optionss));
						
						option = {
						    timeline : {
						        data : dates,
						        label : {
						            formatter : function(s) {
						                return s;
						            }
						        }
						    },
					    	options : optionss
					}; 
					 
					
				     myChart.setOption(option); 
				  }
			);  
		}
	 
		</script>
    </div>
  </body>
</html>
