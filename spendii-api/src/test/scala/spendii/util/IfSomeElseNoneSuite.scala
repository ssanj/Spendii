/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.util

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import IfSomeElseNone._

final class IfSomeElseNoneSuite extends FunSuite with ShouldMatchers {

  import SuiteName._
  import TestData._
  test(IFSOME_ELSENONE_SHOULD + "work with Some(x)") {
    val browser = BROWSER_1
    browser ifSome (verifyBrowserName(_, BROWSER_1_NAME)) elseNone failForNone
  }

  test(IFSOME_ELSENONE_SHOULD + "work on None") {
    val browser = NONE
    browser ifSome(failForSome(_)) elseNone verifyNone(browser)
  }

  test(IFSOME_ELSENONE_SHOULD + "work on Some(x) with doEither syntax") {
    val browser = BROWSER_2
    browser doEither (verifyBrowserName(_, BROWSER_2_NAME), failForNone)
  }

  test(IFSOME_ELSENONE_SHOULD + "work on None with doEither syntax") {
    val browser = NONE
    browser doEither (failForSome(_), verifyNone(browser) )
  }

  test(IFSOME_ELSENONE_SHOULD +"work with Some(x) with the contained method") {
    val browser = BROWSER_1
    val result = browser ifSome (contained) elseNone (BROWSER_2_NAME)
    result should equal (BROWSER_1_NAME)
  }

  test(IFSOME_ELSENONE_SHOULD +"work with doEither syntax with the contained method") {
    val browser = BROWSER_1
    val result = browser doEither (contained, BROWSER_2_NAME)
    result should equal (BROWSER_1_NAME)
  }

  test(IFSOME_ELSENONE_SHOULD + "work with Some(x) with doNothing method") {
    val browser = NONE
    browser ifSome (nothing) elseNone (assert(browser.isEmpty))
  }

  test(IFSOME_ELSENONE_SHOULD + "work with doEither syntax with doNothing method") {
    val browser = NONE
    browser doEither(nothing, assert(browser.isEmpty))
  }

  private def verifyBrowserName[T](actual:T, expected:T) { actual should equal(expected) }

  private def verifyNone[T](op:Option[T]) { assert(op.isEmpty) }

  private def failForNone { fail("Expected Some(x)") }

  private def failForSome[T](t:T) { fail("Expected None, but got " + t) }

}

object SuiteName {
  val IFSOME_ELSENONE_SHOULD = "IfSomeElseNone should "
}

object TestData {
  val BROWSER_1_NAME = "Google Chrome"
  val BROWSER_1 = Some(BROWSER_1_NAME)

  val BROWSER_2_NAME = "Firefox"
  val BROWSER_2 = Some(BROWSER_2_NAME)

  val NONE = None
}