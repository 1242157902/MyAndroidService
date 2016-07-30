<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<head>
    <base href="<%=basePath%>">
    
    <title>hehe</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
   <script type="text/javascript" src="js/chart/jquery-1.8.3.min.js"></script>
   <script type="text/javascript" src="js/chart/chart.js"></script>
   <script type="text/javascript" src="js/chart/export.js"></script>
   <script type="text/javascript" src="js/Calendar/WdatePicker.js"></script>
   
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>
   
   
  <script>
   
   /*$.ajax({
   type: "GET",   
   url: "servlet/PhoneServlet?method=getTimeCount",
   data: {total:$("#total").val()}, 
   dataType: "json",
   success: function(data){
    alert(data.total);
   }
   });
   
    var myDate = new Date();
    $.get("servlet/PhoneServlet?method=getTimeCount",
      function(data,status){   
      var temp=data.split(","); alert(data);
      for(var i=0;i<temp.length;i++) {timeCount.push(eval(temp[i]));}
      xx();
      });  });
      */
    var timeCount= new Array();
    $(function () {
    var temp=$("#timecount").val().split(",");
    for(var i=0;i<temp.length;i++) {timeCount.push(eval(temp[i]));}    
    xx();
    });
   function xx() {
    $('#container').highcharts({
        chart: {
            type: 'column',
            margin: [ 50, 50, 100, 80]
        },
        title: {
            text: '滑屏数据统计(按时间段)'
        },
        xAxis: {
            categories: [
                '00:00-01:00',
                '01:00-02:00',
                '02:00-03:00',
                '03:00-04:00',
                '04:00-05:00',
                '05:00-06:00',
                '06:00-07:00',
                '07:00-08:00',
                '08:00-09:00',
                '09:00-10:00',
                '10:00-11:00',
                '11:00-12:00',
                '12:00-13:00',
                '13:00-14:00',
                '14:00-15:00',
                '15:00-16:00',
                '16:00-17:00',
                '17:00-18:00',
                '18:00-19:00',
                '19:00-20:00',
                '20:00-21:00',
                '21:00-22:00',
                '22:00-23:00',
                '23:00-00:00'
            ],
            labels: {
                rotation: 0,
                align: 'center',
                style: {
                    fontSize: '10px',
                    fontFamily: 'Verdana, sans-serif'
                }
            }
        },
        yAxis: {
            min: 0,
            allowDecimals:false,
            title: {
                text: '滑屏次数 (次)'
            }
        },
        legend: {
            enabled: false
        },
        tooltip: {
            pointFormat:  '<b>{point.y} 次</b>'
        }, 
        plotOptions: {
            column: {
                pointPadding: 0,
                borderWidth: 0
            }
        },
        series: [{
            name: '',
            data: timeCount,
            dataLabels: {
                enabled: true,
                rotation: -90,
                color: '#FFFFFF',
                align: 'right',
                x: 4,
                y: 10,
                style: {
                    fontSize: '13px',
                    fontFamily: 'Verdana, sans-serif',
                    textShadow: '0 0 3px black'
                }
            }
        }]
    });
    }	

 function timeNull() {
  if($("#startdate").val()==""||$("#enddate").val()=="") 
  {alert("请输入起始日期");return false;}
 }
</script>
</head>
<body>
  <form action="${pageContext.request.contextPath }/servlet/PhoneServlet?method=getTimeCount" method="post">
  <input id="timecount" name="timecount" type="hidden" value="${timecount}"/>
     开始日期&nbsp<input id="startdate" name="startdate"  class="easyui-datebox" editable="false" style="width:160px;"  value=""/>
  &nbsp&nbsp
      结束日期&nbsp<input id="enddate" name="enddate"  class="easyui-datebox" editable="false" style="width:160px;"  value=""/>
  &nbsp&nbsp<input id="sumit" type="submit" value="确定"/>
  </form>
  <div id="container" style="min-width:700px;height:400px"></div>   
</body>
</html>