package Strings.RegularExpressions;

import Fundamentals.Stack;
import Graphs.Digraph;

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
}
