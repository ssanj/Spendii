/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import spendii.util.EqualsAndHashCodeConstraints.{HashCodeConstraints, EqualsConstraints}
import Month._

final class SdateWithEqualsAndHashCodeSuite extends ScalaTestSuite with EqualsConstraints[Sdate] with HashCodeConstraints[Sdate] {

  override def objectName = "Sdate"

  override val equalObjects = Seq(Sdate(1, january, 2004), Sdate(1, January, 2004), Sdate(1, January, 2004))
  override val differentObjects = Seq(Sdate(27, september, 2007), Sdate(25, december, 2008))
}
