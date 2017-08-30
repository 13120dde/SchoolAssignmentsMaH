package bildTextU3TrieSuffix;

/**
 * Created by Patrik Lind on 2016-12-14.
 */
public class TestSuffixArr {


    public static void main(String[] args) {

        SuffixArray s = new SuffixArray("tobeornottobe$");

        System.out.println(s.longestMatch("ottobe"));
        System.out.println(s.longestMatch("ottobexxxxxx"));

    }

}
