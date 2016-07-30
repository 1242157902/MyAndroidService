<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>deviceinfobypie</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<script type="text/javascript" src="<%=basePath%>/js/jquery-easyui-1.2.6/chart/jquery-1.8.3.min.js"></script>
	<script src="<%=basePath%>/js/jquery-easyui-1.2.6/chart/chart.js"></script>
    <script src="<%=basePath%>/js/jquery-easyui-1.2.6/chart/export.js"></script>	
		
		<style type="text/css">
              ${demo.css}
		</style>
		<script type="text/javascript">
$(function () {

    $(document).ready(function () {

        // Build the chart
        $('#container').highcharts({
            chart: {
                plotBackgroundColor: null,
                plotBorderWidth: null,
                plotShadow: false
            },
            title: {
                text: '设备类型比例图'
            },
            tooltip: {
                pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
            },
            plotOptions: {
                pie: {
                    allowPointSelect: true,
                    cursor: 'pointer',
                    dataLabels: {
                        enabled: false
                    },
                    showInLegend: true
                }
            },
            series: [{
                type: 'pie',
                name: '所占比例',
                data: [
                    /* ['Firefox',   45.0],
                    ['IE',       26.8],
                    {
                        name: 'Chrome',
                        y: 12.8,
                        sliced: true,
                        selected: true
                    },
                    ['Safari',    8.5],
                    ['Opera',     6.2],
                    ['Others',   0.7] */
                    
                    ${requestScope.devicetypes}
                    
                ]
            }]
        });
    });

});
		</script>
	
	
	
	
 </head>
  
  <body>
   

<div id="container" style="min-width: 510px; height: 600px; max-width: 1000px; margin: 0 auto"></div>
  
  
  </body>
</html>
