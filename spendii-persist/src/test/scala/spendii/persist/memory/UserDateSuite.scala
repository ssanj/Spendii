/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import spendii.persist.DailySpendDao.UserDate
import spendii.date.SomeDay._

final class UserDateSuite extends FunSuite with ShouldMatchers {

  object SuiteName {
    val A_USERDATE_SHOULD = "A UserDate should "
  }

  import SuiteName._
  test(A_USERDATE_SHOULD + "be equal irrespective of username case") {
    val ud1 = UserDate("Jazzy", today)
    val ud2 = UserDate("jazzy", today)
    ud1 should equal (ud2)
  }

  test(A_USERDATE_SHOULD + "have equal hashCodes irrespective of username case") {
    val ud1 = UserDate("Sanj", yesterday)
    val ud2 = UserDate("sAnJ", yesterday)
    ud1.hashCode should equal (ud2.hashCode)
  }

  test(A_USERDATE_SHOULD + "not equal UserDates of different Users") {
    val ud1 = UserDate("Annie", today)
    val ud2 = UserDate("Ann", today)
    ud1 should not equal (ud2)
  }

  test(A_USERDATE_SHOULD + "not equal UserDates of different dates") {
    val ud1 = UserDate("Scala", today)
    val ud2 = UserDate("Scala", yesterday)
    ud1 should not equal (ud2)
  }

}