 new Calendar({  
				inputField: "startdate",  
				trigger: "startdate",
				dateFormat: "%Y年第%W周",
				weekNumbers: true,
				reverseWheel:true,  
				onSelect: function() { this.hide(); } 
			}); 
	 $(function(){
		$('#contentlist').datagrid({
			idField:'cid',	
			title:'内容信息列表',
			//width:2000,
			fit:true,
			height:550 ,
			url:'servlet/ShowDXTServlet?method=showWeekDxt' ,
			//fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			//nowrap: false ,				//折行显示 为true 显示在一会 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:false,
			frozenColumns:[[
	                {field:'id',hidden:true},
				]],							
			columns:[[	
				{
					field:'device_number',
					title:'手机号',
					align:'center',
					width:150
				},	
				{
					field:'employee_id',
					title:'工号',
					align:'center',
					width:150
				},
				{
					field:'score_cur',
					title:'当前积分',
					align:'center',
					width:150 ,
				},
				{
					field:'score_in',
					title:'总收入',
					align:'center',
					width:150 ,
				},		 
					 	
				]] ,
			pagination: true , 
			pageSize: 50,
			pageList:[5,50,100,200,500], 
			});
	});
	$('#submit').click(function(){
		getDurDate();
		$('#contentlist').datagrid('load' ,serializeForm($('#mysearch')));
		
		
	});
	
	 //这个方法将取得某年(year)第几周(weeks)的星期几(weekDay)的日期
	function getXDate(year,weeks,weekDay){ 
		var date = new Date(year,"0","1");
	
		//取得这个日期对象 date 的长整形时间 time
		var time = date.getTime(); 
		//将这个长整形时间加上第N周的时间偏移
		//因为第一周就是当前周,所以有:weeks-1,以此类推
		//7*24*3600000 是一星期的时间毫秒数,(JS中的日期精确到毫秒)
		time+=(weeks-1)*7*24*3600000;
	
		//为日期对象 date 重新设置成时间 time
		date.setTime(time);
		return getNextDate(date,weekDay);
	}
	//这个方法将取得 某日期(nowDate) 所在周的星期几(weekDay)的日期
	function getNextDate(nowDate,weekDay){
		//0是星期日,1是星期一,...
		if(weekDay!=7)
			weekDay%=7; 
		var day = nowDate.getDay(); 
		var time = nowDate.getTime();
		var sub = weekDay-day;
		time+=sub*24*3600000;
		nowDate.setTime(time); 
		 var year = nowDate.getFullYear(); 
	    var month = nowDate.getMonth() + 1; 
	    var date = nowDate.getDate(); 
	    if (month < 10) { month = "0" + month; } 
	    if (date < 10) { date = "0" + date; }  
	    return year + "-" + month + "-" + date;
		
	//	return nowDate;
	}
	//2008第1周的星期5
	//alert("2008第1周的星期5是:"+getXDate(2008,1,5));
	//2008第51的星期5
	//alert("2008第51周的星期5是:"+getXDate(2008,51,5));
	function getDurDate(){
		var value=document.getElementById('startdate').value;
		if(value==""){
			$.messager.alert('提示','请输入时间','error');
			return 0;
		}
		var y = value.substring(0,4);
		var w = value.substring(6,8); 
		var start_date = getXDate(y,w,1);
		var end_date = getXDate(y,w,7);
		document.getElementById('start_date').value = start_date;
		document.getElementById('end_date').value = end_date;
		console.log(start_date+":::"+end_date);
	}