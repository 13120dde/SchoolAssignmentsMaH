package algoritmerProjekt2;

import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by Patrik Lind on 2016-09-22.
 *
 * This class is a simple translator which uses a hashtable to store english words and it's swedish translation.
 * The function of this class is to store words, translate words, show all words stored and delete words.
 */
public class Translator {

    private Hashtable table = new Hashtable(15);
    Scanner scanner;

    public void menu() {

        scanner  = new Scanner(System.in);
        System.out.println("\n****MENU*****************");
        System.out.println("1- Show all words\n2 - Translate word\n3 - Add word\n4 - Remove word\n5 - Exit");



        try{

            System.out.print("Select: ");
            int choice = scanner.nextInt();

            if(choice <1 || choice > 5){
                throw new IncorrectInputException("Incorrect input, try again");
            }



            switch (choice){
                case 1:
                    showTable();
                    break;
                case 2:
                    translateWord();
                    break;
                case 3:
                    addWord();
                    break;
                case 4:
                    removeWord();
                    break;
                case 5:
                    System.out.println("Goodbye");
                    System.exit(0);


            }

        }catch(InputMismatchException e){

            System.out.println("Incorrect input, try again");
            menu();

        } catch (IncorrectInputException e) {
            System.out.println(e.getMessage());
            menu();
        }


    }

    private void showTable() {
        LinkedList<Object> keys = table.getAllKeys();

        System.out.println("\n****All words*****************");

        for (int i = 0; i< keys.size(); i++){
            System.out.println((i+1)+":\t"+keys.get(i)+" = "+table.get(keys.get(i)));
        }
        System.out.println("\nNumber of words in table: "+table.count());
        menu();

    }

    private void removeWord() {
        scanner = new Scanner(System.in);
        System.out.println("\n****Remove word*****************");
        System.out.print("Word to remove (type '-menu' to exit): ");
        String word = scanner.nextLine();

        if(word.equalsIgnoreCase("-menu")){
            menu();
        }else{
            table.remove(word);
            removeWord();
        }
    }

    private void addWord() {

        scanner = new Scanner(System.in);

        System.out.println("\n****Add word*****************");
        System.out.print("Add words in following format <english_word=swedish_word>.\nType '-menu' to exit");

        System.out.print("Input: ");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("-menu")){
            menu();
        }else{
            String[] words = input.split("=");
           table.put(words[0], words[1]);
            addWord();
        }
    }

    private void translateWord() {
        System.out.println("\n****TRANSLATE WORD*****************");
        scanner = new Scanner(System.in);

        System.out.println("Word to translate (type '-menu' to exit): ");
        String input = scanner.nextLine();

        if(input.equalsIgnoreCase("-menu")){
            menu();
        }else{
            if(table.get(input)!=null){
                System.out.println(input+" = "+table.get(input));
            }else{
                System.out.println(input +" cant be found in table.");
            }
            translateWord();
        }

    }

    public static void main(String[] args){
        System.out.println("\n****SIMPLE TRANSLATOR*****************");
        System.out.println("****CREATED BY: PATRIK LIND       ****");
        System.out.println("**************************************");
        new Translator().menu();

    }

    private class IncorrectInputException extends Exception{
        public IncorrectInputException(String msg){
            super(msg);
        }
    }
}
