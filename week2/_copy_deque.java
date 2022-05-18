// Throw an IllegalArgumentException if the client 
//    calls either addFirst() or addLast() with a null argument.
// Throw a java.util.NoSuchElementException if the client
//   calls either removeFirst() or removeLast() when the deque is empty.
// Throw a java.util.NoSuchElementException if the client
//  calls the next() method in the iterator when there 
//  are no more items to return.
// Throw an UnsupportedOperationException if 
//  the client calls the remove() method in the iterator.

// Unit testing.  Your main() method must call directly every public constructor and method to help verify that they work as prescribed (e.g., by printing results to standard output).
// Performance requirements.  Your deque implementation must support 
// each deque operation (including construction) in constant
//  worst-case time. A deque containing n items must use at most
// 48n + 192 bytes of memory. Additionally, your iterator 
// implementation must support each operation (including construction) in constant worst-case time.

// import java.util.IllegalArgumentException;
import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.util.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {
    // initial capacity
    private static final int INIT_CAPACITY = 8;

    private Item[] dq;
    private int n;      // number of elements in deque
    private int first;  // index of first element in deque
    private int last;   // index of last element in queue

    // construct an empty deque
    public Deque() {
        dq = (Item[]) new Object[INIT_CAPACITY];
        n = 0;
        first = 0;
        last = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // [-, -, -, 0, 1, 2, 3]
    private void resize(int capacity) {
        assert capacity >= n;
        Item[] copy = (Item[]) new Object[capacity];
        int tempFirst = first;

        if (first < last) {
            for(int i = 0; i < n; i++) {
                copy[i] = dq[(first + i) % capacity];
            }
        } else {
            for (int i = 0; i < n; i++) {

            }
        }
        for(int i = 0; i < n; i++) {
            copy[i] = dq[(tempFirst + n) % capacity];
            tempFirst++;
        }
        dq = copy;
        first = (first + n) % capacity;
        
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == dq.length) {
            resize(2 * dq.length);
        }

        dq[first--] = item;
        
        if (first < 0) {
            first = dq.length - 1;
        }
        if (n == 0) {
            last++;
            if (last == dq.length) {
                last = 0;
            }
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        if (n == dq.length) {
            resize(2 * dq.length);
        }
        dq[last++] = item;

        if (last == dq.length) {
            last = 0;
        }
        
        // when n == 0, first == last
        if (n == 0) {
            first--;
            if (first < 0) {
                first = dq.length - 1;
            }
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }

        Item item = dq[(first+1) % dq.length];
        dq[(first+1) % dq.length] = null;
        n--;
        first++;
        if (first == dq.length) {
            first = 0;
        }

        // shrink size of array if necessary
        if (n > 0 && n <= dq.length / 4) {
            resize(dq.length / 2);
        } 
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }

        Item item = dq[(last+dq.length-1) % dq.length];
        dq[(last+dq.length-1) % dq.length] = null;
        n--;
        last--;
        if (last < 0) {
            last = dq.length - 1;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() { return i < n; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = dq[(i+first) % dq.length];
            i++;
            return item;
        }
    }
    
    public void printAll() {
        for (int i = 0; i < dq.length; i++) {
            System.out.println(i + "  " + dq[i]);
        }
        System.out.println("first --- " + first);
        System.out.println("last --- " + last);
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        for (int i = 1; i <= 4; i++) {
            dq.addFirst(i);
            dq.addLast(i*10);
        }
        // dq.addFirst(100);
        // dq.addFirst(1);
        // dq.addLast(2);
        // dq.addFirst(0);
        // dq.addLast(3);
        // dq.addLast(4);
        // dq.removeFirst();
        // dq.removeLast();
        // dq.addFirst(-1);
        // dq.addFirst(-2);
        // dq.addLast(5);
        // dq.removeFirst();
        // dq.removeFirst();
        // dq.removeFirst();

        // dq.removeFirst();
        // dq.removeFirst();
        // dq.removeFirst();
        // // dq.removeFirst();

        dq.printAll();
    }

}