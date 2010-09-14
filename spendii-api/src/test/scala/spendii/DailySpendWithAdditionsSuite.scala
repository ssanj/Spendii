/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import spendii.date.Sdate._

final class DailySpendWithAdditionsSuite extends FunSuite with ShouldMatchers {

  test("A DailySpend should add supplied spendtries one at a time") {
    val sp1 = Spendtry("groceries", 30)
    val sp2 = Spendtry("lunch,clothes", 120, "a shopping lunch!")
    val ds = DailySpend(today, List(sp1, sp2).flatten: _*)

    ds.size should equal (2)
    ds.contains(sp1.get) should equal (true)
    ds.contains(sp2.get) should equal (true)
  }

  test("A DailySpend should add multiple spendtries at a time") {
    val sp1 = Spendtry("car", 345, "6-monthly service")
    val sp2 = Spendtry("snack" , 10 ,"Coffee and a croissant")
    val sp3 = Spendtry("parking", 10)

    val ds = DailySpend(yesterday) ++ List(sp1, sp2, sp3).flatten

    ds.size should equal (3)
    ds.contains(sp1.get) should equal (true)
    ds.contains(sp2.get) should equal (true)
    ds.contains(sp3.get) should equal (true)
  }
}
