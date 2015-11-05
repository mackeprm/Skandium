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
 * A <code>Map</code> {@link Function} divides an input parameter into a list of sub-parameters,
 * executes the same code on each sub-parameter in parallel, and reduces the results.
 *
 * @param <P> The input type of the {@link Function}.
 * @param <R> The result type of the {@link Function}.
 * @param <I> The input type of the Map-{@link Function}.
 * @param <O> The result type of the Map-{@link Function}.
 * @author mleyton
 */
public abstract class Map<P, R, I, O> implements Function<P, R> {

    protected final Split<P, I> split;
    protected final Function<I, O> skeleton;
    protected final Merge<O, R> merge;

    /**
     * The constructor.
     *
     * @param split    The code to divide each input parameter.
     * @param skeleton The code to execute on each sub-parameter.
     * @param merge    The code to reduce the results into a single one.
     */
    public Map(Split<P, I> split, Function<I, O> skeleton, Merge<O, R> merge) {
        this.split = split;
        this.skeleton = skeleton;
        this.merge = merge;
    }
}