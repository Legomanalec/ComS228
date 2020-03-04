package edu.iastate.cs228.hw1;

/**
 *  
 * @author Alec Meyer
 *
 */

/** 
 * Empty squares are competed by various forms of life.
 */
public class Empty extends Living 
{
	public Empty (Plain p, int r, int c) 
	{
		plain = p;
		row = r;
		column = c;
	}
	
	public State who()
	{
		return State.EMPTY; 
	}
	
	/**
	 * An empty square will be occupied by a neighboring Badger, Fox, Rabbit, or Grass, or remain empty. 
	 * @param pNew     plain of the next life cycle.
	 * @return Living  life form in the next cycle.   
	 */
	public Living next(Plain pNew)
	{
		int[] population = new int[NUM_LIFE_FORMS];
		this.census(population);
		if (population[RABBIT] > 1) 
			return new Rabbit(pNew, row, column, 0);
		 else if (population[FOX] > 1) 
			return new Fox(pNew, row, column, 0);
		 else if (population[BADGER] > 1) 
			return new Badger(pNew, row, column, 0);
		 else if (population[GRASS] >= 1) 
			return new Grass(pNew, row, column);
		 else {
			this.plain = pNew;
			return this;
		} 
	}
}
