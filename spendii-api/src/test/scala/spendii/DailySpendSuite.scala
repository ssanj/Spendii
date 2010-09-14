/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import spendii.date.Sdate
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import spendii.date.Month.october
import DailySpend._

sealed class DailySpendSuite extends FunSuite with ShouldMatchers {

  test("A DailySpend should contain the date it was created for"){
    val date: Sdate = Sdate(10, october, 2010)
    val ds = DailySpend(date)
    ds.|| should equal (date)
  }
}
