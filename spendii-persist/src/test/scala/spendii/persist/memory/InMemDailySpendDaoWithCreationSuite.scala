/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.DailySpend
import spendii.persist.UserDoesNotExistException
import spendii.util.IfSomeElseNone._

final class InMemDailySpendDaoWithCreationSuite extends InMemDBDailySpendDaoCommon {

  import TestData._
  test("An InMemDB should create a DailySpend that does not exist") {
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (None)
    val ds = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (Some(ds))
  }

  test("An InMemDB should not create a DailySpend that exists") {
    val ds = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (Some(ds))
    dao.createDailySpend(USER_DATE_1, SPENDS_1) should equal (ds)
  }

  test("An InMemDB should not create a DailySpend that exists - case insensitive") {
    val ds = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (Some(ds))
    dao.createDailySpend(USER_DATE_1_MIXED, SPENDS_1) should equal (ds)
  }

  test("An InMemDB should add Labels from a DailySpend that don't exist") {
    dao.findAllLabels.isEmpty should be (true)
    val ds1 = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findAllLabels.isEmpty should be (false)
    contains(LABEL_1) should be (true)
    contains(LABEL_2) should be (true)
    contains(LABEL_3) should be (true)
  }

  test("An InMemDB should not add DailySpends for users that do not exist") {
    val ex = intercept[UserDoesNotExistException]{
      dao.createDailySpend(USER_DATE_UNKNOWN, SPENDS_1)
    }
    ex.message should equal("The user [meh] does not exist. Please create the user before adding spends.")
  }

  private def contains(label:String): Boolean =  dao.findAllLabels.exists(_.equalsIgnoreCase(label))

}