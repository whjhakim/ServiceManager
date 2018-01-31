package core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MonitorRepository {
	//Map<nsTypeId, Map<vnfNodeId,Map<monitorTargetName, MonitorFormat>>>
	private Map<String, Map<String,Map<String, MonitorFormat>>>  monitorRepo = Collections
			.synchronizedMap(new HashMap<String,Map<String,Map<String, MonitorFormat>>>());

	public MonitorRepository() {
		System.out.println("!!!!!! monitor repository initiates");
	}
	
	public void putMonitorFormat(MonitorFormat format) {
		this.monitorRepo.get(format.getNsTypeId()).get(format.getVnfNodeId())
			.put(format.getMonitorTarget(),format);
	}
	
	public JSONObject getNsMonitor(String nsTypeId) {
		JSONObject nsChain = new JSONObject();
		for(String vnf : this.monitorRepo.get(nsTypeId).keySet()) {
			JSONArray vnfChain = new JSONArray();
			for(String monitorTarget : this.monitorRepo.get(nsTypeId).get(vnf).keySet()) {
				vnfChain.add(monitorTarget);
			}
			nsChain.put(vnf, vnfChain);
		}
		return nsChain;
	}
	public JSONArray getVnfMonitor(String nsTypeId, String vnf) {
		JSONArray vnfChain = new JSONArray();
		for(String monitorTarget : this.monitorRepo.get(nsTypeId).get(vnf).keySet()) {
			vnfChain.add(monitorTarget);
		}
		return vnfChain;
	}

}
