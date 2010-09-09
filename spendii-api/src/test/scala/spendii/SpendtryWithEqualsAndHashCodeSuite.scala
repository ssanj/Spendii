/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import util.EqualsAndHashCodeConstraints.{StrictHashCodeConstraints, EqualsConstraints}
import util.ScalaTestSuite

final class SpendtryWithEqualsAndHashCodeSuite extends ScalaTestSuite with EqualsConstraints[Spendtry] with StrictHashCodeConstraints[Spendtry] {

  override def objectName = "A Spendtry"

  override val equalObjects = Seq(Spendtry("lunch,snacks", 15.30, "blah2"), Spendtry("sNacks, Lunch", 15.3, "Blah2"),
                                Spendtry("LunCh,snackS", 15.3, "BlAh2")).flatten

  override val differentObjects = Seq(Spendtry("dinner", 10.2), Spendtry("brekky", 25.0, "buffet"),
                                Spendtry("Lunch,Misc", 35.0, "Indian + drink")).flatten
}