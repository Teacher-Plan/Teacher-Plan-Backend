package com.github.ioloolo.teacher_plan.common.util;

import java.util.HashMap;
import java.util.Map;

public class Cache<K, V> {
	protected final Map<K, V> cache = new HashMap<>();

	public V get(K key) {
		return cache.get(key);
	}

	public boolean has(K key) {
		return cache.containsKey(key);
	}

	public void set(K key, V value) {
		cache.put(key, value);
	}
}
