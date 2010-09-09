/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.persist.DailySpendDao.DailySpendByUser
import spendii.persist._
import spendii.{Spendtry}
import spendii.Spimplicits.seqToString

trait InMemLabelDao extends LabelDao { this:DailySpendDao with UserDao =>

  private var labelSet:Set[String] = init

  override def findAllLabels : Seq[String] = labelSet.toSeq

  override def findLabelsByUser(username:String) : Seq[String] = {
    filterDailySpendByUser(username).map(_.ds).foldLeft(Set[String]())((set,ds) => ds.->.foldLeft(set)(_ ++ _.tags)).toSeq
  }

  override def createLabel(labels:String*) = {
    labelSet = labels.foldLeft(labelSet.map(_.toLowerCase))(_ + _.toLowerCase)
  }

  override def deleteLabel(label:String) = {
    val matches = filterDailySpend(_.exists(containsLabel(_, label)))
    if (matches.isEmpty) {
      labelSet = labelSet.filterNot(_.equalsIgnoreCase(label))
    } else {
      throw new LabelIsBoundException("Could not delete label: [" + label + "] as it is being used by other entries.")
    }
  }

  override def updateLabel(search:String, replace:String) {
    if (labelSet.exists(_.equalsIgnoreCase(search))) {
      updateLabelSet
      updateSpendtryLabels
    }

    def updateSpendtryLabels {
      val matches:Seq[DailySpendByUser] = filterDailySpend(_.exists(containsLabel(_, search)))
      val updatedMatches:Seq[DailySpendByUser] =  matches.map(dsu => DailySpendByUser(dsu.ud, dsu.ds.replace(containsLabel(_, search),
        sp => Spendtry(sp.tags.map(t => if (t.equalsIgnoreCase(search)) replace  else t), sp.cost, sp.description))))
      updatedMatches.foreach(dsu => updateDailySpend(dsu.ud, ds => dsu.ds))
    }

    def updateLabelSet {
      labelSet = labelSet.filterNot(_.equalsIgnoreCase(search))
      labelSet += replace
    }

  }
  def containsLabel(sp:Spendtry, label:String): Boolean = sp.tags.exists(_.equalsIgnoreCase(label))


  private[memory] def clearLabels { labelSet = init }

  private def init = Set[String]()
}