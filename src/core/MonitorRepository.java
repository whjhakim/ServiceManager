package core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonitorRepository {
	//Map<monitorTargetId, related monitorFormat>
	private Map<String,MonitorFormat> requestMonitorInfo = Collections.synchronizedMap(
			new HashMap<String,MonitorFormat>());
	
	//Map<VNF,related monitorTargetName
	private Map<String, List<String>> vnfContains = new HashMap<String,List<String>>();

	public MonitorRepository() {
		System.out.println("!!!!!! monitor repository initiates");
	}
	
	public void putVnfContains(String vnf, List<String> monitorTargetChains) {
		if(this.vnfContains.containsKey(vnf)) {
			return;
		}
		this.vnfContains.put(vnf, monitorTargetChains);
	}
	
	public void putMonitorFormat(String monitorTarget, MonitorFormat format) {
		this.requestMonitorInfo.put(monitorTarget, format);
	}

}
