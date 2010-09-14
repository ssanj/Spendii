/*
 * Copyright 2010 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.examples

import spendii.date.Month._
import spendii.date.Sdate._
import spendii.boundary.{SpenderService}
import SpenderService._
import spendii.persist.memory.{MemoryDB, InMemLabelDao, InMemDailySpendDao, InMemUserDao}
import spendii._

object SpenderRunner2 {

  class Buyer extends SpenderService with MemoryDB

  def main(args: Array[String]) {
    val spender:SpenderService = new Buyer
    val result =
        spender.addUser("sanj", "blue").and(
        spender.addLabel("gardening")).and(
        spender.addLabel("entertainment")).and(
        spender.addDailySpend("sanj", 16|february|2010,
          List(Spendtry("groceries, snacks, fun", 11.50, "Ingredients for gingerbread men, a lolly for Ethan and a pack of chips."),
          Spendtry("software", 39, "Mac OSX 10.6 - SnowLeopard"),
          Spendtry("presents", 50, "Northern Exposure DVD - for Wifey")).flatten
        )).and(
        spender.addDailySpend("sanj", 17|february|2010,
            List(Spendtry("breakfast", 3.50, "Having toast for brekky."),
            Spendtry("lunch", 27, "Had lunch with wifey @ Bottletree cafe."),
            Spendtry("bills", 150, "Insurance (contents, vehicle, home)."),
            Spendtry("snacks",3.50, "Chocolate thick shake @ Mcers."),
            Spendtry("bills", 99.0, "Bigpond.")).flatten
    ))

     spender.locateDailySpendByUser("sanj").fold(l => println("got an error of : " + l), r=> printds(r.map(_.ds)))
     spender.listLabels.fold(l => println("got an error of : " + l), r=> printLabels(r))
  }

  def printLabels(labels:Seq[String]) {
    println
    println("System Labels")
    println(Array.fill(13)("-").mkString)
    for (l <- labels) println(l)
  }

  //TODO: Fix this
  def printds(spends:Seq[DailySpend]) {
    implicit val pad = " "
    def fill(size:Int)(implicit pad:String) : String = Array.fill(size)(pad).mkString
    def fillAndPad(size:Int) : String = fill(size + 1)
    def fillTitle(maxSize:Int, title:String) : String = fill(math.max(maxSize - title.length, 1))
    def findLongest(f:(Spendtry) => Int) : Int = spends.map(ds => ds.->.foldLeft(0.0)((a,b) => math.max(a, f(b))).toInt).foldLeft(0)(_ + _)

    for (ds <- spends) {
      println
      println(ds.||)
      println(fill(ds.||.toString.length)("_"))
      val maxDescriptionLength = findLongest(_.description.length)
      val maxCostLength = findLongest(_.cost.toString.length)
      println("Description" + fillTitle(maxDescriptionLength, "Description") + " Cost " + fill(6) + "Tags")
      ds.->.foreach(sp => println(sp.description + fillAndPad(maxDescriptionLength - sp.description.length) + sp.cost + fill(6)  + sp.tags.mkString(",")))
    }

    println
    println("Total = $" + spends.foldLeft(0.0)(_ + _.$$))
  }
}
