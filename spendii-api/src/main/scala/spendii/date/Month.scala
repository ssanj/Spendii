/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import java.util.{Calendar => Cal}

/**
 * Defines a month in a date as its own type.
 */
sealed abstract class Month(private[date] val month: Int)

case class January() extends Month(Cal.JANUARY)
case class February() extends Month(Cal.FEBRUARY)
case class March() extends Month(Cal.MARCH)
case class April() extends Month(Cal.APRIL)
case class May() extends Month(Cal.MAY)
case class June() extends Month(Cal.JUNE)
case class July() extends Month(Cal.JULY)
case class August() extends Month(Cal.AUGUST)
case class September() extends Month(Cal.SEPTEMBER)
case class October() extends Month(Cal.OCTOBER)
case class November() extends Month(Cal.NOVEMBER)
case class December() extends Month(Cal.DECEMBER)

object Month {

  def january = January()
  def february = February()
  def march = March()
  def april = April()
  def may = May()
  def june = June()
  def july = July()
  def august = August()
  def september = September()
  def october = October()
  def november = November()
  def december = December()

  def getMonth(month:Int) : Option[Month] = {
    month match {
      case Cal.JANUARY => Some(january)
      case Cal.FEBRUARY => Some(february)
      case Cal.MARCH => Some(march)
      case Cal.APRIL => Some(april)
      case Cal.MAY => Some(may)
      case Cal.JUNE => Some(june)
      case Cal.JULY => Some(july)
      case Cal.AUGUST => Some(august)
      case Cal.SEPTEMBER => Some(september)
      case Cal.OCTOBER => Some(october)
      case Cal.NOVEMBER => Some(november)
      case Cal.DECEMBER => Some(december)
      case _ => None
    }
  }
}
