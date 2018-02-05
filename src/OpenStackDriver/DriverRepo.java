package OpenStackDriver;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class DriverRepo {
	//serverId: disk cpu vmInfo
	private Map<String, JSONObject> serversInfo = new HashMap<String, JSONObject>();
	public DriverRepo() {
	}
	
	public boolean containServer(String server) {
		return this.serversInfo.containsKey(server);
	}
	
	public void createServer(String server) {
		if(!this.serversInfo.containsKey(server)) {
			JSONObject serverObj = this.initiateServerObj(); 
			this.serversInfo.put(server, serverObj);
		}
	}
	
	public String getItemValue(String serverId, String type, String item) {
		return this.serversInfo.get(serverId).getJSONObject(type).getString(item);
	}
	private JSONObject initiateServerObj() {
		JSONObject disk = new JSONObject();
		disk.put("totalDisk", "null");
		disk.put("swapDisk","null");
		disk.put("ephemeralDisk", "null");

		JSONObject cpu = new JSONObject();
		cpu.put("vCPUs", "null");

		JSONObject vmInfo = new JSONObject();
		vmInfo.put("vmState", "null");
		vmInfo.put("powerState", "null");
		vmInfo.put("launchTime", "null");
		vmInfo.put("host", "null");
		vmInfo.put("vmId", "null");
		
		JSONObject returnJSON = new JSONObject();
		returnJSON.put("disk", disk);
		returnJSON.put("CPU", cpu);
		returnJSON.put("vmInfo", vmInfo);
		return returnJSON;
	}
	
	
}
