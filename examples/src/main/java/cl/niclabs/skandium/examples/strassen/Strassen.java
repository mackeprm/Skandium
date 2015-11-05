/*   Skandium: A Java(TM) based parallel skeleton library.
 *   
 *   Copyright (C) 2009 NIC Labs, Universidad de Chile.
 * 
 *   Skandium is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Skandium is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.

 *   You should have received a copy of the GNU General Public License
 *   along with Skandium.  If not, see <http://www.gnu.org/licenses/>.
 */
package cl.niclabs.skandium.examples.strassen;

import cl.niclabs.skandium.impl.forkjoin.ForkJoinDaC;

import java.util.Random;
import java.util.function.Function;

/**
 * The main class to execute the Strassen Matrix multiplication algorithm.
 * 
 * @author mleyton
 */
public class Strassen{

	static Random random = new Random();
	
    public static void main(String[] args) throws Exception {
       
    	int THREADS = Runtime.getRuntime().availableProcessors();
    	int SIZE = 2048;   //Size of the matrix to compute
    	int SUBSIZE = 64;  //Threshold to stop dividing sub matrices
    	
    	if(args.length != 0) {
    		THREADS = Integer.parseInt(args[0]);
    		SIZE    = Integer.parseInt(args[1]);
    		SUBSIZE = Integer.parseInt(args[2]);
    	}
    	
    	System.out.println("Computing Strassen Matrix Multiplication threads="+THREADS+" size="+ SIZE+" subsize="+SUBSIZE+ ".");
    	
    	//1. Define the skeleton program structure
		Function<Operands, Matrix> multiply =
				new ForkJoinDaC<>(   //We use a divide and conquer skeleton pattern
						new ShouldDivide(SUBSIZE),
						new DivideOperands(),
    			 new StandardMultiply(), 
    			 new ConquerMatrix());
    	 
         //4. Input parameters
         long init = System.currentTimeMillis();
		Matrix result = multiply.apply(new Operands(newRandomMatrix(SIZE), newRandomMatrix(SIZE)));
		System.out.println("Done:" + result.length() + "x" + result.length() + "in " + (System.currentTimeMillis() - init) + "[ms]");
	}
    
    /**
     * Creates a new Matrix with random content. 
     * @param size The width and height of the desired matrix
     * @return A new Matrix
     */
    public static Matrix newRandomMatrix(int size){
    	
    	int a[][] = new int[size][size];
    	
    	for(int i=0;i<size;i++){
    		for(int j=0;j<size;j++){
    			
    			a[i][j] = random.nextInt();
    		}
    	}
    	
    	return new Matrix(a);
    }
}