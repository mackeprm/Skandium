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

import java.util.function.Function;

/**
 * A <code>Farm</code> {@link Function} provides task replication or master-worker like parallelism.
 * <p>
 * If possible (unsynchronized muscles), parameters submitted to a <code>Farm</code> are computed in parallel with respect to each other.
 *
 * @param <P> The input type of the {@link Function}.
 * @param <R> The result type of the {@link Function}.
 * @author mleyton
 */
public abstract class Farm<P, R> implements Function<P, R> {

    protected final Function<P, R> subskel;

    /**
     * The constructor.
     *
     * @param skeleton The skeleton pattern to replicate.
     */
    public Farm(Function<P, R> skeleton) {
        this.subskel = skeleton;
    }
}
