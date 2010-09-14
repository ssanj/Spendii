/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import spendii.date.Sdate._
import spendii.persist.DailySpendDao.{UserDate, DailySpendByUser}
import spendii.Spimplicits._
import spendii._

trait CommonBoundary extends FunSuite with ShouldMatchers {

  protected def expectError[T](result:Either[String, T], message:String) {
    result.fold(l=> l should equal(message), r=> fail("Expected an error but got " + r))
  }

  protected def expectSuccess[T](result:Either[String, T], expected:T) {
    result.fold(l=> fail("Expected a valid object but got " + l), r => r should equal (expected))
  }

 object TestData {
    val SPENDER_NAME = "blah"
    val SPENDER_PASSWORD = "blee"

    val USER_NAME_1 = "aaaa"
    val USER_PASSWORD_1 = "bbbb"
    val USER_NAME_2 = "cccc"
    val USER_PASSWORD_2 = "dddd"

    val NO_OF_USERS = 5

    val USER = User(SPENDER_NAME, SPENDER_PASSWORD)
    val USERS = List(User(USER_NAME_1, USER_PASSWORD_1), User(USER_NAME_2, USER_PASSWORD_2))

    val SPEND_1 = Spendtry("Lunch", 15.25, "blah").get
    val SPENDS_1 = List(SPEND_1)
    val DS_1 = DailySpend(today)
    val DSU_1 = DailySpendByUser(UserDate(USER.username, today), DS_1)

    val LABEL_1 = "Brekky"
    val LABEL_2 = "Lunch"
    val LABEL_3 = "Dinner"
    val LABELS = List(LABEL_1, LABEL_2, LABEL_3)
  }
}
