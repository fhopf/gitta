package org.synyx.git

import org.jibble.pircbot.PircBot

class Gitta(private val configService: ConfigurationService, private val repositoryService: RepositoryService) extends PircBot {

  setName("Gitta")

  override def onMessage(channel: String, sender: String, login: String, hostname: String, message: String) {
    val priv = privateMessage(message)
    priv match {
      case "help" => sendHelpMessage(channel)
      case "exit" => System.exit(0)
      case "refresh" => refresh
    }
  }

  private def refresh() {

    val repoConfigs = configService.readRepositoryConfig("repo.config")
    val ircConfig = configService.readIrcConfig("irc.config")

    val commits: Iterable[Commit] = repositoryService.refresh(repoConfigs)

    for (channel <- ircConfig.channels) {
      for (commit <- commits.take(5)) {
        val message = String.format("%s (%s): %s", commit.repoName, commit.getAuthorName(), commit.getMessage)
        sendMessage(channel, message)
      }
      if (commits.size > 5) {
        sendMessage(channel, "(...)")
      }
    }

  }


  private def sendHelpMessage(channel: String) {
    sendMessage(channel, "Commands I understand: help (prints this message)")
    sendMessage(channel, "refresh (checks for any updates)")
    sendMessage(channel, "exit")
  }

  private def privateMessage(message: String) = {
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
