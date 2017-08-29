package flertrådatProgrammeringL4;

import javax.swing.*;

/**This class is responsible for modifying the document and replacing the strings passed in to the buffer's constructor.
 * This thread runs as long as there are more strings to modify.
 * Created by Patrik Lind on 2016-12-08.
 */
public class ModifierThread implements Runnable {

    private BoundedBuffer buffer;
    private int count, nbrOfStrings;


    /**Instantiate this object.
     *
     * @param buffer : Bounded Buffer
     * @param nbrOfStrings : int
     */
    public ModifierThread(BoundedBuffer buffer, int nbrOfStrings){
        count=0;
        this.nbrOfStrings=nbrOfStrings;
        this.buffer=buffer;
    }


    @Override
    public void run() {

        System.out.println("Modifier thread started.");
        while(count<nbrOfStrings){

            buffer.modify();
            count++;
        }
        System.out.println("Modifier thread finished.");
    }
}
