 var todayDate=new Date();
 
 if(todayDate.getDay()==1){
	 $('#print').removeAttr("disabled");
 }else{
	 $('#print').attr("disabled",true);
 }
$(function(){
		$('#contentlist').datagrid({
			idField:'cid',	
			title:'内容信息列表',
			//width:2000,
			fit:true,
			height:550 ,
			url:'servlet/ShowDXTServlet?method=showTotalDxt' ,
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
					title:'总积分',
					align:'center',
					width:150 ,
				},
				{
					field:'nums',
					title:'已获得奖励/个',
					align:'center',
					width:150 ,
				},						
				{
					field:'enter_time' ,
					title:'注册时间' ,
					align:'center',
					width:150, 
				},	
					
				{
					field:'memo',
					title:'备注',
					align:'center',
					width:70 ,
				}, 	
				]] ,
			pagination: true , 
			pageSize: 50,
			pageList:[5,50,100,200,500], 
			});
	});
	$('#submit').click(function(){
		var score=$('#score_limit').val();
		if(score==""){
			$.messager.alert('提示','请输入积分','error');
		}else{
			$('#contentlist').datagrid('load' ,serializeForm($('#mysearch')));
		}
		
	});
	$('#print').click(function(){
		
		//对数据库进行更改，并reload  datagrid
		var score_limit=$('#score_limit').val();
		var options=$('#contentlist').datagrid('getPager').data("pagination").options;
		var page = options.pageNumber; 
		var rows=options.pageSize;
//		var total = options.total;  
//		var max = Math.ceil(total/options.pageSize);
//	    alert("options:"+options+"pagezie:"+rows+"page"+page+"total:"+total+"max:"+max);
		if(score_limit==""){
			$.messager.alert('提示','请输入积分','error');
		}else{
		$dialog = $('<div/>').dialog({     
               title: '提示',     
               width: 400,     
               height: 250,     
               iconCls : 'pag-search',    
               closed: true,     
               cache: false,      
               modal: true,  
               toolbar:'#toolbar',
               content: '<p style="font-size:18px;">你确定要打印当前页并消减积分大于'+score_limit+'的积分吗？</p><p style="color:red">注意：1.请使用谷歌或火狐浏览器!<br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;2.点击确定后即视为打印！</p>',               
               buttons : [ {    
                    text : '确定',    
                    iconCls : 'ope-save',    
                    handler : function() {    
                        $.ajax({
				    		type:'post',
				    		url: 'servlet/ShowDXTServlet?method=saveDxt',
							data: {score_limit:score_limit,page:page,rows:rows},
							success:function(data) { 
								if(data=="success"){
										exportexcels(score_limit);
										setTimeout($.messager.alert('提示','打印成功','info',function(){  
	                                         $dialog.dialog('close');      
	                                        $('#contentlist').datagrid('reload');   
                                     	}),5000);
										
								}else if(data!=null&&!data.success){  
                                     $.messager.alert('提示','打印失败','error');  
                                }            
							}  
				    				
    					})                 
                    }    
                }, {    
                    text : '取消',    
                    iconCls : 'ope-close',    
                    handler : function() {    
                        $dialog.dialog('close');    
                    	}    
                	}
                ]  
          }); 
        $dialog.dialog('open'); } 
    });  	
	
	var tableString =""; 
	   
	function exportexcels(score_limit) {
			tableString='<div  style="width:98%;text-align:center;margin:5px auto;">';
	   	 tableString += '<div style="border-bottom:1px ;width:100%;text-align:center; margin:0 auto;font-size:20px;letter-spacing:5px;">';
	        CreateFormPage("报表",score_limit, $("#contentlist"));
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
	function CreateFormPage(strPrintName, score_limit,printDatagrid) {
	    //var tableString = '<table class="tableClass" cellspacing="0">';
	    //tableString += '<tr><td colspan="8" style="border:none;text-align: center;"><strong>' + strPrintName + '</strong></td></tr>';
	
	
	     tableString+='<strong>'+strPrintName+'</strong>';
	     tableString+='<br><span style="font-size:14px;">积分大于'+score_limit+'的用户</span>';
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
	