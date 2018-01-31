package core;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.*;
import net.sf.json.JSONObject;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MonitorFormat {
	public static final ScriptEngine jse = new ScriptEngineManager().getEngineByName("JavaScript");
	private List<MonitorConfigItem> itemIdList = new ArrayList<MonitorConfigItem>();

	private String format;
	private String monitorTarget;
	private String vnfNodeId;
	private String nsTypeId ;
	private String interval;
	public static int count =0;

	public MonitorFormat(Object format,Object monitorTarget,String vnfNodeId, String nsTypeId,
			String interval) {
		System.out.println("!!!!!! monitor formate initiates, monitor target name is " + monitorTarget + "\n");
		this.format = String.valueOf(format);
		this.monitorTarget =String.valueOf(monitorTarget);
		this.vnfNodeId = vnfNodeId;
		this.nsTypeId = nsTypeId;
		this.interval = interval;
	}
	
	public void mapItemId(HashMap<String,String> map) {
		Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
		Matcher m = pattern.matcher(this.format);
		while(m.find()) {
			String itemId = map.get(m.group(1));
			MonitorConfigItem configItem = new MonitorConfigItem(m.group(1),itemId);
			itemIdList.add(configItem);
			this.format = this.format.replace(m.group(1), itemId);
		}
		System.out.println("========");
		System.out.println(this.format);
	}
	
	public String getMonitorTarget() {
		return this.monitorTarget;
	}
	public String getInterval() {
		return this.interval;
	}
	
	public String getNsTypeId() {
		return this.nsTypeId;
	}
	
	public String getVnfNodeId() {
		return this.vnfNodeId;
	}
/*	public String request(ZabbixDriver zabbixDriver,ServiceMgr serviceMgr,String monConfigId) {
		String itemId = "null";
		for(MonitorConfigItem item : this.itemIdList) {
			if(item.getConfigId() ==  monConfigId) {
				itemId = item.getItemId();
				break;
			}
		}
		HashMap<String,String> resultMap = new HashMap<String,String>();
		resultMap.put(itemId, "null");
		JSONObject params = new JSONObject();
		params.put("itemids", itemId);
		params.put("limit", 1);
		serviceMgr.handler(params, zabbixDriver, resultMap, 2);
		while(true) {
			if(resultMap.get(itemId) != "null") {
				break;
			}
		}
		return resultMap.get(itemId);
	}*/
	
	public String request(MonitorThreads monitorThreads) {
		HashMap<String,String> resultMap = new HashMap<String,String>();
		for(MonitorConfigItem itemId : itemIdList) {
			resultMap.put(itemId.getItemId(),"null" );
			JSONObject params = new JSONObject();
			params.put("itemids", itemId.getItemId());
			params.put("limit", 1);
			System.out.print("in the request\n");
			System.out.print(params);
			monitorThreads.handler(params, resultMap, 2);
		}
		while(true) {
			int count = 0;
			for(String key : resultMap.keySet()) {
				if(resultMap.get(key) == "null") {
					break;
				}
				count++;
			}
			if(count == this.itemIdList.size()) {
				break;
			}
		}
		Pattern pattern = Pattern.compile("\\{\\{(.*?)\\}\\}");
		Matcher m = pattern.matcher(this.format);
		String returnValue = this.format;
		while(m.find()) {
			count++;
			String itemValue = resultMap.get(m.group(1));
			String unit = "{{" + m.group(1) + "}}" ;
			System.out.println("unit is " + unit);
			returnValue = returnValue.replace(unit, itemValue);
		}	
		try {
			returnValue = String.valueOf(jse.eval(returnValue));
		}catch(Exception e) {
			e.printStackTrace();
		}
		System.out.println("return value is " + returnValue);
		System.out.println(count);
		return returnValue;
	}
}
