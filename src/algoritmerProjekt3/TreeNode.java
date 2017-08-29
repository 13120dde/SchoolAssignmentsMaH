package algoritmerProjekt3;

/**
 * Created by Patrik Lind on 2016-10-01.
 */
public class TreeNode {

    private int data;
    private TreeNode left, right;


    public TreeNode(int data){
        this.data=data;
    }

    public int getData(){
        return data;
    }

    public TreeNode getLeftChild(){
        return left;
    }

    public TreeNode getRightChild(){
        return right;
    }


    public void setLeftChild(TreeNode leftChild) {
        left = leftChild;
    }

    public void setRightChild(TreeNode rightChild) {
        right = rightChild;
    }

    //Following methods are just for debugging

    public void printTree(){
        if(this.right!=null){
            right.printTree(true, "");
        }
        printNodeValue();
        if(left !=null){
            left.printTree(false,"");
        }

    }

    private void printTree(boolean isRight, String indent){
        if(right !=null){
            right.printTree(true, indent + (isRight ? "         " : "  |      "));
        }
        System.out.print(indent);
        if (isRight){
            System.out.print(" /");
        }else{
            System.out.print("  \\");
        }
        System.out.print("------ ");
        printNodeValue();

        if(left!=null){
            left.printTree(false, indent + (isRight ? " |      " : "         "));
        }
    }

    private void printNodeValue() {
        System.out.print("v: "+data);

        System.out.print('\n');
    }
}
