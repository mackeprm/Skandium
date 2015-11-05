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
 * A <code></code> {@link Function}
 *
 * @param <P> The input type of the {@link Function}.
 * @param <R> The result type of the {@link Function}.
 * @author mleyton
 */
public abstract class Pipe<P, R, X> implements Function<P, R> {

    protected final Function<P, X> stage1;
    protected final Function<X, R> stage2;

    public Pipe(Function<P, X> stage1, Function<X, R> stage2) {
        this.stage1 = stage1;
        this.stage2 = stage2;
    }
}
