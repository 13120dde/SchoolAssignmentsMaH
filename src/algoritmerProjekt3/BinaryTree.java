package algoritmerProjekt3;

/**
 * Created by Patrik Lind on 2016-10-01.
 */
public class BinaryTree {

    private TreeNode rootNode;


    private int size = 0;

    /**
     * Returns the the integer value of number of elements stored in this tree.
     *
     * @return size : int
     */
    public int getSize() {
        return size;
    }

    /**
     * This method is used to visualise the treeNode in console by calling on .printTree() on the returned object.
     *
     * @return : rootNode : TreeNode
     */
    public TreeNode getRoot() {
        return rootNode;
    }

    /**
     * Attempt to add the integer passed in as argument to the tree. If the tree already holds a same value as the data
     * passed in, the new value wont be stored.
     *
     * @param data : int
     */
    public void add(int data) {

        if (rootNode == null) {
            rootNode = new TreeNode(data);
            size++;
        } else {

            try {
                insert(rootNode, new TreeNode(data));
                size++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /*This method looks where the newNode should be inserted in current TreeNode by traversing current untill a leaf is
    found. The method evaluates both object's data values to determine where in the current TreeNode the newNode object
    should fit.

    Throws Exception if the TreeNode already holds the data the newNode tries to add.
     */
    private TreeNode insert(TreeNode current, TreeNode newNode) throws Exception {

        TreeNode parent;

        while (true) {

            parent = current;

            if (newNode.getData() < current.getData()) {
                current = current.getLeftChild();
                if (current == null) {
                    parent.setLeftChild(newNode);
                    return newNode;
                }
            } else if (newNode.getData() > current.getData()) {
                current = current.getRightChild();
                if (current == null) {
                    parent.setRightChild(newNode);
                    return newNode;
                }
            } else {
                throw new Exception("Unable to add: " + current.getData() + ". The value is already stored in the tree.");
            }

        }

    }

    /**
     * Attempts to remove the data passed in as argument if its found in the tree. If the tree doesn't hold the value
     * nothing is removed.
     *
     * @param data : int
     */
    public void delete(int data) {
        rootNode = remove(rootNode, data);
    }

    /**
     * Attempt to find data in the TreeNode. This method takes the TreeNode to search as agrument aswell as the integer
     * value of data to find. If data was not found in the TreeNoede NULL is returned, otherwise the TreeNode object
     * holding the data is returned. To access the actual data use .getData() on the returned object.
     *
     * @param current    : TreeNode
     * @param dataToFind : int
     * @return :TreeNode
     */
    public TreeNode find(TreeNode current, int dataToFind) {

        while (current != null) {
            if (dataToFind == current.getData()) {
                return current;
            } else if (dataToFind < current.getData()) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        return current;
    }

    /**
     * Traverse the TreeNode object passed in as argument ant prints out its values. The traversal type is inorder which
     * means that tha values will get printed in ascending order.
     *
     * @param current : TreeNode
     */
    public void inOrder(TreeNode current) {
        if (current != null) {
            inOrder(current.getLeftChild());
            System.out.print(current.getData() + ", ");
            inOrder(current.getRightChild());
        }

    }

    /*There are actually 4 outcomes to determine how the TreeNode object should be deleted: If the object is a
    leaf, if it has one child (and if the child is a leftChild or rightChild child) and if it has two children.

     */
    private TreeNode remove(TreeNode current, int dataToRemove) {

        TreeNode focusNode = current;
        TreeNode parent = current;

        boolean isLeftChild = true;

        //find the node that holds the data and determine if the node is a leftChild or rightChild child
        while (focusNode.getData() != dataToRemove) {

            parent = focusNode;

            if (dataToRemove < focusNode.getData()) {
                isLeftChild = true;
                focusNode = focusNode.getLeftChild();
            } else if (dataToRemove> focusNode.getData()){
                isLeftChild = false;
                focusNode = focusNode.getRightChild();
            }

            //Node with the data was not found
            if (focusNode == null) {
                return null;

            }
        }

        //Node is a leaf
        if (focusNode.getLeftChild() == null && focusNode.getRightChild() == null) {

            if (focusNode == current) {
                current = null;
            } else if (isLeftChild) {
                parent.setLeftChild(null);
            } else {
                parent.setRightChild(null);
            }
        }

        //Node has only leftChild child
        else if (focusNode.getRightChild() == null) {
            if (focusNode == current) {
                current = focusNode.getLeftChild();
            } else if (isLeftChild) {
                parent.setLeftChild(focusNode.getLeftChild());
            } else {
                parent.setRightChild(focusNode.getLeftChild());
            }

        }

        //Node has only rightChild child
        else if (focusNode.getLeftChild() == null) {
            if (focusNode == current) {
                current = focusNode.getRightChild();
            } else if (isLeftChild) {
                parent.setLeftChild(focusNode.getRightChild());
            } else
                parent.setRightChild(focusNode.getRightChild());
        }

        //Node has two children
        else {
            TreeNode successor = findSuccessor(focusNode);

            if (focusNode == current) {
                current = successor;
            } else if (isLeftChild) {
                parent.setLeftChild(successor);
            } else {
                parent.setRightChild(successor);
            }

            successor.setLeftChild(focusNode.getLeftChild());


        }


        return current;
    }

    /*
    This method is called upon when we need to delete a TreeNode that has two children. We must find a successor to
    replace the Node that we want to delete. We do so by finding the Node's smallest child in the rightChild subtree.
     */
    private TreeNode findSuccessor(TreeNode current) {
        TreeNode successor = null;
        TreeNode successorParent = null;
        TreeNode focusNode = current.getRightChild();

        while (focusNode != null) {
            successorParent = successor;
            successor = focusNode;
            focusNode = focusNode.getLeftChild();
        }

        if (successor != current.getRightChild()) {
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(current.getRightChild());
        }

        return successor;
    }

    /**
     * This method visualizes the tree in the console.
     */
    public void printTree() {
        rootNode.printTree();
    }


}
