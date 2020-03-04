package edu.iastate.cs228.hw1;

/**
 *  
 * @author Alec Meyer

 *
 */

import java.io.File; 
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner; 
import java.util.Random; 

/**
 * 
 * The plain is represented as a square grid of size width x width. 
 *
 */
public class Plain 
{
	private int width; // grid size: width X width 
	
	public Living[][] grid; 
	
	/**
	 *  Default constructor reads from a file 
	 */
	public Plain(String inputFileName) throws FileNotFoundException
	{		
		File file = new File(inputFileName);
		
		//Scanner for the width
		Scanner snWidth = new Scanner(file); 
		
		Scanner sn = new Scanner(file); 
		
		//Find width
		String line = snWidth.nextLine();
		width = line.length() / 3; 
		grid = new Living[width][width];
		
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				String s = sn.next();
				
				if (s.charAt(0) == 'B') {
					int age = Character.getNumericValue(s.charAt(1));
					grid[i][j] = new Badger(this, i, j, age);
				} 
				
				else if (s.charAt(0) == 'R') {
					int age = Character.getNumericValue(s.charAt(1));
					grid[i][j] = new Rabbit(this, i, j, age);
				} 
				
				else if (s.charAt(0) == 'F') {
					int age = Character.getNumericValue(s.charAt(1));
					grid[i][j] = new Fox(this, i, j, age);
				} 
				
				else if (s.charAt(0) == 'G') {
					grid[i][j] = new Grass(this, i, j);
				} 
				
				else if (s.charAt(0) == 'E') {
					grid[i][j] = new Empty(this, i, j);
				}
			}
		}
		snWidth.close();
		sn.close(); 
	}
	
	/**
	 * Constructor that builds a w x w grid without initializing it. 
	 * @param width  the grid 
	 */
	public Plain(int w)
	{
		width = w;
		grid = new Living[w][w];
	}
	
	
	public int getWidth()
	{  
		return width;  
	}
	
	/**
	 * Initialize the plain by randomly assigning to every square of the grid  
	 * one of BADGER, FOX, RABBIT, GRASS, or EMPTY.  
	 * 
	 * Every animal starts at age 0.
	 */
	public void randomInit()
	{
		Random generator = new Random(); 
		 
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < width; j++) {
				int rand = generator.nextInt(5);
				if (rand == 0) 
					grid[i][j] = new Badger(this, i, j, 0);
				else if (rand == 1) 
					grid[i][j] = new Fox(this, i, j, 0);
				else if (rand == 2) 
					grid[i][j] = new Rabbit(this, i, j, 0);
				else if (rand == 3) 
					grid[i][j] = new Grass(this, i, j);
				else if (rand == 4) 
					grid[i][j] = new Empty(this, i, j);
						
			}
		}
	}
	
	
	/**
	 * Output the plain grid. For each square, output the first letter of the living form
	 * occupying the square. If the living form is an animal, then output the age of the animal 
	 * followed by a blank space; otherwise, output two blanks.  
	 */
	public String toString()
	{
		String result = "";
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j].who() == State.BADGER) {
					result += "B" + ((Badger) grid[i][j]).myAge() + " ";
				}
				if (grid[i][j].who() == State.RABBIT) {
					result += "R" + ((Rabbit) grid[i][j]).myAge() + " ";
				}
				if (grid[i][j].who() == State.FOX) {
					result += "F" + ((Fox) grid[i][j]).myAge() + " ";
				}
				if (grid[i][j].who() == State.GRASS) {
					result += "G" + "  ";
				}
				if (grid[i][j].who() == State.EMPTY) {
					result += "E" + "  ";
				}
			}
			result += "\n";
		}
		return result; 
	}
	

	/**
	 * Write the plain grid to an output file.  Also useful for saving a randomly 
	 * generated plain for debugging purpose. 
	 * @throws FileNotFoundException
	 */
	public void write(String outputFileName) throws FileNotFoundException
	{
		PrintWriter pr = new PrintWriter(new File(outputFileName));
		pr.print(this.toString());
		pr.close();
	}			
}
