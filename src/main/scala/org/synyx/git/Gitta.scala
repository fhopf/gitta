package org.synyx.git

import org.jibble.pircbot.PircBot
import math.Ordering.String

class Gitta(val gittaService: GittaServiceImpl) extends PircBot {

  setName("Gitta")

  override def onMessage(channel: String, sender: String, login: String, hostname: String, message: String) = {
    val priv = privateMessage(message)
    priv match {
	  case "help" => sendHelpMessage(channel)
    case "exit" => System.exit(0)
    case "refresh" => gittaService.refresh(new PircBotMessageService(this))
    }
  }

  def sendHelpMessage(channel: String) = {
    sendMessage(channel, "Commands I understand: help (prints this message)")
    sendMessage(channel, "exit")
  }

  def privateMessage(message: String) = {
    if (message.startsWith(getName())) {
      // name + :
      val priv = message.substring(getName().length() + 1, message.length()).trim()
      println("got message: " + priv)
      priv
    } else {
      ""
    }

  }
}
