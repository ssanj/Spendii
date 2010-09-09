/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import scala.Either

object Boundary {

  def makeSafe[E,R](f1: => R)(implicit f2:Exception => E) : Either[E, R] = try { Right(f1) } catch { case x:Exception => Left(f2(x)) }
}