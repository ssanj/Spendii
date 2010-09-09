/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist

import spendii.date.{SdateRange, Sdate}
import spendii.persist.DailySpendDao.{UserDateRange, DailySpendByUser, UserDate}
import spendii.{Spendtry, DailySpend}

trait DailySpendDao {

  def createDailySpend(userDate:UserDate, spends:Seq[Spendtry]) : DailySpend

  def findDailySpendByUserDate(userDate:UserDate) : Option[DailySpend]
  def filterDailySpendByUser(username:String) : Seq[DailySpendByUser]
  def filterDailySpendByDate(date:Sdate) : Seq[DailySpendByUser]
  def filterDailySpendByDateRange(range:SdateRange) : Seq[DailySpendByUser]
  def filterDailySpendByUserDateRange(udr:UserDateRange) : Seq[DailySpendByUser]
  def filterDailySpend(f:(DailySpend) => Boolean) : Seq[DailySpendByUser]
  def updateDailySpend(userDate:UserDate, f:(DailySpend) => DailySpend)
  def deleteDailySpend(userDate:UserDate)
}

object DailySpendDao {
  final case class UserDate(username:String, date:Sdate) {
    override def equals(obj:Any): Boolean = {
      obj match {
        case UserDate(u, d) => (username.equalsIgnoreCase(u) && date == d)
        case _ => false
      }
    }

    override def hashCode: Int = (41 * username.toLowerCase.hashCode) + date.hashCode
  }

  final case class UserDateRange(username:String, range:SdateRange)

  final case class DailySpendByUser(ud:UserDate, ds:DailySpend)
}