import java.util.TreeSet;

public class Q4_Gragh {
	public static void myAnswer(Graph g){
		
		//n is the number of vertices
		int n = g.getVerticeNumber();    //O(1)

		//each node is corresponding to one flag
		//one flag records the vertice number, label and number of one vertice
		Flag[] flag = new Flag[n];       //O(1)
		
		for(int i = 0; i < n; i++)       //O(n) totally
			flag[i] = new Flag(i);       //O(1) each loop

		//make a ladder, which has n levels
		//all flags are placed on the levels
		//the label number of the flag is always equals to its level number
		Ladder ladder = new Ladder(n);   //O(1)

		//put all 
		for(int i = 0; i < n; i++)       //O(n) totally
			ladder.add(flag[i], 0);      //O(1) each loop

		for(int i = n; i > 0; i--){      //n times, O(n + m) totally (including the cost of while-loop inside)
			Flag curFlag = ladder.popHighestFlag();    //O(1) each loop
			curFlag.setNumber(i);                      //O(1) each loop

			AdjacencyList curList = g.getAdjacencyList(curFlag.getIndex());//O(1) each loop
			
			System.out.println("get" + curFlag.getIndex());//O(1) each loop

			GraphNode curNode = curList.getHead();//O(1) each loop

			while(curNode != null){//m times totally(through the whole for-loop), O(m) totally

				int j = curNode.getNodeNumber();//O(1) each loop

				if(flag[j].getNumber() == null){//O(1) each loop
					ladder.raiseFlag(flag[j]);//O(1) each loop
					System.out.println("raise" + j);//O(1) each loop
				}

				curNode = curNode.getNext();//O(1) each loop
			}
		}
	}

	public static void main(String[]arg){
		Graph g = new Graph(4); 
		g.adEdge(0, 1);
		g.adEdge(1, 2);
		g.adEdge(2, 3);
		g.adEdge(3, 0);
		myAnswer(g);
	}
}

class Flag{
	Integer number;
	int index;
	Flag pre;
	Flag next;
	Integer label;
	Level level;
	public Flag(int i){//1
		number = null;
		index = i;
		label = null;//indicates no label
		level = null;
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
		if(pre == null && next == null){//it is the only Flag on its level
			level.releaseHead();
		}
		else if(pre == null && next != null){
			next.pre = null;
			level.setHead(next);
			next = null;
		}
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
	TreeSet<Integer> unEmptyLevel;
	Integer currentHighestLevel;
	public Ladder(int fullSize){
		level = new Level[fullSize + 1];
		for(int i = 0; i < fullSize + 1; i++)
			level[i] = new Level();
		currentHighestLevel = null;
		unEmptyLevel = new TreeSet<Integer>();
	}

	public void add(Flag f, int levelNum){
		unEmptyLevel.add(levelNum);
		if(currentHighestLevel == null || currentHighestLevel < levelNum)
			currentHighestLevel = levelNum;
		level[levelNum].add(f);
		f.setLabel(levelNum);
		f.setLevel(level[levelNum]);
	}

	public Flag popHighestFlag(){
		Flag temp = level[currentHighestLevel].popFlag();
		temp.setLevel(null);
		if(level[currentHighestLevel].isEmpty()){
			unEmptyLevel.remove(currentHighestLevel);
			currentHighestLevel = unEmptyLevel.last();
		}
		return temp;
	}
	
	public void raiseFlag(Flag f){
		int currentLabel = f.getCurrentLabel();
		f.disconnect();
		if(level[currentLabel].isEmpty())
			unEmptyLevel.remove(currentLabel);
		currentLabel++;
		unEmptyLevel.add(currentLabel);
		level[currentLabel].add(f);
		f.setLabel(currentLabel);
		f.setLevel(level[currentLabel]);
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
	}

	public void adEdge(int i, int j){
		list[i].add(j);
		list[j].add(i);
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

