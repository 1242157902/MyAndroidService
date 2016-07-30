var map = new BMap.Map("month_container");
    			var point = new BMap.Point(116.404, 39.915);  // 创建点坐标  
				map.centerAndZoom(point, 5); 
				map.enableScrollWheelZoom();
				// 创建地址解析器实例
				var myGeo = new BMap.Geocoder();
				 
    			$('#submit').click(function(){
    			alert("122");
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
	 