/**************************************************************************
 * <pre>
 *     FileName: z.z.w.project.util.lru.LRUMapCache.java
 *         Desc: 
 *      @author: Wu Zhenzhen - myhongkongzhen@gmail.com
 *     @version: 2013-10-2 下午09:01:51 
 *   LastChange: 2013-10-2 下午09:01:51 
 *      History:
 * </pre>
 **************************************************************************/
package z.z.w.project.util.lru;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * z.z.w.project.util.lru.LRUMapCache.java
 */
public class LRUMapCache<K, V> extends LinkedHashMap<K, V> {
	/**
	 * <br>
	 * Created on: 2013-7-12 下午04:06:16
	 */
	private static final long serialVersionUID = 5264913361041750292L;
	private final int maxCacheSize;
	private static final float DEFAULT_LOAD_FACTOR = 0.75f;
	private final Lock lock = new ReentrantLock();

	/**
	 * <br>
	 * Created on: 2013-7-12 下午04:07:07
	 * 
	 * @param maxCacheSize
	 */
	public LRUMapCache(int maxCacheSize) {
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
