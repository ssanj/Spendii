/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import spendii.util.EqualsAndHashCodeConstraints.{HashCodeConstraints, EqualsConstraints}
import spendii.Spimplicits.dayToDayMonth
import Month._

final class SomeDayWithEqualsAndHashCodeSuite extends ScalaTestSuite with EqualsConstraints[SomeDay] with HashCodeConstraints[SomeDay] {

  override def objectName = "SomeDay"

  override val equalObjects = Seq(SomeDay(1, january, 2004), SomeDay(1, January(), 2004), SomeDay(1, January(), 2004))
  override val differentObjects = Seq(SomeDay(27, september, 2007), SomeDay(25, december, 2008))
}