new Calendar({  
				inputField: "startdate",  
				trigger: "startdate",
				dateFormat: "%Y年第%W周",
				weekNumbers: true,
				reverseWheel:true, 
				opacity:1,
				onSelect: function() { this.hide(); } 
			}); 
	 $(function(){
		$('#contentlist').datagrid({
			idField:'cid',	
			title:'内容信息列表',
			//width:2000,
			fit:true,
			height:550 ,
			url:'servlet/ShowDXTServlet?method=showWeekPrintedDxt' ,
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
					field:'score_count',
					title:'消费积分',
					align:'center',
					width:150 ,
				},
				{
					field:'enter_time',
					title:'换购日期',
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
	$('#print').click(function(){
		var value=document.getElementById('startdate').value;
		if(value=="")
			value="所有";
		exportexcels(value);
    });  	
	
	var tableString =""; 
	   
	function exportexcels(value) {
			tableString='<div  style="width:98%;text-align:center;margin:5px auto;">';
	   	 tableString += '<div style="border-bottom:1px ;width:100%;text-align:center; margin:0 auto;font-size:20px;letter-spacing:5px;">';
	        CreateFormPage(value+"的报表",$("#contentlist"));
	        doPrint();
	}
	function doPrint()
	{
		tableString +="<script type='text/javascript'	src='${pageContext.request.contextPath}/js/jquery-easyui-1.4.1/jquery.min.js'></s"+"cript>";
	//	tableString += "<script language='javascript' src='${pageContext.request.contextPath}/js/jquery.jqprint-0.3.js'></s"+"cript>";
		tableString += "<script language='javascript'>window.print();//$('#table').jqprint();</s"+"cript>";
		// tableString.insertAdjacentHTML("beforeBegin","<scriptlanguage='javascript'>window.print();</s"+"cript>")
		var OpenWindow=document.open('','','height=500,width=611,scrollbars=yes,status =yes');
		
		OpenWindow.document.write(tableString);
		OpenWindow.document.close(); 
	}
	var printData;
	function CreateFormPage(strPrintName,printDatagrid) {
	    //var tableString = '<table class="tableClass" cellspacing="0">';
	    //tableString += '<tr><td colspan="8" style="border:none;text-align: center;"><strong>' + strPrintName + '</strong></td></tr>';
	
	
	     tableString+='<strong>'+strPrintName+'</strong>';
	    tableString+='</div>';
	    tableString+='<table id="table" border="1" class="tableClass" cellspacing="0">';
	    var frozenColumns = printDatagrid.datagrid("options").frozenColumns;  // 得到frozenColumns对象
	    var columns = printDatagrid.datagrid("options").columns;    // 得到columns对象
	    var nameList = '';
	
	
	    // 载入title
	    if (typeof columns != 'undefined' && columns != '') {
	        $(columns).each(function (index) {
	            tableString += '\n<tr>';
	            if (typeof frozenColumns != 'undefined' && typeof frozenColumns[index] != 'undefined') {
	                for (var i = 0; i < frozenColumns[index].length; ++i) {
	                    if (!frozenColumns[index][i].hidden) {
	                        tableString += '\n<th width="' + frozenColumns[index][i].width + '"';
	                        if (typeof frozenColumns[index][i].rowspan != 'undefined' && frozenColumns[index][i].rowspan > 1) {
	                            tableString += ' rowspan="' + frozenColumns[index][i].rowspan + '"';
	                        }
	                        if (typeof frozenColumns[index][i].colspan != 'undefined' && frozenColumns[index][i].colspan > 1) {
	                            tableString += ' colspan="' + frozenColumns[index][i].colspan + '"';
	                        }
	                        if (typeof frozenColumns[index][i].field != 'undefined' && frozenColumns[index][i].field != '') {
	                            nameList += ',{"f":"' + frozenColumns[index][i].field + '", "a":"' + frozenColumns[index][i].align + '"}';
	                        }
	                        tableString += '>' + frozenColumns[0][i].title + '</th>';
	                    }
	                }
	            }
	            for (var i = 0; i < columns[index].length; ++i) {
	                if (!columns[index][i].hidden) {
	                    tableString += '\n<th width="' + columns[index][i].width + '"';
	                    if (typeof columns[index][i].rowspan != 'undefined' && columns[index][i].rowspan > 1) {
	                        tableString += ' rowspan="' + columns[index][i].rowspan + '"';
	                    }
	                    if (typeof columns[index][i].colspan != 'undefined' && columns[index][i].colspan > 1) {
	                        tableString += ' colspan="' + columns[index][i].colspan + '"';
	                    }
	                    if (typeof columns[index][i].field != 'undefined' && columns[index][i].field != '') {
	                        nameList += ',{"f":"' + columns[index][i].field + '", "a":"' + columns[index][i].align + '"}';
	                    }
	                    tableString += '>' + columns[index][i].title + '</th>';
	                }
	            }
	            tableString += '\n</tr>';
	        });
	    }
	    // 载入内容
	    var rows = printDatagrid.datagrid("getRows"); // 这段代码是获取当前页的所有行
	    var nl = eval('([' + nameList.substring(1) + '])');
	    for (var i = 0; i < rows.length; ++i) {
	        tableString += '\n<tr>';
	        $(nl).each(function (j) {
	            var e = nl[j].f.lastIndexOf('_0');
	
	
	            tableString += '\n<td';
	            if (nl[j].a != 'undefined' && nl[j].a != '') {
	                tableString += ' style="text-align:' + nl[j].a + ';"';
	            }
	            tableString += '>';
	            if (e + 2 == nl[j].f.length) {
	                tableString += rows[i][nl[j].f.substring(0, e)];
	            }
	            else {
	
	                    tableString += rows[i][nl[j].f] == null ? "" : rows[i][nl[j].f];
	                
	            }
	            tableString += '</td>';
	        });
	        tableString += '\n</tr>';
	    }
	    tableString += '\n</table>';
	    tableString+='</div>';  
	}	