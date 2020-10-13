package synthesizer;

import java.util.Iterator;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> implements BoundedQueue<T> {
    private int first;
    private int last;
    private int fillCount;
    private T[] rb;
    int size;

    public ArrayRingBuffer(int capacity) {
        rb = (T[]) new Object[capacity];
        size = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow").
     * @param x
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == size){
            throw new RuntimeException("Ring Buffer overflow");
        }
        int middle = Math.round(size/ 2);
        if(fillCount == 0){
            rb[middle] = x;
            first = middle;
            last = middle+1;
            fillCount++;
        } else {
            rb[last] = x;
            last++;
            fillCount++;
            if (last == size) {
                last = 0;
            }
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T dequeue() {
        if (fillCount == 0){
            throw new RuntimeException("Ring Buffer underflow");
        }
        T a = rb[first];
        rb[first] = null;
        first++;
        fillCount--;
        if (first == size) {
            first = 0;
        }
        return a;
    }

    /**
     * Return oldest item, but don't remove it. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow").
     */
    @Override
    public T peek() {
        T a = rb[first];
        return a;
    }

    private T peek(int c) {
        T a = rb[c];
        return a;
    }

    @Override
    public boolean equals(Object a){
        ArrayRingBuffer<T> b = (ArrayRingBuffer<T>)a;
        if (this.capacity() != b.capacity()){
            return false;
        }
        int count = this.first;
        for (int i = 0;i<this.capacity();i++){
            if (count == this.capacity()){
                count = 0;
            }
            if (this.peek(count) != b.peek(count)){
                return false;
            }
            count ++;
        }
        return true;
    }

    @Override
    public int capacity() {
        return size;
    }

    @Override
    public int fillCount(){
        return fillCount;
    }

    @Override
    public Iterator<T> iterator(){
        return new iterator();
    }

    private class iterator implements Iterator<T> {
        private int pos;

        public iterator(){
            pos = first;
        }
        @Override
        public boolean hasNext(){
            return fillCount>0;

        }
        @Override
        public T next(){
            T a = rb[pos];
            pos++;
            if (pos == size){
                pos = 0;
            }
            return a;
        }
    }
}
