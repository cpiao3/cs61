package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
            ArrayRingBuffer<Integer> ls = new ArrayRingBuffer(7);
            ArrayRingBuffer<Integer> ls2 = new ArrayRingBuffer(7);
            ls.enqueue(1);
            ls.enqueue(2);
            ls.enqueue(3);
            ls.enqueue(1);
            ls.enqueue(1);
            ls.enqueue(2);
            ls.enqueue(3);

            ls2.enqueue(3);
            ls2.enqueue(2);
            ls2.enqueue(3);
            ls2.enqueue(1);
            ls2.enqueue(1);
            ls2.enqueue(2);
            ls2.enqueue(1);

            assertFalse(ls.equals(ls2));

        }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
