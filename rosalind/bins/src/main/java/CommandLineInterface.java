import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;

public class CommandLineInterface {
    public static void main(String[] args) throws IOException {
        try (
            InputStream in = CommandLineInterface.class.getResourceAsStream("/problem.input");
            InputStreamReader inr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(inr);
        ) {
            System.out.println(BinarySearch.solve(br));
        } catch (IOException e) {
            throw e;
        }

    }
}