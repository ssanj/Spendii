/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.Spimplicits.dayToDayMonth
import spendii.util.ScalaTestSuite
import Month._

final class SomeDayWithPreviousDaysSuite extends ScalaTestSuite {

  test("SomeDay should reduce days within a month") {
    (24|november|2000) - 4 should equal (20|november|2000)
    (28|april|2009) - 20 should equal (8|april|2009)
  }

  test("SomeDay should reduce days over months") { (11|july|2008) - 11 should equal (30|june|2008) }

  test("SomeDay should reduce days over years") { (10|january|2008) - 12 should equal (29|december|2007) }
}