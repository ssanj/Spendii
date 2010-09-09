/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.Spimplicits.dayToDayMonth
import spendii.util.ScalaTestSuite
import SomeDay._
import Month._

final class SomeDayWithDifferenceInDaysSuite extends ScalaTestSuite {

  test("SomeDay should return difference in days between the same day") { (today <-> today) should equal (1) }

  test("SomeDay should return the difference between 2 days of the same month") { (yesterday <-> today) should equal (2) }

  test("SomeDay should return the difference between 2 days irrespective of which day was first/last") {
    ((10|june|2009) <-> (5|june|2009)) should equal (6)
  }

  test("SomeDay should return the difference between dates of differring years") { ((1|january|2009) <-> (10|january|2010)) should equal (375) }

  test("SomeDay should return the difference between dates of differring months") { ((16|april|2006) <-> (28|july|2006)) should equal (104) }

  test("SomeDay should return the difference between dates of the same week") { ((14|december|2009) <-> (18|december|2009)) should equal (5) }
}