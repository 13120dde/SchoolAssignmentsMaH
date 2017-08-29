package algoritmerProjekt2;

/**
 * Created by Patrik Lind on 2016-09-22.
 *
 * This class is used to store two Object references which represent the key and value used in a hashtable.
 * The Entry objects will be used to build a hashtable.
 *
 *
 */
public class Entry {


    private Object key, value;

    /**Instantiate this object by passing in two Object references as argument. The Objects passed in are
     * stored as key and value in this Entry.
     *
     * @param key : Object
     * @param value : Object
     */
    public Entry(Object key, Object value){
        this.key=key;
        this.value=value;

    }

    @Override
    public boolean equals(Object obj){
        Entry keyToCompare = new Entry(obj, null);
        return key.equals(keyToCompare.key);
    }

    /**Returns the value object this entry holds. Returns NULL if no values are stored.
     *
     * @return value : Object
     */
    public Object getValue() {
        return value;
    }

    /**Returns the key object this entry holds. Returns NULL if no values are stored.
     *
     * @return key : Object
     */
    public Object getKey(){
        return key;
    }
}
