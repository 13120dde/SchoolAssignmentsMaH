package algoritmerProjekt1;

/**Listnode class can store one generic object aswell as a reference to the next listNode object. The class's purpose
 * is to be a part of a single-linked list where each ListNode object stores only one instance of generic data aswell
 * as a reference (pointer) to the next listNode in the chain.
 *
 * Created by Patrik Lind on 2016-09-07.
 */
public class ListNode<T> {

    private T dataValue;
    private ListNode<T> nextNode;

    /**Instantiate a object of this class by passing in a generic datavalue and a reference to the next listNode object
     * as argument.
     *
     * @param dataValue : T
     * @param nextNode : ListNode
     */
    public ListNode(T dataValue, ListNode<T> nextNode){
        this.dataValue =dataValue;
        this.nextNode =nextNode;
    }

    /**Returns the generic object stored in this node without removing it.
     *
     * @return dataValue : T
     */
    public T getData(){
        return dataValue;
    }

    /**Returns a reference to the next ListNode object of this object.
     *
     * @return nextNode : ListNode
     */
    public ListNode<T> getNextNode() {
        return nextNode;
    }
}
