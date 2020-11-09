/******************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *  Data files:   https://algs4.cs.princeton.edu/55compression/abraLZW.txt
 *                https://algs4.cs.princeton.edu/55compression/ababLZW.txt
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 ******************************************************************************/
package Strings.DataCompression;

import Other.BinaryStdIn;
import Other.BinaryStdOut;
import Strings.WordsTree.TST;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 15:06 2020/11/9
 */
public class LZW {

    private static final int R = 256;
    private static final int L = 4096;
    private static final int W = 12;

    public static void compress(){
        String input = BinaryStdIn.readString();
        TST<Integer> tst = new TST<>();
        for (int i = 0; i < R; i++) {
            tst.put("" + (char)i,i);
        }

        int code = R + 1;
        while(input.length() > 0){
            String s = tst.longestPrefixOf(input);
            BinaryStdOut.write(tst.get(s),W);

            int t = s.length();
            if(t < input.length() && code < L){
                tst.put(input.substring(0,t + 1),code++);
            }
            input = input.substring(t);
        }
        BinaryStdOut.write(R,W);
        BinaryStdOut.close();
    }

    public static void expand(){
        String[] st = new String[L];
        int i;
        for (i = 0; i < R; i++) {
            st[i] = "" + (char) i;
        }
        st[i++] = " ";

        int codeword = BinaryStdIn.readInt(W);
        String val = st[codeword];
        while (true){
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if(codeword == R)break;
            String s = st[codeword];
            if(i == codeword)
                s = val + val.charAt(0);
            if(i < L)
                st[i++] = val + s.charAt(0);
            val = s;
        }
        BinaryStdOut.close();
    }

    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }
}
