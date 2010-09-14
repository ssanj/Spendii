/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

/**
 * A simple date range with both startDate and endDate inclusive. If startDate is after endDate, they are switched.
 */
final class SdateRange private (val startDate:Sdate, val endDate:Sdate) extends Iterator[Option[Sdate]] {
  var indexDate:Sdate = startDate

  override def hasNext: Boolean = indexDate <= endDate

  override def next : Option[Sdate] = {
    if (hasNext)  {
      val res = inc(indexDate)
      indexDate = res._1
      Some(res._2)
    } else None
  }

  private def inc(date:Sdate): (Sdate,Sdate) =  (date + 1, date)

  override def hashCode = startDate.hashCode + endDate.hashCode

  def contains(date:Sdate): Boolean =  (date >= startDate && date <= endDate)

  override def equals(that:Any) =
    that match {
      case other:SdateRange => (startDate == other.startDate) && (endDate == other.endDate)
      case _ => false
    }
}

object SdateRange {

  def apply(start:Sdate, end:Sdate): SdateRange = {
    val startDate:Sdate = getValue(start > end)(end)(start)
    val endDate:Sdate = getValue(end < start)(start)(end)
    new SdateRange(startDate, endDate)
  }

  def getValue[T](predicate:Boolean)(ifTrue:T)(ifFalse:T): T = if (predicate) ifTrue else ifFalse
}
