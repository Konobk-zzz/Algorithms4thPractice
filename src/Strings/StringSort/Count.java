/******************************************************************************
 *  Compilation:  javac Count.java
 *  Execution:    java Count alpha < input.txt
 *  Dependencies: Alphabet.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/50strings/abra.txt
 *                https://algs4.cs.princeton.edu/50strings/pi.txt
 *
 *  Create an alphabet specified on the command line, read in a
 *  sequence of characters over that alphabet (ignoring characters
 *  not in the alphabet), computes the frequency of occurrence of
 *  each character, and print out the results.
 *
 *  %  java Count ABCDR < abra.txt
 *  A 5
 *  B 2
 *  C 1
 *  D 1
 *  R 2
 *
 *  % java Count 0123456789 < pi.txt
 *  0 99959
 *  1 99757
 *  2 100026
 *  3 100230
 *  4 100230
 *  5 100359
 *  6 99548
 *  7 99800
 *  8 99985
 *  9 100106
 *
 ******************************************************************************/
package Strings.StringSort;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 15:00 2020/11/5
 */

import Other.StdIn;
import Other.StdOut;

public class Count {

    private Count(){}

    public static void main(String[] args) {
        Alphabet alphabet = new Alphabet(args[0]);
        final int R = alphabet.R();
        int[] count = new int[R];
        while(StdIn.hasNextChar()){
            char c = StdIn.readChar();
            if(alphabet.contains(c)){
                count[alphabet.toIndex(c)]++;
            }
        }
        for(int c = 0; c < R;c++){
            StdOut.println(alphabet.toChar(c) + " " + count[c]);
        }
    }
}
