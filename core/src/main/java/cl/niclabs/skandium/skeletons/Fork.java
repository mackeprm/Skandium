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
package cl.niclabs.skandium.skeletons;

import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;

import java.util.function.Function;

/**
 * A <code>Fork</code> {@link Function} divides an input parameter into a list of sub-parameters,
 * executes a different code on each sub-parameter in parallel, and reduces the results.
 *
 * @param <P> The input type of the {@link Function}.
 * @param <R> The result type of the {@link Function}.
 * @author mleyton
 */
public abstract class Fork<P, R> implements Function<P, R> {

    protected final Split<P, R> split;
    protected final Function<P, R> skeletons[];
    protected final Merge<R, R> merge;

    /**
     * The constructor.
     * <p>
     * Note that the number of elements resulting from the division must match
     * the number of codes to execute or an Exception will be raised. In other words:
     * <p>
     * <code>Split(param).length == skeletons.length</code>
     *
     * @param split     Used to divide the parameter into sub-parameters
     * @param skeletons A list of skeletons to execute, one for each sub-parameter.
     * @param merge     The code used to merge the results of the computation into a single one.
     */
    public Fork(Split<P, R> split, Function<P, R> skeletons[], Merge<R, R> merge) {
        this.split = split;
        this.skeletons = skeletons;
        this.merge = merge;
    }
}