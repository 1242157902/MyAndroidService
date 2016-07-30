$(function(){
		$('#mycontentlist').datagrid({
			idField:'id',	
			title:'图标信息列表',
			//width:2000,
			fit:true,
			height:450 ,
			url:'servlet/IconServlet?method=GetList' ,
			//fitColumns:true ,  
			striped: true ,					//隔行变色特性 
			//nowrap: false ,				//折行显示 为true 显示在一会 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
			{
					field:'title' ,
					title:'标题' ,
					width:150, 
					height:50
				},	
				{
					field:'iconname',
					title:'图标',
					width:100 ,
					height:50,
					align:'center',
				formamytter:function(value,row,index){
    				return '<img src="'+'images/icons/'+value+'" style="width:50;height:50" />';
    								
    			   } 
    								 
    		  },
				{
					field:'picurl',
					title:'链接',
					width:100 ,
					height:50,
					formamytter: function(value,row,index){
       									return '<a href="javascript:" onclick="linkto(\'hmyttp://' + value+ '\')" style="font:12px;">'+row.picurl+'</a>'; 
    								 }   
				},
				{
					field:'updatetime' ,
					title:'更新时间' ,
					width:150 ,
					height:50
				},
				{
					field:'manager' ,
					title:'操作员' ,
					width:150, 
					height:50
				}
				]] ,
			pagination: true , 
			pageSize: 20,
			pageList:[5,10,15,20,50], 
			toolbar: [
			{
               text: "添加",
               iconCls: "icon-add",
               handler: function () { 
               var newIcon='<span>添加新图标</span>';
                if ($('#mytt').tabs('exists',  newIcon)) { $('#mytt').tabs('select', newIcon); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/IconAdd.jsp";
                  $('#mytt').tabs('add',
			      {
				   title :newIcon,
				   content : '<iframe id=mynewIcon frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
            }, '-',
            {
               text: "删除",
               iconCls: "icon-remove",
               handler:function (){
                        if(fctrow()==-1) alert("请选择一行!");
                        else { 
                         $.messager.confirm('确认','确认删除?',function(data){  
                           if(data){
                            var selectedRow = $('#mycontentlist').datagrid('getSelected');
                            $.ajax({ 
                            type: "post",  
                            url: "servlet/IconServlet?method=DeleteIcon", 
                            data: {id:selectedRow.id},  
                            //dataType: "json",  
                            success: function(data) {  if(data!=null)    {alert("删除成功！"); $('#mycontentlist').datagrid('reload'); }  else   alert("删除失败"); },
                            error:function(data) {   alert("删除失败"); }
                            });   
                           $('#mycontentlist').datagrid('reload');  }
                         });  
			            }}	 
            },'-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler: function () { 
                if(fctrow()==-1) {alert("请选择一行!");return;}
                  var selectedRow = $('#mycontentlist').datagrid('getSelected');
                var theIcon='<span>修改图标</span><input type=hidden value='+selectedRow.id+'/>';
                if ($('#mytt').tabs('exists',  theIcon)) { $('#mytt').tabs('select', theIcon); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/IconUpdate.jsp";
                  document.getElementById("myccid").innerHTML=selectedRow.id;
                  document.getElementById("myctitle").innerHTML=selectedRow.title;
                  document.getElementById("mycnamex").innerHTML=selectedRow.iconname;
                  document.getElementById("myclink").innerHTML=selectedRow.picurl;
                  $('#mytt').tabs('add',
			      {
				   title :theIcon,
				   content : '<iframe id=mynewIcon frameborder=0  style=width:100%;height:100% src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
                             
            },'-']
			});
	});
	
	function closeupdatecmyttab()
	{
	  $('#mytt').tabs('close','<span>修改图标</span><input type=hidden value='+document.getElementById("myccid").innerHTML+'/>');
	  $('#contentlist').datagrid('reload');  
	}
	function closeaddcmyttab()
	{
	    $('#mytt').tabs('close','<span>添加新图标</span>');
	    $('#mycontentlist').datagrid('reload');  
	}
	
	function fctpage(){
        return $('#mycontentlist').datagrid('getPager').data("pagination").options.pageNumber;
    } 
    
    
    function fctrow(){
        var row = $('#mycontentlist').datagrid('getSelected');
        return $('#mycontentlist').datagrid('getRowIndex',row);       
    } 
    
    
	function linkto(v){	
	    $('#myclicklink').dialog({  
          title: '查看链接',  
          width: 800,  
          height: 500,  
          left:(screen.width-320)/2,
          top:(screen.height-500)/3,
          closed: false,    
          cache: false,    
          href: '',    
          modal: true  
        });  
       $('#myclicklink').dialog('refresh', 'pages/push/ContentLoadLink.jsp?url='+v);      
	}