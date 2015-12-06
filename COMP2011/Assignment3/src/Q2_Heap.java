import java.security.SecureRandom;
import java.util.Scanner;

public class Q2_Heap {
	//as it is in-place sorting, the operation is put in original array directly
	public static void heapSort(int[] arr){
		
		int size = arr.length;
		int temp, i, j, k;
		
		//following for-loop make the array into binary heap
		//however, element starts from arr[0], other than arr[1]
		//it is because it is in-place algorithm and we can not extant the length of array
		//hence, we just imagine that the heap start from arr[1]
		//every time we access the array, we deduct the index by 1
		for(i = size ; i >= 1 ; i--){
			j = i;
			while(2 * j <= size){
				k = 2 * j;
				if(k < size && arr[k - 1] < arr[k])
					k++;
				if(arr[j - 1] < arr[k - 1]){
					temp = arr[j - 1];
					arr[j - 1] = arr[k - 1];
					arr[k - 1] = temp;
				}
				j = k;
			}
		}
		
		int N = size;    //N indicate the size of unsorted array
		
		while(N > 1){
			
			temp = arr[N - 1];    //exchange the top element (max element in unsorted part) with the last unsorted element
			arr[N - 1] = arr[0];
			arr[0] = temp;
			
			j = 1;
			N--;    //each time one element is sorted, decrease N by 1
			
			//following while-loop down the top element through the unsorted part of the heap
			//as N indicates the unsorted part of heap, this operation will not disorder the part which has been sorted
			while(2 * j <= N){   
				k = 2 * j;
				if(k < N && arr[k - 1] < arr[k])
					k++;
				if(arr[j - 1] < arr[k - 1]){
					temp = arr[j - 1];
					arr[j - 1] = arr[k - 1];
					arr[k - 1] = temp;
				}
				j = k;
			}
		}
	}
	
    public static void main(String a[]){
        Scanner keyboard = new Scanner(System.in);
        int size = 0;
        boolean b = true;
        while (b) {
            System.out.println("how many numbers you want to test (10~200): ");
            size = keyboard.nextInt();
            b = (size < 10 || size > 200);
        }
        
        int[] arr = new int[size];
 
        System.out.println("Press enter to start step 3: 10 randomized arrays");
        keyboard.nextLine();
        SecureRandom random = new SecureRandom();
        
        for(int i = 0; i < size; i++) arr[i] = random.nextInt(500);
        System.out.print("Before sorting: ");
        for(int i = 0; i < size; i++) System.out.print(arr[i] + ", ");
        
        heapSort(arr);
        System.out.println("");
        System.out.print("After sorting: ");
        for(int i = 0; i < size; i++) System.out.print(arr[i] + ", ");
        
        keyboard.close();
    }

}
