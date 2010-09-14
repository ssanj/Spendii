/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import Sdate._

final class SomeDayWithOrderedSuite extends ScalaTestSuite {

  test("Sdate should order ealier days first") { (yesterday compare today) should equal (-1) }

  test("Sdate should order equal days together") { (today compare today) should equal (0) }

  test("Sdate should order later days last") { (today compare yesterday) should equal (1) }
}
