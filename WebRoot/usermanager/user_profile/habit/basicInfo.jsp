<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'basicInfo.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page"> 
	
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/usermanager/user_profile/css/habit.css">
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script> 
	<script type="text/javascript" src="http://echarts.baidu.com/build/dist/echarts.js"></script>
  </head>
  <script>
  	 $(function(){
  	 	$.ajax({
    		type:"post",
    		url: "servlet/UserProfileServlet?method=GetUsersBasic" , 
			success:function(data) { 
			    if(data=="null"){
			    	alert("没有"+phone_num+"在 此期间的数据");
				}else{
					var obj=eval(data);
					dealShow(obj);  
				}          
			},    
			error : function() {    
				 // view("异常！");    
				 alert("异常！");
				 }    
    		});
  	 })
  	 //处理传过来的数据
  	 function dealShow(data){
  	 	var male=0;var female=0;
  	 	var young=0;var midlle=0;var old=0;var noage=0;
  	 	var age={};
  	 	var area={};
  	 	for(var i=0,len=data.length;i<len;i++){
  	 		//性别分布
  	 		console.log("性别："+data[i].sex);
  	 		var age0=data[i].age;
  	 		var dis=data[i].area;
  	 		if(data[i].sex==1)
  	 			male++;
  	 		else
  	 			female++;
  	 		//年龄分布
  	 		if(age[age0])
  	 			age[age0]++;
  	 		else
  	 			age[age0]=1;
  	 		//地域分布
  	 		if(area[dis])
  	 			area[dis]++;
  	 		else
  	 			area[dis]=1;
  	 	}
  	 	var sex=[];
  	 	sex.push({value:female,name:'女'});
  	 	sex.push({value:male,name:'男'});
  	 	show_pie('getoperater','sex',sex,data);
  	 	
  	 	var ages=[];
  	 	for(var a in age){
  	 		var name;
  	 		if(a==0){
  	 			name="未知";
  	 		}else if(a==1){
  	 			name="青年";
  	 		}else if(a==2){
  	 			name="中年";
  	 		}else if(a==3){
  	 			name="老年";
  	 		}
  	 		ages.push({value:age[a],name:name});
  	 	}
  	 	show_pie('getoperater','age',ages,data);
  	 	
  	 	var dis=[];
  	 	for(var d in area){
  	 		dis.push({value:area[d],name:d});
  	 	}
  	 	show_pie('getoperater','dis',dis,data);
  	 }
  	 /**
  	 *运营商分布
  	 *value:选中的值：男女等,datas:原始数据
  	 */
  	 function getoperater(value,datas,loc){
  	 	var operator={};
  	 	var mobile={}; 
  	 	if(loc=="sex"){
  	 		var v=0;
  	 		if(value=='男')
  	 			v=1;
  	 		else
  	 			v=2;
  	 		for(var i=0,len=datas.length;i<len;i++){
  	 			if(datas[i].sex==v){
  	 				//计算运营商分布
  	 				var o=datas[i].operator;
	    			if(operator[o]){
	    				operator[o]+=1;
	    			}else
	    				operator[o]=1;
	    			//计算客户端类型
	    			var m=datas[i].mobile;
	    			if(mobile[m]){
	    				mobile[m]+=1;
	    			}else
	    				mobile[m]=1;
  	 			}
  	 		}
  	 		var p_oper=[];
    		for(var c in operator){
    			p_oper.push({value:operator[c],name:c});
    			console.log(p_oper);
    		}
    		var p_mob=[];
    		for(var c in mobile){
    			p_mob.push({value:mobile[c],name:c});
    			console.log(p_mob);
    		}
    		show_pie('','sex_operator',p_oper,datas);
    		show_pie('','sex_mobile',p_mob,datas);
  	 	}else if(loc=="age"){
  	 		var v=0;
  	 		if(value=="未知"){
  	 			v=0;
  	 		}else if(value=="青年"){
  	 			v=1;
  	 		}else if(value=="中年"){
  	 			v=2;
  	 		}else if(value=="老年"){
  	 			v=3;
  	 		}
  	 		for(var i=0,len=datas.length;i<len;i++){
  	 			if(datas[i].age==v){
  	 				//计算运营商分布
  	 				var o=datas[i].operator;
	    			if(operator[o]){
	    				operator[o]+=1;
	    			}else
	    				operator[o]=1;
	    			//计算客户端类型
	    			var m=datas[i].mobile;
	    			if(mobile[m]){
	    				mobile[m]+=1;
	    			}else
	    				mobile[m]=1;
  	 			}
  	 		}
  	 		var p_oper=[];
    		for(var c in operator){
    			p_oper.push({value:operator[c],name:c});
    			console.log(p_oper);
    		}
    		var p_mob=[];
    		for(var c in mobile){
    			p_mob.push({value:mobile[c],name:c});
    			console.log(p_mob);
    		}
    		show_pie('','age_operator',p_oper,datas);
    		show_pie('','age_mobile',p_mob,datas);
  	 	}else if(loc=="dis"){
  	 		for(var i=0,len=datas.length;i<len;i++){
  	 			if(datas[i].area==value){
  	 				//计算运营商分布
  	 				var o=datas[i].operator;
	    			if(operator[o]){
	    				operator[o]+=1;
	    			}else
	    				operator[o]=1;
	    			//计算客户端类型
	    			var m=datas[i].mobile;
	    			if(mobile[m]){
	    				mobile[m]+=1;
	    			}else
	    				mobile[m]=1;
  	 			}
  	 		}
  	 		var p_oper=[];
    		for(var c in operator){
    			p_oper.push({value:operator[c],name:c});
    			console.log(p_oper);
    		}
    		var p_mob=[];
    		for(var c in mobile){
    			p_mob.push({value:mobile[c],name:c});
    			console.log(p_mob);
    		}
    		show_pie('','dis_operator',p_oper,datas);
    		show_pie('','dis_mobile',p_mob,datas);
  	 	}
  	 }
  	 function show_pie(fn,loc,data,datas){
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
					series : [{
					   name:'所在区域',
					   type:'pie',
					   radius : '55%',
					   center: ['50%', '60%'],
					   data:data
					 }]
			};
			var ecConfig = require('echarts/config');
			function eConsole(param) {
				var mes = '【' + param.type + '】';
				if (typeof param.seriesIndex != 'undefined') {
				   mes += '  seriesIndex : ' + param.seriesIndex;
				   mes += '  dataIndex : ' + param.dataIndex;
				}
				if (param.type == 'click') {
				   var value=param.data["name"];
				   eval(fn+"(value,datas,loc)");
				}
			}
			// 为echarts对象加载数据 
			myChart.setOption(option); 
			myChart.on(ecConfig.EVENT.CLICK, eConsole);
			//myChart.on(ecConfig.EVENT.HOVER, eConsole);
			myChart.on(ecConfig.EVENT.DATA_ZOOM, eConsole);
			myChart.on(ecConfig.EVENT.LEGEND_SELECTED, eConsole);
			myChart.on(ecConfig.EVENT.MAGIC_TYPE_CHANGED, eConsole);
			myChart.on(ecConfig.EVENT.DATA_VIEW_CHANGED, eConsole);
		}); 		
     }
  </script>
  <body>
    <div id="show_sex"> 
    	<dt class="dt">性别比例</dt>
    	<div id="sex">		
    	</div>
    	
    	<div id="sex_operator">		
    	</div>
    	<div id="sex_mobile">		
    	</div>
    	<div id="sex_dis">		
    	</div>
    </div>
     <div id="show_age"> 
    	<dt class="dt">年龄分布</dt>
    	<div id="age">		
    	</div>
    	
    	<div id="age_operator">		
    	</div>
    	<div id="age_mobile">		
    	</div>
    </div>
    <div id="show_dis"> 
    	<dt class="dt">地域分布</dt>
    	<div id="dis">		
    	</div>
    	
    	<div id="dis_operator">		
    	</div>
    	<div id="dis_mobile">		
    	</div>
    </div>
  </body>
</html>
