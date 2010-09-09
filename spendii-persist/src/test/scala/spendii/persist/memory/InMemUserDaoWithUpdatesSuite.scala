/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.User

final class InMemUserDaoWithUpdatesSuite extends InMemDBUserDaoCommon {

  import TestData._

  test("An InMemDB should update an existing User") {
    dao.findUser(USER1.username) should equal (None)
    val user = dao.createUser(USER1.username, USER1.password)
    dao.userCount should equal (1)
    dao.findUser(USER1.username) should equal (Some(user))

    dao.updateUser(user, CHANGED_PASSWORD)
    dao.findUser(USER1.username) should equal (Some(User(USER1.username, CHANGED_PASSWORD)))
    dao.findUser(USER1.username) should not equal (Some(user))
    dao.userCount should equal (1)
  }

  test("An InMemDB should not update a non-existant User") {
    dao.findUser(USER1.username) should equal (None)
    val user = dao.createUser(USER1.username, USER1.password)
    dao.userCount should equal (1)
    dao.findUser(USER1.username) should equal (Some(user))

    dao.updateUser(User(USER2.username, USER2.password), CHANGED_PASSWORD)
    dao.findUser(USER1.username) should equal (Some(user))
    dao.userCount should equal (1)
  }

}