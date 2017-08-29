package flertrådatProgrammeringL4;

import java.util.LinkedList;

/**This class is responsible for reading the strings from the buffer and placing the result in the destination-tab.
 * This thread runs as long as there are more strings to read.
 * Created by Patrik Lind on 2016-12-08.
 */
public class ReaderThread implements Runnable {

    private BoundedBuffer buffer;
    private int count, nbrOfStrings;
    private LinkedList<String> stringList;
    private GUIMonitor gui;


    /**Intantiate this object.
     *
     * @param buffer : BoundedBuffer
     * @param nbrOfStrings : int
     * @param guiMonitor : GUIMonitor
     */
    public ReaderThread(BoundedBuffer buffer, int nbrOfStrings, GUIMonitor guiMonitor){
        this.gui=guiMonitor;
        this.buffer=buffer;
        count = 0;
        this.nbrOfStrings=nbrOfStrings;
        stringList = new LinkedList<String>();

    }


    @Override
    public void run() {

        System.out.println("Reader thread started");
        while(count<nbrOfStrings){
            stringList.add(buffer.readData());
            count++;
        }

        System.out.println("Reader thread finished");
        gui.setDestinationText(stringList, buffer.getNbrReplacement());
        buffer.mark();
    }
}
