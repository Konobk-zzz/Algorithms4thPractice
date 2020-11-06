/******************************************************************************
 *  Compilation: javac MSD.java
 *  Execution:   java MSD < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/51radix/words3.txt
 *                https://algs4.cs.princeton.edu/51radix/shells.txt
 *
 *  Sort an array of strings or integers using MSD radix sort.
 *
 *  % java MSD < shells.txt
 *  are
 *  by
 *  sea
 *  seashells
 *  seashells
 *  sells
 *  sells
 *  she
 *  she
 *  shells
 *  shore
 *  surely
 *  the
 *  the
 *
 ******************************************************************************/
package Strings.StringSort;

import Other.StdIn;
import Other.StdOut;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 8:49 2020/11/6
 */
public class MSD {


    private static int R = 256;
    private static final int M = 15;
    private static String[] aux;

    private static int charAt(String s,int d){
        if(d < s.length())
            return s.charAt(d);
        else
            return -1;
    }

    public static void sort(String[] a){
        int N = a.length;
        aux = new String[N];
        sort(a,0,N-1,0);
    }

    private static void sort(String[] a,int lo,int hi,int d){
        if(hi - lo <= M){
            InsertSort(a,lo,hi,d);
            return;
        }

        int[] count = new int[R+2];

        for (int i = lo; i <= hi; i++) {
            count[charAt(a[i],d)+2]++;
        }

        for (int r = 0; r < R+1; r++) {
            count[r+1] += count[r];
        }

        for (int i = lo; i <= hi; i++) {
            aux[count[charAt(a[i],d)+1]++] = a[i];
        }

        for (int i = lo; i <= hi; i++) {
            a[i] = aux[i-lo];
        }

        for (int r = 0; r < R; r++) {
            sort(a,lo + count[r],lo + count[r+1] - 1,d+1);
        }
    }

    private static void InsertSort(String[] a,int lo,int hi,int d){
        for (int i = lo; i <= hi; i++) {
            for (int j = i; j > lo && a[j].substring(d).compareTo(a[j-1].substring(d)) < 0; j--) {
                String t = a[j];
                a[j] = a[j-1];
                a[j-1] = t;
            }
        }
    }

    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        int n = a.length;
        sort(a);
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
