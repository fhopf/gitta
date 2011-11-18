package org.synyx.git

class GittaServiceImpl(val messageService: PircBotMessageService, val repoService: RepositoryService, val configService: ConfigurationService) {

  // TODO try to get rid of all the loops
  def refresh() {
    val repoConfigs = configService.readRepositoryConfig("/todo")
    val ircConfig = configService.readIrcConfig("/todo")
    for (repoConfig <- repoConfigs) {
      
      // get current commit
      val latestCommit = repoService.latestCommit(repoConfig)

      // update
      repoService.updateRepo(repoConfig)

      // log from last commit to now
      // only do something if there are new commits
      if(repoService.latestCommit(repoConfig) != latestCommit) {
        // feels like there should be a more functional way to this
        for (commit <- repoService.logSince(repoConfig, latestCommit)) {
          val message = "" //String.format("Test", 1)
          for (channel <- ircConfig.channels) {
            messageService.sendMessage(channel, message)
          }
        }  
      }
      
    }

  }

}
