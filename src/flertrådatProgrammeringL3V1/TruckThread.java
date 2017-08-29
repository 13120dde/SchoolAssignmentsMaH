package flertrådatProgrammeringL3V1;

import java.util.LinkedList;

/**
 * Created by Patrik Lind on 2016-11-29.
 * <p>
 * This class represents a consumer-thread that retrieves objects from the buffer. This class has constaints on how
 * many objects it can retrieve, aswell as maximum volume and weight.
 */
public class TruckThread implements Runnable {

    private final boolean continueLoad;
    private String consumerName;
    private Thread thread;
    private boolean threadRunning;
    private double maxWeight, maxVolume, currentWeight, currentVolume;
    private int currentCapacity, maxCapacity;
    private Storage buffer;
    private LinkedList<FoodItem> cargo = new LinkedList<FoodItem>();
    private GUISemaphore gui;

    /**
     * Instantiate this object.
     *
     * @param consumerName
     * @param buffer
     * @param gui
     * @param weight
     * @param volume
     * @param nbrOfItems
     * @param continiueLoad
     */
    public TruckThread(String consumerName, Storage buffer, GUISemaphore gui, double weight, double volume, int nbrOfItems, boolean continiueLoad) {

        this.gui = gui;
        this.consumerName = consumerName;
        this.buffer = buffer;
        threadRunning = false;

        this.maxWeight = weight;
        this.maxVolume = volume;
        this.maxCapacity = nbrOfItems;
        this.continueLoad = continiueLoad;

        currentCapacity = 0;
        currentVolume = 0;
        currentWeight = 0;

    }

    /**
     * Starts the thread. While the thread is running it will attempt to retrieve objects from the buffer if the buffer
     * isn't empty. When the thread reaches its capacity-limit it will either terminate or wait for 5 seconds (if
     * 'continiue load' checkbox is selected.
     */
    public void startConsuming() {
        if (thread == null) {
            thread = new Thread(this);
            threadRunning = true;
            thread.start();
        }
    }

    /**
     * Stops the thread.
     */
    public void stopConsuming() {
        if (thread != null) {
            threadRunning = false;
        }
    }

    /**
     * Returns true if there is more place to store the items, othewise false.
     *
     * @return : boolean
     */
    public boolean isFull() {
        if (currentCapacity >= maxCapacity) {
            return true;
        } else {
            if ((int) currentVolume >= maxVolume - 1 || (int) currentWeight >= maxWeight - 1) {
                return true;
            }
            return false;
        }
    }

    private double remainingVolume() {
        return maxVolume - currentVolume;
    }

    private double remaningWeight() {
        return maxWeight - currentWeight;
    }

    @Override
    public void run() {

        gui.updateConsumerStatus(consumerName, ": Running");
        while (threadRunning) {
            if (!isFull()) {

                try {

                    thread.sleep(100);
                    FoodItem item = buffer.get();
                    if (item != null) {
                        currentCapacity++;
                        currentWeight += item.getWeight();
                        currentVolume += item.getVolume();
                    }

                    gui.updateConsumerCargo(item.getItemName(), consumerName, currentCapacity, currentVolume, currentWeight);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            } else {
                //Put the thread to sleep, reset capacity-variables and update the gui.
                if (continueLoad) {
                    try {
                        int s = 5;
                        while (s >= 0) {
                            gui.updateConsumerStatus(consumerName, ": Sleeping: " + s);
                            thread.sleep(1000);
                            s--;
                        }
                        currentVolume = 0;
                        currentWeight = 0;
                        currentCapacity = 0;
                        gui.updateConsumerCargo("clear list", consumerName, currentCapacity, currentVolume, currentWeight);
                        gui.updateConsumerStatus(consumerName, ": Running");

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    stopConsuming();
                }

            }
        }

        gui.updateConsumerStatus(consumerName, ": Idle");
        gui.changeButtonAvaibility(consumerName);

    }
}
