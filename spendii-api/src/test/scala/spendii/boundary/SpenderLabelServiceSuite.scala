/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

final class SpenderLabelServiceSuite extends CommonBoundary {

  val validSpender = new ValidLabelDao with SpenderLabelService
  val invalidSpender = new NullLabelDao with SpenderLabelService

  object SuiteName {
    val A_SPENDER_LABEL_SERVICE_SHOULD = "A SpenderLabelService should "
  }

  import SuiteName._
  import TestData._
  import labeldao.ExceptionMessages._

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "list all Labels") {
    expectSuccess(validSpender.listLabels, LABELS)
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "return errors on list all Labels") {
    expectError(invalidSpender.listLabels, FIND_ALL_LABELS_FAIL_MSG)
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "locate a labels used by a User") {
    expectSuccess(validSpender.locateLabelsByUser(SPENDER_NAME), List(LABEL_2))
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "return errors on locating lables used by a User") {
    expectError(invalidSpender.locateLabelsByUser(SPENDER_NAME), FIND_LABELS_BY_USER_FAIL_MSG)
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "add a Label") {
    expectSuccess(validSpender.addLabel(LABEL_1), {})
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "return errors on adding a Label") {
    expectError(invalidSpender.addLabel(LABEL_1), CREATE_LABEL_FAIL_MSG)
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "remove a given Label") {
    expectSuccess(validSpender.removeLabel(LABEL_1), {})
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "return errors on removing a given Label") {
    expectError(invalidSpender.removeLabel(LABEL_1), DELETE_LABEL_FAIL_MSG)
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "update a given Label") {
    expectSuccess(validSpender.updateLabelString(LABEL_1, LABEL_2), {})
  }

  test(A_SPENDER_LABEL_SERVICE_SHOULD + "return errors on updating a Label") {
    expectError(invalidSpender.updateLabelString(LABEL_1, LABEL_2), UPDATE_LABEL_FAIL_MSG)
  }

  trait ValidLabelDao extends NullLabelDao {

    override def findAllLabels : Seq[String] = LABELS

    override def findLabelsByUser(username:String) : Seq[String] = List(LABEL_2)

    override def createLabel(labels:String*) { }

    override def deleteLabel(label:String) { }

    override def updateLabel(search:String, replace:String) { }
  }

}