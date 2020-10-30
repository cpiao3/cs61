package lab9;

import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node pointer) {
        while (pointer.key != key && pointer != null) {
            if (key.compareTo(pointer.key) >= 0) {
                if (pointer.right == null){
                    return pointer.value;
                }
                pointer = pointer.right;
            } else {
                if (pointer.left == null) {
                    return pointer.value;
                }
                pointer = pointer.left;
            }
        }
        return pointer.value;
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        Node pointer = root;
        if (pointer == null){
            return null;
        }
        V value = getHelper(key,pointer);
        return value;
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private void putHelper(K key, V value, Node p) {
        if (p == null){
            root = new Node(key,value);
            size++;
        } else{
            while (p != null){
                if (key.compareTo(p.key)==0){
                    p.value = value;
                    break;
                } else if (key.compareTo(p.key)>0){
                    if (p.right == null){
                        p.right = new Node(key,value);
                        size++;
                        break;
                    }
                    p = p.right;
                } else{
                    if (p.left == null){
                        p.left = new Node(key,value);
                        size++;
                        break;
                    }
                    p = p.left;
                }
            }
        }
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        Node pointer = root;
        putHelper(key,value,pointer);

    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<K> iterator() {
        throw new UnsupportedOperationException();
    }
}
