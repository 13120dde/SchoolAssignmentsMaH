package flertrådatProgrammeringL1;

/**
 * Created by TSFANA on 2016-11-10.
 * Modified by Patrik Lind on 2016-11-13.
 *
 * Modified this class to act as a controller between the GUI and the Thread-classes that manipulate the GUI.
 * Model-View-Controller paradigm is suitable in this scenario.
 */
public class Assignment1Main
{


    //Thread objects
    private DisplayThread displayThread;
    private TriangleThread triangelThread;


    GUIDualThreads obj;

    public static void main(String[] args)
    {
        Assignment1Main main = new Assignment1Main();
        main.startProgram(main);

    }

    private void startProgram(Assignment1Main main) {
        obj = new GUIDualThreads(main);
        obj.Start();
    }

    //Following methods belong to DisplayThread assignment.

    protected void startDisplayThread() {
        if(displayThread==null){
            displayThread=new DisplayThread(this);
            displayThread.start();
        }
    }

    protected void stopDisplayThread() {
        if(displayThread!=null){
            displayThread.stopThread();
            displayThread=null;
        }
    }

    protected void animateDisplayText() {
        obj.animateDisplayText();
    }

    //Following methods belong to TriangleThread assignment.

    protected void startTriangleThread(){
        if(triangelThread==null){
            triangelThread = new TriangleThread(this);
            triangelThread.start();
        }
    }

    protected void stopTriangleThread(){
        if(triangelThread!=null){
            triangelThread.stopThread();
            triangelThread=null;
        }
    }

    protected void rotateTriangle() {
        obj.rotateTriangle();
    }

    /**Checks if any additional threads are alive and if so terminates them.

     */
    protected void killAllThreads() {
        if(triangelThread!=null && triangelThread.isAlive()){
            System.out.println("triangle-thread is alive, terminating");
            triangelThread.stopThread();
        }
        if(displayThread!=null  && displayThread.isAlive()){
            System.out.println("display-thread is alive, terminating");
            displayThread.stopThread();
        }
    }
}
