package edu.iastate.cs228.hw2;

import java.io.File;


/**
 * 
 * @author  Alec Meyer
 *
 */

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 * 
 * This class sorts all the points in an array by polar angle with respect to a reference point whose x and y 
 * coordinates are respectively the medians of the x and y coordinates of the original points. 
 * 
 * It records the employed sorting algorithm as well as the sorting time for comparison. 
 *
 */
public class RotationalPointScanner  
{
	private Point[] points; 
	
	private Point medianCoordinatePoint;  // point whose x and y coordinates are respectively the medians of 
	                                      // the x coordinates and y coordinates of those points in the array points[].
	private Algorithm sortingAlgorithm;    
	
	protected String outputFileName;   // "select.txt", "insert.txt", "merge.txt", or "quick.txt"
	
	protected long scanTime; 	       // execution time in nanoseconds. 
	
	
	
	public static Point plotMedPoint;  //Used in the plot class to plot median point

	
	/**
	 * This constructor accepts an array of points and one of the four sorting algorithms as input. Copy 
	 * the points into the array points[]. Set outputFileName. 
	 * 
	 * @param  pts  input array of points 
	 * @throws IllegalArgumentException if pts == null or pts.length == 0.
	 */
	public RotationalPointScanner(Point[] pts, Algorithm algo) throws IllegalArgumentException
	{
		if(pts == null || pts.length == 0)
			throw new IllegalArgumentException("Point[] cannot be null or length 0");
		else {
			sortingAlgorithm = algo;
			
			points = new Point[pts.length];
			for(int i = 0; i < pts.length; i++)
			{
				points[i] = pts[i];
			}
			
			if(algo == Algorithm.InsertionSort)
				outputFileName = "insert.txt";
			else if(algo == Algorithm.MergeSort)
				outputFileName = "merge.txt";
			else if(algo == Algorithm.QuickSort)
				outputFileName = "quick.txt";
			else if(algo == Algorithm.SelectionSort)
				outputFileName = "select.txt";
		}
	}

	
	/**
	 * This constructor reads points from a file. Set outputFileName. 
	 * 
	 * @param  inputFileName
	 * @throws FileNotFoundException 
	 * @throws InputMismatchException   if the input file contains an odd number of integers
	 */
	protected RotationalPointScanner(String inputFileName, Algorithm algo) throws FileNotFoundException, InputMismatchException
	{
		File file = new File(inputFileName);
		Scanner scan = new Scanner(file); // for input to array
		Scanner sn = new Scanner(file);   // check file size/if error
		int fileSize = 0;
		Point[] array;
		//get array size and check if odd amount of integers
		while(sn.hasNextInt())
		{
			sn.nextInt();
			fileSize++;
		}
		if(fileSize % 2 == 1)//makes sure file has even number of integers
		{
			sn.close(); 
			scan.close(); 
			throw new InputMismatchException("File must contain an even amount of integers");
		}
		
		int index = 0;
		array = new Point[fileSize/2];
		while(scan.hasNextInt())
		{
			array[index++] = new Point(scan.nextInt(), scan.nextInt());
		}
		scan.close();
		sn.close();
		points = array;
		
		//set algorithm
		sortingAlgorithm = algo;
		
		if(algo == Algorithm.InsertionSort)
			outputFileName = "insert.txt";
		else if(algo == Algorithm.MergeSort)
			outputFileName = "merge.txt";
		else if(algo == Algorithm.QuickSort)
			outputFileName = "quick.txt";
		else if(algo == Algorithm.SelectionSort)
			outputFileName = "select.txt";
	}

	
	/**
	 * Carry out three rounds of sorting using the algorithm designated by sortingAlgorithm as follows:  
	 *    
	 *     a) Sort points[] by the x-coordinate to get the median x-coordinate. 
	 *     b) Sort points[] again by the y-coordinate to get the median y-coordinate.
	 *     c) Construct medianCoordinatePoint using the obtained median x- and y-coordinates. 
	 *     d) Sort points[] again by the polar angle with respect to medianCoordinatePoint.
	 *  
	 * Based on the value of sortingAlgorithm, create an object of SelectionSorter, InsertionSorter, MergeSorter,
	 * or QuickSorter to carry out sorting. Copy the sorting result back onto the array points[] by calling 
	 * the method getPoints() in AbstractSorter. 
	 *      
	 * @param algo
	 * @return
	 */
	public void scan()
	{
		  
		AbstractSorter aSorter = null; 
		scanTime = 0;
		 
		if(sortingAlgorithm == Algorithm.SelectionSort) 
			aSorter = new SelectionSorter(points);
		else if(sortingAlgorithm == Algorithm.InsertionSort) 
			aSorter = new InsertionSorter(points);
		else if(sortingAlgorithm == Algorithm.MergeSort)
			aSorter = new MergeSorter(points);
		else if(sortingAlgorithm == Algorithm.QuickSort)
			aSorter = new QuickSorter(points);
		
		
		//x component
		aSorter.setComparator(0);
		long startTimeX = System.nanoTime();//start scan time for x component sorting
		aSorter.sort();
		long endTimeX = System.nanoTime();//end scan time
		scanTime += (endTimeX - startTimeX);
		Point medPointX = aSorter.getMedian();
		int xMed = medPointX.getX();
		
		//y component
		aSorter.setComparator(1);
		long startTimeY = System.nanoTime();//start scan time for y component sorting
		aSorter.sort();
		long endTimeY = System.nanoTime();//end scan time
		scanTime += (endTimeY - startTimeY);
		Point medPointY = aSorter.getMedian();
		int yMed = medPointY.getY();
		
		//polar
		medianCoordinatePoint = new Point(xMed, yMed);
		aSorter.setReferencePoint(medianCoordinatePoint);
		aSorter.setComparator(2);
		long startTimePolar = System.nanoTime();//start scan time for polar sorting
		aSorter.sort();
		long endTimePolar = System.nanoTime();//end scan time
		scanTime += (endTimePolar - startTimePolar);
		aSorter.getPoints(points);
		
	}
	
	
	/**
	 * Outputs performance statistics in the format: 
	 * 
	 * <sorting algorithm> <size>  <time>
	 * 
	 * For instance, 
	 * 
	 * selection sort   1000	  9200867
	 * 
	 * Use the spacing in the sample run in Section 2 of the project description. 
	 */
	public String stats()
	{	
		if(sortingAlgorithm == Algorithm.MergeSort || sortingAlgorithm == Algorithm.QuickSort)
			return (sortingAlgorithm + "     " + points.length + " " + scanTime); 
		return (sortingAlgorithm + " " + points.length + " " + scanTime); 
	}
	
	
	/**
	 * Write points[] after a call to scan().  When printed, the points will appear 
	 * in order of polar angle with respect to medianCoordinatePoint with every point occupying a separate 
	 * line.  The x and y coordinates of the point are displayed on the same line with exactly one blank space 
	 * in between. 
	 */
	@Override
	public String toString()
	{
		String str = "";
		for(int i = 0; i < points.length; i++)
		{
			str += points[i].getX() + " " + points[i].getY() + "\n";
		}
		return str;
		
	}

	
	/**
	 *  
	 * This method, called after scanning, writes point data into a file by outputFileName. The format 
	 * of data in the file is the same as printed out from toString().  The file can help you verify 
	 * the full correctness of a sorting result and debug the underlying algorithm. 
	 * 
	 * @throws FileNotFoundException
	 */
	public void writePointsToFile() throws FileNotFoundException
	{
		File file = new File(outputFileName);
		PrintWriter pn = new PrintWriter(file);
		pn.print(this.toString());
		pn.close();
	}	

	
	/**
	 * This method is called after each scan for visually check whether the result is correct.  You  
	 * just need to generate a list of points and a list of segments, depending on the value of 
	 * sortByAngle, as detailed in Section 4.1. Then create a Plot object to call the method myFrame().  
	 * 
	 * I accounted for duplicate segments in the Plot class in the drawSegment method.
	 * 
	 */
	public void draw()
	{	
		int numSegs = (points.length*2)  ;  // number of segments to draw 
		
		Segment[] segments = new Segment[numSegs]; 
		int j = 0;
		int k = 0;
		
		//connecting segments
		for(int i = 0; i < numSegs; i++) 
		{	
			segments[i] = new Segment(points[j], points[++j]);//connecting line segments
			segments[++i] = new Segment(medianCoordinatePoint, points[k++]);//line segments from medianPoint
			
			if(points[j] == points[points.length - 1]) {
				segments[++i] = new Segment(points[j], points[0]);//connect last point to the first
				segments[++i] = new Segment(points[j], medianCoordinatePoint);//connect last segment to mid point
			}
			
		}

		
		//for plotting the medianCoordinatePoint as an individual dot
		plotMedPoint = medianCoordinatePoint;
		
		String sort = null; 
		
		switch(sortingAlgorithm)
		{
		case SelectionSort: 
			sort = "Selection Sort"; 
			break; 
		case InsertionSort: 
			sort = "Insertion Sort"; 
			break; 
		case MergeSort: 
			sort = "Mergesort"; 
			break; 
		case QuickSort: 
			sort = "Quicksort"; 
			break; 
		default: 
			break; 		
		}
		
		// The following statement creates a window to display the sorting result.
		Plot.myFrame(points, segments, sort);
		
	}
		
}
