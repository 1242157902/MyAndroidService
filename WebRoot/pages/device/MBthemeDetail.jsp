<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">    
    <title>MbThemeDetail</title> 
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
</head>
<body>
   <input id="mbtid"  type="hidden" value="<%=request.getParameter("mbtid")%>" />
   
   <div id="mbtpBarDlg" class="easyui-dialog"  align="center" closed="true" >     
     <br><br><br>  
     <div id="mbtprBar" class="easyui-progressbar" style="width:400px;" align="left"></div> 
     <br><br> 
     <a id="mbtpBarBtnOk" class="easyui-linkbutton" onclick="mbtCancelPush()">确定</a>
     <a id="mbtpBarBtnCancel" class="easyui-linkbutton" onclick="mbtCancelPush()">取消</a>
   </div>
   
   <div id="mbtdetaillay" class="easyui-layout" style="width:100%;height:100%" >
   <div region="north" title="查询条件_<%=new String(request.getParameter("mbtname").getBytes("ISO-8859-1"),"UTF-8")%>" collapsed=FALSE style="width:100%;height:200px;" >   
   <form id="mbtsearchbox" method="post"  style="width:100%;table-layout:fixed;">
   <table style="width:100%;table-layout:fixed;overflow:hidden;font-size:12px;" >
   <tr> 
      <td class="pushqrctd"   colspan=5>
      <span class="pushtbblank"></span>手机号<input id="mbtno" name="mbtno" type=text style="width:1112px;height:22px;" class="textbox" value=""/>
      </td>
   </tr> 
   <tr>   
      <td class="pushqrctd" >
      <span class="pushtbblank"></span>性　别<select id="mbtsex" name="mbtsex" class="easyui-combotree" style="width:170px;"  data-options="url:'pages/push/data/gender.json',multiple:true,panelHeight:75"></select>  
	  </td>
      <td class="pushqrctd">
      <span class="pushtbblank"></span>年　龄<select id="mbtage" name="mbtage"  class="easyui-combotree" style="width:170px;"  data-options="url:'pages/push/data/age.json',multiple:true,panelHeight:170" ></select>  
      </td>
      <td class="pushqrctd">
      <span class="pushtbblank"></span>所属单位<select id="mbtcompany"  name="mbtcompany" ></select>
	  </td>
      <td class="pushqrctd">
       <span class="pushtbblank"></span>销售商<select id="mbtseller" name="mbtseller" ></select> 
	  </td>     
	   <td class="pushqrctd">
       <span class="pushtbblank"></span>推送情况<select id="mbtphstatus" name="mbtphstatus"  class="easyui-combotree" style="width:170px;" data-options="url:'pages/push/data/phstatus.json',multiple:true,panelHeight:75" ></select>  
	  </td>           
   </tr> 
   <tr>   
     <td class="pushqrctd">
	   <span class="pushtbblank"></span>运营商<select id="mbtprovi" name="mbtprovi"  class="easyui-combotree" style="width:170px;" data-options="url:'pages/push/data/provider.json',multiple:true,panelHeight:100"></select>  
	 </td>  
     <td class="pushqrctd">
	   <span class="pushtbblank"></span>归属地<select id="mbtarea" name="mbtarea"  class="easyui-combotree" style="width:170px;" data-options="url:'servlet/JsonServlet?method=GetAreaList',multiple:true,panelHeight:200" ></select>  
     </td>
     <td class="pushqrctd">  
       <span class="pushtbblank"></span>手机品牌<select id="mbtbrand" name="mbtbrand" class="easyui-combotree" style="width:170px;" data-options="url:'servlet/JsonServlet?method=GetBrandList',multiple:true,panelHeight:200"  ></select>  
	 </td>
	 <td class="pushqrctd">  
       <span class="pushtbblank"></span>操作系统<select id="mbtphoneos" name="mbtphoneos"  class="easyui-combotree" style="width:158px;" data-options="url:'servlet/JsonServlet?method=GetMbOsList',multiple:true,panelHeight:200" ></select>  
	 </td> 
   </tr>
   <tr>
    <td class="pushqrctd" style="padding-top:10px;border-bottom:0px;border-right:0px;" colspan=5 > 
	  <span class="pushtbblank"></span><a id="mbtsearchbtn" class="easyui-linkbutton"  icon="icon-search">&nbsp;查询&nbsp;&nbsp;&nbsp;</a><span style="padding-left:50px;"></span><a id="nbtclearbtn" class="easyui-linkbutton" icon="icon-redo">&nbsp;清空&nbsp;&nbsp;&nbsp;</a>
	</td>
   </tr>   
  </table>
  </form>
  </div>
  <div region="center" style="height:100%;"><table id="mbtgrid"></table></div>
  </div>



  <script type="text/javascript">
  var mbtcurPage=1;
  var mbtall=false;
  var mbtpageNo=1;
  var mbtbarTotal;
  var mbtbarCur;
  var mbtrecRows;
  var mbtcancelph=false;
  var mbtinteruptpush;
  var mbtvvv="";
  var mbtttt="";
  var mbtiii="";
  $(function() {
    var mbtrolex=<%=new String(request.getParameter("role").getBytes("ISO-8859-1"),"UTF-8")%>;
    if(mbtrolex!=null&&mbtrolex!=1) 
    {  
	     var rolesplit=mbtrolex.split("*");
	     if(rolesplit[0]==2) mbtiii="mbtseller";
	     if(rolesplit[0]==3) mbtiii="mbtcompany";
	      mbtvvv=rolesplit[1];
	      mbtttt=rolesplit[2];
    }
    $('#mbtcompany').combotree({   
       url:'servlet/JsonServlet?method=GetCompanyList', 
       multiple:true,    
       panelHeight:200,
       width:170,  
       onLoadSuccess: function () { if(mbtiii=="mbtcompany"){ $("#mbtcompany").combo("setValue",mbtvvv).combo("setText",mbtttt);$("#mbtcompany").combo("disable");}  }
     }); 
    $('#mbtseller').combotree({   
       url:'servlet/JsonServlet?method=GetCompanyList', 
       multiple:true, 
       panelHeight:200,
       width:170, 
       onLoadSuccess: function () { if(mbtiii=="mbtseller"){ $("#mbtseller").combo("setValue",mbtvvv).combo("setText",mbtttt);$("#mbtseller").combo("disable");}  }    
     }); 
     $('#mbtgrid').datagrid({
     idField:'imei',	
     title:'推送详情_<%=new String(request.getParameter("mbtname").getBytes("ISO-8859-1"),"UTF-8")%>',
     fit:true,
     height:450 ,
     url:'servlet/PlServlet?method=GetAccepterList&mbtid=<%=request.getParameter("mbtid")%>' , 
     striped: true ,	
     loadMsg: '数据正在加载,请耐心的等待...' ,
     rownumbers:true,
     editable:true, 
     onLoadSuccess:function(data){ 
       if(mbtall) {
         $('#mbtgrid').datagrid('selectmbtall');
         if(mbtcurPage<mbtpageNo)  {
                   mbtcurPage++;
                   $('#mbtgrid').datagrid('options').pageNumber=mbtcurPage;
                   $('#mbtgrid').datagrid('getPager').pagination({pageNumber:mbtcurPage});
                   $('#mbtgrid').datagrid('reload'); 
                   }
          else mbtall=false;
          }  
       } ,  	
     onClickRow: function(rowIndex, rowData){  
          mbtall=false;         
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
                 var options  = $('#mbtgrid').datagrid('getPager').data("pagination").options;  
                 mbtpageNo = Math.ceil(options.total/options.pageSize);                 
                 mbtall=true; mbtcurPage=0; $('#mbtgrid').datagrid('reload');  
               }
            },'-',
            {
               text: "全部取消",
               iconCls: "icon-edit",
               handler:function (){
                   $('#mbtgrid').datagrid('clearSelections'); 
                   mbtall=false;
			            }		 
            },'-']
     });
     $('#mbtsearchbtn').click(function(){  
        var mbtmbno=$('#mbtno').val().replace(/(^\s*)|(\s*$)/g, ""); 
        if(mbtmbno.match(/^\d+(\s*,\s*\d+)*$/g)==null&&mbtmbno!="")  {alert("请按格式填写手机号码，并用逗号隔开，如 13800125655，13800125656 ");return;}
        $('#mbtgrid').datagrid('clearSelections'); 
		$('#mbtgrid').datagrid('load' ,serializeForm($('#mbtsearchbox')));
     });
     $('#nbtclearbtn').click(function(){
	    $('#mbtsearchbox').form('clear');
        if(mbtiii=="mbtcompany") { $("#mbtcompany").combo("setValue",mbtvvv).combo("setText",mbtttt);}
        if(mbtiii=="mbtseller") { $("#mbtseller").combo("setValue",mbtvvv).combo("setText",mbtttt);}
	    $('#mbtgrid').datagrid('clearSelections'); 
		$('#mbtgrid').datagrid('load' ,serializeForm($('#mbtsearchbox')));		
     });
  });		
  function serializeForm(mbtform){
	   var mbtobj = {};
	   $.each(mbtform.serializeArray(),function(index){
					if(mbtobj[this['name']]){
						mbtobj[this['name']] = mbtobj[this['name']] + ','+this['value'];
					} else {
						mbtobj[this['name']] =this['value'];
					}
		});
		return mbtobj;
  }
  function getSelections(ti,status){
    mbtrecRows= $('#mbtgrid').datagrid('getSelections');
    if(mbtrecRows.length==0) {alert("没有需要推送的对象");return;}
    mbtbarTotal=mbtrecRows.length;
    mbtbarCur=0;
    mbtcancelph=false;
    $("#mbtpBarBtnCancel").show();$("#mbtpBarBtnOk").hide();
    $('#mbtpBarDlg').dialog({  
            title: ti,  
            width: 600,  
            height: 200,  
            left:(screen.width-600)/2,
            top:(screen.height-200)/3,
            closed: false,    
            cache: false,    
            modal: true,
            onClose:function(){mbtcancelph=true;clearTimeout(mbtinteruptpush); $('#mbtgrid').datagrid('clearSelections');}
    }); 
    startPush(status);
  }
  function startPush(status) {
    var accepter=""; 
    for (var i = 0; i < 5; i++) { 
     if(mbtrecRows[mbtbarCur].mbtallque==status) accepter+=",'"+mbtrecRows[mbtbarCur].imei+"'";
     mbtbarCur++;
     if(mbtbarCur>=mbtbarTotal) break;
    }
    if(accepter!="")
    {
      accepter="("+accepter.substring(1)+")";
      if(status=='0') batchokpush(accepter);
      if(status=='1') batchcancelpush(accepter);
    }
    else  {
      if(mbtbarCur>mbtbarTotal) {finishPush();return; }
      if (mbtbarCur<=mbtbarTotal&&mbtcancelph==false) {
         $('#mbtprBar').progressbar('setValue', Math.floor((mbtbarCur/mbtbarTotal)*100));
         if(mbtbarCur==mbtbarTotal) {finishPush();return;}
         else mbtinteruptpush=setTimeout("startPush("+status+")",500); 
       } 
      else  { $('#mbtgrid').datagrid('clearSelections'); $('#mbtgrid').datagrid('reload');}
    }
  } 
  function finishPush()
  { 
    $("#mbtpBarBtnOk").show();
    $("#mbtpBarBtnCancel").hide();
    $('#mbtgrid').datagrid('clearSelections'); 
    $('#mbtgrid').datagrid('reload');    
    mbtall=false;
  }
  function mbtCancelPush()
  {
    mbtcancelph=true;
    $('#mbtpBarDlg').dialog('close');
  }
  function singleokpush(i)
  {
     var mbtid= document.getElementById("mbtid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PlServlet?method=SingleOkPush", 
     data: {imei:i,tid:mbtid},  
     dataType: "json",  
     success: function(data) {  if(data!=null)    {alert("推送成功");$('#mbtgrid').datagrid('reload');}  else   alert("推送失败"); },
     error:function(data) {   alert("推送失败"); }
     });
     $('#mbtgrid').datagrid('clearSelections'); 
  }
  function singlecancelpush(i)
  {
     var mbtid= document.getElementById("mbtid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PlServlet?method=SingleCancelPush", 
     data: {imei:i,tid:mbtid},  
     dataType: "json",  
     success: function(data) {  if(data!=null)    {alert("取消成功");$('#mbtgrid').datagrid('reload');}  else   alert("取消失败"); },
     error:function(data) {   alert("取消失败"); }
     }); 
     $('#mbtgrid').datagrid('clearSelections');
  }
  function batchokpush(v)
  {
     var mbtid= document.getElementById("mbtid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PlServlet?method=BatchOkPush", 
     data: {imeis:v,tid:mbtid},  
     dataType: "json",  
     success: function(data) {      
        if(data!=null)    {
          if(mbtbarCur>mbtbarTotal) {finishPush();return;}
          if (mbtbarCur<=mbtbarTotal&&mbtcancelph==false) {
            $('#mbtprBar').progressbar('setValue', Math.floor((mbtbarCur/mbtbarTotal)*100));
            if(mbtbarCur==mbtbarTotal) {finishPush();return;}
            else mbtinteruptpush=setTimeout("startPush('0')", 500); 
          } 
          else {$('#mbtgrid').datagrid('clearSelections'); $('#mbtgrid').datagrid('reload');}
        } 
        else   {mbtcancelph=true; $('#mbtgrid').datagrid('clearSelections'); $("#mbtpBarBtnOk").show();$("#mbtpBarBtnCancel").hide(); alert("推送失败");} },
     error:function(data) {  mbtcancelph=true; $('#mbtgrid').datagrid('clearSelections'); $("#mbtpBarBtnOk").show();$("#mbtpBarBtnCancel").hide(); alert("推送失败");}
     }); 
  }
  function batchcancelpush(v)
  {
     var mbtid= document.getElementById("mbtid").value;
     $.ajax({ 
     type: "post",  
     url: "servlet/PlServlet?method=BatchCancelPush", 
     data: {imeis:v,tid:mbtid},  
     dataType: "json",  
     success: function(data) {  
     if(data!=null)    {
        if(mbtbarCur>mbtbarTotal) {finishPush();return;}
          if (mbtbarCur<=mbtbarTotal&&mbtcancelph==false) {
            $('#mbtprBar').progressbar('setValue', Math.floor((mbtbarCur/mbtbarTotal)*100));
            if(mbtbarCur==mbtbarTotal) {finishPush();return;}
            else mbtinteruptpush=setTimeout("startPush('1')",500); 
          } 
         else {$('#mbtgrid').datagrid('clearSelections'); $('#mbtgrid').datagrid('reload');}
     }  
     else  {mbtcancelph=true;  $('#mbtgrid').datagrid('clearSelections'); $("#mbtpBarBtnOk").show();$("#mbtpBarBtnCancel").hide(); alert("取消失败"); }},
     error:function(data) { mbtcancelph=true;  $('#mbtgrid').datagrid('clearSelections'); $("#mbtpBarBtnOk").show();$("#mbtpBarBtnCancel").hide(); alert("取消失败");}
     }); 
  }
 </script>
</body>
</html>