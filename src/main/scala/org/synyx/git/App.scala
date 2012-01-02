package org.synyx.git

object App {

  def main(args: Array[String]): Unit = {
    // init irc bot
    val configService = new ConfigurationService
    val ircConfig: IrcServer = configService.readIrcConfig("irc.config")
    val gitta = new Gitta(new GittaServiceImpl(new RepositoryService(), configService))
    gitta.connect(ircConfig.server)
    gitta.setAutoNickChange(true)
    gitta.setVerbose(true)
    for(channel <- ircConfig.channels) {
      gitta.joinChannel(channel)
    }

    //
  }

}