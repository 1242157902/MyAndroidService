$(function(){$('#date').datebox('setValue', '21/4/2016');})
$('#srbtn').click(function(){
	var sex=$('#sex').combotree('getValues')+"";
	var operator=$('#provi').combotree('getValues')+""; 
	var date=$('#date').datebox('getValue');
	$.ajax({
		type:"post",
		url: "servlet/UserProfileServlet?method=GetUsersHabit" ,
	    data: {sex:sex,operator:operator,date:date} ,
	    success:function(data) { 
		    if(data=="null"){
		    	alert("没有数据");
		    }else{
		    	var obj=eval(data);
		    	dealDate(obj); 
		    }          
	     },    
	     error : function() {    
	          // view("异常！");    
	          alert("异常！");    
	     }  
	});
	$.ajax({
		type:"post",
		url: "servlet/UserProfileServlet?method=GetUsersUseHabit" ,
	    data: {sex:sex,operator:operator,date:date} ,
	    success:function(data) { 
		    if(data=="null"){
		    	alert("没有"+phone_num+"在"+date+"的数据");
		    }else{
		    	var obj=eval(data); 
		    	dealUseData(obj); 
		    }          
	     },    
	     error : function() {    
	          // view("异常！");    
	          alert("异常！");    
	     }  
	});
});
//处理后台传来的jaon，后台为所有符合条件的用户信息
function dealDate(data){
	var hours=[0,0,0,0,0,0,0,0,0,0,0,0];
	var times=[0,0,0,0,0,0,0,0,0,0,0,0];//次数超过2次  10次
	var total=[0,0,0,0,0,0];//总人数 
	for(var i=0,len=data.length;i<len;i++){
		var id=data[i].time_id;
		var num=data[i].nums;
		console.log(id);
		hours[id-1]++;
		if(id==1||id==2||id==3){
			if(num>=5)
				times[id-1]++;
		}else{
			if(num>=40){
				times[id-1]++;
			}
		} 
		 
		/*if(id==1||id==2||id==3){
			total[0]++;
			if(num>=2)
				times[0]++;
			
		}else if(id==4||id==5||id==6){
			total[1]++;
			if(num>=20){
				times[1]++;
			}
		}else if(id==7){
			total[2]++;
			if(num>=20)
				times[2]++;
		}else if(id==8||id==9){
			total[3]++;
			if(num>=20)
				times[3]++;
		}else if(id==10||id==11){
			total[4]++;
			if(num>20)
			times[4]++;
		}else if(id==12){
			total[5]++;
			if(num>=20)
			times[5]++;
		}*/
			
	}
//	console.log(hours);
/*	for(var i=0;i<12;i++){
		times[i]=times[i]/hours[i];
	}*/
	var hx=['0点-2点','2点-4点','4点-6点','6点-8点','8点-10点','10点-12点','12点-14点','14点-16点','16点-18点','18点-20点','20点-22点','22点-24点'];
	var tx=['半夜','上午','中午','下午','晚上','碎觉'];
	show_line(hours,'',hx,'line_show');
	show_line(times,'',hx,'time_show');
}
/**
 * 处理传递过来的每天用户使用手机时长及总次数
 * @returns
 */
function dealUseData(obj){
	
	var days=0;
	var dates=[];
	var dura=[];
	var times=[];
	for(var i=0,len=obj.length;i<len;i++){
		days++;
		dates.push(obj[i].date);
		var num=obj[i].num;
		var dur=obj[i].dura_time/num;
		dura.push(dur);
		var time=obj[i].times/num;
		times.push(time);
	}
	show_line(dura,times,dates,'day_show')
}
function show_line(data,data2,xaxis,loc){
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
            'echarts/chart/line', // 使用柱状图就加载bar模块，按需加载
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
    function (ec) {
      // 基于准备好的dom，初始化echarts图表 
      var myChart = ec.init(document.getElementById(loc)); 
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
		xAxis : [{
			type : 'category',
			boundaryGap : false,
			data : xaxis
		}],
		yAxis : [{
			type : 'value'
		}],
		series : [{
			name:'数',
			type:'line', 
			data:data,
			markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
		},{
			name:'人数',
			type:'line', 
			data:data2,
			markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
		}]
	};
      // 为echarts对象加载数据 
     myChart.setOption(option); 
    });
}