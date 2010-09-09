/*
  * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

/**
 * Have chosen not to make this a case class because we need to return an Option[Spendtry] on creation.
 * We can't have a case class as well as an object with an apply method as the constructor of the case class
 * and the apply method clash.
 *
 * We also want to define our own equals and hashCode methods. This further adds to the rational to make it a vanilla class.
 */
final class Spendtry private(val tags:Seq[String], val cost:Double, val description:String) {

  //We only care about the content of tags not their order or case.
  //We only care about the content of description, irrespective of case.
  override def equals(obj:Any): Boolean = {
    obj match {
      case sp:Spendtry => (sp.tags.sorted == tags.sorted && sp.cost == cost && sp.description.equalsIgnoreCase(description))
      case _ => false
    }
  }

  //Use all fields of the hashCode, but use sorted order for the tags, cost as is and lowerCase hashCode for the description.
  override def hashCode: Int =  {
    val tagHashCode = tags.sorted.hashCode
    (41 * (41 * (41 + tagHashCode)) + cost.hashCode) + description.toLowerCase.hashCode
  }

  override def toString: String = "Spendtry([" + tags.mkString(",") + "]," + cost.toString + "," + description + ")"
}

/**
 * Factory that creates Spendtries.
 * A Spendtry contains :
 * 1. A sequence of tags to classify Spendtries. (at least one is mandatory).
 * 2. A cost of greater than 0.
 * 3. A description (optional). Defaults to "-".
 */
object Spendtry {

  val defaultDescription = "-"

  //tags are stored as lowercase but in order-of-addition.
  //descriptions are stored as is.
  def apply(tags:String, cost:Double, description:String) : Option[Spendtry] = {
    val processedTags = tags.split(",").map(_.trim).filterNot(_.isEmpty).map(_.toLowerCase).toSeq
    if (!processedTags.isEmpty && cost > 0) Some(new Spendtry(processedTags, cost, description)) else None
  }

  def apply(tags:String, cost:Double) : Option[Spendtry] = apply(tags, cost, defaultDescription)
}
