/******************************************************************************
 *  Compilation:  javac Quick3string.java
 *  Execution:    java Quick3string < input.txt
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/51radix/words3.txt
 *                https://algs4.cs.princeton.edu/51radix/shells.txt
 *
 *  Reads string from standard input and 3-way string quicksort them.
 *
 *  % java Quick3string < shell.txt
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
 *
 ******************************************************************************/
package Strings.StringSort;

import Other.StdIn;
import Other.StdOut;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 10:26 2020/11/6
 */
public class Quick3String {

    private static int charAt(String s,int d){
        if(d < s.length())
            return s.charAt(d);
        else
            return -1;
    }

    private static void sort(String[] a){
        sort(a,0,a.length-1,0);
    }

    private static void sort(String[] a,int lo,int hi,int d){
        if(hi <= lo)return;
        int lt = lo;
        int gt = hi;
        int v = charAt(a[lo],d);
        int i = lo + 1;
        while(i <= gt){
            int ct = charAt(a[i], d);
            if(ct < v){
                exch(a,lt++,i++);
            }else if(ct > v){
                exch(a,i,gt--);
            }else{
                i++;
            }
        }
        sort(a,lo,lt-1,d);
        if(v >= 0)sort(a,lt,gt,d+1);
        sort(a,gt+1,hi,d);
    }

    private static void exch(String[] a,int x,int y){
        String t = a[x];
        a[x] = a[y];
        a[y] = t;
    }

    public static void main(String[] args) {

        // read in the strings from standard input
        String[] a = StdIn.readAllStrings();
        int n = a.length;

        // sort the strings
        sort(a);

        // print the results
        for (int i = 0; i < n; i++)
            StdOut.println(a[i]);
    }
}
