package org.synyx.git

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class GittaTest extends FunSuite with ShouldMatchers {

  test("message help") {
    val gitta = new Gitta(null, null)
    gitta.privateMessage("Gitta: help") should be("help")
  }

  test("ignored message") {
    val gitta = new Gitta(null, null)
    gitta.privateMessage("ignored message") should be("")
  }
}
