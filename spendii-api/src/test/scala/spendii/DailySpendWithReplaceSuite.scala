/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import spendii.date.Sdate._
import Spimplicits._

final class DailySpendWithReplaceSuite extends FunSuite with ShouldMatchers {

  import TestData._
  test("A DailySpend should replace Spendtries that match") {
    val expected = DailySpend(today, SPENDS_2: _*)
    val ds1 = DailySpend(today, SPENDS_1: _*)
    val ds2 = ds1.replace(_.description == DESC_1, sp => Spendtry(sp.tags, sp.cost, DESC_2))
    ds2 should not equal (ds1)
    ds2 should equal (expected)
  }

  test("A DailySpend should not replace Spendtries that do not match") {
    val ds1 = DailySpend(today, SPENDS_2: _*)
    val ds2 = ds1.replace(_.cost > 1000, sp => Spendtry(sp.tags, 1000.0, sp.description))
    ds1 should equal (ds2)
  }

  object TestData {
    val DESC_1 = "Paintbrush"
    val DESC_2 = "Replacing screwdrivers that were mutilated"
    val SPENDS_1 = List(Spendtry("Book, Tax", 45.0, "Programming in Scala"),
                        Spendtry("Tools", 10.5, DESC_1),
                        Spendtry("Snacks", 12.5, "Krispy Kremes")).flatten
    val SPENDS_2 = List(Spendtry("Book, Tax", 45.0, "Programming in Scala"),
                        Spendtry("Tools", 10.5, DESC_2),
                        Spendtry("Snacks", 12.5, "Krispy Kremes")).flatten
  }
}
