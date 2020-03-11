package edu.iastate.cs228.hw2;

import java.io.FileNotFoundException;
import java.lang.NumberFormatException; 
import java.lang.IllegalArgumentException; 
import java.util.InputMismatchException;


/**
 *  
 * @author Alec Meyer
 *
 */

/**
 * 
 * This class implements selection sort.   
 *
 */

public class SelectionSorter extends AbstractSorter
{
	// Other private instance variables if you need ... 
	
	/**
	 * Constructor takes an array of points.  It invokes the superclass constructor, and also 
	 * set the instance variables algorithm in the superclass.
	 *  
	 * @param pts  
	 */
	public SelectionSorter(Point[] pts)  
	{
		super(pts);
		algorithm = "selection sort";
	}	

	
	/** 
	 * Apply selection sort on the array points[] of the parent class AbstractSorter.  
	 * implemented from lecture notes
	 */
	@Override 
	public void sort()
	{
		int i, j;
		for(i = 0; i < points.length-1; i++)
		{
			int smallest = i;
			for(j = i+1; j < points.length; j++)
			{
				if(pointComparator.compare(points[j], points[smallest]) < 0)
				{
					smallest = j;
				}	
			}
			swap(i, smallest);
		}
	}	
}
