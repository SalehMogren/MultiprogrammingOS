//Node
class Node<T> {
	public T data;
	public Node<T> next;

	public Node() {
		data = null;
		next = null;
	}

	public Node(T val) {
		data = val;
		next = null;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Node<T> getNext() {
		return next;
	}

	public void setNext(Node<T> next) {
		this.next = next;
	}

}

//Queue

public class Queue<T> {
	private Node<T> head, tail;
	private int size;

	public Queue() {
		head = tail = null;
		size = 0;
	}

	public int length() {
		return size;
	}

	public void enqueue(T e) {
		if (tail == null) {
			head = tail = new Node<T>(e);
		} else {
			tail.next = new Node<T>(e);
			tail = tail.next;
		}
		size++;
	}

	public T serve() {
		T x = head.data;
		head = head.next;
		size--;
		if (size == 0)
			tail = null;
		return x;
	}

	public T peek() {

		return head.data;

	}

}