package cat.richmind.matchprocessor.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ContextWrapper {
	private Map<String, Object> contextItems;
	
	public ContextWrapper() {
		contextItems = new HashMap<>();
	}
	
	public void load(Properties props) {
		for (String propKey : props.stringPropertyNames()) {
			this.put(propKey, props.get(propKey));
		}
	}
	
	public Object get(String key) {
		return contextItems.get(key);
	}
	
	public void put(String key, Object value) {
		contextItems.put(key, value);
	}
}