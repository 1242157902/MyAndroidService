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
       if( staregstart> staregend) {
          alert('开始日期不能大于结束日期！');return;
       }         
       if(staxunit=='day'&&getDays(staregstart,staregend)>31) {
          alert('按日显示，开始日期与结束日期需小于等于31天！');return;
       }
       if(staxunit=='month'&&getDays(staregstart,staregend)>365) {
          alert('按月显示，开始日期与结束日期需小于等于12个月！');return;
       } 
		$.post("${pageContext.request.contextPath }/servlet/StServlet?method=GetChart5Data",{
		start:staregstart,
		end:staregend,
		xunit:staxunit,
		tjtj:statjtj,
		mbinfo_num:$('#mbinfo_num').val(),
		mbinfo_sex:$('#mbinfo_sex').combotree('getValues')+"",
		mbinfo_age:$('#mbinfo_age').combotree('getValues')+"",
		mbinfo_company:$('#mbinfo_company').combotree('getValues')+"",
		mbinfo_seller:$('#mbinfo_seller').combotree('getValues')+"",
		mbinfo_brand:$('#mbinfo_brand').combotree('getValues')+"",
		mbinfo_provi:$('#mbinfo_provi').combotree('getValues')+"",
		mbinfo_area:$('#mbinfo_area').combotree('getValues')+""
		},function(data,status){		    
		  showLineChart(data.yy,data.xx);
		},"json");
	}	
    </script>
</head>
<body>
	<div id="mbinfo_lay" class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',height:150,title:'查询条件'">
			<form id="mbinfo_form" method="post">
				<div style="padding-top:5px;padding-bottom:5px;"> 
				手机号码<input id="mbinfo_num" name="mbinfo_num" type="text" style="width:140px;height:22px;" class="textbox" value="" /> 
				性 别<select id="mbinfo_sex" name="mbinfo_sex" class="easyui-combotree"	style="width:100px;"
					data-options="url:'pages/push/data/gender.json',multiple:true,panelHeight:75"></select>
				年 龄<select id="mbinfo_age" name="mbinfo_age" class="easyui-combotree" style="width:170px;"
					data-options="url:'pages/push/data/age.json',multiple:true,panelHeight:170"></select>
				所属单位<select id="mbinfo_company" name="mbinfo_company" class="easyui-combotree" style="width:170px;"
					data-options="url:'servlet/JsonServlet?method=GetCompanyList',multiple:true,panelHeight:200"></select> 
				销售商<select id="mbinfo_seller" name="mbinfo_seller" class="easyui-combotree" style="width:170px;"
					data-options="url:'servlet/JsonServlet?method=GetCompanyList',multiple:true,panelHeight:200"></select> 
				</div>
				<div style="padding-top:5px;padding-bottom:5px;">
				手机品牌<select id="mbinfo_brand" name="mbinfo_brand" class="easyui-combotree" style="width:170px;"
						data-options="url:'servlet/JsonServlet?method=GetBigBrandList',multiple:true,panelHeight:200"></select>
				运营商<select id="mbinfo_provi" name="mbinfo_provi" class="easyui-combotree" style="width:170px;"
					data-options="url:'pages/push/data/provider.json',multiple:true,panelHeight:100"></select>
				归属地<select id="mbinfo_area" name="mbinfo_area" class="easyui-combotree" style="width:170px;"
					data-options="url:'servlet/JsonServlet?method=GetBigAreaList',multiple:true,panelHeight:200"></select>
				</div>
				<div style="padding-top:5px;padding-bottom:5px;">
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
function showLineChart(vvv,xxx){
var chart6title='滑屏次数';
var chart6unit='次';
if($('#mbinfo_tjtj').combobox('getValue')=='score') {chart6title='滑屏积分';chart6unit='分';}
  $('#container').highcharts({
	       title: {
	           text: '活动信息折线分布图',
	           x: -20 //center
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
	               text: chart6title+'('+chart6unit+')'
	           },
	           plotLines: [{
	               value: 0,
	               width: 1,
	               color: '#808080'
	           }]
	       },	       
	       tooltip: {
	           valueSuffix: chart6unit
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
</script>
</body>
</html>
