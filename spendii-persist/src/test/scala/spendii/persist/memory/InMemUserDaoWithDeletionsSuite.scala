/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import org.scalatest.Tag

final class InMemUserDaoWithDeletionsSuite extends InMemDBUserDaoCommon {

  import TestData._

  object SuiteName {
    val AN_INMEMDB_SHOULD = "An InMemDB should "
  }

  import SuiteName._
  test(AN_INMEMDB_SHOULD + "delete the specified User") {
    val user1 = dao.createUser(USER1.username, USER1.password)
    val user2 = dao.createUser(USER2.username, USER2.password)
    dao.userCount should equal (2)
    dao.findUser(USER1.username) should equal (Some(user1))
    dao.findUser(USER2.username) should equal (Some(user2))

    dao.deleteUser(USER2)
    dao.findUser(USER2.username) should equal (None)
    dao.findUser(USER1.username) should equal (Some(user1))
    dao.userCount should equal (1)
  }

  test(AN_INMEMDB_SHOULD + "delete a User and all his/her spends") {
    val user = dao.createUser(USER2.username, USER2.password)
    dao.findUser(USER2.username) should equal (Some(user))
    dao.createDailySpend(USER_DATE_2, SPENDS_2)
    dao.findDailySpendByUserDate(USER_DATE_2) should not equal (None)

    dao.deleteUser(USER2)
    dao.findUser(USER2.username) should equal (None)
    dao.findDailySpendByUserDate(USER_DATE_2) should equal (None)
  }

  test(AN_INMEMDB_SHOULD + "not delete a User that does not exist") {
    val user = dao.createUser(USER1.username, USER1.password)
    dao.userCount should equal (1)
    dao.findUser(USER1.username) should equal (Some(user))

    dao.deleteUser(USER2)
    dao.findUser(USER1.username) should equal (Some(user))
    dao.userCount should equal (1)
  }
}