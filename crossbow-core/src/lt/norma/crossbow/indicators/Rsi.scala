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

import lt.norma.crossbow.messages.BarClose

/** Calculates Relative Strength Index of the specified indicator. Indicator's value is not set
  * until there are at least `period` bars collected.
  * {{{
  * RS = EMA(Period, UpValues) / EMA(Period, DownValues)
  * RSI = 100 - 100 / (1 + RS)
  * }}} */
class Rsi(period: Int, indicator: Indicator[Double] with History)
  extends ListenerIndicator[Double] {
  def name = "RSI(" + period + "; " + indicator.name + ")"

  if (period < 1)
    throw new IllegalArgumentException("Period of " + name + " indicator cannot be less than 1")

  private val upEma = new Ema(period, new ListenerIndicator[Double] {
    def name = "RSI_UP(" + indicator.name + ")"

    def dependencies = Set(indicator)

    def receive = {
      case BarClose(_) => (indicator(), indicator.history.lastSet) match {
        case (Some(v), Some(l)) if (v > l) => set(v - l)
        case (Some(v), Some(l)) => set(0.0)
        case _ => set(None)
      }
    }
  })
  private val downEma = new Ema(period, new ListenerIndicator[Double] {
    def name = "RSI_DOWN(" + indicator.name + ")"

    def dependencies = Set(indicator)

    def receive = {
      case BarClose(_) => (indicator(), indicator.history.lastSet) match {
        case (Some(v), Some(l)) if (v < l) => set(l - v)
        case (Some(v), Some(l)) => set(0.0)
        case _ => set(None)
      }
    }
  })

  def dependencies = Set(upEma, downEma)

  def receive = {
    case BarClose(_) => (upEma(), downEma()) match {
      case (Some(up), Some(0.0)) => set(100.0)
      case (Some(up), Some(down)) => set(100.0 - 100.0 / (1.0 + up / down))
      case _ => set(None)
    }
  }
}
