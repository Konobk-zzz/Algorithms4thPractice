package Strings.StringSort;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 14:20 2020/11/5
 */

import Other.StdOut;

/******************************************************************************
 *  Compilation:  javac Alphabet.java
 *  Execution:    java Alphabet
 *  Dependencies: StdOut.java
 *
 *  A data type for alphabets, for use with string-processing code
 *  that must convert between an alphabet of size R and the integers
 *  0 through R-1.
 *
 *  Warning: supports only the basic multilingual plane (BMP), i.e,
 *           Unicode characters between U+0000 and U+FFFF.
 *
 ******************************************************************************/
public class Alphabet {
    /**
     *  The binary alphabet { 0, 1 }.
     */
    public static final Alphabet BINARY = new Alphabet("01");

    /**
     *  The octal alphabet { 0, 1, 2, 3, 4, 5, 6, 7 }.
     */
    public static final Alphabet OCTAL = new Alphabet("01234567");

    /**
     *  The decimal alphabet { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 }.
     */
    public static final Alphabet DECIMAL = new Alphabet("0123456789");

    /**
     *  The hexadecimal alphabet { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, A, B, C, D, E, F }.
     */
    public static final Alphabet HEXADECIMAL = new Alphabet("0123456789ABCDEF");

    /**
     *  The DNA alphabet { A, C, T, G }.
     */
    public static final Alphabet DNA = new Alphabet("ACGT");

    /**
     *  The lowercase alphabet { a, b, c, ..., z }.
     */
    public static final Alphabet LOWERCASE = new Alphabet("abcdefghijklmnopqrstuvwxyz");

    /**
     *  The uppercase alphabet { A, B, C, ..., Z }.
     */

    public static final Alphabet UPPERCASE = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");

    /**
     *  The protein alphabet { A, C, D, E, F, G, H, I, K, L, M, N, P, Q, R, S, T, V, W, Y }.
     */
    public static final Alphabet PROTEIN = new Alphabet("ACDEFGHIKLMNPQRSTVWY");

    /**
     *  The base-64 alphabet (64 characters).
     */
    public static final Alphabet BASE64 = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/");

    /**
     *  The ASCII alphabet (0-127).
     */
    public static final Alphabet ASCII = new Alphabet(128);

    /**
     *  The extended ASCII alphabet (0-255).
     */
    public static final Alphabet EXTENDED_ASCII = new Alphabet(256);

    /**
     *  The Unicode 16 alphabet (0-65,535).
     */
    public static final Alphabet UNICODE16      = new Alphabet(65536);


    //字符表
    private char[] alphabet;
    //反查表
    private int[] inverse;
    //字符表的进制数
    private int R;

    public Alphabet(String alpha){
        boolean[] unicode = new boolean[Character.MAX_VALUE];
        for (int i = 0; i < alpha.length(); i++) {
            char c = alpha.charAt(i);
            if (unicode[c])
                throw new IllegalArgumentException("Illegal alphabet: repeated character = '\" + c + \"'");
            unicode[c] = true;
        }

        alphabet = alpha.toCharArray();
        R = alpha.length();
        inverse = new int[Character.MAX_VALUE];
        for (int i = 0; i < inverse.length; i++) {
            inverse[i] = -1;
        }

        for (int c = 0; c < R; c++) {
            inverse[alphabet[c]] = c;
        }
    }

    public Alphabet(int r){
        R=r;
        alphabet = new char[r];
        inverse = new int[r];

        for (int i = 0; i < r; i++) {
            alphabet[i] = (char) i;
            inverse[i] = i;
        }
    }

    public Alphabet(){
        this(256);
    }

    public boolean contains(char c){
        return inverse[c] != -1;
    }

    public int R(){
        return R;
    }

    public int radix(){
        return R;
    }

    public int lgR(){
        int lgR=0;
        for (int t = R-1; t >= 1; t /= 2)lgR++;
        return lgR;
    }

    public int toIndex(char c){
        if(c >= inverse.length || inverse[c] == -1)
            throw new IllegalArgumentException("Character " + c + " not in alphabet");
        return inverse[c];
    }

    public int[] toIndices(String s){
        char[] chars = s.toCharArray();
        int[] target = new int[chars.length];
        for (int i = 0; i < chars.length; i++)
            target[i] = toIndex(chars[i]);
        return target;
    }

    public char toChar(int index){
        if (index < 0 || index >= R)
            throw new IllegalArgumentException("index must be between 0 and " + R + ": " + index);
        return alphabet[index];
    }

    public String toChars(int[] indices){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < indices.length; i++)
            buffer.append(toChar(indices[i]));
        return buffer.toString();
    }

    /**
     * Unit tests the {@code Alphabet} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int[]  encoded1 = Alphabet.BASE64.toIndices("NowIsTheTimeForAllGoodMen");
        String decoded1 = Alphabet.BASE64.toChars(encoded1);
        StdOut.println(decoded1);

        int[]  encoded2 = Alphabet.DNA.toIndices("AACGAACGGTTTACCCCG");
        String decoded2 = Alphabet.DNA.toChars(encoded2);
        StdOut.println(decoded2);

        int[]  encoded3 = Alphabet.DECIMAL.toIndices("01234567890123456789");
        String decoded3 = Alphabet.DECIMAL.toChars(encoded3);
        StdOut.println(decoded3);
    }
}
