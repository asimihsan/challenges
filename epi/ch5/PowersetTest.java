import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Powerset {
    public static <T> void printPowerset(List<T> elements) {
        boolean[] mask = new boolean[elements.size()];
        for (int i = 0; i < Math.pow(2, elements.size()); i++) {
            List<T> output = new ArrayList<>();
            for (int j = 0; j < mask.length; j++) {
                if (mask[j] == true) output.add(elements.get(j));
            }
            System.out.println(output);
            incrementMask(mask);
        }
    }

    private static void incrementMask(boolean[] mask) {
        if (mask[mask.length - 1] == false) {
            mask[mask.length - 1] = true;
            return;
        }
        mask[mask.length - 1] = false;
        boolean carry = true;
        for (int i = mask.length - 2; i >= 0; i--) {
            if (mask[i] == true && carry == true) {
                mask[i] = false;
                carry = true;
            }
            else if (carry == true) {
                mask[i] = true;
                break;
            } else {
                break;
            }
        }
    }
}

class PowersetTest {
    public static void main(String[] args) {
        List<String> elements = new ArrayList<>();
        elements.add("A"); elements.add("B"); elements.add("C");
        Powerset.printPowerset(elements);
    }
}