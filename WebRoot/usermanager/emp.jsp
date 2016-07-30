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

<title>Emp</title>

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

<script type="text/javascript" src="js/org/emp.js"></script>

<script type="text/javascript">
   $(function() {

	$.getJSON("OrgServlet?method=getdepart", function(data) {
		$("#departname").html("");// 清空内容
		$("#departname2").html("");// 清空内容

		$.each(data.orgs, function(i, item) {

			$("#departname").append(
					"<option value='" + item.id + "-" + item.orgname + "'>"
							+ item.orgname + "</option>");
			$("#departname2").append(
					"<option value='" + item.orgname + "'>" + item.orgname
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

$(function() {
	$.getJSON("OrgServlet?method=getorgarea", function(data) {
		$("#orgarea").html("");// 清空内容

		$.each(data.orgareas, function(i, item) {

			$("#orgarea").append(
					"<option value='" + item.orgarea + "'>" + item.orgarea
							+ "</option>");
		});
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
		<div region="north" title="用户查询" collapsed=true style="height:80px;">
			<form id="mysearch" method="post">
			
			<br/>
				&nbsp;&nbsp;&nbsp;所属地区:&nbsp;&nbsp;&nbsp;<select id="orgarea" name="orgarea" style="width:120px;height:20px;"  onchange="showregion()">
						
						
						</select>
						所属区域:&nbsp;&nbsp;&nbsp;<select id="orgregion" name="orgregion" style="width:120px;height:20px;" >
						
						       </select>
				所属部门:&nbsp;&nbsp;&nbsp;<select name="departname2" id="departname2"
						style="width:160px;height:22px;"  >

					</select>
				
				企业员工编号:&nbsp;&nbsp;&nbsp;<input name="empno" value="" style="width:120px;height:22px;" class="textbox"/>
				
					&nbsp;&nbsp;&nbsp;<a id="searchbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a>
				  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				  <a id="clearbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
			</form>

		</div>
		<div region="center">
			<table id="t_user"></table>
		</div>
	</div>

	<div id="mydialog" title="新增员工" modal=true draggable=false
		class="easyui-dialog" closed=true style="width:300px;">
		<form id="myform" action="" method="post">
			<input type="hidden" name="id" value="" /> <br />
			<table>
				<tr>
					<td align="right">所属部门:</td>
					<td><select name="departname" id="departname"
						style="width:200px;height:25px;">

					</select></td>
				</tr>

				<tr>
					<td align="right">姓名:</td>
					<td><input type="text" name="empname" id="empname" value=""
						style="width:200px;height:25px;" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td align="right">企业员工编码:</td>
					<td><input type="text" name="empid" id="empid" value=""
						style="width:200px;height:25px;" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
                <tr>
					<td align="right">职务:</td>
					<td><input type="text" name="empduty" id="empduty" value=""
						style="width:200px;height:25px;" class="easyui-validatebox" data-options="required:true"/></td>
				</tr>
				<tr>
					<td align="right">联系方式:</td>
					<td><input type="text" name="empphonenum" id="empphonenum" value=""
						style="width:200px;height:25px;" class="easyui-validatebox" data-options="required:true,validType:'mobile'" /></td>
				</tr>
				<tr>
					<td align="right">邮箱:</td>
					<td><input type="text" name="empemail" id="empemail" value=""
						style="width:200px;height:25px;" class="easyui-validatebox" data-options="required:true,validType:'email'"/></td>
				</tr>

				<tr>
					<td align="right">性别:</td>
					<td>男<input type="radio" checked="checked" name="empsex"
						value="男" /> 女<input type="radio" name="empsex" value="女" />
					</td>
				</tr>
				<tr>
					<td align="right">年龄:</td>
					<td><select name="empage" style="width:200px;height:25px;">

							<option value="18">18</option>
							<option value="19">19</option>
							<option value="20">20</option>
							<option value="21">21</option>
							<option value="22">22</option>
							<option value="23">23</option>
							<option value="24">24</option>
							<option value="25">25</option>
							<option value="26">26</option>
							<option value="27">27</option>
							<option value="28">28</option>
							<option value="29">29</option>
							<option value="30">30</option>
							<option value="31">31</option>
							<option value="32">32</option>
							<option value="33">33</option>
							<option value="34">34</option>
							<option value="35">35</option>
							<option value="36">36</option>
							<option value="37">37</option>
							<option value="38">38</option>
							<option value="39">39</option>
							<option value="40">40</option>
							<option value="41">41</option>
							<option value="42">42</option>
							<option value="43">43</option>
							<option value="44">44</option>
							<option value="45">45</option>
							<option value="46">46</option>
							<option value="47">47</option>
							<option value="48">48</option>
							<option value="49">49</option>
							<option value="50">50</option>
							<option value="51">51</option>
							<option value="52">52</option>
							<option value="53">53</option>
							<option value="54">54</option>
							<option value="55">55</option>
							<option value="56">56</option>
							<option value="57">57</option>
							<option value="58">58</option>
							<option value="59">59</option>
							<option value="60">60</option>


					</select></td>
				</tr>

				<tr align="center">
					<td colspan="2"><a id="btn1" class="easyui-linkbutton">确定</a>
						<a id="btn2" class="easyui-linkbutton">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="towdim" title="打印二维码" modal=true draggable=false
		class="easyui-dialog" closed=true style="width:330px;heith:300px;text-align:center;">
		
		<div id="printtwodim" style="width:290px;heith:240px;text-align:center;margin:0px auto;"> </div>
		<input type="button" onclick="sureprint('printtwodim')" value="打印" />
		
   </div>
	
	
	
	
	
	
	
	
</body>
</html>
