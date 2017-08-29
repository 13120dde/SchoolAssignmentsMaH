package bildTextu1StringMatching;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.Random;

/**This static class provides two different search algorithms with it's methods:
 * - naiveStringMatcher(String text, String pattern)
 * - rabinKarpMatcher(String text, String pattern)
 *
 * Both algorithms provide the same functionality, namely to find the provided pattern in the provided text and print
 * the indexes of the text-string where the pattern is found. The difference between the algorithms is that the latter
 * operates twice as fast as the former.
 *
 * Created by Patrik Lind, Johan Held on 2016-11-09.
 */
public class StringMatcher {

    /**Search for a pattern in a text, both passed in as argument. If the pattern is found, it's position in the
     * text-string is printed in console.
     *
     *
     * @param text : String
     * @param pattern : String
     */
    public static void naiveStringMatcher(String text, String pattern){


        int n = text.length();
        int m = pattern.length();
        int shift;

        //helper-variables to analyse the algorithm
        int counter =0, indexCounter = 0;


        System.out.print("Pattern found at index: ");
        for (shift= 0; shift< n-m; shift++){
            counter++;
            int i =0;

            for(i =0; i<m; i++){
                counter++;
                if(text.charAt(shift+i)!=pattern.charAt(i)){
                    break;
                }
                if(i==m-1){

                    //Just to make the result more readable
                    if(indexCounter>20){
                        System.out.println();
                        indexCounter=0;
                    }

                    System.out.print(shift+", ");
                    indexCounter++;
                }
            }

        }

        System.out.println("\nNbr of comparisons: "+counter);

    }

    /**Search for a pattern in a text, both passed in as argument. If the pattern is found, it's position in the
     * text-string is stored in a LinkedList and printed in console.
     *
     *
     * @param text : String
     * @param pattern : String
     * @return listOfIndexes : LinkedList<Integer>
     * @throws StringIndexOutOfBoundsException : if pattern.length() > text.length();
     */
    public static LinkedList<Integer> rabinKarpMatcher(String text, String pattern){

        LinkedList<Integer> matchedPositions = new LinkedList<Integer>();

        if(pattern.length()>text.length()){
            throw new StringIndexOutOfBoundsException("pattern must be of less size than text");
        }

        int n = text.length();
        int m = pattern.length();
        long textHash =0, patternHash=0;
        long q = longRandomPrime();
        int radixD = 256;


        //helper-variables to analyse the algorithm
        int counter =0, indexCounter = 0;

        //Prepare
        for (int i =0; i<m; i++){
            textHash = (radixD*textHash + text.charAt(i))%q;
            patternHash = (radixD*patternHash + pattern.charAt(i))%q;
        }

        long h = 1;
        for (int i=0; i<m-1; i++)
            h = (h*radixD) % q;

        //Search for pattern
        System.out.print("Pattern found at index: ");
        for(int shift = 0; shift<n-m; shift++){

            counter++;

            if(textHash==patternHash){
                for (int i =0; i<m; i++){
                    counter++;
                    if(text.charAt(i+shift)!=pattern.charAt(i)){
                        break;
                    }
                    if(i==m-1){

                        //Just to make the result more readable
                        if(indexCounter>20){
                            System.out.println();
                            indexCounter=0;
                        }
                        matchedPositions.add(shift);
                        System.out.print(shift+", ");
                        indexCounter++;
                    }
                }
            }

            //Perform the shift (take out high-order value and put in the next low-order value from array)
            if (shift < n-m){
                textHash = (radixD*(textHash - ((text.charAt(shift)*h) % q)) + text.charAt(shift+m)) % q;
                if (textHash < 0)
                    textHash += q;
            }

        }
        System.out.println("\nNbr of comparisons: "+counter);

        return matchedPositions;
    }

    /**Generate and return a large prime.
     *
     * @return prime : long
     */
    private static long longRandomPrime() {
        BigInteger prime = BigInteger.probablePrime(31, new Random());
        return prime.longValue();
    }

}
