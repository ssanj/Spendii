/*
  * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import date.Sdate

final class DailySpend private (val date:Sdate, val spendtries:Seq[Spendtry]) {

  def || : Sdate = date

  /**
   * Adds a Spendtry to this DailySpend.
   */
  def + (spendtry:Spendtry): DailySpend = ++(List(spendtry))


  /**
   * Adds all spends in the supplied Iterable.
   */
  def ++ (spends:Iterable[Spendtry]): DailySpend = new DailySpend(date, (spendtries ++ spends).toSeq)

  /**
   * Removes positive matches from the supplied (if any) and returns the updated DailySpend.
   */
  def remove(f:Spendtry => Boolean): DailySpend  = filterNot (f)

  /**
   * Dumps the spendtries of this object into an immutable List. The order of Spendtries is the order of addition.
   */
  def -> : List[Spendtry] = spendtries.toList

  /**
   * Filters Spendtries from this DailySpend and returns a new DailySpend with only the Spendtries filtered by f.
   */
  def filter(f:Spendtry => Boolean): DailySpend = new DailySpend(date, spendtries filter(f))

  /**
   * Filters Spendtries from this DailySpend and returns a new DailySpend with only the Spendtries not filtered by f.
   */
  def filterNot(f:Spendtry => Boolean): DailySpend = filter (!f(_))

  /**
   * Filters Spendtries from this DailySpend and returns a tuple of DailySpends. One with all the Spendtries that match and one with those that don't.
   */
 def partition(f:Spendtry => Boolean): (DailySpend, DailySpend) = (filter(f), filterNot(f))

  /**
   * Returns true if the function f, matches any of the Spendtries of this DailySpend, false if not.
   */
  def exists(f:Spendtry => Boolean): Boolean = spendtries exists f

  /**
   * Returns true if this DailySpend contains the supplied Spendtry, false if not.
   */
  def contains(sp:Spendtry): Boolean = exists(_ == sp)

  /**
   *   Replaces Spendtries that match the filter f, with those that are generated from function r, and returns a DailySpend with all the matched
   * Spendtries replaced and all the unmatched Spendtries untouched.
   */
  def replace(f:Spendtry => Boolean, r:Spendtry => Option[Spendtry]): DailySpend = {
      new DailySpend(date, spendtries.map(sp => if(f(sp)) r(sp) else Some(sp)).filter(_.isDefined).map(_.get))
  }

  /**
   *    Returns the number of Spendtries within this DailySpend
   */
  def size: Int = spendtries.size

  /**
   * Returns the total cost (sum) of all Spendtries rounded to the nearest dollar.
   */
  def $$: Double = (0d /: spendtries)(_ + _.cost).ceil

  /**
   * Creates a clone of this DailySpend.
   */
  def **(): DailySpend = new DailySpend(date, spendtries.toList)

  override def equals(obj:Any): Boolean = {
    //order of Spendtries is not important - only the content.
    //Spendtries don't have a sort-order so we can't sort them. We have to check content one-at-a-time.
    def areElementsEqual(ds:DailySpend): Boolean = ds.spendtries.foldLeft(ds.spendtries.size == spendtries.size)(_ && spendtries.contains(_))

    obj match {
      case ds:DailySpend => ds.date == date && areElementsEqual(ds)
      case _ => false
    }
  }

  //We use a simple hashCode of summing the date with that of the individual hashCodes of the Spendtries since we can't determine their order.
  override def hashCode: Int = (41 * (41 + date.hashCode)) + spendtries.foldLeft(0)(_ + _.hashCode)

  override def toString = "DailySpend[" + date + ", " + spendtries.mkString("{", ",", "}") + "]"

  /**
   * Returns true if this DailySpend does not have any Spendtries.
   */
  def isEmpty: Boolean = size == 0

  /**
   * Returns true if this DailySpend does have Spendtries.
   */
  def nonEmpty: Boolean = !isEmpty
}

object DailySpend {

  def apply(date:Sdate, spendtries:Spendtry*): DailySpend = new DailySpend(date, spendtries)

  /**
   * Allows us to have both (Sdate, Spendtry*) and (Sdate, Seq[Spendtry]) signatures
   * which do not clash due to pass-by-name parameters.
   */
  def apply(d: => Sdate, s: => Seq[Spendtry]): DailySpend = new DailySpend(d, s)

  def apply(date:Sdate): DailySpend = new DailySpend(date, List[Spendtry]())
}
