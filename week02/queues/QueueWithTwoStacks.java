import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

public class QueueWithTwoStacks<Item> {
    private Stack<Item> inbox;
    private Stack<Item> outbox;

    public QueueWithTwoStacks() {
        inbox = new Stack<Item>();
        outbox = new Stack<Item>();
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        inbox.push(item);
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        if (outbox.isEmpty()) {
            fillOutbox();
        }

        return outbox.pop();
    }

    public boolean isEmpty() {
        return inbox.isEmpty() && outbox.isEmpty();
    }

    public int size() {
        return inbox.size() + outbox.size();
    }

    private void fillOutbox() {
        while (!inbox.isEmpty()) {
            outbox.push(inbox.pop());
        }
    }

    public static void main(String[] args) {
        QueueWithTwoStacks<Integer> queue = new QueueWithTwoStacks<>();
        queue.enqueue(1);
        queue.enqueue(2);
        queue.enqueue(3);
        queue.enqueue(4);
        queue.enqueue(5);
        queue.enqueue(6);

        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }

        queue.enqueue(7);
        queue.enqueue(8);
        queue.enqueue(9);
        queue.enqueue(10);
        
        while (!queue.isEmpty()) {
            StdOut.println(queue.dequeue());
        }
    }
}
