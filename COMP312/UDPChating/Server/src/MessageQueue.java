import java.util.LinkedList;
/***
 * 队列，其中关键的数据结构是LinkedList
 * 为什么不用jdk自带的实现了Queue接口的LinkedList类，而要自己在再写一遍的原因是
 * 注意，此实现不是同步的。如果多个线程同时访问一个链接列表，而其中至少一个线程从结构上修改了该列表，则它必须 保持外部同步。
 * （结构修改指添加或删除一个或多个元素的任何操作；仅设置元素的值不是结构修改。）
 * 这一般通过对自然封装该列表的对象进行同步操作来完成
 * 因此，为方便起见，直接写了这个线程同步的Queue对象类
 * @author xiayi and zhujiadun
 * time:2010年10月27日14:34:51
 */
public class MessageQueue{
	private LinkedList<String> list = new LinkedList<String>();
	private int size = 0;
	
	public synchronized void push(String e) {
		size++;
		list.addLast(e);
	}
	// 使用removeFirst()方法，返回队列中第一个数据，然后将它从队列中删除
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
