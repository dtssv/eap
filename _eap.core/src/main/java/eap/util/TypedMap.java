package eap.util;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.springframework.util.Assert;

public class TypedMap implements Map {
	
	private Map map;
	
	public TypedMap(Map map) {
		Assert.notNull(map, "'map' must not be null");
		this.map = map;
	}
	
	public Integer getInteger(String key) {
		return getInteger(key, null);
	}
	public Integer getInteger(String key, Integer defaultValue) {
		Object value = map.get(key);
		if (value != null) {
			return new Integer(value.toString());
		}
		
		return defaultValue;
	}
	
	public Date getDate(String key) {
		return getDate(key, null);
	}
	public Date getDate(String key, Date defaultValue) {
		Object value = map.get(key);
		if (value != null) {
			return DateUtil.parse(value.toString());
		}
		
		return defaultValue;
	}

	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	@Override
	public Set entrySet() {
		return map.entrySet();
	}
	@Override
	public Object get(Object key) {
		return map.get(key);
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public Set keySet() {
		return map.keySet();
	}
	@Override
	public Object put(Object key, Object value) {
		return map.put(key, value);
	}
	@Override
	public void putAll(Map m) {
		map.putAll(m);
	}
	@Override
	public Object remove(Object key) {
		return map.remove(key);
	}
	@Override
	public int size() {
		return map.size();
	}
	@Override
	public Collection values() {
		return map.values();
	}
}