import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;

class RPNCalculator {
    public static int parse(String input) {
        Scanner scanner = new Scanner(input);
        Deque<Integer> stack = new ArrayDeque<Integer>();
        while (scanner.hasNext()) {
            String next = scanner.next();
            Integer left, right;
            switch(next) {
                case "+":
                    right = stack.pop();
                    left = stack.pop();
                    stack.push(left + right);
                    break;
                case "-":
                    right = stack.pop();
                    left = stack.pop();
                    stack.push(left - right);
                    break;
                case "*":
                    right = stack.pop();
                    left = stack.pop();
                    stack.push(left * right);
                    break;
                case "/":
                    right = stack.pop();
                    left = stack.pop();
                    stack.push(left / right);
                    break;
                default:
                    stack.push(new Integer(next));
                    break;
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        System.out.println(parse("3 4 + 5 *"));
    }
}