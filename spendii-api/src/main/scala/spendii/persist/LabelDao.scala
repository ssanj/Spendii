/*
  * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist

trait LabelDao {

  def findAllLabels : Seq[String]

  def findLabelsByUser(username:String) : Seq[String]

  def createLabel(labels:String*)

  /**
   * Throws a LabelIsBoundException if the label to be deleted is being used.
   */
  def deleteLabel(label:String)

  def updateLabel(search:String, replace:String)
}

case class LabelIsBoundException(message:String) extends RuntimeException(message)