package OpenStackDriver;
import java.util.Map;
import java.util.TimerTask;

import net.sf.json.JSONObject;

public class NewtonTimerTask extends TimerTask{
	private String serverId;
	private NewtonHttpClient httpClient;
	private DriverRepo driverRepo;
	private int count;
	public NewtonTimerTask(String serverId, NewtonHttpClient httpClient, DriverRepo driverRepo) {
		this.serverId = serverId;
		this.httpClient = httpClient;
		this.driverRepo = driverRepo;
		this.count = 0;
	}

	@Override
	public void run() {
		count++;
		Map<String, Map<String, String>> result = this.httpClient.doGet(this.serverId);
		this.driverRepo.refresh(this.serverId,result);
		System.out.println("count is " + count);
	}
}
