import java.util.*;
import java.security.SecureRandom;
 
public class SortingTester {
     
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
        
        for(int i = 0; i < size; i++) System.out.print(arr[i] + ", ");
        
        Q2_Heap.heapSort(arr);
        System.out.println("");
        
        for(int i = 0; i < size; i++) System.out.print(arr[i] + ", ");
        
        keyboard.close();
    }
}