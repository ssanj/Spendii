/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import spendii.persist.DailySpendDao
import spendii.boundary.dailyspenddao.ExceptionMessages._
import spendii.date.{SdateRange, Sdate}
import spendii.persist.DailySpendDao.{UserDateRange, DailySpendByUser, UserDate}
import spendii.{Spendtry, DailySpend}

trait NullDailySpendDao extends DailySpendDao {

  override def createDailySpend(userDate:UserDate, spends:Seq[Spendtry]) : DailySpend = throwRuntime(CREATE_DS_FAIL_MSG)
  override def findDailySpendByUserDate(userDate:UserDate) : Option[DailySpend] = throwRuntime(FIND_DS_FAIL_MSG)
  override def filterDailySpendByUser(username:String) : Seq[DailySpendByUser] = throwRuntime(FILTER_DS_BY_USER_FAIL_MSG)
  override def filterDailySpendByDate(date:Sdate) : Seq[DailySpendByUser] = throwRuntime(FILTER_DS_BY_DATE_FAIL_MSG)
  override def filterDailySpendByDateRange(range:SdateRange) : Seq[DailySpendByUser] = throwRuntime(FILTER_DS_BY_DATERANGE_FAIL_MSG)
  override def filterDailySpendByUserDateRange(udr:UserDateRange) : Seq[DailySpendByUser] = throwRuntime(FILTER_DS_BY_USER_DATERANGE_FAIL_MSG)
  override def filterDailySpend(f:(DailySpend) => Boolean) : Seq[DailySpendByUser] = throwRuntime(FILTER_DS_FAIL_MSG)
  override def updateDailySpend(userDate:UserDate, f:(DailySpend) => DailySpend) { throwRuntime(UPDATE_DS_FAIL_MSG) }
  override def deleteDailySpend(userDate:UserDate) { throwRuntime(DELETE_DS_FAIL_MSG) }

  private def throwRuntime(m:String) = throw new RuntimeException(m)
}

package dailyspenddao {
  object ExceptionMessages {
   val CREATE_DS_FAIL_MSG = "createDailySpend threw an Exception"
   val FIND_DS_FAIL_MSG = "findDailySpend threw an Exception"
   val FILTER_DS_BY_USER_FAIL_MSG = "filterDailySpendByUser threw an Exception"
   val FILTER_DS_BY_DATE_FAIL_MSG = "filterDailySpendByDate threw an Exception"
   val FILTER_DS_FAIL_MSG = "filterDailySpend threw an Exception"
   val FILTER_DS_BY_DATERANGE_FAIL_MSG = "filterDailySpendByDateRange threw an Exception"
   val FILTER_DS_BY_USER_DATERANGE_FAIL_MSG = "filterDailySpendByUserDateRange threw an Exception"
   val UPDATE_DS_FAIL_MSG = "updateDailySpend threw an Exception"
   val DELETE_DS_FAIL_MSG = "deleteDailySpend threw an Exception"
  }

}