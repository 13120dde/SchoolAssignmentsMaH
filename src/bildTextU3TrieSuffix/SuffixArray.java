package bildTextU3TrieSuffix;

import java.util.Arrays;

/**
 * Created by Patrik Lind on 2016-12-13.
 */
public class SuffixArray {


    private Suffix[] suffixArray;
    private String T;


    public SuffixArray(String text){

        suffixArray = new Suffix[text.length()];
        this.T =text;

        //Fill the array with every suffix of text
        for(int i = 0; i<text.length();i++){
            suffixArray[i] = new Suffix(i, text.substring(i, text.length()));

        }

        System.out.println(toString());
        System.out.println("Test sort:");
        Arrays.sort(suffixArray);
        System.out.println(toString());

    }


    public int compareWithSuffix(int i, String P){
        int c = 0, j = 0;
        int pChar, tChar;

        while(j< P.length() && c==0){
            if(i+j <= T.length()){

                pChar = P.charAt(j);
                tChar = T.charAt(i+j);
                if (T.contains(P.charAt(j) + "")) {
                    c = pChar - tChar;
                } else {
                    StringBuilder sb = new StringBuilder(P);
                    sb.deleteCharAt(j);
                    P = sb.toString();
                    j = 0;
                    continue;

                }
            }else{
                c=1;
            }
            j++;
        }

        return c;
    }

    private int compare(Suffix suffix, String pattern) {
        int c= 0, j =0;
        int len = Math.min(pattern.length(), suffix.suffix.length());

        while(j<len && c ==0){
            if(suffix.index+j<= T.length()){

                if(suffix.suffix.contains(pattern.charAt(j)+"")){
                    c = pattern.charAt(j)- T.charAt(suffix.index+j);
                }else{
                    StringBuilder sb = new StringBuilder(pattern);
                    sb.deleteCharAt(j);
                    pattern = sb.toString();
                    j = 0;
                    continue;
                }

            }else{
                c = 1;
            }
            j++;
        }

        return c;
    }


    public int longestMatch(String pattern){
        int l =0, r= suffixArray.length-1, c=-1, m;

        while(c!=0 && l<=r){

            m = l + (r-l)/2;
            c = compareWithSuffix(suffixArray[m].index, pattern);
//            c = compare(suffixArray[m], pattern);

            if(c==0){
                return suffixArray[m].index;
            }
            if(c<0){
                r = m -1;
            }
            if(c>0){
                l = m+1;
            }
        }

        return l;
    }


    /**Returns a String representation of this suffix-array in format <i : I : suffix> where i corresponds to the
     * array-index where the suffix is stored, I corresponds to the starting-position of the suffix.
     *
     * @return :String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i =0; i<suffixArray.length;i++){
            s.append("i: "+i+"\t"+suffixArray[i].toString()+"\n");
        }
        return s.toString();

    }

    /**Inner class that represents a suffix. Each object holds the suffix-value and the index where the suffix is found
     * in the string.
     *
     * This class implements Comparable for the possibility to sort.
     *
     */
    private class Suffix implements Comparable{

        private int index;
        private String suffix;

        public Suffix(int index, String suffix){

            this.index=index;
            this.suffix=suffix;
        }

        public int length(){
            return suffix.length();
        }

        public char charAt(int i){
            return suffix.charAt(i);
        }

        public String toString(){
            return "\tI: "+index+"\tsuffix: "+suffix;
        }

        @Override
        public int compareTo(Object o) {
            Suffix otherSuffix = (Suffix)o;

            int length = Math.min(this.length(), otherSuffix.length());
            for (int i = 0; i < length; i++){
                if(this.charAt(i)<otherSuffix.charAt(i)){
                    return -1;
                }
                if(this.charAt(i)>otherSuffix.charAt(i)){
                    return 1;
                }
            }

            return this.length()-otherSuffix.length();
        }


    }


}
