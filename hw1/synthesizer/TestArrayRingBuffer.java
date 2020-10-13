package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        AbstractBoundedQueue<Integer> b2 = new ArrayRingBuffer<Integer>(10);

        b2.enqueue(3);
        b2.enqueue(2);
        b2.enqueue(3);
        b2.enqueue(1);
        b2.enqueue(6);
        b2.enqueue(1);
        b2.enqueue(5);
        b2.enqueue(6);

        for (int i : b2){
            System.out.println(i);
        }



        }

    /** Calls tests for ArrayRingBuffer. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
