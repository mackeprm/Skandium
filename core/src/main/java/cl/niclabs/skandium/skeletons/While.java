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

import java.util.function.Function;

/**
 * A <code></code> {@link Function}
 *
 * @param <P> The input and output type of the {@link Function}.
 * @author mleyton
 */
public abstract class While<P> implements Function<P, P> {

    protected final Function<P, P> subskel;
    protected final Condition<P> condition;

    public While(Function<P, P> skeleton, Condition<P> condition) {
        this.subskel = skeleton;
        this.condition = condition;
    }
}
