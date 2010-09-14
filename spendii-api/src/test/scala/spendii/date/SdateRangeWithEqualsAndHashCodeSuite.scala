/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import spendii.util.ScalaTestSuite
import spendii.util.EqualsAndHashCodeConstraints.{StrictHashCodeConstraints, EqualsConstraints}
import Month._
import Sdate._

final class SdateRangeWithEqualsAndHashCodeSuite extends ScalaTestSuite
        with EqualsConstraints[SdateRange] with StrictHashCodeConstraints[SdateRange] {

  override def objectName = "An SdateRange"

  override val equalObjects = Seq((1|january|2009 to 1|june|2009), (1|January|2009 to 1|june|2009), (1|June|2009 to 1|january|2009))
  
  override val differentObjects = Seq((2|january|2009 to 1|june|2009), (1|january|2009 to 2|june|2009), (5|december|2009  to 10|september|2010))
}
