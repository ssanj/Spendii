/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date
import java.util.{Calendar => Cal}
import Cal._
import SomeDay._

/**
 * A class that can model any date given a function that takes {@code Calendar} and modifies it to the appropriate date.
 */
sealed class SomeDay(dateFunc:(Cal) => Unit) extends Sdate {

  protected val time:Long = {
    dateFunc(cal)
    cal.getTimeInMillis
  }

  override def -(days:Int) : Sdate = applyChange(this, days, delDaysFunc)

  override def +(days:Int) : Sdate = applyChange(this, days, addDaysFunc)
}

object SomeDay {

  def today : SomeDay = new SomeDay((Cal) => {})

  def yesterday : SomeDay =  (today - 1).asInstanceOf[SomeDay]

  def apply(day:Int, month:Month, year:Int) : SomeDay = {
    new SomeDay(createDate(day, month, year))
  }

  def unapply(sdate:Sdate) : Option[(Int, Month, Int)] = {
    val c = sdate.cal
    sdate match {
      case date:SomeDay => Month.getMonth(c.get(MONTH)).map((c.get(DAY_OF_MONTH), _:Month, c.get(YEAR)))
      case _ => None
    }
  }

  private def applyChange(date:Sdate, days:Int, op:(Int, Int) => Int) : SomeDay = {
    val decDateFunc = (newCal: Cal) => {
      date ->> newCal
      changeDays(days)(newCal)(op)
    }

    new SomeDay(decDateFunc)
  }

  private def createDate(day:Int, month:Month, year:Int)(cal:Cal) {
    cal.set(DAY_OF_MONTH, day)
    cal.set(MONTH, month.month)
    cal.set(YEAR, year)
  }

  private def addDaysFunc(base:Int, change:Int) : Int = base + change

  private def delDaysFunc(base:Int, change:Int) : Int = base - change

  private def changeDays(days:Int)(cal:Cal)(op:(Int, Int) => Int) { cal.set(DAY_OF_MONTH, op(cal.get(DAY_OF_MONTH), days)) }

  case class DayMonth(day:Int) { def |(month:Month) : MonthYear = MonthYear(day, month) }

  case class MonthYear(day:Int, month:Month) { def |(year:Int) : Sdate = SomeDay(day, month, year) }
}
