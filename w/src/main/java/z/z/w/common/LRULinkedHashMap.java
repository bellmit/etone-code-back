/**
 * z.z.w.common.LRULinkedHashMap.java
 */
package z.z.w.common;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Wu Zhenzhen
 * @version Dec 12, 2012 9:39:32 PM
 */
public class LRULinkedHashMap<K, V> extends LinkedHashMap<K, V> implements
		Serializable {

	/**
	 * <br>
	 * Created on: Dec 12, 2012 9:39:42 PM
	 */
	private static final long serialVersionUID = 1926268829140601880L;

	private final int maxCacheSize;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Lock lock = new ReentrantLock();

	/**
	 * <br>
	 * Created on: 2013-7-12 下午04:07:07
	 * 
	 * @param maxCacheSize
	 */
	public LRULinkedHashMap(int maxCacheSize) {
		super(maxCacheSize, DEFAULT_LOAD_FACTOR, true);
		this.maxCacheSize = maxCacheSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#containsValue(java.lang.Object)
	 */
	@Override
	public boolean containsValue(Object value) {
		try {
			lock.lock();
			return super.containsValue(value);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#get(java.lang.Object)
	 */
	@Override
	public V get(Object key) {
		try {
			lock.lock();
			return super.get(key);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#removeEldestEntry(java.util.Map.Entry)
	 */
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		try {
			lock.lock();
			return size() > this.maxCacheSize;
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.LinkedHashMap#clear()
	 */
	@Override
	public void clear() {
		try {
			lock.lock();
			super.clear();
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#size()
	 */
	@Override
	public int size() {
		try {
			lock.lock();
			return super.size();
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public V put(K key, V value) {
		try {
			lock.lock();
			return super.put(key, value);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#putAll(java.util.Map)
	 */
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		try {
			lock.lock();
			super.putAll(m);
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#keySet()
	 */
	@Override
	public Set<K> keySet() {
		try {
			lock.lock();
			return super.keySet();
		} finally {
			lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.HashMap#containsKey(java.lang.Object)
	 */
	@Override
	public boolean containsKey(Object key) {
		try {
			lock.lock();
			return super.containsKey(key);
		} finally {
			lock.unlock();
		}
	}
}
