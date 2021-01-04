
public interface Dictionary<K, V> {
    public List<K> keys();
    public List<V> values();
    public V get(Object key);
    public boolean isEmpty();
    public V put(K key, V value);
    public V remove(Object key);
    public int size();
    public boolean contains(K key);
}
