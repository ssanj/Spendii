/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import date.SomeDay._
import Spimplicits._

final class DailySpendWithTotalsSuites extends FunSuite with ShouldMatchers {

  test("A DailySpend should tally up the cost of all spendtries"){
    val spends = List(Spendtry("brekky", 10.50), Spendtry("lunch", 20.2), Spendtry("dinner", 50)).flatten
    val ds = DailySpend(today, spends: _*)
    ds.$$ should equal (81.0d)
  }

  test("A DailySpend should round tallies to the nearest dollar."){
    val spends = List(Spendtry("brekky", 53.04), Spendtry("lunch", 100.22), Spendtry("dinner", 250.14)).flatten
    val ds = DailySpend(today, spends: _*)
    ds.$$ should equal (404.0d)
  }

}