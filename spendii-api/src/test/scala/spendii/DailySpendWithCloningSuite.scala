/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import date.Sdate._
import Spimplicits._
import util.IfSomeElseNone._

sealed class DailySpendWithCloningSuite extends FunSuite with ShouldMatchers {

  test("A DailySpend should return a clone that is not the same instance as itself"){
    val sp1 = Spendtry("lunch", 17).get
    val sp2 = Spendtry("tools", 340).get
    val sp3 = Spendtry("dessert", 10).get

    val spends = List(sp1, sp2, sp3)
    val ds1 = DailySpend(yesterday, spends: _*)
    val ds2 = ds1 **

    ds2 should equal (ds1)
    ds2 should not be theSameInstanceAs (ds1)
  }

  test("A DailySpend should not be affected by updates to its clone"){
    val sp1 = Spendtry("dinner", 90).get
    val sp2 = Spendtry("parking", 15).get
    val spends = List(sp1, sp2)
    val ds1 = DailySpend(today, spends: _*)
    val ds2 = ds1 **

    ds1 should equal (ds2)
    ds1.size should equal (2) //therefore ds2.?

    val ds3 = ds2.remove(_ == sp1)
    ds1 should not equal (ds3)
    ds2 should not equal (ds3)

    ds3.size should equal (1)
    ds3.contains(sp2) should equal (true)
  }
}
