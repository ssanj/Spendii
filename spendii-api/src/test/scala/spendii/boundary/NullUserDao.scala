/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import spendii.persist.UserDao
import spendii.User
import spendii.boundary.userdao.ExceptionMessages._

abstract class NullUserDao extends UserDao {
  def findUser(username:String) : Option[User] = throw new RuntimeException(FIND_USER_FAIL_MSG)
  def findAllUsers : Seq[User] = throw new RuntimeException(FIND_ALL_USERS_FAIL_MSG)
  def userCount : Int = throw new RuntimeException(USER_COUNT_FAIL_MSG)
  def createUser(username:String, password:String) : User = throw new RuntimeException(CREATE_USER_FAIL_MSG)
  def deleteUser(user:User) { throw new RuntimeException(DELETE_USER_FAIL_MSG) }
  def updateUser(user:User, f: => String) { throw new RuntimeException(UPDATE_USER_FAIL_MSG) }
}

package userdao {
  object ExceptionMessages {
    val CREATE_USER_FAIL_MSG = "createUser threw an Exception"
    val FIND_ALL_USERS_FAIL_MSG = "findAllUsers threw an Exception"
    val FIND_USER_FAIL_MSG = "findUsers threw an Exception"
    val USER_COUNT_FAIL_MSG = "userCount threw an Exception"
    val UPDATE_USER_FAIL_MSG = "updateUser threw an Exception"
    val DELETE_USER_FAIL_MSG = "deleteUser threw an Exception"
  }
}