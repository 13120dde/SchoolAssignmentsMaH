package flertrådatProgrammeringL2;

import javax.swing.*;
import java.util.Random;

/**
 * Created by Patrik Lind on 2016-11-16.
 */
public class WriterThread implements Runnable {

    private Thread thread;
    private boolean threadRunning = false;
    private String stringToWrite;
    private CharacterBuffer buffer;

    private GUIMutex gui;

    /**Instantiate this object by passing in the String object to write to the buffer,  references to the
     * CharacterBuffer and GUIMutex objects.
     *
     * @param buffer : CharacterBuffer
     * @param guiMutex : GUIMutex
     * @param stringToWrite : String
     */
    public WriterThread(CharacterBuffer buffer, GUIMutex guiMutex, String stringToWrite) {
        this.buffer=buffer;
        this.gui = guiMutex;
        this.stringToWrite = stringToWrite;

    }

    /**Start the thread.
     *
     */
    public void start(){
        if(thread == null){
            thread = new Thread(this);
            thread.start();
            threadRunning = true;
        }
    }

    /**Stop the thread.
     *
     */
    public void stop(){
            threadRunning = false;
    }

    @Override
    public void run() {
        int i = 0;

        while(threadRunning){

            try {
                Thread.sleep(200);

                //Check if synchronized-radiobutton is selected in the gui.
                if(gui.getSynchSelected()){
                    buffer.writeSynchronized(stringToWrite.charAt(i));
                }else{
                    buffer.writeAsynchronized(stringToWrite.charAt(i));
                }

                gui.appendToW(stringToWrite.charAt(i));
                i++;

                //Stops this thread when all characters are put in buffer.
                if(i>=stringToWrite.length()){
                    stop();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Final update on the gui.
        gui.setTransmitted(stringToWrite);

    }
}
