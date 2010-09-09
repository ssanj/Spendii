/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.persist.LabelIsBoundException

final class InMemLabelDaoWithDeletionSuite extends InMemDBLabelDaoCommon {

  object SuiteName {
    val AN_INMEMDB_SHOULD = "An InMemDB should "
  }

  import TestData._
  import SuiteName._
  test(AN_INMEMDB_SHOULD + "delete an existing Label") {
    createAndFindLabels(LABEL_1, LABEL_2, LABEL_3)
    dao.deleteLabel(LABEL_2)
    contains(LABEL_1) should be (true)
    contains(LABEL_2) should be (false)
    contains(LABEL_3) should be (true)
  }

  test(AN_INMEMDB_SHOULD + "not delete non-existant Labels") {
    createAndFindLabels(LABEL_1)
    dao.deleteLabel(LABEL_2)
    contains(LABEL_1) should be (true)
  }

  test(AN_INMEMDB_SHOULD + "not delete a Label that is bound to a Spendtry") {
    createAndFindLabels(LABEL_1, LABEL_2)
    dao.createDailySpend(USER_DATE_1, SPENDS_1) //label_2 is used in spends_1
    val error = intercept[LabelIsBoundException] {
      dao.deleteLabel(LABEL_2)
    }
    error.getMessage should equal ("Could not delete label: [Lunch] as it is being used by other entries.")
    contains(LABEL_1) should be (true)
    contains(LABEL_2) should be (true)
  }

  test(AN_INMEMDB_SHOULD + "delete a Label that is not bound to a Spendtry") {
    createAndFindLabels(LABEL_1, LABEL_2)
    dao.createDailySpend(USER_DATE_1, SPENDS_1)
    dao.deleteLabel(LABEL_1) //label_1 is not used in spends_1
    contains(LABEL_1) should be (false)
    contains(LABEL_2) should be (true)
  }
}