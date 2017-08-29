package flertrådatProgrammeringL3V2;

import sun.awt.Mutex;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;

/**
 * Created by Patrik Lind on 2016-11-30.
 *
 * The Storage class is responsible for storing objects of type FoodItem. It works as a buffer which consumer- and
 * producer-threads that interact with. In addition to number of items capacity, the storage limitations of volume and
 * weight.
 */
public class Storage

{
    private LinkedList<FoodItem> storedItems;
    private double maxWeight, currentWeight, maxVolume, currentVolume;
    private int maxCapacity, currentCapacity;
    private GUISemaphore gui;

    protected static Semaphore semConsumer, semProducer;//, mutex;
    protected static Mutex mutex;


    /**Instantiate this object. The capacity variable passed in as argument represent numer of items that can be placed
     * in the storage, additionally weight and volume represent the maximum weight and volume capacity of the storage.
     * The GUISemaphore reference is used to update the gui when items are being put/retrieved from the storage.
     *
     * @param gui : GUISemaphore
     * @param capacity : int
     * @param weight : double
     * @param volume : double
     */
    public Storage(GUISemaphore gui, int capacity, double weight, double volume) {

        this.gui = gui;
        storedItems = new LinkedList<FoodItem>();
        this.maxCapacity = capacity;
        this.maxWeight = weight;
        this.maxVolume = volume;

        semConsumer = new Semaphore(0);
        semProducer= new Semaphore(maxCapacity);
        mutex = new Mutex();
        gui.setStorageCapacity(capacity, weight, volume);
    }

    /**Puts a FoodItem object in the storage and calculate capacity, volume and weight according to item's data.
     *
     * @param foodItem : FoodItem
     */
    public void put(FoodItem foodItem) {

        storedItems.addLast(foodItem);
        currentCapacity++;
        currentVolume += foodItem.getVolume();
        currentWeight += foodItem.getWeight();
        gui.updateStorage(currentCapacity, currentVolume, currentWeight);

        System.out.println(foodItem.getItemName() + " was put in storage.");

    }

    /**Remove and return the first FoodItem object from the storage and update sthe storage's capacity. If there are
     * no items in the storage NULL is returned.
     *
     * @return : FoodItem
     */
    public FoodItem get() {
        FoodItem temp = storedItems.removeFirst();
        if(temp!=null){
            currentCapacity--;
            currentWeight -= temp.getWeight();
            currentVolume -= temp.getVolume();
            gui.updateStorage(currentCapacity, currentVolume, currentWeight);

        }
        return temp;
    }

    /**Returns the remaining number of items that can be stored in the storage.
     *
     * @return remainingCapacity : int
     */
    public int remainingCapacity() {
        return maxCapacity - currentCapacity;
    }

    /**Returns the remaining volume that can be stored in the storage.
     *
     * @return remainingVolume: double
     */
    public double remainingVolume() {
        return maxVolume - currentVolume;
    }

    /**Returns the remaining weight that can be stored in the storage.
     *
     * @return remainingWeight : double
     */
    public double remainingWeight() {
        return maxWeight - currentWeight;
    }


    /**Returns the weight of the first item in the storage. If there are no items then the MAX_VALUE is returned.
     *
     * @return itemWeight : double
     */
    public double getNextItemsWeight() {
        if(storedItems.peekFirst()!=null){
            return storedItems.peekFirst().getWeight();
        }
        return Double.MAX_VALUE;
    }

    /**Return the volume of the first item in the storage. It there are no items then the MAX_VALUE is returned.
     *
     * @return itemVolume : double
     */
    public double getNextItemsVolume() {
        if(storedItems.peekFirst()!=null){
            return storedItems.peekFirst().getVolume();
        }
        return Double.MAX_VALUE;
    }
}
