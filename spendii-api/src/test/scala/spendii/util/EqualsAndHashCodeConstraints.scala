/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.util

import org.scalatest.BeforeAndAfterAll

object EqualsAndHashCodeConstraints {
  /**
   * Base trait that verifies that at least 3 equal objects and 1 differing object is supplied before the start of any tests.
   * @throws IllegalArgumentException if the above criteria is not met.
   */
  trait ConstraintsBase[T] extends BeforeAndAfterAll { this:ScalaTestSuite =>
    def objectName:String

    val equalObjects:Seq[T]
    val differentObjects:Seq[T]

    override def beforeAll {
      require(equalObjects.size >= 3, "Need at least 3 valid objects")
      require(differentObjects.size >= 1, "Need at least 1 invalid object")
    }
  }

  /**
   * Verifies the following equality checks:
   *
   * 1. Equals is reflexive.
   * 2. Equals is symmetric.
   * 3. Equals is transitive.
   * 4. Equals is consistent.
   * 5. Equals should not equal null.
   * 6. Differing objects are not equal.
   */
  trait EqualsConstraints[T] extends ConstraintsBase[T] { this:ScalaTestSuite =>

    import Traversal._
    test(objectName + " should have a reflexive equals") {
      equalObjects foreach (o => o should equal  (o))
    }

    test(objectName + " should have a symmetric equals") { symmetric[T, Unit](equalObjects, _ should equal (_)) }

    test(objectName + " should have a transitive equals") {
      equalObjects sliding(3) foreach {l=>
        l(0) should equal (l(1))
        l(1) should equal (l(2))
        l(0) should equal (l(2))
      }
    }

    test(objectName + " should have a consistent equals") { symmetric[T, Unit](equalObjects, (o1, o2) => for (i <- 1 to 3)  o1 should equal (o2)) }

    test(objectName + " should be different if unequal") {
      equalObjects foreach (eo => differentObjects.foreach(eo should not equal (_)))
    }

    test(objectName + " should not equal null") {
      equalObjects foreach (_ should not equal (null))
      differentObjects foreach (_ should not equal (null))
    }
  }

  /**
   * Has the following hashCode checks:
   *
   * 1. Equal objects have the same hashCode.
   * 2. hashCode is consistent.
   */
  trait HashCodeConstraints[T] extends ConstraintsBase[T] { this:ScalaTestSuite =>

    test(objectName + " should have the same hashCode 2 objects are equal") {
      val hc = equalObjects.head.hashCode
      equalObjects.tail foreach (obj => obj.hashCode should equal (hc))
    }

    test(objectName + " should have a consistent hashCode") {
      val hc = equalObjects.head.hashCode
      equalObjects.tail foreach (obj => for (i <- 1 to 3) obj.hashCode should equal (hc))
    }
  }

  /**
   * Defines a "strict" hashCode which includes all the checks of HashCode and also verifies that differing objects have differing hashCodes.
   */
  trait StrictHashCodeConstraints[T] extends HashCodeConstraints[T] { this:ScalaTestSuite =>

    //Not necessary for hashCode, but is considered "recommended" for performance.
    test(objectName + " should have different hashCodes if not equal") {
      val hc = equalObjects.head.hashCode
      differentObjects foreach (obj => obj.hashCode should not equal (hc))
    }
  }
}