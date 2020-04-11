class PQNode<T> {
	public T data;
	public int priority;
	public PQNode<T> next;

	public PQNode() {
		next = null;
	}

	public PQNode(T e, int p) {
		data = e;
		priority = p;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public PQNode<T> getNext() {
		return next;
	}

	public void setNext(PQNode<T> next) {
		this.next = next;
	}

}

public class PQ<T> {
	private int size;
	private PQNode<T> head;

	public PQ() {
		head = null;
		size = 0;
	}

	public int length() {
		return size;
	}

	public boolean full() {
		return false;
	}

	public void enqueue(T e, int pty) {
		PQNode<T> tmp = new PQNode<T>(e, pty);
		if ((size == 0) || (pty <= head.priority)) {
			tmp.next = head;
			head = tmp;
		} else {
			PQNode<T> p = head;
			PQNode<T> q = null;
			while ((p != null) && (pty > p.priority)) {
				q = p;
				p = p.next;
			}
			tmp.next = p;
			q.next = tmp;
		}
		size++;
	}


	public PQNode<T> serve() {
		PQNode<T> node = head;
		PQNode<T> pqe = new PQNode<T>(node.data, node.priority);
		head = head.next;
		size--;
		return pqe;
	}

	public void printall() {
		int i = 0;
		PQNode<T> temp = head;

		while (i <= size) {
			System.out.println(temp.data + " , " + temp.priority);
			temp = temp.next;
			i++;

		}
	}

	public PQNode<T> peek() {

		return head;

	}

}