import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item[] queue;
	private int N;
	private int capacity;
	
	public RandomizedQueue() {
		N = 0;
		capacity = 1;
		queue = (Item[]) new Object[capacity]; 
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	public void enqueue(Item item) {
		if (item == null) throw new java.lang.NullPointerException();
		if (N + 1 > capacity) resizePlus();
		queue[N] = item;
		N++;
	}
	
	private void resizePlus() {
		capacity *= 2;
		Item[] copy = (Item[]) new Object[capacity];
		for(int i = 0; i < N; i++) {
			copy[i] = queue[i];
		}
		queue = copy;
	}
	
	private void resizeMinus() {
		capacity /= 2;
		Item[] copy = (Item[]) new Object[capacity];
		for(int i = 0; i < capacity; i++) {
			copy[i] = queue[i];
		}
		queue = copy;
	}
	
	public Item dequeue() {
		if (isEmpty()) throw new java.util.NoSuchElementException();
		int index = StdRandom.uniform(N);
		Item toReturn = queue[index];
		for (int i = index; i < N - 1; i++) {
			queue[i] = queue[i + 1];
		}
		queue[N - 1] = null;
		N = N - 1;
		if (capacity / 4 > N) {
			resizeMinus();
		}
		return toReturn;
	}
	
	public Item sample() {
		if (isEmpty()) throw new java.util.NoSuchElementException();
		return queue[StdRandom.uniform(N)];
	}
	
	public Iterator<Item> iterator() {
		return new RanQueueIterator();
	}
	
	private class RanQueueIterator implements Iterator<Item> {
		private int current = 0;
		private int[] shuffleIndexes = new int[N];
		@Override
		public boolean hasNext() {
			if (current == 0) {
				for (int i = 0; i < N; i++) {
					shuffleIndexes[i] = i;
				}
				StdRandom.shuffle(shuffleIndexes);
			}
			return current < N;
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			if (current >= N || N == 0) throw new java.util.NoSuchElementException();
			else {
				if (current == 0) {
					for (int i = 0; i < N; i++) {
						shuffleIndexes[i] = i;
					}
					StdRandom.shuffle(shuffleIndexes);
				}
				return queue[shuffleIndexes[current++]];
			}
		}
		
	}
	
	public static void main(String[] args) {
		RandomizedQueue rq = new RandomizedQueue();
		rq.enqueue(30);
		rq.enqueue(40);
		rq.enqueue(23);
		rq.enqueue(15);
		rq.dequeue();
	}
}
