/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.persist.DailySpendDao.UserDate

final class InMemDailySpendDaoWithDeletionSuite extends InMemDBDailySpendDaoCommon {

  import TestData._

  test("An InMemDB should delete an existing DailySpend") { deleteDailySpend(USER_DATE_1) }

  test("An InMemDB should delete an existing DailySpend - case insensitive") { deleteDailySpend(USER_DATE_1_MIXED) }

  private def deleteDailySpend(ud:UserDate) {
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1).isDefined should be (true)
    dao.deleteDailySpend(ud)
    dao.findDailySpendByUserDate(ud).isDefined should be (false)
  }

  test("An InMemDB should not delete a DailySpend that does not exist") {
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.findDailySpendByUserDate(USER_DATE_1).isDefined should be (true)
    dao.deleteDailySpend(USER_DATE_2)
    dao.findDailySpendByUserDate(USER_DATE_1).isDefined should be (true)
  }
}