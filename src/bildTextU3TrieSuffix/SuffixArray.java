package bildTextU3TrieSuffix;

import java.util.Arrays;

/**
 * Created by Patrik Lind on 2016-12-13.
 */
public class SuffixArray {

    public Suffix[] getSuffixArray() {
        return suffixArray;
    }

    private Suffix[] suffixArray;


    public SuffixArray(String text){

        suffixArray = new Suffix[text.length()+1];

        //Fill the array with every suffix of text
        for(int i = 0; i<text.length()+1;i++){
            suffixArray[i] = new Suffix(i, text.substring(i, text.length()));

            //Add special-char $
            if(i==text.length()){
                suffixArray[i]=new Suffix(i, "");
            }
        }

        System.out.println(toString());
        System.out.println("Test sort:");
        Arrays.sort(suffixArray);
        System.out.println(toString());

    }

    /**
     *
     * @param pattern : String
     * @return index : int
     */
    public int longestMatch(String pattern){
        Suffix p = new Suffix(0,pattern);

        int m, c=-1, left = 0, right = suffixArray.length;

        while(left<=right && c!=0){
            m = left+(right-left)/2;
            c = p.compareTo(suffixArray[m]);
            if(c==0){
                return m;
            }else if(c<0){
                right = m-1;
            }else{
                left = m+1;
            }
        }

        return -1;
    }

    /**Returns a String representation of this suffix-array in format <i : I : suffix> where i corresponds to the
     * array-index where the suffix is stored, I corresponds to the starting-position of the suffix.
     *
     * @return :String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        for(int i =0; i<suffixArray.length;i++){
            s.append("i: "+i+suffixArray[i].toString()+"\n");
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
            this.suffix=suffix+"$";
        }

        public String getSuffix() {
            return suffix;
        }

        public String toString(){
            return "\tI: "+index+"\tsuffix: "+suffix;
        }

        @Override
        public int compareTo(Object o) {
            Suffix other = (Suffix)o;
            if(this.suffix.equals(other.getSuffix())){
                return 0;
            }else{
                int length = Math.min(this.suffix.length(), other.getSuffix().length());
                for(int i = 0; i< length; i++){
                    if(this.suffix.charAt(i)>other.getSuffix().charAt(i)){
                        return 1;
                    }else if(this.suffix.charAt(i)<other.getSuffix().charAt(i)) {
                        return -1;
                    }
                    //chars at i equal -> next iteratioin.
                }

            }

            return 0;
        }
    }


}
