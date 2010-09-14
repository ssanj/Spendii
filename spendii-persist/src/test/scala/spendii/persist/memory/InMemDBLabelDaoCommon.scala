/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import org.scalatest.{FunSuite, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import spendii.date.Month.{july, august}
import spendii.persist.{DailySpendDao}
import spendii.persist.DailySpendDao.{UserDate}
import spendii.Spimplicits._
import spendii._
import spendii.date.Sdate._

trait InMemDBLabelDaoCommon extends FunSuite with ShouldMatchers with BeforeAndAfterEach{

  val dao = InMemDB

  override def beforeEach() {
    import TestData._
    dao.createUser(USER_DATE_1.username, "lalala")
    dao.createUser(USER_DATE_2.username, "brebrebre")
  }

  override def afterEach() {dao.clearAll}

  def createAndFindLabels(labels:String *) {
    for (label <- labels) {
      dao.findAllLabels.exists(_.equalsIgnoreCase(label)) should be (false)
      dao.createLabel(label)
      dao.findAllLabels.exists(_.equalsIgnoreCase(label)) should be (true)
    }
  }

  protected def contains(label:String): Boolean = dao.findAllLabels.exists(_.equalsIgnoreCase(label))

  object TestData {
    val LABEL_1 = "Breakfast"
    val LABEL_1_MIXED = "BreAkFast"
    val LABEL_2 = "Lunch"
    val LABEL_3 = "Dinner"
    val LABEL_4 = "Snacks"
    val LABEL_5 = "Snax"
    val LABEL_6 = "Junkfood"
    val LABEL_7 = "Presents"
    val LABEL_2_AND_4 = LABEL_2 + "," + LABEL_4
    val LABEL_2_AND_5 = LABEL_2 + "," + LABEL_5

    val USER_DATE_1 = UserDate("sanj", 15 | july | 2010)
    val USER_DATE_1_2 = UserDate("sanj", 25 | july | 2010)
    val USER_DATE_1_3 = UserDate("sanj", 11 | august | 2010)
    val USER_DATE_2 = UserDate("jazz", 16 | july | 2010)
    val SPENDS_1 = List(Spendtry(LABEL_2_AND_4, 20.5, "blah"), Spendtry(LABEL_3, 45.43, "blee"), Spendtry(LABEL_4, 5.05, "Chocolate")).flatten
    val SPENDS_2 = List(Spendtry(LABEL_2_AND_5, 20.5, "blah"), Spendtry(LABEL_3, 45.43, "blee"), Spendtry(LABEL_5, 5.05, "Chocolate")).flatten
    val SPENDS_3 = List(Spendtry(LABEL_6, 15.5, "pizza")).flatten
    val SPENDS_4 = List(Spendtry(LABEL_7, 69.0, "Slow cooker"), Spendtry(LABEL_4, 12.5, "Cold rock")).flatten
  }

}
