/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import date.Sdate._

final class DailySpendWithFilterSuite extends FunSuite with ShouldMatchers {

  import TestData._

  test("A DailySpend should filter Spendtries") {
    val ds1 = DailySpend(today, SPENDS_1: _*)

    val ds2 = ds1 filter (_.cost > 5)
    ds2.size should equal (3)
    ds2.contains(tolls) should equal (true)
    ds2.contains(lunch) should equal (true)
    ds2.contains(petrol) should equal (true)
  }

  test("A DailySpend should filter Spendtries by inverse") {
    val ds1 = DailySpend(today, SPENDS_1: _*)

    val ds2 = ds1 filterNot (_.cost > 5)
    ds2.size should equal (2)
    ds2.contains(misc) should equal (true)
    ds2.contains(snacks) should equal (true)
  }

  object TestData {
    val tolls = Spendtry("Tolls", 7.5).get
    val misc = Spendtry("Misc", 4.5, "Coffee").get
    val lunch = Spendtry("Lunch", 15).get
    val snacks = Spendtry("Snacks", 2.20, "Cheese Twisties").get
    val petrol = Spendtry("Petrol", 56).get
    val SPENDS_1 = List(tolls, misc, lunch, snacks, petrol)
  }
}

