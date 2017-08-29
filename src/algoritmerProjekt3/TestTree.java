package algoritmerProjekt3;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by Patrik Lind on 2016-10-12.
 */
public class TestTree {

    private Scanner scan = new Scanner(System.in);
    private BinaryTree bTree = new BinaryTree();
    private AvlTree aTree = new AvlTree();


    public static void main(String[] args){

       TestTree t = new TestTree();
        t.fillTrees();
        t.menu();

    }

    private void fillTrees() {
        System.out.println("Filling trees with 40 random values...");
        int rnd=0;
        Random rand = new Random(2);
        for (int i = 0; i < 40; ++i){
            rnd = rand.nextInt(10000000);
            aTree.add(rnd);
            bTree.add(rnd);
        }
    }

    private void menu() {
        scan = new Scanner(System.in);

        System.out.println("Menu2\n1:Binary Search Tree\n2: AVL Tree\n3: Quit");

        try{
            int choice = scan.nextInt();
            if(choice<1 || choice > 3){
                throw new Exception("Incorrect input, try again");
            }
            if(choice==1){
                testTree(true);
            }
            if(choice==2){
                testTree(false);
            }
            if(choice==3){
                System.exit(0);
            }

        }catch(InputMismatchException e){
            System.out.println("Incorrect input, try again");
            menu();

        }catch(Exception e){
            System.out.println(e.getMessage());
            menu();

        }






    }

    private void testAvlTree() {
        scan = new Scanner(System.in);
        System.out.println("######Binary Tree######");

    }

    private void testTree(boolean testBinary) {

        scan = new Scanner(System.in);
        if(testBinary){
            System.out.println("######Binary Tree######");
        }else{
            System.out.println("######AVL Tree######");
        }

        System.out.println("Choose:\n1:Insert\n2: Find\n3: Delete\n4: Show tree\n5: Back");

        try{
            int choice = scan.nextInt();
            if(choice<1 || choice > 5){
                throw new Exception("Incorrect input, try again");
            }
            if(choice==1){
                insertIntoTree(testBinary);
            }
            if(choice==2){
                findInTree(testBinary);
            }
            if(choice==3){
                deleteFromTree(testBinary);

            }if(choice==4){
                printTree(testBinary);
            }if(choice==5){
                menu();
            }

        }catch(InputMismatchException e){
            System.out.println("Incorrect input, try again");
            menu();

        }catch(Exception e){
            System.out.println(e.getMessage());
            menu();

        }
    }

    private void printTree(boolean testBinary) {
        if(testBinary){
            bTree.printTree();
            System.out.print("In Order: ");
            bTree.inOrder(bTree.getRoot());
        }else{
            aTree.printTree();
            System.out.print("In Order: ");
            aTree.inOrder(aTree.getRoot());
        }
        System.out.println();
        testTree(testBinary);
    }

    private void deleteFromTree(boolean b) {
        scan = new Scanner(System.in);
        System.out.println("Delete value: ");
        try{
            int value = scan.nextInt();
            if(b){
                bTree.delete(value);
            }else {
                aTree.delete(value);
            }
            testTree(b);

        }catch (InputMismatchException e){
            System.out.println("incorrect input, type a integer.");
            deleteFromTree(b);
        }
    }

    private void findInTree(boolean b) {
        scan = new Scanner(System.in);
        System.out.println("Find value: ");
        try{
            int value = scan.nextInt();

            if(b){
                if(bTree.find(bTree.getRoot(), value)!=null){
                    System.out.println(value+" found");
                }else{
                    System.out.println(value+" not found");
                }
                testTree(b);
            }else{
                if(aTree.find(aTree.getRoot(), value)!=null){
                    System.out.println(value+" found");
                }else{
                    System.out.println(value+" not found");
                }
                testTree(b);
            }
        }catch(InputMismatchException e){
            System.out.println("Incorrect input, type an integer.");
            findInTree(b);
        }
    }

    private void insertIntoTree(boolean b) {
        scan = new Scanner(System.in);

        System.out.println("Type a value, you can input several values by comma-seperating them (Q for quit):");
        String input = scan.nextLine();

        if(input.equalsIgnoreCase("Q")) {
            testTree(b);

        }else if (input.contains(",")) {
            String[] numbers = input.split(",");
            for(int i= 0 ; i<numbers.length;i++){

                if(b==true){
                    bTree.add(Integer.parseInt(numbers[i]));
                }else{
                    aTree.add(Integer.parseInt(numbers[i]));
                }
            }
            testTree(b);
        }else{
            if(b){
                bTree.add(Integer.parseInt(input));
            }else{
                aTree.add(Integer.parseInt(input));
            }
            testTree(b);
        }
    }
}
