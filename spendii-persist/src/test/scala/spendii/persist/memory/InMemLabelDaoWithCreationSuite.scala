/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import collection.Seq
import java.lang.String

final class InMemLabelDaoWithCreationSuite extends InMemDBLabelDaoCommon {

  import TestData._

  test("An InMemDB should create a Label that does not exist") {
    containsLabels(LABEL_1, LABEL_2, LABEL_3) should be (false)
    dao.createLabel(LABEL_1, LABEL_2, LABEL_3)
    containsLabels(LABEL_1, LABEL_2, LABEL_3) should be (true)
  }

  test("An InMemDB should not duplicate a Label") {
    dao.createLabel(LABEL_1)
    verifySingleLabel(dao.findAllLabels, LABEL_1)
    dao.createLabel(LABEL_1)
    verifySingleLabel(dao.findAllLabels, LABEL_1)
    dao.createLabel(LABEL_1_MIXED)
    verifySingleLabel(dao.findAllLabels, LABEL_1)
  }

  private def containsLabels(labels:String*) : Boolean = labels.foldLeft(true)((a,b) => a && dao.findAllLabels.exists(_.equalsIgnoreCase(b)))

  private def verifySingleLabel(labels:Seq[String], label:String) {
    labels.size should equal (1)
    labels.exists(l => l.equalsIgnoreCase(label)) should equal (true)
  }

}