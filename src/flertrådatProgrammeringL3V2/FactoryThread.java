package flertrådatProgrammeringL3V2;

import java.util.Random;

/**
 * Created by Patrik Lind on 2016-11-29.
 *
 * This class represents a producer-thread that generates objects of type FoodItem to store them in the buffer. The
 * FoodItem objects are hard-coded in this class and the method getRandomFoodItem() returns a random item from the
 * collection.
 */
public class FactoryThread implements Runnable {

    private Thread thread;
    private boolean threadRunning;
    private Storage buffer;
    private int randomInt;
    private GUISemaphore gui;
    private String factoryName;

    private FoodItem[] foodBuffer = new FoodItem[20];

    /**
     * Instantiate this object. The arguments passed in to this constructor are used to put items in the storage
     * and to update the GUI. Each instantiated thread will get random sleeptime in the span of 0.3-0.7 seconds.
     *
     * @param factoryName : String
     * @param buffer      : Storage
     * @param gui         : GUISemaphore
     */
    public FactoryThread(String factoryName, Storage buffer, GUISemaphore gui) {

        this.gui = gui;
        this.factoryName = factoryName;

        this.buffer = buffer;
        threadRunning = false;

        //
        randomInt = (new Random().nextInt(4) + 1) * 100;
        fillFoodBuffer();

    }

    /**
     * Returns the name of this object.
     *
     * @return factoryName : String
     */
    public String getFactoryName() {
        return factoryName;
    }

    /*Hard-coded foodbuffer with 20 different foodItems*/
    private void fillFoodBuffer() {
        foodBuffer[0] = new FoodItem(1.1, 0.5, "Milk");
        foodBuffer[1] = new FoodItem(0.6, 0.1, "Cream");
        foodBuffer[2] = new FoodItem(1.1, 0.5, "Yoghurt");
        foodBuffer[3] = new FoodItem(2.34, 0.66, "Butter");
        foodBuffer[4] = new FoodItem(2.4, 1.2, "Flower");
        foodBuffer[5] = new FoodItem(3.7, 1.8, "Sugar");
        foodBuffer[6] = new FoodItem(1.55, 0.27, "Salt");
        foodBuffer[7] = new FoodItem(0.6, 0.19, "Almonds");
        foodBuffer[8] = new FoodItem(1.98, 0.75, "Bread");
        foodBuffer[9] = new FoodItem(1.4, 0.5, "Donuts");
        foodBuffer[10] = new FoodItem(1.2, 1.3, "Jam");
        foodBuffer[11] = new FoodItem(4.1, 2.5, "Ham");
        foodBuffer[12] = new FoodItem(6.8, 3.9, "Chicken");
        foodBuffer[13] = new FoodItem(0.87, 0.55, "Salat");
        foodBuffer[14] = new FoodItem(2.46, 0.29, "Orange");
        foodBuffer[15] = new FoodItem(2.44, 0.4, "Apple");
        foodBuffer[16] = new FoodItem(1.3, 0.77, "Pear");
        foodBuffer[17] = new FoodItem(2.98, 2.0, "Soda");
        foodBuffer[18] = new FoodItem(3.74, 1.5, "Beer");
        foodBuffer[19] = new FoodItem(2.0, 1.38, "Hotdogs");

    }

    /**
     * Starts the thread. While the thread is running it will put a random FoodItem object in the buffer. The
     * run method is made thread-safe by using semaphores and a mutex.
     */
    public void startProducing() {
        if (thread == null) {
            thread = new Thread(this);
            threadRunning = true;
            thread.start();
        }
    }

    /*Returns a random FoodItem object.*/
    private FoodItem getRandomFoodItem() {
        return foodBuffer[new Random().nextInt(foodBuffer.length)];
    }

    /**
     * Stops the thread.
     */
    public void stopProducing() {
        if (thread != null) {
            threadRunning = false;
        }
    }

    @Override
    public void run() {
        System.out.println("Factory thread started");
        gui.updateProducerStatus(factoryName, "Status: Running");
        FoodItem temp = getRandomFoodItem();

        while (threadRunning) {

            try {
                thread.sleep(randomInt);
                Storage.semProducer.acquire();
                Storage.mutex.lock();

                if (buffer.remainingCapacity()>0 && buffer.remainingVolume()>=temp.getVolume()
                        && buffer.remainingWeight()>=temp.getWeight()) {
                    System.out.print(factoryName + ": ");
                    buffer.put(temp);
                    temp = getRandomFoodItem();
                }else{
                    //No items were able to be put in buffer, need to increment the semaphore.
                    Storage.semProducer.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println();
            }


            Storage.mutex.unlock();

            // GUISemaphore.mutex.release();
            Storage.semConsumer.release();


        }

        System.out.println("Factory thread stopped.\n");
        gui.updateProducerStatus(factoryName, "Status: Stopped");

    }
}
