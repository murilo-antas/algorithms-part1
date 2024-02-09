import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class StackWithMax {
    private Stack<Double> stack;
    private Stack<Double> stackMax;

    public StackWithMax() {
        stack = new Stack<>();
        stackMax = new Stack<>();
    }

    public void push(double item) {
        double max = item;
        if (!stackMax.isEmpty()) {
            max = Math.max(max, stackMax.peek());
        }
        stack.push(item);
        stackMax.push(max);
    }

    public double pop() {
        stackMax.pop();
        return stack.pop();
    }

    public double peek() {
        return stack.peek();
    }

    public double max() {
        return stackMax.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }

    public int size() {
        return stack.size();
    }

    public static void main(String[] args) {
        StackWithMax stack = new StackWithMax();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        StdOut.println("Max: " + stack.max());
        StdOut.println("Pop: " + stack.pop());
        StdOut.println("Max: " + stack.max());
        stack.push(-1);
        stack.push(-2);
        while (!stack.isEmpty()) {
            StdOut.println("Max: " + stack.max());
            StdOut.println("Pop: " + stack.pop());
        }
    }
}
