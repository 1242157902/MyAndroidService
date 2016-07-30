<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<base href="<%=basePath%>">
	<title>mobileinfo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
	<script type="text/javascript" src="js/chart/jquery-1.8.3.min.js"></script>
	<script type="text/javascript" src="js/chart/chart.js"></script>
	<script type="text/javascript" src="js/chart/export.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
	<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript">   
    function statis_mbinfo(value){  
       var staregstart=$('#mbinfo_regstart').datebox('getValue'); 
       var staregend=$('#mbinfo_regend').datebox('getValue'); 
       var staxunit=$('#mbinfo_xunit').combobox('getValue');
       var statjtj=$('#mbinfo_tjtj').combobox('getValue');
       var statjxx=$('#mbinfo_tjxx').combotree('getValues');
       var statjgz=$('#mbinfo_tjgz').combobox('getValue');
       if( staregstart> staregend) {
          alert('开始日期不能大于结束日期！');return;
       }         
       if(staxunit=='day'&&getDays(staregstart,staregend)>31) {
          alert('按日显示，开始日期与结束日期需小于等于31天！');return;
       }
       if(staxunit=='month'&&getDays(staregstart,staregend)>365) {
          alert('按月显示，开始日期与结束日期需小于等于12个月！');return;
       }
      /* alert("staregstart:"+staregstart +" staregend:"+staregend+" staxunit:"+staxunit+" statjtj:"+statjtj+" statjxx:"+statjxx+" statjgz:"+statjgz);
		$.post('pages/stats/data/statis_mbinfo_tjgz_provider.json',{a:'s'},function(data)  { 
		alert(data);
		xy=data;
		if(value=='column') showColumnChart(xy);
		if(value=='pie') showPieChart();
		if(value=='line') showLineChart();
		},"json");*/
		$.post("${pageContext.request.contextPath }/servlet/StServlet?method=GetStatsData",{start:staregstart,end:staregend,xunit:staxunit,tjtj:statjtj,show:statjgz,tjxx:""+statjxx+""},function(data,status){		    
		    if(value=='column') showColumnChart(data.yy,data.xx);
		    if(value=='pie') showPieChart();
		    if(value=='line') showLineChart(data.yy,data.xx);
		    if(value=='line1') showLineChart(data.yy,data.xx);
		},"json");
	}	
    </script>
</head>
<body>
	<div id="mbinfo_lay" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:70,title:'查询条件'">
			<form id="mbinfo_form" method="post">
				<div style="padding-top:5px;padding-bottom:5px;"> 
				   开始日期<input id="mbinfo_regstart" name="mbinfo_regstart"  class="easyui-datebox" editable="false" style="width:180px;" />
				   结束日期<input id="mbinfo_regend" name="mbinfo_regend" 	class="easyui-datebox" editable="false" style="width:180px;" />
				  横坐标<select id="mbinfo_xunit" name="mbinfo_xunit"  style="width:120px"></select>
				  统计条件<select id="mbinfo_tjtj" name="mbinfo_tjtj"  style="width:120px"></select>
				 <span  id="tjxxspan">
				  条件选项<select id="mbinfo_tjxx" name="mbinfo_tjxx"	class="easyui-combotree" style="width:170px;" data-options="multiple:true,panelHeight:170"></select>
			     </span>
				  统计规则<select id="mbinfo_tjgz" name="mbinfo_tjgz"  style="width:120px"></select>			           
				 <span style="padding-left:20px;"></span><a id="mbinfo_srhbtn"	class="easyui-linkbutton" icon="icon-search" onclick="statis_mbinfo($('#mbinfo_tjgz').combobox('getValue'))">&nbsp;统计&nbsp;&nbsp;&nbsp;</a>
				</div>
			</form>
		</div>
		<div data-options="region:'center'" style="height:100%;">
		 <div id="container" style="width:100%;height:100%"></div>
		</div>
	</div>
<script>
	$(function(){	 
	   $('#mbinfo_regstart').datebox('setValue',  formatterDate(new Date()));
	   $('#mbinfo_regend').datebox('setValue', formatterDate(new Date()));	
		var xunit = [
			{value:'day',text:'按日显示'},
			{value:'month',text:'按月显示'},
			{value:'year',text:'按年显示'}
		];
		$('#mbinfo_xunit').combobox({
			data:xunit,
			editable:false,
			panelHeight:'auto',
			onChange:onChangeNone,
			onLoadSuccess:function(){
				$(this).combobox('setValue', 'day');
			}
		});
		var atjtj = [
			{value:'regdate',text:'按注册时间'},
			{value:'age',text:'按年龄段'},
			{value:'provider',text:'按运营商'},
			{value:'brand',text:'按手机品牌'},
			{value:'area',text:'按归属地'},
			{value:'unit',text:'按所属单位'}
		];		
		$('#mbinfo_tjtj').combobox({
			data: atjtj,
			editable:false,
			panelHeight:'auto',
			onChange:onChangeTjtj,
			onLoadSuccess:function(){
				$(this).combobox('setValue', 'regdate');
			}
		});
	});
	function inittjgz(value)	{
		var tjgz;
		if(value=='time') tjgz = [
			{value:'column',text:'直方图'},
			{value:'line',text:'折线图'},
			{value:'line1',text:'折线图1'}
		];
		else tjgz = [
			{value:'column',text:'直方图'},
			{value:'pie',text:'饼图'},
			{value:'line',text:'折线图'},
			{value:'line1',text:'折线图1'}
		];
		$('#mbinfo_tjgz').combobox({
			data:tjgz,
			editable:false,
			panelHeight:'auto',
			onChange:onChangeNone,
			onLoadSuccess:function(){
				$(this).combobox('setValue', 'column');
			}
		});
	}
	function onChangeTjtj(value){
		if(value=='regdate')  {
		   $("#tjxxspan").hide();
		   inittjgz('time');
		}
		else if(value=='age'){
		   $("#tjxxspan").show();
		   inittjgz('all');
		   $('#mbinfo_tjxx').combotree({
			 url:'pages/stats/data/statis_mbinfo_tjxx_age.json',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='provider'){
		   inittjgz('all');
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'pages/stats/data/statis_mbinfo_tjxx_provider.json',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='brand'){
		   inittjgz('all');
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'servlet/JsonServlet?method=GetBrandList',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='area'){
		   inittjgz('all');
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'servlet/JsonServlet?method=GetAreaList',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='unit'){
		   inittjgz('all');
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'servlet/JsonServlet?method=GetCompanyList',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else {}
	}
	function onChangeNone(value){
	}
	function formatterDate(date) {
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
		return date.getFullYear() + '-' + month + '-' + day;
	}
  function getDays(strDateStart,strDateEnd){
   var strSeparator = "-"; //日期分隔符
   var oDate1;
   var oDate2;
   var iDays;
   oDate1= strDateStart.split(strSeparator);
   oDate2= strDateEnd.split(strSeparator);
   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24);//把相差的毫秒数转换为天数 
   return iDays ;
}  
	
</script>

 <script type="text/javascript"> 
 function showLineChart(vvv,xxx){
  $('#container').highcharts({
	       title: {
	           text: '设备信息统计',
	           x: -20 //center
	       },
	       subtitle: {
	           text: '来源:ncut',
	           x: -20
	       },
	       xAxis: {
	           categories: eval(xxx),	           
	           labels: {
                  rotation: -45,
                  align: 'right',
                  style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif'
                  }
               }
	       },
	       yAxis: {
	           min:0, // 定义最小值 
	           allowDecimals:false,
               minPadding:1,  
	           title: {
	               text: '设备数量 (个)'
	           },
	           plotLines: [{
	               value: 0,
	               width: 1,
	               color: '#808080'
	           }]
	       },	       
	       tooltip: {
	           valueSuffix: '个'
	       },
	       legend: {
	           layout: 'vertical',
	           align: 'right',
	           verticalAlign: 'middle',
	           borderWidth: 0
	       },
	       series:eval(vvv)
	   });
 } 
 
 function showColumnChart(vvv,xxx){
  $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '设备信息统计'
        },
        subtitle: {
            text: '来源:ncut'
        },
        xAxis: {
            categories: eval(xxx),	           
            labels: {
                 rotation: -45,
                 align: 'right',
                 style: {
                   fontSize: '13px',
                   fontFamily: 'Verdana, sans-serif'
                 }
             }
        },
        yAxis: {
            min: 0,
            minPadding:1, 
	        allowDecimals:false,
            title: {
               text: '设备数量 (个)'
            },
            plotLines: [{
	               value: 0,
	               width: 1,
	               color: '#808080'
	        }]
        },        
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table style="width:100px;"">',
            pointFormat: '<tr><td style="color:{series.color};font-size:12px;">{series.name}: </td>' +
                '<td style="font-size:12px;"><b>{point.y}个</b></td></tr>',
            footerFormat: '</table>',
            shared: true,
            useHTML: true
        },
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: eval(vvv)  
    });
 }
 
 function showPieChart(){
  $('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '设备信息统计'
        },
        tooltip: {
    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                dataLabels: {
                    enabled: true,
                    color: '#000000',
                    connectorColor: '#000000',
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['23岁-35岁',   45.0],
                ['18岁-22岁',       26.8],
                {
                    name: '18岁以下',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                ['36岁-55岁',    8.5],
                ['55岁以上',     6.9]
            ]
        }]
    });
    }
 </script>
</body>
</html>
