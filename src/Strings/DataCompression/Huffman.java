package Strings.DataCompression;

import Other.BinaryStdIn;
import Other.BinaryStdOut;

import java.util.PriorityQueue;

/**
 * @Author: zja
 * @Description:
 * @Date: Created in 9:58 2020/11/9
 */
public class Huffman {


    private static class Node implements Comparable<Node>{

        private char ch;
        private int freq;
        private final Node left,right;

        Node(char ch,int freq,Node left,Node right){
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf(){
            return this.left == null && this.right == null;
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    private static int R = 256;


    private static Node buildTrie(int[] freq){
        PriorityQueue<Node> pq = new PriorityQueue<>();
        for (char c = 0; c < R; c++) {
            if(freq[c] > 0)
                pq.add(new Node(c,freq[c],null,null));
        }

        while(pq.size() > 1){
            Node x = pq.poll();
            Node y = pq.poll();
            Node parent = new Node('\0', x.freq + y.freq, x, y);
            pq.add(parent);
        }
        return pq.poll();
    }

    private static void writeTrie(Node x){
        if(x.isLeaf()){
            BinaryStdOut.write(true);
            BinaryStdOut.write(x.ch);
            return;
        }
        BinaryStdOut.write(false);
        writeTrie(x.left);
        writeTrie(x.right);
    }

    private static void buildCode(String[] st,Node x,String s){
        if(x.isLeaf()){
            if (st[x.ch] != null && !st[x.ch].isEmpty()) System.out.println(x.ch+":出现覆盖 旧："+ st[x.ch]);
            st[x.ch] = s;
            return;
        }
        buildCode(st,x.left,s + '0');
        buildCode(st,x.right,s + '1');
    }

    public static void compress(){
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        int[] freq = new int[R];
        for (int i = 0; i < input.length; i++) {
            freq[input[i]]++;
        }

        Node root = buildTrie(freq);

        String[] st = new String[R];
        buildCode(st,root,"");

        writeTrie(root);

        BinaryStdOut.write(input.length);

        for (int i = 0; i < input.length; i++) {
            String code = st[input[i]];
            for (int j = 0; j < code.length(); j++) {
                if(code.charAt(j) == '1')
                    BinaryStdOut.write(true);
                else
                    BinaryStdOut.write(false);
            }
        }
        BinaryStdOut.close();
    }

    private static Node readTrie(){
        if(BinaryStdIn.readBoolean())
            return new Node(BinaryStdIn.readChar(),0,null,null);
        return new Node('\0',0,readTrie(),readTrie());
    }

    public static void expand(){
        Node root = readTrie();
        int N = BinaryStdIn.readInt();
        for (int i = 0; i < N; i++) {
            Node x = root;
            while(!x.isLeaf()){
                if(BinaryStdIn.readBoolean())
                    x = x.right;
                else
                    x = x.left;
            }
            BinaryStdOut.write(x.ch);
        }
        BinaryStdOut.close();
    }


    public static void main(String[] args) {
        if      (args[0].equals("-")) compress();
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }



}

