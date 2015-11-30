/*
 * Mandate question 2
 * BinarySort
 * BinarySort
 * ZHENG Hongyi
 * 13104036D
 * 2015/9/26
 */

package comp2011.ass1;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;

public class BinarySort {
	
	/*My answer*/
	public static void binarySort (Student[] arr) {
    	int left = 0;
    	int right = 99;
    	int tempId = 0;
    	String tempName;
    	
    	while (true) {		//repeat n/2 times in the worst case
    		
    		/*find the left most male*/
    		while(arr[left].gender == 0&&left < 99)
    			left++;		
    		
    		/*find the right most female*/
    		while(arr[right].gender == 1&&right > 0)
    			right--;		
    		
    		/*whether the searching is done*/
    		if(left >= right)		
    			break;
    		
    		/*swap the left most male and right most female*/
    		tempId = arr[left].id;
    		tempName = arr[left].name;		
    		arr[left].id = arr[right].id;		
    		arr[left].name = arr[right].name;		
    		arr[right].id = tempId;		
    		arr[right].name = tempName;		
    		arr[left].gender = 0;		
    		arr[right].gender = 1;	
    	}
    	
    }
    
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        int size = 100;
        Student[] arr = new Student[size];
        // build 100 students with random id and gender.
        for (int i= 0; i < size; i++ ) {
            int id = Math.abs(random.nextInt());
            arr[i] = new Student(id, (byte) (id % 2));
        }
        binarySort(arr);
        System.out.println(Arrays.stream(arr).
                map(Student::toString).
                collect(Collectors.joining("\n ")));
    }
}

class Student {
    int id;
    String name;
    byte gender; // 0 for girls and 1 for boys. 
    
    public Student (int id, byte gender) {
        this.id = id;
        this.gender = gender;
    }
    
    public String toString () {
        return (gender == 0? "female":"male") + " student " + id;
    }
}