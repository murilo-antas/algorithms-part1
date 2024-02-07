import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private class Node {
        Item item;
        Node previous;
        Node next;
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current.next != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private Node first;
    private Node last;
    private int size = 0;

    public Deque() {
        first = last = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        Node newNode = new Node();
        newNode.item = item;
        if (first == null) {
            first = newNode;
            last = first;
        } else {
            Node oldFirst = first;
            first = newNode;
            first.next = oldFirst;
            oldFirst.previous = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        Node newNode = new Node();
        newNode.item = item;
        if (last == null) {
            last = newNode;
            first = last;
        } else {
            Node oldLast = last;
            last = newNode;
            last.previous = oldLast;
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Item item = last.item;
        last = last.previous;
        if (last == null) {
            first = null;
        }
        size--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    public static void main(String[] args) {
        Deque<String> d = new Deque<String>();
        // Test isEmpty after addFirst and removeFirst
        d.addFirst("addFirst");
        StdOut.println("false == " + d.isEmpty());
        d.removeFirst();
        StdOut.println("true == " + d.isEmpty());

        // Test isEmpty after addLast and removeLast
        d.addLast("addLast");
        StdOut.println("false == " + d.isEmpty());
        d.removeLast();
        StdOut.println("true == " + d.isEmpty());

        // Test isEmpty after addFirst and removeLast
        d.addFirst("addFirst");
        StdOut.println("false == " + d.isEmpty());
        d.removeLast();
        StdOut.println("true == " + d.isEmpty());
    }
}
