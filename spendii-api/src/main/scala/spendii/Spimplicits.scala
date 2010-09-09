/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import date.SomeDay.DayMonth
import date.{RichSdate, Sdate}

/**
 * Contains all implicit conversions for Spendii.
 */
object Spimplicits {

  implicit def seqToString(tags:Seq[String]) : String = tags.mkString(",")

  /**
   * Converts an Int -> DayMonth -> Sdate.
   * Eg. 10 | january | 2010 -> Sdate
   */
  implicit def dayToDayMonth(day:Int) : DayMonth =  DayMonth(day)
}
