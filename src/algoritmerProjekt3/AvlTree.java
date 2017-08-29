package algoritmerProjekt3;

/**
 *  Created by Patrik Lind on 2016-10-12.
 *
 * This class acts like a binary-tree with the additional functionality to rearrange its data to sustain relativeley
 * balanced. Whenever a element is inserted or removed from this tree the structure can become unbalanced and this class
 * resolves the balance by rotating the tree's nodes.
 *
 * There are four cases for imbalance that can occur and are solved with correct rotation:
 *
 * Left-left    =====> rotateRight(Z)
 *
 *          Z                       Y
 *        /  \                    /   \
 *       Y                      X      Z
 *     /
 *    X
 *
 * Left-right  =====> rotateLeft(Y) =====> rotateRight(Z)
 *
 *          Z                 Z               Y
 *        /  \              /   \           /   \
 *       Y                 Y               X     Z
 *        \              /
 *         X            X
 *
 * Right-left  =====> rotateRight(Y) =====> rotateLeft(Z)
 *
 *          Z                 Z                 Y
 *        /  \              /   \             /   \
 *            Y                  Y          Z      X
 *           /                    \
 *          X                      X
 *
 * Right-right  =====> rotateLeft(Z)
 *
 *          Z                   Y
 *        /  \                /   \
 *            Y              Z     X
 *             \
 *              X
 *

 */
public class AvlTree {

    private AvlNode root;


    /**
     * Attempt to add the integer passed in as argument to the tree. If the tree already holds a same value as the data
     * passed in, the new value wont be stored.
     *
     * After insertion this method will rebalance the tree structure.
     *
     * @param data : int
     */
    public void add(int data) {
        try {
            root = add(root, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Attempts to remove the data passed in as argument if its found in the tree. If the tree doesn't hold the value
     * nothing is removed.
     *
     * After deletion this method will rebalance the tree structure.
     *
     * @param data : int
     */
    public void delete(int data){

        root = remove(root, data);

    }

    /**
     * Attempt to find data in the TreeNode. This method takes the TreeNode to search as agrument aswell as the integer
     * value of data to find. If data was not found in the TreeNoede NULL is returned, otherwise the TreeNode object
     * holding the data is returned. To access the actual data use .getData() on the returned object.
     *
     * @param current    : AvlNode
     * @param dataToFind : int
     * @return :AvlNode
     */
    public AvlNode find(AvlNode current, int dataToFind){
        while(current!=null){
            if(dataToFind== current.data){
                return current;
            }else if(dataToFind<current.data){
                current = current.leftChild;
            }else{
                current = current.rightChild;
            }
        }

        return current;
    }


    /**
     * Traverse the TreeNode object passed in as argument and print out its values. The traversal type is inorder which
     * means that tha values will get printed in ascending order.
     *
     * @param current : AvlNode
     */
    public void inOrder(AvlNode current){
        if(current!=null){
            inOrder(current.leftChild);
            System.out.print(current.data+", ");
            inOrder(current.rightChild);
        }

    }

    public void printTree() {
        root.printTree();
    }

    /**
     * This method returns the rootNode of this tree.
     *
     * @return : rootNode : AvlNode
     */
    public AvlNode getRoot() {
        return root;
    }



    /*Remove the node that holds the data passed in as argument. After removal rebalance the tree structure if
    neccessary*/
    private AvlNode remove(AvlNode current, int dataToRemove){

        if(current==null){
            return  current;
        }

        //Recursive search for the node to remove.
        if(dataToRemove<current.data){
            current.leftChild=remove(current.leftChild,dataToRemove);
        }else if(dataToRemove>current.data){
            current.rightChild=remove(current.rightChild, dataToRemove);

         //The node is found
        }else{

            // Node has either 0 or 1 child
            if(current.leftChild==null || current.rightChild==null){
                AvlNode temp = null;

                //Node has right child
                if(temp==current.leftChild){
                    temp = current.rightChild;
                //Node has left child
                }else{
                    temp = current.leftChild;
                }

                //Node has 0 children
                if(temp==null){
                    temp=current;
                    current=null;
                //Node has 1 child
                }else{
                    current = temp;
                }
             //Node has 2 children
            }else{
               AvlNode successor = findSuccessor(current);
                current.data=successor.data;
                current.rightChild=remove(current.rightChild, successor.data);
            }

        }

        if(current==null){
            return current;
        }

        //Set proper height for the node
        current.height = Math.max(height(current.leftChild), height(current.rightChild))+1;

        int balance = getBalance(current);

        //Left-left
        if(balance > 1 && getBalance(current.leftChild) >=0){
            return rotateRight(current);
        }

        //Left-right
        if(balance > 1 && getBalance((current.leftChild))<0){
            current.leftChild = rotateLeft(current.leftChild);
            return rotateRight(current);
        }

        //Right-right
        if(balance<-1 && getBalance(current.rightChild)<=0){
            return rotateLeft(current);
        }

        //Right-left
        if(balance<-1 && getBalance(current.rightChild)>0){
            current.rightChild = rotateRight(current.rightChild);
            return rotateLeft(current);
        }



        return current;
    }

    //Check the balance of the node by evaluating its children
    private int getBalance(AvlNode node) {
        if(node!=null){
            return height(node.leftChild)-height(node.rightChild);
        }
        return 0;
    }

    /*Add the value to the node and rebalance afterwards*/
    private AvlNode add(AvlNode node, int data) {

        /* 1.  Perform the normal BST rotation */
        if (node == null) {
            return (new AvlNode(data));
        }

        if (data < node.data) {
            node.leftChild = add(node.leftChild, data);
        } else {
            node.rightChild = add(node.rightChild, data);
        }

        node.height = Math.max(height(node.leftChild), height(node.rightChild)) + 1;

        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && data < node.leftChild.data) {
            return rotateRight(node);
        }

        // Right Right Case
        if (balance < -1 && data > node.rightChild.data) {
            return rotateLeft(node);
        }

        // Left Right Case
        if (balance > 1 && data > node.leftChild.data) {
            node.leftChild = rotateLeft(node.leftChild);
            return rotateRight(node);
        }

        // Right Left Case
        if (balance < -1 && data < node.rightChild.data) {
            node.rightChild = rotateRight(node.rightChild);
            return rotateLeft(node);
        }

        return node;
    }

    private AvlNode rotateLeft(AvlNode x) {

        AvlNode y = x.rightChild;
        AvlNode T2 = y.leftChild;

        y.leftChild = x;
        x.rightChild = T2;

        x.height = Math.max(height(x.leftChild), height(x.rightChild)) + 1;
        y.height = Math.max(height(y.leftChild), height(y.rightChild)) + 1;

        return y;
    }

    private AvlNode rotateRight(AvlNode y) {

        AvlNode x = y.leftChild;
        AvlNode T2 = x.rightChild;

        x.rightChild = y;
        y.leftChild = T2;

        y.height = Math.max(height(y.leftChild), height(y.rightChild)) + 1;
        x.height = Math.max(height(x.leftChild), height(x.rightChild)) + 1;

        return x;
    }

    /*Returns the integer-value of the node's height, if the node passed in as argument isn't null, otherwise
    * -1 will return*/
    private int height(AvlNode t ) {

        return t == null ? -1 : t.height;
    }

    private AvlNode findSuccessor(AvlNode current) {
        AvlNode successor = null;
        AvlNode successorParent = null;
        AvlNode focusNode = current.rightChild;


        while (focusNode != null) {
            successorParent = successor;
            successor = focusNode;
            focusNode = focusNode.leftChild;
        }

        if (successor != current.rightChild) {
            successorParent.leftChild=successor.rightChild;
            successor.rightChild=current.rightChild;
        }

        return successor;
    }

    public class AvlNode {

        private AvlNode leftChild, rightChild;
        private int data, height;


        /**Instantiate this object by passing in a integer value to be stored.
         *
         * @param n : int
         */
        public AvlNode(int n)
        {
            leftChild = null;
            rightChild = null;
            data = n;
            height = 0;
        }

        public void printTree(){
            if(this.rightChild !=null){
                rightChild.printTree(true, "");
            }
            printNodeValue();
            if(leftChild !=null){
                leftChild.printTree(false,"");
            }

        }

        private void printNodeValue() {
            System.out.print("v: "+data +" h: "+ height);

            System.out.print('\n');
        }

        private void printTree(boolean isRight, String indent){
            if(rightChild !=null){
                rightChild.printTree(true, indent + (isRight ? "         " : "  |      "));
            }
            System.out.print(indent);
            if (isRight){
                System.out.print(" /");
            }else{
                System.out.print("  \\");
            }
            System.out.print("------ ");
            printNodeValue();

            if(leftChild !=null){
                leftChild.printTree(false, indent + (isRight ? " |      " : "         "));
            }
        }
    }
}
