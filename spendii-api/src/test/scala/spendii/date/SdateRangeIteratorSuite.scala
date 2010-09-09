/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date


import spendii.Spimplicits.dayToDayMonth
import spendii.util.ScalaTestSuite
import SomeDay._
import Month._
import spendii.util.IfSomeElseNone._

final class SdateRangeIteratorSuite extends ScalaTestSuite {

  test("An SdateRange should iterate over a single element") {
    val it:Iterator[Option[Sdate]] = today to today
    it.hasNext should equal (true)
    it.next doEither(_ should equal (today), failOnNone)
  }

  test("An SdateRange should iterate over multiple elements") {
    val it:Iterator[Option[Sdate]] = 1|january|2009 to 5|january|2009
    val expectedDays = List(1|january|2009, 2|january|2009, 3|january|2009, 4|january|2009, 5|january|2009)

    val dates =  it.toList.flatten
    dates sameElements (expectedDays) should equal (true)
  }

  test("An SdateRange should return None when next() is called when there are no more elements") {
    val it = today to today
    it.next

    it.hasNext should equal (false)
    it.next doEither(failOnSome, passOnNone)
  }

  private def failOnNone { fail("Expected Some, but got None") }

  private def failOnSome(date:Sdate) { fail("Expected None, but got Some(" + date + ")") }

  private def passOnNone { }

}