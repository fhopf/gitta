package org.synyx.git

import org.eclipse.jgit.revwalk.RevCommit

class GittaServiceImpl(val repoService: RepositoryService, val configService: ConfigurationService) {

  // TODO try to get rid of all the loops
  def refresh(messageService: PircBotMessageService) {
    val repoConfigs = configService.readRepositoryConfig("repo.config")
    val ircConfig = configService.readIrcConfig("irc.config")
    for (repoConfig <- repoConfigs) {

      var latestCommit: RevCommit = null
      if(! repoConfig.isCheckedOut()) {
        repoService.updateRepo(repoConfig)
      } else {
        // get current commit
        latestCommit = repoService.latestCommit(repoConfig)
      }

      // update
      repoService.updateRepo(repoConfig)

      // log from last commit to now
      // only do something if there are new commits
      if(repoService.latestCommit(repoConfig) != latestCommit) {
        // feels like there should be a more functional way to this
        for (commit <- repoService.logSince(repoConfig, latestCommit)) {
          val message = String.format("%s: %s", commit.getAuthorIdent.getEmailAddress, commit.getShortMessage)
          for (channel <- ircConfig.channels) {
            messageService.sendMessage(channel, message)
          }
        }  
      }
      
    }

  }

}
