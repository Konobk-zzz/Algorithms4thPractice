package Searching;

import java.util.Scanner;

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
        if (parentNode.parent == null) {
            root = node;
        }
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
        if (parentNode.parent == null) {
            root = node;
        }
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
            return get(key,node.right);
        }else if (compare > 0) {
            return get(key,node.left);
        }else {
            return node;
        }
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

    private Node min(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    private Node max(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    private void swapNodeKeyValue(Node node1,Node node2) {
        K tKey = node1.key;
        V tValue = node1.value;
        node1.key = node2.key;
        node1.value = node2.value;
        node2.key = tKey;
        node2.value = tValue;
    }

    private void remove(K key) {
        Node targetNode = get(key, root);
        if (targetNode == null) {
            return;
        }
        if (targetNode.left != null && targetNode.right != null) {
            Node replaceNode = min(targetNode.right);
            if (replaceNode == null) {
                replaceNode = max(targetNode.left);
            }
            swapNodeKeyValue(targetNode,replaceNode);
            targetNode = replaceNode;
        }

        if (targetNode.left != null || targetNode.right != null) {
            removeOnlyOneChild(targetNode);
        }else {
            removeLeafNode(targetNode,true);
        }
    }

    private void removeOnlyOneChild(Node node) {
        if (node.left != null) {
            swapNodeKeyValue(node,node.left);
            node.left = null;
        }else {
            swapNodeKeyValue(node,node.right);
            node.right = null;
        }
    }

    private void removeLeafNode(Node node, boolean isDelete) {
        Node parentNode = node.parent;
        Node brotherNode;
        Node remoteNephewNode;
        Node nearNephewNode;
        boolean nodeIsLeft;

        /** 删除为根节点时 */
        if (parentNode == null) {
            if (isDelete) {
                root = node;
            }
            return;
        }

        if (node == parentNode.left) {
            brotherNode = parentNode.right;
            remoteNephewNode = brotherNode == null ? null:brotherNode.right;
            nearNephewNode = brotherNode == null ? null:brotherNode.left;
            nodeIsLeft = true;
        }else {
            brotherNode = parentNode.left;
            remoteNephewNode = brotherNode == null ? null:brotherNode.left;
            nearNephewNode = brotherNode == null ? null:brotherNode.right;
            nodeIsLeft = false;
        }

        /** 如果是红节点直接删除 */
        if (node.color == Color.RED) {
            if (isDelete) {
                removeNode(nodeIsLeft,parentNode);
            }
            return;
        }

        /** case1 parentNode Is RED */
        if (parentNode.color == Color.RED) {
            parentNode.color = Color.BLACK;
            brotherNode.color = Color.RED;
            if (isDelete) {
                removeNode(nodeIsLeft,parentNode);
            }
            return;
        }
        /** case2 brotherNode Is RED */
        if (parentNode.color == Color.BLACK  && brotherNode.color == Color.RED) {
            parentNode.color = Color.RED;
            brotherNode.color = Color.BLACK;
            rotationNode(nodeIsLeft, brotherNode);
            removeLeafNode(node,isDelete);
            return;
        }

        /** case5 node、parentNode、brotherNode All BLACK */
        if (node != root && node.color == Color.BLACK && parentNode.color == Color.BLACK && brotherNode.color == Color.BLACK) {
            if (isDelete){
                removeNode(nodeIsLeft,parentNode);
            }
            brotherNode.color = Color.RED;
            removeLeafNode(parentNode,false);
            return;
        }

        /** case3 remoteNephewNode Is RED */
        if (remoteNephewNode.color == Color.RED) {
            swapNodeColor(brotherNode, remoteNephewNode);
            remoteNephewNode.color = Color.BLACK;
            rotationNode(nodeIsLeft, brotherNode);
            if (isDelete) {
                removeNode(nodeIsLeft, parentNode);
            }
            return;
        }

        /** case4 nearNephewNode Is RED */
        if (nearNephewNode.color == Color.RED) {
            brotherNode.color = Color.RED;
            nearNephewNode.color = Color.BLACK;
            // TODO 表意不明确
            rotationNode(!nodeIsLeft,nearNephewNode);
            removeLeafNode(node,isDelete);
        }


    }

    private void removeNode(boolean nodeIsLeft, Node parentNode) {
        if (nodeIsLeft) {
            parentNode.left = null;
        }else {
            parentNode.right = null;
        }
    }

    private void removeNode(Node node) {
        Node parentNode = node.parent;
        if (parentNode == null) {
            root = null;
        }else if (node == parentNode.left) {
            parentNode.left = null;
        }else {
            parentNode.right = null;
        }
    }

    private void rotationNode(boolean nodeIsLeft, Node node) {
        if (nodeIsLeft) {
            leftRotation(node);
        }else {
            rightRotation(node);
        }
    }

    private void swapNodeColor(Node node1,Node node2) {
        Color tColor = node1.color;
        node1.color = node2.color;
        node2.color = tColor;
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
        }

        System.out.printf("\n== 前序遍历: ");
        tree.preOrder();

        System.out.println("== 请输入要删除的key: ");
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNextLine()) {
            int key = scanner.nextInt();
            System.out.printf("\n== 删除节点: %d", key);
            tree.remove(key);
            System.out.printf("\n== 前序遍历: ");
            tree.preOrder();
        }
    }
}
