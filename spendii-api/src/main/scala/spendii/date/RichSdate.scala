/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.date

sealed class RichSdate(val startDate:Sdate) {

  def to(endDate:Sdate) : SdateRange = SdateRange(startDate, endDate)

  def until(endDate:Sdate) : SdateRange = {
    if (startDate == endDate) SdateRange(startDate, endDate) else SdateRange(startDate, endDate - 1)
  }
}