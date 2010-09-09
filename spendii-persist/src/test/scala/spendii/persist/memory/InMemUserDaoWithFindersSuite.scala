/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

final class InMemUserDaoWithFindersSuite extends InMemDBUserDaoCommon {

  import TestData._

  test("An InMemDB should not find a non-existant user") {
    dao.findUser(USER2.username) should equal (None)
  }

  test("An InMemDB should find an existing User") { findUser(USER2.username) }

  test("An InMemDB should find an existing User irrespective of case") {
    findUser(USER2.username)
    val user = dao.findUser(USER2_MIXED.username)
    user match {
      case Some(u) => u.username.equalsIgnoreCase(USER2_MIXED.username)
      case None => fail("Expected a valid user named [" +  USER2.username + "]")
    }
  }

  test("An InMemDB should find zero Users when empty") {
    dao.findAllUsers.isEmpty should equal (true)
  }

  test("An InMemDB should find all Users") {
    dao.findAllUsers.isEmpty should equal (true)
    dao.createUser(USER1.username, USER1.password)
    dao.createUser(USER2.username, USER2.password)

    val foundUsers = dao.findAllUsers
    foundUsers.size should equal (2)
    verifyUser(foundUsers(0), USER1)
    verifyUser(foundUsers(1), USER2)
  }

  private def findUser(username:String) {
    dao.findUser(username) should equal (None)
    val user = dao.createUser(username, username)
    dao.findUser(username) should equal (Some(user))
  }
}