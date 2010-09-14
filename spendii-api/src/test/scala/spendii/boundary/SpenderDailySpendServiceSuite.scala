/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import spendii.date.Sdate._
import spendii.date.{Sdate, SdateRange}
import spendii.persist.DailySpendDao.{UserDateRange, DailySpendByUser, UserDate}
import spendii.{Spendtry, DailySpend}

final class SpenderDailySpendServiceSuite extends CommonBoundary {

  val validSpender = new SpenderDailySpendService with ValidDailySpendDao
  val invalidSpender = new SpenderDailySpendService with NullDailySpendDao

  object SuiteName {
    val A_SPENDER_DAILYSPEND_SERVICE_SHOULD = "A SpenderDailySpendService should "
  }

  import SuiteName._
  import TestData._
  import dailyspenddao.ExceptionMessages._

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "allow the addition of a DailySpend") {
    expectSuccess(validSpender.addDailySpend(SPENDER_NAME, today, SPENDS_1), DS_1)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return errors that occur during addition of a DailySpend") {
    expectError(invalidSpender.addDailySpend(SPENDER_NAME, today, SPENDS_1), CREATE_DS_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "locate a DailySpend for a User for a given date") {
    expectSuccess(validSpender.locateDailySpend(SPENDER_NAME, today), Some(DS_1))
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error if an error occurs while locating the DailySpend for a User for a given date") {
    expectError(invalidSpender.locateDailySpend(SPENDER_NAME, today), FIND_DS_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "locate a DailySpend for a User ") {
    expectSuccess(validSpender.locateDailySpendByUser(SPENDER_NAME), List(DSU_1))
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error if an error occurs while locating a DailySpend for a User") {
    expectError(invalidSpender.locateDailySpendByUser(SPENDER_NAME), FILTER_DS_BY_USER_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "locate a DailySpend by date") {
    expectSuccess(validSpender.locateDailySpendByDate(today), List(DSU_1))
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error if an error occurs while locating DailySpends by date ") {
    expectError(invalidSpender.locateDailySpendByDate(today), FILTER_DS_BY_DATE_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "match a DailySpend"){
    expectSuccess(validSpender.matchDailySpend(_.|| == today), List(DSU_1))
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error if an error occurs while matching a DailySpend") {
    expectError(invalidSpender.matchDailySpend(_.|| == today), FILTER_DS_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "update a DailySpend") {
    expectSuccess(validSpender.updateSpends(SPENDER_NAME, today, ds => DS_1), {})
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error if an error occurs while updating a DailySpend") {
    expectError(invalidSpender.updateSpends(SPENDER_NAME, today, ds => DS_1), UPDATE_DS_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "delete a DailySpend") {
    expectSuccess(validSpender.removeDailySpend(SPENDER_NAME, today), {})
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error if an error occurs while deleting a DailySpend") {
    expectError(invalidSpender.removeDailySpend(SPENDER_NAME, today), DELETE_DS_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "locate a DailySpends for a given date range") {
    expectSuccess(validSpender.locateDailySpendByDateRange(yesterday to today), List(DSU_1))
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error when locating DailySpends by a given date range") {
    expectError(invalidSpender.locateDailySpendByDateRange(yesterday to today), FILTER_DS_BY_DATERANGE_FAIL_MSG)
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "locate DailySpends by a User and a date range") {
    expectSuccess(validSpender.locateDailySpendByUserDateRange(SPENDER_NAME, yesterday to today), List(DSU_1))
  }

  test(A_SPENDER_DAILYSPEND_SERVICE_SHOULD + "return an error when locating DailySpends by a given User and date range") {
    expectError(invalidSpender.locateDailySpendByUserDateRange(SPENDER_NAME, yesterday to today), FILTER_DS_BY_USER_DATERANGE_FAIL_MSG)
  }

  trait ValidDailySpendDao extends NullDailySpendDao {

    override def createDailySpend(userDate:UserDate, spends:Seq[Spendtry]) : DailySpend = DS_1
    override def findDailySpendByUserDate(userDate:UserDate) : Option[DailySpend] = Some(DS_1)
    override def filterDailySpendByUser(username:String) : Seq[DailySpendByUser] = List(DSU_1)
    override def filterDailySpendByDate(date:Sdate) : Seq[DailySpendByUser] = List(DSU_1)
    override def filterDailySpendByDateRange(range:SdateRange) : Seq[DailySpendByUser] = List(DSU_1)
    override def filterDailySpendByUserDateRange(udr:UserDateRange) : Seq[DailySpendByUser] = List(DSU_1)
    override def filterDailySpend(f:(DailySpend) => Boolean) : Seq[DailySpendByUser] = List(DSU_1)
    override def updateDailySpend(userDate:UserDate, f:(DailySpend) => DailySpend) { }
    override def deleteDailySpend(userDate:UserDate) { }
  }

}
