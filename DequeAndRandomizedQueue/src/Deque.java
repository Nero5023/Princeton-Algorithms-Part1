import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Nero on 17/3/24.
 */
public class Deque<Item> implements Iterable<Item> {
    private Item[] items;
    private int head;
    private int tail;

    // construct an empty deque
    public Deque() {
        items = (Item[]) new Object[1];
        head = tail = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return head == tail && items[head] == null;
    }

    // return the number of items on the deque
    public int size() {
        int newTail = tail;
        if (head == tail && items[head] != null) {
            return items.length;
        }
        if (head > tail) {
            newTail = items.length + tail;
        }
        return newTail - head;
    }

    private void prepareAddingItem(Item item, boolean isAddFront) {
        String str = "front";
        if (!isAddFront)
            str = "end";
        if (item == null)
            throw new NullPointerException("Add null item to the " + str);
        if (size() == items.length)
            resize(2*items.length);
    }

    // add the item to the front
    public void addFirst(Item item) {
        prepareAddingItem(item, true);
        if (head == 0)
            head = items.length - 1;
        else
            head = (head - 1)%items.length;
        items[head] = item;
    }

    // add the item to the end
    public void addLast(Item item) {
        prepareAddingItem(item, false);
        items[tail] = item;
        tail = (tail + 1)%items.length;
    }

    private void prepareRemoveItem() {
        if (isEmpty())
            throw new NoSuchElementException();
        if (size() == items.length/4)
            resize(items.length/2);
    }

    // remove and return the item from the front
    public Item removeFirst() {
        prepareRemoveItem();
        Item item = items[head];
        items[head] = null;
        head = (head + 1)%items.length;
        return item;
    }

    // remove and return the item from the front
    public Item removeLast() {
        prepareRemoveItem();
        if (tail == 0)
            tail = items.length - 1;
        else
            tail = (tail - 1)%items.length;
        Item item = items[tail];
        items[tail] = null;
        return item;
    }



    public Iterator<Item> iterator() {
        return new DequeIter();
    }


    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        int size = size();
        if (head >= tail) {
            tail = items.length + tail;
        }

        for (int i = head; i < tail; i++) {
            copy[i-head] = items[i%items.length];
        }
        head = 0;
        tail = size;
        items = copy;
    }

    private class DequeIter implements Iterator<Item> {
        private int i = head;

        public boolean hasNext() {
            return i < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported Operation Iter remove");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = items[i];
            i = (i+1)%items.length;
            return item;
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<Integer>();
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addLast(3);
        deque.addFirst(-1);
        deque.addLast(4);
        deque.removeFirst();
        deque.removeLast();
        for (Integer num: deque) {
            System.out.println(num);
        }
    }
}
