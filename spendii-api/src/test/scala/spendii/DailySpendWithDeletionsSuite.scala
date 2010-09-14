/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import date.Sdate._
sealed class DailySpendWithDeletionsSuite extends FunSuite with ShouldMatchers {

  test("A DailySpend should delete specified existing spendtries"){
    val sp1 = Spendtry("brekky", 10).get
    val sp2 = Spendtry("lunch", 20).get
    val sp3 = Spendtry("dinner", 30).get
    val sp4 = Spendtry("concert", 200).get
    val spends1 = List(sp1, sp2, sp3, sp4)

    val ds1 = DailySpend(today, spends1: _*)
    ds1.size should equal (4)
    verifySpend(ds1, sp1)
    verifySpend(ds1, sp2)
    verifySpend(ds1, sp3)
    verifySpend(ds1, sp4)

    val ds2 = ds1.remove(_.tags == Seq("lunch"))
    ds2.size should equal (3)
    verifySpend(ds2, sp1)
    verifySpend(ds2, sp3)
    verifySpend(ds2, sp4)

    val ds3 = ds2.remove(_.cost > 100)
    ds3.size should equal (2)
    verifySpend(ds3, sp1)
    verifySpend(ds3, sp3)

    val ds4 = ds3.remove(_ == sp1)
    ds4.size should equal (1)
    verifySpend(ds4, sp3)

    val ds5 = ds4.remove(sp => sp.tags == Seq("dinner") && sp.cost == 30)
    ds5.isEmpty should equal (true)
  }

  test("A DailySpend should not delete spendtries it does not have") {
    val sp1 = Spendtry("lunch", 20).get
    val sp2 = Spendtry("dinner", 30).get
    val sp3 = Spendtry("misc", 45).get
    val spends = List(sp1, sp2)


    val ds1 = DailySpend(today, spends: _*)
    ds1.size should equal (2)
    verifySpend(ds1, sp1)
    verifySpend(ds1, sp2)

    val ds2 = ds1.remove(_.tags == Seq("misc"))
    ds2.size should equal (2)
    verifySpend(ds1, sp1)
    verifySpend(ds1, sp2)
  }

  def verifySpend(ds:DailySpend, sp:Spendtry) { ds.contains(sp) should equal (true) }
}
