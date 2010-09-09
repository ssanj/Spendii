/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii.persist.memory

import spendii.persist.PersistentService

/**
 * A Simple in-memory database. This is used mainly for testing without the need for a "real" database. It is not thread-safe or robust.
 */

trait MemoryDB extends InMemUserDao with InMemDailySpendDao with InMemLabelDao

private[memory] trait MemoryPersistentService extends PersistentService with InMemUserDao with InMemDailySpendDao with InMemLabelDao with DBAdmin

private[memory] object InMemDB extends MemoryPersistentService {

  override def clearAll {
    clearUser
    clearDailySpends
    clearLabels
  }
}