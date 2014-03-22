import org.junit.Test;
import static org.junit.Assert.*;

public class TestTaroString {
    @Test
    public void test1() {
        assertEquals(TaroString.getAnswer("XCYAZTX"), "Possible");
    }

    @Test
    public void test2() {
        assertEquals(TaroString.getAnswer("CTA"), "Impossible");
    }

    @Test
    public void test3() {
        assertEquals(TaroString.getAnswer("ACBBAT"), "Impossible");
    }

    @Test
    public void test4() {
        assertEquals(TaroString.getAnswer("SGHDJHFIOPUFUHCHIOJBHAUINUIT"), "Possible");
    }

    @Test
    public void test5() {
        assertEquals(TaroString.getAnswer("CCCATT"), "Impossible");
    }
}
