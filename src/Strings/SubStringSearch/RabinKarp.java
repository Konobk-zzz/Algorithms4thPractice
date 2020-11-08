package Strings.SubStringSearch;

import Other.StdOut;

/**
 * @Author Konobk
 * @Date 2020/11/8 16:58
 * @Version 1.0
 */
public class RabinKarp {

    private String pat;
    private long patHash;
    private int M;
    private long Q;
    private int R = 256;
    private long RM;

    RabinKarp(String pat){
        this.pat = pat;
        this.M = pat.length();
        this.Q = 997;
        RM = 1;
        for (int i = 1; i <= M-1; i++) {
            RM = (R * RM) % this.Q;
        }
        patHash = hash(pat,M);
    }

    private long hash(String key,int M){
        long h = 0;
        for (int i = 0; i < M; i++) {
            h = (R * h + key.charAt(i)) % Q;
        }
        return h;
    }

    private boolean check(int i){
        return true;
    }

    public int search(String txt){
        int N = txt.length();
        long txtHash = hash(txt,M);
        if(txtHash == patHash && check(0))return 0;
        for (int i = M; i < N; i++) {
            txtHash = (txtHash + Q - (RM * txt.charAt(i - M)) % Q);
            txtHash = (txtHash * R + txt.charAt(i)) % Q;
            if (patHash == txtHash && check(i - M +1))
                return i - M + 1;
        }
        return N;
    }

    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        KMP kmp = new KMP(pat);
        StdOut.println("text:     " + txt);
        int search = kmp.search(txt);
        StdOut.print("pattern:  ");
        for (int i = 0; i < search; i++) {
            StdOut.print(" ");
        }
        StdOut.println(pat);
    }

}
