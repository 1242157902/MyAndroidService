<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<title>移动互联之云平台</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page"> 
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"	href="js/jquery-easyui-1.4.1/themes/icon.css" />
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"	src="js/push/pushindex.js"></script>
<script type="text/javascript"	src="js/push/pushencode.js"></script>
<script type="text/javascript"	src="js/push/pushfunc.js"></script>

<script type="text/javascript">
	$(function() {
		$('a[title]')
				.click(
						function() {
							var src = $(this).attr('title');
							var title = $(this).html();
							//if ($('#tt').tabs('exists', title)) {
							//	$('#tt').tabs('select', title);
							//} else {
								$('#tt')
										.tabs(
												'add',
												{
													title : title,
													content : '<iframe frameborder=0 name=hehe style=width:100%;height:100%; src='
															+ src
															+ ' ></iframe>',
													closable : true
												});
							//}

						});

	});
	
	
</script>
<style type="text/css">
body{
margin:0px;
border:0px;
padding:0px;
width:100%;
height:100%;
}
ul{list-style-type:none;margin:0px; padding:0px;}
ul li{ padding:3px;}
#aa .navi ul li a:link,a:visited{
	 color:#666666;
	 font-size:12px;
	 font-weight:bold;
	 text-decoration:none;
}
 
#aa .navi ul li a:hover {
	cursor:pointer;
	color: red;
}
.icon{width:18px; line-height:18px; display:inline-block;}
.pushqrctd{
table-layout: fixed;
border-bottom:1px #95B8E7 solid;
border-right:1px #95B8E7 solid;
padding-top:5px;
padding-bottom:5px;
overflow: hidden;
}
.pushtbblank{
padding-left:0px;
}
</style>


</head>

<body style="overflow-y:hidden">
	<div id="cc" class="easyui-layout"  style="height:100%;"> 
	
		<div region="north" split="true" style="height:80px;background:#53A6E8;text-align:left;">
		
		<div style="float:left;">		
		<img src="${pageContext.request.contextPath }/images/fyy.gif" style="width:1075px;height:68px;"/>
		</div>
		
		<div style="width:250px;height:73px;float:right;">		
		<div style="width:250px;height:15px;margin-top:52px;color:white;text-align:center;">欢迎您&nbsp;${sessionScope.user.username}&nbsp;&nbsp;|&nbsp;&nbsp;
        <a href="help/help.zip"  style="text-decoration:none;color:white;">帮助文档</a>&nbsp;&nbsp;|&nbsp;&nbsp;
		<a href="${pageContext.request.contextPath }/servlet/LoginOut" style="text-decoration:none;color:white;">安全退出</a></div>				
		</div>
				
		</div>

		<div region="west" iconCls="icon-ok" split="true" title="管理菜单"	style="width:200px;background-color:#E0ECFF">
			<div id="aa" class="easyui-accordion" fit=true>

				 <c:if test="${!empty requestScope.sbxxgl}"> 
					<div title="设备管理" style="overflow:auto;padding:10px;" iconCls="icon-image-add" class="navi">
						<ul>
						<li id="a11">
						<a pushtitle="${pageContext.request.contextPath }/pages/device/PhoneInfoList.jsp"><span class="icon icon-default">&nbsp;</span><span>设备基本信息</span></a>
						</li>
						<li>
						<a pushtitle="${pageContext.request.contextPath }/pages/device/OpenInfoList.jsp"><span class="icon icon-default">&nbsp;</span>设备活动信息</a>
                        </li>
                        <li>
				        <a pushtitle="${pageContext.request.contextPath }/pages/device/MBthemeList.jsp"><span class="icon icon-default">&nbsp;</span>模式批量任务</a><br />
				        </li>				       
						<li>
						<a title="${pageContext.request.contextPath }/pages/PhoneUpdateApkInfo.jsp"><span class="icon icon-default">&nbsp;</span>设备更新apk信息</a>
						</li>
						
						<li>
						<a title="${pageContext.request.contextPath }/pages/device/Scorepie.jsp"><span class="icon icon-default">&nbsp;</span>积分分布查询</a>
						</li>
						<li>
						<a
							title="${pageContext.request.contextPath }/usermanager/wechat_manage/show_dxt_week.jsp"><span class="icon icon-default">&nbsp;</span><span>迪信通周列表</span></a></li>
						<li>
						<a
							title="${pageContext.request.contextPath }/usermanager/wechat_manage/show_dxt.jsp"><span class="icon icon-default">&nbsp;</span><span>迪信通打印列表</span></a></li>
						<li>
						<a
							title="${pageContext.request.contextPath }/usermanager/wechat_manage/show_dxt_printed.jsp"><span class="icon icon-default">&nbsp;</span><span>迪信通换购列表</span></a></li>
						
						</ul>
					</div>
			 	</c:if> 
			 	
				<c:if test="${!empty requestScope.nrgl}">
					<div title="内容推送管理" style="overflow:auto;padding:10px;"  iconCls="icon-text" class="navi">
						<ul>
						<li>
						<a pushtitle="${pageContext.request.contextPath }/pages/push/ContentList.jsp"><span class="icon icon-default">&nbsp;</span>内容管理</a><br />
				        </li>				      
				        <li>
						<a pushtitle="${pageContext.request.contextPath }/pages/push/IconList.jsp"><span class="icon icon-default">&nbsp;</span>图标管理</a><br />
				        </li>
				        <li>
				        <a pushtitle="${pageContext.request.contextPath }/pages/push/QueueList.jsp"><span class="icon icon-default">&nbsp;</span>序列管理</a><br />
				        </li>
				        <li>
				        <a pushtitle="${pageContext.request.contextPath }/pages/push/PushList.jsp"><span class="icon icon-default">&nbsp;</span>推送管理</a><br />
				        </li>
				        <li>
				        <a pushtitle="${pageContext.request.contextPath }/pages/push/PushDefault.jsp"><span class="icon icon-default">&nbsp;</span>默认序列设置</a><br />
				        </li>	
				         <li>
				        <a pushtitle="${pageContext.request.contextPath }/pages/push/PushFavor.jsp"><span class="icon icon-default">&nbsp;</span>维度管理</a><br />
				        </li>			
				         <li>
				        <a pushtitle="${pageContext.request.contextPath }/pages/push/PushInterface.jsp"><span class="icon icon-default">&nbsp;</span>模拟推送</a><br />
				        </li>		        				      
				        <li>
						<a pushtitle="${pageContext.request.contextPath }/pages/appstore/AppList.jsp"><span class="icon icon-default">&nbsp;</span>手机App管理</a><br />
				        </li>
				        <li>
				        <a title="${pageContext.request.contextPath }/pages/Time.jsp"><span class="icon icon-default">&nbsp;</span>间隔时间管理</a><br />
				        </li>
				        <li>
				        <a title="${pageContext.request.contextPath }/pages/apkupdate.jsp"><span class="icon icon-default">&nbsp;</span>APK版本管理</a><br />
				        </li>
				        <li>
				        <a title="${pageContext.request.contextPath }/pages/ApkUpdateInfo.jsp"><span class="icon icon-default">&nbsp;</span>APK历史信息</a><br />
				        </li>
				        
				        </ul>
					</div>
				</c:if>		
						
				<div title="日志管理" style="overflow:auto;padding:10px;" iconCls="icon-image-add" class="navi">
					<ul>
					<li>
					<a pushtitle="${pageContext.request.contextPath }/pages/log/PushLog.jsp"><span class="icon icon-default">&nbsp;</span>推送历史记录</a><br />
			        </li>
					<li>
			        <a pushtitle="${pageContext.request.contextPath }/pages/log/MBthemeLog.jsp"><span class="icon icon-default">&nbsp;</span>模式切换日志</a><br />
			        </li>
			        <li>
			        <a pushtitle="${pageContext.request.contextPath }/pages/log/AcceptLog.jsp"><span class="icon icon-default">&nbsp;</span>序列接收日志</a><br />
			        </li>			        
			        <li>
			        <a pushtitle="${pageContext.request.contextPath }/pages/log/ErrorLog.jsp"><span class="icon icon-default">&nbsp;</span>推送错误日志</a><br />
			        </li>
             		</ul>
		        </div>
				<c:if test="${!empty requestScope.tjfx}">
				<div title="统计分析"  style="overflow:auto;padding:10px;"  iconCls="icon-line" class="navi">
					<ul>
					  <li>
						<a pushtitle="${pageContext.request.contextPath }/pages/stats/StuffSale.jsp"><span class="icon icon-pie">&nbsp;</span>员工销售业绩</a><br />
				      </li>
				      <li>
					     <a href="javascript:void(0);" onclick="deviceshowhide()"><span class="icon icon-pie">&nbsp;</span>设备信息统计</a><br />
				         <ul id="devicestatis">
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart1.jsp">设备数量直方分布图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart2.jsp">设备数量折线分布图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart3.jsp">设备数量总体趋势图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart4.jsp">设备数量统计图</a></li>
				         </ul>
					  </li>
					  <li>
					     <a href="javascript:void(0);" onclick="slideshowhide()"><span class="icon icon-pie">&nbsp;</span>活动信息统计</a><br />
				         <ul id="slidestatis">
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart5.jsp">个人活动信息直方分布图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart6.jsp">个人活动信息折线分布图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart7.jsp">活动信息折线分布图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart8.jsp">活动信息总体趋势图</a></li>
				         <li>&nbsp;&nbsp;&nbsp;&nbsp;<a title="${pageContext.request.contextPath }/pages/stats/Chart9.jsp">活动信息统计图</a></li>
				         </ul>
					  </li>
					</ul>
				</div>
				</c:if>
				<div title="用户行为分析"  style="overflow:auto;padding:10px;"  iconCls="icon-CategorizeMenu" class="navi">
						<ul>
						<li>
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/basicInfo.jsp"><span class="icon icon-group-add">&nbsp;</span><span>基本信息分布</span></a></li>
						 
						 <li> 
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/track/map.jsp"><span class="icon icon-group-add">&nbsp;</span><span>单用户轨迹</span></a></li>
						 
					<!-- 	 <li>
					 	<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/track/monthMapShow.jsp"><span class="icon icon-group-add">&nbsp;</span><span>月统计</span></a></li>
						 
						 
						<li>
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/track/test.jsp"><span class="icon icon-group-add">&nbsp;</span><span>所有用户轨迹</span></a></li>
						 -->
						<li>
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/UserHabit.jsp"><span class="icon icon-group-add">&nbsp;</span><span>使用习惯查询</span></a></li>
					<!-- 	 <li>
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/UserPrefer.jsp"><span class="icon icon-group-add">&nbsp;</span><span>用户偏好查询</span></a></li>
						--> <li> 
						
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/UserModel.jsp"><span class="icon icon-group-add">&nbsp;</span><span>活动分布</span></a></li>
						<!--  <li> 
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/UserApp.jsp"><span class="icon icon-group-add">&nbsp;</span><span>单个APP使用</span></a></li>
						   -->
						  <li> 
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/UsersApp.jsp"><span class="icon icon-group-add">&nbsp;</span><span>偏好查询</span></a></li>
						  <li> 
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/userPreferlist.jsp"><span class="icon icon-group-add">&nbsp;</span><span>用户群</span></a></li>
						    <li> 
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/revelerancedataminging.jsp"><span class="icon icon-group-add">&nbsp;</span><span>特征分析</span></a></li>
						 
						  <li> 
						<a
							title="${pageContext.request.contextPath }/usermanager/user_profile/habit/UserProfile.jsp"><span class="icon icon-group-add">&nbsp;</span><span>特征展示</span></a></li>
						
						</ul>
					</div>

				<c:if test="${!empty requestScope.xtsz}"> 
				<div title="系统用户管理"  style="overflow:auto;padding:10px;"  iconCls="icon-CategorizeMenu" class="navi">
					<ul>
					<li><a
						title="${pageContext.request.contextPath }/servlet/UserManagerServlet?method=listuser"><span class="icon icon-group-add">&nbsp;</span><span>用户管理</span></a></li>
					
					<c:if test="${!empty requestScope.admin}">
					<li><a
						title="${pageContext.request.contextPath }/servlet/RoleManagerServlet?method=listrole"><span class="icon icon-ok">&nbsp;</span>角色管理</a></li>
					<li><a
					title="${pageContext.request.contextPath }/servlet/PrivilegeManagerServlet?method=listprivilege"><span class="icon icon-pie">&nbsp;</span>资源管理</a></li>
					</c:if>
					</ul>
				</div>
				</c:if>  		
				
				<c:if test="${!empty requestScope.qygl}">				
				<div title="企业管理"  style="overflow:auto;padding:10px;"  iconCls="icon-CategorizeMenu" class="navi">
					<ul>
					<li>
					<a title="${pageContext.request.contextPath }/usermanager/org.jsp"><span class="icon icon-group-add">&nbsp;</span><span>企业管理</span></a>
					</li>
					<li>
					<a title="${pageContext.request.contextPath }/usermanager/emp.jsp"><span class="icon icon-group-add">&nbsp;</span><span>员工管理</span></a>
					</li>
				   </ul>
				</div>					
				</c:if>

				<c:if test="${!empty requestScope.ewmgl}">
					 <div title="二维码管理"  style="overflow:auto;padding:10px;"  iconCls="icon-ok" class="navi">
						<ul>
						<li>
						<a title="${pageContext.request.contextPath }/usermanager/erweima.jsp"><span class="icon icon-ok">&nbsp;</span>二维码生成器</a>
						</li>
						</ul>
					</div>
				</c:if>

		       <div title="修改密码"  style="overflow:auto;padding:10px;"  iconCls="icon-edit" class="navi">
					<ul>
					<li>
					<a title="${pageContext.request.contextPath }/usermanager/updateuser.jsp"> <span class="icon icon-redo">&nbsp;</span>修改用户密码</a>
					</li>
					</ul>
				</div>		       
		       <div title="退出登录" selected=true style="overflow:auto;padding:10px;"  iconCls="icon-cancel">
					<a href="${pageContext.request.contextPath }/servlet/LoginOut">退出登录</a><br />
			   </div>


			</div>
		</div>
		
		<div region="center" >
			<div id="tt" class="easyui-tabs" fit=true	style="background:url('images/beijing1.jpg');"></div>
		</div>
		
		<div region="south" title="" style="padding:5px;background:#E0ECFF;height:30px;text-align:center;">
			<span style="text-align:center;font-size:14px">版权所有Copyright @ 2014 北方工业大学计算机应用技术研究所</span>
		</div>
	
	</div>
</body>
</html>
