/******************************************************************************
 *  Compilation:  javac NFA.java
 *  Execution:    java NFA regexp text
 *  Dependencies: Stack.java Bag.java Digraph.java DirectedDFS.java
 *
 *  % java NFA "(A*B|AC)D" AAAABD
 *  true
 *
 *  % java NFA "(A*B|AC)D" AAAAC
 *  false
 *
 *  % java NFA "(a|(bc)*d)*" abcbcd
 *  true
 *
 *  % java NFA "(a|(bc)*d)*" abcbcbcdaaaabcbcdaaaddd
 *  true
 *
 *  Remarks
 *  -----------
 *  The following features are not supported:
 *    - The + operator
 *    - Multiway or
 *    - Metacharacters in the text
 *    - Character classes.
 *
 ******************************************************************************/
package Strings.RegularExpressions;

import Fundamentals.Bag;
import Fundamentals.Stack;
import Graphs.Digraph;
import Graphs.DirectedDFS;
import Other.StdOut;

/**
 * @Author Konobk
 * @Date 2020/11/8 22:09
 * @Version 1.0
 */
public class NFA {

    private char[] re;
    private Digraph G;
    private int M;

    NFA(String regexp){
        Stack<Integer> ops = new Stack<>();
        re = regexp.toCharArray();
        M = re.length;
        G = new Digraph(M + 1);

        for (int i = 0; i < M; i++) {
            int lp = i;
            if(re[i] == '(' || re[i] == '|'){
                ops.push(i);
            }else if(re[i] == ')'){
                int or = ops.pop();
                if (re[or] == '|'){
                    lp = ops.pop();
                    G.addEdge(lp,or + 1);
                    G.addEdge(or,i);
                }else lp = or;
            }
            if(i < M-1 && re[i + 1] == '*'){
                G.addEdge(lp,i + 1);
                G.addEdge(i + 1,lp);
            }
            if(re[i] == '(' || re[i] == '*' || re[i] == ')'){
                G.addEdge(i,i + 1);
            }
        }
    }

    public boolean recognizes(String txt){
        Bag<Integer> pc = new Bag<Integer>();
        DirectedDFS dfs = new DirectedDFS(G, 0);
        for (int v = 0; v < G.V(); v++) {
            if(dfs.marked(v)) pc.add(v);
        }

        for (int i = 0; i < txt.length(); i++) {
            Bag<Integer> match = new Bag<Integer>();
            for (int v:pc) {
                if(v < M)
                    if(re[v] == txt.charAt(i) || re[v] == '.')
                        match.add(v + 1);
            }
            pc = new Bag<Integer>();
            dfs = new DirectedDFS(G, match);
            for (int v = 0; v < G.V(); v++) {
                if(dfs.marked(v)) pc.add(v);
            }
        }

        for (int v:pc) {
            if(v == M)
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        String regexp = "(" + args[0] + ")";
        String txt = args[1];
        NFA nfa = new NFA(regexp);
        StdOut.println(nfa.recognizes(txt));
    }
}
