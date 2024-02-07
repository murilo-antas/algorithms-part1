import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private static final int INITIAL_CAPACITY = 8;
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item");
        }
        if (size == items.length) {
            resize(2 * items.length);
        }
        items[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        int random = StdRandom.uniformInt(size);
        Item item = items[random];
        items[random] = items[--size];
        items[size] = null;

        if (size > 0 && size == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int random = StdRandom.uniformInt(size);
        return items[random];
    }

    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i = 0;
        private int[] indexArray;

        public RandomizedQueueIterator() {
            indexArray = new int[size];
            for (int j = 0; j < size; j++) {
                indexArray[j] = j;
            }
            StdRandom.shuffle(indexArray);
        }

        public boolean hasNext() {
            return i < size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            int index = indexArray[i];
            Item item = items[index];
            i++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();
        for (int i = 0; i < 100; i++) {
            q.enqueue(i);
        }
        while (!q.isEmpty()) {
            StdOut.println(q.dequeue());
        }

        StdOut.println("-----");

        for (int i = 0; i < 100; i++) {
            q.enqueue(i);
        }
        for (int i : q) {
            StdOut.println(i);
        }
    }
}
