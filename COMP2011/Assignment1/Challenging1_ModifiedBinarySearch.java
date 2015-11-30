/*
 * Challenging question 1
 * ModifiedBinarySearch
 * ModifiedBinarySearch
 * ZHENG Hongyi
 * 13104036D
 * 2015/9/26
 */

package comp2011.ass1;

import java.util.*;
import java.security.SecureRandom;

public class ModifiedBinarySearch {
 
	/*My answer*/
    public static int binarySearch( int [ ] arr,int n,int key){
    	int l = 0;
    	int leftMost = 0;
    	int rightMost = 0;
    	int size = n;
    	int result;
    	
    	/*find left most key*/
    	while(l <= n){
    		leftMost = (l+n) / 2;
    		
    		/*if the probe is on the right side of the key*/
   			if(arr[leftMost] > key)		
   				n = leftMost - 1;
   			
   			/*if the probe is on the left side of the key*/
   			else if(arr[leftMost] < key)
   				l = leftMost + 1;
   			
   			/*if the probe is on the key, but the are more keys on the left*/
   			else if(leftMost > 0 && arr[leftMost - 1] == key)
   				n = leftMost - 1;
   			else
    			break;
    		}
    	
    	/*if the key is not in the array, break*/
    	if(l > n)
    		return -1;
    	l = 0;
    	n = size;
    	
    	/*find the right most key*/
   		while(l <= n){
   			rightMost = (l + n) / 2;
   			
   			/*if the probe is on the right side of the key*/
   			if(arr[rightMost] > key)
   				n = rightMost - 1;
   			
   			/*if the probe is on the left side of the key*/
   			else if(arr[rightMost] < key)
    			l = rightMost + 1;
   			
   			/*if the probe is on the key, but the are more keys on the right*/
    		else if(rightMost < size && arr[rightMost + 1] == key)
    			l = rightMost + 1;
    		else
   				break;
    		}

   		/*find the middle key*/
		result = (leftMost + rightMost) / 2;
    	return result;
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
        	arr[0] = 1;
        	
	        while (true) {         	
        	System.out.println("Press enter to fill the array");
        	keyboard.nextLine(); 
        	keyboard.nextLine();
        
        	SecureRandom random=new SecureRandom();
        
	        for(int i = 1; i < size; i++){
	        	arr[i] = arr[i - 1] + random.nextInt(2);
	        }

	        System.out.println("Here is the new array:");
	        for(int k = 0;k < size;k++){
	        	System.out.print(arr[k] + ",");
	        }
	        
	        System.out.println();
	        System.out.println("Press input the key that you want to find");
	        int key = keyboard.nextInt();
        
	        int position = binarySearch(arr,size - 1,key);
        
	        if(position == -1)
	        	System.out.print("The key is not in the array");
	        else
	        	System.out.print("The median key is at the " + (position+1) 
	        			+ " position from left to right.(First is 1 position.)");
	        System.out.print("\nInput 1 to continue/ any NUMBER else to quit.");
	        int control = keyboard.nextInt();
        	if(control != 1)
        		break;
        }
        keyboard.close();
    }
}