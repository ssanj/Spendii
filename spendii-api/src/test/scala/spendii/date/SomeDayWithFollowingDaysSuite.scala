/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import Month._
import Sdate._

final class SomeDayWithFollowingDaysSuite extends ScalaTestSuite {

  test("Sdate should add days within a month") {
    (25|november|2009) + 5 should equal (30|november|2009)
    (5|february|2006) + 7 should equal (12|february|2006)
  }

  test("Sdate should add days over months") { (21|october|2007) + 12 should equal (2|november|2007) }

  test("Sdate should add days over years") { (28|november|2009) + 34 should equal (1|january|2010) }
}
