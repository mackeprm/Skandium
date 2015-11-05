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

import cl.niclabs.skandium.muscles.Condition;

import java.util.ArrayList;

public class MSCondition implements Condition<ArrayList<Integer>> {

	int maxTimes,times;
	
	public MSCondition(int maxTimes) {
		this.maxTimes = maxTimes;
		this.times = 0;
	}
	
	@Override
	public synchronized Boolean apply(ArrayList<Integer> p) {
		return p.size() > 1 && times++ < this.maxTimes;
	}

}
