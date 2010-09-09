/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.util

/**
 * Defines traversal methods to encompass equals comparisons.
 *
 * Symmetric: Given a sequence of a given kind, calls a function with each element and it's  next neighbouring element.
 * For the last element in the Sequence, the head of the sequence is the next neighbouring element.
 * Eg. given List(1,2,3) and a function f, calls to f are:
 * f(1,2)
 * f(2,3)
 * f(3,1)
 *
 *
 */
object Traversal {

  /**
   * Takes a seq of objects of type [A] and a function that processes two consecutive elements, and returns
   * the results of each process.
   *
   * Eg. symmetric[Int,Int](List(1,2,3), _ + _) gives:  Seq(3,5,4)
   */
  def symmetric[A,R](oSeq:Seq[A], f:(A,A) => R): Seq[R] = {
    def doSymmetric(seq:Seq[A], results:Seq[R]): Seq[R] = {
      seq match {
        case a :: b :: list => { doSymmetric(seq.tail, f(a, b) +: results) }
        case a :: Nil =>  f(a, oSeq.head) +: results
        case Nil => results
      }
    }

    doSymmetric(oSeq, List[R]()).reverse
  }
}