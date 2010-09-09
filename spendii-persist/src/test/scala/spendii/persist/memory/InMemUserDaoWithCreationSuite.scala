/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

final class InMemUserDaoWithCreationSuite extends InMemDBUserDaoCommon {

  import TestData._

  test("An InMemDB should create a User that does not exist") {
    dao.userCount should equal (0)
    dao.findUser(USER1.username) should equal (None)
    val user = dao.createUser(USER1.username, USER1.password)
    dao.userCount should equal (1)
    dao.findUser(USER1.username) should equal (Some(user))
  }

  test("An InMemDB should not create a User that exists") {
    dao.userCount should equal (0)
    val user1 = dao.createUser(USER1.username, USER1.password)
    dao.findUser(USER1.username) should equal (Some(user1))
    val user2 = dao.createUser(USER1.username, OVERWRITTEN_PASSWORD)
    dao.findUser(USER1.username) should equal (Some(user1))
    dao.userCount should equal (1)
  }

  test("An InMemDB should not create a User that exists with a username of a different case") {
    dao.userCount should equal (0)
    val user1 = dao.createUser(USER2.username, USER2.password)
    dao.findUser(USER2.username) should equal (Some(user1))

    val user2 = dao.createUser(USER2_MIXED.username, USER2_MIXED.password)
    dao.findUser(USER2_MIXED.username) should equal (Some(user1))
    dao.userCount should equal (1)
  }
}

