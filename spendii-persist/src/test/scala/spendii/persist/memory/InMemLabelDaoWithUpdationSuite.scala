/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.{DailySpend}

final class InMemLabelDaoWithUpdationSuite extends InMemDBLabelDaoCommon {

  object SuiteName {
    val AN_INMEMDB_SHOULD = "An InMemDB should "
  }

  import SuiteName._
  import TestData._
  test(AN_INMEMDB_SHOULD + "update an existing Label") {
    createAndFindLabels(LABEL_1, LABEL_2)
    dao.updateLabel(LABEL_2, LABEL_3)
    contains(LABEL_1) should be (true)
    contains(LABEL_2) should be (false)
    contains(LABEL_3) should be (true)
  }

  test(AN_INMEMDB_SHOULD + "not update a Label that does not exist") {
    dao.findAllLabels.isEmpty should be (true)
    dao.updateLabel(LABEL_1, LABEL_2)
    dao.findAllLabels.isEmpty should be (true)
  }

  test(AN_INMEMDB_SHOULD + "update any DailySpends with the same Label") {
    dao.createLabel(LABEL_4)
    val expected = DailySpend(USER_DATE_1.date, SPENDS_1: _*)
    val expectedUpdated = DailySpend(USER_DATE_1.date, SPENDS_2: _*)
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    val ds1 = dao.findDailySpendByUserDate(USER_DATE_1)
    ds1 should equal (Some(expected))

    dao.updateLabel(LABEL_4, LABEL_5)
    val ds2 = dao.findDailySpendByUserDate(USER_DATE_1)
    ds2 should not equal (Some(expected))
    ds2 should equal (Some(expectedUpdated))
  }
}