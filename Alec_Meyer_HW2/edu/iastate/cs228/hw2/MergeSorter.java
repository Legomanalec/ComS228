package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;

/**
 *  
 * @author
 *
 */

/**
 * 
 * This class implements the mergesort algorithm.   
 *
 */

public class MergeSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/** 
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts   input array of integers
	 */
	public MergeSorter(Point[] pts) 
	{
		super(pts);
		algorithm = "merge sort";
	}


	/**
	 * Perform mergesort on the array points[] of the parent class AbstractSorter. 
	 * 
	 */
	@Override 
	public void sort()
	{
		mergeSortRec(points, 0, points.length-1);
	}

	
	/**
	 * This is a recursive method that carries out mergesort on an array pts[] of points. One 
	 * way is to make copies of the two halves of pts[], recursively call mergeSort on them, 
	 * and merge the two sorted subarrays into pts[].   
	 * 
	 * @param pts	point array 
	 */

	private void mergeSortRec(Point[] pts)
	{
			//needed more parameters to keep time complexity consistent 
	}
	
	
	
	/**
	 * @param pts
	 * @param x left	
	 * @param y right
	 */
	private void mergeSortRec(Point[] pts, int x, int y)
	{	
		int half = (x + y) / 2;
		if (x < y) 
        { 
            // Sort first and second halves 
            mergeSortRec(pts, x, half); 
            mergeSortRec(pts , half + 1, y); 
            merge(pts, x, half, y); 
        }	
	}

	/**
	 * Merges 2 sorted arrays in order
	 * @param arr
	 * @param x
	 * @param z
	 * @param y
	 */
	private void merge(Point[] arr, int x, int z, int y)
	{ 
        //left array
        int lSplit = z - x + 1; 
        Point[] left = new Point [lSplit]; 
        for (int i=0; i<lSplit; ++i) 
        	left[i] = arr[x + i];
          
        //right array
        int rSplit = y - z;
        Point[] right = new Point [rSplit];
        for (int j=0; j<rSplit; ++j) 
        	 right[j] = arr[z + j + 1];
          
        
        //merge sorted arrays
        int i = 0;
        int j = 0; 
        int k = x; 
        while (i < lSplit && j < rSplit) 
        { 
            if (pointComparator.compare(left[i], right[j]) <= 0) 
                arr[k++] = left[i++];  
            else
                arr[k++] = right[j++];  
        } 
  
        //if array length is 1 and is larger it needs to just be appended to the end
        while (i < lSplit) 
            arr[k++] = left[i++];  
        
        while (j < rSplit) 
            arr[k++] = right[j++];    
	}
	

}
