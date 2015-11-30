/*
 * Question 1
 * ZHENG Hongyi
 * 13104036D
 * 2015/11/2
 */

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Q1_BinarySort {
	
	/****** My answer STARS from here******/
	public static void binarySort (Student[] arr) {
    	int size = arr.length;

    	int left = 0;
    	int right = size - 1;
    	int tempId = 0;
    	String tempName;
    	
    	
    	while (true) {
    		
    		/*find the left most male OR brother spring*/
    		while(arr[left].gender == 0 && left < size - 1)
    			left++;		
    		
    		/*find the right most female*/
    		while((arr[right].gender == 1 || arr[right].gender == 2) && right > 0)
    			right--;		
    		
    		/*whether the searching is done*/
    		if(left >= right)		
    			break;
    		
    		/*swap the left most male/brother spring and right most female*/
    		tempId = arr[left].id;
    		tempName = arr[left].name;		
    		arr[left].id = arr[right].id;		
    		arr[left].name = arr[right].name;		
    		arr[right].id = tempId;		
    		arr[right].name = tempName;		
    		arr[left].gender = 0;		
    		arr[right].gender = 1;	
    	}

    	right = size -1;
    	if(arr[left].gender == 0)
    		left++;
    	int k = left;
    	
    	while (true) {
    		
    		/*find the left most brother spring*/
    		while(arr[left].gender == 1 && left < size - 1)
    			left++;		
    		
    		/*find the right most male*/
    		while(arr[right].gender == 2 && right > k)
    			right--;		
    		
    		/*whether the searching is done*/
    		if(left >= right)		
    			break;
    		
    		/*swap the left most brother spring and right most male*/
    		tempId = arr[left].id;
    		tempName = arr[left].name;		
    		arr[left].id = arr[right].id;		
    		arr[left].name = arr[right].name;		
    		arr[right].id = tempId;		
    		arr[right].name = tempName;		
    		arr[left].gender = 1;		
    		arr[right].gender = 2;	
    	}
    	
    }
    
    public static void main(String[] args) {
        SecureRandom random = new SecureRandom();
        int size = 42;
        Student[] arr = new Student[size];
        // build 100 students with random id and gender.
        for (int i= 0; i < size; i++ ) {
            int id = Math.abs(random.nextInt());
            arr[i] = new Student(id, (byte) (id % 3));
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
    byte gender; // 0 for girls and 1 for boys and 2 for Brother Spring
    
    public Student (int id, byte gender) {
        this.id = id;
        this.gender = gender;
    }
    
    public String toString () {
        return (gender == 0? "female":gender == 1? "male":"Brother Spring") + " student " + id;
    }
}