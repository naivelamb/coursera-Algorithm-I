import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private Node first, last;
	private int size;
	private class Node {
		Item item;
		Node next;
		Node before;
	}
	
	public Deque() {
		first = null;
		last = null;
		size = 0;
	}
	
	public boolean isEmpty() {
		return (size == 0);
	}
	
	public int size() {
		return this.size;
	}
	
	public void addFirst(Item item) {
		if (item == null) throw new java.lang.NullPointerException();
		else {
			Node oldfirst = first;
			first = new Node();
			first.item = item;
			if (isEmpty()) last = first;
			else {
				first.next = oldfirst;
				oldfirst.before = first;
			}
			size++;
		}
	}
	
	public void addLast(Item item) {
		if (item == null) throw new java.lang.NullPointerException();
		else {
			Node oldlast = last;
			last = new Node();
			last.item = item;
			last.next = null;
			if (isEmpty()) first = last;
			else {
				oldlast.next = last;
				last.before = oldlast;
			}
			size++;
		}
	}
	
	public Item removeFirst() {
		if (isEmpty()) throw new java.util.NoSuchElementException();
		else {
			Node oldfirst = first;
			first = oldfirst.next;
			if (size == 2) {
				last.before = null;
			}
			size--;
			return oldfirst.item;
		}
	}
	
	public Item removeLast() {
		if (isEmpty()) throw new java.util.NoSuchElementException();
		else {
			Node oldlast = last;
			last = oldlast.before;
			if (size != 1) 	last.next = null;
			else first = last;
			size--;
			return oldlast.item;
		}

	}
	
	public Iterator<Item> iterator() {
		return new DequeIterator();
	}
	
	private class DequeIterator implements Iterator<Item> {
		private Node current = first;
		@Override
		public boolean hasNext() {
			return (current != null);
		}
		
		@Override
		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
		
		@Override
		public Item next() {
			if (current == null) {
				throw new java.util.NoSuchElementException();
			}
			else {
				Item item = current.item;
				current = current.next;
				return item;
			}
		}
	}
	
	public static void main(String[] args) {
		Deque deque = new Deque();
		System.out.println(deque.iterator().hasNext());
	}
}
