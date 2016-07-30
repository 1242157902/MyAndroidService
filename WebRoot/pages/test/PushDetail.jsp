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
   <div region="north" title="查询条件_<%=request.getParameter("ptltitle")%>" collapsed=FALSE style="height:145px;" >
   
   <form id=searchbox method="post" >
   <table style="font-size:12px;" border=1  cellpadding=1>
   <tr>   
      <td>手　机  </td><td><input id="phonenum" name="phonenum" type=text style="width:170px"/></td>
      <td>                
      &nbsp;&nbsp;操作系统 <select id="qrcphoneos" name="qrcphoneos" ></select>
      <div id="qrcphoneosdiv">
      <input type=checkbox value="1" name=phoneos /><span>Andriod</span><br/>
      <input type=checkbox value="2" name=phoneos /><span>IOS</span><br/>
      </div>	
      
      &nbsp;&nbsp;运营商<select id="qrcprovi" name="qrcprovi" ></select>
      <div id="qrcprovidiv">
      <input type=checkbox value="1" name=provi /><span>中国移动</span><br/>
      <input type=checkbox value="2" name=provi /><span>中国联通</span><br/>
      <input type=checkbox value="3" name=provi /><span>中国电信</span><br/>
      </div>
      
      &nbsp;&nbsp;手机品牌<select id="qrcbrand" name="qrcbrand" ></select>
      <div id="qrcbranddiv" >
      <input type=checkbox value="01" name=brand /><span>苹果</span><br/>
      <input type=checkbox value="02" name=brand /><span>小米</span><br/>
      <input type=checkbox value="03" name=brand /><span>三星</span><br/>
      <input type=checkbox value="04" name=brand /><span>华为</span><br/>
      <input type=checkbox value="05" name=brand /><span>酷派</span><br/>
      <input type=checkbox value="06" name=brand /><span>联想</span><br/>
      <input type=checkbox value="07" name=brand /><span>索尼</span><br/>
      <input type=checkbox value="08" name=brand /><span>诺基亚</span><br/>
      <input type=checkbox value="09" name=brand /><span>魅族</span><br/>
      <input type=checkbox value="10" name=brand /><span>HTC</span><br/>
      <input type=checkbox value="11" name=brand /><span>Vivo</span><br/>
      <input type=checkbox value="12" name=brand /><span>中兴</span><br/>
      <input type=checkbox value="13" name=brand /><span>Oppo</span><br/>    
      <input type=checkbox value="14" name=brand /><span>LG</span><br/>    
      <input type=checkbox value="15" name=brand /><span>金立 </span><br/> 
      <input type=checkbox value="16" name=brand /><span>朵唯</span><br/>
      </div>  
      
       &nbsp;&nbsp;推送情况<select id="qrcphstatus" name="qrcphstatus" ></select>
	   <div id="qrcphstatusdiv">
	   <input type=checkbox value="0" name=phstatus /><span>未推送</span><br/>
	   <input type=checkbox value="1" name=phstatus /><span>已推送</span><br/>
	   </div>  
	     
     </td>              
   </tr> 
   <tr>           
      <td>归属地</td>
      <td>
		  <select  id="area" name="area"  class="easyui-combobox" panelHeight="50" data-options="onSelect:function(para){selectarea(para.value);}">
		     <option value="01">北京市</option>
			 <option value="02">河北省</option>	
		  </select>
		  <select id="qrcarea" name="qrcarea" ></select>
		  <div id="qrcareadiv" > </div>
      </td> 
      <td>
	     &nbsp;&nbsp;性别<select id="qrcsex" name="qrcsex" ></select>
	     <div id="qrcsexdiv" >
	     <input type=checkbox value="1" name=sex /><span>男</span><br/>
	     <input type=checkbox value="2" name=sex /><span>女</span><br/>
	     </div>
	     
	     
	     &nbsp;&nbsp;年龄  <select id="qrcage" name="qrcage" ></select>
	     <div id="qrcagediv">
	     <input type=checkbox value="null-17" name=age /><span>18岁以下</span><br/>
	     <input type=checkbox value="18-22" name=age /><span>18岁-22岁</span><br/>
	     <input type=checkbox value="23-26" name=age /><span>23岁-26岁</span><br/>
	     <input type=checkbox value="27-35" name=age /><span>27岁-35岁</span><br/>
	     <input type=checkbox value="36-55" name=age /><span>36岁-55岁</span><br/>
	     <input type=checkbox value="56-null" name=age /><span>55岁以上</span><br/>
	     </div>
	     &nbsp;&nbsp;所属单位<select id="qrccompany" name="qrccompany" ></select>
	     <div id="qrccompanydiv">
	     <input type=checkbox value="0001" name=company /><span>迪信通</span><br/>
	     <input type=checkbox value="0002" name=company /><span>北方工大</span><br/>
	     </div>
	     	
	      &nbsp;&nbsp;接受情况<select id="qrcrecstatus" name="qrcrecstatus" ></select>
	     <div id="qrcrecstatusdiv">
	     <input type=checkbox value="0" name=recstatus /><span>未更新</span><br/>
	     <input type=checkbox value="1" name=recstatus /><span>已更新</span><br/>
	     </div>
       </td> 
   </tr>
   <tr>
      <td colspan=3>
        <a id="searchbtn" class="easyui-linkbutton">查询</a><span style="padding-left:30px;"></span><a id="clearbtn" class="easyui-linkbutton">清空</a>
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
  $(function() {
     initquerycombox();
     $('#pushdetail').datagrid({
     idField:'imei',	
     title:'推送详情_<%=request.getParameter("ptltitle")%>',
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
					field:'company' ,
					title:'所属单位',
					width:110,
					formatter: function(value,row,index){return enCompany(value);}  
				},				
				{
					field:'gender' ,
					title:'性别' ,
					width:50,
					formatter: function(value,row,index){return enGender(value);  }  
				},				
				{
					field:'birth' ,
					title:'年龄' ,
					width:50 ,
					formatter: function(value,row,index){return enAge(value);  }  
				},
				{
					field:'mbno' ,
					title:'手机号',
					width:110
				},				
				{
					field:'mbos' ,
					title:'操作系统',
					width:80,
					formatter: function(value,row,index){return enMbos(value);  }  
				},
				{
					field:'provider' ,
					title:'运营商' ,
					width:110, 
					height:50,
					formatter: function(value,row,index){return enProvider(value);  }  
				},
				{
					field:'area' ,
					title:'归属地' ,
					width:110,
					formatter: function(value,row,index){return enArea(value);  }  
				},
				{
					field:'mbtype' ,
					title:'手机型号' ,
					width:120,
					formatter: function(value,row,index){return enMbtype(value);  }  
				},
				{
					field:'allque' ,
					title:'推送状态' ,
					width:80,
					formatter: function(value,row,index){return enPhstatus(value);  }  
				},
				{
					field:'recstatus' ,
					title:'接受情况' ,
					width:80,
					formatter: function(value,row,index){return enRecstatus(value);  }  
				},
				{
					field:'imei' ,
					title:'操作' ,
					width:80,
					formatter: function(value,row,index){return enPhoption(row.allque,value);  }  
				}
				]] ,
     pagination: true , 
     pageSize: 5 ,
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
	    $('#pushdetail').datagrid('clearSelections'); 
		$('#pushdetail').datagrid('load' ,serializeForm($('#searchbox')));
     });
     $('#clearbtn').click(function(){
		clearquerycombox();	
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
  function initquerycombox()
  {
     $("#area").val("");
     selectarea('00');
     initqrc('qrcsex','qrcsexdiv',60,50);
     initqrc('qrcage','qrcagediv',150,150);  
     initqrc('qrcphoneos','qrcphoneosdiv',100,50);
     initqrc('qrcprovi','qrcprovidiv',150,80);
     initqrc('qrcphstatus','qrcphstatusdiv',100,50);
     initqrc('qrcrecstatus','qrcrecstatusdiv',100,50);
     initqrc('qrcbrand','qrcbranddiv',180,150);
     initqrc('qrccompany','qrccompanydiv',180,150);
  }
  function clearquerycombox()
  {
     $("#area").combobox('setValue','');
     selectarea('00');
     $("#phonenum").val("");
     selectcon('mbarea');     
	 $('#qrcarea').combo('setValue','').combo('setText','');
     selectcon('phoneos');     
	 $('#qrcphoneos').combo('setValue','').combo('setText','');
     selectcon('provi');
	 $('#qrcprovi').combo('setValue','').combo('setText','');
     selectcon('brand');
	 $('#qrcbrand').combo('setValue','').combo('setText','');
     selectcon('sex');
	 $('#qrcsex').combo('setValue','').combo('setText','');
     selectcon('age');
	 $('#qrcage').combo('setValue','').combo('setText','');
     selectcon('phstatus');
	 $('#qrcphstatus').combo('setValue','').combo('setText','');
     selectcon('company');
	 $('#qrccompany').combo('setValue','').combo('setText','');
     selectcon('recstatus');
	 $('#qrcrecstatus').combo('setValue','').combo('setText','');
  }
  function selectarea(v)
  {
     var str=enAreaCheckbox(v);
     $("#qrcareadiv").html(str); 
     initqrc('qrcarea','qrcareadiv',100,150);
  }
  function selectcon(con)
  {
    var aselect= document.getElementsByName(con);
    for(var i=0;i<aselect.length;i++)   aselect[i].checked = false; 
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
   