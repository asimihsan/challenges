import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class TestFibonacci {
    @Test
    public void testZero() {
        assertThat(Fibonacci.calculate(0), is(0));
    }

    @Test
    public void testOne() {
        assertThat(Fibonacci.calculate(1), is(1));
    }

    @Test
    public void testNine() {
        assertThat(Fibonacci.calculate(9), is(34));
    }

    @Test
    public void testMax() {
        assertThat(Fibonacci.calculate(25), is(75025));
    }
}