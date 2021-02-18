package Searching;

public class ClassicalRBTree<K extends Comparable,V> {

    private Node root;

    private void leftRotation (Node node) {
        if (node == null) {
            return;
        }
        Node parentNode = node.parent;
        if (parentNode == null) {
            return;
        }
        parentNode.right = node.left;
        if (node.left != null) {
            node.left.parent = parentNode;
        }
        node.left = parentNode;
        node.parent = parentNode.parent;
        parentNode.parent = node;
    }

    private void rightRotation(Node node) {
        if (node == null) {
            return;
        }
        Node parentNode = node.parent;
        if (parentNode == null) {
            return;
        }
        parentNode.left = node.right;
        if (node.right != null) {
            node.right.parent = parentNode;
        }
        node.right = parentNode;
        node.parent = parentNode.parent;
        parentNode.parent = node;
    }

    private void changeColor(Node node) {
        node.left.color = Color.BLACK;
        node.right.color = Color.BLACK;
        node.color = Color.RED;
    }

    public void put(K key,V value) {
        insert(key,value);
        root.color = Color.BLACK;
    }

    private Node put(K key,V value,Node node) {
        if (node == null) {
            return createNode(key, value,null);
        }
        int compare = node.key.compareTo(key);
        if (compare < 0) {
            node.right = put(key, value, node.right);
            node.right.parent = node;
            putFixUp(node.right);
        }else if (compare > 0) {
            node.left = put(key, value, node.left);
            node.left.parent = node;
            putFixUp(node.left);
        }else {
            node.value = value;
        }
        return node;
    }

    private Node get(K key,Node node) {
        if (node == null) {
            return null;
        }
        int compare = node.key.compareTo(key);
        if (compare < 0) {
            get(key,node.right);
        }else if (compare > 0) {
            get(key,node.left);
        }else {
            return null;
        }
        return null;
    }

    private void insert(K key, V value) {
        Node y = null;
        Node x = root;
        while (x != null) {
            y = x;
            int compare = x.key.compareTo(key);
            if (compare < 0) {
                x = x.right;
            }else {
                x = x.left;
            }
        }

        if (y != null) {
            int compare = y.key.compareTo(key);
            if (compare < 0) {
                y.right = createNode(key, value, y);
                putFixUp(y.right);
            }else {
                y.left = createNode(key, value, y);
                putFixUp(y.left);
            }
        }else {
            root = createNode(key, value, null);
            root.color = Color.BLACK;
        }
    }

    private void putFixUp(Node node) {
        Node tmp;
        Node parentNode = node.parent;
        if (parentNode == null) {
            node.color = Color.BLACK;
            root = node;
            return;
        }
        Node gParentNode = parentNode.parent;
        if (gParentNode == null) {
            parentNode.color = Color.BLACK;
            root = parentNode;
            return;
        }
        if (parentNode.color == Color.RED) {
            if (parentNode == gParentNode.left) {
                Node uncleNode = gParentNode.right;
                if (uncleNode != null && uncleNode.color == Color.RED) {
                    changeColor(gParentNode);
                    putFixUp(gParentNode);
                }else {
                    if (node == parentNode.right) {
                        leftRotation(node);
                        tmp = node;
                        node = parentNode;
                        parentNode = tmp;
                        if (gParentNode.left == node) {
                            gParentNode.left = parentNode;
                        }else {
                            gParentNode.right = parentNode;
                        }
                    }
                    gParentNode.color = Color.RED;
                    parentNode.color = Color.BLACK;
                    rightRotation(parentNode);
                    tmp = parentNode;
                    parentNode = gParentNode;
                    gParentNode = tmp;
                    if (gParentNode.parent != null) {
                        if (gParentNode.parent.left == parentNode) {
                            gParentNode.parent.left = gParentNode;
                        }else {
                            gParentNode.parent.right = gParentNode;
                        }
                    }
                }
            }else {
                Node uncleNode = gParentNode.left;
                if (uncleNode != null && uncleNode.color == Color.RED) {
                    changeColor(gParentNode);
                    putFixUp(gParentNode);
                }else {
                    if (node == parentNode.left) {
                        rightRotation(node);
                        tmp = node;
                        node = parentNode;
                        parentNode = tmp;
                        if (gParentNode.left == node) {
                            gParentNode.left = parentNode;
                        }else {
                            gParentNode.right = parentNode;
                        }
                    }
                    gParentNode.color = Color.RED;
                    parentNode.color = Color.BLACK;
                    leftRotation(parentNode);
                    tmp = parentNode;
                    parentNode = gParentNode;
                    gParentNode = tmp;
                    if (gParentNode.parent != null) {
                        if (gParentNode.parent.left == parentNode) {
                            gParentNode.parent.left = gParentNode;
                        }else {
                            gParentNode.parent.right = gParentNode;
                        }
                    }
                }
            }
        }

        if (gParentNode.parent == null) {
            gParentNode.color = Color.BLACK;
            root = gParentNode;
        }
    }

    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        preOrder(node.left);
        System.out.print(node.value + " ");
        preOrder(node.right);
    }

    private Node createNode(K key,V value,Node parentNode) {
        Node node = new Node();
        node.key = key;
        node.value = value;
        node.color = Color.RED;
        node.parent = parentNode;
        return node;
    }

    public long size(Node node) {
        return node == null?0L:node.size;
    }

    private class Node {
        public K key;
        public V value;
        public Color color;
        public Node parent;
        public Node left;
        public Node right;
        public long size;
    }

    private enum Color {
        RED("red","红色"),
        BLACK("black","黑色");

        private String code;
        private String title;

        Color(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private static int arr[]= {3,2,1,4,5,6,7,16,15,14,13,12,11,10,8,9};
    public static void main(String[] args) {
        int i;
        ClassicalRBTree<Integer,Integer> tree = new ClassicalRBTree<>();

        System.out.printf("== 依次添加: ");
        for(i=0; i<arr.length; i++) {
            System.out.printf("%d ", arr[i]);
            tree.insert(arr[i],arr[i]);
            System.out.printf("\n== 前序遍历: ");
            tree.preOrder();
        }

        System.out.printf("\n== 前序遍历: ");
        tree.preOrder();
    }
}
