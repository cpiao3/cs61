package synthesizer;

public abstract class AbstractBoundedQueue<T> implements BoundedQueue<T>{
    protected int fillCount;
    protected int capacity;
    @Override
    public abstract T peek();
    @Override
    public abstract T dequeue();
    @Override
    public abstract void enqueue(T x);
    @Override
    public abstract int capacity();
    @Override
    public abstract int fillCount();
    @Override
    public abstract boolean isEmpty();
    @Override
    public abstract boolean isFull();
}
