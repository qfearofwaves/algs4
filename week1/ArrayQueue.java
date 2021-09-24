public class ArrayQueue<Item> {
    private Item[] q;   
    private int count;          // number of elements on queue
    private int first;          // index of first element of queue
    private int last;           // index of last element of queue


    public ArrayQueue(int n) {
        q = (Item[]) new Object[n];
        count = 0;
        first = 0;
        last = 0;
    }

    public boolean isEmpty() {
        return count == 0;
    }

    public int size() {
        return count;
    }

    public void enqueue(Item item) {
        if (count == q.length) {
            // queue is full
            System.out.println("queue is full");
            return;
        }
        q[last++] = item;
        count++;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        Item item = q[first];
        q[first] = null;
        count--;
        first++;

        // wrap around
        if (first == q.length) {
            first = 0;
        }
    }

    public Item peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue underflow");
        }
        return q[first];
    }

    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }
}