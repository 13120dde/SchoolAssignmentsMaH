package bildTextu1StringMatching;

import java.util.Random;

/**
 * Created by Patrik Lind on 2016-11-09.
 */
public class TestStringMatcher {

    private String generatedTextWC, generatedPatternWC, generatedTextAD, generatedPatternAD;

    public static void main(String[] args){
        TestStringMatcher t = new TestStringMatcher();

      //  StringMatcher.naiveStringMatcher("aaaaaaaaaa","aaaaa");

        //t.testWithWorstCase();
        t.testWithActualData();

    }

    private void generateWorstCaseData(int n){
        System.out.println("Generating worst-case data...");
        generatedTextWC = new String();
        generatedPatternWC = new String();

        for (int i = 0; i<n; i++){
            if(i<n/2){
                generatedPatternWC+="a";
            }
            generatedTextWC+="a";
        }
        System.out.println("Done.");
    }

    private void generateActualData(int n, char[] chars){

        System.out.println("Generating actual data...");
        generatedTextAD = new String();
        Random r = new Random();

        for (int i = 0 ; i < n; i++){
            generatedTextAD+=chars[r.nextInt(chars.length)];
        }
        System.out.println("Done.");

    }


    private void testWithActualData(){
        char[] textChars ={'a','d','t','g'};


        System.out.println("*****************ACTUAL DATA****************");


        //Testdata 1
        generateActualData(1000, textChars );
        generatedPatternAD = "adtgggttgtgtgtgtggg";
        System.out.println("Testdata 1");
        System.out.println("Text: "+generatedTextAD.substring(0, 50) + "..., length: "+generatedTextAD.length());
        System.out.println("Pattern: "+generatedPatternAD+ ", length: "+generatedPatternAD.length());

        System.out.println("\n***Naive***");
        StringMatcher.naiveStringMatcher(generatedTextAD, generatedPatternAD);
        System.out.println();
        System.out.println("***Rabin-Karp***");
        StringMatcher.rabinKarpMatcher(generatedTextAD,generatedPatternAD);
        System.out.println();

        //Testdata 2
        System.out.println("Testdata 2");
        generateActualData(2000, textChars);
        generatedPatternAD = "adtg";
        System.out.println("Text: "+generatedTextAD.substring(0, 50)+ "..., length: "+generatedTextAD.length());
        System.out.println("Pattern: "+generatedPatternAD+ ", length: "+generatedPatternAD.length());

        System.out.println("\n***Naive***");
        StringMatcher.naiveStringMatcher(generatedTextAD, generatedPatternAD);
        System.out.println();
        System.out.println("***Rabin-Karp***");
        StringMatcher.rabinKarpMatcher(generatedTextAD,generatedPatternAD);
        System.out.println();

        //Testdata 3

        System.out.println("Testdata 3");
        generateActualData(4000, textChars);
        generatedPatternAD="adtg";
        System.out.println("Text: "+generatedTextAD.substring(0, 50) + "..., length: "+generatedTextAD.length());
        System.out.println("Pattern: "+generatedPatternAD+ ", length: "+generatedPatternAD.length());

        System.out.println("\n***Naive***");
        StringMatcher.naiveStringMatcher(generatedTextAD, generatedPatternAD);
        System.out.println();
        System.out.println("***Rabin-Karp***");
        StringMatcher.rabinKarpMatcher(generatedTextAD,generatedPatternAD);
        System.out.println();

    }

    private void testWithWorstCase(){
        System.out.println("****************WORST CASE****************");


        //Testdata 1
        System.out.println("Testdata 1");
        generateWorstCaseData(1000);
        System.out.println("Text: "+generatedTextWC.substring(0, 50)+ "..., length: "+generatedTextWC.length());
        System.out.println("Pattern: "+generatedPatternWC+ ", length: "+generatedPatternWC.length());

        System.out.println("\n***Naive***");
        StringMatcher.naiveStringMatcher(generatedTextWC, generatedPatternWC);
        System.out.println();
        System.out.println("***Rabin-Karp***");
        StringMatcher.rabinKarpMatcher(generatedTextWC,generatedPatternWC);
        System.out.println();

        //Testdata 2
        System.out.println("Testdata 2");

        generateWorstCaseData(2000);
        System.out.println("Text: "+generatedTextWC.substring(0, 50)+ "..., length: "+generatedTextWC.length());
        System.out.println("Pattern: "+generatedPatternWC.substring(0,50)+ "..., length: "+generatedPatternWC.length());

        System.out.println("\n***Naive***");
        StringMatcher.naiveStringMatcher(generatedTextWC, generatedPatternWC);
        System.out.println();
        System.out.println("***Rabin-Karp***");
        StringMatcher.rabinKarpMatcher(generatedTextWC,generatedPatternWC);
        System.out.println();

        //Testdata 3
        System.out.println("Testdata 3");
        generateWorstCaseData(4000);
        System.out.println("Text: "+generatedTextWC.substring(0, 50)+ "..., length: "+generatedTextWC.length());
        System.out.println("Pattern: "+generatedPatternWC.substring(0,50)+ "..., length: "+generatedPatternWC.length());

        System.out.println("\n***Naive***");
        StringMatcher.naiveStringMatcher(generatedTextWC, generatedPatternWC);
        System.out.println();
        System.out.println("***Rabin-Karp***");
        StringMatcher.rabinKarpMatcher(generatedTextWC,generatedPatternWC);
        System.out.println();
    }

}
