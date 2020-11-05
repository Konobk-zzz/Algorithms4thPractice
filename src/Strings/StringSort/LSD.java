package Strings.StringSort;

import Other.StdIn;
import Other.StdOut;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 17:12 2020/11/5
 */
/******************************************************************************
 *  Compilation:  javac LSD.java
 *  Execution:    java LSD < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/51radix/words3.txt
 *
 *  LSD radix sort
 *
 *    - Sort a String[] array of n extended ASCII strings (R = 256), each of length w.
 *
 *    - Sort an int[] array of n 32-bit integers, treating each integer as
 *      a sequence of w = 4 bytes (R = 256).
 *
 *  Uses extra space proportional to n + R.
 *
 *
 *  % java LSD < words3.txt
 *  all
 *  bad
 *  bed
 *  bug
 *  dad
 *  ...
 *  yes
 *  yet
 *  zoo
 *
 ******************************************************************************/
public class LSD {

    public static void sort(String[] a,int W){
        int N = a.length;
        int R = 256;
        String[] aux = new String[N];

        for (int w = W-1; w >= 0; w--) {
            int[] count = new int[R+1];
            //统计频率
            for (int i = 0; i < N; i++) {
                count[a[i].charAt(w)+1]++;
            }
            //将频率抓换为索引
            for (int i = 0; i < R; i++) {
                count[i+1] += count[i];
            }
            //将元素分类
            for (int i = 0; i < N; i++) {
                aux[count[a[i].charAt(w)]++] = a[i];
            }
            //回写
            for (int i = 0; i < N; i++) {
                a[i] = aux[i];
            }
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        // check that strings have fixed length
        int w = a[0].length();
        for (int i = 0; i < n; i++)
            assert a[i].length() == w : "Strings must have fixed length";

        // sort the strings
        sort(a, w);

        // print results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
