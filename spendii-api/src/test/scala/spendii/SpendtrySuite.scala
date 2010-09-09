/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import Spendtry._
import util.IfSomeElseNone._

final class SpendtrySuite extends FunSuite with ShouldMatchers {

  object SuiteName {
    val A_SPENDTRY_SHOULD = "A Spendtry should "
  }

  import SuiteName._
  test(A_SPENDTRY_SHOULD + "create Spendtries with Multiple tags"){
    Spendtry("one, two, three", 25.60) doEither(verifySome(_, List("one", "two", "three"), 25.60), failOnNone)
  }

  test(A_SPENDTRY_SHOULD + "not create a Spendtry with zero tags") {
    Spendtry("", 50.4) doEither(failOnSome, passOnNone)
  }

  test(A_SPENDTRY_SHOULD + "not create a Spendtry with empty tags") {
    Spendtry(" ,,        ", 10.2) doEither(failOnSome, passOnNone)
  }

  test(A_SPENDTRY_SHOULD + "create a Spendtry with only valid tags") {
    Spendtry(" , tag1, tag2,, tag3,  ", 5) doEither(verifySome(_, List("tag1", "tag2", "tag3"), 5), failOnNone)
  }

  test(A_SPENDTRY_SHOULD + "have a valid String representation") {
    val spendtry = Spendtry("Lunch, Snacks, Misc", 15.25, "A Cheapo lunch with misc other items")
    val expectedToString = "Spendtry([lunch,snacks,misc],15.25,A Cheapo lunch with misc other items)"
    spendtry doEither (_.toString should equal (expectedToString), failOnNone)
  }

  def failOnNone { fail("Expected Some[Spendtry]") }

  def failOnSome(sp:Spendtry) { fail("Expected None") }

  def passOnNone {  }

  def verifySome(spendtry:Spendtry, tags:Seq[String], cost:Double) {
    spendtry.tags should equal (tags)
    spendtry.cost should equal (cost)
  }

  def verifyNone(spendtry:Option[Spendtry]) {
    spendtry match {
      case None =>
      case _ => fail("Expected None")
    }
  }
}