package Searching;

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
        x.N = x.left.N + x.right.N +1;
        return x;
    }

    public Iterable<K> keys(){
        ArrayList<K> list = new ArrayList<>();
        keys(root,list);
        return list;
    }

    public void keys(Node x, List list){
        if(x == null)return;
        keys(x.left,list);
        list.add(x.val);
        keys(x.right,list);
    }

    public K min(){
        return min(root).key;
    }

    private Node min(Node x){
        if(x.left == null)return x;
        return min(x.left);
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



}
