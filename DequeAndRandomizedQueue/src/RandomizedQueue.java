import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
/**
 * Created by Nero on 17/3/24.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int index;
    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        index = 0;
    }

    // is the queue empty?
    public boolean isEmpty() {
        return index == 0;
    }

    // return the number of items on the queue
    public int size() {
        return index;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null)
            throw new NullPointerException("Add null item");
        if (index == items.length)
            resize(2*items.length);
        items[index] = item;
        index += 1;
    }


    // remove and return a random item
    public Item dequeue() {
        if (index == 0)
            throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(index);
        Item item = items[randomIndex];
        items[randomIndex] = items[index-1];
        items[index-1] = null;
        index = index - 1;
        return item;
    }

    // return (but do not remove) a random item
    public Item sample() {
        if (index == 0)
            throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(index);
        return items[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator()  {
        return new RandomizedQueueIter();
    }

    private class RandomizedQueueIter implements Iterator<Item> {
        private int i = 0;
        private int[] randomIndexs;

        public RandomizedQueueIter(){
            randomIndexs = new int[index];
            for (int i = 0; i < index; i++) {
                randomIndexs[i] = i;
            }
            StdRandom.shuffle(randomIndexs);
        }

        public boolean hasNext() {
            return i < index;
        }

        public void remove() {
            throw new UnsupportedOperationException("Unsupported Operation Iter remove");
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Item item = items[randomIndexs[i]];
            i += 1;
            return item;
        }
    }


    private void resize(int capaticity) {
        Item[] copy = (Item []) new Object[capaticity];
        int size = size();
        for (int i = 0; i < size; i++) {
            copy[i] = items[i];
        }
        items = copy;
    }


}
