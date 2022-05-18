import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    // initial capacity of the underlying resizing array
    private static final int INIT_CAPACITY = 8;

    private Item[] q;           // queue elements
    private int n;              // number of elements on queue

    // construct an empty randomized queue
    public RandomizedQueue() {
        q = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }
        q = copy;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // double size of array if necessary and recopy to front of array
        if (n == q.length) {
            resize(2 * q.length);
        }
        q[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        
        // randomized idx between 0 and n-1
        int randomIdx = StdRandom.uniform(n);
        
        Item item = q[randomIdx];
        if (randomIdx == n-1) {
            q[n-1] = null;
        } else {
            q[randomIdx] = q[n-1];
            q[n-1] = null;
        }
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }

        int randomIdx = StdRandom.uniform(n);
        return q[randomIdx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int i;

        public RandomizedQueueIterator() {
            i = 0;
        }

        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return q[i++];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; i++) {
            rq.enqueue(i);
        }
        for (int e : rq) {
            System.out.println(e);
        }

        System.out.println("--------------");

        for (int i = 0; i < 3; i++) {
            System.out.println(rq.dequeue());
        }

        for (int e : rq) {
            System.out.println(e);
        }
    }
}