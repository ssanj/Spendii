/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import spendii.persist.LabelDao

abstract class NullLabelDao extends LabelDao {

  import labeldao.ExceptionMessages._
  override def findAllLabels : Seq[String] = throwRuntime(FIND_ALL_LABELS_FAIL_MSG)

  override def findLabelsByUser(username:String) : Seq[String] = throwRuntime(FIND_LABELS_BY_USER_FAIL_MSG)

  override def createLabel(labels:String*) { throwRuntime(CREATE_LABEL_FAIL_MSG) }

  override def deleteLabel(label:String) { throwRuntime(DELETE_LABEL_FAIL_MSG) }

  override def updateLabel(search:String, replace:String) { throwRuntime(UPDATE_LABEL_FAIL_MSG) }

  private def throwRuntime(m:String) = throw new RuntimeException(m)

}
package labeldao {
  object ExceptionMessages {
    val FIND_ALL_LABELS_FAIL_MSG = "findAllLabels threw an Exception"
    val FIND_LABELS_BY_USER_FAIL_MSG = "findLabelsByUser threw an Exception"
    val CREATE_LABEL_FAIL_MSG = "createLabel threw an Exception"
    val DELETE_LABEL_FAIL_MSG = "deleteLabel threw an Exception"
    val UPDATE_LABEL_FAIL_MSG = "updateLabel threw an Exception"
  }
}
