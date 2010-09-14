/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import date.Sdate._
import date.Month._
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{TestFailedException, BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}
import util.EqualsAndHashCodeConstraints._
import util.ScalaTestSuite

final class DailySpendWithEqualsAndHashCodeSuite extends ScalaTestSuite with EqualsConstraints[DailySpend]
        with StrictHashCodeConstraints[DailySpend] {

  val globalSpends1_1 = List(Spendtry("groceries", 10), Spendtry("parking", 20), Spendtry("Snacks", 5.5)).flatten
  val globalSpends1_2 = List(Spendtry("Snacks", 5.5), Spendtry("parking", 20), Spendtry("groceries", 10)) .flatten
  val globalSpends1_3 = List(Spendtry("parking", 20), Spendtry("groceries", 10), Spendtry("Snacks", 5.5)).flatten
  val globalSpends2 = List(Spendtry("groceries", 10), Spendtry("blah", 50)).flatten
  val globalSpends3 = List(Spendtry("dinner", 20).get)
  val differingByDate = DailySpend(yesterday, globalSpends1_1)
  val differingBySpendtries = DailySpend(today, globalSpends2)
  val differingByBoth = DailySpend(24|june|2010, globalSpends3)

  override def objectName = "A DailySpend"

  override val equalObjects = Seq(DailySpend(today, globalSpends1_1), DailySpend(today, globalSpends1_2), DailySpend(today, globalSpends1_3))

  override val differentObjects = Seq(differingByDate, differingBySpendtries, differingByBoth)
}

