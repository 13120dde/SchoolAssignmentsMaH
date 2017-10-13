package testSuffArr;

import java.util.ArrayList;
import java.util.Scanner;

class Pair<A, B> {
    public A A;
    public B B;

    public Pair(A a, B b) {
        A = a;
        B = b;
    }
}

class SuffixArray {
    private String input;
    private ArrayList<Integer> order;

    private SuffixArray() {}

    public static SuffixArray build(String input) {
        input += "$";

        SuffixArray array = new SuffixArray();
        ArrayList<Integer> order = new ArrayList<>();

        for (int i = 0; i < input.length(); i++) {
            boolean added = false;
            String suffix = input.substring(i);

            for (int j = 0; j <= i && !added; j++) {
                if (j == i || suffix.compareTo(input.substring(order.get(j))) <= 0) {
                    order.add(j, i);
                    added = true;
                }
            }
        }

        for (Integer i : order)
            System.out.print(i + " ");
        System.out.println();

        array.input = input;
        array.order = order;
        return array;
    }

    public int longestPrefix(String pattern) {
        Pair<Integer, Integer> longest = new Pair<>(0, 0);

        int left = 0, right = order.size() - 1;

        while (left <= right) {
            int middle = left + (right - left) / 2;
            int result = compare(pattern, input.substring(order.get(middle)));

            if (result == 0)
                return order.get(middle);

            int abs = Math.abs(result) - 1;
            if (abs > longest.B)
                longest = new Pair<>(order.get(middle), abs);

            if (result < 0)
                right = middle - 1;
            else
                left = middle + 1;
        }

        if (longest.B > 0)
            return longest.A;

        if (pattern.length() > 1)
            return longestPrefix(pattern.substring(1));

        return -1;
    }

    private int compare(String s1, String s2) {
        int matches = 0;
        int limit = Math.min(s1.length(), s2.length());

        for (int i = 0; i < limit; i++) {
            int comp = s1.charAt(i) - s2.charAt(i);
            if (comp == 0) {
                matches++;
            } else {
                matches++;
                if (comp < 0)
                    return -matches;
                else
                    return matches;
            }
        }

        return 0;
    }

    public void print() {
        for (Integer i : order)
            System.out.println(String.format("%5d: %s", i, input.substring(i)));
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the string to build the suffix array from: ");
        SuffixArray arr = SuffixArray.build(scanner.nextLine());
        System.out.print("Enter the prefix to search for: ");
        System.out.println("Index: " + arr.longestPrefix(scanner.nextLine()));
    }
}
