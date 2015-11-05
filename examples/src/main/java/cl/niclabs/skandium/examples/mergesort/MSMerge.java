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

import cl.niclabs.skandium.muscles.Merge;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class MSMerge implements Merge<ArrayList<Integer>, ArrayList<Integer>> {

    @Override
    public ArrayList<Integer> apply(Collection<ArrayList<Integer>> p) {
        if (p.size() != 2) {
            throw new IllegalStateException("Merge param was not of size 2, actual size was: " + p.size());
        } else {
            Iterator<ArrayList<Integer>> iterator = p.iterator();
            ArrayList<Integer> left = iterator.next();
            ArrayList<Integer> right = iterator.next();
            ArrayList<Integer> result = new ArrayList<>();
            while (left.size() > 0 || right.size() > 0) {
                if (left.size() > 0 && right.size() > 0) {
                    if (left.get(0) <= right.get(0)) {
                        result.add(left.remove(0));
                    } else {
                        result.add(right.remove(0));
                    }
                } else if (left.size() > 0) {
                    result.add(left.remove(0));
                } else if (right.size() > 0) {
                    result.add(right.remove(0));
                }
            }
            return result;
        }
    }
}
