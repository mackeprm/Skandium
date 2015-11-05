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

import cl.niclabs.skandium.muscles.Merge;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

public class MergeList implements Merge<Range, Range> {

	@Override
	public Range apply(Collection<Range> r) {
		final Range result;
		if (r instanceof RandomAccess) {
			List<Range> randomAccessParam = (List<Range>) r;
			result = new Range(randomAccessParam.get(0).array, randomAccessParam.get(0).left, randomAccessParam.get(1).right);
		} else {
			final Iterator<Range> rangeIterator = r.iterator();
			final Range left = rangeIterator.next();
			final Range right = rangeIterator.next();
			result = new Range(left.array, left.left, right.right);
		}

		return result;
	}
}