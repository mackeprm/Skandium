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

import cl.niclabs.skandium.muscles.Condition;
import cl.niclabs.skandium.muscles.Merge;
import cl.niclabs.skandium.muscles.Split;

import java.util.function.Function;

/**
 * A Divide and Conquer <code>Dac</code> skeleton provides a recursive parallelism pattern.
 * <p>
 * An input parameter is subdivided into smaller parameters until a condition is reached.
 * Then each sub-parameter is computed in parallel, and the results are merged back into a single result.
 *
 * @param <P> The type of the input parameter.
 * @param <R> The type of the result.
 * @author mleyton
 */
public abstract class DaC<P, R> implements Function<P, R> {

    protected final Condition<P> condition;
    protected final Split<P, P> split;
    protected final Function<P, R> skeleton;
    protected final Merge<R, R> merge;

    /**
     * The constructor.
     *
     * @param condition the parameter will be subdivided while this condition holds true.
     * @param split     the code to subdivide a parameter.
     * @param skeleton  the skeleton code to execute when the subdivision process is finished.
     * @param merge     the code to reduce the results into a single output.
     */
    public DaC(Condition<P> condition, Split<P, P> split, Function<P, R> skeleton, Merge<R, R> merge) {

        this.condition = condition;
        this.split = split;
        this.skeleton = skeleton;
        this.merge = merge;
    }
}