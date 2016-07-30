<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>按周统计积分</title>

		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js"></script>
		
		<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/themes/icon.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>	
	
		<script type="text/javascript">

		$(function () {
    $('#container').highcharts({
        chart: {
            plotBackgroundColor: null,
            plotBorderWidth: 1,//null,
            plotShadow: false
        },
        title: {
            text: '各个分数段比例图'
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
                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
                    style: {
                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
                    }
                }
            }
        },
        series: [{
            type: 'pie',
            name: '所占比例:',
            data: [
                ${requestScope.str}
            ]
        }]
    });
});


		</script>
	</head>
	<body>
	
<script src="${pageContext.request.contextPath}/js/chart/chart.js"></script>
<script src="${pageContext.request.contextPath}/js/chart/export.js"></script>


				<div style="margin:20px auto;">
         <form  method="post" action="${pageContext.request.contextPath}/servlet/MbServlet?method=Scorepie">
					
					开始时间:<input  name="begintime"
						class="easyui-datebox" editable="false" value="" style="width:180px;" />
					结束时间: <input  name="endtime"
						class="easyui-datebox" editable="false" value="" style="width:180px;" />
					
					
					<input type="submit" value="确定" class="easyui-linkbutton" />
					<input type="reset" value="取消" class="easyui-linkbutton" />
				
			</form>
</div>

总人数为：${requestScope.count}人

<div id="container" style="min-width: 500px; height: 600px; margin: 0 auto"></div>

	</body>
</html>
