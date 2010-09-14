/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

import java.util.{Calendar => Cal}

/**
 * Defines a month in a date as its own type.
 */
sealed abstract class Month(private val month:Int)

final case object January extends Month(Cal.JANUARY)
final case object February extends Month(Cal.FEBRUARY)
final case object March extends Month(Cal.MARCH)
final case object April extends Month(Cal.APRIL)
final case object May extends Month(Cal.MAY)
final case object June extends Month(Cal.JUNE)
final case object July extends Month(Cal.JULY)
final case object August extends Month(Cal.AUGUST)
final case object September extends Month(Cal.SEPTEMBER)
final case object October extends Month(Cal.OCTOBER)
final case object November extends Month(Cal.NOVEMBER)
final case object December extends Month(Cal.DECEMBER)

object Month {

  def january = January
  def february = February
  def march = March
  def april = April
  def may = May
  def june = June
  def july = July
  def august = August
  def september = September
  def october = October
  def november = November
  def december = December

  def getMonth(month:Int): Option[Month] = {
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
  
  def getMonth(month:Month): Int = month.month
}
