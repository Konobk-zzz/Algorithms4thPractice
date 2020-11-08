/******************************************************************************
 *  Compilation:  javac TrieST.java
 *  Execution:    java TrieST < words.txt
 *  Dependencies: StdIn.java
 *  Data files:   https://algs4.cs.princeton.edu/52trie/shellsST.txt
 *
 *  A string symbol table for extended ASCII strings, implemented
 *  using a 256-way trie.
 *
 *  % java TrieST < shellsST.txt
 *  by 4
 *  sea 6
 *  sells 1
 *  she 0
 *  shells 3
 *  shore 7
 *  the 5
 *
 ******************************************************************************/
package Strings.WordsTree;

import Other.StdIn;
import Other.StdOut;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 15:48 2020/11/6
 */
public class TrieST<T> {

    private static final int R = 256;

    private Node root;

    private static class Node{
        private Object val;
        private Node[] next = new Node[R];
    }

    public T get(String key){
        Node node = get(root, key, 0);
        if(node == null)return null;
        return (T) node.val;
    }

    private Node get(Node x,String key,int d){
        if(x == null)return null;
        if(d == key.length())return x;
        char c = key.charAt(d);
        return get(x.next[c],key,d + 1);
    }

    public void put(String key,T val){
        root = put(root,key,val,0);
    }

    private Node put(Node x,String key,T val,int d){
        if(x == null)x = new Node();
        if(d == key.length()){
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        x.next[c] = put(x.next[c],key,val,d+1);
        return x;
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x == null)return 0;
        int cnt = 0;
        if(x.val != null)cnt++;
        for (int i = 0; i < R; i++) {
            cnt += size(x.next[i]);
        }
        return cnt;
    }

    public Iterable<String> keys(){
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String pre){
        Queue<String> q = new PriorityQueue<String>();
        collect(get(root,pre,0),new StringBuffer(pre),q);
        return q;
    }

    private void collect(Node x, StringBuffer pre, Queue<String> q){
        if(x == null)return;
        if(x.val != null) q.add(pre.toString());
        for (int c = 0; c < R; c++) {
            collect(x.next[c],pre.append((char) c),q);
            pre.deleteCharAt(pre.length() - 1);
        }
    }

    public Iterable<String> keysThatMatch(String pat){
        Queue<String> q = new PriorityQueue<>();
        collect(root,new StringBuffer(),pat,q);
        return q;
    }

    private void collect(Node x,StringBuffer pre,String pat,Queue<String> q){
        if (x == null)return;
        int d = pre.length();
        if (d == pat.length() && x.val != null) q.add(pre.toString());
        if (d == pat.length())return;

        char next = pat.charAt(d);
        for (char c = 0; c < R; c++) {
            if(next == '.' || next == c){
                collect(x.next[c],pre.append(c),pat,q);
                pre.deleteCharAt(pre.length() - 1);
            }
        }
    }

    public String longestPrefixOf(String pre){
        int search = search(root, pre, 0, 0);
        return pre.substring(0,search);
    }

    private int search(Node x,String pre,int d,int length){
        if(x == null)return length;
        if(x.val != null)length = d;
        if(d == pre.length())return length;
        char c = pre.charAt(d);
        return search(x.next[c],pre,d+1,length);
    }

    public void delete(String key){

    }

    private Node delete(Node x,String key,int d){
        if(x == null)return null;
        if(key.length() == d){
            x.val = null;
        }else{
            char c = key.charAt(d);
            x.next[c] = delete(x.next[c],key,d+1);
        }

        if(x.val != null) return x;

        for (int c = 0; c < R; c++) {
            if(x.next[c] != null) return x;
        }
        return null;
    }



    public static void main(String[] args) {

        // build symbol table from standard input
        TrieST<Integer> st = new TrieST<Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        // print results
        if (st.size() < 100) {
            StdOut.println("keys(\"\"):");
            for (String key : st.keys()) {
                StdOut.println(key + " " + st.get(key));
            }
            StdOut.println();
        }

        StdOut.println("longestPrefixOf(\"shellsort\"):");
        StdOut.println(st.longestPrefixOf("shellsort"));
        StdOut.println();

        StdOut.println("longestPrefixOf(\"quicksort\"):");
        StdOut.println(st.longestPrefixOf("quicksort"));
        StdOut.println();

        StdOut.println("keysWithPrefix(\"shor\"):");
        for (String s : st.keysWithPrefix("shor"))
            StdOut.println(s);
        StdOut.println();

        StdOut.println("keysThatMatch(\".he.l.\"):");
        for (String s : st.keysThatMatch(".he.l."))
            StdOut.println(s);
    }
}
