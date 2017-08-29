package flertrådatProgrammeringL1;

/**
 * Created by Patrik Lind on 2016-11-11.
 */
public class TriangleThread extends Thread{


    private Assignment1Main controller;

    //Flag-variable to check if thread is running.
    private boolean isRunning = false;

    /**Instantiate this object by passing in a reference to Assigment1Main as argument
     * (which acts as a controller in the system).
     *
     * @param controller : Assigment1Main
     */
    public TriangleThread(Assignment1Main controller) {
        this.controller =controller;

    }

    public void run(){
        isRunning=true;
        System.out.println("Triangle thread started.");

        while(isRunning){

            try {
                Thread.sleep(10);
                controller.rotateTriangle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Triangle thread terminated.");

    }

    /**Stops this thread by changing the flag-variable's value to false.
     *
     */
    public void stopThread() {
        if(isRunning){
            isRunning=false;
        }
    }
}
