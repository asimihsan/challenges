import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.Reader;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestBinarySearch {

    @Test
    public void sample() {
        assertEquals("4 1 -1 -1 4 2", getOutputForInput("/test_1.input"));
    }

    @Test
    public void singleElementPresent() {
        assertEquals("1", getOutputForInput("/test_2.input"));
    }

    @Test
    public void singleElementNotPresent() {
        assertEquals("-1", getOutputForInput("/test_3.input"));
    }

    @Test
    public void twoElementsPresentLeft() {
        assertEquals("1", getOutputForInput("/test_4.input"));
    }

    @Test
    public void twoElementsPresentRight() {
        assertEquals("2", getOutputForInput("/test_5.input"));
    }

    @Test
    public void twoElementsNotPresentLeft() {
        assertEquals("-1", getOutputForInput("/test_6.input"));
    }

    @Test
    public void twoElementsNotPresentRight() {
        assertEquals("-1", getOutputForInput("/test_7.input"));
    }

    private String getOutputForInput(String inputResource) {
        String returnValue = null;
        try (
            Reader input = getReader(inputResource);
        ) {
            returnValue = BinarySearch.solve(input);
        } catch (final IOException e) {
            fail("Failed to open input file: " + inputResource);
        }
        return returnValue;
    }

    private Reader getReader(String input) throws IOException {
        InputStream in = getClass().getResourceAsStream(input);
        InputStreamReader inr = new InputStreamReader(in);
        BufferedReader br = new BufferedReader(inr);
        return br;
    }
}