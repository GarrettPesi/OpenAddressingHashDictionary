public class OpenAddressingHashDictionary<K,V> implements Dictionary<K,V> {
    private Object[] table;
    private static final int DEFAULT_CAPACITY = 19;
    private int capacity;
    private int size;
    private static final double LOAD_CAPACITY = 0.5;
    
    public OpenAddressingHashDictionary() {
        this(DEFAULT_CAPACITY);
    }
    
    public OpenAddressingHashDictionary(int initialCapacity) {
        capacity = nextPrime(initialCapacity);
        table = new Object[capacity];
    }
    
    //Go through all the entries in the table. If null, skip to the next entry.
    //If not null, add all keys from chain into the List
    //Verify that the list size is equal to the dictionary size, then
    //return the List.
    @Override
    public List<K> keys() {
        List<K> keys = new AList<>();
        for(int i = 0; i < capacity; i++){
            Entry e = (Entry) table[i];
            if(e != null && !e.isRemoved()){
                keys.add(e.getKey());
            }
        }
        assert keys.size() == size;
        return keys;
    }

    //Same as keys method, but return the list of values.
    @Override
    public List<V> values() {
        List<V> values = new AList<>();
        for(int i = 0; i < capacity; i++){
            Entry e = (Entry) table[i];
            if(e != null && !e.isRemoved()){
                values.add(e.getValue());
            }
        }
        assert values.size() == size;
        return values;
    }

    @Override
    public V get(Object key) {
        int index = hashIndex((K)key);
        index = probe(index, (K)key);
        Entry e = (Entry)table[index];
        if(e != null && !e.isRemoved())
            return e.getValue();
        else
            return null;
    }

    @Override
    public boolean isEmpty() {
        return size==0;
    }

    @Override
    public V put(K key, V value) {
        int index = probe(hashIndex(key), key);
        Entry e = (Entry)table[index];
        if(e == null){
            if (isOverloaded()){
                rehash(capacity*2);
                index = probe(hashIndex(key), key);
            }
            table[index] = new Entry(key, value);
            size++;
            return null;
        }
        else{
            if (e.isRemoved())
                size++;
            V oldValue = e.getValue();
            e.setKey(key);
            e.setValue(value);
            return oldValue;
        }
    }

    @Override
    public V remove(Object key) {
        int index = hashIndex((K)key);
        index = probe(index, (K)key);
        Entry e = (Entry)table[index];
        if(e != null && !e.isRemoved()){
            V value = e.getValue();
            e.remove();
            size--;
            return value;
        }
        else
            return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key) {
        int index = hashIndex((K)key);
        index = probe(index, (K)key);
        Entry e = (Entry)table[index];
        return (e != null && !e.isRemoved());
    }
    
    private int hashIndex(K key) {
        int index = key.hashCode() % capacity;
        return (index < 0)?(index+capacity):index;
    }
    
    private boolean isOverloaded(){
        return (double)size/capacity > LOAD_CAPACITY;
    }
    
    private int probe(int index, K key){
        boolean found = false;
        int firstRemovedIndex = -1;
        int inc = 1;
        
        while(!found && (table[index] != null)){
            Entry e = (Entry)table[index];
            if(!e.isRemoved()){
                if(key.equals(e.getKey()))
                    found = true;
                else{
                    index = (index + inc) % capacity;
                    inc += 2;
                }
            }
            else{
                if(firstRemovedIndex == -1)
                    firstRemovedIndex = index;
                index = (index + inc) % capacity;
                inc += 2;
            }
        }
        
        if (found || (firstRemovedIndex == -1))
            return index;
        else
            return firstRemovedIndex;
    }
    
    private void rehash(int newCapacity){
        newCapacity = nextPrime(newCapacity);
        Object [] oldTable = table;
        table = new Object[newCapacity];
        capacity = newCapacity;
        int count = 0;
        for (int i = 0; i < oldTable.length; i++){
            Entry e = (Entry) oldTable[i];
            if (e != null && !e.isRemoved()){
                int newIndex = probe(hashIndex(e.getKey()), e.getKey());
                table[newIndex] = e;
                count++;
            }
        }
        
        assert count == size;
    }
    
    private int nextPrime(int n){
        int i = n;
        while(!isPrime(i))
            i++;
        return i;
    }
    
    private boolean isPrime(int n){
        if (n<2)
            return false;
        else if(n == 2)
            return true;
        else if(n%2 == 0)
            return false;
        else{
            for (int i = 3; i <= Math.sqrt(n); i +=2){
                if(n % i == 0){
                    return false;
                }
            }
            return true;
        }
    }
        
    private class Entry {
        private K key;
        private V value;
        private boolean removed;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
            removed = false;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
            reactivate();
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
            reactivate();
        }
        
        public boolean isRemoved(){
            return removed;
        }
        
        public void remove(){
            key = null;
            value = null;
            removed = true;
        }
        
        public void reactivate(){
            removed = false;
        }

    }
}