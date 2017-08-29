package flertrådatProgrammeringL2;

import javax.swing.*;
import java.util.Random;

/**
 * Created by Patrik Lind on 2016-11-16.
 */
public class ReadThread implements Runnable{

    private Thread thread;
    private boolean  threadRunning;
    private CharacterBuffer buffer;
    private String result="";
    private int amountToRead;

    private GUIMutex gui;

    /**Instantiate this object by passing in a integer value of the nuber of characters to read, references to the
     * CharacterBuffer and the GUIMutex objects.
     *
     *  @param buffer : CharacterBuffer
     * @param guiMutex : GUIMutex
     * @param length
     */
    public ReadThread(CharacterBuffer buffer, GUIMutex guiMutex, int length) {
        this.buffer = buffer;
        gui = guiMutex;
        amountToRead = length;
    }


    /**Starts the thread.
     *
     */
    public void start(){
        if(thread==null){
            thread = new Thread(this);
            thread.start();
            threadRunning = true;
        }
    }

    /**Stops the thread.
     *
     */
    public void stop(){
            threadRunning = false;
    }

    @Override
    public void run() {

        char c;
        int i = 0;

        while(threadRunning){

            try {
                Thread.sleep(500);

                //Check if synchronized-radiobutton is selected in the gui.
                if(gui.getSynchSelected()){
                    c=buffer.readSynchronized();
                }else{
                    c = buffer.readAsynchronized();
                }


                result+=c;
                gui.appendToR(c);
                i++;

                //Stop the thread if i exceeds number of characters to read.
                if(i>=amountToRead){
                    stop();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Final update on the gui.
        gui.setRecieved(result);
        gui.compareStrings();


    }
}
