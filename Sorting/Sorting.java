package sorter;
import java.util.*;

/**
 * This program implements different types of sorting methods to 
 * sort an ArrayList of numbers and an ArrayList of strings.
 *
 * @author Madhuri Manohar
 */
public class Sorting {
	
	private void sop(Object x) {
		System.out.println(x);
	}

	/**
	 * This method implements a simple sorting algorithm that repeatedly compares two elements
	 * starting from the beginning. It compares each pair of adjacent elements and swaps them if
	 * they're in the wrong order.
	 * @param  list  reference to an array of integers to be sorted
	 */
	public void bubbleSort(ArrayList <Comparable> list){
		System.out.println();
		sop("Bubble Sort");
		System.out.println();

		int length = list.size(), i, j;
		boolean swapFlag = true;

		while (swapFlag) {
			swapFlag = false;
			for (i = 0; i < (length - 1); i++){
				if ((list.get(i).compareTo(list.get(i + 1))) > 0) {
					swap(list, i, i+1); //calling method swap
					swapFlag = true;
				}	
			}
		}
	}

	/**
	 *  This method implements a sorting algorithm that does a specific element comparison with the
	 *  previous elements.
	 *
	 * @param  list  reference to an array of integers to be sorted
	 */
	public void selectionSort(ArrayList <Comparable> list){
		//Add your code here
		System.out.println();
		System.out.println("Selection Sort");
		System.out.println();

		int min, len = list.size();
		int i, j;

		for (j = 0; j < len - 1; j++){

			min = j;
			for (i = j + 1; i < len; i++){
				if (list.get(i).compareTo(list.get(min)) < 0) {
					min = i; 
				}
			}
			swap(list, j, min);
		}
	}

	/**
	 *  This method implements a sorting algorithm that builds up the completed ArrayList
	 *  one element at a time. It shifts elements one by one.
	 *
	 * @param  list  reference to an array of integers to be sorted
	 */
	public void insertionSort(ArrayList <Comparable> list){
		//Add your code here
		System.out.println();
		System.out.println("Insertion Sort");
		System.out.println();

		int pos, index, len = list.size();

		for (index = 1; index < len; index++){
			pos = index;

			while ((pos > 0) && ((list.get(pos - 1).compareTo(list.get(pos))) > 0)) {
				swap(list, pos , pos-1);
				pos--;
			}
		}
	}

	/**
	 *  Takes in entire vector, but will merge the following sections
	 *  together:  Left sublist from a[first]..a[mid], right sublist from
	 *  a[mid+1]..a[last].  Precondition:  each sublist is already in
	 *  ascending order
	 *
	 * @param  a      reference to an array of integers to be sorted
	 * @param  first  starting index of range of values to be sorted
	 * @param  mid    midpoint index of range of values to be sorted
	 * @param  last   last index of range of values to be sorted
	 */
	private void merge(ArrayList <Comparable> arr, int first, int mid, int last){
		//Add your code here

		int f = first;
		int l = mid + 1;
		int i, t;

		ArrayList<Comparable> arr2 = new ArrayList<Comparable>();

		for (i = 0; (f <= mid) && (l <= last) ; i++) {
			if(arr.get(f).compareTo(arr.get(l)) < 0) {
				arr2.add(i, arr.get(f));
				f++;
			}
			else {
				arr2.add(i, arr.get(l));
				l++;
			}
		}

		for (;f <= mid; f++, i++) {
			arr2.add(i, arr.get(f));
		}
		for (;l <= last; l++, i++) {
			arr2.add(i, arr.get(l));
		}

		for(i = 0, t = first; t <= last; i++, t++) {
			arr.set(t, arr2.get(i));
		}
	}

	/**
	 *  Recursive mergesort of an array of integers. 
	 *  This method implements a sorting algorithm that continuously splits the ArrayList into halves
	 *  until 2 or 1 element(s) are remaining and swaps them into the correct order and then goes 
	 *  backwards, swapping the elements into the correct order.
	 *
	 * @param  arr    reference to an array of integers to be sorted
	 * @param  first  starting index of range of values to be sorted
	 * @param  last   ending index of range of values to be sorted
	 */
	public void mergeSort(ArrayList <Comparable> arr, int first, int last){
		//Add your code here
		if ((last - first) > 0) {
			int mid = (first + last) / 2;
			mergeSort(arr, first, mid);
			mergeSort(arr, mid + 1, last);
			merge(arr, first, mid, last);
		}
	}

	/**
	 *  This method implements a sorting algorithm that creates a partition or pivot and 
	 *  systematically places the elements of the ArrayList in order. All elements that have a lower
	 *  value are moved to the left of the pivot, and the higher and equal values are placed on the 
	 *  right. The pivot keeps changing till the ArrayList is completely sorted.
	 *  
	 * @param  arr    reference to an array of integers to be sorted
	 * @param  first  starting index of range of values to be sorted
	 * @param  last   ending index of range of values to be sorted
	 */
	public void quickSort(ArrayList<Comparable> arr, int first, int last)
	{
		int pivot;

		if (first < last) {
			pivot = partition(arr, first, last);
			quickSort(arr, first, pivot - 1);
			quickSort(arr, pivot + 1, last);
		}
	}
	private int partition(ArrayList<Comparable> arr, int first, int last) {
		int i = first;
		int j = last+1;
		int pivot = first;
		
		while (true) {
			do {
				++i;
			} while (arr.get(i).compareTo(arr.get(pivot)) <= 0 && i < last);
			do {
				--j;
			} while (arr.get(j).compareTo(arr.get(pivot)) > 0);
			if (i >= j){
				break;
			}
			
			swap(arr, i, j);
		}
		swap(arr, first, j);
		return j;
	}

	/**
	 * Interchanges two elements in an ArrayList
	 *
	 * @param  list  reference to an array of integers
	 * @param  a     index of integer to be swapped
	 * @param  b     index of integer to be swapped
	 */
	public void swap(ArrayList <Comparable> list, int a, int b){
		//replace these lines with your code
		Comparable temp1 = list.get(a);
		Comparable temp2 = list.get(b);
		list.set(a, temp2);
		list.set(b, temp1);
	}
}
