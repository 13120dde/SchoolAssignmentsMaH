package flertrådatProgrammeringL4;

import java.util.Iterator;
import java.util.LinkedList;

/**This class is responsible for placing each String-object from the list passed in as argument to this class's
 * constructor.
 *
 * This threads runs as long as there are more strings in the list passed in.
 *
 * Created by Patrik Lind on 2016-12-08.
 */
public class WriterThread implements Runnable {

    private BoundedBuffer buffer;
    private LinkedList<String> textIn;

    /**Instantiate this object.
     *
     * @param buffer : BoundedBuffer
     * @param textIn : LinkedList<String>
     */
    public WriterThread(BoundedBuffer buffer, LinkedList<String> textIn){

        this.textIn=textIn;
        this.buffer=buffer;

    }

    @Override
    public void run() {
        System.out.println("Writer thread started!");
        Iterator<String> i = textIn.iterator();
        while(i.hasNext()){
            buffer.writeData(i.next());
        }

        System.out.println("Writer thread finished!");
    }
}
