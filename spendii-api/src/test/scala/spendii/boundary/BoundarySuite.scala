/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers
import Boundary._

final class BoundarySuite extends FunSuite with ShouldMatchers {

  test("A Boundary should return success on the Right") {
    val result:Either[Exception, String] = makeSafe {"Hello World"}
    result match {
      case Left(ex) => fail("Expected Right but got Left with " + ex)
      case Right(value) => value should equal ("Hello World")
    }
  }

  test("A Boundary should return failure on the Left") {
    val result:Either[Exception, _] = makeSafe {throw new IllegalStateException("ooops!")}
    result match {
      case Left(ex:IllegalStateException) =>  ex.getMessage should equal ("ooops!")
      case Left(_) => fail("Expected an Exception of type IllegalStateException")
      case Right(_) => fail("Expected a Left")
    }
  }

  implicit def exToEx(e:Exception) : Exception = e
}