import java.util.LinkedList;

public class MessageQueue{
	private LinkedList<String> list = new LinkedList<String>();
	private int size = 0;
	
	public synchronized void push(String e) {
		size++;
		list.addLast(e);
	}

	public synchronized String pop() {
		size--;
		return list.removeFirst().toString();
	}
	public synchronized boolean empty() {
		boolean flag = false;
		if(size==0){
			flag = true;
		}
		return flag;
	}
	
	public synchronized int size(){
		return size;
	}
	
	public synchronized void clear() {
		list.clear();
		size = 0;
	}
}
