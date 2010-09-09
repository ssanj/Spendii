/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

final class InMemLabelDaoWithFindersSuite extends InMemDBLabelDaoCommon {

  import TestData._

  test("An InMemDB should not find a Label that does not exist") {
    dao.findAllLabels.isEmpty should be (true)
  }

  test("An InMemDB should find a Label that does exist") {
    dao.findAllLabels.isEmpty should be (true)
    dao.createLabel(LABEL_1)
    dao.findAllLabels.isEmpty should be (false)
    contains(LABEL_1) should be (true)
  }

  test("An InMemDB should find Labels for a specified User") {
    dao.findAllLabels.isEmpty should be (true)
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.createDailySpend(USER_DATE_2, SPENDS_3)
    dao.createDailySpend(USER_DATE_1_2, SPENDS_2)
    dao.createDailySpend(USER_DATE_1_3, SPENDS_4)
    dao.findAllLabels.isEmpty should be (false)

    val labels = dao.findLabelsByUser(USER_DATE_1.username)
    labels.isEmpty should be (false)
    labels.size should be (5)
    Array(LABEL_2, LABEL_3, LABEL_4, LABEL_5, LABEL_7).map(_.toLowerCase).foreach(labels.contains(_) should be (true))
  }
}