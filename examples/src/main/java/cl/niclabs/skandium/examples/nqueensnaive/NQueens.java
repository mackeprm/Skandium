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
package cl.niclabs.skandium.examples.nqueensnaive;

import cl.niclabs.skandium.impl.forkjoin.ForkJoinDaC;
import cl.niclabs.skandium.impl.forkjoin.ForkJoinMap;

import java.util.Random;
import java.util.function.Function;

/**
 * The main class to execute a naive NQueens counting algorithm which does not consider board symmetries.
 * 
 * @author mleyton
 */
public class NQueens {

	private static Random random = new Random();

	public static void main(String[] args) throws Exception {

		int THREADS = Runtime.getRuntime().availableProcessors();
		int BOARD = 15;  //Size board of the board
		int DEPTH = 3;

		if (args.length != 0) {
			THREADS = Integer.parseInt(args[0]);
			BOARD = Integer.parseInt(args[1]);
			DEPTH = Integer.parseInt(args[2]);
		}

		System.out.println("Computing NQueens threads=" + THREADS + " board=" + BOARD + " depth=" + DEPTH + ".");

		//1. Define the skeleton program structure
		Function<Board, Count> subskel = new ForkJoinDaC<>(   //We use a divide and conquer skeleton pattern
				new ShouldDivide(DEPTH),  //Dive until the depth is "N-3"
				new DivideBoard(),
				new Solve(),
				new ConquerCount());

		Function<Board, Count> nqueens = //Always subdivide the first row.
				new ForkJoinMap<>(new DivideBoard(), subskel, new ConquerCount());

		long init = System.currentTimeMillis();
		Count result = nqueens.apply(new Board(BOARD));
		System.out.println(result + " in " + (System.currentTimeMillis() - init) + "[ms]");
	}
}