$(function(){$('#date').datebox('setValue', '21/4/2016');})
$('#srbtn').click(function(){
	var sex=$('#sex').combotree('getValues')+"";
	var operator=$('#provi').combotree('getValues')+""; 
	var date=$('#date').datebox('getValue');
	$.ajax({
		type:"post",
		url: "servlet/UserProfileServlet?method=GetUsersPic" ,
	    data: {sex:sex,operator:operator,date:date} ,
	    success:function(data) { 
		    if(data=="null"){
		    	alert("没有图片偏好数据的数据");
		    	$('#pic_show').html("");
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
		url: "servlet/UserProfileServlet?method=GetUsersUsersApp" ,
	    data: {sex:sex,operator:operator,date:date} ,
	    success:function(data) { 
		    if(data=="null"){
		    	alert("没有APP使用的数据");
		    	$('#app_show').html("");
		    }else{
		    	var obj=eval(data); 
		    	dealAppData(obj); 
		    }          
	     },    
	     error : function() {    
	          // view("异常！");    
	          alert("异常！");    
	     }  
	});
});
//处理传过来的app使用情况
function dealAppData(datas){
	 
	var apps={};
	for(var i=0,len=datas.length;i<len;i++){
		var classify=datas[i].classify;
		var nums=datas[i].nums;
		if(apps[classify]){
			apps[classify]+=1;
		}else{
			apps[classify]=1;
			
		}
	}
	var app=[];
	var legend=[];
	for(var a in apps){
		app.push({value:apps[a],name:a});
		legend.push(a);
		console.log(app);
	}
	show_pie(app,legend,'app_show');
}

function dealDate(datas) {
	var pics={};
	for(var i=0,len=datas.length;i<len;i++){
		var classify=datas[i].classify;
		var nums=datas[i].nums;
		if(pics[classify]){
			pics[classify]+=1;
		}else{
			pics[classify]=1;
		}
	}
	var pic=[];
	var legend=[];
	for(var a in pics){
		pic.push({value:pics[a],name:a});
		legend.push(a);
		console.log(pics);
	}
	show_pie(pic,legend,'pic_show');
}
function show_pie(data,legend,loc){
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
           
             option = {
		    title : { 
		        x:'center'
		    },
			 tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    legend: {
		        orient : 'vertical',
		        x : 'left', 
		        data:legend
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

            // 为echarts对象加载数据 
            myChart.setOption(option); 
        }
    );
 
}