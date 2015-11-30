/*
 * Question 2
 * ZHENG Hongyi
 * 13104036D
 * 2015/11/2
 */

public class Q2_MergeSort {	
	public static void sort(int[] arr){
		int size = arr.length;
		int[] arr2 = new int[size/2];//use n/2 extra space
		
		for(int i = 1; i < size; i = i * 2){//from bottom to up
			for(int k = 0; k < size - 1 ; k = k + 2 * i)
				arr = merge(arr, arr2, k, (k + i >= size) ? size - 1 : k + i,//if k + i is out of bound, use last element as mid 
						(k + 2 * i >= size) ? (size - 1) : (k + 2 * i - 1));//if k + 2 * i is out of bound, use last element as hi
		}
	}
	
	private static int[] merge(int[] arr, int[] arr2, int lo,int mid, int hi){
		int i = mid;
		
		//put the element after mid(including) into temp array, which should be less than n/2
		for(int k = 0; k < hi - mid + 1;)
			arr2[k++] = arr[i++];
		
		
		int lVal = mid - 1;//largest element in left part of original array
		int rVal = hi - mid;//largest element in right(restored in temp) array
		
		int newVal = hi;//start comparing and filling elements from largest
		
		while(lVal > lo - 1 || rVal > -1){		
			if(lVal == lo - 1)
				arr[newVal--] = arr2[rVal--];
			else if(rVal == -1)
				arr[newVal--] = arr[lVal--];
			else if(lVal == lo - 1 && rVal == -1)
				break;
			else if(arr2[rVal] >= arr[lVal])
				arr[newVal--] = arr2[rVal--];
			else if(arr2[rVal] < arr[lVal])
				arr[newVal--] = arr[lVal--];
		}
		return arr;	
	}
}