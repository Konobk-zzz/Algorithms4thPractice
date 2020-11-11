package Searching;

import Other.StdIn;
import Other.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 8:56 2020/11/11
 */
public class BinarySearchST<Key extends Comparable<Key>,Value> {
    private Key[] keys;
    private Value[] values;
    private int N;
    private static final int INIT_CAPACITY = 10;

    public BinarySearchST(int capacity){
        keys = (Key[]) new Comparable[capacity];
        values = (Value[])  new Object[capacity];
    }

    public BinarySearchST() {
        this(INIT_CAPACITY);
    }

    public Value get(Key key){
        if(isEmpty())return null;
        int rank = rank(key);
        if (rank < N && keys[rank].compareTo(key) == 0)
            return values[rank];
        return null;
    }

    public int rank(Key key){
        int lo = 0,hi = N - 1;
        while(lo <= hi){
            int mid = lo + (hi - lo) / 2;
            int compare = keys[mid].compareTo(key);
            if (compare > 0){
                hi = mid - 1;
            }else if(compare < 0){
                lo = mid + 1;
            }else{
                return mid;
            }
        }
        return lo;
    }

    public void put(Key key,Value value){
        int rank = rank(key);
        if (rank < N && keys[rank].compareTo(key) == 0){
            values[rank] = value;
        }
        if( rank == N)resize(keys.length * 2);
        for (int i = N; i > rank; i--) {
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }
        keys[rank] = key;
        values[rank] = value;
        N++;
    }

    private void resize(int capacity){
        keys = Arrays.copyOf(keys,capacity);
        values = Arrays.copyOf(values,capacity);
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public Iterable<Key> keys(){
        ArrayList<Key> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            list.add(keys[i]);
        }
        return list;
    }

    public static void main(String[] args) {
        BinarySearchST<String, Integer> st = new BinarySearchST<String, Integer>();
        for (int i = 0; !StdIn.isEmpty(); i++) {
            String key = StdIn.readString();
            st.put(key, i);
        }
        for (String s : st.keys())
            StdOut.println(s + " " + st.get(s));
    }

}
