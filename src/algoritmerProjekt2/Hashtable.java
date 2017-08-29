package algoritmerProjekt2;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Patrik Lind on 2016-09-22.
 *
 * A simple HashTable class that stores the elements by chaining which means that the hashtable consists of a sized
 * array which in turn holds LinkedLists in each array-position. When we use the put(key, value) method to store
 * key-value pairs a hashindex is calculated to determine at what position in the sized array the objects will
 * be stored. If there already are objects stored at that position then the new objects will be put in the same
 * LinkedList (chaining).
 *
 * Additional methods could be implemented for this class such as:
 *  - calculateLoadFactor() which as the name suggests calculates the load factor of the hashtable.
 *  - resizeTable() which would increase the sized table's size to stay within the optimal range of the hashtable's
 *  loadfactor.
 */
public class Hashtable {

    private LinkedList<Object> insertionOrder = new LinkedList<>();
    private LinkedList<Entry>[] table;

    /**Instantiate this class by passing in a integer value as argument. The value will determinate how wide the initial
     * array will be. It doesn't represent how many objects can be stored in the hashtable since each position in the
     * sized array holds a dynamic list which in turn stores objects.
     *
     * The bigger the size of the instantiated hashtable the better performance will be by calling the tables functions.
     * The consequence is though that many positions in the array may be empty hence use unneccesary memory.
     *
     * @param size : int
     */
    public Hashtable(int size){
        table = (LinkedList<Entry>[]) new LinkedList<?>[size];

        for(int i = 0; i< size; i++){
            table[i]= new LinkedList<Entry>();
        }

    }

    //Generate and return a hashIndex for the key Object passed in as argument. Divisionmethod is used to calculate
    //the hashindex.
    private int hashIndex(Object key){
        int hashCode = key.hashCode();
        hashCode = hashCode % table.length;
        return (hashCode < 0 ) ? -hashCode : hashCode;
    }

    /**Get and return a specific value by passing in a key Object as argument.
     * The data will not be removed from the hashtable.
     * If the key passed in as argument can't be found then NULL is returned.
     *
     * @param key : Object
     * @return value : Object
     */
    public Object get(Object key){
        int hashIndex = hashIndex(key);
        LinkedList<Entry> entries = table[hashIndex];
        Iterator<Entry> it = entries.listIterator();

        while(it.hasNext()){
            Entry entry = it.next();
            if(entry.equals(key)){
                return entry.getValue();
            }
        }
        return null;
    }

    /**Returns the number of key-value pairs stored in this hashtable as integer.
     *
     * @return nbrOfElements : int
     */
    public int count(){
        return insertionOrder.size();
    }

    /**Put the key-value pair passed in as argument in the hashtable. If the table already has the key stored
     * then the passed in arguments will not be stored in the hashtable.
     * Before the objects will be stored a hashindex will be calculated to determinate on which position in the sized
     * array the pairs will be stored.
     *
     * @param key : Object
     * @param value : Object
     */
    public void put(Object key, Object value){

        if(get(key)==null){
            int hashIndex = hashIndex(key);
            table[hashIndex].add(new Entry(key, value));
            insertionOrder.add(value);
        }
    }

    /**Attempt to remove the key and its paired value from the hashtable if the key is found in the hashtable.
     * Otherwise nothing happens.
     *
     *
     * @param key : Object
     */
    public void remove(Object key){

        if(get(key)!=null){


            int hashIndex = hashIndex(key);

            for(int i = 0; i<table[hashIndex].size(); i++){
                if(table[hashIndex].get(i).equals(key)){
                    insertionOrder.remove(table[hashIndex].get(i).getValue());
                    table[hashIndex].remove(i);
                    //Might as well stop iterating when the key is removed since the table stores only unique keys.
                    break;
                }
            }
        }
    }

    /**Returns the insertionOrder LinkedList object which contain all the values stored in the hashtable.
     * This method is used for error checking and is not a neccesary part of the hashtable.
     *
     * @return insertionOrder : LinkedList<Object>
     */
    public LinkedList<Object> getInsertionOrder(){
        return insertionOrder;
    }

    /**Returns all keys stored in this hashtable by iterating through the table and each of its chained lists.
     * Ineficcient algoritm that has O(n^2). This method is here only if you want to check all the keys, and indirectly
     * values by getting the get(key) method.
     *
     * @return allKeys : LinkedList<Object>
     */
    public LinkedList<Object> getAllKeys(){
        LinkedList<Object> list = new LinkedList<>();

        for (int i =0; i<table.length; i++){
            for(int j =0; j<table[i].size(); j++){
                list.add(table[i].get(j).getKey());
            }

        }
        return list;
    }

}
