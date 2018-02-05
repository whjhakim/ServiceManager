package OpenStackDriver;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class NewtonTimer {
	private Timer timer;
	private long gap;
	private Map<String, NewtonTimerTask> timerMap = new HashMap<String, NewtonTimerTask>();

	public NewtonTimer(long gap) {
		System.out.println("!!!!!! alarm timer initiates");
		this.timer = new Timer();
		this.gap = gap;
	}
	
	public void addTimer(String serverId, NewtonHttpClient httpClient) {
		NewtonTimerTask newtonTimerTask = new NewtonTimerTask(serverId, httpClient);
		this.timerMap.get(alarmFormat.getNsTypeId()).put(alarmFormat.getAlarmId(), alarmTimerTask);

		String interval = alarmFormat.getInterval();
		timer.schedule(alarmTimerTask, 0, Long.valueOf(interval));
	}
	
	public void deleteTimer(String nsTypeId, String alarmId) {
		AlarmTimerTask alarmTimerTask = this.timerMap.get(nsTypeId).get(alarmId);
		alarmTimerTask.cancel();
		this.timerMap.get(nsTypeId).remove(alarmId);
	}
	
	public void stopAllTimers() {
		this.timer.cancel();
	}
	
	public boolean isEmpty() {
		return this.timerMap.isEmpty();
	}
}
