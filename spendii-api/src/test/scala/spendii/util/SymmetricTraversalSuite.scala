/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.util

import org.scalatest.matchers.ShouldMatchers
import org.scalatest.FunSuite
import Traversal.symmetric
import collection.mutable.ArrayBuffer

final class SymmetricTraversalSuite extends FunSuite with ShouldMatchers {

  object SuiteName {
    val A_SYMMETRIC_TRAVERSAL_SHOULD = "A SymmetricTraversal should "
  }

  import SuiteName._
  test(A_SYMMETRIC_TRAVERSAL_SHOULD + "symmetrically match 0 elements") {
    symmetric[Int,String](List[Int](), (a:Int, b:Int) => (a * b).toString).isEmpty should equal (true)
  }

  test(A_SYMMETRIC_TRAVERSAL_SHOULD + "symmetrically match 1 element") {
    val result = symmetric[Int,Int](List(5), _ * _)
    result.size should equal (1)
    result(0) should equal (25)
  }

  test(A_SYMMETRIC_TRAVERSAL_SHOULD + "symmetrically match 2 elements") {
    val result = symmetric[String,String](List("one", "two"), _ + "->" +  _)
    result.size should equal (2)
    result(0) should equal ("one->two")
    result(1) should equal ("two->one")
  }

  test(A_SYMMETRIC_TRAVERSAL_SHOULD + "symmetrically match 3 elements") {
    val result = symmetric[Int, Int](List(1,2,3), _ + _)

    result.size should equal (3)
    result(0) should equal (3)
    result(1) should equal (5)
    result(2) should equal (4)
  }

  test(A_SYMMETRIC_TRAVERSAL_SHOULD + "symmetrically match multiple elements") {
    val result = symmetric[String, String](List("blue", "green", "yellow", "red", "orange"), (c1,c2) =>
      if (c1.length > c2.length) c1 + " > " + c2 else if (c1.length < c2.length) c1 + " < " + c2 else c1 + " == " + c2
    )

    result.size should equal (5)
    result(0) should equal ("blue < green")
    result(1) should equal ("green < yellow")
    result(2) should equal ("yellow > red")
    result(3) should equal ("red < orange")
    result(4) should equal ("orange > blue")
  }
}