package bildTextU3TrieSuffix;

import java.util.Arrays;

/**
 * Created by Patrik Lind, Johan Held on 2016-12-13.
 */
public class SuffixArray {


    private Suffix[] suffixArray;


    public SuffixArray(String text){

        suffixArray = new Suffix[text.length()];

        //Fill the array with every suffix of text
        for(int i = 0; i<text.length();i++){
            suffixArray[i] = new Suffix(i, text.substring(i, text.length()));

        }

        System.out.println(toString());
        System.out.println("Test sort:");
        Arrays.sort(suffixArray);
        System.out.println(toString());

    }


    private int compare(String suffix, String pattern) {
       int length = Math.min(suffix.length(),pattern.length());
       int j=0, comp, hits =0;
       while(j<length){
           comp = pattern.charAt(j)-suffix.charAt(j);
           if(comp==0){
               hits++;
           }else{
               hits++;
               if(comp<0){
                   return -hits;
               }else if(comp>0){
                   return hits;
               }
           }

           j++;
       }

       return 0;
    }


    public int longestCommonPrefix(String pattern){
        int left =0, right= suffixArray.length-1, comp=-1, middle;
        int a=0, b=0;

        while(left<=right && comp!=0){

            middle = left + (right-left)/2;
            comp = compare (suffixArray[middle].suffix, pattern);
            if(comp==0){
                return suffixArray[middle].index;
            }


            int abs = Math.abs(comp)-1;
            if(abs>b){
                a=suffixArray[middle].index;
                b=abs;
            }

            if(comp<0){
                right = middle -1;
            }
            if(comp>0){
                left = middle+1;
            }
        }

        if(b>0){
            return a;
        }
        if(pattern.length()>1){
            return longestCommonPrefix(pattern.substring(1));
        }

        return left;
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
