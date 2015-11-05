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

package cl.niclabs.skandium.examples.mergesort;

import cl.niclabs.skandium.impl.forkjoin.ForkJoinDaC;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class MergeSort {
    static Random random = new Random();

    public static void main(String[] args) {
        int THREADS = Runtime.getRuntime().availableProcessors();
        int SIZE = (int) Math.pow(2, 26);

        if (args.length != 0) {
            THREADS = Integer.parseInt(args[0]);
            SIZE = Integer.parseInt(args[1]);
        }

        ArrayList<Integer> in = generate(SIZE);

        System.out.println("Computing Mergesort threads=" + THREADS + " size=" + SIZE + ".");

        Function<ArrayList<Integer>, ArrayList<Integer>> msort = new ForkJoinDaC<>(
                new MSCondition(SIZE / (THREADS * 2)), new MSSplit(), new MSExecute(),
                new MSMerge());

        long init = System.currentTimeMillis();
        ArrayList<Integer> out = msort.apply(in);
        try {
            System.out.println((System.currentTimeMillis() - init) + "[ms]: " + out.toArray());
            for (int i = 0; i < out.size() - 2; i++) {
                if (out.get(i) > out.get(i + 1)) throw new Exception("Not sorted! " + i + " " + (i + 1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Integer> generate(int size) {

        ArrayList<Integer> array = new ArrayList<>(size);

        for (int i = 0; i < size; i++) {
            array.add(i, random.nextInt());
        }

        return array;
    }

}
