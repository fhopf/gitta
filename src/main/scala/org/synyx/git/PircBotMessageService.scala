package org.synyx.git

import org.jibble.pircbot.PircBot

class PircBotMessageService(val bot: PircBot) {

  def sendMessage(channel: String, message: String): Unit = {
    bot.sendMessage(channel, message);
  }

}