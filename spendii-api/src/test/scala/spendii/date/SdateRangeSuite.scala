/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import Sdate._

final class SdateRangeSuite extends ScalaTestSuite {

  test("An SdateRange should contain start and end dates"){
    val dr = yesterday to today
    dr.startDate should equal (yesterday)
    dr.endDate should equal(today)
  }

  test("An SdateRange should sanitise start dates after end dates") {
    val dr = today to yesterday
    dr.startDate should equal (yesterday)
    dr.endDate should equal (today)
  }
}
