package maxFlow;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Patrik Lind on 2016-11-08.
 */
public class TestMBM {

    Scanner scan;
    BipartiteMatching mbm;
    int v;

    public void testFall4(){

        mbm = new BipartiteMatching(12, 5);
        mbm.addEdge(0,5);
        mbm.addEdge(0,6);
        mbm.addEdge(0,9);
        mbm.addEdge(1,5);
        mbm.addEdge(1,6);
        mbm.addEdge(2,6);
        mbm.addEdge(2,7);
        mbm.addEdge(2,8);
        mbm.addEdge(3,7);
        mbm.addEdge(4,6);
        mbm.addEdge(4,7);
        mbm.runMBM(10);

    }

    public void testFall2(){
        mbm = new BipartiteMatching(19, 9);
        mbm.addEdge(0,15);
        mbm.addEdge(1,10);
        mbm.addEdge(1,13);
        mbm.addEdge(1,15);
        mbm.addEdge(2,13);
        mbm.addEdge(3,9);
        mbm.addEdge(3,12);
        mbm.addEdge(4,16);
        mbm.addEdge(5,14);
        mbm.addEdge(6,15);
        mbm.addEdge(6,16);
        mbm.addEdge(7,9);
        mbm.addEdge(7,10);
        mbm.addEdge(7,14);
        mbm.addEdge(7,16);
        mbm.addEdge(8,11);
        mbm.runMBM(17);


    }
    public static void main(String[] args){

        //new TestMBM().run();

        TestMBM prog = new TestMBM();

      prog.testFall4();
      //  prog.testFall2();

    }

    private void run() {
        scan = new Scanner(System.in);


        System.out.println("Type in number of vertices in graph:");
        v = scan.nextInt();

        scan = new Scanner(System.in);
        System.out.println("Type in number of x-nodes:");
        int x = scan.nextInt();

        mbm = new BipartiteMatching(v+2, x);

        showMenu();
    }

    private void showMenu() {
        scan = new Scanner(System.in);
        System.out.println("1. Add Edge\n2. Run mbm on graph\n3. Exit");

        try{
            int choice = scan.nextInt();
            if(choice==1){
                addEdge();
            }else if(choice==2){
                mbm.runMBM(v);
            }else if(choice==3){
                System.exit(0);
            }else{
                System.out.println("Wrong choice...");
                showMenu();
            }

        }catch(NumberFormatException e){
            System.out.println("Wrong choice...");
            showMenu();
        }catch (InputMismatchException e){
            System.out.println("Wrong choice...");
            showMenu();
        }
    }

    private void addEdge() {
        scan = new Scanner(System.in);
        System.out.println("****Create Edge*****");
        System.out.println("1. Add Edge\n2. Back");

        try{
            int choice = scan.nextInt();
            if(choice == 1){
                System.out.println("Create a edge by typing in <<from,to>> where from and to are integers and" +
                        "capacity is a double.");
                scan = new Scanner(System.in);
                String[] edge = (scan.nextLine()).split(",");
                mbm.addEdge(Integer.parseInt(edge[0]), Integer.parseInt(edge[1]));
                addEdge();

            }else if(choice ==2){
                showMenu();
            }else{
                System.out.println("Wrong choice...");
                addEdge();
            }

        }catch(NumberFormatException e){
            System.out.println("Wrong choice...");
            addEdge();
        }catch(InputMismatchException e){
            System.out.println("Wrong choice...");
            addEdge();
        }
    }
}
