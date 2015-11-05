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
 * An <code>If</code> {@link Function} represents condition branching.
 * Depending on the evaluation of a {@link Condition}, either one or another skeleton
 * program is executed.
 *
 * @param <P> The input type of the {@link Function}.
 * @param <R> The result type of the {@link Function}.
 * @author mleyton
 */
public abstract class If<P, R> implements Function<P, R> {

    protected final Function<P, R> trueCase, falseCase;
    protected final Condition<P> condition;

    /**
     * The constructor.
     *
     * @param condition Used to determine for each parameter, which skeleton is executed.
     * @param trueCase  Executed if the condition evaluates to true.
     * @param falseCase Executed if the conditino evaulates to false.
     */
    public If(Condition<P> condition, Function<P, R> trueCase, Function<P, R> falseCase) {
        this.condition = condition;
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }
}