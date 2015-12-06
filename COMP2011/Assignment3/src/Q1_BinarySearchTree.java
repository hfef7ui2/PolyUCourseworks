import java.security.SecureRandom;
import java.util.ArrayList;

public class Q1_BinarySearchTree<T> {
	TreeNode<T> root;
	public Q1_BinarySearchTree() {
		root = null; 
	} 

	//My answer: recursive version part 1
	public int countLeavesR(){
		if(root == null)
			return 0;
		return countLeavesR(root);
	}
	
	//My answer: recursive version part 2
	public int countLeavesR(TreeNode<T> nd){
		if(nd.leftChild == null && nd.rightChild == null)//if arrives the leave, return 1
			return 1;
		
		int leftNum = 0, rightNum = 0;//else, use two variable to record the leave number in each subtree
		if(nd.leftChild != null)
			leftNum = countLeavesR(nd.leftChild);
		if(nd.rightChild != null)
			rightNum = countLeavesR(nd.rightChild);
		
		return leftNum + rightNum;//return the total leave number in both subtrees
		//totally, this method is called N times, N is the number of nodes. In each time the method called, 
		//the cost is a constant number. Hence total cost is O(N)
	}
	
	//My answer: non-recursive version
	public int countLeavesI(){
		if(root == null)
			return 0;
		
		int count = 0;
		ArrayList<TreeNode<T>> probeList = new ArrayList<TreeNode<T>>();
		
		TreeNode<T> probe = root;//the first probe, which points to root
		probeList.add(probe);
		
		//this iteration loops H + 1 times, where H is the height of the tree (height of single node is 0).
		//it scans one level of the tree each time
		while(true){
			//this iteration loops X time, where X is the number of node in current level
			//it scans (the probe of) one node each time
			for(int i = 0; i < probeList.size(); i++){
				//if current node has both children, new another probe 
				//and put both (current and new) probes into next level
				if(probeList.get(i).leftChild != null && probeList.get(i).rightChild != null){
					TreeNode<T> temp = probeList.get(i).rightChild;
					probeList.add(temp);
					probeList.set(i, probeList.get(i).leftChild);
				}
				//else if current node has only left child, put current probe into its left child
				else if(probeList.get(i).leftChild != null && probeList.get(i).rightChild == null)
					probeList.set(i, probeList.get(i).leftChild);
				//else if current node has only right child, put current probe into its right child
				else if(probeList.get(i).rightChild != null && probeList.get(i).leftChild == null)
					probeList.set(i, probeList.get(i).rightChild);
				//else, this node should be a leave
				//delete this probe (because it dosen't has next level), and increase count by 1
				else{
					probeList.remove(probeList.get(i));
					count++;
				}
			}
			
			//if probeList is empty, it means that there is no non-leave node left
			if(probeList.size() == 0)
				break;
			//totally, it is iterated N times, N is the number of nodes. In each iteration, 
			//the cost is a constant number. Hence total cost is O(N)
			}
		return count;
	}
	
	public void insert(int key, T data) {
		System.out.println(key + "-" + data + " is inserted");
		TreeNode<T> newNode = new TreeNode<T>(key, data);
		if (root == null) { 
			root = newNode; return; 
		}
		TreeNode<T> cur = root;
		while (true) { 
			if (key < cur.key) {
				if (cur.leftChild == null) { 
					cur.leftChild = newNode; 
					return; 
				} 
				cur = cur.leftChild; 
			} 
			else {
				if (cur.rightChild == null) { 
					cur.rightChild = newNode; 
					return; 
				} 
				cur = cur.rightChild; 
			}
		}
	}
	
	public static void main(String[] arg){
		Q1_BinarySearchTree<String> test = new Q1_BinarySearchTree<String>();
		test.insert(1, "Hale");
		test.insert(5, "Balo");
		test.insert(4, "Kole");
		test.insert(12, "Hela");
		test.insert(43, "Bola");
		test.insert(23, "Moira");
		test.insert(13, "Hela");
		test.insert(42, "Bola");
		test.insert(11, "Moira");
		System.out.println("Leaves number (non-recursive): " + test.countLeavesI());
		System.out.println("Leaves number (recursive): " + test.countLeavesR());
	}
}

class TreeNode<T> {
	int key;
	T data;
	TreeNode<T> leftChild, rightChild; 
	public TreeNode(int key, T data) {
		this.key = key; this.data = data; 
	} 
	public String toString() { 
		return key + ":␣" + data;
	} 
}

