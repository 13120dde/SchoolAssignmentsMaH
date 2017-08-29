package flertrådatProgrammeringL2;

import javax.swing.*;
import java.util.Arrays;

/**
 * Created by Patrik Lind on 2016-11-16.
 *
 *Stores a char-datatype that several threads try to access.
 */
public class CharacterBuffer {

    private char character=' ';

    //To determine if the other thread should wait.
    private boolean isupdated = false;


    /**Put the char passed in as argument in the buffer.
     *
     * @param c : char
     */
    public void writeAsynchronized(char c) {
        character = c;
    }

    /**Put the char passed in as argument in the buffer.
     *This method is synchronized hence thread-safe..
     *
     * @param c : char
     */
    public synchronized void writeSynchronized(char c) {

        if(isupdated){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        character = c;
        isupdated = true;
        notify();
    }

    /**Returns the char-variable stored in this buffer.
     *
     * @return : char
     */
    public char readAsynchronized(){
        return character;
    }

    /**Returns the char-variable stored in this buffer.
     *This method is synchronized hence thread-safe.
     * @return : char
     */
    public synchronized char readSynchronized(){
       if(!isupdated){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isupdated =false;
        char c = character;
        notify();
        return c;


    }

}
