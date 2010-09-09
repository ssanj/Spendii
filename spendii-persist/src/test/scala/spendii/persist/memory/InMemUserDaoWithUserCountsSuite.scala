/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.User

final class InMemUserDaoWithUserCountsSuite extends InMemDBUserDaoCommon {

  import TestData._

  test("An InMemDB should have a userCount of zero when empty") {
    dao.userCount should equal (0)
  }

  test("An InMemDB should have a userCount matching the number of Users") {
    dao.userCount should equal (0)
    dao.createUser(USER1.username, USER1.password)
    dao.createUser(USER2.username, USER2.password)

    val user1 = dao.findUser(USER1.username)
    val user2 = dao.findUser(USER2.username)
    dao.userCount should equal (2)

    user1 should equal (Some(USER1))
    user2 should equal (Some(USER2))
  }

  def failOnNone(user:User) { fail("Expected "+ user +", got None")}

}