<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'CountByPhone.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
   <script type="text/javascript" src="js/jquery-easyui-1.2.6/chart/jquery-1.8.3.min.js"></script>
   <script type="text/javascript" src="js/jquery-easyui-1.2.6/chart/chart.js"></script>
   <script type="text/javascript" src="js/jquery-easyui-1.2.6/chart/export.js"></script>
   <script type="text/javascript" src="js/Calendar/WdatePicker.js"></script>
   
   
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.2.6/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.2.6/themes/icon.css" />
	<script type="text/javascript" src="js/jquery-easyui-1.2.6/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="js/jquery-easyui-1.2.6/locale/easyui-lang-zh_CN.js"></script>
   
   
   
   
   
   <script>
    var phoneType;
    var phoneCount= new Array();
    $(function () {
    var temp=$("#phonecount").val().split(",");
    phoneType=$("#phonetype").val().split(",");
    for(var i=0;i<temp.length;i++) {phoneCount.push(eval(temp[i]));}    
    x();
    });
    
    function x() {                                                                   
    $('#container').highcharts({                                           
        chart: {                                                           
            type: 'bar',
            margin: [ 50, 50, 100, 80]                                                   
        },                                                                 
        title: {                                                           
            text: '滑屏数据统计(按手机型号)'                    
        },                                                                 
        xAxis: {                                                           
            categories: phoneType,
            title: {                                                       
                text: null                                                 
            }                                                              
        },                                                                 
        yAxis: {                                                           
            min: 0,
            allowDecimals:false,                                                        
            title: {                                                       
                text: '滑屏次数 (次)',                             
                align: 'high'                                              
            },                                                             
            labels: {                                                      
                overflow: 'justify'                                        
            }                                                              
        },                                                                 
        tooltip: {                                                         
             pointFormat:  '<b>{point.y} 次</b>'                                
        },                                                                 
        plotOptions: {                                                     
            bar: {                                                         
                pointPadding: 0,
                borderWidth: 0                                                   
            }                                                              
        },                                                                 
        legend: {                                                          
            enabled: false                                                
        },                                                                  
        series: [{                                                         
            name: ' ',                                             
            data: phoneCount   
        }]                                                                 
    });  }  
  </script>
</head> 
<body>
  <form action="${pageContext.request.contextPath }/servlet/PhoneServlet?method=getPhoneCount" method="post">
  <input id="phonetype" name="phonetype" type="hidden" value="${phonetype}"/>
  <input id="phonecount" name="phonecount" type="hidden" value="${phonecount}"/>
     开始日期&nbsp<input id="startdate" name="startdate"  class="easyui-datebox" editable="false" style="width:160px;"  value=""/>
  &nbsp&nbsp
      结束日期&nbsp<input id="enddate" name="enddate"class="easyui-datebox" editable="false" style="width:160px;"  value=""/>
  &nbsp&nbsp<input id="sumit" type="submit" value="确定" />
  </form>
  <div id="container" style="min-width:700px;height:400px"></div>
</body>
</html>
