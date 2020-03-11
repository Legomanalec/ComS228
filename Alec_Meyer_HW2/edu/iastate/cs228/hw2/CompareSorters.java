package edu.iastate.cs228.hw2;

/**
 *  
 * @author Alec Meyer
 *
 */

/**
 * 
 * This class executes four sorting algorithms: selection sort, insertion sort, mergesort, and
 * quicksort, over randomly generated integers as well integers from a file input. It compares the 
 * execution times of these algorithms on the same input. 
 *
 */

import java.io.FileNotFoundException;
import java.util.Scanner; 
import java.util.Random; 


public class CompareSorters 
{
	/**
	 * Repeatedly take integer sequences either randomly generated or read from files. 
	 * Use them as coordinates to construct points.  Scan these points with respect to their 
	 * median coordinate point four times, each time using a different sorting algorithm.  
	 * 
	 * @param args
	 **/
	public static void main(String[] args) throws FileNotFoundException
	{		
		 	
		RotationalPointScanner[] scanners = new RotationalPointScanner[4]; 
		Scanner sn = new Scanner(System.in);
		int trial = 1;
		int option = 0;
		Point[] points = null;
		System.out.println("Preformances of Four Sorting Algorithms in Point Scanning");
		System.out.println("keys: 1 (random integers) 2 (file input) 3 (exit)");
		
		while(true)
		{
			System.out.printf("Trial %d: ",trial++);
			option = sn.nextInt();
			
			if(option == 1)
			{
				int num = -1;
				//makes sure user enters a value of points greater than or equal to 2
				while(num < 2) {
					System.out.print("Enter number of random points: ");
					num = sn.nextInt();
				}
				Random generator = new Random();
				points = generateRandomPoints(num, generator);
				
				//Initialize RPS
				scanners[0] = new RotationalPointScanner(points, Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(points, Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(points, Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(points, Algorithm.QuickSort);
			}
			else if(option == 2)
			{
				System.out.println("Points from a file");
				System.out.print("File name: ");
				String fileName = sn.next();
				
				//Initialize RPS
				scanners[0] = new RotationalPointScanner(fileName, Algorithm.SelectionSort);
				scanners[1] = new RotationalPointScanner(fileName, Algorithm.InsertionSort);
				scanners[2] = new RotationalPointScanner(fileName, Algorithm.MergeSort);
				scanners[3] = new RotationalPointScanner(fileName, Algorithm.QuickSort);	
			}
	
			else if(option == 3)
				break;
			
			else
				continue;
			
		
			System.out.println("algorithm size time (ns)");
			System.out.println("----------------------------------");
			for(int i = 0; i < scanners.length; i++)
			{
				scanners[i].scan();
				scanners[i].writePointsToFile();
				System.out.println(scanners[i].stats());
				scanners[i].draw();
			}
			System.out.println("----------------------------------");
		}
		sn.close();
		
	}
	/**
	 * This method generates a given number of random points.
	 * The coordinates of these points are pseudo-random numbers within the range 
	 * [-50,50] × [-50,50]. Please refer to Section 3 on how such points can be generated.
	 * 
	 * Ought to be private. Made public for testing. 
	 * 
	 * @param numPts  	number of points
	 * @param rand      Random object to allow seeding of the random number generator
	 * @throws IllegalArgumentException if numPts < 1
	 */
	public static Point[] generateRandomPoints(int numPts, Random rand) throws IllegalArgumentException
	{ 
		if(numPts < 1)
			throw new IllegalArgumentException();
		
		Point[] array = new Point[numPts];
		Random generator = rand;
		
		for(int i = 0; i < numPts; i++)
		{
			array[i] = new Point(generator.nextInt(101) - 50, generator.nextInt(101) - 50);
		}
		return array; 
		
	}
	
}
