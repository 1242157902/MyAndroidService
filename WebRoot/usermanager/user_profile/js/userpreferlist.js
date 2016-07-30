$(function() {
	/**
		*	初始化数据表格  
	 */
	var editing; //判断用户是否处于编辑状态 
	$('#userslist').datagrid(
		{
			idField : 'id', //只要创建数据表格 就必须要加 ifField
			title : '设备信息列表',
			//width:2000 ,
			fit : true,
			height : 450,
			url : 'servlet/UserProfileServlet?method=GetUsersList',
			striped : true, //隔行变色特性 
			loadMsg : '数据正在加载,请耐心的等待...',
			rownumbers : true,
			columns : [ [
			   {
					field : 'mbno',
					title : '用户手机号码',
					width : 120,
					height : 50
				}, {
					field : 'imei',
					title : '设备号',
					width : 180,
					height : 50
                 },{
					field : 'imsi',
					title : 'SIM卡号',
					width : 180,
					height : 50
                  },{
					field : 'area',
					title : '手机归属地',
					width : 180,
					height : 50
                  },{
					field : 'mbtype',
					title : '用户手机类型',
					width : 150,
					height : 50
					//hidden: true
				},{
					field : 'birth',
					title : '年龄',
					width : 50,
					height : 50
                },{
					field : 'gender',
					title : '性别',
					width : 35,
					formatter : function(value, row, index) {
						return enGender(value);
					}
                },{
					field : 'mbos',
					title : '用户系统版本号',
					width : 120,
					height : 50
            }] ],
			pagination : true,
			pageSize : 10,
			pageList : [ 5, 10, 15, 20, 50 ]
		})
});

$('#srbtn').click(function(){
	$('#userslist').datagrid('load' ,serializeForm($('#my')));
});
//js方法：序列化表单
function serializeForm(form) {
	var obj = {};
	$.each(form.serializeArray(), function(index) {
		if (obj[this['name']]) {
			obj[this['name']] = obj[this['name']] + ',' + this['value'];
		} else {
			obj[this['name']] = this['value'];
		}
	});
	return obj;
}

function saveDepartx() {
	var rows=$('#userslist').datagrid('getRows');
	
	var imeis=[];
	for(var i=0;i<rows.length;i++){ 
		imeis.push(rows[i].imei);
	} 
	var queid=$('#queid').combobox('getValue')+"";
	$.ajax({
		type:'post',
		url: 'servlet/UserProfileServlet?method=PushLists',
		data: {queid:queid,"imeis":imeis},
		success:function(data) {  
			if(data=="none")
				alert("没有此偏好数据");
			else
                alert("推送成功");
		}  
				
	}) 
}
function enGender(v)
{
	if(v==1||v=='1'||v=='男') return '男';
	else if(v==2||v=='2'||v=='女')  return '女';
	else return '';
}
function managerstr(value,rowData,rowIndex) {
   return "<a href=\"javascript:void()\" onclick=\"lookpushlog("+value+")\">点击查看</a>";
}