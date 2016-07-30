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
       var statjtj=$('#mbinfo_tjtj').combobox('getValue');
       var statjxx=$('#mbinfo_tjxx').combotree('getValues');
       if( staregstart> staregend) {
          alert('开始日期不能大于结束日期！');return;
       }      
		$.post("${pageContext.request.contextPath }/servlet/StServlet?method=GetChart3Data",{start:staregstart,end:staregend,tjtj:statjtj,tjxx:""+statjxx+""},function(data,status){		    
		 showPieChart(data);
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
				  统计条件<select id="mbinfo_tjtj" name="mbinfo_tjtj"  style="width:120px"></select>
				 <span  id="tjxxspan">
				  条件选项<select id="mbinfo_tjxx" name="mbinfo_tjxx"	class="easyui-combotree" style="width:170px;" data-options="multiple:true,panelHeight:170"></select>
			     </span>		           
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
		var atjtj = [
			{value:'age',text:'按年龄段'},
			{value:'provider',text:'按运营商'},
			{value:'brand',text:'按手机品牌'},
			{value:'area',text:'按归属地'},
			{value:'seller',text:'按销售单位'},
			{value:'unit',text:'按所属单位'}
		];		 
	   $('#mbinfo_regstart').datebox('setValue',  formatterDate(new Date()));
	   $('#mbinfo_regend').datebox('setValue', formatterDate(new Date()));		  
	   $('#mbinfo_tjtj').combobox({
			data: atjtj,
			editable:false,
			panelHeight:'auto',
			onChange:onChangeTjtj,
			onLoadSuccess:function(){
				$(this).combobox('setValue', 'age');
			}
		});
	});		
	function onChangeTjtj(value){
		if(value=='regdate')  {
		   $("#tjxxspan").hide();
		}
		else if(value=='age'){
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'pages/stats/data/statis_mbinfo_tjxx_age.json',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='provider'){
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'pages/stats/data/statis_mbinfo_tjxx_provider.json',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='brand'){
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'servlet/JsonServlet?method=GetBigBrandList',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='area'){
		   $("#tjxxspan").show();
		   $('#mbinfo_tjxx').combotree({
			 url:'servlet/JsonServlet?method=GetBigAreaList',
			 onLoadSuccess: function (data) {
			   $('#mbinfo_tjxx').combotree('setValue','0');		
			  }		   
		   });
		}
		else if(value=='unit'||value=='seller' ){
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
	 function formatterDate(date) {
	   var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
	   var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0" + (date.getMonth() + 1);
	   return date.getFullYear() + '-' + month + '-' + day;
	 }
</script>
 <script type="text/javascript">  
 function showPieChart(chartdata){
  $('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: null,
            plotShadow: false
        },
        title: {
            text: '设备数量统计图'
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
            name: '占',
            data: eval(chartdata)
        }]
    });
    }
</script>
</body>
</html>
