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

import java.util.Iterator;
import java.util.NoSuchElementException;
// import java.util.UnsupportedOperationException;

public class Deque<Item> implements Iterable<Item> {
    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }

    private Node<Item> first;   // beginning of deque
    private Node<Item> last;    // end of deque
    private int n;              // number of elements on queue

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> oldFirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldFirst;
        first.prev = null;

        if (oldFirst == null) {
            last = first;
        } else {
            oldFirst.prev = first;
        }
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node<Item> oldLast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldLast;

        if (oldLast == null) {
            first = last;
        } else {
            oldLast.next = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }
        Item item = first.item;
        if (first.next == null) {
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev.next = null;
            first.prev = null;
        }
        
        n--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque underflow");
        }
        Item item = last.item;
        if (last.prev == null) {
            first = null;
            last = null;
        } else {
            last = last.prev;
            last.next.prev = null;
            last.next = null;
        }
        n--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator(first);
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> first) {
            current = first;
        }

        public boolean hasNext() { return current != null; }
        public void remove() {
            throw new UnsupportedOperationException();
        }
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> dq = new Deque<>();
        for (int i = 1; i <= 4; i++) {
            dq.addFirst(i);
            dq.addLast(i*10);
        }
        dq.addFirst(100);
        dq.removeFirst();
        dq.removeLast();
        for (int i : dq) {
            System.out.println(i);
        }
        System.out.println("-----------------");
        dq.addFirst(-1);
        dq.addFirst(-2);
        dq.addLast(5);
        for (int i : dq) {
            System.out.println(i);
        }
        System.out.println("-----------------");
        System.out.println("size: " + dq.size());
        System.out.println("empty? " + dq.isEmpty());
        dq.removeFirst();
        dq.removeFirst();
        dq.removeFirst();
        dq.removeFirst();
        dq.removeFirst();
        dq.removeFirst();
        dq.removeLast();
        for (int i : dq) {
            System.out.println(i);
        }
    }
}