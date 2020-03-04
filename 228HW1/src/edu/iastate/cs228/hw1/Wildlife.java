package edu.iastate.cs228.hw1;

import java.io.FileNotFoundException;
import java.util.Scanner; 

/**
 *  
 * @author Alec Meyer

 *
 */

/**
 * 
 * The Wildlife class performs a simulation of a grid plain with
 * squares inhabited by badgers, foxes, rabbits, grass, or none. 
 *
 */
public class Wildlife 
{
	/**
	 * Update the new plain from the old plain in one cycle. 
	 * @param pOld  old plain
	 * @param pNew  new plain 
	 */
	public static void updatePlain(Plain pOld, Plain pNew)
	{
	
		
		for(int i = 0; i < pOld.getWidth(); i++) {
			for(int j = 0; j < pOld.getWidth(); j++) {
				pNew.grid[i][j] = pOld.grid[i][j].next(pNew);
			}
		}
	}
	
	/**
	 * Repeatedly generates plains either randomly or from reading files. 
	 * Over each plain, carries out an input number of cycles of evolution. 
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException
	{	
		
		//Initialize all variables
		Scanner sn = new Scanner(System.in);
		Plain even = null;
		Plain odd = null;
		int option = 0;
		int trial = 1;
		int width = 0;
		int cycles = 0;
		String inputFile;
		
		
		System.out.println("Simulation of Wildlife of the Plain");
		System.out.println("keys: 1 (random plain) 2 (file input) 3 (exit)");
		while(option != 3) {
			
			
			
			System.out.print("Trial " + trial + ": ");
			option = sn.nextInt();
			
			if(option == 1) {
				System.out.println("Random plain");
				
				while(width < 1) {
					System.out.print("Enter grid width: ");
					width = sn.nextInt();
				}
				
				even = new Plain(width);
				odd = new Plain(width);
				even.randomInit();
				
			}
			
			else if(option == 2) {
				System.out.println("Plain input from a file");
				System.out.print("File name: ");
				inputFile = sn.next();
				
				even = new Plain(inputFile);
				odd = new Plain(inputFile);
			}
			
			else if(option == 3) {
				break;
			}
			
			
			else {
				System.out.println("Please select a valid option.");
				continue;
			}
			
			while(cycles < 1) {
				System.out.println("Enter the number of cycles: ");
				cycles = sn.nextInt();
				if(cycles > 0)
					break;
			}
			
			
			System.out.println("Initial plain:\n");
			System.out.println(even.toString());
			
			
			for(int i = 0; i<cycles; i++) {
				
				if(i % 2 == 0)
					updatePlain(even, odd);
				else if(i % 2 == 1)
					updatePlain(odd, even);
				
			}
			
			System.out.println("Final plain:\n");
			if(cycles % 2 == 1) {
				System.out.println(odd.toString());
			}
			else if (cycles % 2 == 0) {
				System.out.println(even.toString());
			}	
			
			trial++;
			cycles = 0;
			width = 0;
		}	
	}
}
