/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.User
import spendii.persist.{DailySpendDao, UserDao}
import spendii.util.IfSomeElseNone._

trait InMemUserDao extends UserDao { this:DailySpendDao =>

  private var userMap = Map[String, User]()

  override def findUser(username:String) : Option[User] = userMap.filter(kv => kv._1.equalsIgnoreCase(username)).headOption.map(_._2)

  override def findAllUsers : Seq[User] = userMap.map(_._2).toSeq

  override def userCount : Int = userMap.size

  override def createUser(username:String, password:String) : User = {
    def createAndReturnUser: User = {
      userMap += (username -> User(username, password))
      userMap(username)
    }

    findUser(username) ifSome (contained) elseNone createAndReturnUser
  }

  override def deleteUser(user:User) {
    val username = user.username
    filterDailySpendByUser(username).foreach(ds => deleteDailySpend(ds.ud))
    userMap = userMap.filterNot(_._1 eq username)
  }

  override def updateUser(user:User, f: => String) {
    if (userMap.contains(user.username)) { //don't need to filter case-insensitively as we have a valid User.
      userMap = userMap.filterNot(_._2.username eq user.username)
      createUser(user.username, f)
    }
  }

  private[memory] def clearUser = userMap = Map[String, User]()
}