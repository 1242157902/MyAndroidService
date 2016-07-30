package xjson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jsonservice {	
	
	
	//list为sql结果集，该函数返回json格式的数据串
	public String treejson(List<node> list) {
		//java中利用map实现键值对数组的功能，这里的jsonmap即为文中的数组kv
		Map<String, jnode> jsonmap=new HashMap<String,jnode>();		
		String root="";//根节点id的集合
		for(node item:list){//遍历sql结果集
			jnode jtem=new jnode();
			if(jsonmap.get(item.getId())!=null) {//判断kv中是否已存在当前节点
				jtem.setId(item.getId());
				jtem.setName(item.getName());
				jtem.setAttr(item.getAttr());
				jtem.setChildren(jsonmap.get(item.getId()).getChildren());
			}
			else {
				jtem.setId(item.getId());
				jtem.setName(item.getName());
				jtem.setAttr(item.getAttr());
			}
			jsonmap.put(item.getId(), jtem);//存入或覆盖当前节点
			
			if(item.getParentId().length()==0)  {
				root=root+","+item.getId();//加入根节点
				continue;
			}
			else {
			if(jsonmap.get(item.getParentId())==null) {//判断kv中是否存在当前节点的父节点
				jtem.setId(item.getParentId());
				jtem.setName("");
				jtem.setAttr("");
				jtem.setChildren(item.getId());
			}
			else {
				jtem=jsonmap.get(item.getParentId());
				jtem.setChildren(jtem.getChildren()+","+item.getId());
			}
			jsonmap.put(item.getId(), jtem);//存入或覆盖当前节点的父节点
			}
	    }
		if(root=="") return "";
		else return nodejson(root.substring(1),jsonmap);//从根节点开始递归求json串
	}
	
	public String nodejson(String root,Map<String, jnode> jsonmap) {
		String rec="";
		String[] nodeid=root.split(",");
		for(String id: nodeid){//根据id遍历节点
			if(jsonmap.get(id).getChildren().length()>0) //判断是否有子节点，有继续递归，无终止节点
		        rec+=",{\"id\":"+id+",\"name\":\""+jsonmap.get(id).getName()
		        +"\",\"attr\":\""+jsonmap.get(id).getAttr()+"\",\"children\":"
		        +nodejson(jsonmap.get(id).getChildren(),jsonmap)+"}";
			else 
				rec+=",{\"id\":"+id+",\"name\":\""+jsonmap.get(id).getName()
				+"\",\"attr\":\""+jsonmap.get(id).getAttr()+"\"}";
		}
		return "["+rec.substring(1)+"]";
	}
	
}
