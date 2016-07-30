<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>QueueList</title>    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
    <span id=qqid  style="display:none"></span>
    <span id=qtitle  style="display:none"></span>
    <span id=qaid  style="display:none"></span>
    <span id=qcid  style="display:none"></span>
    <span id=qshowtime1  style="display:none"></span>
    <span id=qenddate1  style="display:none"></span>
    <span id=qnamex  style="display:none"></span>
    <span id=queue style="display:none"></span>
    <input id=qsaveatompara type="hidden" />
    
	<div id="qclickpic" class="easyui-dialog"  closed="true"></div>
	
    <div id="qdlg" class="easyui-dialog" style="width:600px;height:400px;"  closed="true" modal="true" buttons="#quedlg-buttons" >     
      <div>
                           显示时长<input id="qshowtime" name="showtime"  type="text"/>
                           有效日期<input id="qenddate" name="enddate"  class="easyui-datetimebox" editable="false" style="width:200px;" />
      </div>
      <div id="choosecontentlay" class="easyui-layout" style="width: 100%;height:300px" >
	    <div region="center" ><table id="choosecontentlist"></table></div>
	  </div>
    </div>
	<div id="quedlg-buttons" align="right">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="qSaveAtom()" style="width:90px">确定</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#qdlg').dialog('close')" style="width:90px">取消</a>
    </div>
    

    
	<div id="queuelistlay" class="easyui-layout" style="width: 100%;height:100%" >
	  <div region="center" ><table id="queuelist"></table></div>
	</div>
		
	<input id=qAddAtomCloseTabBtn type="hidden" onclick="qAddAtomCloseTab()"/>
	<input id=qUpdateAtomCloseTabBtn type="hidden" onclick="qUpdateAtomCloseTab()"/>
	<input id=qSortQueCloseTabBtn type="hidden" onclick="qSortQueCloseTab()"/>
	<input id=qLookAtomCloseTabBtn type="hidden" onclick="qLookAtomCloseTab()"/>
	<input id=qAddNewAtomBtn type="hidden" onclick="qAddNewAtom()"/>	
	<input id=qUpdateNewAtomBtn type="hidden" onclick="qUpdateNewAtom()"/>	
	<input id=qAddUpdateAtomBtn type="hidden" onclick="qAddUpdateAtom()"/>	
	<input id=qUpdateUpdateAtomBtn type="hidden" onclick="qUpdateUpdateAtom()"/>	
	
	<script type="text/javascript">	
	  $(function(){
		$('#queuelist').datagrid({
			idField:'cid',	
			title:'序列信息列表',
			fit:true,
			url:'servlet/QueServlet?method=GetQueueList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,							
			columns:[[
				{
					field:'title' ,
					title:'序列名称' ,
					width:150
				},	
				{
					field:'que' ,
					title:'详细信息',
					width:750 
				},
				{
					field:'qid',
					title:'操作',
					width:100 ,
					formatter: function(value,row,index){
						return '<a title="'+ row.oper+'" href="javascript:" onclick="SortQueue('+row.qid+',this.title)" style="font:12px;">重新排序</a>';    
       							}   
				},
				{
					field:'oper',
					title:'模拟',
					width:100 ,
					formatter: function(value,row,index){
       						return '<a title="'+value+'" href="javascript:" onclick="Imitate(this.title)" style="font:12px;">点击开始</a>';    							
    					 }   
				}
				]] ,
			pagination: true , 
			pageSize: 20 ,
			pageList:[5,10,15,20,50], 
			toolbar: [
			{
               text: "添加",
               iconCls: "icon-add",
               handler: function () { 
                var newQue='<span>添加新序列</span>';
                if ($('#tt').tabs('exists',  newQue)) { $('#tt').tabs('select', newQue); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/QueueAdd.jsp";
                  $('#tt').tabs('add',
			      {
				   title :newQue,
				   content : '<iframe name=NewQue frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
            }, '-',
            {
               text: "删除",
               iconCls: "icon-remove",
               handler:function () { 
                   if(fquerow()==-1) alert("请选择一行!");
                   else { 
                         $.messager.confirm('确认','确认删除?',function(data){  
                           if(data){
                            var selectedRow = $('#queuelist').datagrid('getSelected');
                            $.ajax({ 
                            type: "post",  
                            url: "servlet/QueServlet?method=DeleteQueue", 
                            data: {qid:selectedRow.qid},  
                            dataType: "json",  
                            success: function(data) {  if(data!=null)    {alert(data.deletequeue); $('#queuelist').datagrid('reload'); }  else   alert("删除失败"); },
                            error:function(data) {   alert("删除失败"); }
                            });   
                           $('#queuelist').datagrid('reload');  }
                         });  
			            }}	 
            },'-',
            {
               text: "修改",
               iconCls: "icon-edit",
               handler: function () { 
                if(fquerow()==-1) {alert("请选择一行!");return;}
                var selectedRow = $('#queuelist').datagrid('getSelected');
                var theQue='<span>修改序列</span><input type=hidden value='+selectedRow.qid+'/>';
                var url="${pageContext.request.contextPath }/pages/push/QueueUpdate.jsp";
                document.getElementById("qqid").innerHTML=selectedRow.qid;
                document.getElementById("qtitle").innerHTML=selectedRow.title;
                document.getElementById("queue").innerHTML=selectedRow.oper; 
                var tabs = $('#tt').tabs('tabs');  
		        for(var j=0;j<tabs.length;j++){  
		          var x = tabs[j].panel('options').title;        
		          if(x==theQue) { $('#tt').tabs('select',theQue);return;}
		          else  if(x.match(/^<span>修改序列<\/span><input type=hidden value=/g)!=null) 
		          {
		            $('#tt').tabs('select',x);
		            var y = $('#tt').tabs('getSelected');
		            $("#tt").tabs('update',{tab:y,options:{title:theQue, content : '<iframe name=UpdateQue frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>'}});
		            //y.panel('refresh');
		            return;
		          }		        
		        }
		        $('#tt').tabs('add', {
				    title :theQue,
				    content : '<iframe name=UpdateQue frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>',
                    closable : true
			    }); 
               }                             
            },'-',
            {
               text: "查看",
               iconCls: "icon-ok",
                handler: function () { 
               if(fquerow()==-1) {alert("请选择一行!");return;}
                var selectedRow = $('#queuelist').datagrid('getSelected');
                var theQue='<span>查看序列</span><input type=hidden value='+selectedRow.qid+'/>';
                if ($('#tt').tabs('exists',  theQue)) { $('#tt').tabs('select', theQue); } 
                else {
                  var url="${pageContext.request.contextPath }/pages/push/QueueLook.jsp";
                  document.getElementById("qqid").innerHTML=selectedRow.qid;
                  document.getElementById("qtitle").innerHTML=selectedRow.title;
                  document.getElementById("queue").innerHTML=selectedRow.oper;
                  $('#tt').tabs('add',
			      {
				   title :theQue,
				   content : '<iframe name=UpdateQue frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>',
                   closable : true
			      });   	}	
               }
            },'-']
			});
	   });       
       function qlookpic(v){	
	      $('#qclickpic').dialog({  
            title: '查看图片',  
            width: 320,  
            height: 500,  
            left:(screen.width-320)/2,
            top:(screen.height-500)/3,
            closed: false,    
            cache: false,    
            href: '',    
            modal: true  
          });  
          $('#qclickpic').dialog('refresh', 'pages/push/ContentLoadImage.jsp?url='+v);  
	   }
	   function qSaveAtom(){
	    var para= document.getElementById("qsaveatompara").value;
	    if( $("#qshowtime").val().replace(/(^\s*)|(\s*$)/g, "")=='')  {alert("显示时长不能为空!");return false;}  var str=$("#qshowtime").val();
	    if( str.match(/^0*[1-9]$|^0*[1-9]\.[0-9]$|^0*\.[1-9]$/g)==null)   {alert("显示时长应该为大于0小于10的一个数，且只保留一位小叔!");return false;}  
	    if( $("#qenddate").datetimebox('getValue')=='')  {alert("您还没有选择有效日期");return false;}
	    var row = $('#choosecontentlist').datagrid('getSelected');
	    if($('#choosecontentlist').datagrid('getRowIndex',row)==-1) {alert("请选择一行内容!");return false;}
	    if(para=="AddAdd")
	    {
	      document.getElementById("qcid").innerHTML=row.cid;
	      document.getElementById("qshowtime1").innerHTML=document.getElementById("qshowtime").value;
	      document.getElementById("qenddate1").innerHTML= $("#qenddate").datetimebox('getValue');
	      document.getElementById("qnamex").innerHTML=row.namex;
	      frames["NewQue"].document.getElementById("SaveNewAtomBtn").click(); 
	    }
	    else if(para=="UpdateAdd")
	    {
	      document.getElementById("qaid").innerHTML="-1";
	      document.getElementById("qcid").innerHTML=row.cid;
	      document.getElementById("qshowtime1").innerHTML=document.getElementById("qshowtime").value;
	      document.getElementById("qenddate1").innerHTML= $("#qenddate").datetimebox('getValue');
	      document.getElementById("qnamex").innerHTML=row.namex;
	      frames["UpdateQue"].document.getElementById("SaveNewAtomBtn").click(); 	      
	    }
	    else if(para=="AddUpdate")
	    {
	      document.getElementById("qcid").innerHTML=row.cid;
	      document.getElementById("qshowtime1").innerHTML=document.getElementById("qshowtime").value;
	      document.getElementById("qenddate1").innerHTML= $("#qenddate").datetimebox('getValue');
	      document.getElementById("qnamex").innerHTML=row.namex;
	      frames["NewQue"].document.getElementById("SaveUpdateAtomBtn").click(); 
	    }
	    else if(para=="UpdateUpdate")
	    {	    
	      document.getElementById("qcid").innerHTML=row.cid;
	      document.getElementById("qshowtime1").innerHTML=document.getElementById("qshowtime").value;
	      document.getElementById("qenddate1").innerHTML= $("#qenddate").datetimebox('getValue');
	      document.getElementById("qnamex").innerHTML=row.namex;
	      frames["UpdateQue"].document.getElementById("SaveUpdateAtomBtn").click(); 
	    }
	    else return;
	    $("#qdlg").dialog("close"); 	  
       }
       function fquerow(){
          var row = $('#queuelist').datagrid('getSelected');
          return $('#queuelist').datagrid('getRowIndex',row);       
       } 
       function SortQueue(paraqid,paraque)
       {
           var url="${pageContext.request.contextPath }/pages/push/QueueSort.jsp";
           document.getElementById("qqid").innerHTML=paraqid;
           document.getElementById("queue").innerHTML=paraque;
           var sQuetitle='<span>重新排序列</span><input type=hidden value='+paraqid+'/>';
           $('#tt').tabs('add',
		   {
			 title :sQuetitle,
			 content : '<iframe name=SortQue frameborder=0  style="width:100%;height:100%" src='+ url+ ' ></iframe>',
             closable : true
		    });   
       } 
      function qSortQueCloseTab()
	  {
	      $('#tt').tabs('close','<span>重新排序列</span><input type=hidden value='+document.getElementById("qqid").innerHTML+'/>');
	      $('#queuelist').datagrid('reload');  
	  } 
	  function  qLookAtomCloseTab()
	   {
	      $('#tt').tabs('close','<span>查看序列</span><input type=hidden value='+document.getElementById("qqid").innerHTML+'/>');
	   }    
	   function Imitate(v)
	   {  $('#qclickpic').dialog({  
            title: '显示模拟',  
            width: 320,  
            height: 500, 
            left:(screen.width-320)/2,
            top:(screen.height-500)/3, 
            closed: false,    
            cache: false,    
            href: '',    
            modal: true  
          });  
          $('#qclickpic').dialog('refresh', 'pages/push/QueueImitate.jsp?url='+v);
	   }  
    </script>	
    
	<script type="text/javascript">	
	   //AddQue
	   function qAddAtomCloseTab()
	   {
	      $('#tt').tabs('close','<span>添加新序列</span>');
	      $('#queuelist').datagrid('reload');  
	   }
       function qAddNewAtom(){
 	      document.getElementById("qshowtime").value="";
 	      $("#qenddate").datetimebox("setValue","");
 	      var flag=0;
          $('#choosecontentlist').datagrid({
		 	idField:'cid',	
			title:'内容信息列表',
			fit:true,
			url:'servlet/CtServlet?method=GetContentList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,
			pageNumber:1,
			onLoadSuccess:function(data){
			  if(flag==0) {$('#choosecontentlist').datagrid('selectRow',0); flag=1;}
			},			
			columns:[[
				{
					field:'ok' ,
					checkbox:true ,
					width:20
				},	
				{
					field:'title' ,
					title:'标题' ,
					width:145
				},				
				{
					field:'sort' ,
					title:'类别' ,
					width:145
				},						
				{
					field:'namex' ,
					title:'格式' ,
					width:145, 
					formatter: function(value,row,index){
       									return adshowform(value); 
    						   }  
				},
				{
					field:'cid',
					title:'快速浏览',
					width:80 ,
					formatter: function(value,row,index){					    
       					  return '<a href="javascript:" onclick="qlookpic(\'' + row.namex+ '\')" style="font:12px;">点击查看</a>'; 
    					}   
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
		  }); 
	      $('#qdlg').dialog('open').dialog('setTitle','添加新节点');	
	      document.getElementById("qsaveatompara").value="AddAdd";
       }
        function qAddUpdateAtom(){
          var para = frames["NewQue"].document.getElementById("choice").innerHTML.split("*");
 	      document.getElementById("qshowtime").value=para[1];
 	      $("#qenddate").datetimebox("setValue",para[2].replace(/\#/g," "));
 	      var selrow=-1;
 	      var find=0;
 	      var selpage=1;
          $('#choosecontentlist').datagrid({
		 	idField:'cid',	
			title:'内容信息列表',
			fit:true,
			url:'servlet/CtServlet?method=GetContentList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,
			pageNumber:1,
			onLoadSuccess:function(data){
			if(find==1) {			
			   $('#choosecontentlist').datagrid('selectRow',selrow); find=2;
			}
			if(find==0)	{
			   selpage++;
			   $('#choosecontentlist').datagrid('options').pageNumber =selpage;   			
			   $('#choosecontentlist').datagrid('getPager').pagination({pageNumber: selpage});
			   $('#choosecontentlist').datagrid('reload');
			}       
			},						
			columns:[[
				{
					field:'ok' ,
					checkbox:true ,
					width:20
				},	
				{
					field:'title' ,
					title:'标题' ,
					width:145
				},				
				{
					field:'sort' ,
					title:'类别' ,
					width:145
				},						
				{
					field:'namex' ,
					title:'格式' ,
					width:145, 
					formatter: function(value,row,index){
       									return adshowform(value); 
    						   }  
				},
				{
					field:'cid',
					title:'快速浏览',
					width:80 ,
					formatter: function(value,row,index){
					     if(row.cid==para[0]&&find==0)         {	
					          find=1;			        
					          selrow= index;		
					      }					    
       					  return '<a href="javascript:" onclick="qlookpic(\'' + row.namex+ '\')" style="font:12px;">点击查看</a>'; 
    					}   
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
		  }); 
	      $('#qdlg').dialog('open').dialog('setTitle','修改节点');	
	      document.getElementById("qsaveatompara").value="AddUpdate";
       }
	</script>
	
    <script type="text/javascript">	
      //UpdateQue
      function qUpdateAtomCloseTab()
	  {
	      $('#tt').tabs('close','<span>修改序列</span><input type=hidden value='+document.getElementById("qqid").innerHTML+'/>');
	      $('#queuelist').datagrid('reload');  
	  }
	   function qUpdateNewAtom(){
 	      document.getElementById("qshowtime").value="";
 	      $("#qenddate").datetimebox("setValue","");
 	      var flag=0;
          $('#choosecontentlist').datagrid({
		 	idField:'cid',	
			title:'内容信息列表',
			fit:true,
			url:'servlet/CtServlet?method=GetContentList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,
			pageNumber:1,
			onLoadSuccess:function(data){
			  if(flag==0) {$('#choosecontentlist').datagrid('selectRow',0); flag=1;}
			},			
			columns:[[
				{
					field:'ok' ,
					checkbox:true ,
					width:20
				},	
				{
					field:'title' ,
					title:'标题' ,
					width:145
				},				
				{
					field:'sort' ,
					title:'类别' ,
					width:145
				},						
				{
					field:'namex' ,
					title:'格式' ,
					width:145, 
					formatter: function(value,row,index){
       									return adshowform(value); 
    						   }  
				},
				{
					field:'cid',
					title:'快速浏览',
					width:80 ,
					formatter: function(value,row,index){					    
       					  return '<a href="javascript:" onclick="qlookpic(\'' + row.namex+ '\')" style="font:12px;">点击查看</a>'; 
    					}   
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
		  }); 
	      $('#qdlg').dialog('open').dialog('setTitle','添加新节点');	
	      document.getElementById("qsaveatompara").value="UpdateAdd";
       }
       function qUpdateUpdateAtom(){
          var para = frames["UpdateQue"].document.getElementById("choice").innerHTML.split("*");
 	      document.getElementById("qshowtime").value=para[2];
 	      $("#qenddate").datetimebox("setValue",para[3].replace(/\#/g," "));
 	      var selrow=-1;
 	      var find=0;
 	      var selpage=1;
          $('#choosecontentlist').datagrid({
		 	idField:'cid',	
			title:'内容信息列表',
			fit:true,
			url:'servlet/CtServlet?method=GetContentList' ,
			striped: true ,					//隔行变色特性 
			loadMsg: '数据正在加载,请耐心的等待...' ,
			rownumbers:true ,
			editable:true, 
			singleSelect:true,
			pageNumber:1,
			onLoadSuccess:function(data){
			if(find==1) {			
			   $('#choosecontentlist').datagrid('selectRow',selrow); find=2;
			}
			if(find==0)	{
			   selpage++;
			   $('#choosecontentlist').datagrid('options').pageNumber =selpage;   			
			   $('#choosecontentlist').datagrid('getPager').pagination({pageNumber: selpage});
			   $('#choosecontentlist').datagrid('reload');
			}       
			},						
			columns:[[
				{
					field:'ok' ,
					checkbox:true ,
					width:20
				},	
				{
					field:'title' ,
					title:'标题' ,
					width:145
				},				
				{
					field:'sort' ,
					title:'类别' ,
					width:145
				},						
				{
					field:'namex' ,
					title:'格式' ,
					width:145, 
					formatter: function(value,row,index){
       									return adshowform(value); 
    						   }  
				},
				{
					field:'cid',
					title:'快速浏览',
					width:80 ,
					formatter: function(value,row,index){
					     if(row.cid==para[1]&&find==0)         {	
					          find=1;			        
					          selrow= index;		
					      }					    
       					  return '<a href="javascript:" onclick="qlookpic(\'' + row.namex+ '\')" style="font:12px;">点击查看</a>'; 
    					}   
				}
				]] ,
			pagination: true , 
			pageSize: 5 ,
			pageList:[5,10,15,20,50]
		  }); 
	      $('#qdlg').dialog('open').dialog('setTitle','修改节点');	
	      document.getElementById("qsaveatompara").value="UpdateUpdate";
       }
    </script>	
  
</body>
</html>