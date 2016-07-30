package cn.ncut.pushservice;

import java.util.List;

import cn.ncut.pushdao.JsonDao;
import cn.ncut.pushdomain.JsonModel;
import cn.ncut.pushdomain.PushAccepter;

public class JsonService {

	JsonDao dao = new JsonDao();
	public List<JsonModel> GetCompanyList() {
		return dao.GetCompanyList();
	}
	public List<JsonModel> GetBigBrandList() {
		return dao.GetBigBrandList();
	}
	public List<JsonModel> GetBigAreaList() {
		return dao.GetBigAreaList();
	}
	public List<JsonModel> GetAreaList() {
		return dao.GetAreaList();
	}
	public List<JsonModel> GetAppClassList() {
		return dao.GetAppClassList();
	}
	public List<JsonModel> GetContentClassList() {
		return dao.GetContentClassList();
	}
	public List<JsonModel> GetBrandList() {
		return dao.GetBrandList();
	}
	public List<JsonModel> GetMbOsList() {
		return dao.GetMbOsList();
	}
	public void RegisterAccepter(PushAccepter phac) {
		dao.RegisterAccepter(phac);
	}
	public int EnGender(String gender) {
		if(gender==null) return 0;
		if(gender.contains("男")) return 1;
		if(gender.contains("女")) return 2;
		return 0;
	}
	public String EnBirth(String birth) {
		if(birth!=null&&!birth.equals("")) return birth+"-09-01";
		return null;
	}
	public String EnArea(String area) {
		if(area!=null&&!area.equals("")) return area;
		return null;
	}
	public String EnProvider(String provider) {
		if(provider!=null&&!provider.equals("")) return provider;
		return null;
	}
	public String EnMbtype(String mbtype) {
		if(mbtype!=null&&!mbtype.equals("")) return dao.EnMbtype(mbtype);//更新tab_pushmbtype
		return null;
	}
	public String EnMbos(String mbos) {
		if(mbos!=null&&!mbos.equals("")) return dao.EnMbos(mbos);//更新tab_pushmbos
		return null;
	}
	public List<JsonModel> GetSinglePositionList(String comId) {
		return dao.GetSinglePositionList(comId);
	}
	public List<JsonModel> GetDoublePositionList(String comId) {
		return dao.GetDoublePositionList(comId);
	}
	
	
	
	
}
