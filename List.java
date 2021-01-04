
import java.util.ListIterator;

public interface List<T> extends Iterable<T> {
    /**
     * Insert an element at a specified location.
     * @param index
     * @param obj
     * @throws IndexOutOfBoundsException
     */
    public void add(int index, T obj);
    
    /**
     * Append an object to the end of the list.
     * @param obj
     */
    public boolean add(T obj);
    
    public void clear();
    public boolean contains(T obj);
    
    /**
     * If obj is in the list, return the 
     * index of the first occurrence.
     * Otherwise, return -1.
     * @param obj
     * @return 
     */
    public int indexOf(T obj);
    
    public boolean isEmpty();
    
    public int lastIndexOf(T obj);
    
    /**
     * Get and return the value stored at the index.
     * 
     * @param index
     * @return
     * @throws IndexOutOfBoundsException
     */
    public T get(int index);
        
    public T remove(int index);
    public boolean remove(T obj);
    
    public ListIterator<T> listIterator(int index);
    
    /**
     * Update the value in the list at the specified index.
     * Return the old value
     * @throws IndexOutOfBoundsException
     * @param index
     * @param obj
     * @return 
     */
    public T set(int index, T obj);
    
    public int size();
    
    public Object[] toArray();
}
