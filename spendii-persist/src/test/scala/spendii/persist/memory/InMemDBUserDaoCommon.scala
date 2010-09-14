/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import org.scalatest.{FunSuite, BeforeAndAfterEach}
import org.scalatest.matchers.ShouldMatchers
import spendii.persist.DailySpendDao.{UserDate}
import spendii.{Spendtry, User}
import spendii.date.Sdate._

trait InMemDBUserDaoCommon extends FunSuite with ShouldMatchers with BeforeAndAfterEach {

  val dao = InMemDB

  override def afterEach() {dao.clearAll}

  protected def verifyUser(username:String) {
    dao.findUser(username) match {
      case Some(User(name, _, _)) => name should equal (username)
      case None => fail("Could not find user " + username)
    }
  }

  protected def verifyUser(user:User, expectedUser:User) { user should equal (expectedUser) }

  object TestData {

    val USER1 = User("sanj", "blah")
    val USER2 = User("jazzy", "meow")
    val USER2_MIXED = User("JazZy", "meow")
    val USER_DATE_2 = UserDate(USER2.username, today)
    val SPENDS_2 = List(Spendtry("Lunch, Snax", 20.5, "blah"), Spendtry("Dinner", 45.43, "blee"),
      Spendtry("Snax", 5.05, "Chocolate")).flatten
    val CHANGED_PASSWORD = "blahdeblah"
    val OVERWRITTEN_PASSWORD = "rarara"
  }
}
