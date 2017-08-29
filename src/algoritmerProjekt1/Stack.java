package algoritmerProjekt1;

/**
 * The Stack class can store different objects. This stack works with the Lastin-In-First-Out behaviour, meaning
 * that the .push(T data) method will place the item on top of the stack and .pull() will return the last item stored and
 * remove it from the stack.
 *
 * Additional methods of this class are .peek() wich returns the item on top of the stack without removing it from the
 * stack and .count() which returns the number of items stored in the stack.
 *
 * Created by Patrik Lind on 2016-09-07.
 */
public class Stack<T> {

    private ListNode<T> firstNode = null;
    private int size = 0;


    /**
     * Puts the item passed in as argument on the top of the stack.
     *
     * @param data : T
     */
    public void push(T data) {
        firstNode = new ListNode<T>(data, firstNode);
        size++;
    }

    /**
     * Removes and returns the last item put on the stack.
     *
     * @return : itemOnTop : T
     */
    public T pop() {
        if (size == 0) {
            return null;
        } else {
            T data = firstNode.getData();
            firstNode = firstNode.getNextNode();
            size--;
            return data;
        }
    }

    /**
     * Returns the item last placed in the stack without removing it from the stack.
     *
     * @return itemOnTop : T
     */
    public T peek() {
        if (size == 0) {
            return null;
        } else {
            return firstNode.getData();
        }
    }

    /**
     * Returns the Integer value representing the amount of items stored in this stack.
     *
     * @return size : int
     */
    public int count() {
        return size;
    }
}
