/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import java.util.{Calendar => Cal, Date}
import Cal._
import SomeDay._
import spendii.util.ScalaTestSuite
import Month._
import spendii.Spimplicits.dayToDayMonth

final class SomeDaySuite extends ScalaTestSuite {

  test("SomeDay should have a formatted toString representation") {
   (25|december|2008).toString should equal ("Thursday 25 December 2008")
  }

  test("SomeDay should return the date for today") { (today ->) should equal (getTodaysDate) }

  test("SomeDay should return the date for yesterday") { (yesterday ->) should equal (getYesterdaysDate) }

  test("SomeDay should create a specific days") {
    assertDay(SomeDay(10, January(), 2009), 10, JANUARY, 2009)
    assertDay(SomeDay(11, July(), 2006), 11, JULY, 2006)
  }

  test("SomeDay should convert to a Java Date") {
    (SomeDay(10, October(), 2010) ->) should equal (createJavaDateWithouTtime(10, OCTOBER, 2010))
  }

  test("SomeDay should clone its Calendars state") {
    val day = SomeDay(11, November(), 2008)
    val newCal = Cal.getInstance
    day ->> newCal
    assertCal(newCal, 11, NOVEMBER, 2008)
  }

  private def getTodaysCal: Cal = {
    val cal = Cal.getInstance
    cal.set(HOUR, 0)
    cal.set(MINUTE, 0)
    cal.set(SECOND, 0)
    cal.set(MILLISECOND, 0)
    cal
  }

  private def getTodaysDate: Date = { getTodaysCal getTime }

  private def getYesterdaysDate: Date = {
    val cal = getTodaysCal
    cal.set(DAY_OF_MONTH, cal.get(DAY_OF_MONTH) - 1)
    cal.getTime
  }

  private def assertDay(date:Sdate, day:Int, month:Int, year:Int) {
    val cal = Cal.getInstance
    cal.setTimeInMillis((date ->).getTime)
    assertCal(cal, day, month, year)
  }

  private def assertCal(cal:Cal, day:Int, month:Int, year:Int) {
    cal.get(DAY_OF_MONTH) should equal (day)
    cal.get(MONTH) should equal (month)
    cal.get(YEAR) should equal (year)
  }

  private def createJavaDateWithouTtime(day:Int, month:Int, year:Int): Date = {
    val cal = createDay(day, month, year)
    cal.set(HOUR, 0)
    cal.set(MINUTE, 0)
    cal.set(SECOND, 0)
    cal.set(MILLISECOND, 0)
    cal.getTime
  }

  private def createDay(day:Int, month:Int, year:Int): Cal = {
    val cal = Cal.getInstance
    cal.set(DAY_OF_MONTH, day)
    cal.set(MONTH, month)
    cal.set(YEAR, year)
    cal
  }
}
