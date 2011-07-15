/*
 * Copyright 2010-2011 Vilius Normantas <code@norma.lt>
 *
 * This file is part of Crossbow library.
 *
 * Crossbow is free software: you can redistribute it and/or modify it under the terms of the GNU
 * General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * Crossbow is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with Crossbow.  If not,
 * see <http://www.gnu.org/licenses/>.
 */

package lt.norma.crossbow.indicators

import lt.norma.crossbow.core._
import org.scalatest.FunSuite

class AlternativeTest extends FunSuite {
  test("Alternative") {
    val target1 = new Variable[Double] { override def name = "T1" }
    val target2 = new Variable[Double] { override def name = "T2" }
    val i = new Alternative(target1, target2)
    expect("T1 or alternative T2") { i.name }
    expect(Set(target1, target2)) { i.dependencies }
    expect(None) { i() }

    i.send(EmptyData)
    expect(None) { i() }
    target2.set(1)
    i.send(EmptyData)
    expect(1) { i.value }
    target1.set(2)
    i.send(EmptyData)
    expect(2) { i.value }
    target1.unset()
    i.send(EmptyData)
    expect(1) { i.value }
    target2.unset()
    i.send(EmptyData)
    expect(None) { i() }
  }
}