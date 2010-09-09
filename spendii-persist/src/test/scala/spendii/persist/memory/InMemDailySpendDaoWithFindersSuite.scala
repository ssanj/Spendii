/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

final class InMemDailySpendDaoWithFindersSuite extends InMemDBDailySpendDaoCommon {

  import TestData._

  test("An InMemDB should not find a non-existant DailySpend") {
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (None)
  }

  test("An InMemDB should find an existing DailySpend") {
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (None)
    val ds = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1) should equal (Some(ds))
  }

  test("An InMemDB should find an existing DailySpend - case insensitive") {
    dao.findDailySpendByUserDate(USER_DATE_1_MIXED) should equal (None)
    val ds = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1_MIXED) should equal (Some(ds))
  }
}