package Strings.WordsTree;

import Other.StdIn;
import Other.StdOut;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @Author zhujiaao
 * @Date 2020/11/7 14:17
 * @Version 1.0
 */
public class TST<T> {

    private class Node{
        private char c;
        private T val;
        private Node left,mid,right;
    }

    private Node root;

    public T get(String key){
        Node x = get(root, key, 0);
        if (x != null)return (T) x.val;
        return null;
    }

    private Node get(Node x,String key,int d){
        if(x == null)return null;

        if(key.length() == 0)throw new IllegalArgumentException("key can't be empty！");

        char c = key.charAt(d);
        if(c < x.c){
            return get(x.left,key,d);
        }else if(c > x.c){
            return get(x.right,key,d);
        }else if(d < key.length()-1){
            return get(x.mid,key,d+1);
        }else{
            return x;
        }
    }

    public void put(String key,T val){
        root = put(root,key,val,0);
    }

    private Node put(Node x,String key,T val,int d){
        char c = key.charAt(d);
        if(x == null){
            x = new Node();
            x.c = c;
        }

        if(c < x.c) {
            x.left = put(x.left,key,val,d);
        }else if(c > x.c){
            x.right = put(x.right,key,val,d);
        }else if(d < key.length()-1){
            x.mid = put(x.mid,key,val,d + 1);
        }else{
            x.val = val;
        }
        return x;
    }

    public int size(){
        return size(root);
    }

    private int size(Node x){
        if(x == null)return 0;

        int cnt = 0;
        if(x.val != null)cnt++;
        cnt += size(x.left);
        cnt += size(x.mid);
        cnt += size(x.right);

        return cnt;
    }

    public Iterable<String> keys(){
        Queue q = new PriorityQueue();
        collect(root,new StringBuffer(),q);
        return q;
    }

    public Iterable<String> keysWithPrefix(String pre) {
        Queue q = new PriorityQueue();
        collect(get(root,pre,0),new StringBuffer(pre),q);
        return q;
    }

    private void collect(Node x, StringBuffer pre, Queue q){
        if(x == null)return;

        collect(x.left,pre,q);
        collect(x.mid,pre.append(x.c),q);
        if(x.val != null)q.add(pre.toString());
        pre.deleteCharAt(pre.length() - 1);
        collect(x.right,pre,q);
    }

    public String longestPrefixOf(String pre){
        int search = search(root, pre, 0, 0);
        return pre.substring(0,search);
    }

    private int search(Node x,String pre,int d,int length){
        if(x == null)return length;
        if(d == pre.length())return length;

        if(pre.charAt(d) < x.c){
            return search(x.left,pre,d,length);
        }else if(pre.charAt(d) > x.c){
            return search(x.right,pre,d,length);
        }else{
            if(x.val != null)length = d + 1;
            return search(x.mid,pre,d + 1,length);
        }
    }

    public static void main(String[] args) {

        // build symbol table from standard input
        TST<Integer> st = new TST<Integer>();
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

    }
}
