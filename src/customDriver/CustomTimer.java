package customDriver;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CustomTimer {
	private Timer timer;
	private long gap;
	//serverId , timerTask
	private Map<String, CustomTimerTask> timerMap = new HashMap<String, CustomTimerTask>();

	public CustomTimer(long gap) {
		this.timer = new Timer();
		this.gap = gap;
	}
	
	public void addTimer(String serverId,  CustomRepo customRepo) {
		CustomTimerTask customTimerTask = new CustomTimerTask(serverId, customRepo);
		this.timerMap.put(serverId, customTimerTask);
		timer.schedule(customTimerTask, 0, this.gap);
	}
	
	public void deleteTimer(String serverId) {
		CustomTimerTask customTimerTask = this.timerMap.get(serverId);
		customTimerTask.cancel();
		this.timerMap.remove(serverId);
	}
	
	public void stopAllTimers() {
		this.timer.cancel();
	}
	
	public boolean isEmpty() {
		return this.timerMap.isEmpty();
	}
}
