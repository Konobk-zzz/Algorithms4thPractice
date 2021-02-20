package Searching;

import java.util.Scanner;

/**
 * @Author Konobk
 * @Date 2021/1/23 14:19
 * @Version 1.0
 */
public class RedBlackTreeST<K extends Comparable,V> {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String lineStr;
        RedBlackTreeST<String, String> st = new RedBlackTreeST<>();
        while((lineStr = scanner.nextLine()) != null && lineStr.length() != 0){
            String[] split = lineStr.split(",");
            if (split.length == 3 || split.length == 2) {
                if ("p".equals(split[0])) {
                    st.put(split[1],split[2]);
                }else if ("g".equals(split[0])){
                    System.out.println(st.get(split[1]));
                }
            }else {
                System.out.println("不合法的输入!");
            }
        }
    }

    /**
     * 颜色枚举
     */
    enum RedBlackColor {
        RED("red","红色"),
        BLACK("black","黑色");
        private String color;
        private String title;

        RedBlackColor(String color, String title) {
            this.color = color;
            this.title = title;
        }

        public String getColor() {
            return color;
        }
    }

    /**
     * 节点
     */
    class Node {
        public K key;
        public V value;
        public String color;
        public Node left;
        public Node right;
        public long count;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.color = RedBlackColor.RED.getColor();
            this.count = 1;
        }
    }

    private Node root;

    public V get(K key) {
        Node node = get(key, root);
        return node == null?null:node.value;
    }

    private Node get(K key, Node node) {
        if (node == null) {
            return null;
        }
        int compare = node.key.compareTo(key);
        if (compare == 0) {
            return node;
        }else if (compare < 0) {
            return get(key, node.right);
        }else {
            return get(key, node.left);
        }
    }

    public V put(K key, V val) {
        root = put(key,val,root);
        root.color = RedBlackColor.BLACK.getColor();
        return null;
    }

    private Node put(K key, V val, Node node) {
        if (node == null) {
            return new Node(key,val);
        }

        int compare = node.key.compareTo(key);
        if (compare == 0) {
            node.value = val;
        }else if (compare < 0) {
            node.right = put(key,val,node.right);
        }else {
            node.left = put(key,val,node.left);
        }

        if (nodeIsRed(node.right)) {
            node = leftRotate(node);
        }
        if (nodeIsRed(node.left.left) && nodeIsRed(node.left)){
            node = rightRotate(node);
        }
        if (nodeIsRed(node.left) && nodeIsRed(node.right)) {
            node = changeColor(node);
        }

        node.count = size(node.left) + size(node.right) + 1;

        return node;
    }

    public long size(Node node) {
        return node == null?0:node.count;
    }

    private Node changeColor(Node node) {
        node.left.color = RedBlackColor.BLACK.getColor();
        node.right.color = RedBlackColor.BLACK.getColor();
        node.color = RedBlackColor.RED.getColor();
        return node;
    }

    private Node rightRotate(Node node) {
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        x.color = node.color;
        node.color = RedBlackColor.RED.getColor();
        return x;
    }

    private Node leftRotate(Node node) {
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        x.color = node.color;
        node.color = RedBlackColor.RED.getColor();
        return x;
    }

    private boolean nodeIsRed(Node node) {
        if (node == null) {
            return false;
        }
        return RedBlackColor.RED.getColor().equals(node.color);
    }


}
