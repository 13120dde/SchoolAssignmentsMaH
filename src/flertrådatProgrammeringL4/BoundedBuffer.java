package flertrådatProgrammeringL4;


import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;


/**
 * This class is responsible for storing string objects in its buffer and provides thread-safe methods by using
 * monitors. For each String-object stored at a given place in the buffer, its status is also known.
 * <p>
 * <p>
 * Created by Patrik Lind on 2016-12-08.
 */

public class BoundedBuffer {

    private String[] buffer;
    private Status[] status;
    private int writePos, readPos, findPos, maxSize, start, nbrReplacement;
    private String findString, replaceString;
    private JTextPane textPaneSource, textPaneDestination;
    private boolean notifyUser;

    /**
     * Instantiate this object. the 'find' argument represents which Strings to search after and replace with 'replace'
     * argument.
     * <p>
     * The 'notifyUser', if true, should halt the threads working with this buffer and ask the user if he wants to
     * replace the current String object.
     * <p>
     * The 'size' argument represents the buffer-size and shouldn't be higher than number of words in a document.
     *  @param size                : int
     * @param textPaneSource      : JTextPane
     * @param textPaneDestination : JTextPane
     * @param notifyUser          : boolean
     * @param find                : String
     * @param replace             : String
     */
    public BoundedBuffer(int size, JTextPane textPaneSource, JTextPane textPaneDestination, boolean notifyUser, String find, String replace) {
        this.maxSize = size;
        this.textPaneSource = textPaneSource;
        this.textPaneDestination = textPaneDestination;
        this.notifyUser = notifyUser;
        this.findString = find;
        this.replaceString = replace;

        buffer = new String[size];
        status = new Status[size];
        writePos = 0;
        readPos = 0;
        findPos = 0;
        nbrReplacement = 0;
        start = 0;

        //Set starting status as Empty on each position.
        for (int i = 0; i < size; i++) {
            status[i] = Status.Empty;
        }

    }

    /**
     * Returns the amount of replaces done on the document.
     *
     * @return nbrReplacement : int
     */
    public int getNbrReplacement() {
        return nbrReplacement;
    }

    /**
     * Marks every replaced word in the destination-tab JtextPane by highlighting the background-color with green.
     * To find the indexes of each replaced string the Rabin-Karp Stringmatching algorithm is used.
     */
    public void mark() {


        DefaultHighlighter.DefaultHighlightPainter highlightPainter2 =
                new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);

        try {
            String text = textPaneDestination.getDocument().getText(0, textPaneDestination.getDocument().getLength());

            for (int i = -1; (i = text.indexOf(replaceString, i + 1)) != -1; ) {
                System.out.println(i);
               // if (text.charAt(i - 1) == ' ' || text.charAt(i + 1) == ' ') {
                    textPaneDestination.getHighlighter().addHighlight(i, i + replaceString.length(),
                            highlightPainter2);
                //}

            }

        } catch (BadLocationException e) {
            e.printStackTrace();
        }


    }

    /**
     * Checks the status of the string at each position in the buffer. If the status = Status.New and the String-object
     * matches to the string to replace then it will be replaced with the String-object replaceString.
     * <p>
     * If the Status of the object is anything other than Status.New then the thread will be blocked.
     * <p>
     * When done, the status of the modified object is changed to Status.Checked.
     */
    public synchronized void modify() {

        try {
            while (status[findPos] != Status.New) {
                wait();
            }

            String oldString = buffer[findPos];
            if (buffer[findPos].equals(findString)) {

                //Ask user if he wants to replace the substring at position 'start'.
                if (notifyUser) {
                    int x =JOptionPane.showConfirmDialog(null, "TEST", "HEJ", JOptionPane.YES_NO_OPTION);
                    if(x==JOptionPane.YES_OPTION){
                        buffer[findPos] = replace(findString, replaceString, start, findString.length());
                    }

                }else{
                    buffer[findPos] = replace(findString, replaceString, start, findString.length());
                }

            }

            start += oldString.length() + 1;
            status[findPos] = Status.Checked;

            findPos = (findPos + 1) % maxSize;

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        notify();

    }

    /**
     * Replaces the strSource with strReplace and marks the word in the source-tab JTextPane. The start argument
     * represents the index at position to replace the substring, the size argument represents the substring's
     * length.
     * <p>
     *
     * @param strSource  : String
     * @param strReplace : String
     * @param start      : int
     * @param size       : int
     * @return strReplace : String
     */
    public String  replace(String strSource, String strReplace, int start, int size) {
        DefaultHighlighter.DefaultHighlightPainter highlightPainter =
                new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);

            try {

                textPaneSource.getHighlighter().addHighlight(start, start + size,
                        highlightPainter);


            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            nbrReplacement++;

        return strReplace;
    }

    /**
     * Enables a thread to read from the buffer if the status of the String-object at given position = Status.Checked,
     * otherwise blocks the thread.
     * <p>
     * When done changes the status of read object to Status.Empty.
     *
     * @return word : String
     */
    public synchronized String readData() {
        String word = "ERROR";

        try {
            while (status[readPos] != Status.Checked) {
                wait();
            }
            word = buffer[readPos];
            status[readPos] = Status.Empty;
            readPos = (readPos + 1) % maxSize;
            notify();


        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return word;
    }

    /**
     * Puts the String-object passed in as argument in the buffer if the the status at given position is Status.Empty,
     * otherwise blocks the thread.
     * <p>
     * When done changes the status of the object to Status.New.
     *
     * @param word : String
     */
    public synchronized void writeData(String word) {

        try {
            while (status[writePos] != Status.Empty) {
                wait();
            }

            buffer[writePos] = word;
            status[writePos] = Status.New;
            writePos = (writePos + 1) % maxSize;
            notify();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
