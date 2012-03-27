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

package lt.norma.crossbow.core

import org.joda.time.{ DateMidnight, LocalTime }

case class TradingSchedule(validFrom: DateMidnight, validTil: DateMidnight) {
  import TradingSchedule._



  /*case class Schedule(scheduleStart: DateMidnight, scheduleEnd: DateMidnight, tradingHours)
  val schedules = Map[(DateMidnight, DateMidnight), Session]()*/
}

object TradingSchedule {
  implicit def foreverToDateTime(forever: Forever): DateMidnight = new DateMidnight(0)
  implicit def presentToDateTime(forever: Forever): DateMidnight = new DateMidnight(Long.MaxValue)
  trait Forever
  object Forever extends Forever
  trait Present
  object Present extends Present
}


// TODO uzbaigti