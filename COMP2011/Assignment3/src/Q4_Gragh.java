public class Q4_Gragh {
	public static void myAnswer(Graph g){
		//n is the number of vertices
		int n = g.getVerticeNumber();                                      //O(1)
		//make n flags
		Flag[] flag = new Flag[n];                                         //O(1)
		for(int i = 0; i < n; i++)                                         //n times
			flag[i] = new Flag(i);                                         //O(1) each loop
		//make a ladder, which has n levels
		Ladder ladder = new Ladder(n);                                     //O(1)
		//set all labels as 0 (put all flags on level 0 of the ladder)
		for(int i = 0; i < n; i++)                                         //n times
			ladder.addAtBottom(flag[i]);                                   //O(1) each loop (Key operation 1)
		for(int i = n; i > 0; i--){                                        //n times
			//choose an an unnumbered vertex with largest label
			Flag curFlag = ladder.popHighestFlag();                        //O(1) each loop (Key operation 2)
			AdjacencyList curList = g.getAdjacencyList(curFlag.getIndex());//O(1) each loop
			//set number as i
			curFlag.setNumber(i);                                          //O(1) each loop
			System.out.println("The highest label vertex: vertex " 
					+ curFlag.getIndex() + " is picked");                  //O(1) each loop
			GraphNode curNode = curList.getHead();                         //O(1) each loop
			while(curNode != null){                                        //2m times totally(through for-loop)
				int j = curNode.getNodeNumber();                           //O(1) each loop
				//for each unnumbered neighbor, increase its label by 1
				if(flag[j].getNumber() == null){                           //O(1) each loop
					ladder.raiseFlag(flag[j]);                             //O(1) each loop (Key operation 3)
					System.out.println("The label of vertex " + j 
							+ " is increase by 1");                        //O(1) each loop
				}
				curNode = curNode.getNext();                               //O(1) each loop
			}
		}
		//print result for your reference
		System.out.println("\nResult:");
		for(int i = 0; i < n; i++){
			System.out.println("Vertex " + i + " : Label " + flag[i].getCurrentLabel() 
					+ " / Number " + flag[i].getNumber());
		}
	}

	public static void main(String[]arg){
		Graph g = new Graph(4); 
		g.adEdge(0, 1);
		g.adEdge(0, 2);
		g.adEdge(0, 3);
		g.adEdge(1, 2);
		g.adEdge(2, 3);
		g.adEdge(3, 1);
		System.out.println("Start\n");
		myAnswer(g);
	}
}

//following data structures are designed by me
class Flag{
	Integer number;
	int index;
	Flag pre;
	Flag next;
	Integer label;
	Level level;
	
	public Flag(int i){
		number = null;
		index = i;
		label = null;
		level = null;
		pre = next = null;
	}
	
	public void setNumber(int i){
		number = i;
	}

	public int getIndex(){
		return index;
	}

	public Integer getNumber(){
		return number;
	}

	public void setLabel(int l){
		label = l;
	}

	public void disconnect(){
	    //if it is the only Flag on its level
		if(pre == null && next == null){
			level.releaseHead();
		}
		//it is the head and is not the only Flag
		else if(pre == null && next != null){
			next.pre = null;
			level.setHead(next);
			next = null;
		}
		//it is not the head
		else if(pre != null && next != null){  
			pre.next = next;
			next.pre = pre;
			next = pre = null;
		}
	}

	public void setLevel(Level l){
		level = l;
	}

	public int getCurrentLabel(){
		return label;
	}
}


class Ladder{
	Level[] level;
	LevelRecord unEmptyLevel;
	Integer currentHighestLevel;
	
	public Ladder(int fullSize){
		level = new Level[fullSize + 1];
		for(int i = 0; i < fullSize + 1; i++)
			level[i] = new Level();
		currentHighestLevel = null;
		unEmptyLevel = new LevelRecord(fullSize);
	}
	
	public void addAtBottom(Flag f){
		//make the level record
		if(currentHighestLevel == null){
			currentHighestLevel = 0;
			unEmptyLevel.addHead();
		}
		level[0].add(f);
		f.setLabel(0);
		f.setLevel(level[0]);
	}

	public Flag popHighestFlag(){
		//pop one flag from highest level
		Flag temp = level[currentHighestLevel].popFlag();	
		temp.setLevel(null);
		//if currentHighestLevel is empty, remove its record and set current HighestLevel to the new highest level
		if(level[currentHighestLevel].isEmpty()){     
			unEmptyLevel.remove(currentHighestLevel);
			currentHighestLevel = unEmptyLevel.last();
		}
		return temp;
	}
	
	public void raiseFlag(Flag f){
		int currentLabel = f.getCurrentLabel();
		//disconnect the flag from current list
		f.disconnect();
		//increase label by 1
		f.setLabel(currentLabel + 1);
		//add flag to 1 higher level
		level[currentLabel + 1].add(f);
		//update the non-empty level record
		unEmptyLevel.addAfter(currentLabel);
		if(level[currentLabel].isEmpty())
			unEmptyLevel.remove(currentLabel);
		if(++currentLabel > currentHighestLevel)
			currentHighestLevel = currentLabel;
		f.setLevel(level[currentLabel]);
	}
	
	class LevelRecord{
		RecordNode[] quickAccess;
		RecordNode head;
		RecordNode tail;
		
		class RecordNode{
			Integer data;
			RecordNode pre;
			RecordNode next;
			public RecordNode(int i) {
				data = i;
				pre = next = null;
			}
		}
		
		public LevelRecord(int size) {
			head = tail = null;
			quickAccess = new RecordNode[size];
			for(int i = 0; i < size; i++)
				quickAccess[i] = new RecordNode(i);
		}
		
		public void addHead(){
			head = tail = quickAccess[0];
		}
		
		public void addAfter(int i){
			RecordNode cur = quickAccess[i];
			
			if(cur.next == null){
				cur.next = quickAccess[i + 1];
				cur.next.pre = cur;
				tail = cur.next;
				return;
			}
			if(cur.next.data == (i + 1)){
				return;
			}
			RecordNode temp = quickAccess[i + 1];
			
			temp.pre = cur;
			temp.next = cur.next;
			cur.next.pre = temp;
			cur.next = temp;
			return;
		}
		
		public void remove(int i){
			if(head == null) 
				return;
			if(head == tail){
				head = tail = null;
				return;
			}
			RecordNode cur = quickAccess[i];
			if(cur.pre == null){
				head = cur.next;
				head.pre = null;
				cur.next = null;
				return;
			}
			if(cur.next == null){
				tail = cur.pre;
				tail.next = null;
				cur.pre = null;
				return;
			}
			cur.pre.next = cur.next;
			cur.next.pre = cur.pre;
			cur.pre = cur.next = null;
			return;
		}
		
		public Integer last(){
			if(tail == null)
				return null;
			return tail.data;
		}
		
	}
}

class Level{
	Flag headFlag;
	
	public Level(){
		headFlag = null;
	}

	public boolean isEmpty(){
		return headFlag == null;
	}

	public void add(Flag f){
		if(isEmpty())
			headFlag = f;
		else{
			headFlag.pre = f;
			f.next = headFlag;
			headFlag = f;
		}
	}

	public Flag popFlag(){
		Flag temp = headFlag;
		if(headFlag == null || headFlag.next == null)
			headFlag = null;
		else
			headFlag = headFlag.next;
		return temp;
	}

	public void releaseHead(){
		headFlag = null;
	}

	public void setHead(Flag f){
		headFlag = f;
	}
}

//following are simple data structure of Graph
class Graph{
	AdjacencyList[] list;
	int verticeNumber;
	int edgeNumber;

	public Graph(int v){
		verticeNumber = v;
		edgeNumber = 0;
		list = new AdjacencyList[v];
		for(int i = 0; i < v; i++)
			list[i] = new AdjacencyList();
		System.out.println("A " + v + " vertice graph is made");
	}

	public void adEdge(int i, int j){
		list[i].add(j);
		list[j].add(i);
		System.out.println("Edge " + i + "-" + j + " is made");
		edgeNumber++;
	}

	public int getVerticeNumber(){
		return verticeNumber;
	}

	public int getEdgeNumber(){
		return edgeNumber;
	}

	public AdjacencyList getAdjacencyList(int i){
		return list[i];
	}
	
	public int size(){
		return list.length;
	}
}

class AdjacencyList{
	GraphNode headNode;
	
	public AdjacencyList(){
		headNode = null;
	}
	
	public GraphNode getHead(){
		return headNode;
	}
	
	public void add(int i){
		GraphNode nd = new GraphNode(i);
		if(headNode == null)
			headNode = nd;
		else{
			nd.next = headNode;
			headNode = nd;
		}
	}
}

class GraphNode{
	int nodeNumber;
	GraphNode next;

	public GraphNode(int num){
		nodeNumber = num;
		next = null;
	}

	public boolean hasNext(){
		return next != null;
	}

	public GraphNode getNext(){
		return next;
	}

	public int getNodeNumber(){
		return nodeNumber;
	}
}