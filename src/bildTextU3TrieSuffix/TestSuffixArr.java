package bildTextU3TrieSuffix;

/**
 * Created by Patrik Lind on 2016-12-14.
 */
public class TestSuffixArr {


    public static void main(String[] args) {

        SuffixArray s = new SuffixArray("tobeornottobe$");

        System.out.println(s.longestCommonPrefix("not"));
        System.out.println(s.longestCommonPrefix("notxxx"));
        System.out.println(s.longestCommonPrefix("tt"));
        System.out.println(s.longestCommonPrefix("ttx"));
        System.out.println(s.longestCommonPrefix("ttobe"));
        System.out.println(s.longestCommonPrefix("ttobex"));
        System.out.println(s.longestCommonPrefix("xxx"));

    }

}
