/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import spendii.boundary.Boundary._
import SpenderService._
import spendii.persist.{LabelDao, DailySpendDao, UserDao}
import spendii.{Spendtry, DailySpend, User}
import spendii.date.{SdateRange, Sdate}
import scala.Either
import spendii.persist.DailySpendDao.{UserDateRange, DailySpendByUser, UserDate}

/**
 * As this is the boundary we need to use Eithers.
 * TODO: Break this up according to services. Eg. SpenderService extends UserService with LabelService with DailySpendService.
 * trait UserService { this:UserDao =>
 */
trait SpenderService extends SpenderUserService with SpenderDailySpendService with SpenderLabelService {
  this:UserDao with DailySpendDao with LabelDao =>
}

trait SpenderUserService { this:UserDao =>

  def addUser(username:String, password:String) : Either[String, User] = makeSafe(createUser(username, password))

  def listUsers : Either[String, Seq[User]] = makeSafe(findAllUsers)

  def locateUser(username:String) : Either[String, Option[User]] = makeSafe(findUser(username))

  def getNoOfUsers : Either[String, Int] = makeSafe(userCount)

  def removeUser(username:String) : Either[String, Unit] =  findUserAndDo(username)(deleteUser(_))

  def updatePassword(username:String, f: => String) : Either[String, Unit] = findUserAndDo(username)(updateUser(_,f))

  private def findUserAndDo(username:String)(f:(User) => Unit) : Either[String, Unit] = makeSafe {
      val user = findUser(username)
      if (user.isDefined) user.foreach(f(_)) else throw new RuntimeException("Could not locate specified user: [" + username + "]")
  }
}

trait SpenderDailySpendService { this:DailySpendDao =>

  def addDailySpend(username:String, date:Sdate, spends:Seq[Spendtry]) : Either[String, DailySpend] = makeSafe {
    createDailySpend(UserDate(username, date), spends.toSeq)
  }

  def locateDailySpend(username:String, date:Sdate) : Either[String, Option[DailySpend]] = makeSafe { findDailySpendByUserDate(UserDate(username, date)) }

  def locateDailySpendByUser(username:String) : Either[String, Seq[DailySpendByUser]] = makeSafe { filterDailySpendByUser(username) }

  def locateDailySpendByDate(date:Sdate) : Either[String, Seq[DailySpendByUser]] = makeSafe { filterDailySpendByDate(date) }

  def locateDailySpendByDateRange(dr:SdateRange) : Either[String, Seq[DailySpendByUser]] = makeSafe { filterDailySpendByDateRange(dr) }

  def locateDailySpendByUserDateRange(username:String, dr:SdateRange) : Either[String, Seq[DailySpendByUser]] = makeSafe {
    filterDailySpendByUserDateRange(UserDateRange(username, dr))
  }

  def matchDailySpend(f:(DailySpend) => Boolean) : Either[String, Seq[DailySpendByUser]] = makeSafe { filterDailySpend(f) }

  def updateSpends(username:String, date:Sdate, f:(DailySpend) => DailySpend) : Either[String, Unit] = makeSafe {
    updateDailySpend(UserDate(username, date), f)
  }

  def removeDailySpend(username:String, date:Sdate)  : Either[String, Unit] = makeSafe { deleteDailySpend(UserDate(username, date)) }
}

trait SpenderLabelService { this:LabelDao =>

  def listLabels : Either[String, Seq[String]] = makeSafe { findAllLabels }

  def locateLabelsByUser(username:String) : Either[String, Seq[String]] = makeSafe { findLabelsByUser(username) }

  def addLabel(labels:String*): Either[String, Unit] = makeSafe { createLabel(labels:_*) }

  def removeLabel(label:String) : Either[String, Unit] = makeSafe { deleteLabel(label) }

  def updateLabelString(search:String, replace:String): Either[String, Unit] = makeSafe { updateLabel(search, replace) }
}

object SpenderService {
  implicit def exToString(e:Exception) : String = e.getMessage

  class Joiner[T](e1:Either[String, T]) {
    def and[S](f: => Either[String, S]) : Either[String, S] =  e1.right.flatMap(r=> f)
    def and[S](f:(T) => Either[String, S]) : Either[String, S] =  e1.right.flatMap(f(_))
  }

  implicit def eitherToJoiner[T](e:Either[String, T]) : Joiner[T] = new Joiner(e)
}