package bildTextU3TrieSuffix;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Patrik Lind on 2016-12-12.
 */
public class TestTrie {

    TrieTree t = new TrieTree();

    public void fillTrie(String[] data){

        System.out.println("Putting strings into tree:");
        for(int i = 0; i<data.length; i++){
            t.put(data[i].toCharArray(), new Integer(i));
            System.out.print(data[i]+", ");
        }
        System.out.print("\b");
    }

    public void findWord(String input){

        Integer response = (Integer) t.get(input.toCharArray());
        if(response!=null){
            System.out.println(input+" found. Node data: "+response.toString());
        }else{
            System.out.println(input+" not found in tree.");
        }


    }

    public void testTrie(){
        Scanner s = new Scanner(System.in);
        System.out.print("\nType put_word to add word to tree or get_word to search for word in tree:");
        String input = s.nextLine();
        if(input.startsWith("put_")){
            t.put(input.substring(4).toCharArray(), new Integer(new Random().nextInt(Integer.MAX_VALUE)));

        }else if(input.startsWith("get_")){
            findWord(input.substring(4));
        }
        testTrie();
    }
    public static void main(String[] args) {

        String[] a = {"anna", "annars", "albin", "arm", "ankare", "alfons", "arme", "ananas", "almond", "alaska", "bil"
        , "batman", "batmobile", "brev", "bredvid", "bingo", "binder", "buske", "buskage", "citron", "citrus", "cirka", "sen", "senare", "senaste", "seger"};
        TestTrie prog = new TestTrie();
       // prog.fillTrie(a);
        prog.testTrie();


    }

}
