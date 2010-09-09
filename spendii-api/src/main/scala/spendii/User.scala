/*
 * Copyright 2009 Sanjiv Sahayam
 * Licensed under the Apache License, Version 2.0
 */
package spendii

import spendii.date.SomeDay.today
import date.Sdate

case class User(username:String, password:String, creationDate:Sdate = today)