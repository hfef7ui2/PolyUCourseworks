public class Q3_DLLToBBSTree<T> {
	//this class is actually a double linked list class
	//it contains a method to transform itself into balanced binary search tree then return the root of the tree
	public DoubleLinkedNode<T> head = null;
	public DoubleLinkedNode<T> tail = null;
	public DoubleLinkedNode<T> root = null;
	
	public Q3_DLLToBBSTree() {
		head = tail = root = null;
		System.out.println("Double linked list is made");
	}
	
	//Start of my answer
	//This method transform the double linked list into balanced binary search tree
	//and returns the root of the tree
	public BBSTNode<T> dLLToBBST() {
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
		BBSTNode<T> root = dLLToBBST(0, size);
		head = temp;//restore the original value of head		
		
		return root;
	}
	
	public BBSTNode<T> dLLToBBST(int begin, int end){
		//base case
		if(begin >= end)
			return null;
		
		int mid = (begin + end) / 2;
		
		//recursively construct left sub-tree
		//and returns the root of left sub-tree as the left child of current node
		BBSTNode<T> left = dLLToBBST(begin, mid);
		
		BBSTNode<T> cur = new BBSTNode<T>(head.data);//initialize current tree node
		head = head.next;
		
		//recursively construct right sub-tree
		//and returns the root of right sub-tree as the right child of current node		
		BBSTNode<T> right = dLLToBBST(mid + 1, end);
		
		//connect left and right children of current node
		cur.left = left;
		cur.right = right;
		
		return cur;
	}
	//End of my answer
	
	//helpful method to build the double linked list
	public void addAtTail(T data) {
		System.out.print(data + ", ");
		DoubleLinkedNode<T> nd = new DoubleLinkedNode<T>(data);
		if (head == null) {
			head = tail = nd;
		} else {
			tail.next = nd;
			nd.pre = tail;
			tail = nd;
		}
	}
		
	public static void main(String[] arg){
		Q3_DLLToBBSTree<Integer> testList = new Q3_DLLToBBSTree<>();
		System.out.print("List elements: ");
		testList.addAtTail(1);
		testList.addAtTail(4);
		testList.addAtTail(7);
		testList.addAtTail(8);
		testList.addAtTail(11);
		testList.addAtTail(21);
		testList.addAtTail(34);
		testList.addAtTail(35);
		BBSTNode<Integer> testTree = testList.dLLToBBST();
		System.out.println("\nBalanced binary search tree is made");
		System.out.print("Inorder traversal of tree£º ");
		testTree.inorder();
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
	
	public void inorder(){
		if(left != null)
			left.inorder();
		System.out.print(data + ", ");
		if(right != null)
			right.inorder();
	}
}
