package org.synyx.git

import org.jibble.pircbot.PircBot

class Gitta extends PircBot {

  setName("Gitta")

  override def onMessage(channel: String, sender: String, login: String, hostname: String, message: String) = {
    if (message.trim().startsWith("help")) {
      sendMessage(channel, helpMessage());
    } else if (message.trim().equals("exit")) {
      System.exit(0)
    }
  }

  def helpMessage() = {
    "Commands I understand: help (prints this message)\\n exit"
  }
}