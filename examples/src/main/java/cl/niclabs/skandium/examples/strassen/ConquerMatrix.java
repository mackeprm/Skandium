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

import cl.niclabs.skandium.muscles.Merge;

import java.util.Collection;
import java.util.List;

/**
 * Reduces the results into a single matrix.
 *
 * @author mleyton
 */
public class ConquerMatrix implements Merge<Matrix, Matrix> {

    @Override
    public Matrix apply(Collection<Matrix> input) {
        if (input instanceof List) {
            List<Matrix> p = (List<Matrix>) input;
            Matrix c11 = p.get(0).add(p.get(3)).substract(p.get(4)).add(p.get(6));
            Matrix c12 = p.get(2).add(p.get(4));
            Matrix c21 = p.get(1).add(p.get(3));
            Matrix c22 = p.get(0).add(p.get(2)).substract(p.get(1)).add(p.get(5));

            int n = c11.length() * 2;

            Matrix C = new Matrix(n);

            C.copyInto(c11, 0, 0);
            C.copyInto(c12, 0, n / 2);
            C.copyInto(c21, n / 2, 0);
            C.copyInto(c22, n / 2, n / 2);
            return C;
        } else {
            throw new IllegalStateException("not implemented for Collection. Please use List");
        }
    }
}