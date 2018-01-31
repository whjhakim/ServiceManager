package core;

public class MonitorConfigItem {
	private String itemId ="null";
	private String monConfigId = "null";
	public MonitorConfigItem(String configId, String itemId) {
		this.itemId = itemId;
		this.monConfigId = configId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setMonConfigId(String monConfigId) {
		this.monConfigId = monConfigId;
	}
	public String getItemId() {
		return itemId;
	}
	public String getConfigId() {
		return monConfigId;
	}
}
