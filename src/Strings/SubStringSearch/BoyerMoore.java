/******************************************************************************
 *  Compilation:  javac BoyerMoore.java
 *  Execution:    java BoyerMoore pattern text
 *  Dependencies: StdOut.java
 *
 *  Reads in two strings, the pattern and the input text, and
 *  searches for the pattern in the input text using the
 *  bad-character rule part of the Boyer-Moore algorithm.
 *  (does not implement the strong good suffix rule)
 *
 *  % java BoyerMoore abracadabra abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:               abracadabra
 *
 *  % java BoyerMoore rab abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:         rab
 *
 *  % java BoyerMoore bcara abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                                   bcara
 *
 *  % java BoyerMoore rabrabracad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern:                        rabrabracad
 *
 *  % java BoyerMoore abacad abacadabrabracabracadabrabrabracad
 *  text:    abacadabrabracabracadabrabrabracad
 *  pattern: abacad
 *
 ******************************************************************************/
package Strings.SubStringSearch;

import Other.StdOut;

/**
 * @Author zhujiaao
 * @Date 2020/11/8 14:42
 * @Version 1.0
 */
public class BoyerMoore {

    private String pat;

    private int[] right;

    private int R = 256;

    BoyerMoore(String pat){
        this.pat = pat;
        int N = pat.length();
        right = new int[R];
        for (int i = 0; i < R; i++)
            right[i] = -1;
        for (int i = 0; i < N; i++)
            right[pat.charAt(i)] = i;
    }

    public int search(String txt){
        int N = txt.length();
        int M = pat.length();

        int skip;
        for (int i = 0; i < N - M; i += skip) {
            skip = 0;
            for(int j = M - 1; j >= 0; j--){
                if(txt.charAt(i + j) != pat.charAt(j)){
                    skip = j - right[i + j];
                    if (skip < 1) skip = 1;
                    break;
                }
            }
            if (skip == 0)return i;
        }
        return N;
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        KMP kmp = new KMP(pat);
        StdOut.println("text:     " + txt);
        int search = kmp.search(txt);
        StdOut.print("pattern:  ");
        for (int i = 0; i < search; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }
}
