/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist

import spendii.User

trait UserDao {

  def findUser(username:String) : Option[User]

  def findAllUsers : Seq[User]

  def userCount : Int

  def createUser(username:String, password:String) : User

  def deleteUser(user:User)

  /**
   * f returns a new password.
   */
  def updateUser(user:User, f: => String)
}

case class UserDoesNotExistException(message:String) extends RuntimeException(message)

