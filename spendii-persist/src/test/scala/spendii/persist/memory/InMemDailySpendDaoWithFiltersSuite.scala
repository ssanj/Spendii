/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import collection.Seq
import spendii.persist.DailySpendDao.{UserDateRange, DailySpendByUser}
import spendii.{Spendtry, DailySpend}

final class InMemDailySpendDaoWithFiltersSuite extends InMemDBDailySpendDaoCommon  {

  object SuiteName {
    val AN_INMEMDB_SHOULD = "An InMemDB should "
  }

  import TestData._
  import SuiteName._
  test(AN_INMEMDB_SHOULD + "filter DailySpends that match a criteria") {
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    val expected = DailySpendByUser(USER_DATE_2, dao.createDailySpend(USER_DATE_2, SPENDS_2))
    dao.createDailySpend(USER_DATE_3, SPENDS_3)

    val matches = dao.filterDailySpend(ds => ds.exists(_.tags.contains("dinner")))
    matches.size should equal (1)
    matches(0) should equal (expected)
  }

  test(AN_INMEMDB_SHOULD + "not filter DailySpends that do not match a criteria") {
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    val matches = dao.filterDailySpend(ds => ds.exists(_.tags.contains("dinner")))
    matches.isEmpty should be (true)
  }

  test(AN_INMEMDB_SHOULD + "filter DailySpends created by a specific User") { matchCreatedSpends(USER_DATE_1.username) }

  test(AN_INMEMDB_SHOULD + "filter DailySpends created by a specific User - case insensitive") { matchCreatedSpends(USER_DATE_1_MIXED.username) }

  private def matchCreatedSpends(username:String) {
    dao.filterDailySpendByUser(username).isEmpty should equal (true)
    val ds1 = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    val ds2 = dao.createDailySpend(USER_DATE_1_2, SPENDS_2)
    val ds3 = dao.createDailySpend(USER_DATE_1_3, SPENDS_3)

    val matched = dao.filterDailySpendByUser(username)
    matched.isEmpty should equal (false)
    matched.size should equal (3)
    matched.contains(DailySpendByUser(USER_DATE_1, ds1)) should be (true)
    matched.contains(DailySpendByUser(USER_DATE_1_2, ds2)) should be (true)
    matched.contains(DailySpendByUser(USER_DATE_1_3, ds3)) should be (true)
  }


  test(AN_INMEMDB_SHOULD + "filter DailySpends by creation date") {
    dao.filterDailySpendByDate(DATE_1).isEmpty should equal (true)
    val ds1 = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    val ds2 = dao.createDailySpend(USER_DATE_2, SPENDS_2)
    val ds3 = dao.createDailySpend(USER_DATE_4, SPENDS_3)
    dao.filterDailySpendByDate(DATE_1).isEmpty should equal (false)

    var matches: Seq[DailySpendByUser] = dao.filterDailySpendByDate(DATE_1)
    matches.size should equal (2)
    matches.contains(DailySpendByUser(USER_DATE_1, ds1)) should equal (true)
    matches.contains(DailySpendByUser(USER_DATE_2, ds2)) should equal (false)
    matches.contains(DailySpendByUser(USER_DATE_4, ds3)) should equal (true)
  }

  test(AN_INMEMDB_SHOULD + "not filter DailySpends not created by the specified User") {
    dao.filterDailySpendByUser(USER_DATE_1.username).isEmpty should equal (true)
    val ds1 = dao.createDailySpend(USER_DATE_2, SPENDS_2)
    dao.filterDailySpendByUser(USER_DATE_1.username).isEmpty should equal (true)
  }

  test(AN_INMEMDB_SHOULD + "should filter by date range") {
    val dateRange = DATE_2 to DATE_3
    dao.filterDailySpendByDateRange(dateRange).isEmpty should be (true) //30 july to 20 aug
    dao.createDailySpend(USER_DATE_2, SPENDS_2) //*
    dao.createDailySpend(USER_DATE_1, SPENDS_1) //23 july
    dao.createDailySpend(USER_DATE_1_2, SPENDS_3) //*
    val matches = dao.filterDailySpendByDateRange(dateRange)
    matches.isEmpty should be (false)
    matches.size should equal (2)
    matches should contain (DailySpendByUser(USER_DATE_2, DailySpend(USER_DATE_2.date, SPENDS_2: _*)))
    matches should contain (DailySpendByUser(USER_DATE_1_2, DailySpend(USER_DATE_1_2.date, SPENDS_3: _*)))
  }

  test(AN_INMEMDB_SHOULD + "should filter by User and date range") { matchFilterByUSerAndDateRange(USER_DATE_1.username) }

  private def matchFilterByUSerAndDateRange(username:String) {
    val dateRange = DATE_2 to DATE_4
    dao.filterDailySpendByUserDateRange(UserDateRange(USER_1, dateRange)).isEmpty should be (true)  //30th july to 15th september
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.createDailySpend(USER_DATE_1_2, SPENDS_2) //*
    dao.createDailySpend(USER_DATE_2, SPENDS_2)
    dao.createDailySpend(USER_DATE_1_3, SPENDS_3) //*
    dao.createDailySpend(USER_DATE_3, SPENDS_3)
    dao.createDailySpend(USER_DATE_4, SPENDS_1)
    dao.createDailySpend(USER_DATE_1_4, SPENDS_1)
    val matches = dao.filterDailySpendByUserDateRange(UserDateRange(username, dateRange))
    matches.isEmpty should be (false)
    matches.size should equal (2)
    matches should contain (DailySpendByUser(USER_DATE_1_2, DailySpend(USER_DATE_1_2.date, SPENDS_2: _*)))
    matches should contain (DailySpendByUser(USER_DATE_1_3, DailySpend(USER_DATE_1_3.date, SPENDS_3: _*)))

  }

  test(AN_INMEMDB_SHOULD + "should filter by User and date range - case insensitive") { matchFilterByUSerAndDateRange(USER_DATE_1_MIXED.username) }
}