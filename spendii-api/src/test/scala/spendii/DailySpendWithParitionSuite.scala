/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import spendii.date.Sdate._
import Spimplicits._

final class DailySpendWithParitionSuite extends FunSuite with ShouldMatchers {

  import TestData._
  test("A DailySpend should partion based on a criteria") {
    val ds1 = DailySpend(today, SPENDS_1: _*)
    val expectedCheap = DailySpend(today, SPENDS_CHEAP: _*)
    val expectedExp = DailySpend(today, SPENDS_EXP: _*)
    val (exp, cheap) = ds1.partition(_.cost > 50)
    exp should equal (expectedExp)
    cheap should equal (expectedCheap)
  }

  object TestData {
    val SPENDS_1 = List(Spendtry("Bills, Internet", 119.0, "Bigpond"),
                        Spendtry("Bills, Groceries", 123.34, "misc"),
                        Spendtry("Entertainment, Movies, Snacks", 35.0, "Inception, cold rock, chips etc"),
                        Spendtry("Entertainment, DVD, Blu-ray", 85.0, "Pandorum, Equilibirum")).flatten
    val SPENDS_CHEAP = List(Spendtry("Entertainment, Movies, Snacks", 35.0, "Inception, cold rock, chips etc")).flatten
    val SPENDS_EXP = List(Spendtry("Bills, Internet", 119.0, "Bigpond"),
                        Spendtry("Bills, Groceries", 123.34, "misc"),
                        Spendtry("Entertainment, DVD, Blu-ray", 85.0, "Pandorum, Equilibirum")).flatten
  }
}
