/*   Skandium: A Java(TM) based parallel skeleton library.
 *
 *   Copyright (C) 2011 NIC Labs, Universidad de Chile.
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

package cl.niclabs.skandium.examples.quicksort;

import cl.niclabs.skandium.impl.forkjoin.ForkJoinDaC;

import java.util.Random;
import java.util.function.Function;

public class QuickSort {
	static Random random = new Random();

	public static void main(String args[]) throws Exception {

		int THREADS = Runtime.getRuntime().availableProcessors();
		int SIZE = (int) Math.pow(2, 20);

		if (args.length != 0) {
			THREADS = Integer.parseInt(args[0]);
			SIZE = Integer.parseInt(args[1]);
		}

		System.out.println("Computing Quicksort threads=" + THREADS + " size=" + SIZE + ".");

		// 1. Define the skeleton program structure
		Function<Range, Range> sort = new ForkJoinDaC<>(
				new ShouldSplit(SIZE / (THREADS * 2)),  //threshold size for recursion
				new SplitList(), //number of parts to divide by
				new Sort(),
				new MergeList());

		long init = System.currentTimeMillis();

		final Range input = new Range(generate(SIZE), 0, SIZE - 1);
		Range result = sort.apply(input);

		System.out.println((System.currentTimeMillis() - init) + "[ms]: " + result.array);
		for (int i = 0; i < result.array.length - 2; i++) {
			if (result.array[i] > result.array[i + 1]) throw new Exception("Not sorted! " + i + " " + (i + 1));
		}

	}

	public static int[] generate(int size) {

		int array[] = new int[size];

		for (int i = 0; i < size; i++) {
			array[i] = random.nextInt();
		}

		return array;
	}
}
