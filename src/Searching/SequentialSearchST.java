package Searching;

import java.util.PriorityQueue;

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
        PriorityQueue<K> q = new PriorityQueue<>();
        for (Node x = first; x != null; x = x.next)
            q.add(x.key);
        return q;
    }
}
