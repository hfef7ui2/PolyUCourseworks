/*
 * COMP2011
 * Mandate question 1
 * BinarySearch
 * ZHENG Hongyi
 * 13104036D
 * 2015/9/26
 */

package comp2011.ass1;

import java.util.*;
import java.security.SecureRandom;

public class BinarySearch {
 
	/*My answer*/
    public static int binarySearch( int [ ] arr,int n,int key){
    	int l = 0;
    	int m = 0;
    	while(l <= n){
    		m=(l + n) / 2;
    		if(arr[m] > key)
    			n = m - 1;
    		else if(arr[m]<key)
    			l = m + 1;
    		else
    			return m;
    	}
    	return -1;
    }
     
    public static void main(String a[]){
        Scanner keyboard = new Scanner(System.in);
        int size = 0;
        boolean b = true;
        while (b) {
            System.out.println("how many numbers you want to test (10~50): ");
            size = keyboard.nextInt();
            b = (size < 10 || size > 50);
        }
        
        int[] arr = new int[size];
        arr[0]=1;
        System.out.println("Press enter to fill the array");
        keyboard.nextLine(); //to fill out the enter press after inputting the size. 
        keyboard.nextLine();
        
        SecureRandom random=new SecureRandom();
        
        for(int i = 1; i < size; i++){
        	arr[i] = arr[i-1] + random.nextInt(3) + 1;
        }

        System.out.println("Here is the new array:");
        for(int k = 0;k < size;k++){
            System.out.print(arr[k] + ",");
        }

        System.out.println();
        System.out.println("Press input the key that you want to find");
        int key=keyboard.nextInt();
        
        int position=binarySearch(arr,size-1,key);
        
        if(position==-1)
        	System.out.print("The key is not in the array");
        else
        	System.out.print("The key is at the " + (position+1)
        			+ " position from left to right. (the first is 1 position)");

        keyboard.close();
    }
}