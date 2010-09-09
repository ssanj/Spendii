/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import collection.Seq
import spendii.persist.{UserDoesNotExistException, UserDao, LabelDao, DailySpendDao}
import spendii.date.{SdateRange, Sdate}
import spendii.util.IfSomeElseNone._
import spendii.persist.DailySpendDao.{UserDateRange, DailySpendByUser, UserDate}
import spendii.Spimplicits._
import spendii._

trait InMemDailySpendDao extends DailySpendDao { this:LabelDao with UserDao =>

  private var dsMap = initMap

  override def createDailySpend(userDate:UserDate, spends:Seq[Spendtry]) : DailySpend  = {
    def checkUserExists {
      findUser(userDate.username) doEither(nothing, throw new UserDoesNotExistException("The user [" + userDate.username
                + "] does not exist. Please create the user before adding spends."))
    }

    def doCreateDailySpend : DailySpend = {
      def createDailySpendForUserDate {
        dsMap += (userDate -> DailySpend(userDate.date, spends: _*))
        dsMap.get(userDate).foreach(ds => updateLabels(ds ->))
      }

      def returnDailySpend: DailySpend = dsMap(userDate)

      def getDailySpend: Option[DailySpend] = dsMap.get(userDate)

      getDailySpend doEither(contained, { createDailySpendForUserDate; returnDailySpend })
    }

    checkUserExists
    doCreateDailySpend
  }

  override def findDailySpendByUserDate(userDate:UserDate) : Option[DailySpend] = dsMap.get(userDate)

  override def filterDailySpendByDate(date:Sdate) : Seq[DailySpendByUser] = {
    dsMap.filter(kv => kv._1.date == date).toList.map(kv => DailySpendByUser(kv._1, kv._2))
  }

  override def filterDailySpendByDateRange(range:SdateRange) : Seq[DailySpendByUser] = {
    dsMap.filter(kv => range.contains(kv._1.date)).toList.map(kv => DailySpendByUser(kv._1, kv._2))
  }

  override def filterDailySpendByUserDateRange(udr:UserDateRange) : Seq[DailySpendByUser] = {
    dsMap.filter(kv => kv._1.username.equalsIgnoreCase(udr.username) && udr.range.contains(kv._1.date)).toList.map(kv => DailySpendByUser(kv._1, kv._2))
  }

  override def filterDailySpend(f:(DailySpend) => Boolean) : Seq[DailySpendByUser] =
    dsMap.filter(kv => f(kv._2)).toSeq.map(kv => tupleToDailySpendByUser(kv))

  override def filterDailySpendByUser(username:String) : Seq[DailySpendByUser] = {
    val matched = dsMap.filter(kv=> kv._1.username.equalsIgnoreCase(username)).toList.map(kv => DailySpendByUser(kv._1, kv._2))
    if (matched.isEmpty) List[DailySpendByUser]() else matched
  }

  override def updateDailySpend(userDate:UserDate, f:(DailySpend) => DailySpend) {
    dsMap.get(userDate).map(ds => {
      dsMap += (userDate -> f(ds))
      dsMap.get(userDate).foreach(ds => updateLabels(ds ->))
    })
  }

  override def deleteDailySpend(userDate:UserDate) = dsMap -= (userDate)

  def tupleToDailySpendByUser(t:Tuple2[UserDate, DailySpend]):DailySpendByUser = DailySpendByUser(t._1, t._2)

  private def initMap = Map[UserDate, DailySpend]()

  private def updateLabels(spendtries:Seq[Spendtry]) {
    val labels = findAllLabels
    spendtries.foreach(sp => sp.tags.foreach(t => if (!labels.contains(t)) createLabel(t)))
  }

  private[memory] def clearDailySpends = dsMap = initMap

}