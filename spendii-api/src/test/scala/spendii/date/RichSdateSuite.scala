/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import Sdate._
import Month._

final class RichSdateSuite extends ScalaTestSuite {

  test("A RichSdate should creat a date range from start to finish") { verifyRange(to(yesterday, today), yesterday, today) }

  test("A RichSdate should creat a date range from start until finish") {
    val start = 10|february|2009
    val finish = 15|february|2009
    val range = until(start, finish)

    verifyRange(range, start, 14|february|2009)
  }

  test("A RichSdate should creat a date range from start until start") {
    val start = 1|january|2010
    val finish = 1|january|2010
    val range = until(start, finish)

    verifyRange(range, start, start)
  }

  test("A RichSdate should creat a date range from finish to start") { verifyRange(to(today, yesterday), yesterday, today) }

  test("A RichSdate should creat a date range from finish until start") {
    val start = 10|march|2006
    val finish = 5|march|2006
    val range = until(start, finish)

    verifyRange(range, 4|march|2006, start)
  }

  private def verifyRange(range:SdateRange, start:Sdate, finish:Sdate) {
    range.startDate should equal (start)
    range.endDate should equal (finish)
  }

  private def to(start:Sdate, end:Sdate): SdateRange = new RichSdate(start).to(end)

  private def until(start:Sdate, end:Sdate): SdateRange = new RichSdate(start).until(end)

}
