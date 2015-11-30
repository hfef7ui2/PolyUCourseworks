public class Q3_DLLToBBSTree<T> {
	//this class is actually a double linked list class
	//it contains a method to transform itself into balanced binary search tree then return the root of the tree
	public DoubleLinkedNode<T> head = null;
	public DoubleLinkedNode<T> tail = null;
	public DoubleLinkedNode<T> root = null;
	
	//Start of my answer
	//This method transform the double linked list into balanced binary search tree
	//and returns the root of the tree
	public BBSTNode<T> dLLtoBBST() {
		//if the list is empty, return null
		if(head == null)
			return null;
		
		//if the list contains only one element, return that element, which should be the root of the tree
		if(head == tail)
			return new BBSTNode<T>(head.data);
		
		int size = 0;
		DoubleLinkedNode<T> cur = head;
		
		//count the size of the list
		while(cur != null){
			size++;
			cur = cur.next;
		}
		
		DoubleLinkedNode<T> temp = head;//save a copy of head, as it will be changed in later operation
		BBSTNode<T> root = dLLtoBBST(0, size);
		head = temp;//restore the original value of head		
		
		return root;
	}
	
	public BBSTNode<T> dLLtoBBST(int begin, int end){
		//base case
		if(begin >= end)
			return null;
		
		int mid = (begin + end) / 2;
		
		//recursively construct left sub-tree
		//and returns the root of left sub-tree as the left child of current node
		BBSTNode<T> left = dLLtoBBST(begin, mid);
		
		BBSTNode<T> cur = new BBSTNode<T>(head.data);//initialize current tree node
		head = head.next;
		
		//recursively construct right sub-tree
		//and returns the root of right sub-tree as the right child of current node		
		BBSTNode<T> right = dLLtoBBST(mid + 1, end);
		
		//connect left and right children of current node
		cur.left = left;
		cur.right = right;
		
		return cur;
	}
	//End of my answer
	
	//helpful method to build the double linked list
	public void add(T data) {
		DoubleLinkedNode<T> nd = new DoubleLinkedNode<T>(data);
		if (head == null) {
			head = tail = nd;
		} else {
			head.pre = nd;
			nd.next = head;
			head = nd;
		}
	}
}

//Node of the double linked list
class DoubleLinkedNode<T> {
	T data;
	DoubleLinkedNode<T> next;
	DoubleLinkedNode<T> pre;

	public DoubleLinkedNode(T data) {
		this.data = data;
		this.next = null;
		this.pre = null;
	}
}

//Node of the constructed balanced binary search tree
class BBSTNode<T> {
	T data;
	BBSTNode<T> left;
	BBSTNode<T> right;
	
	public BBSTNode(T data) {
		this.data = data;
		this.left = null;
		this.right = null;
	}
}
