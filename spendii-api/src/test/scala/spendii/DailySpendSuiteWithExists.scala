/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import spendii.date.SomeDay._

final class DailySpendSuiteWithExists extends FunSuite with ShouldMatchers {

  private def createDailySpend = {
    val spends = List(Spendtry("Misc", 12.5), Spendtry("Lunch", 5.5), Spendtry("Snacks", 7.32, "Chox")).flatten
    DailySpend(today, spends: _*)
  }

  def doWith(ds:DailySpend)(f:DailySpend => Unit) { f(ds) }

  test("A DailySpend should indicate if it contains an Spendtry") {
    doWith(createDailySpend) {ds =>
      ds.exists(_.cost > 10) should equal (true)
      ds.exists(_.tags.contains("misc")) should equal (true)
    }
  }

  test("A DailySpend should indicate if it does not contain a Spendtry") {
    doWith(createDailySpend) {ds =>
      ds.exists(_.tags.contains("blah")) should equal (false)
      ds.exists(_.cost > 20) should equal (false)
    }
  }
}