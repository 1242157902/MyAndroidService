<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title></title>
  </head>
  <body>
    <div id="pushdefaultlistlay" class="easyui-layout" style="width: 100%;height:100%" >
     <div region="center" >
		<table id="pushdefaultlist"></table>
     </div>
    </div>
    <div id="stuffsale_tb">
      <span>公司:</span><select  id="com"  style="width:160px; border: 1px solid #ccc"> </select>&emsp; 
      <span>部门:</span><select  id="dept" style="width:160px; border: 1px solid #ccc"> </select>&emsp; 
     <a id="statisbtn" class="easyui-linkbutton"  icon="icon-edit" onclick="statisale()">统计</a>
     </div>
    <script type="text/javascript">
    $(function(){	
		$('#com').combobox({ 
		     url:'servlet/StServlet?method=GetComNameList', 
		     editable:false, //不可编辑状态
		     cache: false,
		     valueField:'id',   
		     textField:'name',
		     onHidePanel: function(){
		       $("#dept").combobox("setValue",'');
		       var comid = $('#com').combobox('getValue');		
		       $.ajax({
		         type: "POST",
		         url: "servlet/StServlet?method=GetDeptNameList&comid="+comid,
		         cache: false,
		         dataType : "json",
		         success: function(data){
		          $("#dept").combobox("loadData",data);
		         }
		       }); 	 
		     }
		}); 
		
		$('#dept').combobox({ 
		     //url:'itemManage!categorytbl', 
		     editable:false, //不可编辑状态
		     cache: false,
		     valueField:'id',   
		     textField:'name'
		     //onHidePanel: function(){alert( $('#com').combobox('getValue')+ $('#dept').combobox('getValue'));	}
		 }); 
		 
		 $('#pushdefaultlist').datagrid({
			idField:'emp_no',	
			fit:true,
			height:450 ,
			url:'servlet/StServlet?method=GetStuffList',
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[			
				{
					field:'emp_no' ,
					title:'编号' ,
					width:150, 
					height:50,
					hidden:true
				},	
				{
					field:'emp_id' ,
					title:'工号' ,
					width:150, 
					height:50
				},			
				{
					field:'emp_name' ,
					title:'姓名' ,
					width:150, 
					height:50
				},	
				{
					field:'com_name' ,
					title:'公司' ,
					width:150, 
					height:50
				},	
				{
					field:'depart_name' ,
					title:'部门' ,
					width:150, 
					height:50
				},			
				{
					field:'emp_duty' ,
					title:'职位' ,
					width:150, 
					height:50
				},		
				{
					field:'emp_sale',
					title:'业绩',
					width:100 ,
					height:50,
					formatter: function(value,row,index){
			    					return "<a href=\"javascript:\" onclick=\"EditDefaultQue('"+index+"')\" style=\"font:12px;\">"+value+"</a>"; 
			 				}   
				}
				]] ,
			toolbar: '#stuffsale_tb',
			pagination: true , 
			pageSize: 20,
			pageList:[5,10,15,20,50]
		});
    });
   function statisale()
   {
   var comid=$('#com').combobox('getValue');
   var deptid= $('#dept').combobox('getValue');
    $('#pushdefaultlist').datagrid({url:'servlet/StServlet?method=GetStuffList&comid='+comid+'&deptid='+deptid});
   }
	</script>
  </body>
  </body>
</html>
		