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

<title>Org2</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="js/org/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="js/org/jquery.jqprint-0.3.js"></script>
<script type="text/javascript" src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"  src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="js/org/org2.js"></script>

<script type="text/javascript">
$(function() {
	$.getJSON("OrgServlet?method=getorgarea", function(data) {
		$("#orgarea").html("");// 清空内容

		$.each(data.orgareas, function(i, item) {

			$("#orgarea").append(
					"<option value='" + item.orgarea + "'>" + item.orgarea
							+ "</option>");
		});
	});
	
	

  $.extend($.fn.validatebox.defaults.rules,{  
               mobile:{  
                   validator:function(value,param){  
                       return /^1[3|5|7|8|][0-9]{9}$/.test(value);  
                   },  
                   message:'请输入正确的11位手机号码.'  
               }
           });  



	
	
	

});

function showregion(){

	var orgarea = $("#orgarea").val();
	var encodeoragera = encodeURI(orgarea);

	$.getJSON("OrgServlet?method=getorgregions&orgarea=" + encodeoragera,
			function(data) {
				$("#orgregion").html("");// 清空内容

				$.each(data.orgregions, function(i, item) {

					$("#orgregion").append(
							"<option value='" + item.orgregion + "'>"
									+ item.orgregion + "</option>");
				});
			});

};



 </script>

</head>

<body style="margin:0px;">
	<div id="lay" class="easyui-layout" style="width: 100%;height:600px">
		<div region="north" title="机构查询" collapsed=true style="height:80px;">
			<form id="mysearch" method="post">
			
			<br/>
				
				&nbsp;&nbsp;&nbsp;机构名称:&nbsp;&nbsp;&nbsp;<input type="text" name="searchorgname" id="searchorgname"
						style="width:120px;height:22px;" class="textbox">

				
				机构身份证号:&nbsp;&nbsp;&nbsp;<input  type="text" name="searchorgidcard" id="searchorgidcard" value="" style="width:120px;height:22px;" class="textbox"/>
				
				<a id="searchbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <a id="clearbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
				  
				  
				  
			</form>

		</div>
		<div region="center">
			<table id="t_user"></table>
		</div>
	</div>

	<div id="mydialog" title="新增机构" modal=true draggable=false
		class="easyui-dialog" closed=true style="width:500px;">
		<form id="myform" method="post">
		
		<input type="hidden" name="id" value="" /> <br />
		
				
				<table>
					<tr>
						<td align="right">新机构名称:</td>
						<td><input name="orgname" value=""
							style="height:25px;width:200px;" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td align="right">机构身份证号:</td>
						<td><input name="orgidcard" value=""
							style="height:25px;width:200px;" />
						</td>
					</tr>
					<tr>
						<td align="right">
						所属地区:
						
						</td>
						<td align="right">
						
						      <select id="orgarea" name="orgarea" style="width:100px;height:20px;" onchange="showregion()">
						
						
						      </select>
						
						所属区域:<select id="orgregion" name="orgregion" style="width:100px;height:20px;">
						
						      </select>
						</td>
					</tr>
					
					
					
					
					
					
					<tr>
						<td align="right">负责人:</td>
						<td><input name="orglinkmanname" value=""
							style="height:25px;width:200px;" class="easyui-validatebox" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<td align="right">联系方式:</td>
						<td><input name="orglinkmanphonenum" value=""
							style="height:25px;width:200px;" class="easyui-validatebox" data-options="required:true,validType:'mobile'" />
						</td>
					</tr>
					<tr>
						<td align="right">邮箱:</td>
						<td><input name="orglinkmanemail" value=""
							style="height:25px;width:200px;" class="easyui-validatebox" data-options="required:true,validType:'email'"/></td>
					</tr>
					<tr>
						<td align="right">机构地址:</td>
						<td><input name="orgaddress" value=""
							style="height:25px;width:300px;" class="easyui-validatebox" data-options="required:true" /></td>
					</tr>



					<tr>
						<td align="right">备注:</td>
						<td><textarea name="description" cols="30" rows="10"></textarea>
						</td>
					</tr>
					<tr align="center">
						<td colspan="2"><a id="btn1" class="easyui-linkbutton">确定</a>
							<a id="btn2" class="easyui-linkbutton">取消</a></td>
					</tr>
				</table>
			</form>
	</div>
	
	
	
	
	
	
	
	
	
</body>
</html>
