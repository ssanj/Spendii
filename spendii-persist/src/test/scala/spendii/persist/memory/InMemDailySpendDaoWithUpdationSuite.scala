/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import java.lang.String
import collection.Seq
import spendii.persist.DailySpendDao.UserDate
import spendii.{Spendtry, DailySpend}

final class InMemDailySpendDaoWithUpdationSuite extends InMemDBDailySpendDaoCommon {

  import TestData._

  test("An InMemDB should not update a DailySpend that does not exist") {
    dao.findDailySpendByUserDate(USER_DATE_1) should equal(None)
    dao.updateDailySpend(USER_DATE_1, (ds:DailySpend) => fail("Should not get called if the DailySpend does not exist."))
  }

  test("An InMemDB should update an existing DailySpend") { updateByUserDate(USER_DATE_1) }

  test("An InMemDB should update an existing DailySpend - case insensitive") { updateByUserDate(USER_DATE_1_MIXED)}

  private def updateByUserDate(ud:UserDate) {
    val dse1 = DailySpend(USER_DATE_1.date, SPENDS_1: _*)
    val dse2 = DailySpend(USER_DATE_1.date, (SPENDS_1 ::: SPENDS_2): _*)

    val ds = dao.createDailySpend(USER_DATE_1, SPENDS_1)
    ds should equal (dse1)
    dao.updateDailySpend(ud, (ds:DailySpend) => ds ++ SPENDS_2)
    val dsu = dao.findDailySpendByUserDate(ud)

    dsu should not equal (Some(ds))
    dsu should not equal (Some(dse1))
    dsu should equal (Some(dse2))
  }

  test("An InMemDB should add Labels that didn't after a DailySpend is updated") {
    dao.createDailySpend(USER_DATE_1, SPENDS_2)
    var labels1: Seq[String] = dao.findAllLabels
    labels1.size should equal (1)
    labels1.contains(LABEL_4) should be (true)

    dao.updateDailySpend(USER_DATE_1, ds => ds.replace(_.tags.contains(LABEL_4), sp => Spendtry(LABEL_4_AND_5,
      sp.cost, sp.description)))
    dao.findAllLabels.size should equal (2)
    var labels2: Seq[String] = dao.findAllLabels
    labels2.size should equal (2)
    labels2.contains(LABEL_4) should be (true)
    labels2.contains(LABEL_5) should be (true)
  }
}