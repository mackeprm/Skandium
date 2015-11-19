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

package cl.niclabs.skandium.examples;

import cl.niclabs.skandium.examples.bubblesort.BubbleSort;
import cl.niclabs.skandium.examples.kmeans.Kmeans;
import cl.niclabs.skandium.examples.mergesort.MergeSort;
import cl.niclabs.skandium.examples.nqueensnaive.NQueens;
import cl.niclabs.skandium.examples.pi.PI;
import cl.niclabs.skandium.examples.quicksort.QuickSort;
import cl.niclabs.skandium.examples.strassen.Strassen;

public class Main {

    public static void main(String args[]) throws Exception {
        if (args.length < 3 || args.length > 6) {
            usage();
        }

        if (args[0].equalsIgnoreCase("pi")) {
            String[] sub = {args[1], args[2], args[3]};
            PI.main(sub);
        } else if (args[0].equalsIgnoreCase("nqueens")) {
            String[] sub = {args[1], args[2], args[3]};
            NQueens.main(sub);
        } else if (args[0].equalsIgnoreCase("strassen")) {
            String[] sub = {args[1], args[2], args[3]};
            Strassen.main(sub);
        } else if (args[0].equalsIgnoreCase("quicksort")) {
            String[] sub = {args[1], args[2]};
            QuickSort.main(sub);
        } else if (args[0].equalsIgnoreCase("mergesort")) {
            String[] sub = {args[1], args[2]};
            MergeSort.main(sub);
        } else if (args[0].equalsIgnoreCase("bubblesort")) {
            String[] sub = {args[1], args[2]};
            BubbleSort.main(sub);
        } else if (args[0].equalsIgnoreCase("kmeans")) {
            String[] sub = {args[1], args[2], args[3], args[4], args[5]};
            Kmeans.main(sub);
        } else {
            usage();
        }

        System.exit(0);
    }

    private static void usage() {

        int threads = Runtime.getRuntime().availableProcessors();
        String jarName = getJarName();

        System.out.println("Usage:");
        System.out.println("");
        System.out.println("Pi:        java -jar " + jarName + " pi " + threads + " 2000 8");
        System.out.println("Pi:        java -jar " + jarName + " pi <threads> <decimals> <numparts>");
        System.out.println("");
        System.out.println("Nqueens:   java -jar " + jarName + " nqueens " + threads + " 15 3");
        System.out.println("Nqueens:   java -jar " + jarName + " nqueens <threads> <boardsize> <depth>");
        System.out.println("");
        System.out.println("Strassen:  java -jar " + jarName + " strassen " + threads + " 1024 128");
        System.out.println("Strassen:  java -jar " + jarName + " strassen <threads> <matrixsize> <submatrixsize>");
        System.out.println("");
        System.out.println("QuickSort: java -jar " + jarName + " quicksort " + threads + " 67108864");
        System.out.println("QuickSort: java -jar " + jarName + " quicksort <threads> <size>");
        System.out.println("");
        System.out.println("MergeSort: java -jar " + jarName + " mergesort " + threads + " 67108864");
        System.out.println("MergeSort: java -jar " + jarName + " mergesort <threads> <size>");
        System.out.println("");
        System.out.println("BubbleSort: java -jar " + jarName + " bubblesort " + threads + " 67108864");
        System.out.println("BubbleSort: java -jar " + jarName + " bubblesort <threads> <size>");

        System.exit(1);
    }

    private static String getJarName() {
        return new java.io.File(Main.class.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .getPath())
                .getName();
    }
}
