/*
 * Question 3
 * ZHENG Hongyi
 * 13104036D
 * 2015/11/2
 */

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;

class Node<T> {
    public T data;
    public Node<T> next;
    Node (T data) {
        this.data = data; 
    }
}

class Task {
    Date deadline;
    String desc;
    SimpleDateFormat input = new SimpleDateFormat("MMdd hh");
    SimpleDateFormat output = new SimpleDateFormat("EEE, MMM dd, hh:mm aaa");
    
    public Task (Date deadline, String desc) {
        this.deadline = deadline;
        this.desc = desc;
    }
    
    public Task (String deadline, String desc) {
        try {
            this.deadline = input.parse(deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.desc = desc;
    }

    public int compareTo(Task t) {
        return deadline.compareTo(t.deadline);
    }
    
    public String toString() {
        return desc + " before " + output.format(deadline);
    }
    
    public static void main(String args[]) {
        Task t = new Task("1006 19", "proposal"); 
        System.out.print(t);
    }
}

public class Q3_LinkedList<T> {
    Node<T> head; //firstNode
    Node<T> tail; //lastNode

    public Q3_LinkedList() {
        head = tail = null;
    }
    
    public boolean isEmpty() { 
        return tail == null; //or head == null 
    }
    
    public void insertAtFront(T element) {
        Node<T> newNode = new Node<T>(element);
        newNode.next = head;
        head = newNode;
        if (tail == null) tail = head;
    }    
    
    public void insertAtEnd(T element) {
        Node<T> newNode = new Node<T>(element);
        if (head == null) {
            head = tail = newNode;
            newNode.next = null;
        }
        else {
            newNode.next = null;
            tail.next = newNode;
            tail = newNode;
        }
    }
    
    public T deleteFirst() {
        if (head == null) 
            return null;
        Node<T> temp = head;
        if (head.next == null) 
            head = tail = null;
        else 
            head = head.next;
        return temp.data;
    }
    
    public T deleteLast() {
        if (head == null) 
            return null;
        if (head.next == null) {
            Node<T> temp = head;
            head = tail = null; // head = null;  
            return temp.data;
        }
        Node<T> curNode = head;
        Node<T> nextNode = head.next;
        while ( nextNode.next != null ) {
            curNode = nextNode;
            nextNode = curNode.next;
        }
        tail = curNode;
        curNode.next= null;
        return nextNode.data;
    }
    

/****** My answer STARS from here******/
    public static void mergeSort(Q3_LinkedList<Task> l) { 
    	mergeSort(l.head);
    }
    
    public static Node<Task> mergeSort(Node<Task> head) {
     
    		if (head == null || head.next == null)
    			return head;
     
    		// count total number of elements
    		int count = 0;
    		Node<Task> p = head;
    		while (p != null) {
    			count++;
    			p = p.next;
    		}
     
    		// break list into half and half
    		int middle = count / 2;
     
    		Node<Task> l = head, r = null;
    		Node<Task> p2 = head;
    		int countHalf = 0;
    		while (p2 != null) {
    			countHalf++;
    			Node<Task> next = p2.next;
     
    			if (countHalf == middle) {
    				p2.next = null;
    				r = next;
    			}
    			p2 = next;
    		}
     
    		// recursively sort each part
    		Node<Task> h1 = mergeSort(l);
    		Node<Task> h2 = mergeSort(r);
     
    		// merge
    		Node<Task> merged = merge(h1, h2);
     
    		return merged;
    	}
     
    public static Node<Task> merge(Node<Task> l, Node<Task> r){
    	Node<Task> lp = l, rp = r;
    	Node<Task> newhead = new Node<Task>(new Task("0000 00", "fake task"));
    	Node<Task> cur = newhead;
    	
        while(lp!=null || rp!=null){
            if(lp==null){
                cur.next = rp;
                break;
            }else if(rp==null){
                cur.next = lp;
                break;
            }else{
                if(lp.data.compareTo(rp.data) <= 0){
                    cur.next = lp;
                    lp = lp.next;
                    cur = cur.next;
                }else {
                    cur.next = rp;
                    rp = rp.next;
                    cur = cur.next;
                }
            }
        }
        return newhead.next;
    }
    
    public String toString() {
        if (head == null) return "The list is empty.";
        StringBuilder sb = new StringBuilder();
        Node<T> cur = head;
        while ( cur != null ) {
            sb.append(cur.data + "\n");
            cur = cur.next;
        }
        return sb.toString();
    }
    /****** My answer ENDS here******/
    
    public static void main(String[] args) {
        Q3_LinkedList<Task> list = new Q3_LinkedList<Task>();

        list.insertAtEnd(new Task("1006 23", "project for COMP2011"));
        list.insertAtEnd(new Task("1011 18", "mid-term exam paper of COMP201"));
        list.insertAtEnd(new Task("1015 12", "soda paper camera-ready version"));
        list.insertAtEnd(new Task("1008 23", "mid-term exam paper of COMP2011"));
        list.insertAtEnd(new Task("1017 23", "IandC paper revision"));
        list.insertAtEnd(new Task("1014 18", "final exam paper of COMP2011"));
        list.insertAtEnd(new Task("1006 19", "proposal"));
        list.insertAtEnd(new Task("1014 18", "final exam paper of COMP201"));
        list.insertAtEnd(new Task("1008 8", "lecture 6 slides of COMP2011"));
        System.out.println("Before sorting:");
        System.out.println(list);
        mergeSort(list);
        System.out.println("After sorting:");
        System.out.println(list);
        
    }
}


