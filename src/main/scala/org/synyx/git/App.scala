package org.synyx.git

object App {

  def main(args: Array[String]): Unit = {
    // init irc bot
    val ircConfig: IrcServer = ConfigurationService.readIrcConfig("irc.config")
    val gitta = new Gitta()
    gitta.connect(ircConfig.server)
    gitta.setAutoNickChange(true)
    gitta.setVerbose(true)
    for(channel <- ircConfig.channels) {
      gitta.joinChannel(channel)
    }

    //
  }

}