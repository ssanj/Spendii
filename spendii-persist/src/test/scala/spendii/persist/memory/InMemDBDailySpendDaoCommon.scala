/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import org.scalatest.{FunSuite, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import spendii.date.Month.{july, august, september}
import spendii.Spimplicits.dayToDayMonth
import spendii.persist.{UserDao, LabelDao, DailySpendDao}
import spendii.persist.DailySpendDao.{UserDate}
import spendii.Spimplicits._
import spendii._

trait InMemDBDailySpendDaoCommon extends FunSuite with ShouldMatchers with BeforeAndAfterEach {
  val dao = InMemDB

  override def beforeEach() {
    import TestData._
    dao.createUser(USER_DATE_1.username, "blah")
    dao.createUser(USER_DATE_2.username, "blah")
    dao.createUser(USER_DATE_3.username, "blah")
    dao.createUser(USER_DATE_4.username, "hannah")
  }

  override def afterEach() {dao.clearAll}

  object TestData {
    val LABEL_1 = "lunch"
    val LABEL_2 = "parking"
    val LABEL_3 = "bfpg"
    val LABEL_4 = "dinner"
    val LABEL_5 = "dessert"
    val LABEL_2_AND_3 = LABEL_2 + ", " + LABEL_3
    val LABEL_4_AND_5 = LABEL_4 + ", " + LABEL_5

    val DATE_1 = 23 | july | 2010
    val DATE_2 = 30 | july | 2010
    val DATE_3 = 20 | august | 2010
    val DATE_4 = 15 | september | 2010
    val DATE_5 = 15 | august | 2010
    val DATE_6 = 4 | august | 2010
    val DATE_7 = 1 | september | 2010
    val DATE_8 = 17 | august | 2010
    val DATE_9 = 30 | september | 2010

    val USER_1 = "sanj"
    val USER_1_MIXED = "SanJ"
    val USER_2 = "annie"
    val USER_3 = "jazzy"
    val USER_4 = "sam"
    val USER_5 = "meh"

    val USER_DATE_1 = UserDate(USER_1, DATE_1)
    val USER_DATE_1_MIXED = UserDate(USER_1_MIXED, DATE_1)
    val USER_DATE_1_2 = UserDate(USER_1, DATE_6)
    val USER_DATE_1_3 = UserDate(USER_1, DATE_7)
    val USER_DATE_1_4 = UserDate(USER_1, DATE_9)

    val USER_DATE_2 = UserDate(USER_3, DATE_5)
    val USER_DATE_3 = UserDate(USER_2, DATE_8)
    val USER_DATE_4 = UserDate(USER_4, DATE_1)
    val USER_DATE_UNKNOWN = UserDate(USER_5, DATE_6)

    val SPENDS_1 = List(Spendtry(LABEL_1, 20.0, "blah"), Spendtry(LABEL_2_AND_3, 8.5, "ra")).flatten
    val SPENDS_2 = List(Spendtry(LABEL_4, 34.56, "Wagamama")).flatten
    val SPENDS_3 = List(Spendtry("subscription", 124.00, "magazine"), Spendtry("ebook", 34.95, "working with git")).flatten
  }
}