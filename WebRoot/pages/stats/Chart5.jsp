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
    function statis_mbinfo(){  
       var staregstart=$('#mbinfo_regstart').datebox('getValue'); 
       var staregend=$('#mbinfo_regend').datebox('getValue'); 
       var staxunit=$('#mbinfo_xunit').combobox('getValue');
       var statjtj=$('#mbinfo_tjtj').combobox('getValue');
       var phonenum=$('#mbinfo_num').val();
       if( staregstart> staregend) {
          alert('开始日期不能大于结束日期！');return;
       }         
       if(staxunit=='day'&&getDays(staregstart,staregend)>31) {
          alert('按日显示，开始日期与结束日期需小于等于31天！');return;
       }
       if(staxunit=='month'&&getDays(staregstart,staregend)>365) {
          alert('按月显示，开始日期与结束日期需小于等于12个月！');return;
       }    
		$.post("${pageContext.request.contextPath }/servlet/StServlet?method=GetChart4Data",{mbnum:phonenum,start:staregstart,end:staregend,xunit:staxunit,tjtj:statjtj},function(data,status){		    
		  showColumnChart(data.yy,data.xx);
		},"json");
	}	
    </script>
</head>
<body>
	<div id="mbinfo_lay" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:70,title:'查询条件'">
			<form id="mbinfo_form" method="post">
				<div style="padding-top:5px;padding-bottom:5px;"> 
				   手机号码<input id="mbinfo_num" name="mbinfo_num" type="text" style="width:140px;height:22px;" class="textbox" value="" /> 
				   开始日期<input id="mbinfo_regstart" name="mbinfo_regstart"  class="easyui-datebox" editable="false" style="width:180px;" />
				   结束日期<input id="mbinfo_regend" name="mbinfo_regend" 	class="easyui-datebox" editable="false" style="width:180px;" />
				  横坐标<select id="mbinfo_xunit" name="mbinfo_xunit"  style="width:120px"></select>
				  统计条件<select id="mbinfo_tjtj" name="mbinfo_tjtj"  style="width:120px"></select>					           
				 <span style="padding-left:20px;"></span><a id="mbinfo_srhbtn"	class="easyui-linkbutton" icon="icon-search" onclick="statis_mbinfo()">&nbsp;统计&nbsp;&nbsp;&nbsp;</a>
				</div>
			</form>
		</div>
		<div data-options="region:'center'" style="height:100%;">
		 <div id="container" style="width:100%;height:100%"></div>
		</div>
	</div>
<script>
	$(function(){	
		var xunit = [
			{value:'day',text:'按日显示'},
			{value:'month',text:'按月显示'},
			{value:'year',text:'按年显示'}
		];		
		var atjtj = [
			{value:'count',text:'统计次数'},
			{value:'score',text:'统计积分'}
		];
		 
	   $('#mbinfo_regstart').datebox('setValue',  formatterDate(new Date()));
	   $('#mbinfo_regend').datebox('setValue', formatterDate(new Date()));		
	   $('#mbinfo_xunit').combobox({
			data:xunit,
			editable:false,
			panelHeight:'auto',
			onLoadSuccess:function(){
				$(this).combobox('setValue', 'day');
			}
		});		
		$('#mbinfo_tjtj').combobox({
			data: atjtj,
			editable:false,
			panelHeight:'auto',
			onLoadSuccess:function(){
				$(this).combobox('setValue', 'count');
			}
		});
	});	
	
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
 function showColumnChart(vvv,xxx){
 var chart5title='滑屏次数';
var chart5unit='次';
if($('#mbinfo_tjtj').combobox('getValue')=='score') {chart5title='滑屏积分';chart5unit='分';}
  $('#container').highcharts({
        chart: {
            type: 'column'
        },
        title: {
            text: '个人活动信息直方分布图'
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
               text:chart5title+'('+chart5unit+')'
            },
            plotLines: [{
	               value: 0,
	               width: 1,
	               color: '#808080'
	        }]
        },        
        tooltip: {
            headerFormat: '<span style="font-size:10px">{point.key}</span><table style="width:110px;"">',
            pointFormat: '<tr><td style="color:{series.color};font-size:12px;">{series.name}: </td>' +
                '<td style="font-size:12px;"><b>{point.y}'+chart5unit+'</b></td></tr>',
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
</script>
</body>
</html>
