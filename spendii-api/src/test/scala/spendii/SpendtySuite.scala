/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import Spimplicits._
import Spendtry._

sealed class SpendtySuite extends FunSuite with ShouldMatchers {

  test("A Spendtry cannot be created with a cost less than or equal to zero"){
    Spendtry("blah", 0) should equal (None)
    Spendtry("blah", -1) should equal (None)
    Spendtry("blah", 1) should not equal (None)
  }

  test("A Spendtry cannot be created without a tag"){
    Spendtry("", 5) should equal (None)
  }

  test("A Spendtry can be created with a primary tag") {
    Spendtry("testing", 20) should not equal (None)
  }

  test("A Spendtry can be created with multiple tags") {
    Spendtry("lunch,groceries", 20) should not equal (None)
  }

  test("A Spendtry can be created with a description") {
    Spendtry("dinner", 15, "red rooster") should not equal (None)
  }

  test("A Spendtry can be created without a description") {
    Spendtry("dinner", 15) should not equal (None)
  }

  test("A Spendtry should equal another with the same information") {
    Spendtry("dinner", 15) should equal (Spendtry("dinner", 15))
  }

  test("A Spendtry should not equal another with the different information") {
    Spendtry("dinner", 15) should not equal (Spendtry("lunch", 10))
  }
}