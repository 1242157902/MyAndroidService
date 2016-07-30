<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>ContentList</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
   <input id="ptlid"  type="hidden" value="<%=request.getParameter("ptlid")%>" />
   <div id="pBarDlg" class="easyui-dialog"  align="center" closed="true" >     
     <br><br><br>  
     <div id="prBar" class="easyui-progressbar" style="width:400px;" align="left"></div> 
     <br><br> 
     <a id="pBarBtnOk" class="easyui-linkbutton" onclick="CancelPush()">确定</a>
     <a id="pBarBtnCancel" class="easyui-linkbutton" onclick="CancelPush()">取消</a>
   </div>
   <div id="pushdetaillay" class="easyui-layout" style="width:100%;height:100%" >
   <div region="north" title="查询条件_<%=new String(request.getParameter("ptltitle").getBytes("ISO-8859-1"),"UTF-8")%>" collapsed=FALSE style="width:100%;height:200px;" >   
   <form id=searchbox method="post"  style="width:100%;table-layout:fixed;">
   <table style="width:100%;table-layout:fixed;overflow:hidden;font-size:12px;" >
   <tr> 
      <td class="pushqrctd"   colspan=4>
      <span class="pushtbblank"></span>手机号<input id="phonenum" name="phonenum" type=text style="width:910px;height:22px;" class="textbox" value=""/>
      </td>
      <td class="pushqrctd">
      <span class="pushtbblank"></span>职　位<select id="qrcposition"  name="qrcposition" ></select>
	  </td>
   </tr> 
   <tr>   
      <td class="pushqrctd" >
      <span class="pushtbblank"></span>性　别<select id="qrcsex" name="qrcsex" class="easyui-combotree" style="width:170px;"  data-options="url:'pages/push/data/gender.json',multiple:true,panelHeight:75"></select>  
	  </td>
      <td class="pushqrctd">
      <span class="pushtbblank"></span>年　龄<select id="qrcage" name="qrcage"  class="easyui-combotree" style="width:170px;"  data-options="url:'pages/push/data/age.json',multiple:true,panelHeight:170" ></select>  
      </td>
      <td class="pushqrctd">
      <span class="pushtbblank"></span>所属单位<select id="qrccompany"  name="qrccompany" ></select>
	  </td>
      <td class="pushqrctd">
       <span class="pushtbblank"></span>销售商<select id="qrcseller" name="qrcseller" ></select> 
	  </td>     
	   <td class="pushqrctd">
       <span class="pushtbblank"></span>推送情况<select id="qrcphstatus" name="qrcphstatus"  class="easyui-combotree" style="width:170px;" data-options="url:'pages/push/data/phstatus.json',multiple:true,panelHeight:75" ></select>  
	  </td>           
   </tr> 
   <tr>   
     <td class="pushqrctd">
	   <span class="pushtbblank"></span>运营商<select id="qrcprovi" name="qrcprovi"  class="easyui-combotree" style="width:170px;" data-options="url:'pages/push/data/provider.json',multiple:true,panelHeight:100"></select>  
	 </td>  
     <td class="pushqrctd">
	   <span class="pushtbblank"></span>归属地<select id="qrcarea" name="qrcarea"  class="easyui-combotree" style="width:170px;" data-options="url:'servlet/JsonServlet?method=GetAreaList',multiple:true,panelHeight:200" ></select>  
     </td>
     <td class="pushqrctd">  
       <span class="pushtbblank"></span>手机品牌<select id="qrcbrand" name="qrcbrand" class="easyui-combotree" style="width:170px;" data-options="url:'servlet/JsonServlet?method=GetBrandList',multiple:true,panelHeight:200"  ></select>  
	 </td>
	 <td class="pushqrctd">  
       <span class="pushtbblank"></span>操作系统<select id="qrcphoneos" name="qrcphoneos"  class="easyui-combotree" style="width:158px;" data-options="url:'servlet/JsonServlet?method=GetMbOsList',multiple:true,panelHeight:200" ></select>  
	 </td> 
	 <td class="pushqrctd">
	     <span class="pushtbblank"></span>接收情况<select id="qrcrecstatus" name="qrcrecstatus"  class="easyui-combotree" style="width:170px;" data-options="url:'pages/push/data/recstatus.json',multiple:true,panelHeight:75" ></select>  
	 </td>
   </tr>
   <tr>
    <td class="pushqrctd" style="padding-top:10px;border-bottom:0px;border-right:0px;" colspan=5 > 
	  <span class="pushtbblank"></span><a id="searchbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a><span style="padding-left:50px;"></span><a id="clearbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
	</td>
   </tr>   
  </table>
  </form>
  </div>
  <div region="center" style="height:100%;"><table id="pushdetail"></table></div>
  </div>
  <script type="text/javascript">
  var curPage=1;
  var all=false;
  var pageNo=1;
  var barTotal;
  var barCur;
  var recRows;
  var cancelph=false;
  var interuptpush;
  var vvv="";
  var ttt="";
  var iii="";
  $(function() {
    var rolex=<%=new String(request.getParameter("role").getBytes("ISO-8859-1"),"UTF-8")%>;
    if(rolex!=null&&rolex!=1) 
    {  
	     var rolesplit=rolex.split("*");
	     if(rolesplit[0]==2) iii="qrcseller";
	     if(rolesplit[0]==3) iii="qrccompany";
	      vvv=rolesplit[1];
	      ttt=rolesplit[2];
    }
    $('#qrccompany').combotree({   
       url:'servlet/JsonServlet?method=GetCompanyList', 
       multiple:true,    
       panelHeight:200,
       width:170,  
       onLoadSuccess: function () { if(iii=="qrccompany"){ $("#qrccompany").combo("setValue",vvv).combo("setText",ttt);$("#qrccompany").combo("disable");}  },
       onHidePanel:function(){
	      $("#qrcposition").combotree({url:"servlet/JsonServlet?method=GetPositionList&comid="+$('#qrccompany').combotree('getValues')});       
       }
     }); 
      $('#qrcposition').combotree({   
       url:'servlet/JsonServlet?method=GetPositionList&comid=', 
       multiple:true,    
       panelHeight:200,
       width:170
       }); 
    $('#qrcseller').combotree({   
       url:'servlet/JsonServlet?method=GetCompanyList', 
       multiple:true, 
       panelHeight:200,
       width:170, 
       onLoadSuccess: function () { if(iii=="qrcseller"){ $("#qrcseller").combo("setValue",vvv).combo("setText",ttt);$("#qrcseller").combo("disable");}  }    
     }); 
     $('#pushdetail').datagrid({
     idField:'imei',	
     title:'推送详情_<%=new String(request.getParameter("ptltitle").getBytes("ISO-8859-1"),"UTF-8")%>',
     fit:true,
     height:450 ,
     url:'servlet/PhServlet?method=GetAccepterList&ptlid=<%=request.getParameter("ptlid")%>' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     onLoadSuccess:function(data){ 
       if(all) {
         $('#pushdetail').datagrid('selectAll');
         if(curPage<pageNo)  {
                   curPage++;
                   $('#pushdetail').datagrid('options').pageNumber=curPage;
                   $('#pushdetail').datagrid('getPager').pagination({pageNumber:curPage});
                   $('#pushdetail').datagrid('reload'); 
                   }
          else all=false;
          }  
       } ,  	
     onClickRow: function(rowIndex, rowData){  
          all=false;         
       },
     columns:[[
				{
					field:'ok' ,
					checkbox:true,
					width:30
				},				
				{
					field:'gender' ,
					title:'性别' ,
					width:40,
					formatter: function(value,row,index){return enGender(value);  }  
				},				
				{
					field:'birth' ,
					title:'年龄' ,
					width:40 ,
					formatter: function(value,row,index){return enAge(value);  }  
				},
				{
					field:'mbno' ,
					title:'手机号',
					width:100
				},				
				{
					field:'mbos' ,
					title:'操作系统',
					width:110
				},
				{
					field:'provider' ,
					title:'运营商' ,
					width:110
				},
				{
					field:'area' ,
					title:'归属地' ,
					width:110
				},
				{
					field:'mbtype' ,
					title:'手机型号' ,
					width:120
				},			
				{
					field:'company' ,
					title:'所属单位',
					width:110
				},			
				{
					field:'seller' ,
					title:'销售商',
					width:110
				},
				{
					field:'allque' ,
					title:'推送状态' ,
					width:80,
					formatter: function(value,row,index){return enPhstatus(value);  }  
				},
				{
					field:'recstatus' ,
					title:'接收情况' ,
					width:80,
					formatter: function(value,row,index){return enRecstatus(value);  }  
				},	
				{
					field:'position' ,
					title:'职位' ,
					width:80
				},	
				{
					field:'imei' ,
					title:'操作' ,
					width:80,
					formatter: function(value,row,index){return enPhoption(row.allque,value);  }  
				}
				]] ,
     pagination: true , 
     pageSize: 20 ,
     pageList:[5,10,15,20,50], 		
     toolbar: [
			{
               text: "确认推送",
               iconCls: "icon-edit",
               handler: function () { 
               getSelections('推送进度','0');		
               }
            }, '-',
            {
               text: "取消推送",
               iconCls: "icon-edit",
               handler:function () {  
                getSelections('取消推送进度','1');	                      
               }
              
            },'-',
            {
               text: "全部选中",
               iconCls: "icon-edit",
               handler:function () {  
                 var options  = $('#pushdetail').datagrid('getPager').data("pagination").options;  
                 pageNo = Math.ceil(options.total/options.pageSize);                 
                 all=true; curPage=0; $('#pushdetail').datagrid('reload');  
               }
            },'-',
            {
               text: "全部取消",
               iconCls: "icon-edit",
               handler:function (){
                   $('#pushdetail').datagrid('clearSelections'); 
                   all=false;
			            }		 
            },'-']
     });
     $('#searchbtn').click(function(){
        var tmbno=$('#phonenum').val().replace(/(^\s*)|(\s*$)/g, ""); 
        if(tmbno.match(/^\d+(\s*,\s*\d+)*$/g)==null&&tmbno!="")  {alert("请按格式填写手机号码，并用逗号隔开，如 13800125655，13800125656 ");return;}
        $('#pushdetail').datagrid('clearSelections'); 
		$('#pushdetail').datagrid('load' ,serializeForm($('#searchbox')));
     });
     $('#clearbtn').click(function(){
	    $('#searchbox').form('clear');
        if(iii=="qrccompany") { $("#qrccompany").combo("setValue",vvv).combo("setText",ttt);}
        if(iii=="qrcseller") { $("#qrcseller").combo("setValue",vvv).combo("setText",ttt);}
	    $('#pushdetail').datagrid('clearSelections'); 
		$('#pushdetail').datagrid('load' ,serializeForm($('#searchbox')));		
     });
  });		
  function serializeForm(form){
	   var obj = {};
	   $.each(form.serializeArray(),function(index){
					if(obj[this['name']]){
						obj[this['name']] = obj[this['name']] + ','+this['value'];
					} else {
						obj[this['name']] =this['value'];
					}
		});
		return obj;
  }
  function getSelections(ti,status){
    recRows= $('#pushdetail').datagrid('getSelections');
    if(recRows.length==0) {alert("没有需要推送的对象");return;}
    barTotal=recRows.length;
    barCur=0;
    cancelph=false;
    $("#pBarBtnCancel").show();$("#pBarBtnOk").hide();
    $('#pBarDlg').dialog({  
            title: ti,  
            width: 600,  
            height: 200,  
            left:(screen.width-600)/2,
            top:(screen.height-200)/3,
            closed: false,    
            cache: false,    
            modal: true,
            onClose:function(){cancelph=true;clearTimeout(interuptpush); $('#pushdetail').datagrid('clearSelections');}
    }); 
    startPush(status);
  }
  function startPush(status) {
    var accepter=""; 
    for (var i = 0; i < 5; i++) { 
     if(recRows[barCur].allque==status) accepter+=",'"+recRows[barCur].imei+"'";
     barCur++;
     if(barCur>=barTotal) break;
    }
    if(accepter!="")
    {
      accepter="("+accepter.substring(1)+")";
      if(status=='0') batchokpush(accepter);
      if(status=='1') batchcancelpush(accepter);
    }
    else  {
      if(barCur>barTotal) {finishPush();return; }
      if (barCur<=barTotal&&cancelph==false) {
         $('#prBar').progressbar('setValue', Math.floor((barCur/barTotal)*100));
         if(barCur==barTotal) {finishPush();return;}
         else interuptpush=setTimeout("startPush("+status+")",500); 
       } 
      else  { $('#pushdetail').datagrid('clearSelections'); $('#pushdetail').datagrid('reload');}
    }
  } 
  function finishPush()
  { 
    $("#pBarBtnOk").show();
    $("#pBarBtnCancel").hide();
    $('#pushdetail').datagrid('clearSelections'); 
    $('#pushdetail').datagrid('reload');    
    all=false;
  }
  function CancelPush()
  {
    cancelph=true;
    $('#pBarDlg').dialog('close');
  }
  function singleokpush(i)
  {
     var p= document.getElementById("ptlid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PhServlet?method=SingleOkPush", 
     data: {imei:i,pid:p},  
     dataType: "json",  
     success: function(data) {  if(data!=null)    {alert("推送成功");$('#pushdetail').datagrid('reload');}  else   alert("推送失败"); },
     error:function(data) {   alert("推送失败"); }
     });
     $('#pushdetail').datagrid('clearSelections'); 
  }
  function singlecancelpush(i)
  {
     var p= document.getElementById("ptlid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PhServlet?method=SingleCancelPush", 
     data: {imei:i,pid:p},  
     dataType: "json",  
     success: function(data) {  if(data!=null)    {alert("取消成功");$('#pushdetail').datagrid('reload');}  else   alert("取消失败"); },
     error:function(data) {   alert("取消失败"); }
     }); 
     $('#pushdetail').datagrid('clearSelections');
  }
  function batchokpush(v)
  {
     var w= document.getElementById("ptlid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PhServlet?method=BatchOkPush", 
     data: {imeis:v,pid:w},  
     dataType: "json",  
     success: function(data) {      
        if(data!=null)    {
          if(barCur>barTotal) {finishPush();return;}
          if (barCur<=barTotal&&cancelph==false) {
            $('#prBar').progressbar('setValue', Math.floor((barCur/barTotal)*100));
            if(barCur==barTotal) {finishPush();return;}
            else interuptpush=setTimeout("startPush('0')", 500); 
          } 
          else {$('#pushdetail').datagrid('clearSelections'); $('#pushdetail').datagrid('reload');}
        } 
        else   {cancelph=true; $('#pushdetail').datagrid('clearSelections'); $("#pBarBtnOk").show();$("#pBarBtnCancel").hide(); alert("推送失败");} },
     error:function(data) {  cancelph=true; $('#pushdetail').datagrid('clearSelections'); $("#pBarBtnOk").show();$("#pBarBtnCancel").hide(); alert("推送失败");}
     }); 
  }
  function batchcancelpush(v)
  {
     var w= document.getElementById("ptlid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PhServlet?method=BatchCancelPush", 
     data: {imeis:v,pid:w},  
     dataType: "json",  
     success: function(data) {  
     if(data!=null)    {
        if(barCur>barTotal) {finishPush();return;}
          if (barCur<=barTotal&&cancelph==false) {
            $('#prBar').progressbar('setValue', Math.floor((barCur/barTotal)*100));
            if(barCur==barTotal) {finishPush();return;}
            else interuptpush=setTimeout("startPush('1')",500); 
          } 
         else {$('#pushdetail').datagrid('clearSelections'); $('#pushdetail').datagrid('reload');}
     }  
     else  {cancelph=true;  $('#pushdetail').datagrid('clearSelections'); $("#pBarBtnOk").show();$("#pBarBtnCancel").hide(); alert("取消失败"); }},
     error:function(data) { cancelph=true;  $('#pushdetail').datagrid('clearSelections'); $("#pBarBtnOk").show();$("#pBarBtnCancel").hide(); alert("取消失败");}
     }); 
  }
 </script>
</body>
</html>
   