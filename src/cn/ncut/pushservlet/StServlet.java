package cn.ncut.pushservlet;
import java.util.List;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import cn.ncut.pushdomain.ComBox;
import cn.ncut.pushdomain.Employee;
import cn.ncut.pushdomain.PushAccepter;
import cn.ncut.pushdomain.PushContent;
import cn.ncut.pushdomain.StatsModel;
import cn.ncut.pushservice.StService;
import cn.ncut.pushservice.SwitchService;
import cn.ncut.syssetdomain.User;

public class StServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StService service = new StService();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		String method = request.getParameter("method");
		if (method.equals("GetComNameList")) {
			GetComNameList(request, response);
		}
		if (method.equals("GetDeptNameList")) {
			GetDeptNameList(request, response);
		}
		if (method.equals("GetStuffList")) {
			GetStuffList(request, response);
		}
		if (method.equals("GetStatsData")) {
			GetStatsData(request, response);
		}
		if (method.equals("GetChart1Data")) {
			GetChart1Data(request, response);
		}
		if (method.equals("GetChart2Data")) {
			GetChart2Data(request, response);
		}
		if (method.equals("GetChart3Data")) {
			GetChart3Data(request, response);
		}
		if (method.equals("GetChart4Data")) {
			GetChart4Data(request, response);
		}
		if (method.equals("GetChart5Data")) {
			GetChart5Data(request, response);
		}
		if (method.equals("GetChart6Data")) {
			GetChart6Data(request, response);
		}
		if (method.equals("GetChart7Data")) {
			GetChart7Data(request, response);
		}
	}
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void GetComNameList(HttpServletRequest request,
			HttpServletResponse response) {		
		 User user = (User) request.getSession().getAttribute("user");
		 List<ComBox> CList = service.GetComNameList(user.getComno());
			String json = JSONArray.fromObject(CList).toString();
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void GetDeptNameList(HttpServletRequest request,
			HttpServletResponse response) {		
		 List<ComBox> CList = service.GetDeptNameList(request.getParameter("comid"));
			String json = JSONArray.fromObject(CList).toString();
			try {
				response.getWriter().write(json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	private void GetStuffList(HttpServletRequest request,
			HttpServletResponse response) {		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		String  cid = request.getParameter("comid");	
		String  did = request.getParameter("deptid");	
		User user = (User) request.getSession().getAttribute("user");
		if((cid==null||cid.equals(""))&&(user.getComno().toString().equals("100000")))		cid="all";
		if((cid==null||cid.equals(""))&&(!user.getComno().toString().equals("100000")))	  cid=user.getComno().toString();
		List<Employee> CtList;
		if(cid==null||cid.equals("")) CtList=null;
		else CtList = service.GetStuffList(currentPage, pageSize,cid,did);
		int total = service.GetStuffTotal(cid,did);
		String json = "{\"total\":" + total + " ,\"rows\":"
				+ JSONArray.fromObject(CtList).toString() + "}";
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void GetStatsData(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String xunit=request.getParameter("xunit");
			String show="column";
			String tjtj=request.getParameter("tjtj");
			String tjxx=request.getParameter("tjxx");
			String xx="";
			String yy="";
			String json="";
			int line1=0;
			if(tjtj.equals("regdate")&&(show.equals("line")||show.equals("column")||show.equals("line1")))  {
				List<StatsModel> list=service.GetStatsDayData(start,end,xunit);
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
					if(show.equals("line1"))  {
						line1=line1+Integer.parseInt(list.get(i).getNum());
						xx+=","+line1;
					}
					else xx+=","+list.get(i).getNum();	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '设备数量',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			
			if(tjtj.equals("age"))  {
				//if(tjxx==null||tjxx.equals("")) tjxx="";
				System.out.println(tjtj+"::::::::"+tjxx);
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
                	line1=0;
                	jsony+=",{name:'"+zz[j]+"',data: [";
    				List<StatsModel> list=service.GetStatsDayData(start,end,zz[j]);
    				for(int i=0;i<list.size();i++){
    					if(show.equals("line1"))  {
    						line1=line1+Integer.parseInt(list.get(i).getNum());
    						jy+=","+line1;
    						}
    					else jy+=","+list.get(i).getNum();	
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			    
			    /********	     
				json= "{"
					+"\"yy\": \"[{name: '18岁以下',data: [149.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]}," +
							    "{name: '18岁-22岁',data: [83.6, 78.8, 98.5, 93.4, 106.0, 84.5, 105.0, 104.3, 91.2, 83.5, 106.6, 92.3]}," +
							    "{name: '23岁-35岁',data: [48.9, 38.8, 39.3, 41.4, 47.0, 48.3, 59.0, 59.6, 52.4, 65.2, 59.3, 51.2]}," +
							    "{name: '36岁-55岁',data: [42.4, 33.2, 34.5, 39.7, 52.6, 75.5, 57.4, 60.4, 47.6, 39.1, 46.8, 51.1]}]\","
	                +"\"xx\": \"['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']\"" 
	                +"}";
	            **********/
			}					
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void GetChart1Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String xunit=request.getParameter("xunit");
			String tjtj=request.getParameter("tjtj");
			String tjxx=request.getParameter("tjxx");
			String xx="";
			String yy="";
			String json="";
			
			/*******无*******/
			if(tjtj.equals("regdate"))  {
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDayData(start,end,"");
				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,"");
				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,"");
				else list=null;
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+list.get(i).getNum();	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '设备数量',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			

			/*******年龄*******/
			if(tjtj.equals("age"))  {
				if(tjxx==null||tjxx.equals("")) tjxx="null-17,18-22,23-26,27-35,36-55,56-null";
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
    				List<StatsModel> list;
                	jsony+=",{name:'"+SwitchService.AgeTitle(zz[j])+"',data: [";
                	if(xunit.equals("day")) list=service.GetStatsDayData(start,end,SwitchService.AgeSql(zz[j]));
    				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,SwitchService.AgeSql(zz[j]));
    				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,SwitchService.AgeSql(zz[j]));
    				else list=null;
    				for(int i=0;i<list.size();i++){
    					jy+=","+list.get(i).getNum();	
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			}	
			
			
			/*******供应商*******/
			if(tjtj.equals("provider"))  {
				if(tjxx==null||tjxx.equals("")) tjxx="1,2,3";
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
    				List<StatsModel> list;
                	jsony+=",{name:'"+SwitchService.ProviderTitle(zz[j])+"',data: [";
                	if(xunit.equals("day")) list=service.GetStatsDayData(start,end,SwitchService.ProviderSql(zz[j]));
    				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,SwitchService.ProviderSql(zz[j]));
    				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,SwitchService.ProviderSql(zz[j]));
    				else list=null;
    				for(int i=0;i<list.size();i++){
    					jy+=","+list.get(i).getNum();	
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			}	
			
			
			/*******归属单位*******/
			if(tjtj.equals("unit"))  {
				if(tjxx==null||tjxx.equals("")) tjxx=SwitchService.getComanyList();
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
    				List<StatsModel> list;
                	jsony+=",{name:'"+SwitchService.getComanyName(zz[j])+"',data: [";
                	if(xunit.equals("day")) list=service.GetStatsDayData(start,end,SwitchService.UnitSql(zz[j]));
    				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,SwitchService.UnitSql(zz[j]));
    				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,SwitchService.UnitSql(zz[j]));
    				else list=null;
    				for(int i=0;i<list.size();i++){
    					jy+=","+list.get(i).getNum();	
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			}	
			
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void GetChart2Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String xunit=request.getParameter("xunit");
			String tjtj=request.getParameter("tjtj");
			String tjxx=request.getParameter("tjxx");
			String xx="";
			String yy="";
			String json="";
			int line1=0;
			/*******无*******/
			if(tjtj.equals("regdate"))  {
				List<StatsModel> list;
				line1=service.GetBeforeDeviceSum(start,"");
				if(xunit.equals("day")) list=service.GetStatsDayData(start,end,"");
				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,"");
				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,"");
				else list=null;
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
					line1=line1+Integer.parseInt(list.get(i).getNum());
					xx+=","+line1;
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '设备数量',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			

			/*******年龄*******/
			if(tjtj.equals("age"))  {
				if(tjxx==null||tjxx.equals("")) tjxx="null-17,18-22,23-26,27-35,36-55,56-null";
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
    				List<StatsModel> list;
    				line1=service.GetBeforeDeviceSum(start,SwitchService.AgeSql(zz[j]));
                	jsony+=",{name:'"+SwitchService.AgeTitle(zz[j])+"',data: [";
                	if(xunit.equals("day")) list=service.GetStatsDayData(start,end,SwitchService.AgeSql(zz[j]));
    				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,SwitchService.AgeSql(zz[j]));
    				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,SwitchService.AgeSql(zz[j]));
    				else list=null;
    				for(int i=0;i<list.size();i++){
    					line1=line1+Integer.parseInt(list.get(i).getNum());
						jy+=","+line1;
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			}	
			
			
			/*******供应商*******/
			if(tjtj.equals("provider"))  {
				if(tjxx==null||tjxx.equals("")) tjxx="1,2,3";
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
    				List<StatsModel> list;
    				line1=service.GetBeforeDeviceSum(start,SwitchService.ProviderSql(zz[j]));
                	jsony+=",{name:'"+SwitchService.ProviderTitle(zz[j])+"',data: [";
                	if(xunit.equals("day")) list=service.GetStatsDayData(start,end,SwitchService.ProviderSql(zz[j]));
    				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,SwitchService.ProviderSql(zz[j]));
    				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,SwitchService.ProviderSql(zz[j]));
    				else list=null;
    				for(int i=0;i<list.size();i++){
    					line1=line1+Integer.parseInt(list.get(i).getNum());
						jy+=","+line1;
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			}	
			
			
			/*******归属单位*******/
			if(tjtj.equals("unit"))  {
				if(tjxx==null||tjxx.equals("")) tjxx=SwitchService.getComanyList();
                String[] zz=tjxx.split(",");
                String jsony="";
                String jsonx="";
                for(int j=0;j<zz.length;j++)
                { 
                	String jy="";
    				List<StatsModel> list;
    				line1=service.GetBeforeDeviceSum(start,SwitchService.UnitSql(zz[j]));
                	jsony+=",{name:'"+SwitchService.getComanyName(zz[j])+"',data: [";
                	if(xunit.equals("day")) list=service.GetStatsDayData(start,end,SwitchService.UnitSql(zz[j]));
    				else if(xunit.equals("month")) list=service.GetStatsMonthData(start,end,SwitchService.UnitSql(zz[j]));
    				else if(xunit.equals("year")) list=service.GetStatsYearData(start,end,SwitchService.UnitSql(zz[j]));
    				else list=null;
    				for(int i=0;i<list.size();i++){
    					line1=line1+Integer.parseInt(list.get(i).getNum());
						jy+=","+line1;
    					if(j==0) jsonx+=",'"+list.get(i).getName()+"'";				
    				}  
    				jsony+=jy.substring(1)+"]}";
                }
                jsony="\"yy\": \"["+jsony.substring(1)+"]\"";
                jsonx="\"xx\": \"["+jsonx.substring(1)+"]\"";                
			    json = "{"+jsony+","+jsonx+"}";	
			}	
			
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void GetChart3Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String json= "";
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String tjtj=request.getParameter("tjtj");
			String tjxx=request.getParameter("tjxx");
			int sum=service.GetDeviceSum(start,end,"");
			if(sum!=0) {
				int num=0;
				float temp=0;
				/*******年龄*******/
				if(tjtj.equals("age"))  {
					if(tjxx==null||tjxx.equals("")) tjxx="null-17,18-22,23-26,27-35,36-55,56-null";
	                String[] zz=tjxx.split(",");
	                for(int j=0;j<zz.length;j++)
	                { 
	                	num=service.GetDeviceSum(start,end,SwitchService.AgeSql(zz[j]));
	                	temp+=((float)num*100/(float)sum);
	                	json+=",[\""+SwitchService.AgeTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
	                }   
	                json+=",[\"其他\","+(100-temp)+"]";
				    json = "["+json.substring(1)+"]";	
				}	
				/*******运营商*******/
				if(tjtj.equals("provider"))  {
					if(tjxx==null||tjxx.equals("")) tjxx="1,2,3";
	                String[] zz=tjxx.split(",");
	                for(int j=0;j<zz.length;j++)
	                { 
	                	num=service.GetDeviceSum(start,end,SwitchService.ProviderSql(zz[j]));
	                	temp+=((float)num*100/(float)sum);
	                	json+=",[\""+SwitchService.ProviderTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
	                }   
	                json+=",[\"其他\","+(100-temp)+"]";
				    json = "["+json.substring(1)+"]";	
				}	
				/*******归属单位*******/
				if(tjtj.equals("unit"))  {
					if(tjxx==null||tjxx.equals("")) tjxx=SwitchService.getComanyList();
	                String[] zz=tjxx.split(",");
	                for(int j=0;j<zz.length;j++)
	                { 
	                	num=service.GetDeviceSum(start,end,SwitchService.UnitSql(zz[j]));
	                	temp+=((float)num*100/(float)sum);
	                	json+=",[\""+SwitchService.getComanyName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
	                }   
	                json+=",[\"其他\","+(100-temp)+"]";
				    json = "["+json.substring(1)+"]";	
				}
				/*******销售单位*******/
				if(tjtj.equals("seller"))  {
					if(tjxx==null||tjxx.equals("")) tjxx=SwitchService.getComanyList();
	                String[] zz=tjxx.split(",");
	                for(int j=0;j<zz.length;j++)
	                { 
	                	num=service.GetDeviceSum(start,end,SwitchService.SellerSql(zz[j]));
	                	temp+=((float)num*100/(float)sum);
	                	json+=",[\""+SwitchService.getComanyName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
	                }   
	                json+=",[\"其他\","+(100-temp)+"]";
				    json = "["+json.substring(1)+"]";	
				}
				/*******手机品牌*******/
				if(tjtj.equals("brand"))  {
					if(tjxx==null||tjxx.equals("")) tjxx=SwitchService.getBrandList();
	                String[] zz=tjxx.split(",");
	                for(int j=0;j<zz.length;j++)
	                { 
	                	num=service.GetDeviceSum(start,end,SwitchService.BrandSql(zz[j]));
	                	temp+=((float)num*100/(float)sum);
	                	json+=",[\""+SwitchService.getBrandName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
	                }   
	                json+=",[\"其他\","+(100-temp)+"]";
				    json = "["+json.substring(1)+"]";	
				}
				/*******手机归属地*******/
				if(tjtj.equals("area"))  {
					if(tjxx==null||tjxx.equals("")) tjxx=SwitchService.getAreaList();
	                String[] zz=tjxx.split(",");
	                for(int j=0;j<zz.length;j++)
	                { 
	                	num=service.GetDeviceSum(start,end,SwitchService.AreaSql(zz[j]));
	                	temp+=((float)num*100/(float)sum);
	                	json+=",[\""+SwitchService.getAreaName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
	                }   
	                json+=",[\"其他\","+(100-temp)+"]";
				    json = "["+json.substring(1)+"]";	
				}
			}			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private void GetChart4Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String xunit=request.getParameter("xunit");
			String tjtj=request.getParameter("tjtj");
			String mbnum=request.getParameter("mbnum");
			String xx="";
			String yy="";
			String json="";
			
			/*******次数*******/
			if(tjtj.equals("count"))  {
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDaySlideCountData(start,end,"and device_number='"+mbnum+"'");
				else if(xunit.equals("month")) list=service.GetStatsMonthSlideCountData(start,end,"and device_number='"+mbnum+"'");
				else if(xunit.equals("year")) list=service.GetStatsYearSlideCountData(start,end,"and device_number='"+mbnum+"'");
				else list=null;
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+list.get(i).getNum();	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '滑屏次数',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			

			/*******积分*******/
			if(tjtj.equals("score"))  {
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDaySlideScoreData(start,end,"and device_number='"+mbnum+"'");
				else if(xunit.equals("month")) list=service.GetStatsMonthSlideScoreData(start,end,"");
				else if(xunit.equals("year")) list=service.GetStatsYearSlideScoreData(start,end,"");
				else list=null;
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+list.get(i).getNum();	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '滑屏积分',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			
		
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void GetChart5Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String xunit=request.getParameter("xunit");
			String tjtj=request.getParameter("tjtj");
			String xx="";
			String yy="";
			String json="";
			
			PushAccepter item=new PushAccepter();
			item.setMbno(request.getParameter("mbinfo_num"));
			item.setCurque(request.getParameter("mbinfo_sex"));
			item.setBirth(request.getParameter("mbinfo_age"));
			item.setCompany(request.getParameter("mbinfo_company"));
			item.setSeller(request.getParameter("mbinfo_seller"));
			item.setMbtype(request.getParameter("mbinfo_brand"));
			item.setProvider(request.getParameter("mbinfo_provi"));
			item.setArea(request.getParameter("mbinfo_area"));
			
			
			String con="and device_number in ("+service.GetDeviceNums(item)+" )";
			if(service.GetDeviceNums(item).equals("")) con="";
			/*******次数*******/
			if(tjtj.equals("count"))  {
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDaySlideCountData(start,end,con);
				else if(xunit.equals("month")) list=service.GetStatsMonthSlideCountData(start,end,con);
				else if(xunit.equals("year")) list=service.GetStatsYearSlideCountData(start,end,con);
				else list=null;
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+list.get(i).getNum();	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '滑屏次数',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			

			/*******积分*******/
			if(tjtj.equals("score"))  {
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDaySlideScoreData(start,end,con);
				else if(xunit.equals("month")) list=service.GetStatsMonthSlideScoreData(start,end,con);
				else if(xunit.equals("year")) list=service.GetStatsYearSlideScoreData(start,end,con);
				else list=null;
				for(int i=0;i<list.size();i++){
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+list.get(i).getNum();	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '滑屏积分',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			
		
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void GetChart6Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String xunit=request.getParameter("xunit");
			String tjtj=request.getParameter("tjtj");
			String xx="";
			String yy="";
			String json="";
			int line1=0;
			
			PushAccepter item=new PushAccepter();
			item.setMbno(request.getParameter("mbinfo_num"));
			item.setCurque(request.getParameter("mbinfo_sex"));
			item.setBirth(request.getParameter("mbinfo_age"));
			item.setCompany(request.getParameter("mbinfo_company"));
			item.setSeller(request.getParameter("mbinfo_seller"));
			item.setMbtype(request.getParameter("mbinfo_brand"));
			item.setProvider(request.getParameter("mbinfo_provi"));
			item.setArea(request.getParameter("mbinfo_area"));
			
			
			String con="and device_number in ("+service.GetDeviceNums(item)+" )";
			if(service.GetDeviceNums(item).equals("")) con="";
			/*******次数*******/
			if(tjtj.equals("count"))  {
				line1=service.GetBeforeSlideCountSum(start,con);
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDaySlideCountData(start,end,con);
				else if(xunit.equals("month")) list=service.GetStatsMonthSlideCountData(start,end,con);
				else if(xunit.equals("year")) list=service.GetStatsYearSlideCountData(start,end,con);
				else list=null;
				for(int i=0;i<list.size();i++){
					line1=line1+Integer.parseInt(list.get(i).getNum());
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+line1;	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '滑屏次数',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			

			/*******积分*******/
			if(tjtj.equals("score"))  {
				line1=service.GetBeforeSlideScoreSum(start,con);
				List<StatsModel> list;
				if(xunit.equals("day")) list=service.GetStatsDaySlideScoreData(start,end,con);
				else if(xunit.equals("month")) list=service.GetStatsMonthSlideScoreData(start,end,con);
				else if(xunit.equals("year")) list=service.GetStatsYearSlideScoreData(start,end,con);
				else list=null;
				for(int i=0;i<list.size();i++){
					line1=line1+Integer.parseInt(list.get(i).getNum());
					yy+=",'"+list.get(i).getName()+"'";
				    xx+=","+line1;	
				}
				 yy=yy.substring(1);
				 xx=xx.substring(1);
			     json = "{\"yy\": \"[{name: '滑屏积分',data: ["+xx+"]}]\",\"xx\": \"["+yy+"]\"}";
			}
			
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	private void GetChart7Data(HttpServletRequest request,
			HttpServletResponse response) {		
		try {
			String start=request.getParameter("start");
			String end=request.getParameter("end");
			String tjtj=request.getParameter("tjtj");
			String xunit=request.getParameter("xunit");
			String json="";
			
			PushAccepter item=new PushAccepter();
			item.setMbno(request.getParameter("mbinfo_num"));
			item.setCurque(request.getParameter("mbinfo_sex"));
			item.setBirth(request.getParameter("mbinfo_age"));
			item.setCompany(request.getParameter("mbinfo_company"));
			item.setSeller(request.getParameter("mbinfo_seller"));
			item.setMbtype(request.getParameter("mbinfo_brand"));
			item.setProvider(request.getParameter("mbinfo_provi"));
			item.setArea(request.getParameter("mbinfo_area"));
			
			
			String con="and device_number in ("+service.GetDeviceNums(item)+" )";
			if(service.GetDeviceNums(item).equals("")) con="";
		
			/*******次数*******/
			if(tjtj.equals("count"))  {				
				int sum=service.GetSlideCountSum(start,end,con);
				if(sum!=0) {
					int num=0;
					float temp=0;
					/*******性别*******/
					if(xunit.equals("sex"))  {
						if(item.getCurque()==null||item.getCurque().equals("")) item.setCurque("1,2");
		                String[] zz=item.getCurque().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setCurque(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.SexTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}	
					/*******年龄*******/
					if(xunit.equals("age"))  {
						if(item.getBirth()==null||item.getBirth().equals("")) item.setBirth("null-17,18-22,23-26,27-35,36-55,56-null");
		                String[] zz=item.getBirth().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setBirth(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.AgeTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}	
					/*******运营商*******/
					if(xunit.equals("provider"))  {
						if(item.getProvider()==null||item.getProvider().equals("")) item.setProvider("1,2,3");
		                String[] zz=item.getProvider().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setProvider(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.ProviderTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}	
					/*******归属单位*******/
					if(xunit.equals("unit"))  {
						if(item.getCompany()==null||item.getCompany().equals("")) item.setCompany(SwitchService.getComanyList());
		                String[] zz=item.getCompany().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setCompany(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getComanyName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
					/*******销售单位*******/
					if(xunit.equals("seller"))  {
						if(item.getSeller()==null||item.getSeller().equals("")) item.setSeller(SwitchService.getComanyList());
		                String[] zz=item.getSeller().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setSeller(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getComanyName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
					/*******手机品牌*******/
					if(xunit.equals("brand"))  {
						if(item.getMbtype()==null||item.getMbtype().equals("")) item.setMbtype(SwitchService.getBrandList());
		                String[] zz=item.getMbtype().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setMbtype(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getBrandName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
					/*******手机归属地*******/
					if(xunit.equals("area"))  {
						if(item.getArea()==null||item.getArea().equals("")) item.setArea(SwitchService.getAreaList());
		                String[] zz=item.getArea().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setArea(zz[j]);
		                	num=service.GetSlideCountSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getAreaName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
			     }	
			}

			/*******积分*******/
			if(tjtj.equals("score"))  {
				int sum=service.GetSlideScoreSum(start,end,con);
				if(sum!=0) {
					int num=0;
					float temp=0;
					/*******性别*******/
					if(xunit.equals("sex"))  {
						if(item.getCurque()==null||item.getCurque().equals("")) item.setCurque("1,2");
		                String[] zz=item.getCurque().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setCurque(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.SexTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}	
					/*******年龄*******/
					if(xunit.equals("age"))  {
						if(item.getBirth()==null||item.getBirth().equals("")) item.setBirth("null-17,18-22,23-26,27-35,36-55,56-null");
		                String[] zz=item.getBirth().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setBirth(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.AgeTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}	
					/*******运营商*******/
					if(xunit.equals("provider"))  {
						if(item.getProvider()==null||item.getProvider().equals("")) item.setProvider("1,2,3");
		                String[] zz=item.getProvider().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setProvider(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.ProviderTitle(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}	
					/*******归属单位*******/
					if(xunit.equals("unit"))  {
						if(item.getCompany()==null||item.getCompany().equals("")) item.setCompany(SwitchService.getComanyList());
		                String[] zz=item.getCompany().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setCompany(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getComanyName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
					/*******销售单位*******/
					if(xunit.equals("seller"))  {
						if(item.getSeller()==null||item.getSeller().equals("")) item.setSeller(SwitchService.getComanyList());
		                String[] zz=item.getSeller().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setSeller(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getComanyName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
					/*******手机品牌*******/
					if(xunit.equals("brand"))  {
						if(item.getMbtype()==null||item.getMbtype().equals("")) item.setMbtype(SwitchService.getBrandList());
		                String[] zz=item.getMbtype().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setMbtype(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getBrandName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
					/*******手机归属地*******/
					if(xunit.equals("area"))  {
						if(item.getArea()==null||item.getArea().equals("")) item.setArea(SwitchService.getAreaList());
		                String[] zz=item.getArea().split(",");
		                for(int j=0;j<zz.length;j++)
		                { 
		                	PushAccepter tempitem=item;
		                	tempitem.setArea(zz[j]);
		                	num=service.GetSlideScoreSum(start,end,"and device_number in ("+service.GetDeviceNums(tempitem)+" )");
		                	temp+=((float)num*100/(float)sum);
		                	json+=",[\""+SwitchService.getAreaName(zz[j])+"\","+((float)num*100/(float)sum)+"]";                	
		                }   
		                json+=",[\"其他\","+(100-temp)+"]";
					    json = "["+json.substring(1)+"]";	
					}
			     }	
			}
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
