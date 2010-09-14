/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.examples

import spendii.date.Month._
import spendii.date.Sdate._
import org.scalatest.matchers.ShouldMatchers
import spendii.persist.memory.MemoryDB
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import spendii.boundary.{SpenderService}
import spendii.boundary.SpenderService._
import spendii.{User, Spendtry, DailySpend}
import spendii.Spendtry

final class SpenderExamplesSuite extends FunSuite with ShouldMatchers with BeforeAndAfterEach {

  object SuiteName {
    val A_SPENDER_SHOULD = "A Spender should "
  }

  object SuiteConstants {
    val USER_LEX = ("Lex", "Xel")
    val USER_KENT = ("Kent", "Tnek")
  }

  import SuiteName._
  import SuiteConstants._
  def createSpender : SpenderService = {
    val spender  = new SpenderService with MemoryDB
    val addUser = (spender.addUser _).tupled
    addUser(USER_LEX).and(addUser(USER_KENT))
    spender
  }

  test(A_SPENDER_SHOULD + "add Spends") {
    val spender = createSpender
    val date = 17|may|2010
    val sp1 = Spendtry("toll", 7.50).get
    val sp2 = Spendtry("lunch", 10.0, "Subway").get
    val result = spender.addDailySpend("Lex", date, List(sp1, sp2)).
            and(spender.locateDailySpendByUser("Lex"))
    verifySuccess(result)
    result.right.foreach{spends =>
      spends.size should equal (1)
      val ds = spends(0).ds
      ds.size should equal (2)
      ds.contains(sp1) should equal (true)
      ds.contains(sp2) should equal (true)
    }
  }

  test(A_SPENDER_SHOULD + "return errors when adding Spends") {
    val spender = createSpender
    val result = spender.addDailySpend("Lex", 18|may|2010, List(Spendtry("lunch", 30.0).get)).and(
    spender.addDailySpend("Lex", 19|may|2010, List(Spendtry("misc,home", 100.0, "table cloth, napkins etc").get))).and(
    spender.addDailySpend("Sanj", 20|may|2010, List(Spendtry("dinner", 34.50).get))).and(
    spender.locateDailySpendByUser("Lex"))
    verifyError(result, "The user [Sanj] does not exist. Please create the user before adding spends.")
  }

  test(A_SPENDER_SHOULD + "list all Users") {
    val spender = createSpender
    val result = spender.listUsers
    verifySuccess(result)
    result.right.foreach{
      users => users.size should equal (2)
      users(0).username should equal ("Lex")
      users(1).username should equal ("Kent")
    }
  }

  test(A_SPENDER_SHOULD + "locate Labels by User") {
    val spender = createSpender
    val result = spender.addDailySpend("Lex", 18|may|2010, List(Spendtry("lunch", 30.0).get)).and(
    spender.addDailySpend("Lex", 19|may|2010, List(Spendtry("misc,home", 100.0, "table cloth, napkins etc").get))).and(
    spender.addDailySpend("Kent", 20|may|2010, List(Spendtry("dinner", 34.50).get))).and(
    spender.locateLabelsByUser("Lex"))
    verifySuccess(result)
    result.right.foreach{labels =>
      labels.size should equal(3)
      labels should contain ("lunch")
      labels should contain ("misc")
      labels should contain ("home")
    }
  }

  test(A_SPENDER_SHOULD + "update Spends") {
    val spender = createSpender
    val date = 21|may|2010
    val sp1 = Spendtry("Snacks", 4.5, "Coffee").get
    val sp2 = Spendtry("Fuel", 45.5).get
    val sp3 = Spendtry("Snacks", 2.0, "Cheese Twisties").get
    val sp4 = Spendtry("Dinner", 30.0).get
    val result = spender.addDailySpend("Kent", date, List(sp1, sp2, sp3, sp4)).and(
                 spender.updateSpends("Kent", date, _.filter(_.tags.contains("fuel")))).and(
                 spender.locateDailySpend("Kent", date))
    verifySuccess(result)
    result.right.foreach { op =>
      verifyDailySpend

      def verifyDailySpend {
        op match {
          case None => fail("Expected a valid DailySpend")
          case Some(ds) => { ds.->.size should equal (1); ds.contains(sp2) should equal (true) }
        }
      }
    }
  }

  private def verifySuccess[T](result:Either[String, T]) {
    result match {
      case Left(ex) => fail("Unexpected Exception " + ex)
      case Right(r) =>
    }
  }

  private def verifyError[T](result:Either[String, T], msg:String) {
    result match {
      case Left(ex) => msg should equal (ex)
      case Right(r) => fail("Unexpected an error")
    }
  }
}
