/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.boundary

import spendii.User

final class SpenderUserServiceSuite extends CommonBoundary {

  protected val validSpender = new ValidUserDao with SpenderUserService
  protected val invalidSpender = new NullUserDao with SpenderUserService

  object SuiteName {
    val A_SPENDER_USER_SERVICE_SHOULD = "A SpenderUserService should"
  }

  import TestData._
  import SuiteName._
  import userdao.ExceptionMessages._

  test(A_SPENDER_USER_SERVICE_SHOULD + " allow creation of a valid user") {
      val expectedUser = User(SPENDER_NAME, SPENDER_PASSWORD)
      expectSuccess(validSpender.addUser(SPENDER_NAME, SPENDER_PASSWORD), expectedUser)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return errors in creating Users as a Left") {
    expectError(invalidSpender.addUser("",""), CREATE_USER_FAIL_MSG)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " list all users") {
    expectSuccess(validSpender.listUsers, USERS)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return errors when listing users as a Left") {
    expectError(invalidSpender.listUsers, FIND_ALL_USERS_FAIL_MSG)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " locate a User") {
    expectSuccess(validSpender.locateUser(SPENDER_NAME), Some(User(SPENDER_NAME, SPENDER_PASSWORD)))
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return errors when locating a User as a Left") {
    expectError(invalidSpender.locateUser(""), FIND_USER_FAIL_MSG)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return the number of Users") {
    expectSuccess(validSpender.getNoOfUsers, 5)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return errors when returning the number of Users as a Left") {
    expectError(invalidSpender.getNoOfUsers, USER_COUNT_FAIL_MSG)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " update a User's password") {
    expectSuccess(validSpender.updatePassword(SPENDER_NAME, "rarara"), {})
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return an error if the User to update can't be found") {
    val spender = new FailedFindUserUserDao with SpenderUserService
    expectError(spender.updatePassword(SPENDER_NAME, "rarara"), "Could not locate specified user: [blah]")
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return an error if the User can't be updated") {
    val spender = new ExceptionThrowingFindUsersDao with SpenderUserService
    expectError(spender.updatePassword(SPENDER_NAME, "rarara"), UPDATE_USER_FAIL_MSG)
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " delete a specified User") {
    expectSuccess(validSpender.removeUser(SPENDER_NAME), {})
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " return an error if the User to delete can't be found") {
    val spender = new FailedFindUserUserDao with SpenderUserService
    expectError(spender.removeUser(USER_NAME_1), "Could not locate specified user: [aaaa]")
  }

  test(A_SPENDER_USER_SERVICE_SHOULD + " should return an error if the User can't be deleted.") {
    val spender = new ExceptionThrowingFindUsersDao with SpenderUserService
    expectError(spender.removeUser(SPENDER_NAME), DELETE_USER_FAIL_MSG)
  }

  trait ValidUserDao extends NullUserDao {
    override def createUser(username:String, password:String) : User = USER
    override def findAllUsers : Seq[User] = USERS
    override def findUser(username:String) : Option[User] = Some(USER)
    override def userCount : Int = NO_OF_USERS
    override def updateUser(user:User, f: => String) {}
    override def deleteUser(user:User) {}
  }

  trait FailedFindUserUserDao extends ValidUserDao {
    override def findUser(username:String) : Option[User] = None
  }

  //We need findUser to pass without an exception, but all other methods to throw an exception.
  trait ExceptionThrowingFindUsersDao extends NullUserDao {
    override def findUser(username:String) : Option[User] = Some(User(SPENDER_NAME, SPENDER_PASSWORD))
  }
}