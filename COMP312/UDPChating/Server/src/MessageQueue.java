import java.util.LinkedList;
/***
 * ���У����йؼ������ݽṹ��LinkedList
 * Ϊʲô����jdk�Դ���ʵ����Queue�ӿڵ�LinkedList�࣬��Ҫ�Լ�����дһ���ԭ����
 * ע�⣬��ʵ�ֲ���ͬ���ġ��������߳�ͬʱ����һ�������б�����������һ���̴߳ӽṹ���޸��˸��б��������� �����ⲿͬ����
 * ���ṹ�޸�ָ��ӻ�ɾ��һ������Ԫ�ص��κβ�����������Ԫ�ص�ֵ���ǽṹ�޸ġ���
 * ��һ��ͨ������Ȼ��װ���б�Ķ������ͬ�����������
 * ��ˣ�Ϊ���������ֱ��д������߳�ͬ����Queue������
 * @author xiayi and zhujiadun
 * time:2010��10��27��14:34:51
 */
public class MessageQueue{
	private LinkedList<String> list = new LinkedList<String>();
	private int size = 0;
	
	public synchronized void push(String e) {
		size++;
		list.addLast(e);
	}
	// ʹ��removeFirst()���������ض����е�һ�����ݣ�Ȼ�����Ӷ�����ɾ��
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
