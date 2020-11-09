/******************************************************************************
 *  Compilation:  javac SequentialSearchST.java
 *  Execution:    java SequentialSearchST
 *  Dependencies: StdIn.java StdOut.java
 *  Data files:   https://algs4.cs.princeton.edu/31elementary/tinyST.txt
 *
 *  Symbol table implementation with sequential search in an
 *  unordered linked list of key-value pairs.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java SequentialSearchST < tinyST.txt
 *  L 11
 *  P 10
 *  M 9
 *  X 7
 *  H 5
 *  C 4
 *  R 3
 *  A 8
 *  E 12
 *  S 0
 *
 ******************************************************************************/
package Searching;

import Other.StdIn;
import Other.StdOut;
import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 17:07 2020/11/9
 */
public class SequentialSearchST<K,V> {

    private Node first;

    private class Node{
        private K key;
        private V val;
        private Node next;

        public Node(K key,V val,Node next){
            this.key = key;
            this.val = val;
            this.next = next;
        }
    }

    public void put(K key,V val){
        if (key == null) throw new IllegalArgumentException("first argument to put() is null");
        for (Node x = first; x != null; x = x.next)
            if(x.key.equals(key)){
                x.val = val;
                return;
            }
        first = new Node(key,val,first);
    }

    public V get(K key){
        if (key == null) throw new IllegalArgumentException("argument to get() is null");
        for (Node x = first; x != null; x = x.next)
            if(x.key.equals(key)){
                return x.val;
            }
        return null;
    }

    public boolean contains(K key){
        if(key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }

    public void delete(K key){
        if(key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node last = first;
        for (Node x = first; x != null; x = x.next){
            if(x.key.equals(key)){
                if(x == first){
                    first = first.next;
                }else{
                    last.next = x.next;
                }
                return;
            }
            last = last.next;
        }
    }

    public Iterable<K> keys(){
        List<K> q = new ArrayList<K>();
        for (Node x = first; x != null; x = x.next)
            q.add(x.key);
        return q;
    }

    public static void main(String[] args) {
        SequentialSearchST<String, Integer> st = new SequentialSearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }
}
