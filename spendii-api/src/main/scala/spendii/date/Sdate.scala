/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date


import java.util.{Calendar => Cal, Date}
import Cal._
import scala.Math.signum
import Sdate._

/**
 * Models a date on which spending occurs (which is any day really!) The {@code java.util.Calendar} class has be semi-wrapped for the
 * functionality of this class.
 */
final class Sdate private(f:(Cal) => Cal) extends Ordered[Sdate] {

  /**
   * {@code Calendar} instance with its time component initialized.
   */
  private val cal:Cal = f(removeTime(Cal.getInstance))

  /**
   * The date, as time in milliseconds.
   */
  private val time:Long = cal.getTimeInMillis

  /**
   * Removes the time-component (hh:mm:ss:ms) of a {@code java.util.Calendar} instance.
   */
  private def removeTime(cal:Cal): Cal = {
    cal.set(HOUR, 0)
    cal.set(MINUTE, 0)
    cal.set(SECOND, 0)
    cal.set(MILLISECOND, 0)
    cal
  }
  
  private def getDays(timePeriod:Long): Int =  (timePeriod / MILS_IN_A_DAY).toInt
  
  private def alterDays(days:Int, f:(Int, Int) => Int) = 
    (newCal:Cal) => {
      this.->>(newCal)
      newCal.set(DAY_OF_MONTH, f(cal.get(DAY_OF_MONTH), days))
      newCal
    }          

  /**
   * Returns a {@code Date} representation of this Sdate.
   */
  def -> : Date = new Date(time)

  /**
   * Removes the supplied number of days from the this Sdate and returns a new Sdate in the past relative to this date.
   */
  def -(days:Int): Sdate = new Sdate(alterDays(days, _ - _))
  
  
  /**
   * Adds the supplied number of days to the this Sdate and returns a new Sdate in the future relative to this date.
   */
  def +(days:Int): Sdate = new Sdate(alterDays(days, _ + _))
  
  /**
   * Returns the number of days between this date and the other date inclusive.
   * If the other date is the same date as this the value should be 1.
   * Eg. today <->(today) == 1
   * Eg. yesterday <->(today) == 2
   * Eg. Monday <->(Friday) == 5
   *
   * If other is before this date, then the answer will be the same as above.
   *
   * Eg. today <->(yesterday) == 2
   *
   */
  def <->(other:Sdate) : Int = {
    getDays(math.abs(time - other.time)) + 1
  }

  /**
   * Copies the state of this Sdate's Cal into the supplied newCal.
   */
  def ->>(newCal:Cal) {
    newCal.set(DAY_OF_MONTH, cal.get(DAY_OF_MONTH))
    newCal.set(MONTH, cal.get(MONTH))
    newCal.set(YEAR, cal.get(YEAR))
  }

  override def equals(obj:Any) : Boolean = {
    obj match {
      case otherDay:Sdate => time == otherDay.time
      case _ => false
    }
  }

  override def hashCode = time.hashCode

  override def compare(that:Sdate) = math.signum(time - that.time).toInt

  override def toString = String.format("%1$tA %1$te %1$tB %1$tY", ->)
}

object Sdate {
    
  def today: Sdate = new Sdate(cal => cal)

  def yesterday: Sdate =  (today - 1)
  
  def apply(day:Int, month:Month, year:Int): Sdate = {
    new Sdate(newCal => {
      newCal.set(DAY_OF_MONTH, day)
      newCal.set(MONTH, month.month)
      newCal.set(YEAR, year)
      newCal
    })
  }  
  
  case class DayMonth(day:Int) { def |(month:Month): MonthYear = MonthYear(day, month) }

  case class MonthYear(day:Int, month:Month) { def |(year:Int): Sdate = Sdate(day, month, year) }
 
  /**
   * Converts an Int -> DayMonth -> Sdate.
   * Eg. 10 | january | 2010 -> Sdate
   */  
  implicit def dayToDayMonth(day:Int): DayMonth =  DayMonth(day)  
  
 /**
  * Converts an Sdate to a RichSdate  to a SdateRange.
  * Eg. Sdate1 to Sdate2, Sdate1 until Sdate2.
  */
  implicit def sDateToRichSdate(startDate:Sdate) : RichSdate = new RichSdate(startDate)
    
  private val MILS_IN_A_DAY:Long = 86400000
}
