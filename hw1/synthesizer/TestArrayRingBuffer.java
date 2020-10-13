package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        AbstractBoundedQueue<Integer> b2 = new ArrayRingBuffer<Integer>(2);
        b2.enqueue(1);
        b2.enqueue(1);
        b2.dequeue();
        b2.dequeue();





        }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
