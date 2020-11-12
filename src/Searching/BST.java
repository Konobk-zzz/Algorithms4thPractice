/******************************************************************************
 *  Compilation:  javac BST.java
 *  Execution:    java BST
 *  Dependencies: StdIn.java StdOut.java Queue.java
 *  Data files:   https://algs4.cs.princeton.edu/32bst/tinyST.txt
 *
 *  A symbol table implemented with a binary search tree.
 *
 *  % more tinyST.txt
 *  S E A R C H E X A M P L E
 *
 *  % java BST < tinyST.txt
 *  A 8
 *  C 4
 *  E 12
 *  H 5
 *  L 11
 *  M 9
 *  P 10
 *  R 3
 *  S 0
 *  X 7
 *
 ******************************************************************************/
package Searching;

import Other.StdIn;
import Other.StdOut;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 10:43 2020/11/11
 */
public class BST<K extends Comparable,V> {

    private class Node{
        private K key;
        private V val;
        private Node left,right;
        private int N;

        Node(K key,V val,int N){
            this.key = key;
            this.val = val;
            this.N = N;
        }
    }

    private Node root;

    public V get(K key){
        return get(root,key);
    }

    private V get(Node x,K key){
        if (x == null)return null;
        int cmp = key.compareTo(x.key);
        if(cmp < 0) return get(x.left,key);
        else if(cmp > 0) return get(x.right,key);
        else return x.val;
    }

    public void put(K key,V val){
        root = put(root,key,val);
    }

    private Node put(Node x,K key,V val){
        if(x == null)return new Node(key,val,1);
        int cmp = key.compareTo(x.key);
        if(cmp < 0) x.left = put(x.left,key,val);
        else if(cmp > 0) x.right = put(x.right,key,val);
        else x.val = val;
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<K> keys(){
        ArrayList<K> list = new ArrayList<>();
        keys(root,list);
        return list;
    }

    private void keys(Node x, List list){
        if(x == null)return;
        keys(x.left,list);
        list.add(x.key);
        keys(x.right,list);
    }

    private void keys(Node x,List list,K lo,K hi){
        if(x == null)return;
        int cmpLo = lo.compareTo(x.key);
        int cmpHi = hi.compareTo(x.key);
        if (cmpLo < 0)keys(x.left,list,lo,hi);
        if (cmpLo <= 0 && cmpHi >= 0) list.add(x.key);
        if (cmpLo > 0)keys(x.right,list,lo,hi);
    }

    public K min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null)return x;
        return min(x.left);
    }

    public K max(){
        return max(root).key;
    }

    private Node max(Node x){
        if(x.right == null)return x;
        return max(x.right);
    }

    public K floor(K key){
        Node node = floor(root, key);
        if (node == null)return null;
        return node.key;
    }

    private Node floor(Node x,K key){
        if (x == null)return null;
        int cmp = key.compareTo(x.key);
        if(cmp == 0)return x;
        if (cmp < 0){
            return floor(x.left,key);
        }
        Node t = floor(x.right,key);
        if (t == null)return x;
        else return t;
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if (x == null)return 0;
        return x.N;
    }

    public K select(int key){
        Node x = select(root, key);
        if (x == null)return null;
        return x.key;
    }

    private Node select(Node x,int key){
        if(x == null) return null;
        int t = size(x.left);
        if(t > key) return select(x.left,key);
        else if(t < key) return select(x.right,key - t - 1);
        else return x;
    }

    public int rank(K key){
        return rank(root,key);
    }

    private int rank(Node x,K key){
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if(cmp < 0)return rank(x.left,key);
        else if(cmp > 0)return 1 + size(x.left) + rank(x.right,key);
        else return size(x.left);
    }

    public void deleteMin(){
        root = deleteMin(root);
    }

    private Node deleteMin(Node x){
        if(x.left == null)return x.right;
        x.left = deleteMin(x.left);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void deleteMax(){
        root = deleteMax(root);
    }

    private Node deleteMax(Node x){
        if(x.right == null)return x.left;
        x.right = deleteMin(x.right);
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public void delete(K key){
        root = delete(root, key);
    }

    private Node delete(Node x,K key){
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0){
            x.left = delete(x.left,key);
        }else if (cmp > 0){
            x.right = delete(x.right,key);
        }else{
            if (x.right == null) return x.left;
            if (x.left == null) return x.right;
            Node t = x;
            x = min(x.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.N = size(x.left) + size(x.right) + 1;
        return x;
    }

    public Iterable<K> levelOrder() {
        List<K> keys = new ArrayList<>();
        List<Node> queue = new ArrayList<Node>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node x = queue.remove(0);
            if (x == null) continue;
            keys.add(x.key);
            queue.add(x.left);
            queue.add(x.right);
        }
        return keys;
    }

    public static void main(String[] args) {
        BST<String, Integer> st = new BST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }

        for (String s : st.levelOrder())
            StdOut.println(s + " " + st.get(s));

        StdOut.println();

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));

        System.out.println("Min:  " + st.min());
        System.out.println("Max:  " + st.max());

        st.deleteMin();
        st.deleteMax();

        st.delete("L");

        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));

        System.out.println(st.rank("e"));
    }

}
