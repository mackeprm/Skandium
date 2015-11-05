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

import cl.niclabs.skandium.muscles.Split;

import java.util.ArrayList;
import java.util.Collection;

public class MSSplit implements Split<ArrayList<Integer>, ArrayList<Integer>> {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<ArrayList<Integer>> apply(ArrayList<Integer> p) {
		int middle = p.size() / 2;
		ArrayList<Integer> left = new ArrayList<>();
		ArrayList<Integer> right = new ArrayList<>();
		for (int i=0; i<middle; i++) {
			left.add(p.get(i));
		}
		for (int i=middle; i<p.size(); i++) {
			right.add(p.get(i));
		}
		Collection<ArrayList<Integer>> r = new ArrayList<>(2);
		r.add(new ArrayList<>(left));
		r.add(new ArrayList<>(right));
		return r;
	}


}
