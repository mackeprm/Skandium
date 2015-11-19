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

/**
 * The following methods must be implemented by a SkeletonVisitor.
 * These methods are part of the skeleton design pattern.
 * 
 * @author mleyton
 */
public interface SkeletonVisitor {

    <P, R> void visit(DaC<P, R> skeleton);

    <P, R> void visit(Farm<P, R> skeleton);

    <P, R> void visit(Pipe<P, R> skeleton);

    <P, R> void visit(Seq<P, R> skeleton);

    <P, R> void visit(If<P, R> skeleton);

    <P> void visit(While<P> skeleton);

    <P> void visit(For<P> skeleton);

    <P, R> void visit(Map<P, R> skeleton);

    <P, R> void visit(Fork<P, R> skeleton);

    <P> void visit(MMKmeans<P> skeleton);

    <P> void visit(SMKmeans<P> skeleton);

    <P> void visit(HPKmeans<P> skeleton);

    <P, R> void visit(AbstractSkeleton<P, R> skeleton);
}
