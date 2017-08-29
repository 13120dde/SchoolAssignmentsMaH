package bildTextU3TrieSuffix;

import algoritmerProjekt3.TreeNode;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by Patrik Lind on 2016-12-12.
 */
public class TrieTree {

    private TrieNode root = null;

    public TrieTree(){

        root = new TrieNode(' ', new Object());
    }

    public void put(char[] key, Object data){

        TrieNode u = root;
        TrieNode v;

        for(int i = 0; i< key.length; i++){
           v = delta(u, key[i]);
            if(v==null){
                //No node in with value key[i] is found.
                u = addNode(u, key[i]);
            }else{
                u = v;
            }
        }
        u.data = data;

    }

    /**
     *
     * @param parent
     * @param c
     * @return
     */
    private TrieNode addNode(TrieNode parent, char c) {
        parent.isLeaf =false;
        TrieNode newNode = new TrieNode(c, null);
        parent.listChildren.add(newNode);
        return newNode;
    }

    /**If the node passed in as argument has listChildren, iterates trought them and returns the child-node with value c.
     * If no such node is found in u, null is returned.
     *
     * @param u : TrieNode
     * @param c : char
     * @return childWithChar : TrieNode
     */
    private TrieNode delta(TrieNode u, char c) {
        if(u.isLeaf){
            return null;
        }
        TrieNode child;
        Iterator<TrieNode> i = u.listChildren.iterator();
        while(i.hasNext()){
            child = i.next();
            if(child.value ==c){
                return child;
            }
        }

        return null;
    }

    public Object get(char[]key){
        TrieNode u = root;
        TrieNode v;

        for(int i = 0; i<key.length; i++){
            v=delta(u, key[i]);
            if(v!=null){
                u=v;
            }else{
                return null;
            }
        }

        return u.data;
    }

    private class TrieNode{


        private LinkedList<TrieNode> listChildren;
        private char value;
        private Object data;
        private boolean isLeaf;

        public TrieNode(char c, Object data){

            value = c;
            listChildren = new LinkedList<TrieNode>();
            this.data = data;
            isLeaf = true;

        }

    }
}
