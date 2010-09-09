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

  /**
   * Converts Sdate to RichSdate for Range-like functionality.
   */
  implicit def sDateToRichSdate(startDate:Sdate) : RichSdate = new RichSdate(startDate)

  implicit def seqToString(tags:Seq[String]) : String = tags.mkString(",")

  /**
   * Converts an Int -> DayMonth -> Sdate.
   * Eg. 10 | january | 2010 -> Sdate
   */
  implicit def dayToDayMonth(day:Int) : DayMonth =  DayMonth(day)
}