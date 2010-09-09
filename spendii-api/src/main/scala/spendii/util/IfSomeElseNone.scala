/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.util

/**
 * Simplifies use of Option[T].
 *
 * Allows for the following syntax given op is Option[T]:
 *
 * op ifSome (t => work_with_some(t)) elseNone (work_with_none)
 * op ifSome (contained) elseNone (work_with_none)
 * op ifSome (doNothing) elseNone (work_with_none)
 *
 * or
 *
 * op either(t => work_with_some(t), work_With_none)
 * op either(contained, work_With_none)
 * op either(doNothing, work_With_none)
 *
 * Without this api, you would have to do something like:
 *
 * if (op.isDefined) {val t=op.get;work_with_some(t)} else work_with_none
 *
 * Import IfSomeElseNone._ to use.
 */
object IfSomeElseNone {

  case class IfSome[T](f: Option[T]) {
    def ifSome[U](s:T => U): ElseNone[T,U] = ElseNone(f, s)

    case class ElseNone[T,U](f: Option[T], s:T => U) {
      def elseNone(e: => U): U = f match {
        case Some(t) => s(t)
        case None => e
      }
    }
  }

  case class DoEither[T](f: Option[T]) {
    def doEither[U](s:T => U, e: => U): U = IfSome(f).ifSome(s).elseNone(e)
  }

  //use this method with ifSome to retrieve the optioned value.
  def contained[T](t: T): T = t

  //use this method with ifSome to do nothing with the optioned value.
  def nothing[T](t: T): Unit = {}

  implicit def opToOptional[T](op:Option[T]): IfSome[T] = IfSome(op)

  implicit def opToDoEither[T](op:Option[T]): DoEither[T] = DoEither(op)
}