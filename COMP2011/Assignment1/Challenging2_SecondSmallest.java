/*
 * COMP2011
 * Challenging question 2
 * SecondSmallest
 * ZHENG Hongyi
 * 13104036D
 * 2015/9/26
 */

package comp2011.ass1;

import java.util.*;
import java.security.SecureRandom;

public class SecondSmallest {
 
	/*My answer part 1*/
    public static Node divideAndConquer(int[ ] arr){
    	
    	Node node=new Node();	//initialize one node of the tree
    	
    	/*escape condition: when the array length is 1, make a leaf node*/
    	if(arr.length == 1){
    		node.body = arr[0];
    		return node;
    	}
    	
    	/*divide the input array into two*/
    	int[] arrB = new int[arr.length / 2];
    	int[] arrC = new int[arr.length - arr.length / 2];
    	for(int i = 0;i < arr.length / 2;i++){
    		arrB[i] = arr[i];
    	}
    	for(int j = 0;j < arr.length - arr.length / 2;j++){
    		arrC[j] = arr[arr.length / 2+j];
    	}
    	
    	/*start recursive, divide the question into half each time*/
    	node.left = divideAndConquer(arrB);
    	node.right = divideAndConquer(arrC);
    	
    	/*filter the smaller one in the children and put it into node value*/
    	node.body = node.left.body < node.right.body ? node.left.body : node.right.body;
    	
    	return node;
    	
    }
    
    /*My answer part 2*/
    public static int secondSmallest(int[] arr){
    	
    	/*find smallest number, which is the root node*/
    	Node min = divideAndConquer(arr);
    	
    	/*assign the children which is not equal to minimum to second smallest*/
       	int secondMin = min.left.body == min.body ? min.right.body : min.left.body;
    	int temp;
    	
    	/* 
    	 * compare all the element which have been compared with minimum
    	 * find the smallest one among them
    	 * it is the second smallest among all the input elements
    	 */
    	while(min.left != null && min.left.left != null){
    		min=min.left.body == min.body ? min.left : min.right;
    		temp=min.left.body == min.body ? min.right.body : min.left.body;
    		if(temp < secondMin)
    			secondMin = temp;
    	}
    	
       	return secondMin;
    }
     
    public static void main(String a[]){
        Scanner keyboard = new Scanner(System.in);
        int size = 0;
        boolean b = true;
        while (b) {
            System.out.println("how many numbers you want to test (10~20): ");
            size = keyboard.nextInt();
            b = (size < 10 || size > 20);
        }
        
        int[] arr = new int[size];
        System.out.println("Press enter to fill the array");
        keyboard.nextLine(); //to fill out the enter press after inputting the size. 
        keyboard.nextLine();
        
        SecureRandom random = new SecureRandom();
        
        for(int i = 0; i < size; i++){
        	arr[i] = random.nextInt(100);
        }

        System.out.println("Here is the new array:");
        for(int k = 0;k < size;k++){
            System.out.print(arr[k] + ",");
        }

        System.out.println();
        System.out.println("Press input Enter to find the min");
        
        int min=secondSmallest(arr);
        
       	System.out.print("The second smallest number is "+min);

        keyboard.close();
    }
}

class Node {
	int body;
	Node left;
	Node right;
}